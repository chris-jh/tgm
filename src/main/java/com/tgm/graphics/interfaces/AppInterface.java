/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.graphics.interfaces;

import com.tgm.graphics.enums.Screen;

/**
 *
 * @author christopher
 */
public interface AppInterface {
        
    public void processNextScreen(Screen scene);
    public void quit();
    
    public int getWidth();
    public int getHeight();
    
    public ScreenInterface getCurrentScreenInterface();

    
}
