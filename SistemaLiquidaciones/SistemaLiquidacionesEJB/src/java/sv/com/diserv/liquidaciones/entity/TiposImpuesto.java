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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(name = "TIPOS_IMPUESTO", catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "TiposImpuesto.findAll", query = "SELECT t FROM TiposImpuesto t"),
    @NamedQuery(name = "TiposImpuesto.findByTipoImpuesto", query = "SELECT t FROM TiposImpuesto t WHERE t.tipoImpuesto = :tipoImpuesto"),
    @NamedQuery(name = "TiposImpuesto.findByDescripcion", query = "SELECT t FROM TiposImpuesto t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TiposImpuesto.findByValorImpuesto", query = "SELECT t FROM TiposImpuesto t WHERE t.valorImpuesto = :valorImpuesto"),
    @NamedQuery(name = "TiposImpuesto.findByBanEstado", query = "SELECT t FROM TiposImpuesto t WHERE t.banEstado = :banEstado"),
    @NamedQuery(name = "TiposImpuesto.findByFechaInactivacion", query = "SELECT t FROM TiposImpuesto t WHERE t.fechaInactivacion = :fechaInactivacion")})
public class TiposImpuesto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TIPO_IMPUESTO", nullable = false, length = 2)
    private String tipoImpuesto;
    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    private String descripcion;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "VALOR_IMPUESTO", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorImpuesto;
    @Basic(optional = false)
    @Column(name = "BAN_ESTADO", nullable = false, length = 1)
    private String banEstado;
    @Basic(optional = false)
    @Column(name = "FECHA_INACTIVACION", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaInactivacion;

    public TiposImpuesto() {
    }

    public TiposImpuesto(String tipoImpuesto) {
        this.tipoImpuesto = tipoImpuesto;
    }

    public TiposImpuesto(String tipoImpuesto, String descripcion, BigDecimal valorImpuesto, String banEstado, Date fechaInactivacion) {
        this.tipoImpuesto = tipoImpuesto;
        this.descripcion = descripcion;
        this.valorImpuesto = valorImpuesto;
        this.banEstado = banEstado;
        this.fechaInactivacion = fechaInactivacion;
    }

    public String getTipoImpuesto() {
        return tipoImpuesto;
    }

    public void setTipoImpuesto(String tipoImpuesto) {
        this.tipoImpuesto = tipoImpuesto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getValorImpuesto() {
        return valorImpuesto;
    }

    public void setValorImpuesto(BigDecimal valorImpuesto) {
        this.valorImpuesto = valorImpuesto;
    }

    public String getBanEstado() {
        return banEstado;
    }

    public void setBanEstado(String banEstado) {
        this.banEstado = banEstado;
    }

    public Date getFechaInactivacion() {
        return fechaInactivacion;
    }

    public void setFechaInactivacion(Date fechaInactivacion) {
        this.fechaInactivacion = fechaInactivacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoImpuesto != null ? tipoImpuesto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TiposImpuesto)) {
            return false;
        }
        TiposImpuesto other = (TiposImpuesto) object;
        if ((this.tipoImpuesto == null && other.tipoImpuesto != null) || (this.tipoImpuesto != null && !this.tipoImpuesto.equals(other.tipoImpuesto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.TiposImpuesto[ tipoImpuesto=" + tipoImpuesto + " ]";
    }
    
}
