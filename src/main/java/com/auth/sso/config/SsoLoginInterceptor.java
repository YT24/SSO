package com.auth.sso.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.UUID;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class SsoLoginInterceptor implements HandlerInterceptor {
    @Autowired
    private SsoClientProperties ssoClientProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = getToken(request);
        if (token != null && !token.isEmpty()) {
            // 校验token有效性
            boolean valid = checkToken(token);
            if (valid) {
                return true;
            }
        }
        // 未登录，重定向到SSO登录页
        String state = UUID.randomUUID().toString().replace("-", "");
        String loginUrl = ssoClientProperties.getServerUrl() + "/login.html"
                + "?client_id=" + URLEncoder.encode(ssoClientProperties.getClientId(), "UTF-8")
                + "&client_secret=" + URLEncoder.encode(ssoClientProperties.getClientSecret(), "UTF-8")
                + "&redirect_uri=" + URLEncoder.encode(ssoClientProperties.getRedirectUri(), "UTF-8")
                + "&state=" + state;
        response.sendRedirect(loginUrl);
        return false;
    }

    private String getToken(HttpServletRequest request) {
        // 1. Header
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        // 2. Cookie
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        // 3. Query param
        token = request.getParameter("token");
        return token;
    }

    private boolean checkToken(String token) {
        try {
            String url = ssoClientProperties.getServerUrl() + "/oauth2/check_token?access_token="
                    + URLEncoder.encode(token, "UTF-8");
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(3000);
            int code = conn.getResponseCode();
            if (code != 200)
                return false;
            StringBuilder resp = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null)
                    resp.append(line);
            }
            // 简单判断返回内容，实际可根据SSO返回格式调整
            return resp.toString().contains("true") || resp.toString().contains("\"success\":true");
        } catch (Exception e) {
            return false;
        }
    }
}