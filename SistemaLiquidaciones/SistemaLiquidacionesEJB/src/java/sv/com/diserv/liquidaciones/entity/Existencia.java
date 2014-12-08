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

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "Existencia.findAll", query = "SELECT e FROM Existencia e"),
    @NamedQuery(name = "Existencia.findByCorrExistencia", query = "SELECT e FROM Existencia e WHERE e.corrExistencia = :corrExistencia"),
    @NamedQuery(name = "Existencia.findByExitencia", query = "SELECT e FROM Existencia e WHERE e.exitencia = :exitencia")})
public class Existencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CORR_EXISTENCIA", nullable = false)
    private Integer corrExistencia;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(precision = 12, scale = 2)
    private BigDecimal exitencia;
    @JoinColumn(name = "CORR_SUCUR", referencedColumnName = "CORR_SUCUR")
    @ManyToOne
    private Bodegas corrSucur;
    @JoinColumn(name = "CORR_ARTICULO", referencedColumnName = "CORR_ARTICULO", nullable = false)
    @ManyToOne(optional = false)
    private Articulos corrArticulo;

    public Existencia() {
    }

    public Existencia(Integer corrExistencia) {
        this.corrExistencia = corrExistencia;
    }

    public Integer getCorrExistencia() {
        return corrExistencia;
    }

    public void setCorrExistencia(Integer corrExistencia) {
        this.corrExistencia = corrExistencia;
    }

    public BigDecimal getExitencia() {
        return exitencia;
    }

    public void setExitencia(BigDecimal exitencia) {
        this.exitencia = exitencia;
    }

    public Bodegas getCorrSucur() {
        return corrSucur;
    }

    public void setCorrSucur(Bodegas corrSucur) {
        this.corrSucur = corrSucur;
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
        hash += (corrExistencia != null ? corrExistencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Existencia)) {
            return false;
        }
        Existencia other = (Existencia) object;
        if ((this.corrExistencia == null && other.corrExistencia != null) || (this.corrExistencia != null && !this.corrExistencia.equals(other.corrExistencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.Existencia[ corrExistencia=" + corrExistencia + " ]";
    }
    
}
