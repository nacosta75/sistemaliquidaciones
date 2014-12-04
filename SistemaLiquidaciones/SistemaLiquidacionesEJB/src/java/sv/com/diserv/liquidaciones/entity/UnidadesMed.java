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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(name = "UNIDADES_MED", catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "UnidadesMed.findAll", query = "SELECT u FROM UnidadesMed u"),
    @NamedQuery(name = "UnidadesMed.findByCorrUnidad", query = "SELECT u FROM UnidadesMed u WHERE u.corrUnidad = :corrUnidad"),
    @NamedQuery(name = "UnidadesMed.findByCodUnidad", query = "SELECT u FROM UnidadesMed u WHERE u.codUnidad = :codUnidad"),
    @NamedQuery(name = "UnidadesMed.findByDescripcion", query = "SELECT u FROM UnidadesMed u WHERE u.descripcion = :descripcion")})
public class UnidadesMed implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CORR_UNIDAD", nullable = false, precision = 7)
    private Double corrUnidad;
    @Basic(optional = false)
    @Column(name = "COD_UNIDAD", nullable = false, length = 2)
    private String codUnidad;
    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "corrUnidad")
    private List<Articulos> articulosList;

    public UnidadesMed() {
    }

    public UnidadesMed(Double corrUnidad) {
        this.corrUnidad = corrUnidad;
    }

    public UnidadesMed(Double corrUnidad, String codUnidad, String descripcion) {
        this.corrUnidad = corrUnidad;
        this.codUnidad = codUnidad;
        this.descripcion = descripcion;
    }

    public Double getCorrUnidad() {
        return corrUnidad;
    }

    public void setCorrUnidad(Double corrUnidad) {
        this.corrUnidad = corrUnidad;
    }

    public String getCodUnidad() {
        return codUnidad;
    }

    public void setCodUnidad(String codUnidad) {
        this.codUnidad = codUnidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Articulos> getArticulosList() {
        return articulosList;
    }

    public void setArticulosList(List<Articulos> articulosList) {
        this.articulosList = articulosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (corrUnidad != null ? corrUnidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UnidadesMed)) {
            return false;
        }
        UnidadesMed other = (UnidadesMed) object;
        if ((this.corrUnidad == null && other.corrUnidad != null) || (this.corrUnidad != null && !this.corrUnidad.equals(other.corrUnidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.UnidadesMed[ corrUnidad=" + corrUnidad + " ]";
    }
    
}
