/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(catalog = "bdproduccion", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Authorities.findAll", query = "SELECT a FROM Authorities a"),
    @NamedQuery(name = "Authorities.findByIdAuthority", query = "SELECT a FROM Authorities a WHERE a.idAuthority = :idAuthority"),
    @NamedQuery(name = "Authorities.findByNombre", query = "SELECT a FROM Authorities a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Authorities.findByDescripcion", query = "SELECT a FROM Authorities a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "Authorities.findByEnabled", query = "SELECT a FROM Authorities a WHERE a.enabled = :enabled")})
public class Authorities implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idAuthority;
    @Size(max = 80)
    private String nombre;
    @Size(max = 280)
    private String descripcion;
    private Boolean enabled;
    @OneToMany(mappedBy = "idAuthority")
    private List<GroupAuthorities> groupauthoritiesList;

    public Authorities() {
    }

    public Authorities(Integer idAuthority) {
        this.idAuthority = idAuthority;
    }

    public Integer getIdAuthority() {
        return idAuthority;
    }

    public void setIdAuthority(Integer idAuthority) {
        this.idAuthority = idAuthority;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @XmlTransient
    public List<GroupAuthorities> getGroupauthoritiesList() {
        return groupauthoritiesList;
    }

    public void setGroupauthoritiesList(List<GroupAuthorities> groupauthoritiesList) {
        this.groupauthoritiesList = groupauthoritiesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAuthority != null ? idAuthority.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Authorities)) {
            return false;
        }
        Authorities other = (Authorities) object;
        if ((this.idAuthority == null && other.idAuthority != null) || (this.idAuthority != null && !this.idAuthority.equals(other.idAuthority))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.Authorities[ idAuthority=" + idAuthority + " ]";
    }

}
