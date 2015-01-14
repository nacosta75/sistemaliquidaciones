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
import sv.com.diserv.liquidaciones.entity.UnidadesMed;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.util.Constants;

/**
 *
 * @author abraham.acosta
 */
@Stateless
public class UmedidaArticuloBean implements UmedidaArticuloBeanLocal{

     static final Logger logger = Logger.getLogger(UmedidaArticuloBean.class.getName());
    @PersistenceContext(unitName = "SistemaLiquidacionesEJBPU")
    
    private EntityManager em;
        
    @EJB
    GenericDaoServiceBeanLocal genericDaoBean;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<UnidadesMed> loadAllUmedidaArticulos() throws DiservBusinessException {
         logger.log(Level.INFO, "[loadAllUnidadesMed] " );
        List<UnidadesMed> tipoUmedidaList = null;
        Query query;
        try {
            query = em.createNamedQuery("UnidadesMed.findAll");
       
            tipoUmedidaList = query.getResultList();
            if (tipoUmedidaList != null) {
                logger.log(Level.INFO, "[loadAllUnidadesMed] Se encontraron " + tipoUmedidaList.size() + " tipoUmedidas");
            }
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllUnidadesMed][NoResultException]No se encontraron tiposUmedida");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron UnidadesMedes");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllUnidadesMed][Exception]Se mostro una excepcion al buscar UnidadesMed");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return tipoUmedidaList;
    }

    @Override
    public UnidadesMed loadUmedidaById(int id) throws DiservBusinessException {
        logger.log(Level.INFO, "[finduMedidaById] Idlinea:" + id);
        UnidadesMed medida = null;
        Query query;
        try {
            query = em.createNamedQuery("UnidadesMed.findByIdumedida");
            query.setParameter("idumedida", id);
            medida = (UnidadesMed) query.getSingleResult();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[finduMedidaById][NoResultException]No se encontraron umedida");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[finduMedidaById][Exception]Se mostro una excepcion al buscar umedida");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return medida;
    }
    
}
