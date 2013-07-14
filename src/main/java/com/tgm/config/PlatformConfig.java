/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.config;

import com.tgm.enums.Platform;
import com.tgm.resources.Path;
import java.util.List;
import org.springframework.util.Assert;

/**
 *
 * @author christopher
 */
public class PlatformConfig {

    private Platform platform;
    private String emulatorPath;
    private String emulatorArgs;
    private List<Path> gamePath;

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(platform);
        Assert.notNull(gamePath);
        if (!Platform.PC.equals(platform)) {
            Assert.notNull(emulatorPath);
            Assert.notNull(emulatorArgs);
        }
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

    /**
     * @return the emulatorPath
     */
    public String getEmulatorPath() {
        return emulatorPath;
    }

    /**
     * @param emulatorPath the emulatorPath to set
     */
    public void setEmulatorPath(String emulatorPath) {
        this.emulatorPath = emulatorPath;
    }

    /**
     * @return the emulatorArgs
     */
    public String getEmulatorArgs() {
        return emulatorArgs;
    }

    /**
     * @param emulatorArgs the emulatorArgs to set
     */
    public void setEmulatorArgs(String emulatorArgs) {
        this.emulatorArgs = emulatorArgs;
    }

    /**
     * @return the gamePath
     */
    public List<Path> getGamePath() {
        return gamePath;
    }

    /**
     * @param gamePath the gamePath to set
     */
    public void setGamePath(List<Path> gamePath) {
        this.gamePath = gamePath;
    }
}
