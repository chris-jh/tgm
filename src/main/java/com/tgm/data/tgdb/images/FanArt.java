/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.tgdb.images;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author christopher
 */
@XmlRootElement(name = "fanart")
@XmlAccessorType(XmlAccessType.FIELD)
public class FanArt {

    /*<fanart>
     <original width="1920" height="1080">fanart/original/3487-1.jpg</original>
     <thumb>fanart/thumb/3487-1.jpg</thumb>
     </fanart>*/
    @XmlElement(name = "original")
    private String original;
    @XmlElement(name = "thumb")
    private String thumb;

    /**
     * @return the original
     */
    public String getOriginal() {
        return original;
    }

    /**
     * @param original the original to set
     */
    public void setOriginal(String original) {
        this.original = original;
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
