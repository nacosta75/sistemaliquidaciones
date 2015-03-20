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
 * @author sonia.garcia
 */
@Entity
@Table(name = "RELACION_ASIGNACIONES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RelacionAsignaciones.findAll", query = "SELECT r FROM RelacionAsignaciones r"),
    @NamedQuery(name = "RelacionAsignaciones.findByIdrelacion", query = "SELECT r FROM RelacionAsignaciones r WHERE r.idrelacion = :idrelacion"),
    @NamedQuery(name = "RelacionAsignaciones.findByIccIdvendedor", query = "SELECT r FROM RelacionAsignaciones r WHERE r.idlote.icc=:icc and r.idmov.idpersona.idpersona=:idpersona"),
    @NamedQuery(name = "RelacionAsignaciones.findByIdlote", query = "SELECT r FROM RelacionAsignaciones r WHERE r.idlote = :idlote")})
public class RelacionAsignaciones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDRELASIG")
    @SequenceGenerator(allocationSize =1, name = "SEQ_IDRELASIGNACION", sequenceName = "SEQ_IDRELASIGNACION")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_IDRELASIGNACION" )
    private Integer idrelacion;
   
    @JoinColumn(name = "IDMOV", referencedColumnName = "IDMOV")
    @ManyToOne(optional = false)
    private Movimientos idmov;
    
    @JoinColumn(name = "IDLOTE", referencedColumnName = "IDLOTE")
    @ManyToOne(optional = false)
    private LotesExistencia idlote;

    public RelacionAsignaciones() {
    }

    public RelacionAsignaciones(Integer idrelacion) {
        this.idrelacion = idrelacion;
    }

    public Integer getIdrelacion() {
        return idrelacion;
    }

    public void setIdrelacion(Integer idrelacion) {
        this.idrelacion = idrelacion;
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
        if (!(object instanceof RelacionAsignaciones)) {
            return false;
        }
        RelacionAsignaciones other = (RelacionAsignaciones) object;
        if ((this.idrelacion == null && other.idrelacion != null) || (this.idrelacion != null && !this.idrelacion.equals(other.idrelacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RelacionLiquidaciones[ idrelacion=" + idrelacion + " ]";
    }

    /**
     * @return the idlote
     */
    public LotesExistencia getIdlote() {
        return idlote;
    }

    /**
     * @param idlote the idlote to set
     */
    public void setIdlote(LotesExistencia idlote) {
        this.idlote = idlote;
    }
    
}
