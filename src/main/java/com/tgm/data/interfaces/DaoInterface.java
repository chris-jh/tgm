/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.interfaces;

import com.tgm.data.entity.EntityInterface;
import java.io.Serializable;
import java.util.List;
import javax.persistence.NoResultException;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author christopher
 */
public interface DaoInterface<E extends EntityInterface, I extends Serializable> {

    public E createInstance();
    
    public E findById(I id) throws DataAccessException, NoResultException;

    public List<E> findAll() throws DataAccessException, NoResultException;
    
    public List<E> findAllAndFetch() throws DataAccessException, NoResultException;

    public E saveOrUpdate(E e) throws DataAccessException;

    public void delete(E e) throws DataAccessException;
    
    public List<E> fetch(List<E> entities) throws DataAccessException;
}
