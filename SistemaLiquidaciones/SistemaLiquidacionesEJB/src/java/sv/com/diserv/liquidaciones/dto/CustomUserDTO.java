/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE DISERV,SA DE CV  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author alvarenga.miranda@gmail.com
 */
package sv.com.diserv.liquidaciones.dto;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sv.com.diserv.liquidaciones.entity.Usuarios;

/**
 *
 *alvarenga.miranda@gmail.com
 */
public class CustomUserDTO implements UserDetails {

    private static final long serialVersionUID = -4993799715730712001L;
    private List<GrantedAuthority> perfiles;
    private String username;
    private String password;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Usuarios usuario;
    private String ipConnect;
    private Integer registrosLista;

    public Collection<? extends GrantedAuthority> getPerfiles() {
        return perfiles;
    }

    @SuppressWarnings("unchecked")
    public void setPerfiles(List<? extends GrantedAuthority> perfiles) {
        this.perfiles = (List<GrantedAuthority>) perfiles;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return perfiles;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public String getIpConnect() {
        return ipConnect;
    }

    public void setIpConnect(String ipConnect) {
        this.ipConnect = ipConnect;
    }

    public Integer getRegistrosLista() {
        return registrosLista;
    }

    public void setRegistrosLista(Integer registrosLista) {
        this.registrosLista = registrosLista;
    }
}
