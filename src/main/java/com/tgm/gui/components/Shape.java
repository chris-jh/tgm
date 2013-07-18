/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.gui.components;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.RoundedRectangle;

/**
 *
 * @author christopher
 */
public class Shape extends AbstractComponent {

    private Rectangle rectangle;
    private RoundedRectangle roundRectangle;
    private Circle circle;
    private Type type;
    private org.newdawn.slick.geom.Shape shape;
    private ShapeFill fill;
    private float cornerRadius;
    private Color fillColor;

    public Shape(String id, Type type, float x, float y, float width, float height) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        initShape();
    }

    public Shape(String id, Type type, float x, float y, float width, float height, float cornerRadius) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.cornerRadius = cornerRadius;
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        initShape();
        initialised = true;
    }

    @Override
    public void update(GameContainer gc, int i) throws SlickException {
        this.shape.setLocation(x, y);
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        if (fill != null){
            g.draw(shape, fill);
        } else {
            g.draw(shape);
        }
    }

    private void initShape() {
        switch (this.type) {
            case RECTANGLE: {
                this.rectangle = new Rectangle(x, y, width, height);
                this.shape = this.rectangle;
                break;
            }
            case ROUND_RECTANGLE: {
                this.roundRectangle = new RoundedRectangle(x, y, width, height, cornerRadius);
                this.shape = this.roundRectangle;
                break;
            }
            case CIRCULE: {
                this.circle = new Circle(x, y, width);
                this.shape = this.circle;
                break;
            }
        }
        
        if (fillColor != null) {
            fill = new GradientFill(0, 0, fillColor, width, height, fillColor);
        }

    }

    public void setFill(Color color) {
        this.fillColor = color;
    }

    /**
     * @param type the type to set
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * @param shape the shape to set
     */
    public void setShape(org.newdawn.slick.geom.Shape shape) {
        this.shape = shape;
    }

    /**
     * @param cornerRadius the cornerRadius to set
     */
    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public enum Type {
        RECTANGLE,
        ROUND_RECTANGLE,
        CIRCULE,
    }
}
