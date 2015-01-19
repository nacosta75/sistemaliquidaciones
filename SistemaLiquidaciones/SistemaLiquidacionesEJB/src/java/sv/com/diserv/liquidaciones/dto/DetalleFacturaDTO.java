/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 *   Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * 
 * @author edwin alvarenga
 * 
 */
package sv.com.diserv.liquidaciones.dto;

import java.io.Serializable;

/**
 *
 * @author edwin.alvarenga
 */
public class DetalleFacturaDTO implements Serializable {

    private String idOrdentrabajo;
    private String descripcion;
    private Integer cantidad;
    private Double precioUnitario;
    private Double precioTotal;

    public String getIdOrdentrabajo() {
        return idOrdentrabajo;
    }

    public void setIdOrdentrabajo(String idOrdentrabajo) {
        this.idOrdentrabajo = idOrdentrabajo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    @Override
    public String toString() {
        return "DetalleFacturaDTO{" + "idOrdentrabajo=" + idOrdentrabajo + ", descripcion=" + descripcion + ", cantidad=" + cantidad + ", precioUnitario=" + precioUnitario + ", precioTotal=" + precioTotal + '}';
    }
}
