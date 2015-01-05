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
@Table(name = "BODEGAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bodegas.findAll", query = "SELECT b FROM Bodegas b"),
    @NamedQuery(name = "Bodegas.findByIdbodega", query = "SELECT b FROM Bodegas b WHERE b.idbodega = :idbodega"),
    @NamedQuery(name = "Bodegas.findByNombre", query = "SELECT b FROM Bodegas b WHERE b.nombre = :nombre"),
    @NamedQuery(name = "Bodegas.findByDireccion", query = "SELECT b FROM Bodegas b WHERE b.direccion = :direccion"),
    @NamedQuery(name = "Bodegas.findByTelefono", query = "SELECT b FROM Bodegas b WHERE b.telefono = :telefono"),
    @NamedQuery(name = "Bodegas.findByEncargado", query = "SELECT b FROM Bodegas b WHERE b.encargado = :encargado"),
    @NamedQuery(name = "Bodegas.findByActiva", query = "SELECT b FROM Bodegas b WHERE b.activa = :activa")})
public class Bodegas implements Serializable {
    private static final long serialVersionUID = 1L;
   
     
     
    @Id 
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDBODEGA")
    @SequenceGenerator(name = "SEQ_BODEGAS", sequenceName = "SEQ_BODEGAS")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BODEGAS" )
    private Integer idbodega;
    
    @Size(max = 60)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 100)
    @Column(name = "DIRECCION")
    private String direccion;
    @Size(max = 20)
    @Column(name = "TELEFONO")
    private String telefono;
    @Size(max = 60)
    @Column(name = "ENCARGADO")
    private String encargado;
    @Column(name = "ACTIVA")
    private String activa;
    @OneToMany(mappedBy = "idbodegaentrada")
    private List<Movimientos> movimientosList;
    @OneToMany(mappedBy = "idbodegasalida")
    private List<Movimientos> movimientosList1;
    @OneToMany(mappedBy = "idbodega")
    private List<SaldoExistencia> saldoExistenciaList;
    @JoinColumn(name = "IDSUCURSAL", referencedColumnName = "IDSUCURSAL")
    @ManyToOne(optional = false)
    private Sucursales idsucursal;
    
    @Column(name = "APLICA_VEND")
    private String aplicaVend;

    public String getAplicaVend() {
        return aplicaVend;
    }

    public void setAplicaVend(String aplicaVend) {
        this.aplicaVend = aplicaVend;
    }


    public Bodegas() {
    }

    public Bodegas(Integer idbodega) {
        this.idbodega = idbodega;
    }

    
    public Integer getIdbodega() {
        return idbodega;
    }

    public void setIdbodega(Integer idbodega) {
        this.idbodega = idbodega;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    public List<Movimientos> getMovimientosList1() {
        return movimientosList1;
    }

    public void setMovimientosList1(List<Movimientos> movimientosList1) {
        this.movimientosList1 = movimientosList1;
    }

    @XmlTransient
    public List<SaldoExistencia> getSaldoExistenciaList() {
        return saldoExistenciaList;
    }

    public void setSaldoExistenciaList(List<SaldoExistencia> saldoExistenciaList) {
        this.saldoExistenciaList = saldoExistenciaList;
    }

    public Sucursales getIdsucursal() {
        return idsucursal;
    }

    public void setIdsucursal(Sucursales idsucursal) {
        this.idsucursal = idsucursal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idbodega != null ? idbodega.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bodegas)) {
            return false;
        }
        Bodegas other = (Bodegas) object;
        if ((this.idbodega == null && other.idbodega != null) || (this.idbodega != null && !this.idbodega.equals(other.idbodega))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Bodegas[ idbodega=" + idbodega + " ]";
    }
    
}
