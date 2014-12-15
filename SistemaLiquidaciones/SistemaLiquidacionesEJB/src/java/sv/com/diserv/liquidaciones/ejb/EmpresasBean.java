/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import sv.com.diserv.liquidaciones.dto.BusquedaEmpresaDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesEmpresaDTO;
import static sv.com.diserv.liquidaciones.ejb.EmpresasBean.logger;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
