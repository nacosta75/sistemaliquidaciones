package sv.com.diserv.liquidaciones.dto;

import sv.com.diserv.liquidaciones.entity.Personas;

/**
 *
 * @author sonia.garcia
 */
public class OperacionesBodegaVendedorDTO extends BaseResponse {

    private Personas persona;

    public OperacionesBodegaVendedorDTO(Integer codigoRespuesta, String mensajeRespuesta) {
        super(codigoRespuesta, mensajeRespuesta);
    }

    public Personas getPersona() {
        return persona;
    }

    public void setPersona(Personas persona) {
        this.persona = persona;
    }

}
