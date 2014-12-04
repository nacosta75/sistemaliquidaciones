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
@Table(name = "RELACION_ASIGNACIONES", catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "RelacionAsignaciones.findAll", query = "SELECT r FROM RelacionAsignaciones r"),
    @NamedQuery(name = "RelacionAsignaciones.findByCorrMov", query = "SELECT r FROM RelacionAsignaciones r WHERE r.corrMov = :corrMov"),
    @NamedQuery(name = "RelacionAsignaciones.findByRelacion", query = "SELECT r FROM RelacionAsignaciones r WHERE r.relacion = :relacion"),
    @NamedQuery(name = "RelacionAsignaciones.findByBanEstado", query = "SELECT r FROM RelacionAsignaciones r WHERE r.banEstado = :banEstado")})
public class RelacionAsignaciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CORR_MOV", nullable = false)
    private Integer corrMov;
    @Basic(optional = false)
    @Column(nullable = false)
    private int relacion;
    @Basic(optional = false)
    @Column(name = "BAN_ESTADO", nullable = false, length = 2)
    private String banEstado;

    public RelacionAsignaciones() {
    }

    public RelacionAsignaciones(Integer corrMov) {
        this.corrMov = corrMov;
    }

    public RelacionAsignaciones(Integer corrMov, int relacion, String banEstado) {
        this.corrMov = corrMov;
        this.relacion = relacion;
        this.banEstado = banEstado;
    }

    public Integer getCorrMov() {
        return corrMov;
    }

    public void setCorrMov(Integer corrMov) {
        this.corrMov = corrMov;
    }

    public int getRelacion() {
        return relacion;
    }

    public void setRelacion(int relacion) {
        this.relacion = relacion;
    }

    public String getBanEstado() {
        return banEstado;
    }

    public void setBanEstado(String banEstado) {
        this.banEstado = banEstado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (corrMov != null ? corrMov.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelacionAsignaciones)) {
            return false;
        }
        RelacionAsignaciones other = (RelacionAsignaciones) object;
        if ((this.corrMov == null && other.corrMov != null) || (this.corrMov != null && !this.corrMov.equals(other.corrMov))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.RelacionAsignaciones[ corrMov=" + corrMov + " ]";
    }
    
}
