/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tgm.data.dao;

import com.tgm.data.entity.EntityInterface;
import com.tgm.data.interfaces.DaoInterface;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 *
 * @author christopher
 */
public abstract class AbstractDao<E extends EntityInterface, I extends Serializable> implements DaoInterface<E, I> {

    private Class<E> entityClass;
    private EntityInterface entityInterface;
    protected EntityManager entityManager;
    @Autowired
    protected ApplicationContext applicationContext;
    private String findAllQuery;

    public AbstractDao(Class<E> entityClass) {
        Assert.isAssignable(EntityInterface.class, entityClass);
        this.entityClass = entityClass;
    }

    /*public void afterPropertiesSet() throws Exception {
     buildEntity();
     }*/
    public void buildEntity() {
        if (entityInterface == null) {
            entityInterface = (EntityInterface) createInstance();
            findAllQuery = "select o from " + entityInterface.getTable() + " o";
        }
    }

    @Override
    public E createInstance() {
        return (E) applicationContext.getAutowireCapableBeanFactory().createBean(entityClass);
    }

    @Transactional(readOnly = true, noRollbackFor = NoResultException.class)
    @Override
    public E findById(I id) throws DataAccessException, NoResultException {
        return (E) entityManager.find(entityClass, id);
    }

    @Transactional(readOnly = true, noRollbackFor = NoResultException.class)
    @Override
    public List<E> findAll() throws DataAccessException, NoResultException {
        buildEntity();
        return entityManager.createQuery(findAllQuery, entityClass).getResultList();
    }

    @Transactional(readOnly = true, noRollbackFor = NoResultException.class)
    @Override
    public List<E> findAllAndFetch() throws DataAccessException, NoResultException {
        buildEntity();
        return fetch(entityManager.createQuery(findAllQuery, entityClass).getResultList());
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public E saveOrUpdate(E e) throws DataAccessException {
        return entityManager.merge(e);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void delete(E e) throws DataAccessException {
        entityManager.remove(e);
    }

    @Transactional(readOnly = true)
    @Override
    public List<E> fetch(List<E> entities) throws DataAccessException {
        for (E entity : entities) {
            ((EntityInterface) entity).fetch();
        }
        return entities;
    }

    
    
    /**
     * @param applicationContext the applicationContext to set
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * @param entityManager the entityManager to set
     */
    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
