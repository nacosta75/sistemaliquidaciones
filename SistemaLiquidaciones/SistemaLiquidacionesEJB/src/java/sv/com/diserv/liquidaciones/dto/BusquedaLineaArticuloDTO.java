/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.dto;

import sv.com.diserv.liquidaciones.entity.Empresas;

/**
 *
 * @author trompudo
 */
public class BusquedaLineaArticuloDTO {
    
    private Integer idlinea;
    private String activa;
    private String desclinea;
    private Empresas idempresa;

    public Integer getIdlinea() {
        return idlinea;
    }

    public void setIdlinea(Integer idlinea) {
        this.idlinea = idlinea;
    }

    public String getActiva() {
        return activa;
    }

    public void setActiva(String activa) {
        this.activa = activa;
    }

    public String getDesclinea() {
        return desclinea;
    }

    public void setDesclinea(String desclinea) {
        this.desclinea = desclinea;
    }

    public Empresas getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(Empresas idempresa) {
        this.idempresa = idempresa;
    }
    
    
    
}
