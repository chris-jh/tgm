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
    private PlatformDao emulatorDao;
    @Autowired
    private GameDao emulatorGameDao;
    @Autowired
    private ApplicationContext context;
    @Autowired
    private MountUtils mountUtils;
    private final HashMap<Platform, PlatformConfig> emuConfigs = new HashMap<Platform, PlatformConfig>();
    private final String assertMessage = "An EmuConfig already contains platform ";

    public void scan() {
        Logger.getLogger(this.getClass()).info("Platform Scanner Initialised...");

        findAllEmuConfigs();

        /*for (Map.Entry<Platform, EmuConfig> entry : emuConfigs.entrySet()) {
         scanEmu(entry.getValue());
         }

         showList();*/
        Logger.getLogger(this.getClass()).info("Platform Scanner Initialised...Completed");

    }

    private void findAllEmuConfigs() {
        for (Map.Entry<String, PlatformConfig> entry : context.getBeansOfType(PlatformConfig.class).entrySet()) {
            Assert.isTrue((!emuConfigs.containsKey(entry.getValue().getPlatform())), assertMessage + entry.getValue().getPlatform());
            emuConfigs.put(entry.getValue().getPlatform(), entry.getValue());
        }
    }

    @Transactional
    public void scanEmu(PlatformConfig config) {
        Logger.getLogger(this.getClass()).info("Scanning Emulator: " + config.getPlatform());
        PlatformEntity e = null;
        try {
            e = emulatorDao.findPlatformByName(config.getPlatform());
        } catch (Exception ex) {
        }

        if (e == null) {
            e = emulatorDao.createInstance();
            e.setName(config.getPlatform());
            e = emulatorDao.saveOrUpdate(e);
        }

        for (Path path : config.getRomPath()) {
            String romPath = path.getPath();
            Logger.getLogger(this.getClass()).info("    Scanning Rom Path: [" + romPath + "]");
            if (Protocol.SMB.equals(path.getProtocol())) {
            }


            int max = RandomUtils.nextInt(10);
            for (int i = 0; i < max; i++) {
                GameEntity g = emulatorGameDao.createInstance();
                int t = RandomUtils.nextInt(999999);
                g.setRomLocation(romPath + "/" + t + ".rom");
                g.setName("" + t);
                g.setPlatformRef(e);
                g = emulatorGameDao.saveOrUpdate(g);
                Logger.getLogger(this.getClass()).info("        Found: [" + g.getName() + "]");
            }
        }
        Logger.getLogger(this.getClass()).info("Scanning Emulator: " + config.getPlatform() + " Done");

    }

    @Transactional
    public void showList() {
        Logger.getLogger(this.getClass()).info("Emulator Game List...");
        for (GameEntity emulatorGame : emulatorGameDao.findAllAndFetch()) {
            Logger.getLogger(this.getClass()).info("   " + emulatorGame.getPlatformRef().getName() + " - " + emulatorGame.getName() + " [" + emulatorGame.getRomLocation() + "]");
        }
        Logger.getLogger(this.getClass()).info("Emulator Game List...Done");
    }

    /**
     * @param context the context to set
     */
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    /**
     * @param emulatorDao the emulatorDao to set
     */
    public void setEmulatorDao(PlatformDao emulatorDao) {
        this.emulatorDao = emulatorDao;
    }

    /**
     * @param emulatorGameDao the emulatorGameDao to set
     */
    public void setEmulatorGameDao(GameDao emulatorGameDao) {
        this.emulatorGameDao = emulatorGameDao;
    }

    /**
     * @param mountUtils the mountUtils to set
     */
    public void setMountUtils(MountUtils mountUtils) {
        this.mountUtils = mountUtils;
    }
}
