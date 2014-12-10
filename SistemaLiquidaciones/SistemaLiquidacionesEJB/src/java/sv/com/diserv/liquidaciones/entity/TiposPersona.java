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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author abraham.acosta
 */
@Entity
@Table(name = "TIPOS_PERSONA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiposPersona.findAll", query = "SELECT t FROM TiposPersona t"),
    @NamedQuery(name = "TiposPersona.findByIdtipopersona", query = "SELECT t FROM TiposPersona t WHERE t.idtipopersona = :idtipopersona"),
    @NamedQuery(name = "TiposPersona.findByDescripcion", query = "SELECT t FROM TiposPersona t WHERE t.descripcion = :descripcion")})
public class TiposPersona implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDTIPOPERSONA")
    private Integer idtipopersona;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(mappedBy = "idtipopersona")
    private List<Personas> personasList;

    public TiposPersona() {
    }

    public TiposPersona(Integer idtipopersona) {
        this.idtipopersona = idtipopersona;
    }

    public TiposPersona(Integer idtipopersona, String descripcion) {
        this.idtipopersona = idtipopersona;
        this.descripcion = descripcion;
    }

    public Integer getIdtipopersona() {
        return idtipopersona;
    }

    public void setIdtipopersona(Integer idtipopersona) {
        this.idtipopersona = idtipopersona;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Personas> getPersonasList() {
        return personasList;
    }

    public void setPersonasList(List<Personas> personasList) {
        this.personasList = personasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipopersona != null ? idtipopersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TiposPersona)) {
            return false;
        }
        TiposPersona other = (TiposPersona) object;
        if ((this.idtipopersona == null && other.idtipopersona != null) || (this.idtipopersona != null && !this.idtipopersona.equals(other.idtipopersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TiposPersona[ idtipopersona=" + idtipopersona + " ]";
    }
    
}
