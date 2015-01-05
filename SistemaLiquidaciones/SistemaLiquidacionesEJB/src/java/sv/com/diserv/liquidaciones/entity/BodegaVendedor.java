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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author vitual-lubuntu
 */
@Entity
@Table(name = "BODEGA_VENDEDOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BodegaVendedor.findAll", query = "SELECT b FROM BodegaVendedor b"),
    @NamedQuery(name = "BodegaVendedor.findById", query = "SELECT b FROM BodegaVendedor b WHERE b.id = :id")})
public class BodegaVendedor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "IDBODEGA", referencedColumnName = "IDBODEGA")
    @ManyToOne(optional = false)
    private Bodegas idbodega;
    @JoinColumn(name = "IDPERSONA", referencedColumnName = "IDPERSONA")
    @ManyToOne(optional = false)
    private Personas idpersona;

    public BodegaVendedor() {
    }

    public BodegaVendedor(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Bodegas getIdbodega() {
        return idbodega;
    }

    public void setIdbodega(Bodegas idbodega) {
        this.idbodega = idbodega;
    }

    public Personas getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(Personas idpersona) {
        this.idpersona = idpersona;
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
        if (!(object instanceof BodegaVendedor)) {
            return false;
        }
        BodegaVendedor other = (BodegaVendedor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.BodegaVendedor[ id=" + id + " ]";
    }
    
}
