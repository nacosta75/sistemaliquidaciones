/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.dto;

import sv.com.diserv.liquidaciones.entity.DetListaPrecio;
import sv.com.diserv.liquidaciones.entity.EncListaPrecio;

/**
 *
 * @author abraham.acosta
 */
public class OperacionesPreciosDTO extends BaseResponse{
    
    EncListaPrecio encListaPrecio;
    DetListaPrecio detListaPrecio;

     public OperacionesPreciosDTO(Integer codigoRespuesta, String mensajeRespuesta) {
        super(codigoRespuesta, mensajeRespuesta);
    }
     
    public EncListaPrecio getEncListaPrecio() {
        return encListaPrecio;
    }

    public void setEncListaPrecio(EncListaPrecio encListaPrecio) {
        this.encListaPrecio = encListaPrecio;
    }

    public DetListaPrecio getDetListaPrecio() {
        return detListaPrecio;
    }

    public void setDetListaPrecio(DetListaPrecio detListaPrecio) {
        this.detListaPrecio = detListaPrecio;
    }
    
    
}
