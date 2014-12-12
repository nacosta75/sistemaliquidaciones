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
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sv.com.diserv.liquidaciones.dto.BusquedaSucursalDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesSucursalDTO;
import sv.com.diserv.liquidaciones.entity.Sucursales;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.util.Constants;

/**
 *
 * @author abraham.acosta
 */
@Stateless
public class SucursalesBean implements SucursalesBeanLocal {

    
    static final Logger logger = Logger.getLogger(SucursalesBean.class.getName());
    @PersistenceContext(unitName = "SistemaLiquidacionesEJBPU")
    
    private EntityManager em;
    
    
    @EJB
    GenericDaoServiceBeanLocal genericDaoBean;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Sucursales> loadAllSucursal(int inicio, int fin) throws DiservBusinessException {
       
        logger.log(Level.INFO, "[loadAllSucursal] desde:" + inicio + " hasta:" + fin);
        List<Sucursales> sucursaleList = null;
        Query query;
        try {
            query = em.createNamedQuery("Sucursales.findAll");
            query.setFirstResult(inicio);
            query.setMaxResults(fin);
            sucursaleList = query.getResultList();
            if (sucursaleList != null) {
                logger.log(Level.INFO, "[loadAllSucursal] Se encontraron " + sucursaleList.size() + " sucursales");
            }
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllSucursal][NoResultException]No se encontraron usuarios");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron Sucursales");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllSucursal][Exception]Se mostro una excepcion al buscar Sucursal");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return sucursaleList;
    }

    @Override
    public Integer countAllSucursal() throws DiservBusinessException {
        
         logger.log(Level.INFO, "[countAllSucursal]INIT");
        int count = 0;
        Query query;
        try {
         
            query = em.createQuery("SELECT count(s) FROM Sucursales s ");
            count = ((Long) query.getSingleResult()).intValue();
            logger.log(Level.INFO, "[Total de registros encontrados]" + count);
        } catch (Exception e) {
            logger.log(Level.INFO, "[Excepcion en countAllSucursal]" + e.toString());
            //throw new DesempenoBusinessException(Constants.CODE_OPERATION_FALLIDA, "");
            e.printStackTrace();
        }
        return count;
        
    }

    @Override
    public OperacionesSucursalDTO guardarSucursal(Sucursales sucursal) throws DiservBusinessException {
         OperacionesSucursalDTO response = new OperacionesSucursalDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo guardar bodega");
        try {
            sucursal = genericDaoBean.create(sucursal);
            response = new OperacionesSucursalDTO(Constants.CODE_OPERACION_SATISFACTORIA, "sucursal creada satisfactoriamente");
            response.setSucursales(sucursal);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }
    

    @Override
    public OperacionesSucursalDTO actualizarSucursal(Sucursales sucursal) throws DiservBusinessException {
          OperacionesSucursalDTO response = new OperacionesSucursalDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo guardar sucursal");
        try {
            sucursal = genericDaoBean.update(sucursal);
            response = new OperacionesSucursalDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Sucursal actualizada satisfactoriamente");
            response.setSucursales(sucursal);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }

    @Override
    public List<Sucursales> buscarSucursalByCriteria(BusquedaSucursalDTO request) throws DiservBusinessException {
        logger.info("[consultarBitacoraFinalizadasParametros]Parametros=" + request.toString());
        List<Sucursales> response = new ArrayList<>();
        Sucursales sucursales;
        List<String> condiciones = new ArrayList<>();
        if (request.getIdsucursal()!= null) {
            condiciones.add(" UPPER(idSucursal) LIKE UPPER('%" + request.getIdsucursal() + "%') ");
        }
      
        if (request.getDescripcion() != null) {
            condiciones.add(" UPPER(descripcion) LIKE UPPER('%" + request.getDescripcion() + "%') ");
        }
        if (request.getDireccion() != null) {
            condiciones.add(" UPPER(direccion) LIKE UPPER('%" + request.getDireccion() + "%') ");
        }
        if (request.getTelefono() != null) {
            condiciones.add(" UPPER(telefono) LIKE UPPER('%" + request.getTelefono() + "%') ");
        }
        if (request.getEncargado() != null) {
            condiciones.add(" UPPER(encargado) LIKE UPPER('%" + request.getEncargado() + "%') ");
        }
        if (request.getIdempresa() != null) {
            condiciones.add(" UPPER(empresa) LIKE UPPER('%" + request.getIdempresa() + "%') ");
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM Sucursales ");
            if (!condiciones.isEmpty()) {
                sb.append(" WHERE ");
                sb.append(condiciones.get(0));
                for (int i = 1; i < condiciones.size(); i++) {
                    sb.append(" AND ");
                    sb.append(condiciones.get(i));
                }
            }
            sb.append(" ORDER BY idSucursal DESC ");
            System.out.println("SQL A EJECUTAR:--> " + sb.toString());
            System.out.println("PARAMETROS RECIBIDOS:-->" + request.toString());
            Query q = em.createNativeQuery(sb.toString());
            List<Object[]> lista = q.getResultList();
            if (lista.size() > 0) {
                for (Object[] item : lista) {
                    sucursales = new Sucursales();
                    sucursales.setIdsucursal(Integer.parseInt(item[0] != null ? item[0].toString() : "0"));
                    //sucursales.setCodigo(item[1] != null ? item[1].toString() : "N/D");
                    sucursales.setDescripcion(item[2] != null ? item[2].toString() : "N/D");
                    sucursales.setDireccion(item[3] != null ? item[3].toString() : "N/D");
                    sucursales.setTelefono(item[4] != null ? item[4].toString() : "N/D");
                    sucursales.setEncargado(item[5] != null ? item[5].toString() : "N/D");
                    //sucursales.setIdempresa(item[6] != null ? item[6].toString() : "N/D");
                    //sucursales.setActiva(item[7] != null ? item[7].toString() : "N/D");
                   // bodega.setEstadoBodega(item[8] != null ? Boolean.valueOf(item[8].toString()) : false);
                    response.add(sucursales);
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
    public List<Sucursales> loadSucursalByNombreLike(String likeNombre) throws DiservBusinessException {
         logger.log(Level.INFO, "[loadSucursalByNombreLike]Buscando:" + likeNombre);
        Query query;
        try {
            query = em.createQuery("Sucursales.findBySomeCriteria");
            query.setParameter("nombreSucursal", "%" + likeNombre + "%");
            return query.getResultList();
        } catch (Exception e) {
            logger.log(Level.ERROR, "[getDelito]Exception={0}", e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Sucursales loadSucursalByID(Integer idSucursal) throws DiservBusinessException {
         logger.log(Level.INFO, "[loadBodegaByID] idSucursal:" + idSucursal);
        Sucursales sucursal = null;
        Query query;
        try {
            query = em.createNamedQuery("Bodegas.findByIdBodega");
            query.setParameter("idSucursal", idSucursal);
            sucursal = (Sucursales) query.getSingleResult();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadSucursalByID][NoResultException]No se encontraron usuarios");
//            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron bodega");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadSucursalByID][Exception]Se mostro una excepcion al buscar sucursal");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return sucursal;
    }

    @Override
    public List<Sucursales> loadAllSucursalsByLike(String nombreLike) throws DiservBusinessException {
          logger.log(Level.INFO, "[loadAllSucursaleByLike] nombreLike:" + nombreLike);
        List<Sucursales> sucursaleList = null;
        Query query;
        try {
            query = em.createNamedQuery("Sucursales.findByNombreSucursaleLike");
            query.setParameter("nombreSucursale", "%" + nombreLike.toUpperCase() + "%");
            query.setMaxResults(Constants.REGISTROS_A_MOSTRAR_LISTA);
            sucursaleList = query.getResultList();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllSucursalesByLike][NoResultException]No se encontraron usuarios");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron sucursales");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllSucursalesByLike][Exception]Se mostro una excepcion al buscar sucursales");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return sucursaleList;
    }

   

}
