/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.Local;
import sv.com.diserv.liquidaciones.dto.BusquedaArticuloDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesArticuloDTO;
import sv.com.diserv.liquidaciones.entity.Articulos;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

/**
 *
 * @author abraham.acosta
 */
@Local
public interface ArticulosBeanLocal {
    
    public List<Articulos> loadAllMarcas(int inicio, int fin) throws DiservBusinessException;
    public Integer countAllMarcaArticulo() throws DiservBusinessException;
    public OperacionesArticuloDTO guardarMarca(Articulos articulo) throws DiservBusinessException;
    public List<Articulos> loadAllMarcaArticuloByLike(String nombreLike) throws DiservBusinessException;
    public OperacionesArticuloDTO actualizarMarca(Articulos articulo) throws DiservBusinessException;
    public List<Articulos> buscarMarcaByCriteria(BusquedaArticuloDTO request) throws DiservBusinessException;
    public List<Articulos> loadMarcaByDescripcionLike(String likeNombre) throws DiservBusinessException;
    public Articulos loadMarcaByID(Integer idArticulo) throws DiservBusinessException;
}