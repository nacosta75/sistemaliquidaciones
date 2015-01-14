package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.Local;
import sv.com.diserv.liquidaciones.dto.OperacionesMovimientoDetDTO;
import sv.com.diserv.liquidaciones.entity.MovimientosDet;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

/**
 *
 * @author sonia.garcia
 */
@Local
public interface MovimientosDetBeanLocal {

    public List<MovimientosDet> loadAllMovimientosDet(int inicio, int fin) throws DiservBusinessException;
    public Integer countAllMovimientosDet(int tipoMovimiento) throws DiservBusinessException;
    public OperacionesMovimientoDetDTO guardarMovimientoDet(MovimientosDet movimiento) throws DiservBusinessException;
    public OperacionesMovimientoDetDTO actualizarMovimientoDet(MovimientosDet movimiento) throws DiservBusinessException;
    public OperacionesMovimientoDetDTO eliminarMovimientoDet(MovimientosDet movimiento) throws DiservBusinessException;
    
}
