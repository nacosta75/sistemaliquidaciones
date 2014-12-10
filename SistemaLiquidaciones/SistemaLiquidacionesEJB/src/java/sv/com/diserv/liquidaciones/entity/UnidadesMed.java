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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "UNIDADES_MED")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UnidadesMed.findAll", query = "SELECT u FROM UnidadesMed u"),
    @NamedQuery(name = "UnidadesMed.findByIdumedida", query = "SELECT u FROM UnidadesMed u WHERE u.idumedida = :idumedida"),
    @NamedQuery(name = "UnidadesMed.findByDescumedida", query = "SELECT u FROM UnidadesMed u WHERE u.descumedida = :descumedida"),
    @NamedQuery(name = "UnidadesMed.findByIdusuariocrea", query = "SELECT u FROM UnidadesMed u WHERE u.idusuariocrea = :idusuariocrea"),
    @NamedQuery(name = "UnidadesMed.findByFechacrea", query = "SELECT u FROM UnidadesMed u WHERE u.fechacrea = :fechacrea"),
    @NamedQuery(name = "UnidadesMed.findByIdusuariomod", query = "SELECT u FROM UnidadesMed u WHERE u.idusuariomod = :idusuariomod"),
    @NamedQuery(name = "UnidadesMed.findByFechamod", query = "SELECT u FROM UnidadesMed u WHERE u.fechamod = :fechamod")})
public class UnidadesMed implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDUMEDIDA")
    private Integer idumedida;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "DESCUMEDIDA")
    private String descumedida;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDUSUARIOCREA")
    private int idusuariocrea;
    @Column(name = "FECHACREA")
    @Temporal(TemporalType.DATE)
    private Date fechacrea;
    @Column(name = "IDUSUARIOMOD")
    private Integer idusuariomod;
    @Column(name = "FECHAMOD")
    @Temporal(TemporalType.DATE)
    private Date fechamod;
    @JoinColumn(name = "IDEMPRESA", referencedColumnName = "IDEMPRESA")
    @ManyToOne(optional = false)
    private Empresas idempresa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idumedida")
    private List<Articulos> articulosList;

    public UnidadesMed() {
    }

    public UnidadesMed(Integer idumedida) {
        this.idumedida = idumedida;
    }

    public UnidadesMed(Integer idumedida, String descumedida, int idusuariocrea) {
        this.idumedida = idumedida;
        this.descumedida = descumedida;
        this.idusuariocrea = idusuariocrea;
    }

    public Integer getIdumedida() {
        return idumedida;
    }

    public void setIdumedida(Integer idumedida) {
        this.idumedida = idumedida;
    }

    public String getDescumedida() {
        return descumedida;
    }

    public void setDescumedida(String descumedida) {
        this.descumedida = descumedida;
    }

    public int getIdusuariocrea() {
        return idusuariocrea;
    }

    public void setIdusuariocrea(int idusuariocrea) {
        this.idusuariocrea = idusuariocrea;
    }

    public Date getFechacrea() {
        return fechacrea;
    }

    public void setFechacrea(Date fechacrea) {
        this.fechacrea = fechacrea;
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

    public Empresas getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(Empresas idempresa) {
        this.idempresa = idempresa;
    }

    @XmlTransient
    public List<Articulos> getArticulosList() {
        return articulosList;
    }

    public void setArticulosList(List<Articulos> articulosList) {
        this.articulosList = articulosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idumedida != null ? idumedida.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UnidadesMed)) {
            return false;
        }
        UnidadesMed other = (UnidadesMed) object;
        if ((this.idumedida == null && other.idumedida != null) || (this.idumedida != null && !this.idumedida.equals(other.idumedida))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.UnidadesMed[ idumedida=" + idumedida + " ]";
    }
    
}
