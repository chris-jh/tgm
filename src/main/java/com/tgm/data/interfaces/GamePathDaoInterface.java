/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.interfaces;

import com.tgm.data.entity.GameEntity;
import com.tgm.data.entity.GamePathEntity;
import java.util.List;
import javax.persistence.NoResultException;

/**
 *
 * @author christopher
 */
public interface GamePathDaoInterface<E, I> extends DaoInterface<GamePathEntity, Integer> {

    public List<GamePathEntity> findByGame(GameEntity game) throws NoResultException;

    public GamePathEntity findByFileName(String fileName) throws NoResultException;
}
