/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.tgdb.images;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author christopher
 */
@XmlRootElement(name = "Images")
@XmlAccessorType(XmlAccessType.FIELD)
public class Images {

    @XmlElement(name = "boxart")
    private List<BoxArt> boxArt;
    @XmlElement(name = "fanart")
    private List<FanArt> fanArt;

    /**
     * @return the boxArt
     */
    public List<BoxArt> getBoxArt() {
        return boxArt;
    }

    /**
     * @param boxArt the boxArt to set
     */
    public void setBoxArt(List<BoxArt> boxArt) {
        this.boxArt = boxArt;
    }

    /**
     * @return the fanArt
     */
    public List<FanArt> getFanArt() {
        return fanArt;
    }

    /**
     * @param fanArt the fanArt to set
     */
    public void setFanArt(List<FanArt> fanArt) {
        this.fanArt = fanArt;
    }

    
}
