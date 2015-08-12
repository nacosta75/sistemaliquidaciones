package sv.com.diserv.liquidaciones.dto;

import sv.com.diserv.liquidaciones.entity.LotesExistencia;
import sv.com.diserv.liquidaciones.entity.Personas;

/**
 *
 * @author sonia.garcia
 */
public class OperacionesLotesExistenciasDTO extends BaseResponse {

    private LotesExistencia lotesExistencia;

    public OperacionesLotesExistenciasDTO(Integer codigoRespuesta, String mensajeRespuesta) {
        super(codigoRespuesta, mensajeRespuesta);
    }

    /**
     * @return the lotesExistencia
     */
    public LotesExistencia getLotesExistencia() {
        return lotesExistencia;
    }

    /**
     * @param lotesExistencia the lotesExistencia to set
     */
    public void setLotesExistencia(LotesExistencia lotesExistencia) {
        this.lotesExistencia = lotesExistencia;
    }

   

}
