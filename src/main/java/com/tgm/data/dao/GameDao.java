/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.dao;

import com.tgm.data.entity.GameEntity;
import com.tgm.data.entity.PlatformEntity;
import com.tgm.enums.Platform;
import java.util.List;
import javax.persistence.NoResultException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author christopher
 */
@Repository("gameDao")
public class GameDao extends AbstractDao<GameEntity, Integer> {

    protected GameDao() {
        super(GameEntity.class);
    }
    
    @Transactional(readOnly = true)
    public GameEntity findGameByName(String name) throws NoResultException {
        return (GameEntity)getEntityManager().createNamedQuery("GameEntity.findByName").setParameter("name", name).getSingleResult();
    }
    
    @Transactional(readOnly = true)
    public GameEntity findByNameAndPlatform(String name, PlatformEntity platform) throws NoResultException {
        return (GameEntity)getEntityManager().createNamedQuery("GameEntity.findByNameAndPlatform").setParameter("name", name).setParameter("platform", platform).getSingleResult();
    }
    
    
    
    @Transactional(readOnly = true)
    public List<GameEntity> findGamesByPlaform(PlatformEntity platform) throws NoResultException {
        return (List<GameEntity>)getEntityManager().createNamedQuery("GameEntity.findGamesByPlatform").setParameter("platform", platform).getResultList();
    }
}
