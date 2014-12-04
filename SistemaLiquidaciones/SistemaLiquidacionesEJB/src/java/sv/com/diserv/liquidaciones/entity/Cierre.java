/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
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
import javax.persistence.UniqueConstraint;

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(catalog = "", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"CORR_CORRELATIVO"})})
@NamedQueries({
    @NamedQuery(name = "Cierre.findAll", query = "SELECT c FROM Cierre c"),
    @NamedQuery(name = "Cierre.findByCorrCorrelativo", query = "SELECT c FROM Cierre c WHERE c.corrCorrelativo = :corrCorrelativo"),
    @NamedQuery(name = "Cierre.findByFechaOperacion", query = "SELECT c FROM Cierre c WHERE c.fechaOperacion = :fechaOperacion"),
    @NamedQuery(name = "Cierre.findByCorrInicial", query = "SELECT c FROM Cierre c WHERE c.corrInicial = :corrInicial"),
    @NamedQuery(name = "Cierre.findByCorrFinal", query = "SELECT c FROM Cierre c WHERE c.corrFinal = :corrFinal"),
    @NamedQuery(name = "Cierre.findByBanEstado", query = "SELECT c FROM Cierre c WHERE c.banEstado = :banEstado")})
public class Cierre implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CORR_CORRELATIVO", nullable = false)
    private Integer corrCorrelativo;
    @Basic(optional = false)
    @Column(name = "FECHA_OPERACION", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaOperacion;
    @Basic(optional = false)
    @Column(name = "CORR_INICIAL", nullable = false)
    private int corrInicial;
    @Basic(optional = false)
    @Column(name = "CORR_FINAL", nullable = false)
    private int corrFinal;
    @Basic(optional = false)
    @Column(name = "BAN_ESTADO", nullable = false, length = 1)
    private String banEstado;
    @JoinColumn(name = "CORR_CORRELATIVO", referencedColumnName = "CORR_CORRELATIVO", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Correlativos correlativos;
    @JoinColumn(name = "CORR_CAJA", referencedColumnName = "CORR_CAJA", nullable = false)
    @ManyToOne(optional = false)
    private Cajas corrCaja;

    public Cierre() {
    }

    public Cierre(Integer corrCorrelativo) {
        this.corrCorrelativo = corrCorrelativo;
    }

    public Cierre(Integer corrCorrelativo, Date fechaOperacion, int corrInicial, int corrFinal, String banEstado) {
        this.corrCorrelativo = corrCorrelativo;
        this.fechaOperacion = fechaOperacion;
        this.corrInicial = corrInicial;
        this.corrFinal = corrFinal;
        this.banEstado = banEstado;
    }

    public Integer getCorrCorrelativo() {
        return corrCorrelativo;
    }

    public void setCorrCorrelativo(Integer corrCorrelativo) {
        this.corrCorrelativo = corrCorrelativo;
    }

    public Date getFechaOperacion() {
        return fechaOperacion;
    }

    public void setFechaOperacion(Date fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public int getCorrInicial() {
        return corrInicial;
    }

    public void setCorrInicial(int corrInicial) {
        this.corrInicial = corrInicial;
    }

    public int getCorrFinal() {
        return corrFinal;
    }

    public void setCorrFinal(int corrFinal) {
        this.corrFinal = corrFinal;
    }

    public String getBanEstado() {
        return banEstado;
    }

    public void setBanEstado(String banEstado) {
        this.banEstado = banEstado;
    }

    public Correlativos getCorrelativos() {
        return correlativos;
    }

    public void setCorrelativos(Correlativos correlativos) {
        this.correlativos = correlativos;
    }

    public Cajas getCorrCaja() {
        return corrCaja;
    }

    public void setCorrCaja(Cajas corrCaja) {
        this.corrCaja = corrCaja;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (corrCorrelativo != null ? corrCorrelativo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cierre)) {
            return false;
        }
        Cierre other = (Cierre) object;
        if ((this.corrCorrelativo == null && other.corrCorrelativo != null) || (this.corrCorrelativo != null && !this.corrCorrelativo.equals(other.corrCorrelativo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.Cierre[ corrCorrelativo=" + corrCorrelativo + " ]";
    }
    
}
