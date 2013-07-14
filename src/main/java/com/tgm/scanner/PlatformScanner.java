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
import com.tgm.enums.Protocol;
import com.tgm.resources.Path;
import com.tgm.utils.MountUtils;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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

    public void scan() {
        Logger.getLogger(this.getClass()).info("Platform Scanner Initialised...");

        findAllPlatformConfigs();

        /*for (Map.Entry<Platform, PlatformConfig> entry : platformConfigs.entrySet()) {
         scanPlatform(entry.getValue());
         }

         showList();*/
        Logger.getLogger(this.getClass()).info("Platform Scanner Initialised...Completed");

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

        for (Path path : config.getGamePath()) {
            String romPath = path.getPath();
            Logger.getLogger(this.getClass()).info("    Scanning Rom Path: [" + romPath + "]");
            if (Protocol.SMB.equals(path.getProtocol())) {
            }


            int max = RandomUtils.nextInt(10);
            for (int i = 0; i < max; i++) {
                GameEntity g = gameDao.createInstance();
                int t = RandomUtils.nextInt(999999);
                g.setRomLocation(romPath + "/" + t + ".rom");
                g.setName("" + t);
                g.setPlatformRef(e);
                g = gameDao.saveOrUpdate(g);
                Logger.getLogger(this.getClass()).info("        Found: [" + g.getName() + "]");
            }
        }
        Logger.getLogger(this.getClass()).info("Scanning Platform: " + config.getPlatform() + " Done");

    }

    @Transactional
    public void showList() {
        Logger.getLogger(this.getClass()).info("Game List...");
        for (GameEntity game : gameDao.findAllAndFetch()) {
            Logger.getLogger(this.getClass()).info("   " + game.getPlatformRef().getName() + " - " + game.getName() + " [" + game.getRomLocation() + "]");
        }
        Logger.getLogger(this.getClass()).info("Game List...Done");
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
