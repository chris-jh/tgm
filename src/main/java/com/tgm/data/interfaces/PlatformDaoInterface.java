/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.interfaces;

import com.tgm.data.entity.PlatformEntity;
import com.tgm.data.interfaces.DaoInterface;
import com.tgm.enums.Platform;
import javax.persistence.NoResultException;

/**
 *
 * @author christopher
 */
public interface PlatformDaoInterface<E,I> extends DaoInterface<PlatformEntity, Integer>{

    PlatformEntity findPlatformByName(Platform name) throws NoResultException;
    
}
