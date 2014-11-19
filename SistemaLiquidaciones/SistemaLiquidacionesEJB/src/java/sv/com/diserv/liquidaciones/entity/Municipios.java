/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(name = "MUNICIPIOS", catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "Municipios.findAll", query = "SELECT m FROM Municipios m"),
    @NamedQuery(name = "Municipios.findByIdmuni", query = "SELECT m FROM Municipios m WHERE m.municipiosPK.idmuni = :idmuni"),
    @NamedQuery(name = "Municipios.findByIddepto", query = "SELECT m FROM Municipios m WHERE m.municipiosPK.iddepto = :iddepto"),
    @NamedQuery(name = "Municipios.findByNommuni", query = "SELECT m FROM Municipios m WHERE m.nommuni = :nommuni")})
public class Municipios implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MunicipiosPK municipiosPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NOMMUNI", nullable = false, length = 30)
    private String nommuni;
    @JoinColumn(name = "IDDEPTO", referencedColumnName = "IDDEPTO", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private DeptoPais deptoPais;

    public Municipios() {
    }

    public Municipios(MunicipiosPK municipiosPK) {
        this.municipiosPK = municipiosPK;
    }

    public Municipios(MunicipiosPK municipiosPK, String nommuni) {
        this.municipiosPK = municipiosPK;
        this.nommuni = nommuni;
    }

    public Municipios(String idmuni, String iddepto) {
        this.municipiosPK = new MunicipiosPK(idmuni, iddepto);
    }

    public MunicipiosPK getMunicipiosPK() {
        return municipiosPK;
    }

    public void setMunicipiosPK(MunicipiosPK municipiosPK) {
        this.municipiosPK = municipiosPK;
    }

    public String getNommuni() {
        return nommuni;
    }

    public void setNommuni(String nommuni) {
        this.nommuni = nommuni;
    }

    public DeptoPais getDeptoPais() {
        return deptoPais;
    }

    public void setDeptoPais(DeptoPais deptoPais) {
        this.deptoPais = deptoPais;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (municipiosPK != null ? municipiosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Municipios)) {
            return false;
        }
        Municipios other = (Municipios) object;
        if ((this.municipiosPK == null && other.municipiosPK != null) || (this.municipiosPK != null && !this.municipiosPK.equals(other.municipiosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.Municipios[ municipiosPK=" + municipiosPK + " ]";
    }
    
}
