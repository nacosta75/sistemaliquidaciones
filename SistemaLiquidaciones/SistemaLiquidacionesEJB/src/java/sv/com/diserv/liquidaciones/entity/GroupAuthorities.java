/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(catalog = "bdproduccion", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Groupauthorities.findAll", query = "SELECT g FROM Groupauthorities g"),
    @NamedQuery(name = "Groupauthorities.findByIdcorrelativo", query = "SELECT g FROM Groupauthorities g WHERE g.idcorrelativo = :idcorrelativo")})
public class GroupAuthorities implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idcorrelativo;
    @JoinColumn(name = "idAuthority", referencedColumnName = "idAuthority")
    @ManyToOne
    private Authorities idAuthority;
    @JoinColumn(name = "groupId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Groups groupId;

    public GroupAuthorities() {
    }

    public GroupAuthorities(Integer idcorrelativo) {
        this.idcorrelativo = idcorrelativo;
    }

    public Integer getIdcorrelativo() {
        return idcorrelativo;
    }

    public void setIdcorrelativo(Integer idcorrelativo) {
        this.idcorrelativo = idcorrelativo;
    }

    public Authorities getIdAuthority() {
        return idAuthority;
    }

    public void setIdAuthority(Authorities idAuthority) {
        this.idAuthority = idAuthority;
    }

    public Groups getGroupId() {
        return groupId;
    }

    public void setGroupId(Groups groupId) {
        this.groupId = groupId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcorrelativo != null ? idcorrelativo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupAuthorities)) {
            return false;
        }
        GroupAuthorities other = (GroupAuthorities) object;
        if ((this.idcorrelativo == null && other.idcorrelativo != null) || (this.idcorrelativo != null && !this.idcorrelativo.equals(other.idcorrelativo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.Groupauthorities[ idcorrelativo=" + idcorrelativo + " ]";
    }
    
}
