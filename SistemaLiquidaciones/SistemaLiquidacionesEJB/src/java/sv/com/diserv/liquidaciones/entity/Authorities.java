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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(name = "AUTHORITIES", catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "Authorities.findAll", query = "SELECT a FROM Authorities a"),
    @NamedQuery(name = "Authorities.findByIdauthority", query = "SELECT a FROM Authorities a WHERE a.idauthority = :idauthority"),
    @NamedQuery(name = "Authorities.findByIdNombreUsuario", query = "SELECT distinct r FROM Authorities r join r.groupAuthoritiesList as gr JOIN gr.groupid as rg JOIN rg.groupMembersList as ur JOIN ur.idusuario as user where user.nombreUsuario = :idUsuario"),
    @NamedQuery(name = "Authorities.findByNombre", query = "SELECT a FROM Authorities a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Authorities.findByDescripcion", query = "SELECT a FROM Authorities a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "Authorities.findByEnabled", query = "SELECT a FROM Authorities a WHERE a.enabled = :enabled")})
public class Authorities implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDAUTHORITY", nullable = false)
    private Integer idauthority;
    @Size(max = 80)
    @Column(name = "NOMBRE", length = 80)
    private String nombre;
    @Size(max = 280)
    @Column(name = "DESCRIPCION", length = 280)
    private String descripcion;
    @Column(name = "ENABLED")
    private Integer enabled;
    @OneToMany(mappedBy = "idauthority")
    private List<GroupAuthorities> groupAuthoritiesList;

    public Authorities() {
    }

    public Authorities(Integer idauthority) {
        this.idauthority = idauthority;
    }

    public Integer getIdauthority() {
        return idauthority;
    }

    public void setIdauthority(Integer idauthority) {
        this.idauthority = idauthority;
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

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public List<GroupAuthorities> getGroupAuthoritiesList() {
        return groupAuthoritiesList;
    }

    public void setGroupAuthoritiesList(List<GroupAuthorities> groupAuthoritiesList) {
        this.groupAuthoritiesList = groupAuthoritiesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idauthority != null ? idauthority.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Authorities)) {
            return false;
        }
        Authorities other = (Authorities) object;
        if ((this.idauthority == null && other.idauthority != null) || (this.idauthority != null && !this.idauthority.equals(other.idauthority))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.Authorities[ idauthority=" + idauthority + " ]";
    }

}
