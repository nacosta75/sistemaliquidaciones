/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.dto;

import sv.com.diserv.liquidaciones.entity.Empresas;

/**
 *
 * @author abraham.acosta
 */
public class OperacionesEmpresaDTO extends BaseResponse {
    
    private Empresas epresas;

    public Empresas getEpresas() {
        return epresas;
    }

    public void setEpresas(Empresas epresas) {
        this.epresas = epresas;
    }
    
    public OperacionesEmpresaDTO(Integer codigoRespuesta, String mensajeRespuesta) {
        super(codigoRespuesta, mensajeRespuesta);
    }
    
}
