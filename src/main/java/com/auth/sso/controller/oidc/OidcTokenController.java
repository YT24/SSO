package com.auth.sso.controller.oidc;

import com.auth.sso.common.Result;
import com.auth.sso.service.OAuthService;
import com.auth.sso.vo.TokenResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import com.auth.sso.service.impl.OAuthServiceImpl;

@RestController
@RequestMapping("/oidc")
public class OidcTokenController {

    @Autowired
    private OAuthService oAuthService;

    /**
     * OIDC password模式生成token
     */
    @PostMapping("/token")
    public Result<TokenResponse> passwordToken(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String client_id,
            @RequestParam String client_secret,
            @RequestParam(required = false) String scope) {
        // 直接复用OAuthServiceImpl的校验和token生成逻辑
        // 这里建议scope包含openid
        // 由于OAuthService接口未定义passwordGrant方法，这里直接实现逻辑
        return Result.success(oAuthService.exchangeTokenByPassword(username, password, client_id, client_secret, scope));
    }

    /**
     * 获取JWT公钥（JWK格式，便于客户端验签）
     * https://8gwifi.org/jwkconvertfunctions.jsp
     * 你需要将 JWK 格式的 n（模数）和 e（指数）转换为 PEM 格式的公钥（即 -----BEGIN PUBLIC KEY----- ... -----END PUBLIC KEY-----）。
     *   jwt.io 支持直接粘贴 PEM 格式公钥。
     */
    @GetMapping("/jwks")
    public Map<String, Object> getJwks() {
        RSAPublicKey publicKey = (RSAPublicKey) OAuthServiceImpl.getOrCreateKeyPair().getPublic();
        Map<String, Object> jwk = new HashMap<>();
        jwk.put("kty", "RSA");
        jwk.put("alg", "RS256");
        jwk.put("use", "sig");
        jwk.put("n", Base64.getUrlEncoder().withoutPadding().encodeToString(publicKey.getModulus().toByteArray()));
        jwk.put("e", Base64.getUrlEncoder().withoutPadding().encodeToString(publicKey.getPublicExponent().toByteArray()));
        jwk.put("kid", "sso-key-1");
        return Collections.singletonMap("keys", Collections.singletonList(jwk));
    }

    /**
     * 解析id_token获取用户信息
     */
    @GetMapping("/parse_id_token")
    public Result<Map<String, Object>> parseIdToken(@RequestParam String id_token) {
        try {
            RSAPublicKey publicKey = (RSAPublicKey) OAuthServiceImpl.getOrCreateKeyPair().getPublic();
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(id_token)
                    .getBody();
                    
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("sub", claims.getSubject());
            userInfo.put("aud", claims.getAudience());
            userInfo.put("iss", claims.getIssuer());
            userInfo.put("exp", claims.getExpiration());
            userInfo.put("iat", claims.getIssuedAt());
            userInfo.put("username", claims.get("username"));
            
            return Result.success(userInfo);
        } catch (Exception e) {
            return Result.fail(500,"无效的id_token:" + e.getMessage());
        }
    }
} 