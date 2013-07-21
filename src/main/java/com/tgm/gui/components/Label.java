/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.gui.components;

import java.awt.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author christopher
 */
public class Label extends AbstractComponent {

    public final static int LEFT = 0;
    public final static int RIGHT = 1;
    public final static int CENTER = 2;
    private String text = "";
    private TrueTypeFont trueTypeFont;
    //private TrueTypeFont trueTypeFontShadow;
    private Font font = new Font("Arial", Font.PLAIN, 14);
    private boolean update = false;
    private Color colour = Color.black;
    private int align = 0;
    private float offset = 0;
    private boolean shadow = false;
    

    public Label(String id, String text, float x, float y) {
        this.id = id;
        this.text = text;
        this.x = x;
        this.y = y;
    }

    public Label(String id, String text, float x, float y, String fontName, int fontStyle, int fontSize, Color colour) {
        this.id = id;
        this.text = text;
        this.x = x;
        this.y = y;
        this.colour = colour;
        font = new Font(fontName, fontStyle, fontSize);
    }

    public Label(String id, String text, float x, float y, String fontName, int fontStyle, int fontSize, Color colour, boolean shadow) {
        this.id = id;
        this.text = text;
        this.x = x;
        this.y = y;
        this.colour = colour;
        this.shadow = shadow;
        font = new Font(fontName, fontStyle, fontSize);
    }

    public Label(String id, String text, float x, float y, float w, float h, int align, String fontName, int fontStyle, int fontSize, Color colour) {
        this.id = id;
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.colour = colour;
        this.align = align;
        font = new Font(fontName, fontStyle, fontSize);
    }

    public Label(String id, String text, float x, float y, float w, float h, int align, String fontName, int fontStyle, int fontSize, Color colour, boolean shadow) {
        this.id = id;
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.colour = colour;
        this.align = align;
        this.shadow = shadow;
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
        if (!this.text.equals(text)) {
            this.text = text;
        }
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
    boolean fp = true;

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        if (visible) {
            if (update) {
                update = false;
                trueTypeFont = new TrueTypeFont(font, true);
            }

            if (fp) {
                if (align == CENTER) {
                    offset = (getWidth() - getTextWidth()) / 2f;
                }
                if (align == RIGHT) {
                    offset = (getWidth() - getTextWidth());
                }
                fp = false;
            }
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        if (visible) {
            if (shadow) {
                trueTypeFont.drawString(getX() + offset + ((float) font.getSize() / 7f), getY() + ((float) font.getSize() / 7f), getText(), Color.black);
            }
            trueTypeFont.drawString(getX() + offset, getY(), getText(), colour);
        }
    }

    public float getTextWidth() {
        if (initialised) {
            return trueTypeFont.getWidth(getText());
        }
        return 0;
    }

    public float getTextHeight() {
        if (initialised) {
            return trueTypeFont.getHeight(getText());
        }
        return 0;
    }

    /**
     * @param color the color to set
     */
    public void setColour(Color colour) {
        this.colour = colour;
    }

    /**
     * @return the colour
     */
    public Color getColour() {
        return colour;
    }
}
