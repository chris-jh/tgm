/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.interfaces;

import com.tgm.config.PlatformConfig;
import com.tgm.data.entity.GameEntity;
import com.tgm.data.entity.PlatformEntity;
import com.tgm.enums.Platform;
import com.tgm.resources.Path;

/**
 *
 * @author christopher
 */
public interface PlatformScannerInterface {

    public void processGame(Platform platform, String fileName, String path) throws Exception;

    public GameEntity saveGame(PlatformEntity platform, String name, String path) throws Exception;

    public PlatformEntity savePlatform(PlatformConfig config) throws Exception;

    public void scanGamePath(PlatformEntity platform, Path path);

    public boolean scanPlatform(PlatformConfig config) throws Exception;

    public void showList();
    
    public boolean isScanning();
}
