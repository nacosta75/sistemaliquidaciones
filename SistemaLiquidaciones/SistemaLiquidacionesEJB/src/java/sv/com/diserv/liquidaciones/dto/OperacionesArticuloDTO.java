/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.dto;

import sv.com.diserv.liquidaciones.entity.Articulos;

/**
 *
 * @author abraham.acosta
 */
public class OperacionesArticuloDTO extends BaseResponse {
    
    private Articulos articulo;
    
    public OperacionesArticuloDTO(Integer codigoRespuesta, String mensajeRespuesta) {
        super(codigoRespuesta, mensajeRespuesta);
    }

    public Articulos getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulos articulo) {
        this.articulo = articulo;
    }
    
    
    
}
