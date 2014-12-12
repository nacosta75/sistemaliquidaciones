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
public class BusquedaSucursalDTO {
    
   
    private Integer Idsucursal;
    private String descripcion;
    private String direccion;
    private String telefono;
    private String encargado;
    private Empresas idempresa;

    public Integer getIdsucursal() {
        return Idsucursal;
    }

    public void setIdsucursal(Integer Idsucursal) {
        this.Idsucursal = Idsucursal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public Empresas getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(Empresas idempresa) {
        this.idempresa = idempresa;
    }
    
    
    
}
