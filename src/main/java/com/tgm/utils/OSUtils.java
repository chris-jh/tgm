/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author christopher
 */
@Component
public class OSUtils {

    private static String OS = System.getProperty("os.name").toLowerCase();
    private static String HOME = System.getProperty("user.home");
    private static String TEMP = System.getProperty("java.io.tmpdir");
    @Value(value = "${tgm.path:~/.tgm}")
    private String tmgPath;

    public static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    public static boolean isMac() {
        return (OS.indexOf("mac") >= 0);
    }

    public static boolean isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
    }
    
    public String getAppHomePath(){
        if (tmgPath.startsWith("~")) {
            return HOME + tmgPath.substring(1);
        } else {
            return tmgPath;
        }
    }
    
    public static String getTempPath(){
        return TEMP;
    }

    /**
     * @param aTmgPath the tmgPath to set
     */
    public void setTmgPath(String aTmgPath) {
        tmgPath = aTmgPath;
    }
}
