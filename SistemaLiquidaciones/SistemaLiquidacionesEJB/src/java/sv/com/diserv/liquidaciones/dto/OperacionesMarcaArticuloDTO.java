/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.dto;

import sv.com.diserv.liquidaciones.entity.MarcaArticulo;

/**
 *
 * @author trompudo
 */
public class OperacionesMarcaArticuloDTO extends BaseResponse {
    
    private MarcaArticulo marcaArticulo;
    
    public OperacionesMarcaArticuloDTO(Integer codigoRespuesta, String mensajeRespuesta) {
        super(codigoRespuesta, mensajeRespuesta);
    }

    public MarcaArticulo getMarcaArticulo() {
        return marcaArticulo;
    }

    public void setMarcaArticulo(MarcaArticulo marcaArticulo) {
        this.marcaArticulo = marcaArticulo;
    }
    
    
    
}
