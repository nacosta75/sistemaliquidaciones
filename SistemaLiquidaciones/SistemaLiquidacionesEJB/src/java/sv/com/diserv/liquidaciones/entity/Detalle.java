/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "Detalle.findAll", query = "SELECT d FROM Detalle d"),
    @NamedQuery(name = "Detalle.findByCorrMov", query = "SELECT d FROM Detalle d WHERE d.corrMov = :corrMov"),
    @NamedQuery(name = "Detalle.findByCantAsignada", query = "SELECT d FROM Detalle d WHERE d.cantAsignada = :cantAsignada"),
    @NamedQuery(name = "Detalle.findByCantLiquidada", query = "SELECT d FROM Detalle d WHERE d.cantLiquidada = :cantLiquidada"),
    @NamedQuery(name = "Detalle.findByCantPendLiquidar", query = "SELECT d FROM Detalle d WHERE d.cantPendLiquidar = :cantPendLiquidar"),
    @NamedQuery(name = "Detalle.findByPrecio", query = "SELECT d FROM Detalle d WHERE d.precio = :precio"),
    @NamedQuery(name = "Detalle.findByValorImpuesto", query = "SELECT d FROM Detalle d WHERE d.valorImpuesto = :valorImpuesto"),
    @NamedQuery(name = "Detalle.findByTipoMov", query = "SELECT d FROM Detalle d WHERE d.tipoMov = :tipoMov"),
    @NamedQuery(name = "Detalle.findByFechaMov", query = "SELECT d FROM Detalle d WHERE d.fechaMov = :fechaMov"),
    @NamedQuery(name = "Detalle.findByNoDoc", query = "SELECT d FROM Detalle d WHERE d.noDoc = :noDoc"),
    @NamedQuery(name = "Detalle.findByUltCosto", query = "SELECT d FROM Detalle d WHERE d.ultCosto = :ultCosto")})
public class Detalle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CORR_MOV", nullable = false)
    private Integer corrMov;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "CANT_ASIGNADA", nullable = false, precision = 14, scale = 2)
    private BigDecimal cantAsignada;
    @Basic(optional = false)
    @Column(name = "CANT_LIQUIDADA", nullable = false, precision = 14, scale = 2)
    private BigDecimal cantLiquidada;
    @Basic(optional = false)
    @Column(name = "CANT_PEND_LIQUIDAR", nullable = false, precision = 14, scale = 2)
    private BigDecimal cantPendLiquidar;
    @Basic(optional = false)
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal precio;
    @Basic(optional = false)
    @Column(name = "VALOR_IMPUESTO", nullable = false, precision = 14, scale = 2)
    private BigDecimal valorImpuesto;
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
    @Basic(optional = false)
    @Column(name = "ULT_COSTO", nullable = false, precision = 14, scale = 4)
    private BigDecimal ultCosto;
    @JoinColumn(name = "CORR_MOV", referencedColumnName = "CORR_MOV", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Movimientos movimientos;
    @JoinColumn(name = "CORR_ARTICULO", referencedColumnName = "CORR_ARTICULO", nullable = false)
    @ManyToOne(optional = false)
    private Articulos corrArticulo;

    public Detalle() {
    }

    public Detalle(Integer corrMov) {
        this.corrMov = corrMov;
    }

    public Detalle(Integer corrMov, BigDecimal cantAsignada, BigDecimal cantLiquidada, BigDecimal cantPendLiquidar, BigDecimal precio, BigDecimal valorImpuesto, String tipoMov, Date fechaMov, int noDoc, BigDecimal ultCosto) {
        this.corrMov = corrMov;
        this.cantAsignada = cantAsignada;
        this.cantLiquidada = cantLiquidada;
        this.cantPendLiquidar = cantPendLiquidar;
        this.precio = precio;
        this.valorImpuesto = valorImpuesto;
        this.tipoMov = tipoMov;
        this.fechaMov = fechaMov;
        this.noDoc = noDoc;
        this.ultCosto = ultCosto;
    }

    public Integer getCorrMov() {
        return corrMov;
    }

    public void setCorrMov(Integer corrMov) {
        this.corrMov = corrMov;
    }

    public BigDecimal getCantAsignada() {
        return cantAsignada;
    }

    public void setCantAsignada(BigDecimal cantAsignada) {
        this.cantAsignada = cantAsignada;
    }

    public BigDecimal getCantLiquidada() {
        return cantLiquidada;
    }

    public void setCantLiquidada(BigDecimal cantLiquidada) {
        this.cantLiquidada = cantLiquidada;
    }

    public BigDecimal getCantPendLiquidar() {
        return cantPendLiquidar;
    }

    public void setCantPendLiquidar(BigDecimal cantPendLiquidar) {
        this.cantPendLiquidar = cantPendLiquidar;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getValorImpuesto() {
        return valorImpuesto;
    }

    public void setValorImpuesto(BigDecimal valorImpuesto) {
        this.valorImpuesto = valorImpuesto;
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

    public BigDecimal getUltCosto() {
        return ultCosto;
    }

    public void setUltCosto(BigDecimal ultCosto) {
        this.ultCosto = ultCosto;
    }

    public Movimientos getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(Movimientos movimientos) {
        this.movimientos = movimientos;
    }

    public Articulos getCorrArticulo() {
        return corrArticulo;
    }

    public void setCorrArticulo(Articulos corrArticulo) {
        this.corrArticulo = corrArticulo;
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
        if (!(object instanceof Detalle)) {
            return false;
        }
        Detalle other = (Detalle) object;
        if ((this.corrMov == null && other.corrMov != null) || (this.corrMov != null && !this.corrMov.equals(other.corrMov))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.Detalle[ corrMov=" + corrMov + " ]";
    }
    
}
