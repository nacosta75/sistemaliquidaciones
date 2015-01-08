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
import sv.com.diserv.liquidaciones.dto.BusquedaMarcaArticuloDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesMarcaArticuloDTO;
import sv.com.diserv.liquidaciones.entity.MarcaArticulo;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.util.Constants;

/**
 *
 * @author abraham.acosta
 */
@Stateless
public class MarcaArticuloBean implements MarcaArticuloBeanLocal{
    
    static final Logger logger = Logger.getLogger(MarcaArticuloBean.class.getName());
    @PersistenceContext(unitName = "SistemaLiquidacionesEJBPU")
    private EntityManager em;
    @EJB
    GenericDaoServiceBeanLocal genericDaoBean;
    
    
     @Override
    public List<MarcaArticulo> loadAllMarcas() throws DiservBusinessException {
          logger.log(Level.INFO, "[loadAllMarcaArticulo]");
        List<MarcaArticulo> marcasList = null;
        Query query;
        try {
            query = em.createNamedQuery("MarcaArticulo.findAll");
            marcasList = query.getResultList();
            if (marcasList != null) {
                logger.log(Level.INFO, "[loadAllMarcaArticulo] Se encontraron " + marcasList.size() + " marcas");
            }
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllMarcaArticulo][NoResultException]No se encontraron Marcas");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron marcas");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllMarcaArticulo][Exception]Se mostro una excepcion al buscar marcas");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return marcasList;
    }

    @Override
    public List<MarcaArticulo> loadAllMarcas(int inicio, int fin) throws DiservBusinessException {
          logger.log(Level.INFO, "[loadAllMarcaArticulo] desde:" + inicio + " hasta:" + fin);
        List<MarcaArticulo> marcasList = null;
        Query query;
        try {
            query = em.createNamedQuery("MarcaArticulo.findAll");
            query.setFirstResult(inicio);
            query.setMaxResults(fin);
            marcasList = query.getResultList();
            if (marcasList != null) {
                logger.log(Level.INFO, "[loadAllMarcaArticulo] Se encontraron " + marcasList.size() + " marcas");
            }
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllMarcaArticulo][NoResultException]No se encontraron Marcas");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron marcas");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllMarcaArticulo][Exception]Se mostro una excepcion al buscar marcas");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return marcasList;
    }

    @Override
    public Integer countAllMarcaArticulo() throws DiservBusinessException {
        logger.log(Level.INFO, "[countAllMarca]INIT");
        int count = 0;
        Query query;
        try {
           //query = em.createNamedQuery("Marcas.countAll");
            query = em.createQuery("SELECT count(s) FROM MarcaArticulo s ");
            count = ((Long) query.getSingleResult()).intValue();
            logger.log(Level.INFO, "[Total de registros encontrados]" + count);
        } catch (Exception e) {
            logger.log(Level.INFO, "[Excepcion en countAllMarca]" + e.toString());
            //throw new DesempenoBusinessException(Constants.CODE_OPERATION_FALLIDA, "");
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public OperacionesMarcaArticuloDTO guardarMarca(MarcaArticulo marca) throws DiservBusinessException {
        OperacionesMarcaArticuloDTO response = new OperacionesMarcaArticuloDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo guardar marca");
        try {
            marca = genericDaoBean.create(marca);
            response = new OperacionesMarcaArticuloDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Marca creada satisfactoriamente");
            response.setMarcaArticulo(marca);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }

    @Override
    public List<MarcaArticulo> loadAllMarcaArticuloByLike(String descmarca) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadAllMarcaByLike] nombreLike:" + descmarca);
        List<MarcaArticulo> marcaList = null;
        Query query;
        try {
            query = em.createNamedQuery("MarcaArticulo.findByDescmarca");
            query.setParameter("descmarca", "%" + descmarca.toUpperCase() + "%");
            query.setMaxResults(Constants.REGISTROS_A_MOSTRAR_LISTA);
            marcaList = query.getResultList();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllMarcaByLike][NoResultException]No se encontraron marcas");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron marcas");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllMarcaByLike][Exception]Se mostro una excepcion al buscar marca");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return marcaList;
    }

    @Override
    public OperacionesMarcaArticuloDTO actualizarMarca(MarcaArticulo marca) throws DiservBusinessException {
        OperacionesMarcaArticuloDTO response = new OperacionesMarcaArticuloDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo guardar marca");
        try {
            marca = genericDaoBean.update(marca);
            response = new OperacionesMarcaArticuloDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Marca actualizada satisfactoriamente");
            response.setMarcaArticulo(marca);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }

    @Override
    public List<MarcaArticulo> buscarMarcaByCriteria(BusquedaMarcaArticuloDTO request) throws DiservBusinessException {
        logger.info("[consultarMarcaArticulosParametros]Parametros=" + request.toString());
        List<MarcaArticulo> response = new ArrayList<>();
        MarcaArticulo MarcaArticulo;
        List<String> condiciones = new ArrayList<>();
        if (request.getIdmarca()!= null) {
            condiciones.add(" UPPER(idMarcaArticulo) LIKE UPPER('%" + request.getIdmarca() + "%') ");
        }
        if (request.getDescripcion() != null) {
            condiciones.add(" UPPER(descmarca) LIKE UPPER('%" + request.getDescripcion() + "%') ");
        }
        if (request.getActiva() != null) {
            condiciones.add(" UPPER(activa) LIKE UPPER('%" + request.getActiva() + "%') ");
        }
       
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM MarcaArticulo ");
            if (!condiciones.isEmpty()) {
                sb.append(" WHERE ");
                sb.append(condiciones.get(0));
                for (int i = 1; i < condiciones.size(); i++) {
                    sb.append(" AND ");
                    sb.append(condiciones.get(i));
                }
            }
            sb.append(" ORDER BY idmarca DESC ");
            System.out.println("SQL A EJECUTAR:--> " + sb.toString());
            System.out.println("PARAMETROS RECIBIDOS:-->" + request.toString());
            Query q = em.createNativeQuery(sb.toString());
            List<Object[]> lista = q.getResultList();
            if (lista.size() > 0) {
                for (Object[] item : lista) {
                    MarcaArticulo = new MarcaArticulo();
                    MarcaArticulo.setIdmarca(Integer.parseInt(item[0] != null ? item[0].toString() : "0"));
                    //MarcaArticulo.setCodigo(item[1] != null ? item[1].toString() : "N/D");
                    MarcaArticulo.setActiva(item[2] != null ? item[2].toString() : "N/D");
                    MarcaArticulo.setDescmarca(item[3] != null ? item[3].toString() : "N/D");
                    //MarcaArticulo.setTelefono(item[4] != null ? item[4].toString() : "N/D");
                    //MarcaArticulo.setEncargado(item[5] != null ? item[5].toString() : "N/D");
                   // MarcaArticulo.setIdempresa(item[6] != null ? item[6].toString() : "N/D");
                    //MarcaArticulo.setActiva(item[7] != null ? item[7].toString() : "N/D");
                   // marca.setEstadoMarcaArticulo(item[8] != null ? Boolean.valueOf(item[8].toString()) : false);
                    response.add(MarcaArticulo);
                }
            }
        } catch (NoResultException e) {
            logger.info("[consultarMarcasFinalizadasParametros]No se encontraron registros con criterios recibidos");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron registros con parametros proporcionados");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    public List<MarcaArticulo> loadMarcaByDescripcionLike(String likeNombre) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadMarcaByNombreLike]Buscando:" + likeNombre);
        Query query;
        try {
            query = em.createQuery("MarcaArticulo.findByDescmarca");
            query.setParameter("descmarca", "%" + likeNombre + "%");
            return query.getResultList();
        } catch (Exception e) {
            logger.log(Level.ERROR, "[getDelito]Exception={0}", e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public MarcaArticulo loadMarcaByID(Integer idMarca) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadMarcaByID] Idmarca:" + idMarca);
        MarcaArticulo marca = null;
        Query query;
        try {
            query = em.createNamedQuery("MarcaArticulo.findByIdmarca");
            query.setParameter("idmarca", idMarca);
            marca = (MarcaArticulo) query.getSingleResult();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadMarcaByID][NoResultException]No se encontraron marcas");
//            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron marca");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadMarcaByID][Exception]Se mostro una excepcion al buscar marca");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return marca;
    }

   
    
}
