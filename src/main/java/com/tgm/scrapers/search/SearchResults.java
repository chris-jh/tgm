/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scrapers.search;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mark
 */
@XmlRootElement(name = "Data")
public class SearchResults {

    @XmlElement(name = "Game")
    private List games;

    /**
     * @return the id
     */
    public List getGames() {
        return games;
    }

    /**
     * @param id the id to set
     */
    public void setGames(List games) {
        this.games = games;
    }
}
