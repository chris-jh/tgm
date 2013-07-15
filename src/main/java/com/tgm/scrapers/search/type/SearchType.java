/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scrapers.search.type;

import com.tgm.scrapers.search.SearchResults;

/**
 *
 * @author mark
 */
public interface SearchType {
    public SearchResults search(String game, String platform);
    public SearchResults search(String game, long platformId);
    public SearchResults search(String game);
}
