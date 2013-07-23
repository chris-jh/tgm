/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.tgdb;

import com.tgm.data.tgdb.images.BoxArt;
import com.tgm.data.tgdb.images.FanArt;
import com.tgm.scrapers.interfaces.GameDetailsInterface;
import com.tgm.data.tgdb.images.Images;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author christopher
 */
@XmlRootElement(name = "Game")
@XmlAccessorType(XmlAccessType.FIELD)
public class Game implements GameDetailsInterface {

    @XmlElement(name = "id")
    private Long id;
    @XmlElement(name = "GameTitle")
    private String gameTitle;
    @XmlElement(name = "ReleaseDate")
    private String releaseDate;
    @XmlElement(name = "PlatformId")
    private String platformId;
    @XmlElement(name = "Platform")
    private String platform;
    @XmlElement(name = "Overview")
    private String overview;
    @XmlElement(name = "Images")
    private List<Images> gameImages;

    /**
     * @return the id
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the gameTitle
     */
    @Override
    public String getGameTitle() {
        return gameTitle;
    }

    /**
     * @param gameTitle the gameTitle to set
     */
    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    /**
     * @return the releaseDate
     */
    @Override
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * @param releaseDate the releaseDate to set
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * @return the platform
     */
    @Override
    public String getPlatform() {
        return platform;
    }

    /**
     * @param platform the platform to set
     */
    public void setPlatform(String platform) {
        this.platform = platform;
    }

    /**
     * @return the platformId
     */
    @Override
    public String getPlatformId() {
        return platformId;
    }

    /**
     * @param platformId the platformId to set
     */
    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    /**
     * @return the overview
     */
    @Override
    public String getOverview() {
        return overview;
    }

    /**
     * @param overview the overview to set
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * @return the gameImages
     */
    public List<Images> getGameImages() {
        return gameImages;
    }

    /**
     * @param gameImages the gameImages to set
     */
    public void setGameImages(List<Images> gameImages) {
        this.gameImages = gameImages;
    }

    @Override
    public String getBoxArt() {
        if (!getGameImages().isEmpty()) {
            for (Images images : gameImages) {
                for (BoxArt boxArt : images.getBoxArt()) {
                    if (boxArt.getSide().equals("front")) {
                        return boxArt.getValue();
                    }
                }
            }
        }
        return "blankbox.png";
    }

    @Override
    public String getFanArt() {
        if (!getGameImages().isEmpty()) {
            for (Images images : gameImages) {
                for (FanArt fanArt : images.getFanArt()) {
                    return fanArt.getOriginal();
                }
            }
        }
        return "blankart.png";
    }

    @Override
    public String getScreenShot() {
        return "blankscreenshot.png";

    }
}
