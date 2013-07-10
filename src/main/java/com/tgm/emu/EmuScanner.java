/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.emu;

import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author christopher
 */
public class EmuScanner implements InitializingBean {

    @Autowired
    private ApplicationContext context;

    public void afterPropertiesSet() throws Exception {
        Logger.getLogger(this.getClass()).info("EmuScanner Initilize");

        for (Map.Entry<String, EmuConfig> entry : context.getBeansOfType(EmuConfig.class).entrySet()) {
            Logger.getLogger(this.getClass()).info("Loading Emu Config: " + entry.getValue().getName());
            for (String string : entry.getValue().getRomPath()) {
                Logger.getLogger(this.getClass()).info("Loading Emu Config: " + entry.getValue().getName() + " Searching Rom Path [" + string + "]");
            }
        }
    }

    /**
     * @param context the context to set
     */
    public void setContext(ApplicationContext context) {
        this.context = context;
    }
}
