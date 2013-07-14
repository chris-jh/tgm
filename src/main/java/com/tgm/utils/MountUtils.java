/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.utils;

import com.tgm.enums.Platform;
import com.tgm.enums.Protocol;
import com.tgm.resources.Path;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.util.Collection;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author christopher
 */
@Component
public class MountUtils {

    private final String OK = "OK";
    public final static String SMB = "smb:";
    private final static String mountPath = "/tgm/mounted/";
    @Autowired
    private OSUtils osUtils;

    public boolean isMountable(Path path){
        if (Protocol.FILE.equals(path.getProtocol())){
            return false;
        }
        return true;
    }
    
    public String getMountedPath(Platform platform, Path path) throws Exception {
        return getMountPath(parseStringSpaces(platform.toString() + "/" + getLastFileName(path), SpaceType.UNDERSCORE), false);
    }

    public boolean mount(Platform platform, Path path) {
        try {
            if (Protocol.SMB.equals(path.getProtocol())) {
                File f = new File(path.getPath());
                return mountSMBDrive(path.getUsername(), path.getPassword(), path.getPath(), parseStringSpaces(platform.toString() + "/" + getLastFileName(path), SpaceType.UNDERSCORE));
            } else if (Protocol.FILE.equals(path.getProtocol())) {
                return true;
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass()).fatal(e);
        }
        return false;
    }

    public boolean unmount(Platform platform, Path path) {
        try {
            if (Protocol.SMB.equals(path.getProtocol())) {
                return unmountSMBDrive(parseStringSpaces(platform.toString() + "/" + getLastFileName(path), SpaceType.UNDERSCORE));
            } else if (Protocol.FILE.equals(path.getProtocol())) {
                return true;
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass()).fatal(e);
        }
        return false;
    }

    //windows net use X: //ip/roms /USER:username password
    //macosx mount -t smbfs //username:password@ip/roms ./mtest/
    //linux mount -t cifs //server/share -o username=UserName,password=myPassword /share
    public boolean mountSMBDrive(String username, String password, String srcPath, String destPath) {
        Logger.getLogger(this.getClass()).info("Mounting SMB/CIFS " + srcPath + " TO " + getMountPath(destPath));
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
                sb.append(parseStringSpaces(username, SpaceType.PERCENT_20));
                if (password != null) {
                    sb.append(":").append(parseStringSpaces(password, SpaceType.PERCENT_20)).append("@");
                } else {
                    sb.append("@");
                }
            }
            sb.append(parseStringSpaces(srcPath, SpaceType.PERCENT_20));
            sb.append(" ").append(getMountPath(destPath));

            p = executeScript(sb.toString());
            if (p != null) {
                Logger.getLogger(this.getClass()).info(p);
                return true;
            }
            return false;
        } catch (Exception e) {
            Logger.getLogger(this.getClass()).fatal(e);
            return false;
        } finally {
            sb.delete(0, sb.length());
            sb = null;
            p = null;
        }
    }

    public boolean unmountSMBDrive(String destPath) {
        if (OSUtils.isMac()) {
            return smbUnmountMac(destPath);
        }
        Logger.getLogger(this.getClass()).fatal("OS is not supported for mount SMB drive.");
        return false;
    }

    private boolean smbUnmountMac(String destPath) {
        String p = null;
        try {
            p = executeScript("umount " + getMountPath(destPath));
            if (p != null) {
                Logger.getLogger(this.getClass()).info(p);
                return true;
            }
            return false;
        } catch (Exception e) {
            Logger.getLogger(this.getClass()).fatal(e);
            return false;
        } finally {
            p = null;
        }
    }

    private String parseStringSpaces(String p, SpaceType type) {
        if (SpaceType.SLASH.equals(type)) {
            return StringUtils.replace(p, " ", "\\ ");
        } else if (SpaceType.DOUBLE_SLASH.equals(type)) {
            return StringUtils.replace(p, " ", "\\\\ ");
        } else if (SpaceType.PERCENT_20.equals(type)) {
            return StringUtils.replace(p, " ", "%20");
        } else if (SpaceType.UNDERSCORE.equals(type)) {
            return StringUtils.replace(p, " ", "_");
        } else {
            return p;
        }
    }

    private String getMountPath(String p) {
        return getMountPath(p, true);
    }

    private String getMountPath(String p, boolean check) {
        if (OSUtils.isWindows()) {
            return "P:"; //TODO: look for spare windows drive to mount........
        } else {
            if (check) {
                checkMountedDrive(osUtils.getTempPath() + mountPath + p);
            }
            return osUtils.getTempPath() + mountPath + p;
        }
    }

    private void checkMountedDrive(String p) {
        File f;
        String c;
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

    private String executeScript(String script) throws Exception {
        Process p;
        BufferedReader reader;
        BufferedReader ireader;
        BufferedReader ereader;
        StringBuilder sb = new StringBuilder();
        String line;
        boolean error = false;
        try {
            Logger.getLogger(this.getClass()).info("EXECUTE: " + script);
            p = Runtime.getRuntime().exec(script);
            p.waitFor();

            ireader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            ereader = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            reader = ireader;

            line = reader.readLine();
            if (line == null) {
                line = ereader.readLine();
                if (line != null) {
                    reader = ereader;
                    error = true;
                }
            }

            if (line != null) {
                sb.append(line);
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                if (error) {
                    throw new Exception(sb.toString());
                }
                return sb.toString();
            } else {
                return OK;
            }
        } catch (Exception ioe) {
            Logger.getLogger(this.getClass()).fatal(ioe);
            return null;
        } finally {
            reader = null;
            ireader = null;
            ereader = null;
            sb.delete(0, sb.length());
            sb = null;
            line = null;
        }

    }

    public static String getLastFileName(Path path) throws Exception {
        File f;
        try {
            f = new File(path.getPath());
            if ((f.getName() == null) || f.getName().trim().equals("")) {
                throw new Exception("No Filename Found");
            }
            return f.getName();
        } finally {
            f = null;
        }
    }

    /**
     * @param osUtils the osUtils to set
     */
    public void setOsUtils(OSUtils osUtils) {
        this.osUtils = osUtils;
    }

    public enum SpaceType {

        NONE,
        SLASH,
        DOUBLE_SLASH,
        UNDERSCORE,
        PERCENT_20,
    }
}
