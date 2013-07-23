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

    /**
     * @return the gameTitle
     */
    String getGameTitle();

    /**
     * @return the id
     */
    Long getId();

    /**
     * @return the overview
     */
    String getOverview();

    /**
     * @return the platform
     */
    String getPlatform();

    /**
     * @return the platformId
     */
    String getPlatformId();

    /**
     * @return the releaseDate
     */
    String getReleaseDate();
    
    String getBoxArt();
    
    String getFanArt();
    
    String getScreenShot();
    
    
}
