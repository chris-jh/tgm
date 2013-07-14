/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.config;

import com.tgm.enums.Platform;
import com.tgm.resources.Path;
import com.tgm.resources.Path;
import java.util.List;
import org.springframework.util.Assert;

/**
 *
 * @author christopher
 */
public class PlatformConfig {
    private Platform platform;
    private String emuPath;
    private String emuArgs;
    private List<Path> romPath;
    
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(platform);
        Assert.notNull(emuPath);
        Assert.notNull(emuArgs);
        Assert.notNull(romPath);
    }

    /**
     * @return the emuPath
     */
    public String getEmuPath() {
        return emuPath;
    }

    /**
     * @param emuPath the emuPath to set
     */
    public void setEmuPath(String emuPath) {
        this.emuPath = emuPath;
    }

    /**
     * @return the emuArgs
     */
    public String getEmuArgs() {
        return emuArgs;
    }

    /**
     * @param emuArgs the emuArgs to set
     */
    public void setEmuArgs(String emuArgs) {
        this.emuArgs = emuArgs;
    }

    /**
     * @return the romPath
     */
    public List<Path> getRomPath() {
        return romPath;
    }

    /**
     * @param romPath the romPath to set
     */
    public void setRomPath(List<Path> romPath) {
        this.romPath = romPath;
    }

    /**
     * @return the platform
     */
    public Platform getPlatform() {
        return platform;
    }

    /**
     * @param platform the platform to set
     */
    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

}
