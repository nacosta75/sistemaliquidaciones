/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.Local;
import sv.com.diserv.liquidaciones.dto.BusquedaMarcaArticuloDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesMarcaArticuloDTO;
import sv.com.diserv.liquidaciones.entity.MarcaArticulo;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

/**
 *
 * @author abraham.acosta
 */
@Local
public interface MarcaArticuloBeanLocal {
    
    public List<MarcaArticulo> loadAllMarcas() throws DiservBusinessException;
    public List<MarcaArticulo> loadAllMarcas(int inicio, int fin) throws DiservBusinessException;
    public Integer countAllMarcaArticulo() throws DiservBusinessException;
    public OperacionesMarcaArticuloDTO guardarMarca(MarcaArticulo linea) throws DiservBusinessException;
    public List<MarcaArticulo> loadAllMarcaArticuloByLike(String nombreLike) throws DiservBusinessException;
    public OperacionesMarcaArticuloDTO actualizarMarca(MarcaArticulo linea) throws DiservBusinessException;
    public List<MarcaArticulo> buscarMarcaByCriteria(BusquedaMarcaArticuloDTO request) throws DiservBusinessException;
    public List<MarcaArticulo> loadMarcaByDescripcionLike(String likeNombre) throws DiservBusinessException;
    public MarcaArticulo loadMarcaByID(Integer idMarca) throws DiservBusinessException;

    
}
