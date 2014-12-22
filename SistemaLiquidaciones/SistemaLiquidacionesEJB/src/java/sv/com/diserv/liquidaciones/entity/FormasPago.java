/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author abraham.acosta
 */
@Entity
@Table(name = "FORMAS_PAGO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FormasPago.findAll", query = "SELECT f FROM FormasPago f"),
    @NamedQuery(name = "FormasPago.findByIdfpago", query = "SELECT f FROM FormasPago f WHERE f.idfpago = :idfpago"),
    @NamedQuery(name = "FormasPago.findByDescfpago", query = "SELECT f FROM FormasPago f WHERE f.descfpago = :descfpago"),
    @NamedQuery(name = "FormasPago.findByEstado", query = "SELECT f FROM FormasPago f WHERE f.estado = :estado"),
    @NamedQuery(name = "FormasPago.findByCodcontable", query = "SELECT f FROM FormasPago f WHERE f.codcontable = :codcontable"),
    @NamedQuery(name = "FormasPago.findByIdusuariocrea", query = "SELECT f FROM FormasPago f WHERE f.idusuariocrea = :idusuariocrea"),
    @NamedQuery(name = "FormasPago.findByFechaCrea", query = "SELECT f FROM FormasPago f WHERE f.fechaCrea = :fechaCrea"),
    @NamedQuery(name = "FormasPago.findByIdusuariomod", query = "SELECT f FROM FormasPago f WHERE f.idusuariomod = :idusuariomod"),
    @NamedQuery(name = "FormasPago.findByFechamod", query = "SELECT f FROM FormasPago f WHERE f.fechamod = :fechamod")})
public class FormasPago implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDFPAGO")
    @SequenceGenerator(name = "SEQ_SEQ_FORMAS_PAGO", sequenceName = "SEQ_LINEAS")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LINEAS")
    private Integer idfpago;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DESCFPAGO")
    private String descfpago;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "ESTADO")
    private String estado;
    @Size(max = 30)
    @Column(name = "CODCONTABLE")
    private String codcontable;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDUSUARIOCREA")
    private int idusuariocrea;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_CREA")
    @Temporal(TemporalType.DATE)
    private Date fechaCrea;
    @Column(name = "IDUSUARIOMOD")
    private Integer idusuariomod;
    @Column(name = "FECHAMOD")
    @Temporal(TemporalType.DATE)
    private Date fechamod;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idfpago")
    private List<Liquidaciones> liquidacionesList;
    @JoinColumn(name = "IDEMPRESA", referencedColumnName = "IDEMPRESA")
    @ManyToOne(optional = false)
    private Empresas idempresa;

    public FormasPago() {
    }

    public FormasPago(Integer idfpago) {
        this.idfpago = idfpago;
    }

    public FormasPago(Integer idfpago, String descfpago, String estado, int idusuariocrea, Date fechaCrea) {
        this.idfpago = idfpago;
        this.descfpago = descfpago;
        this.estado = estado;
        this.idusuariocrea = idusuariocrea;
        this.fechaCrea = fechaCrea;
    }

    public Integer getIdfpago() {
        return idfpago;
    }

    public void setIdfpago(Integer idfpago) {
        this.idfpago = idfpago;
    }

    public String getDescfpago() {
        return descfpago;
    }

    public void setDescfpago(String descfpago) {
        this.descfpago = descfpago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodcontable() {
        return codcontable;
    }

    public void setCodcontable(String codcontable) {
        this.codcontable = codcontable;
    }

    public int getIdusuariocrea() {
        return idusuariocrea;
    }

    public void setIdusuariocrea(int idusuariocrea) {
        this.idusuariocrea = idusuariocrea;
    }

    public Date getFechaCrea() {
        return fechaCrea;
    }

    public void setFechaCrea(Date fechaCrea) {
        this.fechaCrea = fechaCrea;
    }

    public Integer getIdusuariomod() {
        return idusuariomod;
    }

    public void setIdusuariomod(Integer idusuariomod) {
        this.idusuariomod = idusuariomod;
    }

    public Date getFechamod() {
        return fechamod;
    }

    public void setFechamod(Date fechamod) {
        this.fechamod = fechamod;
    }

    @XmlTransient
    public List<Liquidaciones> getLiquidacionesList() {
        return liquidacionesList;
    }

    public void setLiquidacionesList(List<Liquidaciones> liquidacionesList) {
        this.liquidacionesList = liquidacionesList;
    }

    public Empresas getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(Empresas idempresa) {
        this.idempresa = idempresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfpago != null ? idfpago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormasPago)) {
            return false;
        }
        FormasPago other = (FormasPago) object;
        if ((this.idfpago == null && other.idfpago != null) || (this.idfpago != null && !this.idfpago.equals(other.idfpago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FormasPago[ idfpago=" + idfpago + " ]";
    }
    
}
