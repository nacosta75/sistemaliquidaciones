package sv.com.diserv.liquidaciones.dto;

import sv.com.diserv.liquidaciones.entity.RelacionAsignaciones;

/**
 *
 * @author sonia.garcia
 */
public class OperacionesRelAsignacionDTO extends BaseResponse {

    private RelacionAsignaciones relacion;

    public OperacionesRelAsignacionDTO(Integer codigoRespuesta, String mensajeRespuesta) {
        super(codigoRespuesta, mensajeRespuesta);
    }

    /**
     * @return the relacion
     */
    public RelacionAsignaciones getRelacion() {
        return relacion;
    }

    /**
     * @param relacion the relacion to set
     */
    public void setRelacion(RelacionAsignaciones relacion) {
        this.relacion = relacion;
    }

    
}
