/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scrapers.tgdb;

import com.tgm.data.tgdb.Game;
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

    String gameListUrlString = "http://thegamesdb.net/api/GetGamesList.php";
    String gameUrlString = "http://thegamesdb.net/api/GetGame.php";

    
    public TheGamesDBScraper() {
        super(Result.class);
    }
    
    @Override
    public ResultInterface searchById(Long id) {
        return performSearch(gameUrlString + "?id=" + id);
    }

    @Override
    public ResultInterface quickSearch(String game, String platform) {
        return performSearch(gameListUrlString + "?name=" + game + "&platform=" + platform);
    }
    
     @Override
    public ResultInterface fullSearch(String game, String platform) {
        return performSearch(gameUrlString + "?name=" + game + "&platform=" + platform);
    }

    @Override
    public ResultInterface search(String game, String platform) {
        return searchById(((Game)quickSearch(game, platform).getGames().get(0)).getId());
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
