/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.graphics.slick;

/**
 *
 * @author christopher
 */
public class Label  {

    private String text;
    private float x;
    private float y;
    
    public Label(String text, float x, float y){
        this.text = text;
        this.x = x;
        this.y = y;
    }
    
    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
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
     * @return the x
     */
    public float getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public float getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(float y) {
        this.y = y;
    }
    
}
