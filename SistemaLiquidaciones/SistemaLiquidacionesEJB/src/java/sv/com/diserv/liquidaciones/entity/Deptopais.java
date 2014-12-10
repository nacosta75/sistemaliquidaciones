/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author abraham.acosta
 */
@Entity
@Table(name = "DEPTOPAIS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deptopais.findAll", query = "SELECT d FROM Deptopais d"),
    @NamedQuery(name = "Deptopais.findByIddepto", query = "SELECT d FROM Deptopais d WHERE d.iddepto = :iddepto"),
    @NamedQuery(name = "Deptopais.findByNomdepto", query = "SELECT d FROM Deptopais d WHERE d.nomdepto = :nomdepto")})
public class Deptopais implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "IDDEPTO")
    private String iddepto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NOMDEPTO")
    private String nomdepto;
    @OneToMany(mappedBy = "iddepto")
    private List<Empresas> empresasList;

    public Deptopais() {
    }

    public Deptopais(String iddepto) {
        this.iddepto = iddepto;
    }

    public Deptopais(String iddepto, String nomdepto) {
        this.iddepto = iddepto;
        this.nomdepto = nomdepto;
    }

    public String getIddepto() {
        return iddepto;
    }

    public void setIddepto(String iddepto) {
        this.iddepto = iddepto;
    }

    public String getNomdepto() {
        return nomdepto;
    }

    public void setNomdepto(String nomdepto) {
        this.nomdepto = nomdepto;
    }

    @XmlTransient
    public List<Empresas> getEmpresasList() {
        return empresasList;
    }

    public void setEmpresasList(List<Empresas> empresasList) {
        this.empresasList = empresasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddepto != null ? iddepto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deptopais)) {
            return false;
        }
        Deptopais other = (Deptopais) object;
        if ((this.iddepto == null && other.iddepto != null) || (this.iddepto != null && !this.iddepto.equals(other.iddepto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Deptopais[ iddepto=" + iddepto + " ]";
    }
    
}
