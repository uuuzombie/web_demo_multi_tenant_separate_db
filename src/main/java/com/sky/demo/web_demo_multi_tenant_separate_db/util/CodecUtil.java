package com.sky.demo.web_demo_multi_tenant_separate_db.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Created by user on 16/9/29.
 */
public class CodecUtil {

    private static final String CHARSET = "utf-8";

    public static String encode(String input) throws UnsupportedEncodingException {
        return Base64.getEncoder().encodeToString(input.getBytes(CHARSET));
    }

    public static String decode(String input) throws UnsupportedEncodingException {
        byte[] b = Base64.getDecoder().decode(input);
        return new String(b, CHARSET);
    }
}
