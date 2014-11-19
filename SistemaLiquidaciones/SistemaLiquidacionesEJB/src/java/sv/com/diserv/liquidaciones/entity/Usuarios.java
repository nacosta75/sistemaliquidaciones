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

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(name = "USUARIOS", catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findByIdusuario", query = "SELECT u FROM Usuarios u WHERE u.idusuario = :idusuario"),
    @NamedQuery(name = "Usuarios.findByNombreusuario", query = "SELECT u FROM Usuarios u WHERE u.nombreusuario = :nombreusuario"),
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
    @Column(name = "IDUSUARIO", nullable = false)
    private Integer idusuario;
    @Size(max = 50)
    @Column(name = "NOMBREUSUARIO", length = 50)
    private String nombreUsuario;
    @Size(max = 25)
    @Column(name = "CONTRASENA", length = 25)
    private String contrasena;
    @Size(max = 6)
    @Column(name = "CODIGOEMPLEADO", length = 6)
    private String codigoEmpleado;
    @Size(max = 40)
    @Column(name = "NOMBRE_COMPLETO", length = 40)
    private String nombreCompleto;
    @Column(name = "STATUS")
    private Boolean status;
    @Column(name = "REGISTROSLISTA")
    private Integer registrosLista;
    @OneToMany(mappedBy = "idusuario")
    private List<GroupMembers> groupMembersList;

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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public Integer getRegistrosLista() {
        return registrosLista;
    }

    public void setRegistrosLista(Integer registrosLista) {
        this.registrosLista = registrosLista;
    }

    public List<GroupMembers> getGroupMembersList() {
        return groupMembersList;
    }

    public void setGroupMembersList(List<GroupMembers> groupMembersList) {
        this.groupMembersList = groupMembersList;
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
        return "sv.com.diserv.liquidaciones.entity.Usuarios[ idusuario=" + idusuario + " ]";
    }

}
