/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.gui.components;

import java.awt.Font;
import org.newdawn.slick.Color;

/**
 *
 * @author christopher
 */
public class MenuLabel extends Label {

    private int index = 0;
    private String key;

    public MenuLabel(int index, String key, String id, String text, float x, float y) {
        super(id, text, x, y);
        this.index = index;
        this.key = key;
    }

    public MenuLabel(int index, String key, String id, String text, float x, float y, String fontName, int fontStyle, int fontSize, Color colour) {
        super(id, text, x, y, fontName, fontStyle, fontSize, colour);
        this.index = index;
        this.key = key;
    }

    public MenuLabel(int index, String key, String id, String text, float x, float y, String fontName, int fontStyle, int fontSize, Color colour, boolean shadow) {
        super(id, text, x, y, fontName, fontStyle, fontSize, colour, shadow);
        this.index = index;
        this.key = key;
    }

    public MenuLabel(int index, String key, String id, String text, float x, float y, float w, float h, int align, String fontName, int fontStyle, int fontSize, Color colour) {
        super(id, text, x, y, w, h, align, fontName, fontStyle, fontSize, colour);
        this.index = index;
        this.key = key;
    }

    public MenuLabel(int index, String key, String id, String text, float x, float y, float w, float h, int align, String fontName, int fontStyle, int fontSize, Color colour, boolean shadow) {
        super(id, text, x, y, w, h, align, fontName, fontStyle, fontSize, colour, shadow);
        this.index = index;
        this.key = key;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }
}
