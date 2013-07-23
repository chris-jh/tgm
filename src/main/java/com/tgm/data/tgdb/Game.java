/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.tgdb;

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
public class Game {

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
}
