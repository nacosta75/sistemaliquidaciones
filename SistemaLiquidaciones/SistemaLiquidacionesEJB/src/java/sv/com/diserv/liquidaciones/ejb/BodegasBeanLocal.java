/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.Local;
import sv.com.diserv.liquidaciones.dto.BusquedaBodegaDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesBodegaDTO;
import sv.com.diserv.liquidaciones.entity.SucurBode;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

/**
 *
 * @author edwin.alvarenga
 */
@Local
public interface BodegasBeanLocal {

    /**
     * *
     *
     * @param inicio
     * @param fin
     * @return
     * @throws DiservBusinessException
     */
    public List<SucurBode> loadAllBodega(int inicio, int fin) throws DiservBusinessException;

    /**
     * *
     *
     * @return @throws DiservBusinessException
     */
    public Integer countAllBodegas() throws DiservBusinessException;

    /**
     * *
     *
     * @param bodega
     * @return
     * @throws DiservBusinessException
     */
    public OperacionesBodegaDTO guardarBodega(SucurBode bodega) throws DiservBusinessException;

    /**
     * *
     *
     * @param nombreLike
     * @return
     * @throws DiservBusinessException
     */
    public List<SucurBode> loadAllBodegasByLike(String nombreLike) throws DiservBusinessException;

    /**
     * *
     *
     * @param cliente
     * @return
     * @throws DiservBusinessException
     */
    public OperacionesBodegaDTO actualizarBodega(SucurBode cliente) throws DiservBusinessException;

    /**
     * *
     *
     * @param request
     * @return
     * @throws DiservBusinessException
     */
    public List<SucurBode> buscarBodegaByCriteria(BusquedaBodegaDTO request) throws DiservBusinessException;

    /**
     * *
     *
     * @param likeNombre
     * @return
     * @throws DiservBusinessException
     */
    public List<SucurBode> loadBodegaByNombreLike(String likeNombre) throws DiservBusinessException;

    /**
     * **
     *
     * @param idCliente
     * @return
     * @throws DiservBusinessException
     */
    public SucurBode loadBodegaByID(Integer idCliente) throws DiservBusinessException;
}
