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
import sv.com.diserv.liquidaciones.dto.BusquedaEmpresaDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesEmpresaDTO;
import static sv.com.diserv.liquidaciones.ejb.EmpresasBean.logger;
import sv.com.diserv.liquidaciones.entity.Empresas;
import sv.com.diserv.liquidaciones.entity.Empresas;
import sv.com.diserv.liquidaciones.entity.Empresas;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.util.Constants;

/**
 *
 * @author abraham.acosta
 */
@Stateless
public class EmpresasBean implements EmpresasBeanLocal {

    
    static final Logger logger = Logger.getLogger(EmpresasBean.class.getName());
    @PersistenceContext(unitName = "SistemaLiquidacionesEJBPU")
    
    private EntityManager em;
    
    
    @EJB
    GenericDaoServiceBeanLocal genericDaoBean;
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public List<Empresas> loadAllEmpresa(int inicio, int fin) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadAllEmpresa] desde:" + inicio + " hasta:" + fin);
        List<Empresas> empresasList = null;
        Query query;
        try {
            query = em.createNamedQuery("Empresaes.findAll");
            query.setFirstResult(inicio);
            query.setMaxResults(fin);
            empresasList = query.getResultList();
            if (empresasList != null) {
                logger.log(Level.INFO, "[loadAllEmpresa] Se encontraron " + empresasList.size() + " empresas");
            }
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllEmpresa][NoResultException]No se encontraron usuarios");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron Empresaes");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllEmpresa][Exception]Se mostro una excepcion al buscar Empresa");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return empresasList;
    }

    @Override
    public Integer countAllEmpresa() throws DiservBusinessException {
    
     logger.log(Level.INFO, "[countAllEmpresa]INIT");
        int count = 0;
        Query query;
        try {
         
            query = em.createQuery("SELECT count(s) FROM Empresas s ");
            count = ((Long) query.getSingleResult()).intValue();
            logger.log(Level.INFO, "[Total de registros encontrados]" + count);
        } catch (Exception e) {
            logger.log(Level.INFO, "[Excepcion en countAllEmpresa]" + e.toString());
            e.printStackTrace();
        }
        return count;

    
    }

    @Override
    public OperacionesEmpresaDTO guardarEmpresa(Empresas empresa) throws DiservBusinessException {
        OperacionesEmpresaDTO response = new OperacionesEmpresaDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo guardar empresa");
        try {
            empresa = genericDaoBean.create(empresa);
            response = new OperacionesEmpresaDTO(Constants.CODE_OPERACION_SATISFACTORIA, "empresa creada satisfactoriamente");
            response.setEpresas(empresa);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }

    @Override
    public List<Empresas> loadAllEmpresasByLike(String nombreLike) throws DiservBusinessException {
           logger.log(Level.INFO, "[loadAllSucursaleByLike] nombreLike:" + nombreLike);
        List<Empresas> empresasList = null;
        Query query;
        try {
            query = em.createNamedQuery("Empresas.findByNombre");
            query.setParameter("nombre", "%" + nombreLike.toUpperCase() + "%");
            query.setMaxResults(Constants.REGISTROS_A_MOSTRAR_LISTA);
            empresasList = query.getResultList();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllEmpresasByLike][NoResultException]No se encontraron empresas");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron empresas");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllEmpresasByLike][Exception]Se mostro una excepcion al buscar empresas");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return empresasList;
    }

    @Override
    public OperacionesEmpresaDTO actualizarEmpresa(Empresas empresa) throws DiservBusinessException {
         OperacionesEmpresaDTO response = new OperacionesEmpresaDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo guardar empresa");
        try {
            empresa = genericDaoBean.create(empresa);
            response = new OperacionesEmpresaDTO(Constants.CODE_OPERACION_SATISFACTORIA, "empresa creada satisfactoriamente");
            response.setEpresas(empresa);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }

    @Override
    public List<Empresas> buscarEmpresaByCriteria(BusquedaEmpresaDTO request) throws DiservBusinessException {
        logger.info("[consultarBitacoraFinalizadasParametros]Parametros=" + request.toString());
        List<Empresas> response = new ArrayList<>();
        Empresas empresas;
        List<String> condiciones = new ArrayList<>();
        if (request.getIdempresa()!= null) {
            condiciones.add(" UPPER(idEmpresa) LIKE UPPER('%" + request.getIdempresa() + "%') ");
        }
      
        if (request.getNombre() != null) {
            condiciones.add(" UPPER(nombre) LIKE UPPER('%" + request.getNombre() + "%') ");
        }
        if (request.getDireccion() != null) {
            condiciones.add(" UPPER(direccion) LIKE UPPER('%" + request.getDireccion() + "%') ");
        }
        if (request.getTelefono() != null) {
            condiciones.add(" UPPER(telefono) LIKE UPPER('%" + request.getTelefono() + "%') ");
        }
        if (request.getRegistro()!= null) {
            condiciones.add(" UPPER(registro) LIKE UPPER('%" + request.getRegistro() + "%') ");
        }
        if (request.getNit() != null) {
            condiciones.add(" UPPER(nit) LIKE UPPER('%" + request.getNit() + "%') ");
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM Empresas ");
            if (!condiciones.isEmpty()) {
                sb.append(" WHERE ");
                sb.append(condiciones.get(0));
                for (int i = 1; i < condiciones.size(); i++) {
                    sb.append(" AND ");
                    sb.append(condiciones.get(i));
                }
            }
            sb.append(" ORDER BY idEmpresa DESC ");
            System.out.println("SQL A EJECUTAR:--> " + sb.toString());
            System.out.println("PARAMETROS RECIBIDOS:-->" + request.toString());
            Query q = em.createNativeQuery(sb.toString());
            List<Object[]> lista = q.getResultList();
            if (lista.size() > 0) {
                for (Object[] item : lista) {
                    empresas = new Empresas();
                    empresas.setIdempresa(Integer.parseInt(item[0] != null ? item[0].toString() : "0"));
                    empresas.setCodigo(item[1] != null ? item[1].toString() : "N/D");
                    empresas.setNombre(item[2] != null ? item[2].toString() : "N/D");
                    empresas.setDireccion(item[3] != null ? item[3].toString() : "N/D");
                    empresas.setNit(item[4] != null ? item[4].toString() : "N/D");
                    empresas.setCiudad(item[5] != null ? item[5].toString() : "N/D");
                    empresas.setPais(item[6] != null ? item[6].toString() : "N/D");
                    empresas.setEmail(item[7] != null ? item[7].toString() : "N/D");
                    //empresas.setActiva(item[7] != null ? item[7].toString() : "N/D");
                    response.add(empresas);
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
    public List<Empresas> loadEmpresaByNombreLike(String likeNombre) throws DiservBusinessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Empresas loadEmpresaByID(Integer idEmpresa) throws DiservBusinessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
