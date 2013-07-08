/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.interfaces;

import org.jsfml.graphics.RenderTarget;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;

/**
 *
 * @author christopher
 */
public interface AppInterface {
    
    public Clock getClock();
    public RenderTarget getRenderTarget();
    public RenderWindow getRenderWindow();
    
}
