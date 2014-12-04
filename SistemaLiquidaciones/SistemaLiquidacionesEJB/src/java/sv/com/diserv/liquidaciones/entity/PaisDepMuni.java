/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(name = "PAIS_DEP_MUNI", catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "PaisDepMuni.findAll", query = "SELECT p FROM PaisDepMuni p"),
    @NamedQuery(name = "PaisDepMuni.findByCorr", query = "SELECT p FROM PaisDepMuni p WHERE p.corr = :corr"),
    @NamedQuery(name = "PaisDepMuni.findByCorrDeptoMuni", query = "SELECT p FROM PaisDepMuni p WHERE p.corrDeptoMuni = :corrDeptoMuni"),
    @NamedQuery(name = "PaisDepMuni.findByDescripcion", query = "SELECT p FROM PaisDepMuni p WHERE p.descripcion = :descripcion")})
public class PaisDepMuni implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer corr;
    @Column(name = "CORR_DEPTO_MUNI")
    private Integer corrDeptoMuni;
    @Basic(optional = false)
    @Column(nullable = false, length = 40)
    private String descripcion;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "paisDepMuni")
    private Empresas empresas;

    public PaisDepMuni() {
    }

    public PaisDepMuni(Integer corr) {
        this.corr = corr;
    }

    public PaisDepMuni(Integer corr, String descripcion) {
        this.corr = corr;
        this.descripcion = descripcion;
    }

    public Integer getCorr() {
        return corr;
    }

    public void setCorr(Integer corr) {
        this.corr = corr;
    }

    public Integer getCorrDeptoMuni() {
        return corrDeptoMuni;
    }

    public void setCorrDeptoMuni(Integer corrDeptoMuni) {
        this.corrDeptoMuni = corrDeptoMuni;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Empresas getEmpresas() {
        return empresas;
    }

    public void setEmpresas(Empresas empresas) {
        this.empresas = empresas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (corr != null ? corr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaisDepMuni)) {
            return false;
        }
        PaisDepMuni other = (PaisDepMuni) object;
        if ((this.corr == null && other.corr != null) || (this.corr != null && !this.corr.equals(other.corr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.PaisDepMuni[ corr=" + corr + " ]";
    }
    
}
