/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author christopher
 */
public class RegExpUtils {

    public static void main(String args[]) {
        System.out.println("FIND=" + RegExpUtils.substringRegExp("Quack Shot Starring Donald Duck (JUE) (Sw) (123123123) (REV01) [a1][c][!].gen", " \\(.*[1ABCEFGD4JKSUIW]\\)"));

    }

    public static boolean match(String v, String pattern) {
        return (substringRegExp(v, pattern) != null);
    }

    public static String substringMultiRegExp(String v, String... patterns) {
        StringBuffer b;
        String t;
        try {
            b = new StringBuffer();
            for (String p : patterns) {
                t = substringRegExp(v, p);
                if (t != null) {
                    b.append(t);
                }
            }
            if (b.length() > 0){
                return b.toString();
            }
            return null;
        } finally {
            t = null;
            b = null;
        }
    }

    public static String substringRegExp(String v, String pattern) {
        Pattern p;
        Matcher m;
        String t;
        try {
            p = Pattern.compile(pattern);
            m = p.matcher(v);

            if (m.find()) {
                t = m.group();
                if (StringUtils.countMatches(t, ")") > 1) {
                    return t.substring(0, t.indexOf(")") + 1).trim();
                }
                return t.trim();
            }
            return null;
        } finally {
            p = null;
            m = null;
            t = null;
        }
    }
}
