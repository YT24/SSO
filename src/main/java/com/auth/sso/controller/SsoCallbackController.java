package com.auth.sso.controller;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.auth.sso.config.SsoClientProperties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;

@Controller
public class SsoCallbackController {

    @Resource
    private SsoClientProperties ssoClientProperties;

    @GetMapping("/sso/callback")
    public String callback(@RequestParam String code, HttpServletRequest request) throws Exception {
        // 1. 用code换token
        String tokenUrl = ssoClientProperties.getServerUrl() + "/oauth2/token";
        String params = "code=" + code
                + "&client_id=" + ssoClientProperties.getClientId()
                + "&client_secret=" + ssoClientProperties.getClientSecret()
                + "&redirect_uri=" + ssoClientProperties.getRedirectUri();

        HttpURLConnection conn = (HttpURLConnection) new URL(tokenUrl).openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        try (OutputStream os = conn.getOutputStream()) {
            os.write(params.getBytes());
        }
        StringBuilder resp = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null)
                resp.append(line);
        }
        JSONObject json = new JSONObject(resp.toString());
        String accessToken = json.getJSONObject("data").getString("access_token");

        // 2. 用token获取用户信息
        URL userInfoUrl = new URL(ssoClientProperties.getServerUrl() + "/oauth2/userinfo");
        HttpURLConnection userConn = (HttpURLConnection) userInfoUrl.openConnection();
        userConn.setRequestProperty("Authorization", "Bearer " + accessToken);
        StringBuilder userResp = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(userConn.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null)
                userResp.append(line);
        }
        JSONObject userJson = new JSONObject(userResp.toString());
        Object userInfo = userJson.get("data");

        // 3. 本地建立会话
        HttpSession session = request.getSession();
        session.setAttribute("user", userInfo);

        // 4. 跳转到首页
        return "redirect:/";
    }
}