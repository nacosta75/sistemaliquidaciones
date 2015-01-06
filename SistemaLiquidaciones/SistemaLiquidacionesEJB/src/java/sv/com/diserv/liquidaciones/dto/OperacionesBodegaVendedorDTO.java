package sv.com.diserv.liquidaciones.dto;

import sv.com.diserv.liquidaciones.entity.BodegaVendedor;
/**
 *
 * @author sonia.garcia
 */
public class OperacionesBodegaVendedorDTO extends BaseResponse {

    private BodegaVendedor bodegaVendedor;

    public OperacionesBodegaVendedorDTO(Integer codigoRespuesta, String mensajeRespuesta) {
        super(codigoRespuesta, mensajeRespuesta);
    }

    /**
     * @return the bodegaVendedor
     */
    public BodegaVendedor getBodegaVendedor() {
        return bodegaVendedor;
    }

    /**
     * @param bodegaVendedor the bodegaVendedor to set
     */
    public void setBodegaVendedor(BodegaVendedor bodegaVendedor) {
        this.bodegaVendedor = bodegaVendedor;
    }

   
}
