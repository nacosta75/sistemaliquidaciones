/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.dto;

import sv.com.diserv.liquidaciones.entity.EncListaPrecio;

/**
 *
 * @author abraham.acosta
 */
public class OperacionesPrecioDTO extends BaseResponse{
    
    private EncListaPrecio listaPrecio;
    
    public OperacionesPrecioDTO(Integer codigoRespuesta, String mensajeRespuesta) {
        super(codigoRespuesta, mensajeRespuesta);
    }

    public EncListaPrecio getListaPrecio() {
        return listaPrecio;
    }

    public void setListaPrecio(EncListaPrecio listaPrecio) {
        this.listaPrecio = listaPrecio;
    }
    
    
    
    
}
