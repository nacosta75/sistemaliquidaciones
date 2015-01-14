/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.Local;
import sv.com.diserv.liquidaciones.dto.BusquedaLoteExistenciaDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesMovimientoDTO;
import sv.com.diserv.liquidaciones.entity.LotesExistencia;
import sv.com.diserv.liquidaciones.entity.Movimientos;
import sv.com.diserv.liquidaciones.entity.Personas;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

/**
 *
 * @author sonia.garcia
 */
@Local
public interface LotesExistenciasBeanLocal {

    public List<Movimientos> loadAllMovimientos(int inicio, int fin, int tipo) throws DiservBusinessException;

    /**
     * *
     *
     * @param tipoMovimiento
     * @return
     * @throws DiservBusinessException
     */
    public Integer countAllMovimientos(int tipoMovimiento) throws DiservBusinessException;

    /**
     * *
     *
     * @param movimiento
     * @return
     * @throws DiservBusinessException
     */
    public OperacionesMovimientoDTO guardarMovimiento(Movimientos movimiento) throws DiservBusinessException;

    /**
     *
     * @param nombreLike
     * @return
     * @throws DiservBusinessException
     */
    public List<Personas> loadAllPersonasByLike(String nombreLike) throws DiservBusinessException;

    /**
     * *
     *
     * @param movimiento
     * @return
     * @throws DiservBusinessException
     */
    public OperacionesMovimientoDTO actualizarMovimiento(Movimientos movimiento) throws DiservBusinessException;

    /**
     * *
     *
     * @param movimiento
     * @return
     * @throws DiservBusinessException
     */
    public OperacionesMovimientoDTO eliminarMovimiento(Movimientos movimiento) throws DiservBusinessException;

    /**
     * *
     *
     * @param re
     * @return
     * @throws DiservBusinessException
     */
    public List<LotesExistencia> buscarLoteByCriteria(BusquedaLoteExistenciaDTO re) throws DiservBusinessException;

    /**
     * *
     *
     * @param idVendedor
     * @param tipoMov
     * @return
     * @throws DiservBusinessException
     */
    public Integer maxNumDocByVendedorAndTipoMov(int idVendedor, int tipoMov) throws DiservBusinessException;
}
