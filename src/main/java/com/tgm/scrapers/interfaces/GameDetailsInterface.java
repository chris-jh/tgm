/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scrapers.interfaces;

/**
 *
 * @author christopher
 */
public interface GameDetailsInterface {

    public String getGameTitle();

    public Long getId();

    public String getOverview();

    public String getPlatform();

    public String getPlatformId();

    public String getReleaseDate();

    public String getBoxArt();

    public String getFanArt();

    public String getScreenShot();
    
    public String getBanner();
    
    public String getClearLogo();

    public String getRating();

    public String getPublisher();

    public String getDeveloper();

    public String getPlayers();

    public String getCoOp();
}
