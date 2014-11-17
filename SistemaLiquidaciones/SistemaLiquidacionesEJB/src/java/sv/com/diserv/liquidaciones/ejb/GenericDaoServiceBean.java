/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sv.com.diserv.liquidaciones.exception.DiservBusinessRolledbackException;
import sv.com.diserv.liquidaciones.util.Constants;

/**
 *
 * @author edwin.alvarenga
 */
@Stateless
public class GenericDaoServiceBean implements GenericDaoServiceBeanLocal {

    Logger logger = Logger.getLogger(GenericDaoServiceBean.class.getName());
    @PersistenceContext(unitName = "SistemaLiquidacionesEJBPU")
    private EntityManager em;

    /**
     * *
     *
     * @param <T>
     * @param t
     * @return
     */
    @Override
    public <T> T create(T t) throws DiservBusinessRolledbackException {
        logger.info("[GenericDaoServiceBean][create]INFO");
        logger.info("[create]Type entity=" + t.getClass().getCanonicalName());
        try {
            em.persist(t);
            em.flush();
            em.refresh(t);
        } catch (Exception e) {
            logger.error("[Exception]" + e);
            throw new DiservBusinessRolledbackException(Constants.CODE_OPERATION_FALLIDA, "No se pudo actualizar crear registro");
        }
        return t;
    }

    /**
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T find(Class<T> pEntityClass, Object id) {
        logger.log(Level.INFO, "[GenericDaoServiceBean]find");
        try {
            return this.em.find(pEntityClass, id);
        } catch (NoResultException e) {
            logger.error("[NoResultException]" + e);

            e.printStackTrace();
        } catch (Exception e) {
            logger.error("[Exception]" + e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     */
    @Override
    public <T> Boolean delete(T entity) throws DiservBusinessRolledbackException {
        logger.log(Level.INFO, "[GenericDaoServiceBean]delete");
        try {
            em.remove(em.merge(entity));
            return true;
        } catch (PersistenceException p) {
            logger.error("[PersistenceException]" + p);
            throw new DiservBusinessRolledbackException(Constants.CODE_OPERATION_FALLIDA, "PersistenceException, no se pudo eliminar registro");
        } catch (Exception e) {
            logger.error("[Exception]" + e);
            e.printStackTrace();
            throw new DiservBusinessRolledbackException(Constants.CODE_OPERATION_FALLIDA, "Exception, no se pudo eliminar registro");

        }
    }

    /**
     *
     * @param t
     * @return
     */
    @Override
    public <T> T update(T t) throws DiservBusinessRolledbackException {
        logger.log(Level.INFO, "[GenericDaoServiceBean]update");
        try {
            em.merge(t);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[Exception]" + e);
            throw new DiservBusinessRolledbackException(Constants.CODE_OPERATION_FALLIDA, "No se pudo actualizar registro");

        }
        // return null;
    }

    /**
     *
     * @param queryName
     * @return
     */
    @Override
    public <T> List<T> findByNamedQuery(String queryName) {
        logger.log(Level.INFO, "[GenericDaoServiceBean]findByNamedQuery");
        List<T> response = null;
        try {
            response = this.em.createNamedQuery(queryName).getResultList();
        } catch (NoResultException e) {
            logger.error("[NoResultException]" + e);
            logger.log(Level.INFO, "[GenericDaoServiceBean]findByNamedQuery=No se encontraron registros");
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("[Exception]" + e);
            e.printStackTrace();
        }
        return response;
    }

    /**
     *
     * @param queryName
     * @param resultLimit
     * @return
     */
    @Override
    public <T> List<T> findByNamedQuery(String queryName, int resultLimit) {
        logger.log(Level.INFO, "[GenericDaoServiceBean]findByNamedQuery");
        List<T> response = null;
        try {
            return this.em.createNamedQuery(queryName).setMaxResults(resultLimit).getResultList();
        } catch (NoResultException e) {
            logger.error("[NoResultException]" + e);
            logger.log(Level.INFO, "[GenericDaoServiceBean]findByNamedQuery=No se encontraron registros");
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("[Exception]" + e);
            e.printStackTrace();
        }
        return response;
    }

    /**
     *
     * @param t
     * @return
     */
    @Override
    public <T> List<T> findAll(Class<T> t) {
        logger.log(Level.INFO, "[GenericDaoServiceBean]findAll ");
        List<T> response = null;
        Query query = null;
        try {
            query = em.createQuery("from " + t.getName());
            //     javax.persistence.criteria.CriteriaQuery cq = em.get.getCriteriaBuilder().createQuery();
            // cq.select(cq.from(t));
            response = query.getResultList();
            // response = em.createQuery(cq).getResultList();
        } catch (NoResultException e) {
            logger.error("[NoResultException]" + e);
            logger.log(Level.INFO, "[GenericDaoServiceBean]findAll=No se encontraron registros");
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("[Exception]" + e);
            e.printStackTrace();
        }
        return response;
    }

    /**
     *
     * @param lista
     */
    @Override
    public void saveOrUpdate(List lista) {
        logger.log(Level.INFO, "[GenericDaoServiceBean]create");
        try {
            if (lista != null) {
                for (Object e : lista) {
                    em.persist(e);
                    em.flush();
                    em.refresh(e);
                }
            }
        } catch (Exception e) {
            logger.error("[Exception]" + e);
            e.printStackTrace();
        }
    }

    /**
     *
     * @param <T>
     * @param sql
     * @param pEntityClass
     * @return
     */
    @Override
    public <T> List<T> findByNativeQuery(String sql, Class<T> pEntityClass) {
        List<T> response = null;
        try {
            response = this.em.createNativeQuery(sql, pEntityClass).getResultList();
        } catch (NoResultException e) {
            logger.error("[NoResultException]" + e);
            logger.log(Level.INFO, "[GenericDaoServiceBean]findByNamedQuery=No se encontraron registros");
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("[Exception]" + e);
            e.printStackTrace();
        }
        return response;
    }
}
