/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.gui.components;

import com.tgm.gui.interfaces.ComponentInterface;
import com.tgm.gui.utils.ScreenUtils;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author christopher
 */
public class Panel extends AbstractComponent {

    private List<ComponentInterface> components = new ArrayList<ComponentInterface>();
    private Image backgroundImage;
    private String background = null;

    public Panel(){
        
    }
    
    public Panel(String id, float x, float y, float width, float height) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public Panel(String id, float x, float y, float width, float height, String backgroundPath) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.background = backgroundPath;
    }
    
    public void setBackground(String background) {
        this.background = background;
    }

    public void add(ComponentInterface componentInterface) {
        if (!components.contains(componentInterface)) {
            componentInterface.setParentComponent(this);
            components.add(componentInterface);
        }
    }

    public void add(int index, ComponentInterface componentInterface) {
        if (!components.contains(componentInterface)) {
            componentInterface.setParentComponent(this);
            components.add(index, componentInterface);
        }
    }

    public void remove(ComponentInterface componentInterface) {
        if (components.contains(componentInterface)) {
            components.remove(componentInterface);
            componentInterface.setParentComponent(null);
        }
    }

    public ComponentInterface getComponent(String id) {
        for (ComponentInterface componentInterface : components) {
            if (id.equals(componentInterface.getId())) {
                return componentInterface;
            }
        }
        return null;
    }

    public ComponentInterface getComponentAt(int index) {
        return components.get(index);
    }

    public int getNumberOfComponent() {
        return components.size();
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        if (background != null) {
            backgroundImage = new Image(ScreenUtils.readMediaAsString(background));
        }

        for (ComponentInterface componentInterface : components) {
            if (!componentInterface.isInitialised()) {
                componentInterface.setParentScreen(parentScreen);
                componentInterface.setParentComponent(this);
                componentInterface.setAppInterface(appInterface);
                componentInterface.init(gc);
            }
        }
        initialised = true;
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        if (visible) {
            for (ComponentInterface componentInterface : components) {
                if (!componentInterface.isInitialised()) {
                    componentInterface.init(gc);
                }
                componentInterface.update(gc, i);
            }
        }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        if (visible) {
            if (background != null) {
                backgroundImage.draw(getX(), getY(), getWidth(), getHeight());
            }

            for (ComponentInterface componentInterface : components) {
                if (componentInterface.isInitialised()) {
                    componentInterface.render(gc, g);
                }
            }
        }
    }

    /**
     * @return the visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * @param visible the visible to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
