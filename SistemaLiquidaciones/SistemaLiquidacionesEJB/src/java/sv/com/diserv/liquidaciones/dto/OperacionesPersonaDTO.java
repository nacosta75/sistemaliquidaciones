package sv.com.diserv.liquidaciones.dto;

import sv.com.diserv.liquidaciones.entity.Personas;

/**
 *
 * @author sonia.garcia
 */
public class OperacionesPersonaDTO extends BaseResponse {

    private Personas persona;

    public OperacionesPersonaDTO(Integer codigoRespuesta, String mensajeRespuesta) {
        super(codigoRespuesta, mensajeRespuesta);
    }

    public Personas getPersona() {
        return persona;
    }

    public void setPersona(Personas persona) {
        this.persona = persona;
    }

}
