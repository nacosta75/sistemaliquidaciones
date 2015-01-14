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
import sv.com.diserv.liquidaciones.dto.OperacionesRelAsignacionDTO;
import sv.com.diserv.liquidaciones.entity.LotesExistencia;
import sv.com.diserv.liquidaciones.entity.Movimientos;
import sv.com.diserv.liquidaciones.entity.RelacionAsignaciones;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.util.Constants;

/**
 *
 * @author sonia.garcia
 */
@Stateless
public class RelacionAsignacionBean implements RelacionAsignacionBeanLocal {

    static final Logger logger = Logger.getLogger(RelacionAsignacionBean.class.getName());
    @PersistenceContext(unitName = "SistemaLiquidacionesEJBPU")
    private EntityManager em;
    @EJB
    GenericDaoServiceBeanLocal genericDaoBean;

    @Override
    public Integer countAllRelASig() throws DiservBusinessException {
        logger.log(Level.INFO, "[countAllRelASig]INIT");
        int count = 0;
        Query query;
        try {
            query = em.createQuery("SELECT count(r.idrelacion) FROM RelacionAsignaciones r");
            count = ((Long) query.getSingleResult()).intValue();
            logger.log(Level.INFO, "[Total de registros encontrados]" + count);
        } catch (Exception e) {
            logger.log(Level.INFO, "[Excepcion en countAllRelASig]" + e.toString());
            e.printStackTrace();
        }
        return count;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<RelacionAsignaciones> loadAllRelAsig(int inicio, int fin) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadAllRelAsig] desde:" + inicio + " hasta:" + fin);
        List<RelacionAsignaciones> relacionesList = null;
        Query query;
        try {
            query = em.createNamedQuery("RelacionAsignaciones.findAll");
            query.setFirstResult(inicio);
            query.setMaxResults(fin);
            relacionesList = query.getResultList();
            if (relacionesList != null) {
                logger.log(Level.INFO, "[loadAllRelAsig] Se encontraron " + relacionesList.size() + " asignaciones");
            }
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllRelAsig][NoResultException]No se encontraron asignaciones");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron asignaciones");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllRelAsig][Exception]Se mostro una excepcion al buscar asignaciones");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return relacionesList;
    }

    @Override
    public OperacionesRelAsignacionDTO guardarRelacionAsignacion(RelacionAsignaciones relacionAsignacion) throws DiservBusinessException {
        OperacionesRelAsignacionDTO response = new OperacionesRelAsignacionDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo guardar asignacion");
        try {
            relacionAsignacion = genericDaoBean.create(relacionAsignacion);
            response = new OperacionesRelAsignacionDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Asignacion creada satisfactoriamente");
            response.setRelacion(relacionAsignacion);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }

    @Override
    public OperacionesRelAsignacionDTO eliminarRelacionAsignacion(RelacionAsignaciones relacionAsignacion) throws DiservBusinessException {
        OperacionesRelAsignacionDTO response = new OperacionesRelAsignacionDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo eliminar asignacion");
        try {
            Boolean respuesta = genericDaoBean.delete(relacionAsignacion);
            response = new OperacionesRelAsignacionDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Asignacion eliminada satisfactoriamente");
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }

    @Override
    public OperacionesRelAsignacionDTO guardarRelacionAsignacion(Movimientos movimiento, List<LotesExistencia> lotes) throws DiservBusinessException {
        OperacionesRelAsignacionDTO response = new OperacionesRelAsignacionDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo guardar asignacion");
        try {
             for(LotesExistencia lote:lotes){
                 RelacionAsignaciones relacion = new RelacionAsignaciones();
                 relacion.setIdlote(lote);
                 relacion.setIdmov(movimiento);
                 relacion = genericDaoBean.create(relacion);
                 response = new OperacionesRelAsignacionDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Asignacion creada satisfactoriamente");
                 response.setRelacion(relacion);
             }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }
}
