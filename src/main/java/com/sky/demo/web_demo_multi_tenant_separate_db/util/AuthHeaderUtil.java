package com.sky.demo.web_demo_multi_tenant_separate_db.util;

import com.google.common.collect.Maps;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 16/9/29.
 */
public class AuthHeaderUtil {

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String AUTHORIZATION = "Authorization";
    public static final String APPLICATION_JSON = "application/json";
    public static final String ACCEPT_TYPE = "Accept";


    private static Map<String, String> generateAuthHeader(String id, String token) throws Exception {
        Map<String, String> map = Maps.newHashMap();
        map.put(CONTENT_TYPE, APPLICATION_JSON);
        map.put(ACCEPT_TYPE, APPLICATION_JSON);

        String authCode = generateAuthorization(id, token);
        map.put(AUTHORIZATION, authCode);
        return map;
    }

    private static String generateAuthorization(String id, String token) throws UnsupportedEncodingException {
        String timestamp = String.valueOf(new Date().getTime());
        String authCode = timestamp + token + id;

        String enCodeBySha = SHAUtil.encrypt(authCode);       //SHA-256
        String code = timestamp + ":" + enCodeBySha + ":" + id;

        return CodecUtil.encode(code);      //Base64编码
    }

    public static void main(String[] args) throws Exception {
        String id = "83af3152-b58f-fd93-beb9-c38aca1d2b06";
        String token = "ebdb57afec464a979f946f1c6967a8cf";

        Map<String, String> map = AuthHeaderUtil.generateAuthHeader(id, token);
        System.out.println(map);
    }
}