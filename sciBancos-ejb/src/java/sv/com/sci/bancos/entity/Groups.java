/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.sci.bancos.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "GROUPS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Groups.findAll", query = "SELECT g FROM Groups g"),
    @NamedQuery(name = "Groups.countAll", query = "SELECT count(g) FROM Groups g"),
    @NamedQuery(name = "Groups.findById", query = "SELECT g FROM Groups g WHERE g.id = :id"),
    @NamedQuery(name = "Groups.findByGroupname", query = "SELECT g FROM Groups g WHERE g.groupname = :groupname")})
public class Groups implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_GROUPS", sequenceName = "SEQ_GROUPS")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GROUPS")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "GROUPNAME")
    private String groupname;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupid")
    private List<Groupauthorities> groupauthoritiesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "groupid")
    private List<Groupmembers> groupmembersList;

    public Groups() {
    }

    public Groups(Long id) {
        this.id = id;
    }

    public Groups(Long id, String groupname) {
        this.id = id;
        this.groupname = groupname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @XmlTransient
    public List<Groupauthorities> getGroupauthoritiesList() {
        return groupauthoritiesList;
    }

    public void setGroupauthoritiesList(List<Groupauthorities> groupauthoritiesList) {
        this.groupauthoritiesList = groupauthoritiesList;
    }

    @XmlTransient
    public List<Groupmembers> getGroupmembersList() {
        return groupmembersList;
    }

    public void setGroupmembersList(List<Groupmembers> groupmembersList) {
        this.groupmembersList = groupmembersList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Groups)) {
            return false;
        }
        Groups other = (Groups) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Groups[ id=" + id + " ]";
    }
    
}
