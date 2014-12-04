/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "Personas.findAll", query = "SELECT p FROM Personas p"),
    @NamedQuery(name = "Personas.findByCorrPersona", query = "SELECT p FROM Personas p WHERE p.corrPersona = :corrPersona"),
    @NamedQuery(name = "Personas.findByCorrPersonaHijo", query = "SELECT p FROM Personas p WHERE p.corrPersonaHijo = :corrPersonaHijo"),
    @NamedQuery(name = "Personas.findByCorrTipoPersona", query = "SELECT p FROM Personas p WHERE p.corrTipoPersona = :corrTipoPersona"),
    @NamedQuery(name = "Personas.findByCorrDepto", query = "SELECT p FROM Personas p WHERE p.corrDepto = :corrDepto"),
    @NamedQuery(name = "Personas.findByBanTipoPersona", query = "SELECT p FROM Personas p WHERE p.banTipoPersona = :banTipoPersona"),
    @NamedQuery(name = "Personas.findByNombre", query = "SELECT p FROM Personas p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Personas.findByRazonSocial", query = "SELECT p FROM Personas p WHERE p.razonSocial = :razonSocial"),
    @NamedQuery(name = "Personas.findByCalleOPasaje", query = "SELECT p FROM Personas p WHERE p.calleOPasaje = :calleOPasaje"),
    @NamedQuery(name = "Personas.findByColonia", query = "SELECT p FROM Personas p WHERE p.colonia = :colonia"),
    @NamedQuery(name = "Personas.findByNit", query = "SELECT p FROM Personas p WHERE p.nit = :nit"),
    @NamedQuery(name = "Personas.findByTipoContribuyente", query = "SELECT p FROM Personas p WHERE p.tipoContribuyente = :tipoContribuyente"),
    @NamedQuery(name = "Personas.findByNoRegistroFiscal", query = "SELECT p FROM Personas p WHERE p.noRegistroFiscal = :noRegistroFiscal"),
    @NamedQuery(name = "Personas.findByTelefono1", query = "SELECT p FROM Personas p WHERE p.telefono1 = :telefono1"),
    @NamedQuery(name = "Personas.findByExt1", query = "SELECT p FROM Personas p WHERE p.ext1 = :ext1"),
    @NamedQuery(name = "Personas.findByTelefono2", query = "SELECT p FROM Personas p WHERE p.telefono2 = :telefono2"),
    @NamedQuery(name = "Personas.findByExt2", query = "SELECT p FROM Personas p WHERE p.ext2 = :ext2"),
    @NamedQuery(name = "Personas.findByTelefono3", query = "SELECT p FROM Personas p WHERE p.telefono3 = :telefono3"),
    @NamedQuery(name = "Personas.findByExt3", query = "SELECT p FROM Personas p WHERE p.ext3 = :ext3"),
    @NamedQuery(name = "Personas.findByTelefono4", query = "SELECT p FROM Personas p WHERE p.telefono4 = :telefono4"),
    @NamedQuery(name = "Personas.findByExt4", query = "SELECT p FROM Personas p WHERE p.ext4 = :ext4"),
    @NamedQuery(name = "Personas.findByFax", query = "SELECT p FROM Personas p WHERE p.fax = :fax"),
    @NamedQuery(name = "Personas.findByDiaQuedan", query = "SELECT p FROM Personas p WHERE p.diaQuedan = :diaQuedan"),
    @NamedQuery(name = "Personas.findByBanCreditoActivo", query = "SELECT p FROM Personas p WHERE p.banCreditoActivo = :banCreditoActivo"),
    @NamedQuery(name = "Personas.findByLimiteCredito", query = "SELECT p FROM Personas p WHERE p.limiteCredito = :limiteCredito"),
    @NamedQuery(name = "Personas.findByCorreo", query = "SELECT p FROM Personas p WHERE p.correo = :correo"),
    @NamedQuery(name = "Personas.findByUltSaldo", query = "SELECT p FROM Personas p WHERE p.ultSaldo = :ultSaldo"),
    @NamedQuery(name = "Personas.findByFechaUltSaldo", query = "SELECT p FROM Personas p WHERE p.fechaUltSaldo = :fechaUltSaldo"),
    @NamedQuery(name = "Personas.findByBanEstadoCivil", query = "SELECT p FROM Personas p WHERE p.banEstadoCivil = :banEstadoCivil")})
