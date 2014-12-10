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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author abraham.acosta
 */
@Entity
@Table(name = "MARCA_ARTICULO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MarcaArticulo.findAll", query = "SELECT m FROM MarcaArticulo m"),
    @NamedQuery(name = "MarcaArticulo.findByIdmarca", query = "SELECT m FROM MarcaArticulo m WHERE m.idmarca = :idmarca"),
    @NamedQuery(name = "MarcaArticulo.findByDescmarca", query = "SELECT m FROM MarcaArticulo m WHERE m.descmarca = :descmarca"),
    @NamedQuery(name = "MarcaArticulo.findByIdempresa", query = "SELECT m FROM MarcaArticulo m WHERE m.idempresa = :idempresa"),
    @NamedQuery(name = "MarcaArticulo.findByActiva", query = "SELECT m FROM MarcaArticulo m WHERE m.activa = :activa")})
public class MarcaArticulo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDMARCA")
    private Integer idmarca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "DESCMARCA")
    private String descmarca;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDEMPRESA")
    private int idempresa;
    @Size(max = 1)
    @Column(name = "ACTIVA")
    private String activa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idmarca")
    private List<Articulos> articulosList;

    public MarcaArticulo() {
    }

    public MarcaArticulo(Integer idmarca) {
        this.idmarca = idmarca;
    }

    public MarcaArticulo(Integer idmarca, String descmarca, int idempresa) {
        this.idmarca = idmarca;
        this.descmarca = descmarca;
        this.idempresa = idempresa;
    }

    public Integer getIdmarca() {
        return idmarca;
    }

    public void setIdmarca(Integer idmarca) {
        this.idmarca = idmarca;
    }

    public String getDescmarca() {
        return descmarca;
    }

    public void setDescmarca(String descmarca) {
        this.descmarca = descmarca;
    }

    public int getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(int idempresa) {
        this.idempresa = idempresa;
    }

    public String getActiva() {
        return activa;
    }

    public void setActiva(String activa) {
        this.activa = activa;
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
        hash += (idmarca != null ? idmarca.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarcaArticulo)) {
            return false;
        }
        MarcaArticulo other = (MarcaArticulo) object;
        if ((this.idmarca == null && other.idmarca != null) || (this.idmarca != null && !this.idmarca.equals(other.idmarca))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MarcaArticulo[ idmarca=" + idmarca + " ]";
    }
    
}
