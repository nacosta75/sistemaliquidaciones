/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE FISCALIA GENERAL DE LA REPUBLICA  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author edwin.alvarenga@fgr.gob.sv
 * @author edwin alvarenga
 * Fiscalía General de la República 2014
 */
package sv.com.diserv.liquidaciones.dto;

import sv.com.diserv.liquidaciones.entity.SucurBode;

/**
 *
 * @author edwin.alvarenga
 */
public class OperacionesBodegaDTO extends BaseResponse {

    private SucurBode bodega;

    public OperacionesBodegaDTO(Integer codigoRespuesta, String mensajeRespuesta) {
        super(codigoRespuesta, mensajeRespuesta);
    }

    public SucurBode getBodega() {
        return bodega;
    }

    public void setBodega(SucurBode bodega) {
        this.bodega = bodega;
    }

}
