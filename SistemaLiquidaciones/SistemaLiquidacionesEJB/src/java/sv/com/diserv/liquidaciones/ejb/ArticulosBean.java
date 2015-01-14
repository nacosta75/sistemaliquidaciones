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
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.dto.BusquedaArticuloDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesArticuloDTO;
import sv.com.diserv.liquidaciones.entity.Articulos;
import sv.com.diserv.liquidaciones.entity.Empresas;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

/**
 *
 * @author abraham.acosta
 */
@Stateless
public class ArticulosBean implements ArticulosBeanLocal {

    static final Logger logger = Logger.getLogger(ArticulosBean.class.getName());
    @PersistenceContext(unitName = "SistemaLiquidacionesEJBPU")
    private EntityManager em;
    @EJB
    GenericDaoServiceBeanLocal genericDaoBean;

    @EJB
    TipoArticuloBeanLocal tipoArticuloBean;
    
    @EJB
    UmedidaArticuloBeanLocal uMedidaBean;
    
    @EJB
    MarcaArticuloBeanLocal marcaBean;
    
    @EJB
    LineaArticuloBeanLocal lineaBean;
    
    @EJB
    EmpresasBeanLocal empresaBean;
    
    
    @Override
    public List<Articulos> loadAllArticulos(int inicio, int fin) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadAllArticulos] desde:" + inicio + " hasta:" + fin);
        List<Articulos> articulosList = null;
        Query query;
        try {
            query = em.createNamedQuery("Articulos.findAll");
            query.setFirstResult(inicio);
            query.setMaxResults(fin);
            articulosList = query.getResultList();
            if (articulosList != null) {
                logger.log(Level.INFO, "[loadAllArticulos] Se encontraron " + articulosList.size() + " articulos");
            }
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllArticulos][NoResultException]No se encontraron Articulos");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron articulos");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllArticulos][Exception]Se mostro una excepcion al buscar articulos");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return articulosList;
    }

    @Override
    public Integer countAllArticulos() throws DiservBusinessException {
        logger.log(Level.INFO, "[countAllArticulos]INIT");
        int count = 0;
        Query query;
        try {
            //query = em.createNamedQuery("Articulos.countAll");
            query = em.createQuery("SELECT count(s) FROM Articulos s ");
            count = ((Long) query.getSingleResult()).intValue();
            logger.log(Level.INFO, "[Total de registros encontrados]" + count);
        } catch (Exception e) {
            logger.log(Level.INFO, "[Excepcion en countAllArticulo]" + e.toString());
            //throw new DesempenoBusinessException(Constants.CODE_OPERATION_FALLIDA, "");
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public OperacionesArticuloDTO guardarArticulo(Articulos articulo) throws DiservBusinessException {
        OperacionesArticuloDTO response = new OperacionesArticuloDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo guardar articulo");
        try {
            articulo = genericDaoBean.create(articulo);
            response = new OperacionesArticuloDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Articulo creado satisfactoriamente");
            response.setArticulo(articulo);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }

    @Override
    public List<Articulos> loadAllArticuloByLike(String nombreLike) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadAllArticuloByLike] nombreLike:" + nombreLike);
        List<Articulos> articuloList = null;
        Query query;
        try {
            query = em.createNamedQuery("Articulos.findByDescarticulo");
            query.setParameter("descarticulo", "%" + nombreLike.toUpperCase() + "%");
            query.setMaxResults(Constants.REGISTROS_A_MOSTRAR_LISTA);
            articuloList = query.getResultList();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllArticuloByLike][NoResultException]No se encontraron articulos");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron articulos");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllArticuloByLike][Exception]Se mostro una excepcion al buscar articulo");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return articuloList;
    }

    @Override
    public OperacionesArticuloDTO actualizarArticulo(Articulos articulo) throws DiservBusinessException {
        OperacionesArticuloDTO response = new OperacionesArticuloDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo guardar articulo");
        try {
            articulo = genericDaoBean.update(articulo);
            response = new OperacionesArticuloDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Articulo actualizado satisfactoriamente");
            response.setArticulo(articulo);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }

    @Override
    public List<Articulos> buscarArticuloByCriteria(BusquedaArticuloDTO request) throws DiservBusinessException {
        logger.info("[consultarArticulosParametros]Parametros=" + request.toString());
        List<Articulos> response = new ArrayList<>();
        Articulos Articulos;
        List<String> condiciones = new ArrayList<>();
        if (request.getIdarticulo() != null) {
            condiciones.add(" UPPER(idArticulo) LIKE UPPER('%" + request.getIdarticulo() + "%') ");
        }
        if (request.getDescarticulo() != null) {
            condiciones.add(" UPPER(descarticulo) LIKE UPPER('%" + request.getDescarticulo() + "%') ");
        }
        if (request.getCodarticulo() != null) {
            condiciones.add(" UPPER(codarticulo) LIKE UPPER('%" + request.getCodarticulo() + "%') ");
        }

        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT idarticulo,codarticulo,descarticulo,idtipoarticulo, "+
                    "idumedida,idmarca,idlinea,idempresa "
                    + " FROM Articulos ");
            if (!condiciones.isEmpty()) {
                sb.append(" WHERE ");
                sb.append(condiciones.get(0));
                for (int i = 1; i < condiciones.size(); i++) {
                    sb.append(" AND ");
                    sb.append(condiciones.get(i));
                }
            }
            sb.append(" ORDER BY idarticulo asc ");
            System.out.println("SQL A EJECUTAR:--> " + sb.toString());
            System.out.println("PARAMETROS RECIBIDOS:-->" + request.toString());
            Query q = em.createNativeQuery(sb.toString());
            List<Object[]> lista = q.getResultList();
            if (lista.size() > 0) {
                for (Object[] item : lista) {
                    
                    Articulos = new Articulos();
                    
                    Articulos.setIdarticulo(Integer.parseInt(item[0] != null ? item[0].toString() : "0"));
                    Articulos.setCodarticulo(item[1] != null ? item[1].toString() : "N/D");
                    Articulos.setDescarticulo(item[2] != null ? item[2].toString() : "N/D");
                     Articulos.setIdtipoarticulo(tipoArticuloBean.loadTipoArticuloById(Integer.parseInt(item[3] != null ? item[3].toString() : "0")));//tipoarticulo
                    Articulos.setIdumedida(uMedidaBean.loadUmedidaById(Integer.parseInt(item[4] != null ? item[4].toString() : "0")));//umedida
                    Articulos.setIdmarca(marcaBean.loadMarcaByID(Integer.parseInt(item[5] != null ? item[5].toString() : "0")));//MarcaArticulo
                    Articulos.setIdlinea(lineaBean.loadLineaByID(Integer.parseInt(item[6] != null ? item[6].toString() : "0")));//LineaArticulo
                    Articulos.setIdempresa(empresaBean.loadEmpresaByID(Integer.parseInt(item[7] != null ? item[7].toString() : "0")));//EmpresasArticulo
                    
                     response.add(Articulos);
                }
            }
        } catch (NoResultException e) {
            logger.info("[consultarArticulosFinalizadasParametros]No se encontraron registros con criterios recibidos");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron registros con parametros proporcionados");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public List<Articulos> loadArticuloByDescripcionLike(String likeNombre) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadArticuloByNombreLike]Buscando:" + likeNombre);
        Query query;
        try {
            query = em.createQuery("Articulos.findByDescarticulo");
            query.setParameter("descarticulo", "%" + likeNombre + "%");
            return query.getResultList();
        } catch (Exception e) {
            logger.log(Level.ERROR, "[getdescarticulo]Exception={0}", e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Articulos loadArticuloByID(Integer idArticulo) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadArticuloByID] Idarticulo:" + idArticulo);
        Articulos articulo = null;
        Query query;
        try {
            query = em.createNamedQuery("Articulos.findByIdarticulo");
            query.setParameter("idarticulo", idArticulo);
            articulo = (Articulos) query.getSingleResult();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadArticuloByID][NoResultException]No se encontraron articulos");
//            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron articulo");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadArticuloByID][Exception]Se mostro una excepcion al buscar articulo");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return articulo;
    }

}
