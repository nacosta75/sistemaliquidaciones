/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "Cajas.findAll", query = "SELECT c FROM Cajas c"),
    @NamedQuery(name = "Cajas.findByCorrCaja", query = "SELECT c FROM Cajas c WHERE c.corrCaja = :corrCaja"),
    @NamedQuery(name = "Cajas.findByNombreCaja", query = "SELECT c FROM Cajas c WHERE c.nombreCaja = :nombreCaja")})
public class Cajas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CORR_CAJA", nullable = false)
    private Integer corrCaja;
    @Basic(optional = false)
    @Column(name = "NOMBRE_CAJA", nullable = false, length = 30)
    private String nombreCaja;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "corrCaja")
    private List<Cierre> cierreList;

    public Cajas() {
    }

    public Cajas(Integer corrCaja) {
        this.corrCaja = corrCaja;
    }

    public Cajas(Integer corrCaja, String nombreCaja) {
        this.corrCaja = corrCaja;
        this.nombreCaja = nombreCaja;
    }

    public Integer getCorrCaja() {
        return corrCaja;
    }

    public void setCorrCaja(Integer corrCaja) {
        this.corrCaja = corrCaja;
    }

    public String getNombreCaja() {
        return nombreCaja;
    }

    public void setNombreCaja(String nombreCaja) {
        this.nombreCaja = nombreCaja;
    }

    public List<Cierre> getCierreList() {
        return cierreList;
    }

    public void setCierreList(List<Cierre> cierreList) {
        this.cierreList = cierreList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (corrCaja != null ? corrCaja.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cajas)) {
            return false;
        }
        Cajas other = (Cajas) object;
        if ((this.corrCaja == null && other.corrCaja != null) || (this.corrCaja != null && !this.corrCaja.equals(other.corrCaja))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.Cajas[ corrCaja=" + corrCaja + " ]";
    }
    
}
