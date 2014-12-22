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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author abraham.acosta
 */
@Entity
@Table(name = "LIQUIDACIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Liquidaciones.findAll", query = "SELECT l FROM Liquidaciones l"),
    @NamedQuery(name = "Liquidaciones.findByIdliq", query = "SELECT l FROM Liquidaciones l WHERE l.idliq = :idliq"),
    @NamedQuery(name = "Liquidaciones.findByNoRemesa", query = "SELECT l FROM Liquidaciones l WHERE l.noRemesa = :noRemesa"),
    @NamedQuery(name = "Liquidaciones.findByValor", query = "SELECT l FROM Liquidaciones l WHERE l.valor = :valor"),
    @NamedQuery(name = "Liquidaciones.findByObservaciones", query = "SELECT l FROM Liquidaciones l WHERE l.observaciones = :observaciones")})
public class Liquidaciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDLIQ")
    @SequenceGenerator(name = "SEQ_LIQUIDACIONES", sequenceName = "SEQ_LIQUIDACIONES")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LIQUIDACIONES")
    private Integer idliq;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "NO_REMESA")
    private String noRemesa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALOR")
    private BigDecimal valor;
    @Size(max = 200)
    @Column(name = "OBSERVACIONES")
    private String observaciones;
    @JoinColumn(name = "IDMOV", referencedColumnName = "IDMOV")
    @ManyToOne(optional = false)
    private Movimientos idmov;
    @JoinColumn(name = "IDFPAGO", referencedColumnName = "IDFPAGO")
    @ManyToOne(optional = false)
    private FormasPago idfpago;

    public Liquidaciones() {
    }

    public Liquidaciones(Integer idliq) {
        this.idliq = idliq;
    }

    public Liquidaciones(Integer idliq, String noRemesa, BigDecimal valor) {
        this.idliq = idliq;
        this.noRemesa = noRemesa;
        this.valor = valor;
    }

    public Integer getIdliq() {
        return idliq;
    }

    public void setIdliq(Integer idliq) {
        this.idliq = idliq;
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

    public Movimientos getIdmov() {
        return idmov;
    }

    public void setIdmov(Movimientos idmov) {
        this.idmov = idmov;
    }

    public FormasPago getIdfpago() {
        return idfpago;
    }

    public void setIdfpago(FormasPago idfpago) {
        this.idfpago = idfpago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idliq != null ? idliq.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Liquidaciones)) {
            return false;
        }
        Liquidaciones other = (Liquidaciones) object;
        if ((this.idliq == null && other.idliq != null) || (this.idliq != null && !this.idliq.equals(other.idliq))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Liquidaciones[ idliq=" + idliq + " ]";
    }
    
}
