/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.countAll", query = "SELECT count(u) FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findByIdusuario", query = "SELECT u FROM Usuarios u WHERE u.idUsuario = :idusuario"),
    @NamedQuery(name = "Usuarios.findByNombreUsuario", query = "SELECT u FROM Usuarios u WHERE u.nombreUsuario = :nombreUsuario"),
    @NamedQuery(name = "Usuarios.findByContrasena", query = "SELECT u FROM Usuarios u WHERE u.contrasena = :contrasena"),
    @NamedQuery(name = "Usuarios.findByCodigoempleado", query = "SELECT u FROM Usuarios u WHERE u.codigoEmpleado = :codigoempleado"),
    @NamedQuery(name = "Usuarios.findByNombreCompleto", query = "SELECT u FROM Usuarios u WHERE u.nombreCompleto = :nombreCompleto"),
    @NamedQuery(name = "Usuarios.findByStatus", query = "SELECT u FROM Usuarios u WHERE u.status = :status"),
    @NamedQuery(name = "Usuarios.findByRegistroslista", query = "SELECT u FROM Usuarios u WHERE u.registrosLista = :registroslista")})
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idUsuario;
    @Column(length = 50)
    private String nombreUsuario;
    @Column(length = 25)
    private String contrasena;
    @Column(length = 6)
    private String codigoEmpleado;
    @Column(name = "NOMBRE_COMPLETO", length = 40)
    private String nombreCompleto;
    private Boolean status;
    private Integer registrosLista;
    @OneToMany(mappedBy = "idusuario")
    private List<GroupMembers> groupMembersList;

    public Usuarios() {
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

    public List<GroupMembers> getGroupMembersList() {
        return groupMembersList;
    }

    public void setGroupMembersList(List<GroupMembers> groupMembersList) {
        this.groupMembersList = groupMembersList;
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.idUsuario);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuarios other = (Usuarios) obj;
        if (!Objects.equals(this.idUsuario, other.idUsuario)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Usuarios{" + "idUsuario=" + idUsuario + ", nombreUsuario=" + nombreUsuario + ", contrasena=" + contrasena + ", codigoEmpleado=" + codigoEmpleado + ", nombreCompleto=" + nombreCompleto + ", status=" + status + ", registrosLista=" + registrosLista + ", groupMembersList=" + groupMembersList + '}';
    }

}
