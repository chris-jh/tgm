/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.gui.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 *
 * @author christopher
 */
public class SolidBackground extends AbstractComponent {

    private Color colour = new Color(0, 0, 0);

    public SolidBackground(String id, Color colour, float x, float y, float width, float height) {
        this.id = id;
        this.colour = colour;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        initialised = true;
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        if (isVisible()) {
            if (colour != null) {
                Color old = g.getColor();
                g.setColor(colour);
                g.fillRect(getX(), getY(), getWidth(), getHeight());
                g.setColor(old);
                old = null;
            }
        }
    }

    /**
     * @param colour the colour to set
     */
    public void setColour(Color colour) {
        this.colour = colour;
    }
}
