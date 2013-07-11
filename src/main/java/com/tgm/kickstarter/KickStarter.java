/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.kickstarter;

import com.tgm.App;
import com.tgm.emu.EmuScanner;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author christopher
 */
public class KickStarter implements KickStartInterface {

    @Autowired
    private EmuScanner emuTest;
    
    @Autowired
    private App app;
    
    public void kickStart(){
        Logger.getLogger(this.getClass()).info("EmuBoot");
        emuTest.scan();
        app.init();
    }

    /**
     * @param emuTest the emuTest to set
     */
    public void setEmuTest(EmuScanner emuTest) {
        this.emuTest = emuTest;
    }

    /**
     * @return the app
     */
    public App getApp() {
        return app;
    }

    /**
     * @param app the app to set
     */
    public void setApp(App app) {
        this.app = app;
    }
}
