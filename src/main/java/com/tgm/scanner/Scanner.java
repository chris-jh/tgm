/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scanner;

import com.tgm.interfaces.ScannerInterface;
import com.tgm.interfaces.PlatformScannerInterface;
import com.tgm.config.PlatformConfig;
import com.tgm.enums.Platform;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.Assert;

/**
 *
 * @author christopher
 */
public class Scanner implements ScannerInterface {

    private boolean scanning = false;
    private final HashMap<Platform, PlatformConfig> platformConfigs = new HashMap<Platform, PlatformConfig>();
    private Lock scanLock = new ReentrantLock(true);
    private final String assertMessage = "An PlatformConfig already contains platform ";
    @Autowired
    private ApplicationContext context;
    @Autowired
    private PlatformScannerInterface platformScannerInterface;

    @Async
    @Override
    public void scan() {
        Logger.getLogger(this.getClass()).info("Scanner Starting Up...");
        if (scanLock.tryLock()) {
            scanning = true;
            try {
                Logger.getLogger(this.getClass()).info("Scanner Initialised...1");

                try {
                    findAllPlatformConfigs();

                    platformScannerInterface.showList();


                    for (Map.Entry<Platform, PlatformConfig> entry : platformConfigs.entrySet()) {

                        scanning = platformScannerInterface.scanPlatform(entry.getValue());

                        if (!scanning) {
                            return;
                        }

                    }

                    platformScannerInterface.showList();
                    Logger.getLogger(this.getClass()).info("Scanner Initialised...Completed");
                } catch (Exception e) {
                    Logger.getLogger(this.getClass()).fatal(e);
                }

            } finally {
                scanLock.unlock();
            }
        } else {
            Logger.getLogger(this.getClass()).info("Scanner Already Scanning......");
        }

    }

    private void findAllPlatformConfigs() {
        Logger.getLogger(this.getClass()).info("Find All Platform Configs......");

        for (Map.Entry<String, PlatformConfig> entry : context.getBeansOfType(PlatformConfig.class).entrySet()) {
            Assert.isTrue((!platformConfigs.containsKey(entry.getValue().getPlatform())), assertMessage + entry.getValue().getPlatform());
            platformConfigs.put(entry.getValue().getPlatform(), entry.getValue());
        }
        Logger.getLogger(this.getClass()).info("Find All Platform Configs......Done");

    }

    /**
     * @param platformScannerInterface the platformScannerInterface to set
     */
    public void setPlatformScannerInterface(PlatformScannerInterface platformScannerInterface) {
        this.platformScannerInterface = platformScannerInterface;
    }

    /**
     * @param context the context to set
     */
    public void setContext(ApplicationContext context) {
        this.context = context;
    }
}
