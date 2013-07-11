/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.dao;

import com.tgm.data.entity.EmulatorGame;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author christopher
 */
@Repository("emulatorGameDao")
public class EmulatorGameDao extends AbstractDao<EmulatorGame, Integer> {

    protected EmulatorGameDao() {
        super(EmulatorGame.class);
    }

    
    /*@Transactional(readOnly = true)
     public List<Emulator> findEmulatorByName(String name) throws DataAccessException {
     return getEntityManager().createNamedQuery("Emulator.findByName").setParameter("name", name).getResultList();
     }*/
}
