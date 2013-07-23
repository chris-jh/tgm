/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scrapers.tgdb;

import com.tgm.scrapers.AbstractHttpScraper;
import com.tgm.scrapers.interfaces.GameDetailsInterface;
import com.tgm.scrapers.interfaces.ResultInterface;
import com.tgm.scrapers.interfaces.ScraperInterface;
import java.util.List;

/**
 *
 * @author christopher
 */
public class TheGamesDBScraper extends AbstractHttpScraper implements ScraperInterface {

    String urlString = "http://thegamesdb.net/api/GetGame.php";

    public TheGamesDBScraper() {
        super(Result.class);
    }

    @Override
    public ResultInterface search(String game, String platform) {
        return performSearch(urlString + "?name=" + game + "&platform=" + platform);
    }

    public static void main(String args[]) {
        TheGamesDBScraper d = new TheGamesDBScraper();
        ResultInterface r = d.search("Comix Zone", "Sega Megadrive");
        List<GameDetailsInterface> list = r.getGames();
        for (GameDetailsInterface gameDetailsInterface : list) {
            System.out.println("GAME: "+gameDetailsInterface.getGameTitle()+" - "+gameDetailsInterface.getOverview());
        }
    }
}
