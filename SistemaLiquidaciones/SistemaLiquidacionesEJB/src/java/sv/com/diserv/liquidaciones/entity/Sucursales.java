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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
@Table(name = "SUCURSALES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sucursales.findAll", query = "SELECT s FROM Sucursales s"),
    @NamedQuery(name = "Sucursales.findByIdsucursal", query = "SELECT s FROM Sucursales s WHERE s.idsucursal = :idsucursal"),
    @NamedQuery(name = "Sucursales.findByDescripcion", query = "SELECT s FROM Sucursales s WHERE s.descripcion = :descripcion"),
    @NamedQuery(name = "Sucursales.findByDireccion", query = "SELECT s FROM Sucursales s WHERE s.direccion = :direccion"),
    @NamedQuery(name = "Sucursales.findByTelefono", query = "SELECT s FROM Sucursales s WHERE s.telefono = :telefono"),
    @NamedQuery(name = "Sucursales.findByEncargado", query = "SELECT s FROM Sucursales s WHERE s.encargado = :encargado"),
    @NamedQuery(name = "Sucursales.findByActiva", query = "SELECT s FROM Sucursales s WHERE s.activa = :activa")})
public class Sucursales implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDSUCURSAL")
    @SequenceGenerator(name = "SEQ_SUCUR_BODE", sequenceName = "SEQ_SUCUR_BODE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SUCUR_BODE")
    private Integer idsucursal;
    
    @Size(max = 60)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 100)
    @Column(name = "DIRECCION")
    private String direccion;
    @Size(max = 20)
    @Column(name = "TELEFONO")
    private String telefono;
    @Size(max = 60)
    @Column(name = "ENCARGADO")
    private String encargado;
    @Size(max = 1)
    @Column(name = "ACTIVA")
    private String activa;
    @OneToMany(mappedBy = "idsucursal")
    private List<Movimientos> movimientosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idsucursal")
    private List<Personas> personasList;
    @JoinColumn(name = "IDEMPRESA", referencedColumnName = "IDEMPRESA")
    @ManyToOne(optional = false)
    private Empresas idempresa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idsucursal")
    private List<Bodegas> bodegasList;

    public Sucursales() {
    }

    public Sucursales(Integer idsucursal) {
        this.idsucursal = idsucursal;
    }

    public Integer getIdsucursal() {
        return idsucursal;
    }

    public void setIdsucursal(Integer idsucursal) {
        this.idsucursal = idsucursal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public String getActiva() {
        return activa;
    }

    public void setActiva(String activa) {
        this.activa = activa;
    }

    @XmlTransient
    public List<Movimientos> getMovimientosList() {
        return movimientosList;
    }

    public void setMovimientosList(List<Movimientos> movimientosList) {
        this.movimientosList = movimientosList;
    }

    @XmlTransient
    public List<Personas> getPersonasList() {
        return personasList;
    }

    public void setPersonasList(List<Personas> personasList) {
        this.personasList = personasList;
    }

    public Empresas getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(Empresas idempresa) {
        this.idempresa = idempresa;
    }

    @XmlTransient
    public List<Bodegas> getBodegasList() {
        return bodegasList;
    }

    public void setBodegasList(List<Bodegas> bodegasList) {
        this.bodegasList = bodegasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsucursal != null ? idsucursal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sucursales)) {
            return false;
        }
        Sucursales other = (Sucursales) object;
        if ((this.idsucursal == null && other.idsucursal != null) || (this.idsucursal != null && !this.idsucursal.equals(other.idsucursal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Sucursales[ idsucursal=" + idsucursal + " ]";
    }
    
}
