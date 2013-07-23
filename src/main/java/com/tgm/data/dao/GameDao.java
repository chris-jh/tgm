/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.dao;

import com.tgm.data.entity.GameEntity;
import com.tgm.data.entity.PlatformEntity;
import com.tgm.data.interfaces.GameDaoInterface;
import java.util.List;
import javax.persistence.NoResultException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author christopher
 */
@Repository("gameDao")
public class GameDao<E, I> extends AbstractDao<GameEntity, Integer> implements GameDaoInterface<E, I> {

    public GameDao() {
        super(GameEntity.class);
    }

    @Transactional(readOnly = true, noRollbackFor = NoResultException.class)
    @Override
    public GameEntity findGameByName(String name) throws NoResultException {
        return (GameEntity) entityManager.createNamedQuery("GameEntity.findByName").setParameter("name", name).getSingleResult();
    }

    @Transactional(readOnly = true, noRollbackFor = NoResultException.class)
    @Override
    public GameEntity findByNameAndPlatform(String name, PlatformEntity platform) throws NoResultException {
        return (GameEntity) entityManager.createNamedQuery("GameEntity.findByNameAndPlatform").setParameter("name", name).setParameter("platform", platform).getSingleResult();
    }

    @Transactional(readOnly = true, noRollbackFor = NoResultException.class)
    @Override
    public List<GameEntity> findGamesByPlaform(PlatformEntity platform) throws NoResultException {
        return (List<GameEntity>) entityManager.createNamedQuery("GameEntity.findGamesByPlatform").setParameter("platform", platform).getResultList();
    }

    @Transactional(readOnly = true, noRollbackFor = NoResultException.class)
    @Override
    public List<GameEntity> findRecentlyAddedGamesByPlatform(PlatformEntity platform) throws NoResultException {
        return (List<GameEntity>) entityManager.createNamedQuery("GameEntity.findRecentlyAddedGamesByPlatform").setParameter("platform", platform).setMaxResults(10).getResultList();
    }

    @Override
    public List<GameEntity> findRecentlyPlayedGamesByPlatform(PlatformEntity platform) throws NoResultException {
        return (List<GameEntity>) entityManager.createNamedQuery("GameEntity.findRecentlyPlayedGamesByPlatform").setParameter("platform", platform).setMaxResults(10).getResultList();

    }
}
