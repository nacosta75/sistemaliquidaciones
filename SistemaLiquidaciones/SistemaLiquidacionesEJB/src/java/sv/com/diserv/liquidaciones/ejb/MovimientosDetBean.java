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
import sv.com.diserv.liquidaciones.dto.OperacionesMovimientoDetDTO;
import sv.com.diserv.liquidaciones.entity.MovimientosDet;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.util.Constants;

/**
 *
 * @author sonia.garcia
 */
@Stateless
public class MovimientosDetBean implements MovimientosDetBeanLocal {

    static final Logger logger = Logger.getLogger(MovimientosDetBean.class.getName());
    @PersistenceContext(unitName = "SistemaLiquidacionesEJBPU")
    private EntityManager em;
    @EJB
    GenericDaoServiceBeanLocal genericDaoBean;

    @Override
    public Integer countAllMovimientosDet(int tipoMovimiento) throws DiservBusinessException {
        logger.log(Level.INFO, "[countAllMovimientosDet]INIT");
        int count = 0;
        Query query;
        try {
            query = em.createQuery("SELECT count(m.idmov) FROM MovimientosDet m ");
            count = ((Long) query.getSingleResult()).intValue();
            logger.log(Level.INFO, "[Total de registros encontrados]" + count);
        } catch (Exception e) {
            logger.log(Level.INFO, "[Excepcion en countAllMovimientosDet]" + e.toString());
            e.printStackTrace();
        }
        return count;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<MovimientosDet> loadAllMovimientosDet(int inicio, int fin) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadAllMovimientosDet] desde:" + inicio + " hasta:" + fin);
        List<MovimientosDet> movimientosList = null;
        Query query;
        try {
            query = em.createNamedQuery("MovimientosDet.findAll");
            query.setFirstResult(inicio);
            query.setMaxResults(fin);
            movimientosList = query.getResultList();
            if (movimientosList != null) {
                logger.log(Level.INFO, "[loadAllMovimientosDet] Se encontraron " + movimientosList.size() + " movimientos");
            }
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllMovimientosDet][NoResultException]No se encontraron movimientos");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron movimientos");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllMovimientosDet][Exception]Se mostro una excepcion al buscar movimientos");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return movimientosList;
    }

    @Override
    public OperacionesMovimientoDetDTO guardarMovimientoDet(MovimientosDet movimiento) throws DiservBusinessException {
        OperacionesMovimientoDetDTO response = new OperacionesMovimientoDetDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo guardar movimiento");
        try {
            movimiento = genericDaoBean.create(movimiento);
            response = new OperacionesMovimientoDetDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Movimiento creada satisfactoriamente");
            response.setMovimiento(movimiento);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }

    @Override
    public OperacionesMovimientoDetDTO actualizarMovimientoDet(MovimientosDet movimiento) throws DiservBusinessException {
        OperacionesMovimientoDetDTO response = new OperacionesMovimientoDetDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo guardar persona");
        try {
            movimiento = genericDaoBean.update(movimiento);
            response = new OperacionesMovimientoDetDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Persona actualizada satisfactoriamente");
            response.setMovimiento(movimiento);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }

    @Override
    public OperacionesMovimientoDetDTO eliminarMovimientoDet(MovimientosDet movimiento) throws DiservBusinessException {
        OperacionesMovimientoDetDTO response = new OperacionesMovimientoDetDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo eliminar persona");
        try {
            Boolean respuesta = genericDaoBean.delete(movimiento);
            response = new OperacionesMovimientoDetDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Persona eliminada satisfactoriamente");
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }

}
