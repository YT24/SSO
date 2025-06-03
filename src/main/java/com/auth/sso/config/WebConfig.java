package com.auth.sso.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private SsoLoginInterceptor ssoLoginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(ssoLoginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/oauth2/callback",
                        "/static/**",
                        "/public/**",
                        "/oauth2/authorize",
                        "/oauth2/token",
                        "/oauth2/userinfo",
                        "/oauth2/check_token",
                        "/oauth2/refresh_token",
                        "/oauth2/login",
                        "/login.html",
                        "/",
                        "/index.html",
                        "/favicon.ico");
    }
}