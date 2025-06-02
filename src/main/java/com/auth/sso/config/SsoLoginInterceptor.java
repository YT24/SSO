package com.auth.sso.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

@Component
public class SsoLoginInterceptor implements HandlerInterceptor {
    @Value("${sso.server-url}")
    private String ssoServerUrl;
    @Value("${sso.client-id}")
    private String clientId;
    @Value("${sso.redirect-uri}")
    private String redirectUri;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Object user = request.getSession().getAttribute("user");
        if (user == null) {
            String authorizeUrl = ssoServerUrl + "/oauth2/authorize"
                    + "?client_id=" + clientId
                    + "&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8")
                    + "&response_type=code";
            response.sendRedirect(authorizeUrl);
            return false;
        }
        return true;
    }
}