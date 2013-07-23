/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scrapers.interfaces;

/**
 *
 * @author christopher
 */
public interface ScraperInterface<E extends ResultInterface> {

    public ResultInterface quickSearch(String game, String platform);

    public ResultInterface fullSearch(String game, String platform);

    public ResultInterface searchById(Long id);

    public ResultInterface search(String game, String platform);

    public ResultInterface performSearch(String searchString);
}
