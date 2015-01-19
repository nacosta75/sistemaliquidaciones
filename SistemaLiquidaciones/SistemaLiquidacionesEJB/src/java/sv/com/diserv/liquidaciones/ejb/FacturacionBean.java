/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE MINISTERIO DE HACIENDA EL SALVADDOR Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 *  @author edwin.alvarenga@mh.gob.sv
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
//import sv.com.ats.business.dto.BusquedaFacturaDTO;
//import sv.com.ats.business.dto.CalculoSumaFacturaResponseDTO;
//import sv.com.ats.business.dto.ConsultaFacturaDTO;
//import sv.com.ats.business.dto.DetalleFacturaDTO;
//import sv.com.ats.business.dto.GenericResponse;
//import sv.com.ats.business.dto.OperacionServicioClienteDTO;
//import sv.com.ats.business.dto.OperacionesDocumentoClienteDTO;
//import sv.com.ats.business.dto.OperacionesGastoClienteDTO;
//import sv.com.ats.business.entity.Configuracion;
//import sv.com.ats.business.entity.DetalleDocumentoCliente;
//import sv.com.ats.business.entity.Documentocliente;
//import sv.com.ats.business.entity.EstadoFactura;
//import sv.com.ats.business.entity.EstadoOrdentrabajo;
//import sv.com.ats.business.entity.GastosporCliente;
//import sv.com.ats.business.entity.Ordentrabajo;
//import sv.com.ats.business.entity.ServiciosPrestados;
//import sv.com.ats.business.exception.AtsBusinessBusinessRolledbackException;
//import sv.com.ats.business.exception.AtsBusinessException;
//import sv.com.ats.business.util.Assemble;
//import sv.com.ats.business.util.Constants;
//import sv.com.ats.business.util.UtilFormat;

/**
 *
 * @author edwin.alvarenga
 */
