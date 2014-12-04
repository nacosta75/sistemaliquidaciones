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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(name = "TIPOS_PERSONA", catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "TiposPersona.findAll", query = "SELECT t FROM TiposPersona t"),
    @NamedQuery(name = "TiposPersona.findByCorrTipoPersona", query = "SELECT t FROM TiposPersona t WHERE t.corrTipoPersona = :corrTipoPersona"),
    @NamedQuery(name = "TiposPersona.findByDescripcion", query = "SELECT t FROM TiposPersona t WHERE t.descripcion = :descripcion")})
public class TiposPersona implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CORR_TIPO_PERSONA", nullable = false)
    private Integer corrTipoPersona;
    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    private String descripcion;

    public TiposPersona() {
    }

    public TiposPersona(Integer corrTipoPersona) {
        this.corrTipoPersona = corrTipoPersona;
    }

    public TiposPersona(Integer corrTipoPersona, String descripcion) {
        this.corrTipoPersona = corrTipoPersona;
        this.descripcion = descripcion;
    }

    public Integer getCorrTipoPersona() {
        return corrTipoPersona;
    }

    public void setCorrTipoPersona(Integer corrTipoPersona) {
        this.corrTipoPersona = corrTipoPersona;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (corrTipoPersona != null ? corrTipoPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TiposPersona)) {
            return false;
        }
        TiposPersona other = (TiposPersona) object;
        if ((this.corrTipoPersona == null && other.corrTipoPersona != null) || (this.corrTipoPersona != null && !this.corrTipoPersona.equals(other.corrTipoPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.TiposPersona[ corrTipoPersona=" + corrTipoPersona + " ]";
    }
    
}
