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
@Table(name = "TIPOS_MOV", catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "TiposMov.findAll", query = "SELECT t FROM TiposMov t"),
    @NamedQuery(name = "TiposMov.findByTipoMov", query = "SELECT t FROM TiposMov t WHERE t.tipoMov = :tipoMov"),
    @NamedQuery(name = "TiposMov.findByDescripcion", query = "SELECT t FROM TiposMov t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "TiposMov.findByBanEstado", query = "SELECT t FROM TiposMov t WHERE t.banEstado = :banEstado"),
    @NamedQuery(name = "TiposMov.findByClaseOperacion", query = "SELECT t FROM TiposMov t WHERE t.claseOperacion = :claseOperacion")})
public class TiposMov implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "TIPO_MOV", nullable = false, length = 2)
    private String tipoMov;
    @Basic(optional = false)
    @Column(nullable = false, length = 30)
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "BAN_ESTADO", nullable = false, length = 1)
    private String banEstado;
    @Basic(optional = false)
    @Column(name = "CLASE_OPERACION", nullable = false, length = 1)
    private String claseOperacion;

    public TiposMov() {
    }

    public TiposMov(String tipoMov) {
        this.tipoMov = tipoMov;
    }

    public TiposMov(String tipoMov, String descripcion, String banEstado, String claseOperacion) {
        this.tipoMov = tipoMov;
        this.descripcion = descripcion;
        this.banEstado = banEstado;
        this.claseOperacion = claseOperacion;
    }

    public String getTipoMov() {
        return tipoMov;
    }

    public void setTipoMov(String tipoMov) {
        this.tipoMov = tipoMov;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getBanEstado() {
        return banEstado;
    }

    public void setBanEstado(String banEstado) {
        this.banEstado = banEstado;
    }

    public String getClaseOperacion() {
        return claseOperacion;
    }

    public void setClaseOperacion(String claseOperacion) {
        this.claseOperacion = claseOperacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tipoMov != null ? tipoMov.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TiposMov)) {
            return false;
        }
        TiposMov other = (TiposMov) object;
        if ((this.tipoMov == null && other.tipoMov != null) || (this.tipoMov != null && !this.tipoMov.equals(other.tipoMov))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.TiposMov[ tipoMov=" + tipoMov + " ]";
    }
    
}
