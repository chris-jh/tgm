/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.dao;

import com.tgm.data.interfaces.PlatformDaoInterface;
import com.tgm.data.entity.PlatformEntity;
import com.tgm.enums.Platform;
import javax.persistence.NoResultException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author christopher
 */
@Repository("platformDao")
public class PlatformDao<E,I> extends AbstractDao<PlatformEntity, Integer> implements PlatformDaoInterface<E, I> {
 
    public PlatformDao() {
        super(PlatformEntity.class);
    }
 
    @Transactional(readOnly = true, noRollbackFor = NoResultException.class)
    @Override
    public PlatformEntity findPlatformByName(Platform name) throws NoResultException {
        return (PlatformEntity)entityManager.createNamedQuery("PlatformEntity.findByName").setParameter("name", name).getSingleResult();
    }
}
