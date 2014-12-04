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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(name = "BANCOS_EMISORES", catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "BancosEmisores.findAll", query = "SELECT b FROM BancosEmisores b"),
    @NamedQuery(name = "BancosEmisores.findByCodBancoEmisor", query = "SELECT b FROM BancosEmisores b WHERE b.codBancoEmisor = :codBancoEmisor"),
    @NamedQuery(name = "BancosEmisores.findByNombreBanco", query = "SELECT b FROM BancosEmisores b WHERE b.nombreBanco = :nombreBanco"),
    @NamedQuery(name = "BancosEmisores.findByBanEstado", query = "SELECT b FROM BancosEmisores b WHERE b.banEstado = :banEstado"),
    @NamedQuery(name = "BancosEmisores.findByBanEmisorBanco", query = "SELECT b FROM BancosEmisores b WHERE b.banEmisorBanco = :banEmisorBanco"),
    @NamedQuery(name = "BancosEmisores.findByBanTarjeta", query = "SELECT b FROM BancosEmisores b WHERE b.banTarjeta = :banTarjeta")})
public class BancosEmisores implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "COD_BANCO_EMISOR", nullable = false)
    private Integer codBancoEmisor;
    @Basic(optional = false)
    @Column(name = "NOMBRE_BANCO", nullable = false, length = 50)
    private String nombreBanco;
    @Basic(optional = false)
    @Column(name = "BAN_ESTADO", nullable = false, length = 1)
    private String banEstado;
    @Basic(optional = false)
    @Column(name = "BAN_EMISOR_BANCO", nullable = false, length = 1)
    private String banEmisorBanco;
    @Basic(optional = false)
    @Column(name = "BAN_TARJETA", nullable = false, length = 1)
    private String banTarjeta;
    @OneToMany(mappedBy = "codBancoEmisorHijo")
    private List<BancosEmisores> bancosEmisoresList;
    @JoinColumn(name = "COD_BANCO_EMISOR_HIJO", referencedColumnName = "COD_BANCO_EMISOR")
    @ManyToOne
    private BancosEmisores codBancoEmisorHijo;

    public BancosEmisores() {
    }

    public BancosEmisores(Integer codBancoEmisor) {
        this.codBancoEmisor = codBancoEmisor;
    }

    public BancosEmisores(Integer codBancoEmisor, String nombreBanco, String banEstado, String banEmisorBanco, String banTarjeta) {
        this.codBancoEmisor = codBancoEmisor;
        this.nombreBanco = nombreBanco;
        this.banEstado = banEstado;
        this.banEmisorBanco = banEmisorBanco;
        this.banTarjeta = banTarjeta;
    }

    public Integer getCodBancoEmisor() {
        return codBancoEmisor;
    }

    public void setCodBancoEmisor(Integer codBancoEmisor) {
        this.codBancoEmisor = codBancoEmisor;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public String getBanEstado() {
        return banEstado;
    }

    public void setBanEstado(String banEstado) {
        this.banEstado = banEstado;
    }

    public String getBanEmisorBanco() {
        return banEmisorBanco;
    }

    public void setBanEmisorBanco(String banEmisorBanco) {
        this.banEmisorBanco = banEmisorBanco;
    }

    public String getBanTarjeta() {
        return banTarjeta;
    }

    public void setBanTarjeta(String banTarjeta) {
        this.banTarjeta = banTarjeta;
    }

    public List<BancosEmisores> getBancosEmisoresList() {
        return bancosEmisoresList;
    }

    public void setBancosEmisoresList(List<BancosEmisores> bancosEmisoresList) {
        this.bancosEmisoresList = bancosEmisoresList;
    }

    public BancosEmisores getCodBancoEmisorHijo() {
        return codBancoEmisorHijo;
    }

    public void setCodBancoEmisorHijo(BancosEmisores codBancoEmisorHijo) {
        this.codBancoEmisorHijo = codBancoEmisorHijo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codBancoEmisor != null ? codBancoEmisor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BancosEmisores)) {
            return false;
        }
        BancosEmisores other = (BancosEmisores) object;
        if ((this.codBancoEmisor == null && other.codBancoEmisor != null) || (this.codBancoEmisor != null && !this.codBancoEmisor.equals(other.codBancoEmisor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.BancosEmisores[ codBancoEmisor=" + codBancoEmisor + " ]";
    }
    
}
