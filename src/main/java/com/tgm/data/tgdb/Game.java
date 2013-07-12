/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.tgdb;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author christopher
 */
@XmlRootElement(name = "Game")
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
    private List gameImages;
    
    

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
}
