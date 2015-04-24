/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.dto;

import java.util.Date;

/**
 *
 * @author abraham.acosta
 */
public class BusquedaMovimientoDTO {
    
   private Integer idmov;
   private Date fecha;
   private Integer noDoc;
   private Integer idpersona;
   private Integer idtipomov;
   private Integer idsucursal;

    public Integer getIdmov() {
        return idmov;
    }

    public void setIdmov(Integer idmov) {
        this.idmov = idmov;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getNoDoc() {
        return noDoc;
    }

    public void setNoDoc(Integer noDoc) {
        this.noDoc = noDoc;
    }

    public Integer getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(Integer idpersona) {
        this.idpersona = idpersona;
    }

    public Integer getIdtipomov() {
        return idtipomov;
    }

    public void setIdtipomov(Integer idtipomov) {
        this.idtipomov = idtipomov;
    }

    public Integer getIdsucursal() {
        return idsucursal;
    }

    public void setIdsucursal(Integer idsucursal) {
        this.idsucursal = idsucursal;
    }
   
   
   
   
}
