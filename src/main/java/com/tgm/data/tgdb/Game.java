/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.tgdb;

import com.tgm.data.tgdb.images.Banner;
import com.tgm.data.tgdb.images.BoxArt;
import com.tgm.data.tgdb.images.ClearLogo;
import com.tgm.data.tgdb.images.FanArt;
import com.tgm.scrapers.interfaces.GameDetailsInterface;
import com.tgm.data.tgdb.images.Images;
import com.tgm.data.tgdb.images.Screenshot;
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
    private String releaseDate = "";
    @XmlElement(name = "PlatformId")
    private String platformId;
    @XmlElement(name = "Platform")
    private String platform = "";
    @XmlElement(name = "Overview")
    private String overview = "";
    @XmlElement(name = "Rating")
    private String rating = "0";
    @XmlElement(name = "Publisher")
    private String publisher  = "";
    @XmlElement(name = "Developer")
    private String developer = "";
    @XmlElement(name = "Players")
    private String players = "1";
    @XmlElement(name = "Co-op")
    private String coOp = "No";
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
        try {
            if (!getGameImages().isEmpty()) {
                for (Images images : gameImages) {
                    for (BoxArt image : images.getBoxArt()) {
                        if (image.getSide().equals("front")) {
                            return image.getValue();
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public String getFanArt() {
        try {
            if (!getGameImages().isEmpty()) {
                for (Images images : gameImages) {
                    for (FanArt image : images.getFanArt()) {
                        return image.getOriginal();
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public String getScreenShot() {
        try {
            if (!getGameImages().isEmpty()) {
                for (Images images : gameImages) {
                    for (Screenshot image : images.getScreenshot()) {
                        return image.getOriginal();
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public String getBanner() {
        try {
            if (!getGameImages().isEmpty()) {
                for (Images images : gameImages) {
                    for (Banner image : images.getBanner()) {
                        return image.getValue();
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public String getClearLogo() {
        try {
            if (!getGameImages().isEmpty()) {
                for (Images images : gameImages) {
                    for (ClearLogo image : images.getClearLogo()) {
                        return image.getValue();
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * @return the rating
     */
    @Override
    public String getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(String rating) {
        this.rating = rating;
    }

    /**
     * @return the publisher
     */
    @Override
    public String getPublisher() {
        return publisher;
    }

    /**
     * @param publisher the publisher to set
     */
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    /**
     * @return the developer
     */
    @Override
    public String getDeveloper() {
        return developer;
    }

    /**
     * @param developer the developer to set
     */
    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    /**
     * @return the players
     */
    @Override
    public String getPlayers() {
        return players;
    }

    /**
     * @param players the players to set
     */
    public void setPlayers(String players) {
        this.players = players;
    }

    /**
     * @return the coOp
     */
    @Override
    public String getCoOp() {
        return coOp;
    }

    /**
     * @param coOp the coOp to set
     */
    public void setCoOp(String coOp) {
        this.coOp = coOp;
    }
}
