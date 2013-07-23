/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scrapers.interfaces;

/**
 *
 * @author christopher
 */
public interface ScrapperInterface<E extends ResultInterface> {

    public ResultInterface search(String game, String platform);

    public ResultInterface performSearch(String searchString);
    
}
