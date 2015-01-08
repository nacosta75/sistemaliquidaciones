/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "TIPOARTICULO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipoarticulo.findAll", query = "SELECT t FROM Tipoarticulo t"),
    @NamedQuery(name = "Tipoarticulo.findByIdtipoarticulo", query = "SELECT t FROM Tipoarticulo t WHERE t.idtipoarticulo = :idtipoarticulo"),
    @NamedQuery(name = "Tipoarticulo.findByDescripcion", query = "SELECT t FROM Tipoarticulo t WHERE t.descripcion = :descripcion"),
    @NamedQuery(name = "Tipoarticulo.findByKit", query = "SELECT t FROM Tipoarticulo t WHERE t.kit = :kit"),
    @NamedQuery(name = "Tipoarticulo.findByKardex", query = "SELECT t FROM Tipoarticulo t WHERE t.kardex = :kardex")})
public class Tipoarticulo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDTIPOARTICULO")
    private Integer idtipoarticulo;
    @Size(max = 30)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 1)
    @Column(name = "KIT")
    private String kit;
    @Size(max = 1)
    @Column(name = "KARDEX")
    private String kardex;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtipoarticulo")
    private List<Articulos> articulosList;

    public Tipoarticulo() {
    }

    public Tipoarticulo(Integer idtipoarticulo) {
        this.idtipoarticulo = idtipoarticulo;
    }

    public Integer getIdtipoarticulo() {
        return idtipoarticulo;
    }

    public void setIdtipoarticulo(Integer idtipoarticulo) {
        this.idtipoarticulo = idtipoarticulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getKit() {
        return kit;
    }

    public void setKit(String kit) {
        this.kit = kit;
    }

    public String getKardex() {
        return kardex;
    }

    public void setKardex(String kardex) {
        this.kardex = kardex;
    }

    @XmlTransient
    public List<Articulos> getArticulosList() {
        return articulosList;
    }

    public void setArticulosList(List<Articulos> articulosList) {
        this.articulosList = articulosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipoarticulo != null ? idtipoarticulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipoarticulo)) {
            return false;
        }
        Tipoarticulo other = (Tipoarticulo) object;
        if ((this.idtipoarticulo == null && other.idtipoarticulo != null) || (this.idtipoarticulo != null && !this.idtipoarticulo.equals(other.idtipoarticulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Tipoarticulo[ idtipoarticulo=" + idtipoarticulo + " ]";
    }
    
}
