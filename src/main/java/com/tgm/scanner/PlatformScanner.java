/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scanner;

import com.tgm.config.PlatformConfig;
import com.tgm.data.dao.PlatformDao;
import com.tgm.data.dao.GameDao;
import com.tgm.data.entity.PlatformEntity;
import com.tgm.data.entity.GameEntity;
import com.tgm.enums.Platform;
import com.tgm.resources.Path;
import com.tgm.utils.GameUtils;
import com.tgm.utils.MountUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 *
 * @author christopher
 */
public class PlatformScanner {

    @Autowired
    private PlatformDao platformDao;
    @Autowired
    private GameDao gameDao;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private MountUtils mountUtils;
    private final HashMap<Platform, PlatformConfig> platformConfigs = new HashMap<Platform, PlatformConfig>();
    private final String assertMessage = "An PlatformConfig already contains platform ";
    private boolean scanning = false;
    private Lock scanLock = new ReentrantLock(true);

    @Async
    public void scan() {
        if (scanLock.tryLock()) {
            try {
                Logger.getLogger(this.getClass()).info("Platform Scanner Initialised...");

                findAllPlatformConfigs();

                for (Map.Entry<Platform, PlatformConfig> entry : platformConfigs.entrySet()) {
                    scanPlatform(entry.getValue());
                }
                
                showList();
                Logger.getLogger(this.getClass()).info("Platform Scanner Initialised...Completed");

            } finally {
                scanLock.unlock();
            }
        } else {
            Logger.getLogger(this.getClass()).info("Platform Scanner Already Scanning......");
        }

    }

    private void findAllPlatformConfigs() {
        for (Map.Entry<String, PlatformConfig> entry : context.getBeansOfType(PlatformConfig.class).entrySet()) {
            Assert.isTrue((!platformConfigs.containsKey(entry.getValue().getPlatform())), assertMessage + entry.getValue().getPlatform());
            platformConfigs.put(entry.getValue().getPlatform(), entry.getValue());
        }
    }

    @Transactional
    public void scanPlatform(PlatformConfig config) {
        Logger.getLogger(this.getClass()).info("Scanning Platform: " + config.getPlatform());
        try {
            PlatformEntity platform = savePlatform(config);
            for (Path path : config.getGamePath()) {
                scanGamePath(platform, path);
            }
            Logger.getLogger(this.getClass()).info("Scanning Platform: " + config.getPlatform() + " Done");
        } catch (Exception e) {
            Logger.getLogger(this.getClass()).fatal(e);
        }
    }

    public void scanGamePath(PlatformEntity platform, Path path) {
        Logger.getLogger(this.getClass()).info("Scanning Game Path: [" + path.getPath() + "]");
        if (mountUtils.isMountable(path)) {
            if (mountUtils.mount(platform.getName(), path)) {
                try {
                    GameUtils.processGameFiles(this, platform.getName(), mountUtils.getMountedPath(platform.getName(), path), platform.getName().getFileTypes(), true);
                } catch (Exception e) {
                    Logger.getLogger(this.getClass()).fatal(e);
                }
                if (!mountUtils.unmount(platform.getName(), path)) {
                    Logger.getLogger(this.getClass()).fatal("Unable to unmount drive for platform: " + platform.getName());
                }
            } else {
                Logger.getLogger(this.getClass()).fatal("Unable to mount drive check gamepath config for platform: " + platform.getName());
            }
        } else {
            try {
                GameUtils.processGameFiles(this, platform.getName(), mountUtils.getMountedPath(platform.getName(), path), platform.getName().getFileTypes(), false);
            } catch (Exception e) {
                Logger.getLogger(this.getClass()).fatal(e);
            }
        }
        Logger.getLogger(this.getClass()).info("Scanning Game Path: [" + path.getPath() + "] Completed");
    }

    //TODO: Add method to find game details from thegamesdb.net
    @Transactional
    public void processGame(Platform platform, String fileName, String path) throws Exception {
        Logger.getLogger(this.getClass()).info("Found Game: " + platform.name() + " - " + fileName + " | [" + path + "]");
        saveGame(platformDao.findPlatformByName(platform), fileName, path);
    }

    @Transactional
    public PlatformEntity savePlatform(PlatformConfig config) throws Exception {
        PlatformEntity e = null;
        try {
            e = platformDao.findPlatformByName(config.getPlatform());
        } catch (Exception ex) {
        }

        if (e == null) {
            e = platformDao.createInstance();
            e.setName(config.getPlatform());
            e = platformDao.saveOrUpdate(e);
        }
        return e;
    }

    @Transactional
    public GameEntity saveGame(PlatformEntity platform, String name, String path) throws Exception {
        GameEntity g = null;
        try {
            try {
                g = gameDao.findByNameAndPlatform(name, platform);
            } catch (Exception e) {
                g = null;
            }

            if (g == null) {
                g = gameDao.createInstance();
                g.setGamePath(path);
                g.setName(name);
                g.setPlatformRef(platform);
                g = gameDao.saveOrUpdate(g);
                Logger.getLogger(this.getClass()).info("SAVED GAME: " + g.toString() + " " + g.getName() + " " + g.getId());
                return g;
            } else {
                return g;
            }
        } finally {
            g = null;
        }
    }

    @Transactional
    public void showList() {
        Logger.getLogger(this.getClass()).info("\n\n\nGame List...");
        int c = 0;
        for (GameEntity game : gameDao.findAllAndFetch()) {
            Logger.getLogger(this.getClass()).info("   " + game.getPlatformRef().getName() + " - " + game.getName() + " [" + game.getGamePath() + "]");
            c++;
        }
        Logger.getLogger(this.getClass()).info("Game List...Done [" + c + "]");
    }

    /**
     * @param context the context to set
     */
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    /**
     * @param platformDao the platformDao to set
     */
    public void setPlatformDao(PlatformDao platformDao) {
        this.platformDao = platformDao;
    }

    /**
     * @param gameDao the gameDao to set
     */
    public void setGameDao(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    /**
     * @param mountUtils the mountUtils to set
     */
    public void setMountUtils(MountUtils mountUtils) {
        this.mountUtils = mountUtils;
    }
}
