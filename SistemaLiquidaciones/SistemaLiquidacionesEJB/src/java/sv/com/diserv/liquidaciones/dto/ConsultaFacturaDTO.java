/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 *   Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * 
 * @author edwin alvarenga
 * 
 */
package sv.com.diserv.liquidaciones.dto;

import java.util.List;

/**
 *
 * @author edwin.alvarenga
 */
public class ConsultaFacturaDTO extends BaseResponse {

    private EncabezadoFacturaDTO documentoCliente;
    private List<DetalleFacturaDTO> listaDetalle;

    public ConsultaFacturaDTO(Integer codigoRespuesta, String mensajeRespuesta) {
        super(codigoRespuesta, mensajeRespuesta);
    }

    public EncabezadoFacturaDTO getDocumentoCliente() {
        return documentoCliente;
    }

    public void setDocumentoCliente(EncabezadoFacturaDTO documentoCliente) {
        this.documentoCliente = documentoCliente;
    }

    public List<DetalleFacturaDTO> getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<DetalleFacturaDTO> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    @Override
    public String toString() {
        return "ConsultaFacturaDTO{" + "documentoCliente=" + documentoCliente + ", listaDetalle=" + listaDetalle + '}';
    }
}
