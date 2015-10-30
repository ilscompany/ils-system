package com.ils.data.repository;

import com.ils.data.exception.DataException;
import com.ils.data.model.AbstractEntity;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.List;

/**
 * Created by mara on 7/12/15.
 */
public abstract class AbstractRepository<T extends AbstractEntity> {

    private EntityManager entityManager = null;

    public T save(T entity) throws DataException {

        if (entity != null) {
            getEntityManager().getTransaction().begin();
            try {
                if (entity.getId() == null) {
                    getEntityManager().persist(entity);
                    getEntityManager().flush();

                } else {
                    entity = getEntityManager().merge(entity);
                    getEntityManager().flush();
                }
                getEntityManager().getTransaction().commit();

            } catch (Exception e) {
                getEntityManager().getTransaction().rollback();
                throw new DataException("Unable to save entity : " + entity + ", " + e.getMessage(), e);
            }finally{
                getEntityManager().close();
            }
        } else {
            String details = "Unable to save entity : " + entity;
            throw new DataException(details);
        }
        return entity;
    }

    @Transactional
    public void delete(T entity) throws DataException {

        if (entity != null) {
            try {
                getEntityManager().getTransaction().begin();
                getEntityManager().remove(getEntityManager().contains(entity) ? entity : getEntityManager().merge(entity));
                getEntityManager().flush();
                getEntityManager().getTransaction().commit();
            } catch (Exception e) {
                getEntityManager().getTransaction().rollback();
                throw new DataException("Unable to delete entity : " + entity, e);
            } finally{
                getEntityManager().close();
            }
        }
    }

    protected T findSingle(String queryString, Object queryParam, Class<T> clazz)
            throws DataException {
        try {
            TypedQuery<T> query = getEntityManager().createQuery(queryString, clazz);
            query.setParameter(1, queryParam);
            if (query.getResultList() != null && !query.getResultList().isEmpty()) {
                T result = query.getSingleResult();
                return result;
            }
        } catch (Exception e) {
            throw new DataException(e);
        } finally{
            getEntityManager().close();
        }
        return null;
    }

    protected List<T> findList(String queryString, Object queryParam, Class<T> clazz) throws DataException {
        try {
            TypedQuery<T> query = getEntityManager().createQuery(queryString, clazz);
            query.setParameter(1, queryParam);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataException(e);
        } finally{
            getEntityManager().close();
        }
    }

    protected List<T> findAll(String queryString, Class<T> clazz) throws DataException {
        try {
            TypedQuery<T> query = getEntityManager().createQuery(queryString, clazz);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataException(e);
        } finally{
            getEntityManager().close();
        }
    }

    public EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = getTransactionManager().getEntityManagerFactory().createEntityManager();
        } else if (!entityManager.isOpen()) {
            entityManager = getTransactionManager().getEntityManagerFactory().createEntityManager();
        }
        return entityManager;
    }

    public abstract JpaTransactionManager getTransactionManager();
}
