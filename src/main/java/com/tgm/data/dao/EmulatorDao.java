/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.dao;

import com.tgm.data.entity.Emulator;
import java.util.List;
import javax.persistence.NoResultException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author christopher
 */
@Repository("emulatorDao")
public class EmulatorDao extends AbstractDao<Emulator, Integer> {
 
    protected EmulatorDao() {
        super(Emulator.class);
    }
 
    @Transactional(readOnly = true)
    public Emulator findEmulatorByName(String name) throws NoResultException {
        return (Emulator)getEntityManager().createNamedQuery("Emulator.findByName").setParameter("name", name).getSingleResult();
    }
}
