/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import sv.com.diserv.liquidaciones.entity.Deptopais;

/**
 *
 * @author abraham.acosta
 */
public class BusquedaEmpresaDTO implements Serializable{
    
    
     private Integer idempresa;
     private String codigo;
     private String nombre;
     private String direccion;
     private String ciudad;
     private String idmuni;
     private String pais;
     private String telefono;
     private String registro;
     private String nit;
     private String email;
     private String web;
     private String giro;
     private Character activa;
     private BigDecimal iva;
     private BigDecimal renta;
     private BigDecimal retencion;
     private Deptopais iddepto;

    public Integer getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(Integer idempresa) {
        this.idempresa = idempresa;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getIdmuni() {
        return idmuni;
    }

    public void setIdmuni(String idmuni) {
        this.idmuni = idmuni;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getGiro() {
        return giro;
    }

    public void setGiro(String giro) {
        this.giro = giro;
    }

    public Character getActiva() {
        return activa;
    }

    public void setActiva(Character activa) {
        this.activa = activa;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getRenta() {
        return renta;
    }

    public void setRenta(BigDecimal renta) {
        this.renta = renta;
    }

    public BigDecimal getRetencion() {
        return retencion;
    }

    public void setRetencion(BigDecimal retencion) {
        this.retencion = retencion;
    }

    public Deptopais getIddepto() {
        return iddepto;
    }

    public void setIddepto(Deptopais iddepto) {
        this.iddepto = iddepto;
    }
     
     
     
     
    
}
