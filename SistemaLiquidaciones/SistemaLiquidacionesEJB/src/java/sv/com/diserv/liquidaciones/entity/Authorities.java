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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
@Table(name = "AUTHORITIES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Authorities.findAll", query = "SELECT a FROM Authorities a"),
    @NamedQuery(name = "Authorities.findByIdauthority", query = "SELECT a FROM Authorities a WHERE a.idauthority = :idauthority"),
    @NamedQuery(name = "Authorities.findByNombre", query = "SELECT a FROM Authorities a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Authorities.findByDescripcion", query = "SELECT a FROM Authorities a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "Authorities.findByIdNombreUsuario", query = "SELECT distinct r FROM Authorities r join r.groupauthoritiesList as gr JOIN gr.groupid as rg JOIN rg.groupmembersList as ur JOIN ur.idusuario as user where user.nombreusuario = :idUsuario"),
    @NamedQuery(name = "Authorities.findByEnabled", query = "SELECT a FROM Authorities a WHERE a.enabled = :enabled")})
public class Authorities implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDAUTHORITY")
    @SequenceGenerator(name = "SEQ_AUTHORITIES", sequenceName = "SEQ_AUTHORITIES")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AUTHORITIES")
    private Integer idauthority;
    @Size(max = 80)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 280)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "ENABLED")
    private Integer enabled;
    @OneToMany(mappedBy = "idauthority")
    private List<Groupauthorities> groupauthoritiesList;

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

    @XmlTransient
    public List<Groupauthorities> getGroupauthoritiesList() {
        return groupauthoritiesList;
    }

    public void setGroupauthoritiesList(List<Groupauthorities> groupauthoritiesList) {
        this.groupauthoritiesList = groupauthoritiesList;
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
        return "entity.Authorities[ idauthority=" + idauthority + " ]";
    }

}