public class Personas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CORR_PERSONA", nullable = false)
    private Integer corrPersona;
    @Column(name = "CORR_PERSONA_HIJO")
    private Integer corrPersonaHijo;
    @Column(name = "CORR_TIPO_PERSONA")
    private Integer corrTipoPersona;
    @Column(name = "CORR_DEPTO")
    private Integer corrDepto;
    @Column(name = "BAN_TIPO_PERSONA", length = 1)
    private String banTipoPersona;
    @Column(length = 80)
    private String nombre;
    @Column(name = "RAZON_SOCIAL", length = 80)
    private String razonSocial;
    @Column(name = "CALLE_O_PASAJE", length = 100)
    private String calleOPasaje;
    @Column(length = 80)
    private String colonia;
    @Column(length = 20)
    private String nit;
    @Column(name = "TIPO_CONTRIBUYENTE", length = 1)
    private String tipoContribuyente;
    @Column(name = "NO_REGISTRO_FISCAL", length = 20)
    private String noRegistroFiscal;
    @Column(length = 13)
    private String telefono1;
    private Integer ext1;
    @Column(length = 13)
    private String telefono2;
    private Integer ext2;
    @Column(length = 13)
    private String telefono3;
    private Integer ext3;
    @Column(length = 13)
    private String telefono4;
    private Integer ext4;
    @Column(length = 13)
    private String fax;
    @Column(name = "DIA_QUEDAN", length = 1)
    private String diaQuedan;
    @Column(name = "BAN_CREDITO_ACTIVO", length = 1)
    private String banCreditoActivo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "LIMITE_CREDITO", precision = 14, scale = 2)
    private BigDecimal limiteCredito;
    @Column(length = 30)
    private String correo;
    @Column(name = "ULT_SALDO", precision = 14, scale = 2)
    private BigDecimal ultSaldo;
    @Column(name = "FECHA_ULT_SALDO")
    @Temporal(TemporalType.DATE)
    private Date fechaUltSaldo;
    @Column(name = "BAN_ESTADO_CIVIL", length = 1)
    private String banEstadoCivil;

    public Personas() {
    }

    public Personas(Integer corrPersona) {
        this.corrPersona = corrPersona;
    }

    public Integer getCorrPersona() {
        return corrPersona;
    }

    public void setCorrPersona(Integer corrPersona) {
        this.corrPersona = corrPersona;
    }

    public Integer getCorrPersonaHijo() {
        return corrPersonaHijo;
    }

    public void setCorrPersonaHijo(Integer corrPersonaHijo) {
        this.corrPersonaHijo = corrPersonaHijo;
    }

    public Integer getCorrTipoPersona() {
        return corrTipoPersona;
    }

    public void setCorrTipoPersona(Integer corrTipoPersona) {
        this.corrTipoPersona = corrTipoPersona;
    }

    public Integer getCorrDepto() {
        return corrDepto;
    }

    public void setCorrDepto(Integer corrDepto) {
        this.corrDepto = corrDepto;
    }

    public String getBanTipoPersona() {
        return banTipoPersona;
    }

    public void setBanTipoPersona(String banTipoPersona) {
        this.banTipoPersona = banTipoPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCalleOPasaje() {
        return calleOPasaje;
    }

    public void setCalleOPasaje(String calleOPasaje) {
        this.calleOPasaje = calleOPasaje;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getTipoContribuyente() {
        return tipoContribuyente;
    }

    public void setTipoContribuyente(String tipoContribuyente) {
        this.tipoContribuyente = tipoContribuyente;
    }

    public String getNoRegistroFiscal() {
        return noRegistroFiscal;
    }

    public void setNoRegistroFiscal(String noRegistroFiscal) {
        this.noRegistroFiscal = noRegistroFiscal;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public Integer getExt1() {
        return ext1;
    }

    public void setExt1(Integer ext1) {
        this.ext1 = ext1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public Integer getExt2() {
        return ext2;
    }

    public void setExt2(Integer ext2) {
        this.ext2 = ext2;
    }

    public String getTelefono3() {
        return telefono3;
    }

    public void setTelefono3(String telefono3) {
        this.telefono3 = telefono3;
    }

    public Integer getExt3() {
        return ext3;
    }

    public void setExt3(Integer ext3) {
        this.ext3 = ext3;
    }

    public String getTelefono4() {
        return telefono4;
    }

    public void setTelefono4(String telefono4) {
        this.telefono4 = telefono4;
    }

    public Integer getExt4() {
        return ext4;
    }

    public void setExt4(Integer ext4) {
        this.ext4 = ext4;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getDiaQuedan() {
        return diaQuedan;
    }

    public void setDiaQuedan(String diaQuedan) {
        this.diaQuedan = diaQuedan;
    }

    public String getBanCreditoActivo() {
        return banCreditoActivo;
    }

    public void setBanCreditoActivo(String banCreditoActivo) {
        this.banCreditoActivo = banCreditoActivo;
    }

    public BigDecimal getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public BigDecimal getUltSaldo() {
        return ultSaldo;
    }

    public void setUltSaldo(BigDecimal ultSaldo) {
        this.ultSaldo = ultSaldo;
    }

    public Date getFechaUltSaldo() {
        return fechaUltSaldo;
    }

    public void setFechaUltSaldo(Date fechaUltSaldo) {
        this.fechaUltSaldo = fechaUltSaldo;
    }

    public String getBanEstadoCivil() {
        return banEstadoCivil;
    }

    public void setBanEstadoCivil(String banEstadoCivil) {
        this.banEstadoCivil = banEstadoCivil;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (corrPersona != null ? corrPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personas)) {
            return false;
        }
        Personas other = (Personas) object;
        if ((this.corrPersona == null && other.corrPersona != null) || (this.corrPersona != null && !this.corrPersona.equals(other.corrPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.Personas[ corrPersona=" + corrPersona + " ]";
    }
    
}
