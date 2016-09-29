package com.sky.demo.web_demo_multi_tenant_separate_db.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by rg on 2015/7/15.
 * SHA加密
 */
public class SHAUtil {

    private static final Logger logger = LoggerFactory.getLogger(SHAUtil.class);

    private static final String ALGORITHM = "SHA-256";
    private static final String CHASET = "UTF-8";

    public static String encrypt(String str) throws UnsupportedEncodingException {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance(ALGORITHM);
        } catch (Exception e) {
            logger.error("MessageDigest get error",e);
            return "";
        }

        byte[] byteArray = str.getBytes(CHASET);
        byte[] md5Bytes = sha.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }


    public static void main(String[] args) {
        String str = "abc";
        String encrypt = null;
        try {
            encrypt = SHAUtil.encrypt(str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(encrypt);
    }
}
