package sv.com.diserv.liquidaciones.dto;

import sv.com.diserv.liquidaciones.entity.Movimientos;
import sv.com.diserv.liquidaciones.entity.MovimientosDet;

/**
 *
 * @author sonia.garcia
 */
public class OperacionesMovimientoDetDTO extends BaseResponse {

    private MovimientosDet movimiento;

    public OperacionesMovimientoDetDTO(Integer codigoRespuesta, String mensajeRespuesta) {
        super(codigoRespuesta, mensajeRespuesta);
    }

    /**
     * @return the movimiento
     */
    public MovimientosDet getMovimiento() {
        return movimiento;
    }

    /**
     * @param movimiento the movimiento to set
     */
    public void setMovimiento(MovimientosDet movimiento) {
        this.movimiento = movimiento;
    }

   

   

}
