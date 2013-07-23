/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scrapers.tgdb;

import com.tgm.scrapers.AbstractHttpScrapper;
import com.tgm.scrapers.interfaces.GameDetailsInterface;
import com.tgm.scrapers.interfaces.ResultInterface;
import com.tgm.scrapers.interfaces.ScrapperInterface;
import java.util.List;

/**
 *
 * @author christopher
 */
public class TheGamesDBScrapper extends AbstractHttpScrapper implements ScrapperInterface {

    String urlString = "http://thegamesdb.net/api/GetGame.php";

    public TheGamesDBScrapper() {
        super(Result.class);
    }

    @Override
    public ResultInterface search(String game, String platform) {
        return performSearch(urlString + "?name=" + game + "&platform=" + platform);
    }

    public static void main(String args[]) {
        TheGamesDBScrapper d = new TheGamesDBScrapper();
        ResultInterface r = d.search("Comix Zone", "Sega Megadrive");
        List<GameDetailsInterface> list = r.getGames();
        for (GameDetailsInterface gameDetailsInterface : list) {
            System.out.println("GAME: "+gameDetailsInterface.getGameTitle()+" - "+gameDetailsInterface.getOverview());
        }
    }
}
