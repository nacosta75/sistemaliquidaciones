/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(name = "GROUPAUTHORITIES", catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "GroupAuthorities.findAll", query = "SELECT g FROM GroupAuthorities g"),
    @NamedQuery(name = "GroupAuthorities.findByIdcorrelativo", query = "SELECT g FROM GroupAuthorities g WHERE g.idcorrelativo = :idcorrelativo")})
public class GroupAuthorities implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDCORRELATIVO", nullable = false)
    private Integer idcorrelativo;
    @JoinColumn(name = "GROUPID", referencedColumnName = "ID", nullable = false)
    @ManyToOne(optional = false)
    private Groups groupid;
    @JoinColumn(name = "IDAUTHORITY", referencedColumnName = "IDAUTHORITY")
    @ManyToOne
    private Authorities idauthority;

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

    public Groups getGroupid() {
        return groupid;
    }

    public void setGroupid(Groups groupid) {
        this.groupid = groupid;
    }

    public Authorities getIdauthority() {
        return idauthority;
    }

    public void setIdauthority(Authorities idauthority) {
        this.idauthority = idauthority;
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
        return "sv.com.diserv.liquidaciones.entity.GroupAuthorities[ idcorrelativo=" + idcorrelativo + " ]";
    }
    
}
