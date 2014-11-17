/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.Local;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

/**
 *
 * @author edwin.alvarenga
 */
@Local
public interface GenericDaoServiceBeanLocal {

    /**
     *
     * @param <T>
     * @param t
     * @return
     * @throws sv.com.diserv.liquidaciones.exception.DiservBusinessException
     */
    public <T> T create(T t) throws DiservBusinessException;

    /**
     * *
     *
     * @param lista
     */
    public void saveOrUpdate(List lista);

    /**
     *
     * @param <T>
     * @param pEntityClass
     * @param id
     * @return
     */
    public <T> T find(Class<T> pEntityClass, Object id);

    /**
     * *
     *
     * @param <T>
     * @param entity
     * @return
     * @throws sv.com.diserv.liquidaciones.exception.DiservBusinessException
     */
    public <T> Boolean delete(T entity) throws DiservBusinessException;

    /**
     * *
     *
     * @param <T>
     * @param t
     * @return
     * @throws sv.com.diserv.liquidaciones.exception.DiservBusinessException
     */
    public <T> T update(T t) throws DiservBusinessException;

    /**
     * *
     *
     * @param <T>
     * @param queryName
     * @return
     */
    public <T> List<T> findByNamedQuery(String queryName);

    /**
     * *
     *
     * @param <T>
     * @param sql
     * @param pEntityClass
     * @return
     */
    public <T> List<T> findByNativeQuery(String sql, Class<T> pEntityClass);

    /**
     * **
     *
     * @param <T>
     * @param pEntityClass
     * @return
     */
    public <T> List<T> findAll(Class<T> pEntityClass);

    /**
     * **
     *
     * @param <T>
     * @param queryName
     * @param resultLimit
     * @return
     */
    public <T> List<T> findByNamedQuery(String queryName, int resultLimit);

}
