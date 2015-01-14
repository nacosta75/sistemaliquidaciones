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
import sv.com.diserv.liquidaciones.dto.OperacionesMovimientoDTO;
import sv.com.diserv.liquidaciones.entity.Movimientos;
import sv.com.diserv.liquidaciones.entity.MovimientosDet;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.util.Constants;

/**
 *
 * @author sonia.garcia
 */
@Stateless
public class MovimientosBean implements MovimientosBeanLocal {

    static final Logger logger = Logger.getLogger(MovimientosBean.class.getName());
    @PersistenceContext(unitName = "SistemaLiquidacionesEJBPU")
    private EntityManager em;
    @EJB
    GenericDaoServiceBeanLocal genericDaoBean;

    @Override
    public Integer countAllMovimientos(int tipoMovimiento) throws DiservBusinessException {
        logger.log(Level.INFO, "[countAllMovimientos]INIT");
        int count = 0;
        Query query;
        try {
            query = em.createQuery("SELECT count(m.idmov) FROM Movimientos m where m.idtipomov.idtipomov=" + tipoMovimiento);
            count = ((Long) query.getSingleResult()).intValue();
            logger.log(Level.INFO, "[Total de registros encontrados]" + count);
        } catch (Exception e) {
            logger.log(Level.INFO, "[Excepcion en countAllMovimientos]" + e.toString());
            e.printStackTrace();
        }
        return count;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Movimientos> loadAllMovimientos(int inicio, int fin, int tipo) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadAllMovimientos] desde:" + inicio + " hasta:" + fin);
        List<Movimientos> movimientosList = null;
        Query query;
        try {
            query = em.createNamedQuery("Movimientos.findAllByTipo");
            query.setParameter("idtipomov", tipo);
            query.setFirstResult(inicio);
            query.setMaxResults(fin);
            movimientosList = query.getResultList();
            if (movimientosList != null) {
                logger.log(Level.INFO, "[loadAllAsignaciones] Se encontraron " + movimientosList.size() + " movimientos");
            }
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllAsignaciones][NoResultException]No se encontraron movimientos");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron movimientos");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllAsignaciones][Exception]Se mostro una excepcion al buscar movimientos");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return movimientosList;
    }

    @Override
    public OperacionesMovimientoDTO guardarMovimiento(Movimientos movimiento) throws DiservBusinessException {
        OperacionesMovimientoDTO response = new OperacionesMovimientoDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo guardar persona");
        try {
            movimiento = genericDaoBean.create(movimiento);
            response = new OperacionesMovimientoDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Persona creada satisfactoriamente");
            response.setMovimiento(movimiento);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }

    @Override
    public OperacionesMovimientoDTO actualizarMovimiento(Movimientos movimiento) throws DiservBusinessException {
        OperacionesMovimientoDTO response = new OperacionesMovimientoDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo guardar persona");
        try {
            movimiento = genericDaoBean.update(movimiento);
            response = new OperacionesMovimientoDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Persona actualizada satisfactoriamente");
            response.setMovimiento(movimiento);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }

    @Override
    public OperacionesMovimientoDTO eliminarMovimiento(Movimientos movimiento) throws DiservBusinessException {
        OperacionesMovimientoDTO response = new OperacionesMovimientoDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo eliminar persona");
        try {
            Boolean respuesta = genericDaoBean.delete(movimiento);
            response = new OperacionesMovimientoDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Persona eliminada satisfactoriamente");
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }

    @Override
    public Integer maxNumDocByVendedorAndTipoMov(int idVendedor, int tipoMov) throws DiservBusinessException {
        logger.log(Level.INFO, "[maxNumDocByVendedorAndTipoMov]INIT");
        int count = 0;
        Query query;
        try {
            query = em.createQuery("SELECT max(m.nodoc) FROM Movimientos m where m.idtipomov.idtipomov=" + tipoMov + " AND m.idpersona.idpersona =" + idVendedor);
            count = (int) query.getSingleResult();
            logger.log(Level.INFO, "[Total de registros encontrados]" + count);
        } catch (Exception e) {
            logger.log(Level.INFO, "[Excepcion en maxNumDocByVendedorAndTipoMov]" + e.toString());
            e.printStackTrace();
        }
        return count;
    }

    /**
     * *
     *
     * @param codigoMovimiento
     * @return
     * @throws DiservBusinessException
     */
    @Override
    public List<MovimientosDet> loadDetalleMovimientoByIdMovimento(Integer codigoMovimiento) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadDetalleMovimientoByIdMovimento] codigoMovimiento:" + codigoMovimiento);
        List<MovimientosDet> personaList = null;
        Query query;
        try {
            query = em.createNamedQuery("MovimientosDet.findByIdMovimiento");
            query.setParameter("idMovimiento", codigoMovimiento);
            query.setMaxResults(Constants.REGISTROS_A_MOSTRAR_LISTA);
            personaList = query.getResultList();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadDetalleMovimientoByIdMovimento][NoResultException]No se encontraron movimientos");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron persona");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadDetalleMovimientoByIdMovimento][Exception]Se mostro una excepcion al buscar movimientos");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return personaList;
    }
    
    @Override
    public Movimientos findMovimientoById( Integer idMovimiento) throws DiservBusinessException{
      logger.log(Level.INFO, "[findMovimientoById] Idmovimiento:" + idMovimiento);
        Movimientos movimiento = null;
        Query query;
        try {
            query = em.createNamedQuery("Movimientos.findByIdmov");
            query.setParameter("idmov", idMovimiento);
            movimiento = (Movimientos) query.getSingleResult();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[findMovimientoById][NoResultException]No se encontraron movimientos");
//            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron articulo");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[findMovimientoById][Exception]Se mostro una excepcion al buscar movimiento");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return movimiento;   
    }
}
