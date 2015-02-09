/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sv.com.diserv.liquidaciones.entity.EncListaPrecio;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.util.Constants;

/**
 *
 * @author abraham.acosta
 */
@Stateless
public class EncListaPrecioBean implements EncListaPrecioBeanLocal{

      static final Logger logger = Logger.getLogger(EncListaPrecioBean.class.getName());
    @PersistenceContext(unitName = "SistemaLiquidacionesEJBPU")
    
    private EntityManager em;
        
    @EJB
    GenericDaoServiceBeanLocal genericDaoBean;

    
    @Override
    public List<EncListaPrecio> loadAllCanales() throws DiservBusinessException {
        
        logger.log(Level.INFO, "[loadAllCanales] " );
        List<EncListaPrecio> canalesLista = null;
        Query query;
        try {
            query = em.createNamedQuery("EncListaPrecio.findAll");
       
            canalesLista = query.getResultList();
            if (canalesLista != null) {
                logger.log(Level.INFO, "[EncListaPrecio.findAll] Se encontraron " + canalesLista.size() + " canales");
            }
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllCanales][NoResultException] No se encontraron EncListaPrecio");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron UnidadesMedes");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllUnidadesMed][Exception]Se mostro una excepcion al buscar UnidadesMed");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return canalesLista ;
    }

    @Override
    public EncListaPrecio loadCanalById(int id) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadCanalById] findByIdlista:" + id);
        EncListaPrecio lista = null;
        Query query;
        try {
            query = em.createNamedQuery("EncListaPrecio.findByIdlista");
            query.setParameter("idlista", id);
            lista = (EncListaPrecio) query.getSingleResult();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadCanalById][NoResultException]No se encontraron lista de precio");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadCanalById][Exception]Se mostro una excepcion al buscar lista de precio");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return lista;
    }
    
}
