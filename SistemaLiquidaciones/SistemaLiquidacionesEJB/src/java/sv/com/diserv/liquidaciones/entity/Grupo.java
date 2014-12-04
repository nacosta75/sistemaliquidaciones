/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g"),
    @NamedQuery(name = "Grupo.findByCorrGrupo", query = "SELECT g FROM Grupo g WHERE g.corrGrupo = :corrGrupo"),
    @NamedQuery(name = "Grupo.findByCodGpo", query = "SELECT g FROM Grupo g WHERE g.codGpo = :codGpo"),
    @NamedQuery(name = "Grupo.findByDescripcion", query = "SELECT g FROM Grupo g WHERE g.descripcion = :descripcion")})
public class Grupo implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CORR_GRUPO", nullable = false, precision = 7)
    private Double corrGrupo;
    @Basic(optional = false)
    @Column(name = "COD_GPO", nullable = false, length = 2)
    private String codGpo;
    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    private String descripcion;
    @JoinColumn(name = "CORR_LINEA", referencedColumnName = "CORR_LINEA", nullable = false)
    @ManyToOne(optional = false)
    private Lineas corrLinea;
    @OneToMany(mappedBy = "corrGrupo")
    private List<Articulos> articulosList;

    public Grupo() {
    }

    public Grupo(Double corrGrupo) {
        this.corrGrupo = corrGrupo;
    }

    public Grupo(Double corrGrupo, String codGpo, String descripcion) {
        this.corrGrupo = corrGrupo;
        this.codGpo = codGpo;
        this.descripcion = descripcion;
    }

    public Double getCorrGrupo() {
        return corrGrupo;
    }

    public void setCorrGrupo(Double corrGrupo) {
        this.corrGrupo = corrGrupo;
    }

    public String getCodGpo() {
        return codGpo;
    }

    public void setCodGpo(String codGpo) {
        this.codGpo = codGpo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Lineas getCorrLinea() {
        return corrLinea;
    }

    public void setCorrLinea(Lineas corrLinea) {
        this.corrLinea = corrLinea;
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
        hash += (corrGrupo != null ? corrGrupo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.corrGrupo == null && other.corrGrupo != null) || (this.corrGrupo != null && !this.corrGrupo.equals(other.corrGrupo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.Grupo[ corrGrupo=" + corrGrupo + " ]";
    }
    
}
