/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.tgdb.images;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author christopher
 */
@XmlRootElement(name = "boxart")
@XmlAccessorType(XmlAccessType.FIELD)
public class BoxArt {

    @XmlValue
    private String value;
    @XmlAttribute(name = "side")
    private String side;
    @XmlAttribute(name = "width")
    private String width;
    @XmlAttribute(name = "height")
    private String height;
    @XmlAttribute(name = "thumb")
    private String thumb;
    
    /*side  = "front" width  = "1525" height  = "2160" thumb  = "boxart/thumb/original/front/2-1.jpg" > boxart / original / front / 2 - 1.
    jpg*/

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the side
     */
    public String getSide() {
        return side;
    }

    /**
     * @param side the side to set
     */
    public void setSide(String side) {
        this.side = side;
    }

    /**
     * @return the width
     */
    public String getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public String getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
     * @return the thumb
     */
    public String getThumb() {
        return thumb;
    }

    /**
     * @param thumb the thumb to set
     */
    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
    
    
}
