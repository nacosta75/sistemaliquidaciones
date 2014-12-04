/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "Movimientos.findAll", query = "SELECT m FROM Movimientos m"),
    @NamedQuery(name = "Movimientos.findByCorrMov", query = "SELECT m FROM Movimientos m WHERE m.corrMov = :corrMov"),
    @NamedQuery(name = "Movimientos.findByTipoMov", query = "SELECT m FROM Movimientos m WHERE m.tipoMov = :tipoMov"),
    @NamedQuery(name = "Movimientos.findByFechaMov", query = "SELECT m FROM Movimientos m WHERE m.fechaMov = :fechaMov"),
    @NamedQuery(name = "Movimientos.findByNoDoc", query = "SELECT m FROM Movimientos m WHERE m.noDoc = :noDoc"),
    @NamedQuery(name = "Movimientos.findByCorrPersona", query = "SELECT m FROM Movimientos m WHERE m.corrPersona = :corrPersona"),
    @NamedQuery(name = "Movimientos.findByCorrCaja", query = "SELECT m FROM Movimientos m WHERE m.corrCaja = :corrCaja"),
    @NamedQuery(name = "Movimientos.findByCorrSucSalida", query = "SELECT m FROM Movimientos m WHERE m.corrSucSalida = :corrSucSalida"),
    @NamedQuery(name = "Movimientos.findByCorrSucEntrada", query = "SELECT m FROM Movimientos m WHERE m.corrSucEntrada = :corrSucEntrada"),
    @NamedQuery(name = "Movimientos.findByBanTipoVenta", query = "SELECT m FROM Movimientos m WHERE m.banTipoVenta = :banTipoVenta"),
    @NamedQuery(name = "Movimientos.findByNombre", query = "SELECT m FROM Movimientos m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Movimientos.findByNoRegistro", query = "SELECT m FROM Movimientos m WHERE m.noRegistro = :noRegistro"),
    @NamedQuery(name = "Movimientos.findByTipoContribuyente", query = "SELECT m FROM Movimientos m WHERE m.tipoContribuyente = :tipoContribuyente"),
    @NamedQuery(name = "Movimientos.findByGiro", query = "SELECT m FROM Movimientos m WHERE m.giro = :giro"),
    @NamedQuery(name = "Movimientos.findByCalleOPasaje", query = "SELECT m FROM Movimientos m WHERE m.calleOPasaje = :calleOPasaje"),
    @NamedQuery(name = "Movimientos.findByColonia", query = "SELECT m FROM Movimientos m WHERE m.colonia = :colonia"),
    @NamedQuery(name = "Movimientos.findByCorrDepto", query = "SELECT m FROM Movimientos m WHERE m.corrDepto = :corrDepto"),
    @NamedQuery(name = "Movimientos.findByNoTarjeta", query = "SELECT m FROM Movimientos m WHERE m.noTarjeta = :noTarjeta"),
    @NamedQuery(name = "Movimientos.findByNoVoucher", query = "SELECT m FROM Movimientos m WHERE m.noVoucher = :noVoucher"),
    @NamedQuery(name = "Movimientos.findByObserva1", query = "SELECT m FROM Movimientos m WHERE m.observa1 = :observa1"),
    @NamedQuery(name = "Movimientos.findByObserva2", query = "SELECT m FROM Movimientos m WHERE m.observa2 = :observa2"),
    @NamedQuery(name = "Movimientos.findByBanEstado", query = "SELECT m FROM Movimientos m WHERE m.banEstado = :banEstado")})
