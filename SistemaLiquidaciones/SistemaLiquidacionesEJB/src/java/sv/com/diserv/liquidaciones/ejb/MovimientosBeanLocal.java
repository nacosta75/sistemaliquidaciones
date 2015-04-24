/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.Local;
import sv.com.diserv.liquidaciones.dto.BusquedaMovimientoDTO;
import sv.com.diserv.liquidaciones.dto.BusquedaPersonaDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesMovimientoDTO;
import sv.com.diserv.liquidaciones.entity.Movimientos;
import sv.com.diserv.liquidaciones.entity.MovimientosDet;
import sv.com.diserv.liquidaciones.entity.Personas;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

/**
 *
 * @author sonia.garcia
 */
@Local
public interface MovimientosBeanLocal {

    public List<Movimientos> loadAllMovimientos(int inicio, int fin, int tipo) throws DiservBusinessException;
    public Integer countAllMovimientos(int tipoMovimiento) throws DiservBusinessException;
    public OperacionesMovimientoDTO guardarMovimiento(Movimientos movimiento) throws DiservBusinessException;
    public OperacionesMovimientoDTO actualizarMovimiento(Movimientos movimiento) throws DiservBusinessException;
    public OperacionesMovimientoDTO eliminarMovimiento(Movimientos movimiento) throws DiservBusinessException;
    public Integer maxNumDocByVendedorAndTipoMov(int idVendedor, int tipoMov) throws DiservBusinessException;
    public List<MovimientosDet> loadDetalleMovimientoByIdMovimento(Integer codigoMovimiento) throws DiservBusinessException;
    public Movimientos findMovimientoById( Integer idMovimiento) throws DiservBusinessException;
    public List<Movimientos> buscarMovimientoByCriteria(BusquedaMovimientoDTO input) throws DiservBusinessException;
}
