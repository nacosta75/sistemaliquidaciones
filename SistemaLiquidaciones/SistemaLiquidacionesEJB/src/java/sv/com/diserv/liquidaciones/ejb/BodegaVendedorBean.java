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
import sv.com.diserv.liquidaciones.dto.OperacionesBodegaVendedorDTO;
import sv.com.diserv.liquidaciones.entity.BodegaVendedor;
import sv.com.diserv.liquidaciones.entity.Bodegas;
import sv.com.diserv.liquidaciones.entity.Personas;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.util.Constants;

/**
 *
 * @author sonia.garcia
 */
@Stateless
public class BodegaVendedorBean implements BodegaVendedorBeanLocal {

    static final Logger logger = Logger.getLogger(BodegaVendedorBean.class.getName());
    @PersistenceContext(unitName = "SistemaLiquidacionesEJBPU")
    private EntityManager em;
    @EJB
    GenericDaoServiceBeanLocal genericDaoBean;

    /**
     * metodo para contar registros ingresados bodega
     *
     * @return Integer con la suma de los registros encontados
     * @throws DiservBusinessException
     */
    @Override
    public Integer countAllBodegasAsignables() throws DiservBusinessException {
        logger.log(Level.INFO, "[countAllBodega]INIT");
        int count = 0;
        Query query;
        try {
            query = em.createQuery("SELECT count(s) FROM Bodegas s ");
            count = ((Long) query.getSingleResult()).intValue();
            logger.log(Level.INFO, "[Total de registros encontrados]" + count);
        } catch (Exception e) {
            logger.log(Level.INFO, "[Excepcion en countAllBodega]" + e.toString());
            e.printStackTrace();
        }
        return count;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Bodegas> loadAllBodegasAsignables() throws DiservBusinessException {
        logger.log(Level.INFO, "[loadAllBodegasAsignables]");
        List<Bodegas> bodegaList = null;
        Query query;
        try {
            query = em.createNamedQuery("BodegaVendedor.findAllBodegasAsignables");
            bodegaList = query.getResultList();
            if (bodegaList != null) {
                logger.log(Level.INFO, "[loadAllBodegasAsignables] Se encontraron " + bodegaList.size() + " bodegas");
            }
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllBodegasAsignables][NoResultException]No se encontraron bodegas asignables");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron bodega");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllBodegasAsignables][Exception]Se mostro una excepcion al buscar bodega");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return bodegaList;
    }

    @Override
    public OperacionesBodegaVendedorDTO asignarBodega(Integer idBodega, Integer idVendedor) throws DiservBusinessException {
        OperacionesBodegaVendedorDTO response = new OperacionesBodegaVendedorDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo guardar bodega");
        try {
            BodegaVendedor bodegaven = new BodegaVendedor();
            bodegaven.setIdbodega(new Bodegas(idBodega));
            bodegaven.setIdpersona(new Personas(idVendedor));
            bodegaven = genericDaoBean.create(bodegaven);
            response = new OperacionesBodegaVendedorDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Asigancion de bodega satisfactoria");
//            response.setBodega(bodega);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }
    
    @Override
    public OperacionesBodegaVendedorDTO desasignarBodega(BodegaVendedor bodegaVendedor) throws DiservBusinessException {
        OperacionesBodegaVendedorDTO response = new OperacionesBodegaVendedorDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo eliminar la asignacion");
        try {
             Boolean respuesta =genericDaoBean.delete(bodegaVendedor);
            response = new OperacionesBodegaVendedorDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Asignacion eliminada satisfactoriamente");
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }

    
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Bodegas findBodegaByID(Integer idBodega) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadBodegaByID] Idbodega:" + idBodega);
        Bodegas bodega = null;
        Query query;
        try {
            query = em.createNamedQuery("Bodegas.findByIdbodega");
            query.setParameter("idbodega", idBodega);
            bodega = (Bodegas) query.getSingleResult();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadBodegaByID][NoResultException]No se encontraron usuarios");
//            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron bodega");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadBodegaByID][Exception]Se mostro una excepcion al buscar bodega");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return bodega;
    }
    
    @Override
    public Bodegas findBodegaAsignada(Integer idVendedor) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadBodegaAsignada] Idvendedor:" + idVendedor);
        Bodegas bodega = null;
        Query query;
        try {
            query = em.createNamedQuery("BodegaVendedor.findByIdVendedor");
            query.setParameter("idVendedor", idVendedor);
            bodega = (Bodegas) query.getSingleResult();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadBodegaAsignada][NoResultException]No se encontraron bodegas");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadBodegaAsignada][Exception]Se mostro una excepcion al buscar bodega");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return bodega;
    }
    
    @Override
     public BodegaVendedor findBodegaVendedorByIdVendedorBodega(Integer idVendedor, Integer idBodega) throws DiservBusinessException{
         logger.log(Level.INFO, "[loadBodegaVendedorByIdVendedorBodega] Idvendedor:" + idVendedor);
        BodegaVendedor bodegaVendedor = null;
        Query query;
        try {
            query = em.createNamedQuery("BodegaVendedor.findByIdVendedorBodega");
            query.setParameter("idVendedor", idVendedor);
            query.setParameter("idBodega", idBodega);
            bodegaVendedor = (BodegaVendedor) query.getSingleResult();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadBodegaVendedorByIdVendedorBodega][NoResultException]No se encontraron bodegas");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadBodegaVendedorByIdVendedorBodega][Exception]Se mostro una excepcion al buscar bodega");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return bodegaVendedor;
     }
}
