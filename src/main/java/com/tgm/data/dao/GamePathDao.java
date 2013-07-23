/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.dao;

import com.tgm.data.entity.GameEntity;
import com.tgm.data.entity.GamePathEntity;
import com.tgm.data.interfaces.GamePathDaoInterface;
import java.util.List;
import javax.persistence.NoResultException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author christopher
 */
@Repository("gamePathDao")
public class GamePathDao<E, I> extends AbstractDao<GamePathEntity, Integer> implements GamePathDaoInterface<E, I> {

    public GamePathDao() {
        super(GamePathEntity.class);
    }

    @Transactional(readOnly = true, noRollbackFor = NoResultException.class)
    @Override
    public List<GamePathEntity> findByGame(GameEntity game) throws NoResultException {
        return (List<GamePathEntity>) entityManager.createNamedQuery("GamePathEntity.findByName").setParameter("game", game).getResultList();
    }

    @Transactional(readOnly = true, noRollbackFor = NoResultException.class)
    @Override
    public GamePathEntity findByFileName(String name) throws NoResultException {
        return (GamePathEntity) entityManager.createNamedQuery("GamePathEntity.findByFileName").setParameter("name", name).getSingleResult();
    }

}
