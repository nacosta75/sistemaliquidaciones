/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.Stateless;
import sv.com.diserv.liquidaciones.dto.BusquedaArticuloDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesArticuloDTO;
import sv.com.diserv.liquidaciones.entity.Articulos;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

/**
 *
 * @author abraham.acosta
 */
@Stateless
public class ArticulosBean implements ArticulosBeanLocal {

    @Override
    public List<Articulos> loadAllMarcas(int inicio, int fin) throws DiservBusinessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer countAllMarcaArticulo() throws DiservBusinessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OperacionesArticuloDTO guardarMarca(Articulos articulo) throws DiservBusinessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Articulos> loadAllMarcaArticuloByLike(String nombreLike) throws DiservBusinessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OperacionesArticuloDTO actualizarMarca(Articulos articulo) throws DiservBusinessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Articulos> buscarMarcaByCriteria(BusquedaArticuloDTO request) throws DiservBusinessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Articulos> loadMarcaByDescripcionLike(String likeNombre) throws DiservBusinessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Articulos loadMarcaByID(Integer idArticulo) throws DiservBusinessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
