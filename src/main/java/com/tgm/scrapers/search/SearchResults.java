/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scrapers.search;

import com.tgm.data.tgdb.Game;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mark
 */
@XmlRootElement(name = "Data")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchResults {

    @XmlElement(name = "Game")
    private List<Game> games;

    /**
     * @return the id
     */
    public List<Game> getGames() {
        return games;
    }

    /**
     * @param id the id to set
     */
    public void setGames(List<Game> games) {
        this.games = games;
    }
}
