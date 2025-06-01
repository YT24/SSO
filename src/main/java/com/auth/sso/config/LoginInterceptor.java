package com.auth.sso.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    // 可配置的白名单
    private static final Set<String> WHITE_LIST = new HashSet<>();
    static {
        // OauthController所有接口白名单
        WHITE_LIST.add("/oauth2/authorize");
        WHITE_LIST.add("/oauth2/token");
        WHITE_LIST.add("/oauth2/userinfo");
        WHITE_LIST.add("/oauth2/check_token");
        WHITE_LIST.add("/oauth2/refresh_token");
        WHITE_LIST.add("/login");
    }

    private String clientId;

    private String clientSecret;

    private String redirectUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String uri = request.getRequestURI();
        // 白名单放行
        if (WHITE_LIST.stream().anyMatch(uri::startsWith)) {
            return true;
        }
        // 判断登录（以session中user为例）
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            // 未登录，跳转到登录页，带上原始地址
            String redirect = String.format(
                    "/login?clientId=%s&clientSecret=%s&redirectUrl=%s", clientId, clientSecret, redirectUrl);
            response.sendRedirect(redirect);
            return false;
        }
        return true;
    }

    // 可扩展：提供setWhiteList方法支持外部配置
}