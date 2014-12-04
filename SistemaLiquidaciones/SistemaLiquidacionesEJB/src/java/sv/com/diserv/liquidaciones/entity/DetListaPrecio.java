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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(name = "DET_LISTA_PRECIO", catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "DetListaPrecio.findAll", query = "SELECT d FROM DetListaPrecio d"),
    @NamedQuery(name = "DetListaPrecio.findByCorrLista", query = "SELECT d FROM DetListaPrecio d WHERE d.corrLista = :corrLista"),
    @NamedQuery(name = "DetListaPrecio.findByPrecio", query = "SELECT d FROM DetListaPrecio d WHERE d.precio = :precio")})
public class DetListaPrecio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CORR_LISTA", nullable = false)
    private Integer corrLista;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 14, scale = 2)
    private BigDecimal precio;
    @JoinColumn(name = "CORR_LISTA", referencedColumnName = "CORR_LISTA", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private EncListaPrecio encListaPrecio;
    @JoinColumn(name = "CORR_ARTICULO", referencedColumnName = "CORR_ARTICULO", nullable = false)
    @ManyToOne(optional = false)
    private Articulos corrArticulo;

    public DetListaPrecio() {
    }

    public DetListaPrecio(Integer corrLista) {
        this.corrLista = corrLista;
    }

    public Integer getCorrLista() {
        return corrLista;
    }

    public void setCorrLista(Integer corrLista) {
        this.corrLista = corrLista;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public EncListaPrecio getEncListaPrecio() {
        return encListaPrecio;
    }

    public void setEncListaPrecio(EncListaPrecio encListaPrecio) {
        this.encListaPrecio = encListaPrecio;
    }

    public Articulos getCorrArticulo() {
        return corrArticulo;
    }

    public void setCorrArticulo(Articulos corrArticulo) {
        this.corrArticulo = corrArticulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (corrLista != null ? corrLista.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetListaPrecio)) {
            return false;
        }
        DetListaPrecio other = (DetListaPrecio) object;
        if ((this.corrLista == null && other.corrLista != null) || (this.corrLista != null && !this.corrLista.equals(other.corrLista))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.DetListaPrecio[ corrLista=" + corrLista + " ]";
    }
    
}
