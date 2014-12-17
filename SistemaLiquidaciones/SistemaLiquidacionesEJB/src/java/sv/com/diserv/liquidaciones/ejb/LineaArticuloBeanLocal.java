/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.Local;
import sv.com.diserv.liquidaciones.dto.BusquedaLineaArticuloDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesLineaArticuloDTO;
import sv.com.diserv.liquidaciones.entity.LineaArticulo;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

/**
 *
 * @author trompudo
 */
@Local
public interface LineaArticuloBeanLocal {
    
     /**
     * *
     *
     * @param inicio
     * @param fin
     * @return
     * @throws DiservBusinessException
     */
    public List<LineaArticulo> loadAllLineas(int inicio, int fin) throws DiservBusinessException;

    /**
     * *
     *
     * @return @throws DiservBusinessException
     */
    public Integer countAllLineaArticulo() throws DiservBusinessException;

    /**
     * *
     *
     * @param linea
     * @return
     * @throws DiservBusinessException
     */
    public OperacionesLineaArticuloDTO guardarLinea(LineaArticulo linea) throws DiservBusinessException;

    /**
     * *
     *
     * @param nombreLike
     * @return
     * @throws DiservBusinessException
     */
    public List<LineaArticulo> loadAllLineaArticuloByLike(String nombreLike) throws DiservBusinessException;

    /**
     * *
     *
     * @param linea
     * @return
     * @throws DiservBusinessException
     */
    public OperacionesLineaArticuloDTO actualizarLinea(LineaArticulo linea) throws DiservBusinessException;

    /**
     * *
     *
     * @param request
     * @return
     * @throws DiservBusinessException
     */
    public List<LineaArticulo> buscarLineaByCriteria(BusquedaLineaArticuloDTO request) throws DiservBusinessException;

    /**
     * *
     *
     * @param likeNombre
     * @return
     * @throws DiservBusinessException
     */
    public List<LineaArticulo> loadLineaByDescripcionLike(String likeNombre) throws DiservBusinessException;

    /**
     * **
     *
     * @param idLinea
     * @return
     * @throws DiservBusinessException
     */
    public LineaArticulo loadLineaByID(Integer idLinea) throws DiservBusinessException;
}
