/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.dto;

import sv.com.diserv.liquidaciones.entity.Sucursales;

/**
 *
 * @author abraham.acosta
 */
public class OperacionesSucursalDTO extends BaseResponse {
    
    private Sucursales sucursales;
    
     public OperacionesSucursalDTO(Integer codigoRespuesta, String mensajeRespuesta) {
        super(codigoRespuesta, mensajeRespuesta);
    }

    public Sucursales getSucursales() {
        return sucursales;
    }

    public void setSucursales(Sucursales sucursales) {
        this.sucursales = sucursales;
    }
     
     
     
}
