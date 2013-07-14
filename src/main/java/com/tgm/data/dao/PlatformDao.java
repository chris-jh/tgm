/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.dao;

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
public class PlatformDao extends AbstractDao<PlatformEntity, Integer> {
 
    protected PlatformDao() {
        super(PlatformEntity.class);
    }
 
    @Transactional(readOnly = true)
    public PlatformEntity findPlatformByName(Platform name) throws NoResultException {
        return (PlatformEntity)getEntityManager().createNamedQuery("PlatformEntity.findByName").setParameter("name", name).getSingleResult();
    }
}