@Stateless
public class FacturacionBean implements FacturacionBeanLocal {

// 
//    static final Logger logger = Logger.getLogger(FacturacionBean.class.getName());
//    @EJB
//    GenericDaoServiceBeanLocal genericDaoBean;
//    @PersistenceContext(unitName = "SistemawebatsEJBPU")
//    private EntityManager em;
    @PostConstruct
    public void loadDefault() {
//        logger.info("[loadDefault]Load constants");
//        Constants a = new Constants();
    }
//
//    /**
//     *
//     * @return @throws AtsBusinessException
//     */
//    @Override
//    public List<GastosporCliente> loadGastosPorCliente() throws AtsBusinessException {
//        logger.log(Level.INFO, "[loadGastosPorCliente] ");
//        List<GastosporCliente> gastoList = null;
//        Query query;
//        try {
//            query = em.createNamedQuery("GastosporCliente.findAll");
//            gastoList = query.getResultList();
//        } catch (NoResultException ex) {
//            logger.log(Level.INFO, "[loadGastosPorCliente][NoResultException]No se encontraron gastos");
//            throw new AtsBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron gastos");
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.log(Level.INFO, "[loadGastosPorCliente][Exception]Se mostro una excepcion al buscar gastos");
//            throw new AtsBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
//        }
//        return gastoList;
//    }
//
//    /**
//     * *
//     *
//     * @param gasto
//     * @return
//     * @throws AtsBusinessException
//     */
//    @Override
//    public OperacionesGastoClienteDTO actualizarGastoCliente(GastosporCliente gasto) throws AtsBusinessException {
//        OperacionesGastoClienteDTO response = new OperacionesGastoClienteDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo guardar actualizarGastoCliente");
//        try {
//            gasto = em.merge(gasto);
//            response = new OperacionesGastoClienteDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Gasto actualizado satisfactoriamente");
//            response.setGastoCliente(gasto);
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setMensajeRespuesta(e.toString());
//        }
//        return response;
//    }
//
//    /**
//     *
//     * @param gasto
//     * @return
//     * @throws AtsBusinessException
//     */
//    @Override
//    public OperacionesGastoClienteDTO guardarGastoCliente(GastosporCliente gasto) throws AtsBusinessException {
//        OperacionesGastoClienteDTO response = new OperacionesGastoClienteDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo guardar gasto cliente");
//        try {
//            em.persist(gasto);
//            response = new OperacionesGastoClienteDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Gasto creado satisfactoriamente");
//            response.setGastoCliente(gasto);
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setMensajeRespuesta(e.toString());
//        }
//        return response;
//    }
//
//    /**
//     *
//     * @return @throws AtsBusinessException
//     */
//    @Override
//    public List<ServiciosPrestados> loadAllServiciosCliente() throws AtsBusinessException {
//        logger.log(Level.INFO, "[loadAllServiciosCliente] ");
//        List<ServiciosPrestados> servicioList = null;
//        Query query;
//        try {
//            query = em.createNamedQuery("ServiciosPrestados.findAll");
//            servicioList = query.getResultList();
//        } catch (NoResultException ex) {
//            logger.log(Level.INFO, "[loadAllServiciosCliente][NoResultException]No se encontraron servicios");
//            throw new AtsBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron gastos");
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.log(Level.INFO, "[loadAllServiciosCliente][Exception]Se mostro una excepcion al buscar servicios");
//            throw new AtsBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
//        }
//        return servicioList;
//    }
//
//    /**
//     *
//     * @param numeroSerie
//     * @param idFactura
//     * @return
//     * @throws AtsBusinessException
//     */
//    @Override
//    public Documentocliente findDocumentoClienteByIdDFacturaAndSerie(String numeroSerie, Integer idFactura) throws AtsBusinessException {
//        logger.log(Level.INFO, "[findDocumentoClienteByIdDFacturaAndSerie]Numero Serie: " + numeroSerie + ",idFactura:" + idFactura);
//        List<Documentocliente> documento = null;
//        Query query;
//        try {
//            query = em.createNamedQuery("Documentocliente.findByNumFacturaAndSerie");
//            query.setParameter("idfactura", idFactura);
//            query.setParameter("serieFactura", numeroSerie);
//            documento = query.getResultList();
//            return documento.get(0);
//        } catch (NoResultException ex) {
//            logger.log(Level.INFO, "[findDocumentoClienteByIdDFacturaAndSerie][NoResultException]No se encontraron servicios");
//            throw new AtsBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron gastos");
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.log(Level.INFO, "[findDocumentoClienteByIdDFacturaAndSerie][Exception]Se mostro una excepcion al buscar servicios");
//            throw new AtsBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
//        }
//
//    }
//
//    /**
//     * *
//     *
//     * @param gasto
//     * @return
//     * @throws AtsBusinessException
//     */
//    @Override
//    public OperacionServicioClienteDTO actualizarServicioCliente(ServiciosPrestados servicio) throws AtsBusinessException {
//        OperacionServicioClienteDTO response = new OperacionServicioClienteDTO(Constants.CODE_OPERATION_FALLIDA, "No se pudo guardar Actualizar Servicio Cliente");
//        try {
//            servicio = em.merge(servicio);
//            response = new OperacionServicioClienteDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Gasto actualizado satisfactoriamente");
//            response.setServicio(servicio);
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setMensajeRespuesta(e.toString());
//        }
//        return response;
//    }
//
//    /**
//     *
//     * @param gasto
//     * @return
//     * @throws AtsBusinessException
//     */
//    @Override
//    public OperacionServicioClienteDTO guardarServicioCliente(ServiciosPrestados servicio) throws AtsBusinessException {
//        OperacionServicioClienteDTO response = new OperacionServicioClienteDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo guardar Servicio Cliente");
//        try {
//            servicio.setIdservicio(UtilFormat.getCadenaAlfanumAleatoria(3));
//            em.persist(servicio);
//            response = new OperacionServicioClienteDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Gasto creado satisfactoriamente");
//            response.setServicio(servicio);
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setMensajeRespuesta(e.toString());
//        }
//        return response;
//    }
//
//    /**
//     *
//     * @param re
//     * @return
//     * @throws AtsBusinessException
//     */
//    @Override
//    public Integer countFindDocumentosClienteByRequest(BusquedaFacturaDTO re) throws AtsBusinessException {
//        logger.info("[countFindDocumentosClienteByRequest]Parametros=" + re.toString());
//        List<String> condiciones = new ArrayList<>();
//        Query query;
//        Integer count = null;
//        try {
//            if (re.getIdCliente() != null) {
//                condiciones.add(" d.idCliente.idCliente='" + re.getIdCliente() + "'");
//            }
//            if (re.getIdFactura() != null) {
//                condiciones.add(" d.idfactura='" + re.getIdFactura() + "'");
//            }
//            if (re.getFechaInicio() != null && re.getFechaFinal() != null) {
//                condiciones.add(" d.fechaCreacion BETWEEN :startDate AND :endDate");
//            } else if (re.getFechaFinal() != null && re.getFechaInicio() == null) {
//                condiciones.add(" d.fechaCreacion < :endDate");
//            } else if (re.getFechaInicio() != null && re.getFechaFinal() == null) {
//                condiciones.add(" d.fechaCreacion > :startDate");
//            }
//            StringBuilder sb = new StringBuilder();
//            sb.append(" SELECT count(d.idcorrelativo) FROM Documentocliente d ");
//            if (!condiciones.isEmpty()) {
//                sb.append(" WHERE ");
//                sb.append(condiciones.get(0));
//                for (int i = 1; i < condiciones.size(); i++) {
//                    sb.append(" AND ");
//                    sb.append(condiciones.get(i));
//                }
//            }
//            logger.info("[countFindDocumentosClienteByRequest]SQL A EJECUTAR:--> " + sb.toString());
//            query = em.createQuery(sb.toString());
//            if (re.getFechaInicio() != null && re.getFechaFinal() != null) {
//                query.setParameter("startDate", re.getFechaInicio(), TemporalType.TIMESTAMP);
//                query.setParameter("endDate", re.getFechaFinal(), TemporalType.TIMESTAMP);
//            } else if (re.getFechaInicio() != null && re.getFechaFinal() == null) {
//                query.setParameter("startDate", re.getFechaInicio(), TemporalType.TIMESTAMP);
//            } else if (re.getFechaFinal() != null && re.getFechaInicio() == null) {
//                query.setParameter("endDate", re.getFechaFinal(), TemporalType.TIMESTAMP);
//            }
//            count = ((Long) query.getSingleResult()).intValue();
//        } catch (NoResultException e) {
//            logger.info("[countFindDocumentosClienteByRequest]No se encontraron registros con criterios recibidos");
//            throw new AtsBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron registros con parametros proporcionados");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return count;
//    }
//
//    /**
//     *
//     * @param re
//     * @param paginaInicio
//     * @param paginaFinal
//     * @return
//     * @throws AtsBusinessException
//     */
//    @Override
//    public List<Documentocliente> findDocumentoClienteByRequest(BusquedaFacturaDTO re, Integer paginaInicio, Integer paginaFinal) throws AtsBusinessException {
//        logger.info("[findDocumentoClienteByRequest]Parametros=" + re.toString());
//        List<Documentocliente> response = null;
//        List<String> condiciones = new ArrayList<>();
//        Query query;
//        try {
//            if (re.getIdCliente() != null) {
//                condiciones.add(" d.idCliente.idCliente='" + re.getIdCliente() + "'");
//            }
//            if (re.getIdFactura() != null) {
//                condiciones.add(" d.idfactura='" + re.getIdFactura() + "'");
//            }
//            if (re.getNumeroSerie() != null) {
//                condiciones.add(" d.serieFactura='" + re.getNumeroSerie() + "'");
//            }
//            if (re.getFechaInicio() != null && re.getFechaFinal() != null) {
//                condiciones.add(" d.fechaCreacion BETWEEN :startDate AND :endDate");
//            } else if (re.getFechaFinal() != null && re.getFechaInicio() == null) {
//                condiciones.add(" d.fechaCreacion < :endDate");
//            } else if (re.getFechaInicio() != null && re.getFechaFinal() == null) {
//                condiciones.add(" d.fechaCreacion > :startDate");
//            }
//            StringBuilder sb = new StringBuilder();
//            sb.append(" SELECT d FROM Documentocliente d ");
//            if (!condiciones.isEmpty()) {
//                sb.append(" WHERE ");
//                sb.append(condiciones.get(0));
//                for (int i = 1; i < condiciones.size(); i++) {
//                    sb.append(" AND ");
//                    sb.append(condiciones.get(i));
//                }
//            }
//            sb.append(" order by d.idcorrelativo desc ");
//            logger.info("[findDocumentoClienteByRequest]SQL A EJECUTAR:--> " + sb.toString());
//            query = em.createQuery(sb.toString());
//            if (re.getFechaInicio() != null && re.getFechaFinal() != null) {
//                query.setParameter("startDate", re.getFechaInicio(), TemporalType.TIMESTAMP);
//                query.setParameter("endDate", re.getFechaFinal(), TemporalType.TIMESTAMP);
//            } else if (re.getFechaInicio() != null && re.getFechaFinal() == null) {
////                System.out.println("FECHA  MAYOR QUE=" + UtilFormat.convertirFechaYYYYMMMDDD(re.getFechaInicio()));
//                query.setParameter("startDate", re.getFechaInicio(), TemporalType.TIMESTAMP);
//            } else if (re.getFechaFinal() != null && re.getFechaInicio() == null) {
//                query.setParameter("endDate", re.getFechaFinal(), TemporalType.TIMESTAMP);
//            }
//            if (paginaFinal != null && paginaInicio != null) {
//                query.setFirstResult(paginaInicio);
//                query.setMaxResults(paginaFinal);
//            }
//            response = query.getResultList();
//        } catch (NoResultException e) {
//            logger.info("[findDocumentoClienteByRequest]No se encontraron registros con criterios recibidos");
//            throw new AtsBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron registros con parametros proporcionados");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    /**
//     * *
//     *
//     * @param idEstado
//     * @param cantidadDias
//     * @return
//     * @throws AtsBusinessException
//     */
//    @Override
//    public Integer countFindDocumentoClienteByEstadoAndDias(Integer idEstado, Integer cantidadDias) throws AtsBusinessException {
//        logger.info("[countFindDocumentoClienteByEstadoAndDias]esado=" + idEstado + ",dias" + cantidadDias);
//        Integer count = 0;
//        Query query;
//        StringBuilder sql = new StringBuilder();
//        try {
//            sql.append("SELECT count(d.idcorrelativo) FROM Documentocliente d WHERE d.fechaCreacion < :fechaCreacion and d.idEstado.idestado =:idEstado ");
//            Calendar calendar = new GregorianCalendar();
////            calendar.setTimeZone(TimeZone.getTimeZone("UTC+1"));//Munich time
//            calendar.setTime(new Date());
//            calendar.add(Calendar.DATE, -cantidadDias);//substract the number of days to look back
//            Date fechaCreacionAnterior = calendar.getTime();
//            query = em.createQuery(sql.toString());
//            System.out.println("fecha desde:" + UtilFormat.convertirFechaDDMMYYYHHMMSS(fechaCreacionAnterior));
//            query.setParameter("fechaCreacion", fechaCreacionAnterior, TemporalType.DATE);
//            query.setParameter("idEstado", idEstado);
//            count = ((Long) query.getSingleResult()).intValue();
//        } catch (NoResultException e) {
//            logger.info("[countFindDocumentoClienteByEstadoAndDias]No se encontraron registros con criterios recibidos");
//            throw new AtsBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron registros con parametros proporcionados");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return count;
//    }
//
//    /**
//     * *
//     *
//     * @param idEstado
//     * @param cantidadDias
//     * @param paginaInicio
//     * @param paginaFinal
//     * @return
//     * @throws AtsBusinessException
//     */
//    @Override
//    public List<Documentocliente> findDocumentoClienteByEstadoAndDias(Integer idEstado, Integer cantidadDias, Integer paginaInicio, Integer paginaFinal) throws AtsBusinessException {
//        logger.info("[findDocumentoClienteByEstadoAndDias]esado=" + idEstado + ",dias" + cantidadDias);
//        StringBuilder sql = new StringBuilder();
//        try {
//            sql.append("SELECT  d FROM Documentocliente d WHERE d.fechaCreacion < :fechaCreacion and d.idEstado.idestado =:idEstado ");
//            Calendar calendar = new GregorianCalendar();
////            calendar.setTimeZone(TimeZone.getTimeZone("UTC+1"));//Munich time
//            calendar.setTime(new Date());
//            calendar.add(Calendar.DATE, -cantidadDias);//substract the number of days to look back
//            Date fechaCreacionAnterior = calendar.getTime();
//            TypedQuery<Documentocliente> query = em.createQuery(sql.toString(), Documentocliente.class);
//            System.out.println("fecha desde:" + UtilFormat.convertirFechaDDMMYYYHHMMSS(fechaCreacionAnterior));
//            query.setParameter("fechaCreacion", fechaCreacionAnterior, TemporalType.DATE);
//            query.setParameter("idEstado", idEstado);
//            if (paginaFinal != null && paginaInicio != null) {
//                query.setFirstResult(paginaInicio);
//                query.setMaxResults(paginaFinal);
//            }
//            return query.getResultList();
//        } catch (NoResultException e) {
//            logger.info("[findDocumentoClienteByEstadoAndDias]No se encontraron registros con criterios recibidos");
//            throw new AtsBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron registros con parametros proporcionados");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     *
//     * @param idfactura
//     * @param numeroSerie
//     * @return
//     * @throws AtsBusinessException
//     */
//    @Override
//    public List<DetalleDocumentoCliente> findDetalleDocumentoByRequest(Integer idfactura, String numeroSerie) throws AtsBusinessException {
//        logger.info("[findDetalleDocumentoByRequest]idFactura=" + idfactura + ",serie:" + numeroSerie);
//        List<DetalleDocumentoCliente> response = null;
//        Query query;
//        try {
//            query = em.createNamedQuery("DetalleDocumentoCliente.findBySerieAndFactura");
//            query.setParameter("idFactura", idfactura);
//            query.setParameter("numeroSerie", numeroSerie);
//            response = query.getResultList();
//        } catch (NoResultException e) {
//            logger.info("[findDetalleDocumentoByRequest]No se encontraron registros con criterios recibidos");
//            throw new AtsBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron registros con parametros proporcionados");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    /**
//     *
//     * @return @throws AtsBusinessException
//     */
//    @Override
//    public Configuracion loadConfiguracionSistema() throws AtsBusinessException {
//        logger.info("[loadConfiguracionSistema]");
//        Configuracion response = null;
//        Query query;
//        try {
//            query = em.createNamedQuery("Configuracion.findAll");
//            query.setFirstResult(0);
//            query.setMaxResults(1);
//            response = (Configuracion) query.getSingleResult();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    /**
//     *
//     * @param encabezado
//     * @return
//     * @throws AtsBusinessBusinessRolledbackException
//     */
//    @TransactionAttribute(TransactionAttributeType.REQUIRED)
//    @Override
//    public OperacionesDocumentoClienteDTO guardarDocumetoCliente(Documentocliente encabezado, Boolean isnew) throws AtsBusinessBusinessRolledbackException {
//        OperacionesDocumentoClienteDTO response = new OperacionesDocumentoClienteDTO(Constants.CODE_OPERATION_FALLIDA, "No se pudo guardar documento ni su detalle");
//        Documentocliente enca;
//        if (isnew) {
//            if (!validarSiExisteDocumento(encabezado)) {
//                em.persist(encabezado);
//                em.flush();
//                em.refresh(encabezado);
//                response.setCodigoRespuesta(Constants.CODE_OPERACION_SATISFACTORIA);
//                response.setMensajeRespuesta("Documento Guardado satisfactoriamente");
//            } else {
//                throw new AtsBusinessBusinessRolledbackException(Constants.CODE_OPERATION_FALLIDA, "Ya existe un registro con ese mismo número y serie de factura,Favor revisar");
//            }
//        } else {
//            enca = em.getReference(Documentocliente.class, encabezado.getIdcorrelativo());
//            enca.setIdCliente(encabezado.getIdCliente());
//            enca.setIdfactura(encabezado.getIdfactura());
//            enca.setIdEstado(encabezado.getIdEstado());
//            enca.setTipoDocumento(encabezado.getTipoDocumento());
//            em.merge(enca);
//            em.flush();
//            em.refresh(enca);
//            response.setCodigoRespuesta(Constants.CODE_OPERACION_SATISFACTORIA);
//            response.setMensajeRespuesta("Documento cargado-actualizados satisfactoriamente");
//        }
//        System.out.println("estado;" + encabezado.getIdEstado().getDesEstado() + ",id:" + encabezado.getIdcorrelativo());
//        response.setDocumento(encabezado);
//        return response;
//    }
//
//    /**
//     * Valida si ya existe numero de factura con la serie proporcionada
//     *
//     * @param encabezado
//     * @return
//     */
//    private Boolean validarSiExisteDocumento(Documentocliente encabezado) {
//        List< Documentocliente> docTemp;
//        Query query;
//        try {
//            //primero validamos si no existe factura con los mismos datos
//            query = em.createNamedQuery("Documentocliente.findByIdfacturaAndSerie");
//            query.setParameter("idfactura", encabezado.getIdfactura());
//            query.setParameter("serieFactura", encabezado.getSerieFactura());
//            query.setParameter("tipoDocumento", encabezado.getTipoDocumento());
//            docTemp = query.getResultList();
//            if (docTemp.size() > 0) {
//                return Boolean.TRUE;
//            } else {
//                return Boolean.FALSE;
//            }
//        } catch (NoResultException e) {
////            e.printStackTrace();
//            return Boolean.FALSE;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Boolean.TRUE;
//        }
//    }
//
//    /**
//     *
//     * @param listaDetalle
//     * @param encabezado
//     * @return
//     * @throws AtsBusinessBusinessRolledbackException
//     */
//    @Override
//    @TransactionAttribute(TransactionAttributeType.REQUIRED)
//    public OperacionesDocumentoClienteDTO guardarDetalleDocumentoCliente(DetalleDocumentoCliente detalle, Documentocliente encabezado, Boolean isNew) throws AtsBusinessBusinessRolledbackException {
//        OperacionesDocumentoClienteDTO response = new OperacionesDocumentoClienteDTO(Constants.CODE_OPERATION_FALLIDA, "No se pudo guardar detalle documento ni su detalle");
//        GenericResponse responseUpdate;
//        Ordentrabajo ordenSelected;
//        try {
//            //guardamos los detalle de documento
//            if (encabezado.getIdcorrelativo() != null) {
//                if (isNew) {
//                    detalle.setSerieFactura(encabezado.getSerieFactura());
//                    detalle.setIdfactura(encabezado.getIdfactura());
//                    em.persist(detalle);
//                    em.flush();
//                } else {
//                    em.merge(detalle);
//                    em.flush();
//                }
//                response.setDetalleDocumento(detalle);
//                //validamos si le cambiamos el estado a la ordentrabajo
//                if (detalle.getCambiarEstado() != null) {
//                    if (detalle.getCambiarEstado()) {
//                        ordenSelected = detalle.getIdOrdenTrabajo();
//                        ordenSelected.setIdEstado(new EstadoOrdentrabajo(Constants.CODIGO_ESTADO_FACTURADO));
//                        ordenSelected.setNumFact(encabezado.getIdfactura());
//                        ordenSelected.setSerieFactura(encabezado.getSerieFactura());
//                        em.merge(ordenSelected);
//                    }
//                }
//                responseUpdate = calcularIvaDocumentoCliente(encabezado);
//                if (responseUpdate.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
//                    response.setCodigoRespuesta(Constants.CODE_OPERACION_SATISFACTORIA);
//                    response.setMensajeRespuesta("detalle Documento cliente guardado satisfactoriamente");
//                    response.setDocumento(encabezado);
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setMensajeRespuesta(e.toString());
//            throw new AtsBusinessBusinessRolledbackException(Constants.CODE_OPERATION_FALLIDA, e.toString());
//        }
//        return response;
//    }
//
//    /**
//     * *
//     *
//     * @param detalle
//     * @param encabezado
//     * @return
//     * @throws AtsBusinessBusinessRolledbackException
//     */
//    @Override
//    public OperacionesDocumentoClienteDTO eliminarDetalleDocumentoCliente(DetalleDocumentoCliente detalle, Documentocliente encabezado) throws AtsBusinessBusinessRolledbackException {
//        OperacionesDocumentoClienteDTO response = new OperacionesDocumentoClienteDTO(Constants.CODE_OPERATION_FALLIDA, "No se pudo eliminar detalle documento ni su detalle");
//        GenericResponse responseUpdate;
//        Ordentrabajo ordenSelected;
//        try {
//            //eliminamos el detalle factura
//            if (encabezado.getIdcorrelativo() != null) {
//                detalle = em.getReference(DetalleDocumentoCliente.class, detalle.getIdcorrelativo());
//                em.remove(detalle);
//                response.setDetalleDocumento(detalle);
//                //validamos si le cambiamos el estado a la ordentrabajo
//                if (detalle.getCambiarEstado() != null) {
//                    if (detalle.getCambiarEstado()) {
//                        ordenSelected = detalle.getIdOrdenTrabajo();
//                        ordenSelected.setIdEstado(new EstadoOrdentrabajo(Constants.CODIGO_ESTADO_FACTURADO));
//                        ordenSelected.setNumFact(encabezado.getIdfactura());
//                        ordenSelected.setSerieFactura(encabezado.getSerieFactura());
//                        em.merge(ordenSelected);
//                    }
//                }
//                responseUpdate = calcularIvaDocumentoCliente(encabezado);
//                if (responseUpdate.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
//                    response.setCodigoRespuesta(Constants.CODE_OPERACION_SATISFACTORIA);
//                    response.setMensajeRespuesta("Item Documento cliente eliminado satisfactoriamente");
//                    response.setDocumento(encabezado);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setMensajeRespuesta(e.toString());
//            throw new AtsBusinessBusinessRolledbackException(Constants.CODE_OPERATION_FALLIDA, e.toString());
//        }
//        return response;
//    }
//
//    /**
//     *
//     * @param encabezado
//     * @return
//     * @throws AtsBusinessBusinessRolledbackException
//     */
//    private GenericResponse calcularIvaDocumentoCliente(Documentocliente encabezado) throws AtsBusinessBusinessRolledbackException {
//        GenericResponse response = new GenericResponse(Constants.CODE_OPERATION_FALLIDA, "No se pudo calcular iva para documento cliente");
//        CalculoSumaFacturaResponseDTO responseSuma;
//        Query query;
//        Configuracion config;
//        try {
//            // recuperamos la configuracion d esitemsa
//            query = em.createNamedQuery("Configuracion.findAll");
//            query.setFirstResult(0);
//            query.setMaxResults(1);
//            config = (Configuracion) query.getSingleResult();
//            if (config != null) {
//                //primero validamos si no existe factura con los mismos datos
//                responseSuma = calcularSumaFactura(encabezado.getIdfactura(), encabezado.getSerieFactura());
//                if (responseSuma.getCodigoRespuesta().intValue() == Constants.CODE_OPERACION_SATISFACTORIA) {
//                    if (encabezado.getTipoDocumento().equalsIgnoreCase("CF")) {
//                        encabezado.setGravado(responseSuma.getSumaFactura());
//                        encabezado.setIva(responseSuma.getSumaFactura() * config.getPorcentajeIva());
//                        encabezado.setTotal(encabezado.getIva() + responseSuma.getSumaFactura());
//                    } else {
//                        encabezado.setTotal(responseSuma.getSumaFactura());
//                        encabezado.setValorExento(responseSuma.getSumaFactura());
//                    }
////                //enca = em.getReference(Documentocliente.class, encabezado.getIdcorrelativo());
//                } else {
//                    encabezado.setGravado(0.0);
//                    encabezado.setIva(0.0);
//                    encabezado.setTotal(0.0);
//                    response = new GenericResponse(Constants.CODE_OPERACION_SATISFACTORIA, "Calculo de subtotales satisfactora");
//                }
//                response = new GenericResponse(Constants.CODE_OPERACION_SATISFACTORIA, "Calculo de subtotales satisfactora");
//                em.merge(encabezado);
//                em.flush();
//            } else {
//                throw new AtsBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se pudo recuperar configuracion de sistema");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setMensajeRespuesta(e.toString());
//            throw new AtsBusinessBusinessRolledbackException(Constants.CODE_OPERATION_FALLIDA, e.toString());
//        }
//        return response;
//    }
//
//    /**
//     * *
//     *
//     * @param idFactura
//     * @param numeroSerie
//     * @return
//     */
//    private CalculoSumaFacturaResponseDTO calcularSumaFactura(Integer idFactura, String numeroSerie) {
//        logger.info("[calcularSumaFactura]IdFactura:" + idFactura);
//        CalculoSumaFacturaResponseDTO response = new CalculoSumaFacturaResponseDTO(Constants.CODE_OPERATION_FALLIDA, "No se pudo calcular suma de factura");
//        Query query;
//        Double sumas;
//        try {
//            query = em.createNativeQuery("select  sum(precio_total) from detalledocumentocliente where idfactura =?1 and serie_factura=?2");
//            query.setParameter(1, idFactura);
//            query.setParameter(2, numeroSerie);
//            sumas = (Double) query.getSingleResult();
//            response = new CalculoSumaFacturaResponseDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Operacion ok");
//            response.setSumaFactura(sumas);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return response;
//    }
//
//    /**
//     * Consulta detalle factura con los detalles asociados
//     *
//     * @param encabezado
//     * @return ConsultaFacturaDTO detalle
//     */
//    @Override
//    public ConsultaFacturaDTO cargarInformacionFactura(Documentocliente encabezado) {
//        Documentocliente docTemp;
//        ConsultaFacturaDTO response = new ConsultaFacturaDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo cargar detalle factura");
//        List< DetalleDocumentoCliente> lista;
//        List<DetalleFacturaDTO> listaDto;
//        Query query;
//        Configuracion conf;
//        try {
//            query = em.createNamedQuery("Documentocliente.findByIdfacturaAndSerie");
//            query.setParameter("idfactura", encabezado.getIdfactura());
//            query.setParameter("serieFactura", encabezado.getSerieFactura());
//            query.setParameter("tipoDocumento", encabezado.getTipoDocumento());
//            docTemp = (Documentocliente) query.getSingleResult();
//            if (docTemp != null) {
//                //cargamos detalle de factura;
//                query = em.createNamedQuery("DetalleDocumentoCliente.findBySerieAndFactura");
//                query.setParameter("idFactura", encabezado.getIdfactura());
//                query.setParameter("numeroSerie", encabezado.getSerieFactura());
//                lista = query.getResultList();
//                if (lista != null) {
//                    if (lista.size() > 0) {
//                        conf = loadConfiguracionSistema();
//                        listaDto = Assemble.loadDetalleFromEntity(lista);
//                        response.setListaDetalle(listaDto);
//                        response.setDocumentoCliente(Assemble.loadDetalleFromEntity(docTemp, lista, conf));
//                        response.setCodigoRespuesta(Constants.CODE_OPERACION_SATISFACTORIA);
//                        response.setMensajeRespuesta("Busqueda detalle factura satisfactoria");
//                    }
//                }
//            }
//        } catch (NoResultException e) {
//            e.printStackTrace();
//            response.setMensajeRespuesta(e.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setMensajeRespuesta(e.toString());
//        }
//        return response;
//    }
//
//    /**
//     *
//     * @return @throws AtsBusinessException
//     */
//    @Override
//    public List<EstadoFactura> loadAllEstadoFactura() throws AtsBusinessException {
//        logger.info("[loadAllEstadoFactura]");
//        List<EstadoFactura> response = null;
//        Query query;
//        try {
//            query = em.createNamedQuery("EstadoFactura.findAll");
//            response = query.getResultList();
//        } catch (NoResultException e) {
//            logger.info("[loadAllEstadoFactura]No se encontraron registros con criterios recibidos");
//            throw new AtsBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron registros con parametros proporcionados");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return response;
//    }
}
