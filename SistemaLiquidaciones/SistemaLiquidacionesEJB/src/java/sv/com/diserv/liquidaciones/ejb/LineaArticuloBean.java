/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.com.diserv.liquidaciones.dto.BusquedaLineaArticuloDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesLineaArticuloDTO;
import sv.com.diserv.liquidaciones.entity.LineaArticulo;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sv.com.diserv.liquidaciones.util.Constants;

/**
 *
 * @author trompudo
 */
@Stateless
public class LineaArticuloBean implements LineaArticuloBeanLocal{

    
    static final Logger logger = Logger.getLogger(LineaArticuloBean.class.getName());
    @PersistenceContext(unitName = "SistemaLiquidacionesEJBPU")
    private EntityManager em;
    @EJB
    GenericDaoServiceBeanLocal genericDaoBean;
    
    
    @Override
    public List<LineaArticulo> loadAllLineas(int inicio, int fin) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadAllLineaArticulo] desde:" + inicio + " hasta:" + fin);
        List<LineaArticulo> lineasList = null;
        Query query;
        try {
            query = em.createNamedQuery("LineaArticulo.findAll");
            query.setFirstResult(inicio);
            query.setMaxResults(fin);
            lineasList = query.getResultList();
            if (lineasList != null) {
                logger.log(Level.INFO, "[loadAllLineaArticulo] Se encontraron " + lineasList.size() + " lineas");
            }
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllLineaArticulo][NoResultException]No se encontraron Lineas");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron lineas");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllLineaArticulo][Exception]Se mostro una excepcion al buscar lineas");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return lineasList;
    }

    @Override
    public Integer countAllLineaArticulo() throws DiservBusinessException {
        logger.log(Level.INFO, "[countAllLinea]INIT");
        int count = 0;
        Query query;
        try {
           //query = em.createNamedQuery("Lineas.countAll");
            query = em.createQuery("SELECT count(s) FROM LineaArticulo s ");
            count = ((Long) query.getSingleResult()).intValue();
            logger.log(Level.INFO, "[Total de registros encontrados]" + count);
        } catch (Exception e) {
            logger.log(Level.INFO, "[Excepcion en countAllLinea]" + e.toString());
            //throw new DesempenoBusinessException(Constants.CODE_OPERATION_FALLIDA, "");
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public OperacionesLineaArticuloDTO guardarLinea(LineaArticulo linea) throws DiservBusinessException {
         OperacionesLineaArticuloDTO response = new OperacionesLineaArticuloDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo guardar linea");
        try {
            linea = genericDaoBean.create(linea);
            response = new OperacionesLineaArticuloDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Linea creada satisfactoriamente");
            response.setLineaArticulo(linea);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }

    @Override
    public List<LineaArticulo> loadAllLineaArticuloByLike(String desclinea) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadAllLineaByLike] nombreLike:" + desclinea);
        List<LineaArticulo> lineaList = null;
        Query query;
        try {
            query = em.createNamedQuery("LineaArticulo.findByDesclinea");
            query.setParameter("desclinea", "%" + desclinea.toUpperCase() + "%");
            query.setMaxResults(Constants.REGISTROS_A_MOSTRAR_LISTA);
            lineaList = query.getResultList();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllLineaByLike][NoResultException]No se encontraron usuarios");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron linea");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllLineaByLike][Exception]Se mostro una excepcion al buscar linea");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return lineaList;
    }

    @Override
    public OperacionesLineaArticuloDTO actualizarLinea(LineaArticulo linea) throws DiservBusinessException {
        OperacionesLineaArticuloDTO response = new OperacionesLineaArticuloDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo guardar linea");
        try {
            linea = genericDaoBean.update(linea);
            response = new OperacionesLineaArticuloDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Linea actualizado satisfactoriamente");
            response.setLineaArticulo(linea);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }

    @Override
    public List<LineaArticulo> buscarLineaByCriteria(BusquedaLineaArticuloDTO request) throws DiservBusinessException {
        logger.info("[consultarLineaArticulosParametros]Parametros=" + request.toString());
        List<LineaArticulo> response = new ArrayList<>();
        LineaArticulo LineaArticulo;
        List<String> condiciones = new ArrayList<>();
        if (request.getIdlinea()!= null) {
            condiciones.add(" UPPER(idLineaArticulo) LIKE UPPER('%" + request.getIdlinea() + "%') ");
        }
        if (request.getDesclinea() != null) {
            condiciones.add(" UPPER(desclinea) LIKE UPPER('%" + request.getDesclinea() + "%') ");
        }
        if (request.getActiva() != null) {
            condiciones.add(" UPPER(activa) LIKE UPPER('%" + request.getActiva() + "%') ");
        }
       
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM LineaArticulo ");
            if (!condiciones.isEmpty()) {
                sb.append(" WHERE ");
                sb.append(condiciones.get(0));
                for (int i = 1; i < condiciones.size(); i++) {
                    sb.append(" AND ");
                    sb.append(condiciones.get(i));
                }
            }
            sb.append(" ORDER BY idlinea DESC ");
            System.out.println("SQL A EJECUTAR:--> " + sb.toString());
            System.out.println("PARAMETROS RECIBIDOS:-->" + request.toString());
            Query q = em.createNativeQuery(sb.toString());
            List<Object[]> lista = q.getResultList();
            if (lista.size() > 0) {
                for (Object[] item : lista) {
                    LineaArticulo = new LineaArticulo();
                    LineaArticulo.setIdlinea(Integer.parseInt(item[0] != null ? item[0].toString() : "0"));
                    //LineaArticulo.setCodigo(item[1] != null ? item[1].toString() : "N/D");
                    LineaArticulo.setActiva(item[2] != null ? item[2].toString() : "N/D");
                    LineaArticulo.setDesclinea(item[3] != null ? item[3].toString() : "N/D");
                    //LineaArticulo.setTelefono(item[4] != null ? item[4].toString() : "N/D");
                    //LineaArticulo.setEncargado(item[5] != null ? item[5].toString() : "N/D");
                   // LineaArticulo.setIdempresa(item[6] != null ? item[6].toString() : "N/D");
                    //LineaArticulo.setActiva(item[7] != null ? item[7].toString() : "N/D");
                   // linea.setEstadoLineaArticulo(item[8] != null ? Boolean.valueOf(item[8].toString()) : false);
                    response.add(LineaArticulo);
                }
            }
        } catch (NoResultException e) {
            logger.info("[consultarLineasFinalizadasParametros]No se encontraron registros con criterios recibidos");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron registros con parametros proporcionados");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public List<LineaArticulo> loadLineaByDescripcionLike(String likeNombre) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadLineaByNombreLike]Buscando:" + likeNombre);
        Query query;
        try {
            query = em.createQuery("LineaArticulo.findByDesclinea");
            query.setParameter("desclinea", "%" + likeNombre + "%");
            return query.getResultList();
        } catch (Exception e) {
            logger.log(Level.ERROR, "[getDelito]Exception={0}", e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public LineaArticulo loadLineaByID(Integer idLinea) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadLineaByID] Idlinea:" + idLinea);
        LineaArticulo linea = null;
        Query query;
        try {
            query = em.createNamedQuery("LineaArticulo.findByIdlinea");
            query.setParameter("idlinea", idLinea);
            linea = (LineaArticulo) query.getSingleResult();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadLineaByID][NoResultException]No se encontraron lineas");
//            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron linea");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadLineaByID][Exception]Se mostro una excepcion al buscar linea");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return linea;
    }

    
}
