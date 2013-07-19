/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.kickstarter;

import com.tgm.interfaces.KickStartInterface;
import com.tgm.gui.interfaces.AppInterface;
import com.tgm.interfaces.ScannerInterface;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author christopher
 */
public class KickStarter implements KickStartInterface {

    @Autowired
    private AppInterface appInterface;

    @Autowired
    private ScannerInterface scanner;

    @Override
    public void kickStart() {
        try {
            //scanner.scan();
            appInterface.init();
        } catch (Exception e) {
        }
    }

    /**
     * @param appInterface the appInterface to set
     */
    public void setAppInterface(AppInterface appInterface) {
        this.appInterface = appInterface;
    }

    /**
     * @param scanner the scanner to set
     */
    public void setScanner(ScannerInterface scanner) {
        this.scanner = scanner;
    }
}
