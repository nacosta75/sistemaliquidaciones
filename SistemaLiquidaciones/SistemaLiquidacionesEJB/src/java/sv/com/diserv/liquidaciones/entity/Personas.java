/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author abraham.acosta
 */
@Entity
@Table(name = "PERSONAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Personas.findAll", query = "SELECT p FROM Personas p"),
    @NamedQuery(name = "Personas.findByIdpersona", query = "SELECT p FROM Personas p WHERE p.idpersona = :idpersona"),
    @NamedQuery(name = "Personas.findByNombre", query = "SELECT p FROM Personas p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Personas.findByRazonSocial", query = "SELECT p FROM Personas p WHERE p.razonSocial = :razonSocial"),
    @NamedQuery(name = "Personas.findByCalleOPasaje", query = "SELECT p FROM Personas p WHERE p.calleOPasaje = :calleOPasaje"),
    @NamedQuery(name = "Personas.findByColonia", query = "SELECT p FROM Personas p WHERE p.colonia = :colonia"),
    @NamedQuery(name = "Personas.findByNit", query = "SELECT p FROM Personas p WHERE p.nit = :nit"),
    @NamedQuery(name = "Personas.findByNoRegistroFiscal", query = "SELECT p FROM Personas p WHERE p.noRegistroFiscal = :noRegistroFiscal"),
    @NamedQuery(name = "Personas.findByTelefono1", query = "SELECT p FROM Personas p WHERE p.telefono1 = :telefono1"),
    @NamedQuery(name = "Personas.findByExt1", query = "SELECT p FROM Personas p WHERE p.ext1 = :ext1"),
    @NamedQuery(name = "Personas.findByTelefono2", query = "SELECT p FROM Personas p WHERE p.telefono2 = :telefono2"),
    @NamedQuery(name = "Personas.findByExt2", query = "SELECT p FROM Personas p WHERE p.ext2 = :ext2"),
    @NamedQuery(name = "Personas.findByTelefono3", query = "SELECT p FROM Personas p WHERE p.telefono3 = :telefono3"),
    @NamedQuery(name = "Personas.findByExt3", query = "SELECT p FROM Personas p WHERE p.ext3 = :ext3"),
    @NamedQuery(name = "Personas.findByFax", query = "SELECT p FROM Personas p WHERE p.fax = :fax"),
    @NamedQuery(name = "Personas.findByCreditoActivo", query = "SELECT p FROM Personas p WHERE p.creditoActivo = :creditoActivo"),
    @NamedQuery(name = "Personas.findByLimiteCredito", query = "SELECT p FROM Personas p WHERE p.limiteCredito = :limiteCredito"),
    @NamedQuery(name = "Personas.findByCorreo", query = "SELECT p FROM Personas p WHERE p.correo = :correo"),
    @NamedQuery(name = "Personas.findByUltSaldo", query = "SELECT p FROM Personas p WHERE p.ultSaldo = :ultSaldo"),
    @NamedQuery(name = "Personas.findByFechaUltSaldo", query = "SELECT p FROM Personas p WHERE p.fechaUltSaldo = :fechaUltSaldo"),
    @NamedQuery(name = "Personas.findByEstadoCivil", query = "SELECT p FROM Personas p WHERE p.estadoCivil = :estadoCivil"),
    @NamedQuery(name = "Personas.findByIdusuariocrea", query = "SELECT p FROM Personas p WHERE p.idusuariocrea = :idusuariocrea"),
    @NamedQuery(name = "Personas.findByFechacrea", query = "SELECT p FROM Personas p WHERE p.fechacrea = :fechacrea"),
    @NamedQuery(name = "Personas.findByIdusuariomod", query = "SELECT p FROM Personas p WHERE p.idusuariomod = :idusuariomod"),
    @NamedQuery(name = "Personas.findByFechamod", query = "SELECT p FROM Personas p WHERE p.fechamod = :fechamod")})
