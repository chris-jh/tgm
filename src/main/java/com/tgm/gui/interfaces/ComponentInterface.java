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
public interface ComponentInterface {

    public String getId();

    public float getX();

    public float getY();

    public void setX(float x);

    public void setY(float y);

    public void setLocation(float x, float y);

    public float getWidth();

    public float getHeight();

    public void setWidth(float width);

    public void setHeight(float height);

    public void setSize(float width, float height);

    public boolean isInitialised();

    public ComponentInterface getParentComponent();

    public void setParentComponent(ComponentInterface parentComponent);
    
    public void setParentScreen(ScreenInterface screenInterface);

    public void setAppInterface(AppInterface appInterface);
    
    public void init(GameContainer gc) throws SlickException;

    public void update(GameContainer gc, int i) throws SlickException;

    public void render(GameContainer gc, Graphics g) throws SlickException;
    
    public void setVisable(boolean visible);
    
    public boolean isVisiable();
}
