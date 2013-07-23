/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.scrapers.interfaces;

import java.util.List;

/**
 *
 * @author christopher
 */
public interface ResultInterface<E extends GameDetailsInterface> {

    public String getBaseImgUrl();

    /**
     * @return the id
     */
    public List<E> getGames();

    public E getFirstGame();

    public boolean hasGame();

    /**
     * @param id the id to set
     */
    public void setGames(List<E> games);
}
