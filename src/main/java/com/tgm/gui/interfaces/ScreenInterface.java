/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.gui.interfaces;

import com.tgm.gui.enums.Command;
import com.tgm.gui.enums.Screen;
import org.newdawn.slick.Game;

/**
 *
 * @author christopher
 */
public interface ScreenInterface extends Game{
    
    public void configure();
    
    public void setAppInterface(AppInterface appInterface) throws Exception;

    public void reset();

    public void updateNextScene(Screen scene);
    
    public void command(Command command);
    
}
