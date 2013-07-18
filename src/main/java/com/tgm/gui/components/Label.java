/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.gui.components;

import java.awt.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author christopher
 */
public class Label extends AbstractComponent {

    private String text = "";
    private TrueTypeFont trueTypeFont;
    private Font font = new Font("Arial", Font.PLAIN, 14);
    private boolean update = false;

    public Label(String id, String text, float x, float y) {
        this.id = id;
        this.text = text;
        this.x = x;
        this.y = y;
    }

    public Label(String id, String text, float x, float y, String fontName, int fontStyle, int fontSize) {
        this.id = id;
        this.text = text;
        this.x = x;
        this.y = y;
        font = new Font(fontName, fontStyle, fontSize);
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @param font the font to set
     */
    public void setFont(Font font) {
        this.font = font;
        update = true;
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        trueTypeFont = new TrueTypeFont(font, true);
        initialised = true;
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        if (update) {
            update = false;
            trueTypeFont = new TrueTypeFont(font, true);
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        if (visible) {
            trueTypeFont.drawString(getX(), getY(), getText());
        }
    }
}
