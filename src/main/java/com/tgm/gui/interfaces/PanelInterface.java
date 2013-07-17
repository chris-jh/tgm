/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.gui.interfaces;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author christopher
 */
public interface PanelInterface {
    
    public void init(GameContainer gc) throws SlickException;

    public void update(GameContainer gc, int i) throws SlickException;

    public void render(GameContainer gc, Graphics g) throws SlickException;
    
}
