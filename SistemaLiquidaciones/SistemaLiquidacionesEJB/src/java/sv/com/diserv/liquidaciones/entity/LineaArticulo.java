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
@Table(name = "LINEA_ARTICULO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LineaArticulo.findAll", query = "SELECT l FROM LineaArticulo l"),
    @NamedQuery(name = "LineaArticulo.findByIdlinea", query = "SELECT l FROM LineaArticulo l WHERE l.idlinea = :idlinea"),
    @NamedQuery(name = "LineaArticulo.findByActiva", query = "SELECT l FROM LineaArticulo l WHERE l.activa = :activa"),
    @NamedQuery(name = "LineaArticulo.findByDesclinea", query = "SELECT l FROM LineaArticulo l WHERE l.desclinea = :desclinea")})
public class LineaArticulo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDLINEA")
    private Integer idlinea;
    @Size(max = 1)
    @Column(name = "ACTIVA")
    private String activa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "DESCLINEA")
    private String desclinea;
    @JoinColumn(name = "IDEMPRESA", referencedColumnName = "IDEMPRESA")
    @ManyToOne(optional = false)
    private Empresas idempresa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idlinea")
    private List<Articulos> articulosList;

    public LineaArticulo() {
    }

    public LineaArticulo(Integer idlinea) {
        this.idlinea = idlinea;
    }

    public LineaArticulo(Integer idlinea, String desclinea) {
        this.idlinea = idlinea;
        this.desclinea = desclinea;
    }

    public Integer getIdlinea() {
        return idlinea;
    }

    public void setIdlinea(Integer idlinea) {
        this.idlinea = idlinea;
    }

    public String getActiva() {
        return activa;
    }

    public void setActiva(String activa) {
        this.activa = activa;
    }

    public String getDesclinea() {
        return desclinea;
    }

    public void setDesclinea(String desclinea) {
        this.desclinea = desclinea;
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
        hash += (idlinea != null ? idlinea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LineaArticulo)) {
            return false;
        }
        LineaArticulo other = (LineaArticulo) object;
        if ((this.idlinea == null && other.idlinea != null) || (this.idlinea != null && !this.idlinea.equals(other.idlinea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.LineaArticulo[ idlinea=" + idlinea + " ]";
    }
    
}
