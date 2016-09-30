package com.sky.demo.web_demo_multi_tenant_separate_db.util;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 16/9/29.
 */
public class UnicodeUtil {

    private static final Pattern PATTERN_CN = Pattern.compile("[\\u4e00-\\u9fa5]");     //中文
    private static final Pattern PATTERN_UNICODE = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

    /**
     *
     * @param str
     * @return
     */
    private static String encodeUnicode(String str) {
        StringBuilder unicodeStr = new StringBuilder();
        char[] utfBytes = str.toCharArray();
        for (char utfByte : utfBytes) {
            String hexB = Integer.toHexString(utfByte);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeStr.append("\\u").append(hexB);
        }
        return unicodeStr.toString();
    }

    /**
     * 编码 中文->Unicode码
     * @param str
     * @return
     */
    public static String encodeCnToUnicode(String str) {
        Matcher matcher = PATTERN_CN.matcher(str);
        while (matcher.find()) {
            String unicode = UnicodeUtil.encodeUnicode(matcher.group());
            str = str.replace(matcher.group(), unicode);
        }
        return str;
    }

    /**
     * 编码 中文->Unicode码  \u1234 -> \x{1234}
     * perl don't support \u1234,use \x{1234} instead
     * @param str
     * @return
     */
    public static String encodeCnToUnicodeWithX(String str) {
        Matcher matcher = PATTERN_CN.matcher(str);
        while (matcher.find()) {
            String unicode = UnicodeUtil.encodeUnicode(matcher.group());
            StringBuilder xCode = new StringBuilder();
            xCode.append("\\x{").append(unicode.substring(unicode.indexOf("u") + 1)).append("}");
            str = str.replace(matcher.group(), xCode);
        }
        return str;
    }

    /**
     * 解码 Unicode -> 中文
     * @param str
     * @return
     */
    public static String decodeUnicodeToCn(String str) {
        Matcher matcher = PATTERN_UNICODE.matcher(str);
        while (matcher.find()) {
            char ch = (char)Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), String.valueOf(ch));
        }
        return str;
    }

    public static void main(String[] args) {
        List<String> list = Lists.newArrayList("\u6700\u8fd1", "\u5929", "\u5ba2\u6237\u7aef", "\u64cd\u4f5c", "\u7c7b\u578b", "\u91ca\u653e\u72b6\u6001", "\u6d41\u91cf\u5927\u5c0f", "\u90e8\u7f72\u7248\u672c");
        for (String str : list) {
            System.out.println(UnicodeUtil.encodeCnToUnicodeWithX(str));
        }

        for (String str : list) {
            System.out.println(UnicodeUtil.decodeUnicodeToCn(str));
        }
    }
}
