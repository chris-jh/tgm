/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.interfaces;

import com.tgm.data.entity.GameEntity;
import com.tgm.data.entity.PlatformEntity;
import java.util.List;
import javax.persistence.NoResultException;

/**
 *
 * @author christopher
 */
public interface GameDaoInterface<E, I> extends DaoInterface<GameEntity, Integer> {

    public GameEntity findGameByName(String name) throws NoResultException;

    public GameEntity findByNameAndPlatform(String name, PlatformEntity platform) throws NoResultException;

    public List<GameEntity> findGamesByPlaform(PlatformEntity platform) throws NoResultException;

    public List<GameEntity> findRecentlyAddedGamesByPlatform(PlatformEntity platform) throws NoResultException;

    public List<GameEntity> findRecentlyPlayedGamesByPlatform(PlatformEntity platform) throws NoResultException;
}
