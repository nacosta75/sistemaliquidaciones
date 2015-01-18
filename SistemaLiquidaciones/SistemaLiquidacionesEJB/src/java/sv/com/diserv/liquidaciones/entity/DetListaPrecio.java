/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author abraham.acosta
 */
@Entity
@Table(name = "DET_LISTA_PRECIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetListaPrecio.findAll", query = "SELECT d FROM DetListaPrecio d"),
    @NamedQuery(name = "DetListaPrecio.findByIdlistadet", query = "SELECT d FROM DetListaPrecio d WHERE d.idlistadet = :idlistadet"),
    @NamedQuery(name = "DetListaPrecio.findByPrecio", query = "SELECT d FROM DetListaPrecio d WHERE d.precio = :precio"),
    @NamedQuery(name = "DetListaPrecio.findByIdArticulo", query = "SELECT d FROM DetListaPrecio d WHERE d.idarticulo.idarticulo=:idarticulo"),
    @NamedQuery(name = "DetListaPrecio.findByIdArticuloIdEnc", query = "SELECT d FROM DetListaPrecio d WHERE d.idlistaenc.idlista = :idlista AND d.idarticulo.idarticulo = :idarticulo")
     })
public class DetListaPrecio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDLISTADET")
    private Integer idlistadet;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PRECIO")
    private BigDecimal precio;
    @JoinColumn(name = "IDLISTAENC", referencedColumnName = "IDLISTA")
    @ManyToOne(optional = false)
    private EncListaPrecio idlistaenc;
    @JoinColumn(name = "IDARTICULO", referencedColumnName = "IDARTICULO")
    @ManyToOne(optional = false)
    private Articulos idarticulo;

    public DetListaPrecio() {
    }

    public DetListaPrecio(Integer idlistadet) {
        this.idlistadet = idlistadet;
    }

    public Integer getIdlistadet() {
        return idlistadet;
    }

    public void setIdlistadet(Integer idlistadet) {
        this.idlistadet = idlistadet;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public EncListaPrecio getIdlistaenc() {
        return idlistaenc;
    }

    public void setIdlistaenc(EncListaPrecio idlistaenc) {
        this.idlistaenc = idlistaenc;
    }

    public Articulos getIdarticulo() {
        return idarticulo;
    }

    public void setIdarticulo(Articulos idarticulo) {
        this.idarticulo = idarticulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlistadet != null ? idlistadet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetListaPrecio)) {
            return false;
        }
        DetListaPrecio other = (DetListaPrecio) object;
        if ((this.idlistadet == null && other.idlistadet != null) || (this.idlistadet != null && !this.idlistadet.equals(other.idlistadet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DetListaPrecio[ idlistadet=" + idlistadet + " ]";
    }
    
}
