/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.gui.components;

import com.tgm.gui.interfaces.AppInterface;
import com.tgm.gui.interfaces.ComponentInterface;
import com.tgm.gui.interfaces.ScreenInterface;

/**
 *
 * @author christopher
 */
public abstract class AbstractComponent implements ComponentInterface {

    protected String id;
    protected float x = 0;
    protected float y = 0;
    protected float width = 0;
    protected float height = 0;
    protected boolean initialised = false;
    protected ComponentInterface parentComponent;
    protected ScreenInterface parentScreen;
    protected AppInterface appInterface;
    protected boolean visible = true;
    protected float sx = 1.0f;
    protected float sy = 1.0f;
    protected boolean focused = false;
    protected float moveToX = 0.0f;
    protected boolean moveX = false;
    protected float moveXSpeed = 50.0f;
    protected long moveXTime=0;
    protected long moveXTimeSpeed=50;
    
    
    @Override
    public boolean isInitialised() {
        return initialised;
    }

    @Override
    public float getX() {
        return parentComponent != null ? parentComponent.getX() + x : x;
    }

    @Override
    public float getY() {
        return parentComponent != null ? parentComponent.getY() + y : y;
    }

    @Override
    public void setX(float x) {
        this.x = x;
    }

    @Override
    public void setY(float y) {
        this.y = y;
    }

    @Override
    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public float getWidth() {
        return this.width;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public void setWidth(float width) {
        this.width = width;
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }

    /**
     * @return the id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the parentComponent
     */
    @Override
    public ComponentInterface getParentComponent() {
        return parentComponent;
    }

    /**
     * @param parentComponent the parentComponent to set
     */
    @Override
    public void setParentComponent(ComponentInterface parentComponent) {
        this.parentComponent = parentComponent;
    }

    @Override
    public void setAppInterface(AppInterface appInterface) {
        this.appInterface = appInterface;
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public boolean isVisible() {
        return this.visible;
    }

    @Override
    public void setParentScreen(ScreenInterface screenInterface) {
        this.parentScreen = screenInterface;
    }

    /**
     * @return the sx
     */
    public float getSx() {
        return sx;
    }

    /**
     * @param sx the sx to set
     */
    public void setSx(float sx) {
        this.sx = sx;
    }

    /**
     * @return the sy
     */
    public float getSy() {
        return sy;
    }

    /**
     * @param sy the sy to set
     */
    public void setSy(float sy) {
        this.sy = sy;
    }

    /**
     * @return the focused
     */
    public boolean isFocused() {
        return focused;
    }

    /**
     * @param focused the focused to set
     */
    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    /**
     * @param moveToX the moveToX to set
     */
    public void setMoveToX(float moveToX) {
        this.moveToX = moveToX;
        moveX = true;
    }
}
