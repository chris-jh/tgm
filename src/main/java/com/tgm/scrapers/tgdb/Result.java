/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scrapers.tgdb;

import com.tgm.data.tgdb.Game;
import com.tgm.scrapers.interfaces.GameDetailsInterface;
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
public class Result implements ResultInterface<Game>{
    
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


    

    
    
}
