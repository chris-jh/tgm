/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.gui.components;

import com.tgm.gui.utils.ScreenUtils;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author christopher
 */
public class Image extends AbstractComponent {

    String path;
    org.newdawn.slick.Image background;

    public Image(String id, String path, float x, float y, float width, float height) {
        this.id = id;
        this.path = path;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        initialised = true;
        background = new org.newdawn.slick.Image(ScreenUtils.readMediaAsString(path));
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        background.draw(getX(), getY(), getWidth(), getHeight());
    }
}
