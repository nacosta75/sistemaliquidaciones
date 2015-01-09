package sv.com.diserv.liquidaciones.dto;

import sv.com.diserv.liquidaciones.entity.Movimientos;

/**
 *
 * @author sonia.garcia
 */
public class OperacionesMovimientoDTO extends BaseResponse {

    private Movimientos movimiento;

    public OperacionesMovimientoDTO(Integer codigoRespuesta, String mensajeRespuesta) {
        super(codigoRespuesta, mensajeRespuesta);
    }

    /**
     * @return the movimiento
     */
    public Movimientos getMovimiento() {
        return movimiento;
    }

    /**
     * @param movimiento the movimiento to set
     */
    public void setMovimiento(Movimientos movimiento) {
        this.movimiento = movimiento;
    }

   

}
