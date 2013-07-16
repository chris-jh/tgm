/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.graphics.screens;

import com.tgm.graphics.enums.Screen;
import com.tgm.graphics.interfaces.AppInterface;
import com.tgm.graphics.interfaces.ScreenInterface;
import com.tgm.graphics.utils.ScreenUtils;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author christopher
 */
public abstract class AbstractScreen implements ScreenInterface {

    protected int screenWidth, screenHeight;
    protected String screenName = null;
    protected AppInterface appInterface;
    protected boolean close = false;
    Image background;
    protected String backgroundPath = "images/background.png";

    public void initBackground() throws SlickException {
        background = new Image(ScreenUtils.readMediaAsString(backgroundPath));
    }

    public void drawBackground() {
        background.draw(1, 1, appInterface.getWidth(), appInterface.getHeight());
    }

    protected void quit() {
        close = true;
        appInterface.quit();
    }

    @Override
    public void updateNextScene(Screen scene) {
        appInterface.processNextScreen(scene);
    }

    @Override
    public void setAppInterface(AppInterface appInterface1) {
        this.appInterface = appInterface1;
    }

    @Override
    public String getTitle() {
        return screenName;
    }

    @Override
    public boolean closeRequested() {
        return close;
    }
}
