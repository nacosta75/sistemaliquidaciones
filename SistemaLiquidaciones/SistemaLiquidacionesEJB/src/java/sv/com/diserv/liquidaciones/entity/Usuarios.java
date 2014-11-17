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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(catalog = "bdproduccion", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findByIdUsuario", query = "SELECT u FROM Usuarios u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "Usuarios.findByNombreUsuario", query = "SELECT u FROM Usuarios u WHERE u.nombreUsuario = :nombreUsuario"),
    @NamedQuery(name = "Usuarios.findByContrasena", query = "SELECT u FROM Usuarios u WHERE u.contrasena = :contrasena"),
    @NamedQuery(name = "Usuarios.findByCodigoEmpleado", query = "SELECT u FROM Usuarios u WHERE u.codigoEmpleado = :codigoEmpleado"),
    @NamedQuery(name = "Usuarios.findByNombreCompleto", query = "SELECT u FROM Usuarios u WHERE u.nombreCompleto = :nombreCompleto"),
    @NamedQuery(name = "Usuarios.findByStatus", query = "SELECT u FROM Usuarios u WHERE u.status = :status"),
    @NamedQuery(name = "Usuarios.findByRegistrosLista", query = "SELECT u FROM Usuarios u WHERE u.registrosLista = :registrosLista")})
public class Usuarios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer idUsuario;
    @Size(max = 50)
    private String nombreUsuario;
    @Size(max = 25)
    private String contrasena;
    @Size(max = 6)
    private String codigoEmpleado;
    @Size(max = 40)
    @Column(name = "nombre_completo")
    private String nombreCompleto;
    private Boolean status;
    private Integer registrosLista;
    @OneToMany(mappedBy = "idUsuario")
    private List<GroupMembers> groupmembersList;

    public Usuarios() {
    }

    public Usuarios(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getRegistrosLista() {
        return registrosLista;
    }

    public void setRegistrosLista(Integer registrosLista) {
        this.registrosLista = registrosLista;
    }

    @XmlTransient
    public List<GroupMembers> getGroupmembersList() {
        return groupmembersList;
    }

    public void setGroupmembersList(List<GroupMembers> groupmembersList) {
        this.groupmembersList = groupmembersList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.Usuarios[ idUsuario=" + idUsuario + " ]";
    }
    
}
