/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author edwin.alvarenga
 */
@Embeddable
public class MunicipiosPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "IDMUNI", nullable = false, length = 3)
    private String idmuni;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "IDDEPTO", nullable = false, length = 2)
    private String iddepto;

    public MunicipiosPK() {
    }

    public MunicipiosPK(String idmuni, String iddepto) {
        this.idmuni = idmuni;
        this.iddepto = iddepto;
    }

    public String getIdmuni() {
        return idmuni;
    }

    public void setIdmuni(String idmuni) {
        this.idmuni = idmuni;
    }

    public String getIddepto() {
        return iddepto;
    }

    public void setIddepto(String iddepto) {
        this.iddepto = iddepto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmuni != null ? idmuni.hashCode() : 0);
        hash += (iddepto != null ? iddepto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MunicipiosPK)) {
            return false;
        }
        MunicipiosPK other = (MunicipiosPK) object;
        if ((this.idmuni == null && other.idmuni != null) || (this.idmuni != null && !this.idmuni.equals(other.idmuni))) {
            return false;
        }
        if ((this.iddepto == null && other.iddepto != null) || (this.iddepto != null && !this.iddepto.equals(other.iddepto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.MunicipiosPK[ idmuni=" + idmuni + ", iddepto=" + iddepto + " ]";
    }
    
}
