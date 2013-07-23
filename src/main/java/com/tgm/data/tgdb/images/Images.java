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
    @XmlElement(name = "banner")
    private List<Banner> banner;
    @XmlElement(name = "screenshot")
    private List<Screenshot> screenshot;
    @XmlElement(name = "clearlogo")
    private List<ClearLogo> clearLogo;
    

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

    /**
     * @return the banner
     */
    public List<Banner> getBanner() {
        return banner;
    }

    /**
     * @param banner the banner to set
     */
    public void setBanner(List<Banner> banner) {
        this.banner = banner;
    }

    /**
     * @return the screenshot
     */
    public List<Screenshot> getScreenshot() {
        return screenshot;
    }

    /**
     * @param screenshot the screenshot to set
     */
    public void setScreenshot(List<Screenshot> screenshot) {
        this.screenshot = screenshot;
    }

    /**
     * @return the clearLogo
     */
    public List<ClearLogo> getClearLogo() {
        return clearLogo;
    }

    /**
     * @param clearLogo the clearLogo to set
     */
    public void setClearLogo(List<ClearLogo> clearLogo) {
        this.clearLogo = clearLogo;
    }
}
