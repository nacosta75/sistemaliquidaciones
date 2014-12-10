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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author abraham.acosta
 */
@Entity
@Table(name = "RELACION_LIQUIDACIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RelacionLiquidaciones.findAll", query = "SELECT r FROM RelacionLiquidaciones r"),
    @NamedQuery(name = "RelacionLiquidaciones.findByIdrelacion", query = "SELECT r FROM RelacionLiquidaciones r WHERE r.idrelacion = :idrelacion"),
    @NamedQuery(name = "RelacionLiquidaciones.findByEstado", query = "SELECT r FROM RelacionLiquidaciones r WHERE r.estado = :estado")})
public class RelacionLiquidaciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDRELACION")
    private Integer idrelacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "IDMOV", referencedColumnName = "IDMOV")
    @ManyToOne(optional = false)
    private Movimientos idmov;

    public RelacionLiquidaciones() {
    }

    public RelacionLiquidaciones(Integer idrelacion) {
        this.idrelacion = idrelacion;
    }

    public RelacionLiquidaciones(Integer idrelacion, String estado) {
        this.idrelacion = idrelacion;
        this.estado = estado;
    }

    public Integer getIdrelacion() {
        return idrelacion;
    }

    public void setIdrelacion(Integer idrelacion) {
        this.idrelacion = idrelacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Movimientos getIdmov() {
        return idmov;
    }

    public void setIdmov(Movimientos idmov) {
        this.idmov = idmov;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrelacion != null ? idrelacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RelacionLiquidaciones)) {
            return false;
        }
        RelacionLiquidaciones other = (RelacionLiquidaciones) object;
        if ((this.idrelacion == null && other.idrelacion != null) || (this.idrelacion != null && !this.idrelacion.equals(other.idrelacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RelacionLiquidaciones[ idrelacion=" + idrelacion + " ]";
    }
    
}
