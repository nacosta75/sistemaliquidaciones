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
import java.util.Date;

/**
 *
 * @author edwin.alvarenga
 */
public class EncabezadoFacturaDTO implements Serializable {

    private String nombreCliente;
    private String ivaCliente;
    private String direccion;
    private String nitCliente;
    private String municipio;
    private String departamento;
    private String proveedor;
    private String numeroDm;
    private String referencia;
    private String aduana;
    private Double gravado;
    private Double totalIva;
    private Double sumasTotal;
    private Date fechaCreacion;
    private String formaPago;
    private String idOrdentrabajo;
    private Integer idFactura;
    private String cantidaEnletras;
    private String nombreUsuario;
    private Double totalFactura;
    private String tipoDocumento;

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getIvaCliente() {
        return ivaCliente;
    }

    public void setIvaCliente(String ivaCliente) {
        this.ivaCliente = ivaCliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNitCliente() {
        return nitCliente;
    }

    public void setNitCliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getNumeroDm() {
        return numeroDm;
    }

    public void setNumeroDm(String numeroDm) {
        this.numeroDm = numeroDm;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getAduana() {
        return aduana;
    }

    public void setAduana(String aduana) {
        this.aduana = aduana;
    }

    public Double getGravado() {
        return gravado;
    }

    public void setGravado(Double gravado) {
        this.gravado = gravado;
    }

    public Double getTotalIva() {
        return totalIva;
    }

    public void setTotalIva(Double totalIva) {
        this.totalIva = totalIva;
    }

    public Double getSumasTotal() {
        return sumasTotal;
    }

    public void setSumasTotal(Double sumasTotal) {
        this.sumasTotal = sumasTotal;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public String getIdOrdentrabajo() {
        return idOrdentrabajo;
    }

    public void setIdOrdentrabajo(String idOrdentrabajo) {
        this.idOrdentrabajo = idOrdentrabajo;
    }

    public Integer getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Integer idFactura) {
        this.idFactura = idFactura;
    }

    public String getCantidaEnletras() {
        return cantidaEnletras;
    }

    public void setCantidaEnletras(String cantidaEnletras) {
        this.cantidaEnletras = cantidaEnletras;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public Double getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(Double totalFactura) {
        this.totalFactura = totalFactura;
    }

    @Override
    public String toString() {
        return "EncabezadoFacturaDTO{" + "nombreCliente=" + nombreCliente + ", ivaCliente=" + ivaCliente + ", direccion=" + direccion + ", nitCliente=" + nitCliente + ", municipio=" + municipio + ", departamento=" + departamento + ", proveedor=" + proveedor + ", numeroDm=" + numeroDm + ", referencia=" + referencia + ", aduana=" + aduana + ", gravado=" + gravado + ", totalIva=" + totalIva + ", sumasTotal=" + sumasTotal + ", fechaCreacion=" + fechaCreacion + ", formaPago=" + formaPago + ", idOrdentrabajo=" + idOrdentrabajo + ", idFactura=" + idFactura + ", cantidaEnletras=" + cantidaEnletras + ", nombreUsuario=" + nombreUsuario + ", totalFactura=" + totalFactura + '}';
    }
}
