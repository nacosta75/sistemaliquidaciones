/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE DISERV,SA DE CV  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 *alvarenga.miranda@gmail.com
 * @author edwin alvarenga
 * DISERV,SA DE CV
 */
package sv.com.diserv.liquidaciones.dto;

import java.io.Serializable;

/**
 *
 * @author edwin.alvarenga
 */
public class BusquedaUserDTO implements Serializable {

    int carnetUsuario;
    String usuarioUsuario;
    String nombreUsuario;

    public int getCarnetUsuario() {
        return carnetUsuario;
    }

    public void setCarnetUsuario(int carnetUsuario) {
        this.carnetUsuario = carnetUsuario;
    }

    public String getUsuarioUsuario() {
        return usuarioUsuario;
    }

    public void setUsuarioUsuario(String usuarioUsuario) {
        this.usuarioUsuario = usuarioUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.carnetUsuario;
        hash = 41 * hash + (this.usuarioUsuario != null ? this.usuarioUsuario.hashCode() : 0);
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
        final BusquedaUserDTO other = (BusquedaUserDTO) obj;
        if (this.carnetUsuario != other.carnetUsuario) {
            return false;
        }
        if ((this.usuarioUsuario == null) ? (other.usuarioUsuario != null) : !this.usuarioUsuario.equals(other.usuarioUsuario)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BusquedasDTO{" + "carnetUsuario=" + carnetUsuario + ", usuarioUsuario=" + usuarioUsuario + ", nombreUsuario=" + nombreUsuario + '}';
    }
}