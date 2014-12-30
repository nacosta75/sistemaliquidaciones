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
    
    public List<MarcaArticulo> loadAllMarcas(int inicio, int fin) throws DiservBusinessException;
    public Integer countAllMarcaArticulo() throws DiservBusinessException;
    public OperacionesMarcaArticuloDTO guardarLinea(MarcaArticulo linea) throws DiservBusinessException;
    public List<MarcaArticulo> loadAllMarcaArticuloByLike(String nombreLike) throws DiservBusinessException;
    public OperacionesMarcaArticuloDTO actualizarLinea(MarcaArticulo linea) throws DiservBusinessException;
    public List<MarcaArticulo> buscarLineaByCriteria(BusquedaMarcaArticuloDTO request) throws DiservBusinessException;
    public List<MarcaArticulo> loadLineaByDescripcionLike(String likeNombre) throws DiservBusinessException;
    public MarcaArticulo loadLineaByID(Integer idLinea) throws DiservBusinessException;
}
