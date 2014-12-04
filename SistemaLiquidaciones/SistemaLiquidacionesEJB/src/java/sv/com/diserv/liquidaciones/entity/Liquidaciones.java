/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "Liquidaciones.findAll", query = "SELECT l FROM Liquidaciones l"),
    @NamedQuery(name = "Liquidaciones.findByCorrMov", query = "SELECT l FROM Liquidaciones l WHERE l.corrMov = :corrMov"),
    @NamedQuery(name = "Liquidaciones.findByCodBancoEmisor", query = "SELECT l FROM Liquidaciones l WHERE l.codBancoEmisor = :codBancoEmisor"),
    @NamedQuery(name = "Liquidaciones.findByNoRemesa", query = "SELECT l FROM Liquidaciones l WHERE l.noRemesa = :noRemesa"),
    @NamedQuery(name = "Liquidaciones.findByValor", query = "SELECT l FROM Liquidaciones l WHERE l.valor = :valor"),
    @NamedQuery(name = "Liquidaciones.findByObservaciones", query = "SELECT l FROM Liquidaciones l WHERE l.observaciones = :observaciones")})
public class Liquidaciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CORR_MOV", nullable = false)
    private Integer corrMov;
    @Basic(optional = false)
    @Column(name = "COD_BANCO_EMISOR", nullable = false)
    private int codBancoEmisor;
    @Basic(optional = false)
    @Column(name = "NO_REMESA", nullable = false, length = 20)
    private String noRemesa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal valor;
    @Column(length = 200)
    private String observaciones;

    public Liquidaciones() {
    }

    public Liquidaciones(Integer corrMov) {
        this.corrMov = corrMov;
    }

    public Liquidaciones(Integer corrMov, int codBancoEmisor, String noRemesa, BigDecimal valor) {
        this.corrMov = corrMov;
        this.codBancoEmisor = codBancoEmisor;
        this.noRemesa = noRemesa;
        this.valor = valor;
    }

    public Integer getCorrMov() {
        return corrMov;
    }

    public void setCorrMov(Integer corrMov) {
        this.corrMov = corrMov;
    }

    public int getCodBancoEmisor() {
        return codBancoEmisor;
    }

    public void setCodBancoEmisor(int codBancoEmisor) {
        this.codBancoEmisor = codBancoEmisor;
    }

    public String getNoRemesa() {
        return noRemesa;
    }

    public void setNoRemesa(String noRemesa) {
        this.noRemesa = noRemesa;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
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
        if (!(object instanceof Liquidaciones)) {
            return false;
        }
        Liquidaciones other = (Liquidaciones) object;
        if ((this.corrMov == null && other.corrMov != null) || (this.corrMov != null && !this.corrMov.equals(other.corrMov))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.Liquidaciones[ corrMov=" + corrMov + " ]";
    }
    
}
