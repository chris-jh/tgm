/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.emu;

import com.tgm.data.dao.EmulatorDao;
import com.tgm.data.dao.EmulatorGameDao;
import com.tgm.data.entity.Emulator;
import com.tgm.data.entity.EmulatorGame;
import java.util.Map;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author christopher
 */
public class EmuScanner {

    @Autowired
    private EmulatorDao emulatorDao;
    @Autowired
    private EmulatorGameDao emulatorGameDao;
    @Autowired
    private ApplicationContext context;

    public void scan() {
        for (Map.Entry<String, EmuConfig> entry : context.getBeansOfType(EmuConfig.class).entrySet()) {
            scanEmu(entry.getValue());
        }
        showList();
    }

    @Transactional
    public void scanEmu(EmuConfig config) {
        Logger.getLogger(this.getClass()).info("Scanning Emulator: " + config.getName());
        Emulator e = null;
        try {
            e = emulatorDao.findEmulatorByName(config.getName());
        } catch (Exception ex) {
        }

        if (e == null) {
            e = emulatorDao.createInstance();
            e.setName(config.getName());
            e = emulatorDao.saveOrUpdate(e);
        }

        for (String romPath : config.getRomPath()) {
            Logger.getLogger(this.getClass()).info("    Scanning Rom Path: [" + romPath + "]");

            int max = RandomUtils.nextInt(10);
            for (int i = 0; i < max; i++) {
                EmulatorGame g = emulatorGameDao.createInstance();
                int t = RandomUtils.nextInt(999999);
                g.setRomLocation(romPath + "/" + t + ".rom");
                g.setName("" + t);
                g.setEmulatorRef(e);
                g = emulatorGameDao.saveOrUpdate(g);
                Logger.getLogger(this.getClass()).info("        Found: [" + g.getName() + "]");
            }
        }
        Logger.getLogger(this.getClass()).info("Scanning Emulator: " + config.getName() + " Done");

    }

    @Transactional
    public void showList() {
        Logger.getLogger(this.getClass()).info("Emulator Game List...");
        for (EmulatorGame emulatorGame : emulatorGameDao.findAllAndFetch()) {
            Logger.getLogger(this.getClass()).info("   " + emulatorGame.getEmulatorRef().getName() + " - " + emulatorGame.getName() + " [" + emulatorGame.getRomLocation() + "]");
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
    public void setEmulatorDao(EmulatorDao emulatorDao) {
        this.emulatorDao = emulatorDao;
    }

    /**
     * @param emulatorGameDao the emulatorGameDao to set
     */
    public void setEmulatorGameDao(EmulatorGameDao emulatorGameDao) {
        this.emulatorGameDao = emulatorGameDao;
    }
}
