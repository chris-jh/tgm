/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.gui.lib;

import com.tgm.gui.App;
import com.tgm.gui.enums.Screen;
import com.tgm.gui.interfaces.ScreenInterface;
import com.tgm.gui.utils.ScreenUtils;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author christopher
 */
public class Engine extends BasicGame {

    private HashMap<Screen, ScreenInterface> screens;
    ScreenInterface screen;

    public void setScreens(HashMap<Screen, ScreenInterface> screens) {
        this.screens = screens;
    }

    public void setScreen(ScreenInterface screen) {
        this.screen = screen;
    }

    public Engine(String gamename) {
        super(gamename);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        Logger.getLogger(this.getClass()).info("INIT ENGINE");

        for (Map.Entry<Screen, ScreenInterface> entry : screens.entrySet()) {
            Logger.getLogger(this.getClass()).info("INIT ENGINE SCREEN: " + entry.getValue().getTitle());
            entry.getValue().init(gc);
        }
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
            System.exit(0);
        }
        if (screen != null) {
            screen.update(gc, i);
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        if (screen != null) {
            screen.render(gc, g);
        }

    }
}
