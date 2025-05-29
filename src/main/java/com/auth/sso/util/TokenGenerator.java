package com.auth.sso.util;

import java.security.SecureRandom;
import java.util.Base64;

public class TokenGenerator {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    /**
     * 生成符合CAS风格的高强度token
     * @param byteLength 字节长度，32字节=256bit，Base64后约43字符
     */
    public static String generateToken(int byteLength) {
        byte[] randomBytes = new byte[byteLength];
        SECURE_RANDOM.nextBytes(randomBytes);
        // Base64 URL Safe 编码，去掉+/=，更适合URL和Header传递
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
} 