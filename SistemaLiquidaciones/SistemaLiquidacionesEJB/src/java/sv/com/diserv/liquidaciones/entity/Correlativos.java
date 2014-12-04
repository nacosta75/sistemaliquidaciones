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
@Table(catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "Correlativos.findAll", query = "SELECT c FROM Correlativos c"),
    @NamedQuery(name = "Correlativos.findByCorrCorrelativo", query = "SELECT c FROM Correlativos c WHERE c.corrCorrelativo = :corrCorrelativo"),
    @NamedQuery(name = "Correlativos.findByCorrSucursal", query = "SELECT c FROM Correlativos c WHERE c.corrSucursal = :corrSucursal"),
    @NamedQuery(name = "Correlativos.findByTipoDocumento", query = "SELECT c FROM Correlativos c WHERE c.tipoDocumento = :tipoDocumento")})
public class Correlativos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CORR_CORRELATIVO", nullable = false)
    private Integer corrCorrelativo;
    @Column(name = "CORR_SUCURSAL")
    private Integer corrSucursal;
    @Column(name = "TIPO_DOCUMENTO", length = 2)
    private String tipoDocumento;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "correlativos")
    private Cierre cierre;
    @JoinColumn(name = "CORR_EMPRESA", referencedColumnName = "CORR_EMPRESA")
    @ManyToOne
    private Empresas corrEmpresa;

    public Correlativos() {
    }

    public Correlativos(Integer corrCorrelativo) {
        this.corrCorrelativo = corrCorrelativo;
    }

    public Integer getCorrCorrelativo() {
        return corrCorrelativo;
    }

    public void setCorrCorrelativo(Integer corrCorrelativo) {
        this.corrCorrelativo = corrCorrelativo;
    }

    public Integer getCorrSucursal() {
        return corrSucursal;
    }

    public void setCorrSucursal(Integer corrSucursal) {
        this.corrSucursal = corrSucursal;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public Cierre getCierre() {
        return cierre;
    }

    public void setCierre(Cierre cierre) {
        this.cierre = cierre;
    }

    public Empresas getCorrEmpresa() {
        return corrEmpresa;
    }

    public void setCorrEmpresa(Empresas corrEmpresa) {
        this.corrEmpresa = corrEmpresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (corrCorrelativo != null ? corrCorrelativo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Correlativos)) {
            return false;
        }
        Correlativos other = (Correlativos) object;
        if ((this.corrCorrelativo == null && other.corrCorrelativo != null) || (this.corrCorrelativo != null && !this.corrCorrelativo.equals(other.corrCorrelativo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.Correlativos[ corrCorrelativo=" + corrCorrelativo + " ]";
    }
    
}
