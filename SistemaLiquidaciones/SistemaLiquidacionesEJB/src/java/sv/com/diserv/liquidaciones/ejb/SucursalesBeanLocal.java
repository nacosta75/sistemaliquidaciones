/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import sv.com.diserv.liquidaciones.dto.BusquedaSucursalDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesSucursalDTO;
import sv.com.diserv.liquidaciones.entity.Sucursales;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

/**
 *
 * @author abraham.acosta
 */
public interface SucursalesBeanLocal {
    
     /**
     * *
     *
     * @param inicio
     * @param fin
     * @return
     * @throws DiservBusinessException
     */
    public List<Sucursales> loadAllSucursal(int inicio, int fin) throws DiservBusinessException;

    /**
     * *
     *
     * @return @throws DiservBusinessException
     */
    public Integer countAllSucursal() throws DiservBusinessException;

    /**
     * *
     *
     * @param sucursal
     * @return
     * @throws DiservBusinessException
     */
    public OperacionesSucursalDTO guardarSucursal(Sucursales sucursal) throws DiservBusinessException;

    /**
     * *
     *
     * @param nombreLike
     * @return
     * @throws DiservBusinessException
     */
    public List<Sucursales> loadAllSucursalsByLike(String nombreLike) throws DiservBusinessException;

    /**
     * *
     *
     * @param cliente
     * @return
     * @throws DiservBusinessException
     */
    public OperacionesSucursalDTO actualizarSucursal(Sucursales sucursal) throws DiservBusinessException;

    /**
     * *
     *
     * @param request
     * @return
     * @throws DiservBusinessException
     */
    public List<Sucursales> buscarSucursalByCriteria(BusquedaSucursalDTO request) throws DiservBusinessException;

    /**
     * *
     *
     * @param likeNombre
     * @return
     * @throws DiservBusinessException
     */
    public List<Sucursales> loadSucursalByNombreLike(String likeNombre) throws DiservBusinessException;

    /**
     * **
     *
     * @param idSucursal
     * @return
     * @throws DiservBusinessException
     */
    public Sucursales loadSucursalByID(Integer idSucursal) throws DiservBusinessException;

    
}
