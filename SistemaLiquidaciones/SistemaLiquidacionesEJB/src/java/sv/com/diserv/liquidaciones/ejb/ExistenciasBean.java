/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sv.com.diserv.liquidaciones.entity.SaldoExistencia;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.util.Constants;

/**
 *
 * @author abraham.acosta
 */
@Stateless
public class ExistenciasBean implements ExistenciasBeanLocal {

     static final Logger logger = Logger.getLogger(ExistenciasBean.class.getName());
    @PersistenceContext(unitName = "SistemaLiquidacionesEJBPU")
    
    private EntityManager em;
    
    
    @EJB
    GenericDaoServiceBeanLocal genericDaoBean;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public List<SaldoExistencia> loadExistenciaArticulo(int idArticulo,int inicio, int fin) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadAllExistencia] desde:" + inicio + " hasta:" + fin);
        List<SaldoExistencia> existenciaList = null;
        Query query;
        try {
            query = em.createNamedQuery("SaldoExistencia.findByIdArticulo");
            query.setParameter("idarticulo", idArticulo);
            query.setFirstResult(inicio);
            query.setMaxResults(fin);
            existenciaList = query.getResultList();
            if (existenciaList != null) {
                logger.log(Level.INFO, "[loadAllExistencia] Se encontraron " + existenciaList.size() + " existencia");
            }
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllExistencia][NoResultException]No se encontraron existencia");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron Existencias");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllExistencia][Exception]Se mostro una excepcion al buscar Existencia");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return existenciaList;
    }
    
}
