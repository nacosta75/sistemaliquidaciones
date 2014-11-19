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
import javax.persistence.Id;
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
@Table(name = "BODEGAS", catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "Bodegas.findAll", query = "SELECT b FROM Bodegas b"),
    @NamedQuery(name = "Bodegas.findByIdbodega", query = "SELECT b FROM Bodegas b WHERE b.idbodega = :idbodega"),
    @NamedQuery(name = "Bodegas.findByCodigo", query = "SELECT b FROM Bodegas b WHERE b.codigo = :codigo"),
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
    @Column(name = "IDBODEGA", nullable = false)
    private Integer idbodega;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CODIGO", nullable = false, length = 10)
    private String codigo;
    @Size(max = 60)
    @Column(name = "NOMBRE", length = 60)
    private String nombre;
    @Size(max = 100)
    @Column(name = "DIRECCION", length = 100)
    private String direccion;
    @Size(max = 20)
    @Column(name = "TELEFONO", length = 20)
    private String telefono;
    @Size(max = 60)
    @Column(name = "ENCARGADO", length = 60)
    private String encargado;
    @Column(name = "ACTIVA")
    private String activa;
    @JoinColumn(name = "IDEMPRESA", referencedColumnName = "IDEMPRESA", nullable = false)
    @ManyToOne(optional = false)
    private Empresas idempresa;

    public Bodegas() {
    }

    public Bodegas(Integer idbodega) {
        this.idbodega = idbodega;
    }

    public Bodegas(Integer idbodega, String codigo) {
        this.idbodega = idbodega;
        this.codigo = codigo;
    }

    public Integer getIdbodega() {
        return idbodega;
    }

    public void setIdbodega(Integer idbodega) {
        this.idbodega = idbodega;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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

    public Empresas getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(Empresas idempresa) {
        this.idempresa = idempresa;
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
        return "sv.com.diserv.liquidaciones.entity.Bodegas[ idbodega=" + idbodega + " ]";
    }
    
}
