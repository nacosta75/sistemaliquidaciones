/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.dto;

import sv.com.diserv.liquidaciones.entity.LineaArticulo;

/**
 *
 * @author trompudo
 */
public class OperacionesLineaArticuloDTO extends BaseResponse {
    
    private LineaArticulo lineaArticulo;
    
     public OperacionesLineaArticuloDTO(Integer codigoRespuesta, String mensajeRespuesta) {
        super(codigoRespuesta, mensajeRespuesta);
    }

    public LineaArticulo getLineaArticulo() {
        return lineaArticulo;
    }

    public void setLineaArticulo(LineaArticulo lineaArticulo) {
        this.lineaArticulo = lineaArticulo;
    }
     
     
    
    
    
}