public class Personas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDPERSONA")
    private Integer idpersona;
    @Size(max = 80)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 80)
    @Column(name = "RAZON_SOCIAL")
    private String razonSocial;
    @Size(max = 100)
    @Column(name = "CALLE_O_PASAJE")
    private String calleOPasaje;
    @Size(max = 80)
    @Column(name = "COLONIA")
    private String colonia;
    @Size(max = 20)
    @Column(name = "NIT")
    private String nit;
    @Size(max = 20)
    @Column(name = "NO_REGISTRO_FISCAL")
    private String noRegistroFiscal;
    @Size(max = 13)
    @Column(name = "TELEFONO1")
    private String telefono1;
    @Column(name = "EXT1")
    private Integer ext1;
    @Size(max = 13)
    @Column(name = "TELEFONO2")
    private String telefono2;
    @Column(name = "EXT2")
    private Integer ext2;
    @Size(max = 13)
    @Column(name = "TELEFONO3")
    private String telefono3;
    @Column(name = "EXT3")
    private Integer ext3;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 13)
    @Column(name = "FAX")
    private String fax;
    @Size(max = 1)
    @Column(name = "CREDITO_ACTIVO")
    private String creditoActivo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "LIMITE_CREDITO")
    private BigDecimal limiteCredito;
    @Size(max = 30)
    @Column(name = "CORREO")
    private String correo;
    @Column(name = "ULT_SALDO")
    private BigDecimal ultSaldo;
    @Column(name = "FECHA_ULT_SALDO")
    @Temporal(TemporalType.DATE)
    private Date fechaUltSaldo;
    @Size(max = 1)
    @Column(name = "ESTADO_CIVIL")
    private String estadoCivil;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDUSUARIOCREA")
    private int idusuariocrea;
    @Column(name = "FECHACREA")
    @Temporal(TemporalType.DATE)
    private Date fechacrea;
    @Column(name = "IDUSUARIOMOD")
    private Integer idusuariomod;
    @Column(name = "FECHAMOD")
    @Temporal(TemporalType.DATE)
    private Date fechamod;
    @OneToMany(mappedBy = "idpersona")
    private List<Movimientos> movimientosList;
    @JoinColumn(name = "IDTIPOPERSONA", referencedColumnName = "IDTIPOPERSONA")
    @ManyToOne
    private TiposPersona idtipopersona;
    @JoinColumn(name = "IDSUCURSAL", referencedColumnName = "IDSUCURSAL")
    @ManyToOne(optional = false)
    private Sucursales idsucursal;
    @JoinColumn(name = "IDEMPRESA", referencedColumnName = "IDEMPRESA")
    @ManyToOne(optional = false)
    private Empresas idempresa;

    public Personas() {
    }

    public Personas(Integer idpersona) {
        this.idpersona = idpersona;
    }

    public Personas(Integer idpersona, int idusuariocrea) {
        this.idpersona = idpersona;
        this.idusuariocrea = idusuariocrea;
    }

    public Integer getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(Integer idpersona) {
        this.idpersona = idpersona;
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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCreditoActivo() {
        return creditoActivo;
    }

    public void setCreditoActivo(String creditoActivo) {
        this.creditoActivo = creditoActivo;
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

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public int getIdusuariocrea() {
        return idusuariocrea;
    }

    public void setIdusuariocrea(int idusuariocrea) {
        this.idusuariocrea = idusuariocrea;
    }

    public Date getFechacrea() {
        return fechacrea;
    }

    public void setFechacrea(Date fechacrea) {
        this.fechacrea = fechacrea;
    }

    public Integer getIdusuariomod() {
        return idusuariomod;
    }

    public void setIdusuariomod(Integer idusuariomod) {
        this.idusuariomod = idusuariomod;
    }

    public Date getFechamod() {
        return fechamod;
    }

    public void setFechamod(Date fechamod) {
        this.fechamod = fechamod;
    }

    @XmlTransient
    public List<Movimientos> getMovimientosList() {
        return movimientosList;
    }

    public void setMovimientosList(List<Movimientos> movimientosList) {
        this.movimientosList = movimientosList;
    }

    public TiposPersona getIdtipopersona() {
        return idtipopersona;
    }

    public void setIdtipopersona(TiposPersona idtipopersona) {
        this.idtipopersona = idtipopersona;
    }

    public Sucursales getIdsucursal() {
        return idsucursal;
    }

    public void setIdsucursal(Sucursales idsucursal) {
        this.idsucursal = idsucursal;
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
        hash += (idpersona != null ? idpersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personas)) {
            return false;
        }
        Personas other = (Personas) object;
        if ((this.idpersona == null && other.idpersona != null) || (this.idpersona != null && !this.idpersona.equals(other.idpersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Personas[ idpersona=" + idpersona + " ]";
    }
    
}
