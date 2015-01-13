package sv.com.diserv.liquidaciones.ejb;

import java.math.BigDecimal;
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
import sv.com.diserv.liquidaciones.dto.BusquedaLoteExistenciaDTO;
import sv.com.diserv.liquidaciones.dto.BusquedaPersonaDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesLostesExistenciasDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesMovimientoDTO;
import sv.com.diserv.liquidaciones.entity.Articulos;
import sv.com.diserv.liquidaciones.entity.LotesExistencia;
import sv.com.diserv.liquidaciones.entity.Movimientos;
import sv.com.diserv.liquidaciones.entity.Personas;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.util.Constants;

/**
 *
 * @author sonia.garcia
 */
@Stateless
public class LotesExistenciaBean implements LotesExistenciasBeanLocal {

    static final Logger logger = Logger.getLogger(LotesExistenciaBean.class.getName());
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
            query = em.createQuery("SELECT count(m) FROM Movimientos m where m.idtipomov.idtipomov="+tipoMovimiento);
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
    public List<Personas> loadAllPersonasByLike(String nombreLike) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadAllPersonaByLike] nombreLike:" + nombreLike);
        List<Personas> personaList = null;
        Query query;
        try {
            query = em.createNamedQuery("Personas.findByNombrePersonaLike");
            query.setParameter("nombrePersona", "%" + nombreLike.toUpperCase() + "%");
            query.setMaxResults(Constants.REGISTROS_A_MOSTRAR_LISTA);
            personaList = query.getResultList();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllBodegaByLike][NoResultException]No se encontraron usuarios");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron persona");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllBodegaByLike][Exception]Se mostro una excepcion al buscar persona");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return personaList;
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
             Boolean respuesta =genericDaoBean.delete(movimiento);
            response = new OperacionesMovimientoDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Persona eliminada satisfactoriamente");
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }

    @Override
    public List<LotesExistencia> buscarLoteByCriteria(BusquedaLoteExistenciaDTO re) throws DiservBusinessException {
        logger.info("[buscarLoteByCriteria]Parametros=" + re.toString());
        List<LotesExistencia> response = new ArrayList<>();
        LotesExistencia loteExistencia;
        List<String> condiciones = new ArrayList<>();
        if (re.getIdArticulo()!= null) {
            condiciones.add(" idarticulo=" + re.getIdArticulo()+ " ");
        }
        if (re.getIcc() != null) {
            condiciones.add(" UPPER(icc) LIKE UPPER('%" + re.getIcc()+ "%') ");
        }
        if (re.getImei()!= null) {
            condiciones.add(" UPPER(imei) LIKE UPPER('%" + re.getImei()+ "%') ");
        }
        if (re.getTelefono()!= null) {
            condiciones.add(" UPPER(telefono) LIKE UPPER('%" + re.getTelefono()+ "%') ");
        }
               
        condiciones.add("estado =1");
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM Lotes_Existencia ");
            if (!condiciones.isEmpty()) {
                sb.append(" WHERE ");
                sb.append(condiciones.get(0));
                for (int i = 1; i < condiciones.size(); i++) {
                    sb.append(" AND ");
                    sb.append(condiciones.get(i));
                }
            }
            if(re.getLotes()!=null)
                sb.append(" AND idlote not in ("+re.getLotes()+")");
            sb.append(" ORDER BY idlote DESC ");
            System.out.println("SQL A EJECUTAR:--> " + sb.toString());
            System.out.println("PARAMETROS RECIBIDOS:-->" + re.toString());
            Query q = em.createNativeQuery(sb.toString());
            List<Object[]> lista = q.getResultList();
            if (lista.size() > 0) {
                for (Object[] item : lista) {
                     loteExistencia  = new LotesExistencia();
                     loteExistencia.setIdlote(Integer.parseInt(item[0] != null ? item[0].toString() : "0"));
                     loteExistencia.setIdarticulo(new Articulos(Integer.parseInt(item[1] != null ? item[1].toString() : "0")));
                     loteExistencia.setIdmov(new Movimientos(Integer.parseInt(item[2] != null ? item[2].toString() : "0")));
                     loteExistencia.setIcc(item[3] != null ? item[3].toString() : "N/D");
                     loteExistencia.setImei(item[4] != null ? item[4].toString() : "N/D");
                     loteExistencia.setTelefono(Integer.parseInt(item[5] != null ? item[5].toString() : "0"));
                     
                    
//                    SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
//                    String strFecha = item[21] != null ? item[21].toString() : "";
//                    Date fecha = null;
//                    try {
//
//                        fecha = formatoDelTexto.parse(strFecha);
//
//                    } catch (ParseException ex) {
//
//                        ex.printStackTrace();
//
//                    }
                    
                    response.add(loteExistencia);
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

    @Override
    public Integer maxNumDocByVendedorAndTipoMov(int idVendedor, int tipoMov) throws DiservBusinessException {
        logger.log(Level.INFO, "[maxNumDocByVendedorAndTipoMov]INIT");
        int count = 0;
        Query query;
        try {
            query = em.createQuery("SELECT max(m.nodoc) FROM Movimientos m where m.idtipomov.idtipomov="+tipoMov+" AND m.idpersona.idpersona ="+idVendedor);
            count =  (int) query.getSingleResult();
            logger.log(Level.INFO, "[Total de registros encontrados]" + count);
        } catch (Exception e) {
            logger.log(Level.INFO, "[Excepcion en maxNumDocByVendedorAndTipoMov]" + e.toString());
            e.printStackTrace();
        }
        return count;
    }

}
