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
@Table(name = "BODEGA_VENDEDOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BodegaVendedor.findAll", query = "SELECT b FROM BodegaVendedor b"),
    @NamedQuery(name = "BodegaVendedor.findById", query = "SELECT b FROM BodegaVendedor b WHERE b.id = :id"),
    @NamedQuery(name = "BodegaVendedor.findAllBodegasAsignables", query = "SELECT b FROM Bodegas b where b.aplicaVend='S' AND b.idbodega not in (SELECT bv.idbodega.idbodega FROM BodegaVendedor bv)"),
//    @NamedQuery(name = "BodegaVendedor.findByIdVendedor", query = "SELECT b FROM Bodegas b inner join BodegaVendedor bv on bv.idbodega.idbodega=b.idbodega WHERE bv.idpersona.idpersona = :idVendedor"),
    @NamedQuery(name = "BodegaVendedor.findByIdVendedorBodega", query = "SELECT b FROM BodegaVendedor b WHERE b.idpersona.idpersona = :idVendedor AND b.idbodega.idbodega = :idBodega")

})
public class BodegaVendedor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    @SequenceGenerator(allocationSize =1, name = "SEQ_IDBODEGAVEND", sequenceName = "SEQ_IDBODEGAVEND")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_IDBODEGAVEND" )
    private Integer id;
    @JoinColumn(name = "IDBODEGA", referencedColumnName = "IDBODEGA")
    @ManyToOne(optional = false)
    private Bodegas idbodega;
    @JoinColumn(name = "IDPERSONA", referencedColumnName = "IDPERSONA")
    @ManyToOne(optional = false)
    private Personas idpersona;
//
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
