/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import sv.com.diserv.liquidaciones.dto.BusquedaMarcaArticuloDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesMarcaArticuloDTO;
import sv.com.diserv.liquidaciones.entity.MarcaArticulo;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

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
    public List<MarcaArticulo> loadAllMarcas(int inicio, int fin) throws DiservBusinessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer countAllMarcaArticulo() throws DiservBusinessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OperacionesMarcaArticuloDTO guardarLinea(MarcaArticulo linea) throws DiservBusinessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MarcaArticulo> loadAllMarcaArticuloByLike(String nombreLike) throws DiservBusinessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OperacionesMarcaArticuloDTO actualizarLinea(MarcaArticulo linea) throws DiservBusinessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MarcaArticulo> buscarLineaByCriteria(BusquedaMarcaArticuloDTO request) throws DiservBusinessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MarcaArticulo> loadLineaByDescripcionLike(String likeNombre) throws DiservBusinessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MarcaArticulo loadLineaByID(Integer idLinea) throws DiservBusinessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