public class Movimientos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CORR_MOV", nullable = false)
    private Integer corrMov;
    @Basic(optional = false)
    @Column(name = "TIPO_MOV", nullable = false, length = 2)
    private String tipoMov;
    @Basic(optional = false)
    @Column(name = "FECHA_MOV", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaMov;
    @Basic(optional = false)
    @Column(name = "NO_DOC", nullable = false)
    private int noDoc;
    @Column(name = "CORR_PERSONA")
    private Integer corrPersona;
    @Column(name = "CORR_CAJA")
    private Integer corrCaja;
    @Column(name = "CORR_SUC_SALIDA")
    private Integer corrSucSalida;
    @Column(name = "CORR_SUC_ENTRADA")
    private Integer corrSucEntrada;
    @Column(name = "BAN_TIPO_VENTA", length = 1)
    private String banTipoVenta;
    @Column(length = 80)
    private String nombre;
    @Column(name = "NO_REGISTRO", length = 20)
    private String noRegistro;
    @Column(name = "TIPO_CONTRIBUYENTE", length = 1)
    private String tipoContribuyente;
    @Column(length = 200)
    private String giro;
    @Column(name = "CALLE_O_PASAJE", length = 100)
    private String calleOPasaje;
    @Column(length = 80)
    private String colonia;
    @Column(name = "CORR_DEPTO")
    private Integer corrDepto;
    @Column(name = "NO_TARJETA", length = 30)
    private String noTarjeta;
    @Column(name = "NO_VOUCHER", length = 30)
    private String noVoucher;
    @Column(length = 100)
    private String observa1;
    @Column(length = 100)
    private String observa2;
    @Column(name = "BAN_ESTADO", length = 1)
    private String banEstado;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "movimientos")
    private Detalle detalle;

    public Movimientos() {
    }

    public Movimientos(Integer corrMov) {
        this.corrMov = corrMov;
    }

    public Movimientos(Integer corrMov, String tipoMov, Date fechaMov, int noDoc) {
        this.corrMov = corrMov;
        this.tipoMov = tipoMov;
        this.fechaMov = fechaMov;
        this.noDoc = noDoc;
    }

    public Integer getCorrMov() {
        return corrMov;
    }

    public void setCorrMov(Integer corrMov) {
        this.corrMov = corrMov;
    }

    public String getTipoMov() {
        return tipoMov;
    }

    public void setTipoMov(String tipoMov) {
        this.tipoMov = tipoMov;
    }

    public Date getFechaMov() {
        return fechaMov;
    }

    public void setFechaMov(Date fechaMov) {
        this.fechaMov = fechaMov;
    }

    public int getNoDoc() {
        return noDoc;
    }

    public void setNoDoc(int noDoc) {
        this.noDoc = noDoc;
    }

    public Integer getCorrPersona() {
        return corrPersona;
    }

    public void setCorrPersona(Integer corrPersona) {
        this.corrPersona = corrPersona;
    }

    public Integer getCorrCaja() {
        return corrCaja;
    }

    public void setCorrCaja(Integer corrCaja) {
        this.corrCaja = corrCaja;
    }

    public Integer getCorrSucSalida() {
        return corrSucSalida;
    }

    public void setCorrSucSalida(Integer corrSucSalida) {
        this.corrSucSalida = corrSucSalida;
    }

    public Integer getCorrSucEntrada() {
        return corrSucEntrada;
    }

    public void setCorrSucEntrada(Integer corrSucEntrada) {
        this.corrSucEntrada = corrSucEntrada;
    }

    public String getBanTipoVenta() {
        return banTipoVenta;
    }

    public void setBanTipoVenta(String banTipoVenta) {
        this.banTipoVenta = banTipoVenta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNoRegistro() {
        return noRegistro;
    }

    public void setNoRegistro(String noRegistro) {
        this.noRegistro = noRegistro;
    }

    public String getTipoContribuyente() {
        return tipoContribuyente;
    }

    public void setTipoContribuyente(String tipoContribuyente) {
        this.tipoContribuyente = tipoContribuyente;
    }

    public String getGiro() {
        return giro;
    }

    public void setGiro(String giro) {
        this.giro = giro;
    }

    public String getCalleOPasaje() {
        return calleOPasaje;
    }

    public void setCalleOPasaje(String calleOPasaje) {
        this.calleOPasaje = calleOPasaje;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public Integer getCorrDepto() {
        return corrDepto;
    }

    public void setCorrDepto(Integer corrDepto) {
        this.corrDepto = corrDepto;
    }

    public String getNoTarjeta() {
        return noTarjeta;
    }

    public void setNoTarjeta(String noTarjeta) {
        this.noTarjeta = noTarjeta;
    }

    public String getNoVoucher() {
        return noVoucher;
    }

    public void setNoVoucher(String noVoucher) {
        this.noVoucher = noVoucher;
    }

    public String getObserva1() {
        return observa1;
    }

    public void setObserva1(String observa1) {
        this.observa1 = observa1;
    }

    public String getObserva2() {
        return observa2;
    }

    public void setObserva2(String observa2) {
        this.observa2 = observa2;
    }

    public String getBanEstado() {
        return banEstado;
    }

    public void setBanEstado(String banEstado) {
        this.banEstado = banEstado;
    }

    public Detalle getDetalle() {
        return detalle;
    }

    public void setDetalle(Detalle detalle) {
        this.detalle = detalle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (corrMov != null ? corrMov.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movimientos)) {
            return false;
        }
        Movimientos other = (Movimientos) object;
        if ((this.corrMov == null && other.corrMov != null) || (this.corrMov != null && !this.corrMov.equals(other.corrMov))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.Movimientos[ corrMov=" + corrMov + " ]";
    }
    
}
