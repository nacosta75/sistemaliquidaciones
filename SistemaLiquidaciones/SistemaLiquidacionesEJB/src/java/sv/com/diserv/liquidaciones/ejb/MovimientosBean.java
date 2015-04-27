package sv.com.diserv.liquidaciones.ejb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import sv.com.diserv.liquidaciones.dto.BusquedaMovimientoDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesMovimientoDTO;
import sv.com.diserv.liquidaciones.entity.Bodegas;
import sv.com.diserv.liquidaciones.entity.Movimientos;
import sv.com.diserv.liquidaciones.entity.MovimientosDet;
import sv.com.diserv.liquidaciones.entity.Personas;
import sv.com.diserv.liquidaciones.entity.Sucursales;
import sv.com.diserv.liquidaciones.entity.TiposMov;
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

    @Override
    public List<Movimientos> buscarMovimientoByCriteria(BusquedaMovimientoDTO input) throws DiservBusinessException {
        logger.info("[buscarMovimientoByCriteria]Parametros=" + input.toString());
        List<Movimientos> response = new ArrayList<>();
        Movimientos movimientos;
        List<String> condiciones = new ArrayList<>();
        if (input.getIdmov() != null) {
            condiciones.add(" idMov=" + input.getIdmov() + " ");
        }
        if (input.getFecha()!= null) {
            condiciones.add(" UPPER(fechamov) = UPPER('" + input.getFecha() + "') ");
        }
        if (input.getIdpersona() != null) {
            condiciones.add(" idpersona=" + input.getIdpersona() + " ");
        }
        if (input.getIdsucursal() != null) {
            condiciones.add(" idsucursal=" + input.getIdsucursal() + " ");
        }
        if (input.getNoDoc() != null) {
            condiciones.add(" idnodoc=" + input.getNoDoc() + " ");
        }
        if (input.getIdtipomov() != 0) {
            condiciones.add(" idtipomov = " + input.getIdtipomov() + " ");
        }

        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM movimientos ");
            if (!condiciones.isEmpty()) {
                sb.append(" WHERE ");
                sb.append(condiciones.get(0));
                for (int i = 1; i < condiciones.size(); i++) {
                    sb.append(" AND ");
                    sb.append(condiciones.get(i));
                }
            }
            sb.append(" ORDER BY fechamov DESC ");
            System.out.println("SQL A EJECUTAR:--> " + sb.toString());
            System.out.println("PARAMETROS RECIBIDOS:-->" + input.toString());
            Query q = em.createNativeQuery(sb.toString());
            List<Object[]> lista = q.getResultList();
            if (lista.size() > 0) {
                for (Object[] item : lista) {
                    movimientos = new Movimientos();
                    movimientos.setIdmov(Integer.parseInt(item[0] != null ? item[0].toString() : "0"));
                    
                    SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
                    String strFecha = item[1] != null ? item[1].toString() : "";
                    Date fecha = null;
                    try {

                        fecha = formatoDelTexto.parse(strFecha);

                    } catch (ParseException ex) {

                        ex.printStackTrace();

                    }
                    movimientos.setFechamov(fecha);
                    movimientos.setNodoc(Integer.parseInt(item[2] != null ? item[2].toString() : "0"));//NODOC
                    movimientos.setIdcaja(Integer.parseInt(item[3] != null ? item[3].toString() : "0"));//idcaja
                    movimientos.setNombre(item[4] != null ? item[4].toString() : "N/D");
                    movimientos.setNoRegistro(item[5] != null ? item[5].toString() : "N/D");//noregistro
                    movimientos.setDireccion(item[6] != null ? item[6].toString() : "N/D");
                    movimientos.setObserva1(item[7] != null ? item[7].toString() : "N/D");
                    movimientos.setEstado(item[8] != null ? item[8].toString() : "N/D");
                    movimientos.setIdtipomov(new TiposMov(Integer.parseInt(item[9] != null ? item[9].toString() : "0")));
                    movimientos.setIdsucursal(new Sucursales(Integer.parseInt(item[10] != null ? item[10].toString() : "0")));
                    movimientos.setIdpersona(new Personas(Integer.parseInt(item[11] != null ? item[11].toString() : "0")));
                    movimientos.setIdbodegaentrada(new Bodegas(Integer.parseInt(item[12] != null ? item[12].toString() : "0")));
                    movimientos.setIdbodegasalida(new Bodegas(Integer.parseInt(item[12] != null ? item[12].toString() : "0")));
                    
                    response.add(movimientos);
                }
            }
        } catch (NoResultException e) {
            logger.info("[consultarBitacoraFinalizadasParametros]No se encontraron registros con criterios recibidos");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron registros con parametros proporcionados");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
