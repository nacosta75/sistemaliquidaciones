/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE noe acosta  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * 
 * @author edwin alvarenga
 *  2014
 */
package sv.com.diserv.liquidaciones.dto;

import java.io.Serializable;
import sv.com.diserv.liquidaciones.entity.Sucursales;

/**
 *
 * @author edwin.alvarenga
 */
public class BusquedaBodegaDTO implements Serializable {
    
    
    private Integer idBodega;
    private String nombre;
    private String direccion;
    private String telefono;
    private String encargado;
    private String activa;
    private String codigo;
    private Sucursales sucursales;

    public Sucursales getSucursales() {
        return sucursales;
    }

    public void setSucursales(Sucursales sucursales) {
        this.sucursales = sucursales;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getIdBodega() {
        return idBodega;
    }

    public void setIdBodega(Integer idBodega) {
        this.idBodega = idBodega;
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

    public String getActiva() {
        return activa;
    }

    public void setActiva(String activa) {
        this.activa = activa;
    }
    
    
    
}
