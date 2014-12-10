/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author abraham.acosta
 */
@Entity
@Table(name = "TIPOS_MOV")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposMov.findAll", query = "SELECT t FROM TiposMov t"),
    @NamedQuery(name = "TiposMov.findByIdtipomov", query = "SELECT t FROM TiposMov t WHERE t.idtipomov = :idtipomov"),
    @NamedQuery(name = "TiposMov.findByDescripcion", query = "SELECT t FROM TiposMov t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TiposMov.findByEstado", query = "SELECT t FROM TiposMov t WHERE t.estado = :estado"),
    @NamedQuery(name = "TiposMov.findByClaseOperacion", query = "SELECT t FROM TiposMov t WHERE t.claseOperacion = :claseOperacion")})
public class TiposMov implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDTIPOMOV")
    private Integer idtipomov;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "ESTADO")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CLASE_OPERACION")
    private String claseOperacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtipomov")
    private List<Movimientos> movimientosList;
    @JoinColumn(name = "IDEMPRESA", referencedColumnName = "IDEMPRESA")
    @ManyToOne(optional = false)
    private Empresas idempresa;

    public TiposMov() {
    }

    public TiposMov(Integer idtipomov) {
        this.idtipomov = idtipomov;
    }

    public TiposMov(Integer idtipomov, String descripcion, String estado, String claseOperacion) {
        this.idtipomov = idtipomov;
        this.descripcion = descripcion;
        this.estado = estado;
        this.claseOperacion = claseOperacion;
    }

    public Integer getIdtipomov() {
        return idtipomov;
    }

    public void setIdtipomov(Integer idtipomov) {
        this.idtipomov = idtipomov;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getClaseOperacion() {
        return claseOperacion;
    }

    public void setClaseOperacion(String claseOperacion) {
        this.claseOperacion = claseOperacion;
    }

    @XmlTransient
    public List<Movimientos> getMovimientosList() {
        return movimientosList;
    }

    public void setMovimientosList(List<Movimientos> movimientosList) {
        this.movimientosList = movimientosList;
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
        hash += (idtipomov != null ? idtipomov.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TiposMov)) {
            return false;
        }
        TiposMov other = (TiposMov) object;
        if ((this.idtipomov == null && other.idtipomov != null) || (this.idtipomov != null && !this.idtipomov.equals(other.idtipomov))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TiposMov[ idtipomov=" + idtipomov + " ]";
    }
    
}
