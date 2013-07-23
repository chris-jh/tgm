/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scrapers.tgdb;

import com.tgm.data.tgdb.Game;
import com.tgm.scrapers.interfaces.ResultInterface;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author christopher
 */
@XmlRootElement(name = "Data")
@XmlAccessorType(XmlAccessType.FIELD)
public class Result implements ResultInterface<Game> {

    @XmlElement(name = "baseImgUrl")
    private String baseImgUrl;
    @XmlElement(name = "Game")
    private List<Game> games;

    @Override
    public List<Game> getGames() {
        return games;
    }

    @Override
    public void setGames(List<Game> games) {
        this.games = games;
    }

    /**
     * @return the baseImgUrl
     */
    @Override
    public String getBaseImgUrl() {
        return baseImgUrl;
    }

    /**
     * @param baseImgUrl the baseImgUrl to set
     */
    public void setBaseImgUrl(String baseImgUrl) {
        this.baseImgUrl = baseImgUrl;
    }
}
