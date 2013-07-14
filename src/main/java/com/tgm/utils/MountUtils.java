/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author christopher
 */
@Component
public class MountUtils {

    public final static String SMB = "smb:";
    private final static String mountPath = "/mounted";
    
    @Autowired
    private OSUtils osUtils;

    //windows net use X: //ip/roms /USER:username password
    //macosx mount -t smbfs //username:password@ip/roms ./mtest/
    //linux mount -t cifs //server/share -o username=UserName,password=myPassword /share
    public boolean mountSMBDrive(String username, String password, String srcPath, String destPath) {
        if (OSUtils.isMac()) {
            return smbMountMac(username, password, srcPath, destPath);
        }
        Logger.getLogger(this.getClass()).fatal("OS is not supported for mount SMB drive.");
        return false;
    }

    private boolean smbMountMac(String username, String password, String srcPath, String destPath) {
        StringBuilder sb = new StringBuilder();
        String p = null;
        try {
            sb.append("mount -t smbfs //");
            if (username != null) {
                sb.append(username);
                if (password != null) {
                    sb.append(":").append(password).append("@");
                } else {
                    sb.append("@");
                }
            }
            sb.append(srcPath);
            sb.append(" ").append(getMountPath(destPath));

            p = executeScript(sb.toString());
            if (p != null) {
                Logger.getLogger(this.getClass()).info(p);
                return true;
            }
            return false;
        } finally {
            sb.delete(0, sb.length());
            sb = null;
            p = null;
        }
    }

    private String getMountPath(String p) {
        checkMountedDrive(osUtils.getAppHomePath() + mountPath + p);
        return osUtils.getAppHomePath() + mountPath + p;
    }

    private void checkMountedDrive(String p) {
        File f;
        try {
            f = new File(p);
            if (!f.exists()) {
                f.mkdirs();
            }
        } finally {
            f = null;
        }
    }

    private String conv(String c) {
        if (c.startsWith("~")) {
            return System.getProperty("user.home") + c.substring(1);
        } else {
            return c;
        }
    }

    private String executeScript(String script) {
        Process p;
        try {
            p = Runtime.getRuntime().exec(script);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            StringBuilder sb = new StringBuilder();

            String line = reader.readLine();
            sb.append(line);
            while (line != null) {
                line = reader.readLine();
                sb.append(line);
            }
            return sb.toString();

        } catch (Exception ioe) {
            return null;
        }

    }

    /**
     * @param osUtils the osUtils to set
     */
    public void setOsUtils(OSUtils osUtils) {
        this.osUtils = osUtils;
    }
}
