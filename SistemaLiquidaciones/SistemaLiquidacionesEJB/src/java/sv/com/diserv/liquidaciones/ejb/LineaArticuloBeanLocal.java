/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.Local;
import sv.com.diserv.liquidaciones.dto.BusquedaLineaArticuloDTO;
import sv.com.diserv.liquidaciones.dto.CatalogoDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesLineaArticuloDTO;
import sv.com.diserv.liquidaciones.entity.LineaArticulo;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

/**
 *
 * @author abraham.acosta
 */
@Local
public interface LineaArticuloBeanLocal {

    public List<LineaArticulo> loadAllLineas() throws DiservBusinessException;
    
    public List<LineaArticulo> loadAllLineas(int inicio, int fin) throws DiservBusinessException;

    public Integer countAllLineaArticulo() throws DiservBusinessException;

    public OperacionesLineaArticuloDTO guardarLinea(LineaArticulo linea) throws DiservBusinessException;

    public List<LineaArticulo> loadAllLineaArticuloByLike(String nombreLike) throws DiservBusinessException;

    public OperacionesLineaArticuloDTO actualizarLinea(LineaArticulo linea) throws DiservBusinessException;

    public List<LineaArticulo> buscarLineaByCriteria(BusquedaLineaArticuloDTO request) throws DiservBusinessException;

    public List<LineaArticulo> loadLineaByDescripcionLike(String likeNombre) throws DiservBusinessException;

    public LineaArticulo loadLineaByID(Integer idLinea) throws DiservBusinessException;

    
}
