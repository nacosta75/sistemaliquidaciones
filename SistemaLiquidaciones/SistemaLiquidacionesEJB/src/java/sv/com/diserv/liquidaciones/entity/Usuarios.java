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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "USUARIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findByIdusuario", query = "SELECT u FROM Usuarios u WHERE u.idusuario = :idusuario"),
    @NamedQuery(name = "Usuarios.findByNombreUsuario", query = "SELECT u FROM Usuarios u WHERE u.nombreusuario = :nombreusuario"),
    @NamedQuery(name = "Usuarios.findByContrasena", query = "SELECT u FROM Usuarios u WHERE u.contrasena = :contrasena"),
    @NamedQuery(name = "Usuarios.findByCodigoempleado", query = "SELECT u FROM Usuarios u WHERE u.codigoempleado = :codigoempleado"),
    @NamedQuery(name = "Usuarios.findByNombreCompleto", query = "SELECT u FROM Usuarios u WHERE u.nombreCompleto = :nombreCompleto"),
    @NamedQuery(name = "Usuarios.findByStatus", query = "SELECT u FROM Usuarios u WHERE u.status = :status"),
    @NamedQuery(name = "Usuarios.findByRegistroslista", query = "SELECT u FROM Usuarios u WHERE u.registroslista = :registroslista")})
public class Usuarios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDUSUARIO")
    private Integer idusuario;
    @Size(max = 50)
    @Column(name = "NOMBREUSUARIO")
    private String nombreusuario;
    @Size(max = 25)
    @Column(name = "CONTRASENA")
    private String contrasena;
    @Size(max = 6)
    @Column(name = "CODIGOEMPLEADO")
    private String codigoempleado;
    @Size(max = 40)
    @Column(name = "NOMBRE_COMPLETO")
    private String nombreCompleto;
    @Column(name = "STATUS")
    private Integer status;
    @Column(name = "REGISTROSLISTA")
    private Integer registroslista;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idusuariocrea")
    private List<LotesExistencia> lotesExistenciaList;
    @OneToMany(mappedBy = "idusuario")
    private List<Groupmembers> groupmembersList;
    @JoinColumn(name = "IDSUCURSAL", referencedColumnName = "IDSUCURSAL")
    @ManyToOne(optional = false)
    private Sucursales idsucursal;
    

    public Usuarios() {
    }

    public Usuarios(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCodigoempleado() {
        return codigoempleado;
    }

    public void setCodigoempleado(String codigoempleado) {
        this.codigoempleado = codigoempleado;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRegistroslista() {
        return registroslista;
    }

    public void setRegistroslista(Integer registroslista) {
        this.registroslista = registroslista;
    }

    @XmlTransient
    public List<LotesExistencia> getLotesExistenciaList() {
        return lotesExistenciaList;
    }

    public void setLotesExistenciaList(List<LotesExistencia> lotesExistenciaList) {
        this.lotesExistenciaList = lotesExistenciaList;
    }

    @XmlTransient
    public List<Groupmembers> getGroupmembersList() {
        return groupmembersList;
    }

    public void setGroupmembersList(List<Groupmembers> groupmembersList) {
        this.groupmembersList = groupmembersList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idusuario != null ? idusuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.idusuario == null && other.idusuario != null) || (this.idusuario != null && !this.idusuario.equals(other.idusuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Usuarios[ idusuario=" + idusuario + " ]";
    }
    
     public Sucursales getIdsucursal() {
        return idsucursal;
    }

    public void setIdsucursal(Sucursales idsucursal) {
        this.idsucursal = idsucursal;
    }
    
    
}
