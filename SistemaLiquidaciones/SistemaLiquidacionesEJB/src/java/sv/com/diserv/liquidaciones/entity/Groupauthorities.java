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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author abraham.acosta
 */
@Entity
@Table(name = "GROUPAUTHORITIES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Groupauthorities.findAll", query = "SELECT g FROM Groupauthorities g"),
    @NamedQuery(name = "Groupauthorities.findByIdcorrelativo", query = "SELECT g FROM Groupauthorities g WHERE g.idcorrelativo = :idcorrelativo")})
public class Groupauthorities implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDCORRELATIVO")
    @SequenceGenerator(name = "SEQ_GROUPAUTHORITIES", sequenceName = "SEQ_GROUPAUTHORITIES")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GROUPAUTHORITIES")
    private Integer idcorrelativo;
    @JoinColumn(name = "GROUPID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Groups groupid;
    @JoinColumn(name = "IDAUTHORITY", referencedColumnName = "IDAUTHORITY")
    @ManyToOne
    private Authorities idauthority;

    public Groupauthorities() {
    }

    public Groupauthorities(Integer idcorrelativo) {
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
        if (!(object instanceof Groupauthorities)) {
            return false;
        }
        Groupauthorities other = (Groupauthorities) object;
        if ((this.idcorrelativo == null && other.idcorrelativo != null) || (this.idcorrelativo != null && !this.idcorrelativo.equals(other.idcorrelativo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Groupauthorities[ idcorrelativo=" + idcorrelativo + " ]";
    }
    
}
