/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.graphics.screens;

import com.tgm.graphics.App;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author christopher
 */
public class MainScreen extends AbstractScreen {

    {
        screenName = "Main Scene";
    }

    @Override
    public void configure() {
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        initBackground();
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        App.menu.update(gc, i);
    }

    @Override
    public void render(GameContainer gc, Graphics grphcs) throws SlickException {
        drawBackground();
        App.menu.render(gc, grphcs);
    }

    @Override
    public void reset() {
    }
}
