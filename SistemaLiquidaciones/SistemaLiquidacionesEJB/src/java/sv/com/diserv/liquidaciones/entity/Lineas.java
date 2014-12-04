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
@Table(catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "Lineas.findAll", query = "SELECT l FROM Lineas l"),
    @NamedQuery(name = "Lineas.findByCorrLinea", query = "SELECT l FROM Lineas l WHERE l.corrLinea = :corrLinea"),
    @NamedQuery(name = "Lineas.findByCodLinea", query = "SELECT l FROM Lineas l WHERE l.codLinea = :codLinea"),
    @NamedQuery(name = "Lineas.findByDescripcion", query = "SELECT l FROM Lineas l WHERE l.descripcion = :descripcion")})
public class Lineas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CORR_LINEA", nullable = false)
    private Integer corrLinea;
    @Basic(optional = false)
    @Column(name = "COD_LINEA", nullable = false, length = 2)
    private String codLinea;
    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "corrLinea")
    private List<Grupo> grupoList;

    public Lineas() {
    }

    public Lineas(Integer corrLinea) {
        this.corrLinea = corrLinea;
    }

    public Lineas(Integer corrLinea, String codLinea, String descripcion) {
        this.corrLinea = corrLinea;
        this.codLinea = codLinea;
        this.descripcion = descripcion;
    }

    public Integer getCorrLinea() {
        return corrLinea;
    }

    public void setCorrLinea(Integer corrLinea) {
        this.corrLinea = corrLinea;
    }

    public String getCodLinea() {
        return codLinea;
    }

    public void setCodLinea(String codLinea) {
        this.codLinea = codLinea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (corrLinea != null ? corrLinea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lineas)) {
            return false;
        }
        Lineas other = (Lineas) object;
        if ((this.corrLinea == null && other.corrLinea != null) || (this.corrLinea != null && !this.corrLinea.equals(other.corrLinea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.Lineas[ corrLinea=" + corrLinea + " ]";
    }
    
}
