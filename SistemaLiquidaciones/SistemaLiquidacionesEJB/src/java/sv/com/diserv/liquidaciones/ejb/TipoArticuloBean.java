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
import static sv.com.diserv.liquidaciones.ejb.LineaArticuloBean.logger;
import sv.com.diserv.liquidaciones.entity.Tipoarticulo;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.util.Constants;

/**
 *
 * @author abraham.acosta
 */
@Stateless
public class TipoArticuloBean implements TipoArticuloBeanLocal{
    
    
     static final Logger logger = Logger.getLogger(TipoArticuloBean.class.getName());
    @PersistenceContext(unitName = "SistemaLiquidacionesEJBPU")
    
    private EntityManager em;
        
    @EJB
    GenericDaoServiceBeanLocal genericDaoBean;


    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Tipoarticulo> loadAllTiposArticulos() throws DiservBusinessException {
         logger.log(Level.INFO, "[loadAllTipoarticulo] " );
        List<Tipoarticulo> tipoArticuloList = null;
        Query query;
        try {
            query = em.createNamedQuery("Tipoarticulo.findAll");
       
            tipoArticuloList = query.getResultList();
            if (tipoArticuloList != null) {
                logger.log(Level.INFO, "[loadAllTipoarticulo] Se encontraron " + tipoArticuloList.size() + " tipoArticulos");
            }
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllTipoarticulo][NoResultException]No se encontraron tiposArticulo");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron Tipoarticuloes");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllTipoarticulo][Exception]Se mostro una excepcion al buscar Tipoarticulo");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return tipoArticuloList;
    }

    @Override
    public Tipoarticulo loadTipoArticuloById(int id) throws DiservBusinessException  {
        logger.log(Level.INFO, "[findTipoArticuloById] " );
        Tipoarticulo tipoArticulo = null;
        Query query;
        try {
            query = em.createNamedQuery("Tipoarticulo.findByIdtipoarticulo");
            query.setParameter("idtipoarticulo", id);
            tipoArticulo = (Tipoarticulo) query.getSingleResult();
            if (tipoArticulo!= null) {
                logger.log(Level.INFO, "[findTipoArticuloById] Se encontraron " + tipoArticulo.getDescripcion() + " tipoArticulos");
            }
            
        }
         catch (Exception e) {
            logger.log(Level.INFO, "[Excepcion en findTipoArticuloById]" + e.toString());
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return tipoArticulo;
    }
    
}
