/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.gui.interfaces;

import com.tgm.gui.enums.Screen;

/**
 *
 * @author christopher
 */
public interface AppInterface {
        
    public void init() throws Exception;
    public void processNextScreen(Screen scene);
    public void quit();
    
    
    public int getWidth();
    public int getHeight();
    
    public ScreenInterface getCurrentScreenInterface();
    public ScreenInterface getScreenInterface(Screen screen);

    
    
}
