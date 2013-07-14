/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.kickstarter;

import com.tgm.App;
import com.tgm.scanner.PlatformScanner;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author christopher
 */
public class KickStarter implements KickStartInterface {

    @Autowired
    private PlatformScanner platformScanner;
    
    @Autowired
    private App app;
    
    public void kickStart(){
        Logger.getLogger(this.getClass()).info("Kickstarting...");
        platformScanner.scan();
        //app.init();
    }

    /**
     * @param PlatformScanner the platformScanner to set
     */
    public void setPlatformScanner(PlatformScanner platformScanner) {
        this.platformScanner = platformScanner;
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
