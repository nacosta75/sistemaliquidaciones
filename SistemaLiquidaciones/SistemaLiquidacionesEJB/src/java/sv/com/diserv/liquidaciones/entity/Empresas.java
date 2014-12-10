/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "EMPRESAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empresas.findAll", query = "SELECT e FROM Empresas e"),
    @NamedQuery(name = "Empresas.findByIdempresa", query = "SELECT e FROM Empresas e WHERE e.idempresa = :idempresa"),
    @NamedQuery(name = "Empresas.findByCodigo", query = "SELECT e FROM Empresas e WHERE e.codigo = :codigo"),
    @NamedQuery(name = "Empresas.findByNombre", query = "SELECT e FROM Empresas e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Empresas.findByDireccion", query = "SELECT e FROM Empresas e WHERE e.direccion = :direccion"),
    @NamedQuery(name = "Empresas.findByCiudad", query = "SELECT e FROM Empresas e WHERE e.ciudad = :ciudad"),
    @NamedQuery(name = "Empresas.findByIdmuni", query = "SELECT e FROM Empresas e WHERE e.idmuni = :idmuni"),
    @NamedQuery(name = "Empresas.findByPais", query = "SELECT e FROM Empresas e WHERE e.pais = :pais"),
    @NamedQuery(name = "Empresas.findByTelefono", query = "SELECT e FROM Empresas e WHERE e.telefono = :telefono"),
    @NamedQuery(name = "Empresas.findByRegistro", query = "SELECT e FROM Empresas e WHERE e.registro = :registro"),
    @NamedQuery(name = "Empresas.findByNit", query = "SELECT e FROM Empresas e WHERE e.nit = :nit"),
    @NamedQuery(name = "Empresas.findByEmail", query = "SELECT e FROM Empresas e WHERE e.email = :email"),
    @NamedQuery(name = "Empresas.findByWeb", query = "SELECT e FROM Empresas e WHERE e.web = :web"),
    @NamedQuery(name = "Empresas.findByGiro", query = "SELECT e FROM Empresas e WHERE e.giro = :giro"),
    @NamedQuery(name = "Empresas.findByActiva", query = "SELECT e FROM Empresas e WHERE e.activa = :activa"),
    @NamedQuery(name = "Empresas.findByIva", query = "SELECT e FROM Empresas e WHERE e.iva = :iva"),
    @NamedQuery(name = "Empresas.findByRenta", query = "SELECT e FROM Empresas e WHERE e.renta = :renta"),
    @NamedQuery(name = "Empresas.findByRetencion", query = "SELECT e FROM Empresas e WHERE e.retencion = :retencion")})
public class Empresas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDEMPRESA")
    private Integer idempresa;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CODIGO")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 100)
    @Column(name = "DIRECCION")
    private String direccion;
    @Size(max = 20)
    @Column(name = "CIUDAD")
    private String ciudad;
    @Size(max = 3)
    @Column(name = "IDMUNI")
    private String idmuni;
    @Size(max = 20)
    @Column(name = "PAIS")
    private String pais;
    @Size(max = 20)
    @Column(name = "TELEFONO")
    private String telefono;
    @Size(max = 10)
    @Column(name = "REGISTRO")
    private String registro;
    @Size(max = 20)
    @Column(name = "NIT")
    private String nit;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 60)
    @Column(name = "EMAIL")
    private String email;
    @Size(max = 60)
    @Column(name = "WEB")
    private String web;
    @Size(max = 100)
    @Column(name = "GIRO")
    private String giro;
    @Column(name = "ACTIVA")
    private Character activa;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "IVA")
    private BigDecimal iva;
    @Column(name = "RENTA")
    private BigDecimal renta;
    @Column(name = "RETENCION")
    private BigDecimal retencion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idempresa")
    private List<EncListaPrecio> encListaPrecioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idempresa")
    private List<UnidadesMed> unidadesMedList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idempresa")
    private List<LineaArticulo> lineaArticuloList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idempresa")
    private List<Personas> personasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idempresa")
    private List<Articulos> articulosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idempresa")
    private List<FormasPago> formasPagoList;
    @JoinColumn(name = "IDDEPTO", referencedColumnName = "IDDEPTO")
    @ManyToOne
    private Deptopais iddepto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idempresa")
    private List<TiposMov> tiposMovList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idempresa")
    private List<Sucursales> sucursalesList;

    public Empresas() {
    }

    public Empresas(Integer idempresa) {
        this.idempresa = idempresa;
    }

    public Empresas(Integer idempresa, String codigo, String nombre) {
        this.idempresa = idempresa;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Integer getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(Integer idempresa) {
        this.idempresa = idempresa;
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

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getIdmuni() {
        return idmuni;
    }

    public void setIdmuni(String idmuni) {
        this.idmuni = idmuni;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getGiro() {
        return giro;
    }

    public void setGiro(String giro) {
        this.giro = giro;
    }

    public Character getActiva() {
        return activa;
    }

    public void setActiva(Character activa) {
        this.activa = activa;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getRenta() {
        return renta;
    }

    public void setRenta(BigDecimal renta) {
        this.renta = renta;
    }

    public BigDecimal getRetencion() {
        return retencion;
    }

    public void setRetencion(BigDecimal retencion) {
        this.retencion = retencion;
    }

    @XmlTransient
    public List<EncListaPrecio> getEncListaPrecioList() {
        return encListaPrecioList;
    }

    public void setEncListaPrecioList(List<EncListaPrecio> encListaPrecioList) {
        this.encListaPrecioList = encListaPrecioList;
    }

    @XmlTransient
    public List<UnidadesMed> getUnidadesMedList() {
        return unidadesMedList;
    }

    public void setUnidadesMedList(List<UnidadesMed> unidadesMedList) {
        this.unidadesMedList = unidadesMedList;
    }

    @XmlTransient
    public List<LineaArticulo> getLineaArticuloList() {
        return lineaArticuloList;
    }

    public void setLineaArticuloList(List<LineaArticulo> lineaArticuloList) {
        this.lineaArticuloList = lineaArticuloList;
    }

    @XmlTransient
    public List<Personas> getPersonasList() {
        return personasList;
    }

    public void setPersonasList(List<Personas> personasList) {
        this.personasList = personasList;
    }

    @XmlTransient
    public List<Articulos> getArticulosList() {
        return articulosList;
    }

    public void setArticulosList(List<Articulos> articulosList) {
        this.articulosList = articulosList;
    }

    @XmlTransient
    public List<FormasPago> getFormasPagoList() {
        return formasPagoList;
    }

    public void setFormasPagoList(List<FormasPago> formasPagoList) {
        this.formasPagoList = formasPagoList;
    }

    public Deptopais getIddepto() {
        return iddepto;
    }

    public void setIddepto(Deptopais iddepto) {
        this.iddepto = iddepto;
    }

    @XmlTransient
    public List<TiposMov> getTiposMovList() {
        return tiposMovList;
    }

    public void setTiposMovList(List<TiposMov> tiposMovList) {
        this.tiposMovList = tiposMovList;
    }

    @XmlTransient
    public List<Sucursales> getSucursalesList() {
        return sucursalesList;
    }

    public void setSucursalesList(List<Sucursales> sucursalesList) {
        this.sucursalesList = sucursalesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idempresa != null ? idempresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empresas)) {
            return false;
        }
        Empresas other = (Empresas) object;
        if ((this.idempresa == null && other.idempresa != null) || (this.idempresa != null && !this.idempresa.equals(other.idempresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Empresas[ idempresa=" + idempresa + " ]";
    }
    
}
