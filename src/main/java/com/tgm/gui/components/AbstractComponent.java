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
    public void setVisable(boolean visible) {
        this.visible = visible;
    }

    @Override
    public boolean isVisiable() {
        return this.visible;
    }

    @Override
    public void setParentScreen(ScreenInterface screenInterface) {
        this.parentScreen = screenInterface;
    }
}
