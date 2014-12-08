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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    @NamedQuery(name = "Empresas.findByCorrEmpresa", query = "SELECT e FROM Empresas e WHERE e.corrEmpresa = :corrEmpresa"),
    @NamedQuery(name = "Empresas.findByCodEmpresa", query = "SELECT e FROM Empresas e WHERE e.codEmpresa = :codEmpresa"),
    @NamedQuery(name = "Empresas.findByNombreEmpresa", query = "SELECT e FROM Empresas e WHERE e.nombreEmpresa = :nombreEmpresa"),
    @NamedQuery(name = "Empresas.findByRazonSocial", query = "SELECT e FROM Empresas e WHERE e.razonSocial = :razonSocial"),
    @NamedQuery(name = "Empresas.findByCalleOPasaje", query = "SELECT e FROM Empresas e WHERE e.calleOPasaje = :calleOPasaje"),
    @NamedQuery(name = "Empresas.findByColonia", query = "SELECT e FROM Empresas e WHERE e.colonia = :colonia"),
    @NamedQuery(name = "Empresas.findByNit", query = "SELECT e FROM Empresas e WHERE e.nit = :nit"),
    @NamedQuery(name = "Empresas.findByTipoContribuyente", query = "SELECT e FROM Empresas e WHERE e.tipoContribuyente = :tipoContribuyente"),
    @NamedQuery(name = "Empresas.findByNoRegistroFiscal", query = "SELECT e FROM Empresas e WHERE e.noRegistroFiscal = :noRegistroFiscal"),
    @NamedQuery(name = "Empresas.findByGiroEmpresa", query = "SELECT e FROM Empresas e WHERE e.giroEmpresa = :giroEmpresa"),
    @NamedQuery(name = "Empresas.findByTelefono1", query = "SELECT e FROM Empresas e WHERE e.telefono1 = :telefono1"),
    @NamedQuery(name = "Empresas.findByTelefono2", query = "SELECT e FROM Empresas e WHERE e.telefono2 = :telefono2"),
    @NamedQuery(name = "Empresas.findByTelefono3", query = "SELECT e FROM Empresas e WHERE e.telefono3 = :telefono3"),
    @NamedQuery(name = "Empresas.findByTelefono4", query = "SELECT e FROM Empresas e WHERE e.telefono4 = :telefono4"),
    @NamedQuery(name = "Empresas.findByFax", query = "SELECT e FROM Empresas e WHERE e.fax = :fax"),
    @NamedQuery(name = "Empresas.findByDiaQuedan", query = "SELECT e FROM Empresas e WHERE e.diaQuedan = :diaQuedan"),
    @NamedQuery(name = "Empresas.findByCodCorrRep1", query = "SELECT e FROM Empresas e WHERE e.codCorrRep1 = :codCorrRep1"),
    @NamedQuery(name = "Empresas.findByCodCorrRep2", query = "SELECT e FROM Empresas e WHERE e.codCorrRep2 = :codCorrRep2"),
    @NamedQuery(name = "Empresas.findByCodCorrRep3", query = "SELECT e FROM Empresas e WHERE e.codCorrRep3 = :codCorrRep3"),
    @NamedQuery(name = "Empresas.findByCodCorrContador", query = "SELECT e FROM Empresas e WHERE e.codCorrContador = :codCorrContador"),
    @NamedQuery(name = "Empresas.findByCodCorrAuditor", query = "SELECT e FROM Empresas e WHERE e.codCorrAuditor = :codCorrAuditor")})
public class Empresas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CORR_EMPRESA")
    private Integer corrEmpresa;
    @Column(name = "COD_EMPRESA")
    private Integer codEmpresa;
    @Size(max = 80)
    @Column(name = "NOMBRE_EMPRESA")
    private String nombreEmpresa;
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
    @Size(max = 1)
    @Column(name = "TIPO_CONTRIBUYENTE")
    private String tipoContribuyente;
    @Size(max = 20)
    @Column(name = "NO_REGISTRO_FISCAL")
    private String noRegistroFiscal;
    @Size(max = 200)
    @Column(name = "GIRO_EMPRESA")
    private String giroEmpresa;
    @Size(max = 13)
    @Column(name = "TELEFONO1")
    private String telefono1;
    @Size(max = 13)
    @Column(name = "TELEFONO2")
    private String telefono2;
    @Size(max = 13)
    @Column(name = "TELEFONO3")
    private String telefono3;
    @Size(max = 13)
    @Column(name = "TELEFONO4")
    private String telefono4;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 13)
    @Column(name = "FAX")
    private String fax;
    @Size(max = 1)
    @Column(name = "DIA_QUEDAN")
    private String diaQuedan;
    @Column(name = "COD_CORR_REP1")
    private Integer codCorrRep1;
    @Column(name = "COD_CORR_REP2")
    private Integer codCorrRep2;
    @Column(name = "COD_CORR_REP3")
    private Integer codCorrRep3;
    @Column(name = "COD_CORR_CONTADOR")
    private Integer codCorrContador;
    @Column(name = "COD_CORR_AUDITOR")
    private Integer codCorrAuditor;
    @JoinColumn(name = "CORR_EMPRESA", referencedColumnName = "CORR", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private PaisDepMuni paisDepMuni;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "corrEmpresa")
    private List<Bodegas> sucurBodeList;

    public Empresas() {
    }

    public Empresas(Integer corrEmpresa) {
        this.corrEmpresa = corrEmpresa;
    }

    public Integer getCorrEmpresa() {
        return corrEmpresa;
    }

    public void setCorrEmpresa(Integer corrEmpresa) {
        this.corrEmpresa = corrEmpresa;
    }

    public Integer getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(Integer codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
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

    public String getGiroEmpresa() {
        return giroEmpresa;
    }

    public void setGiroEmpresa(String giroEmpresa) {
        this.giroEmpresa = giroEmpresa;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getTelefono3() {
        return telefono3;
    }

    public void setTelefono3(String telefono3) {
        this.telefono3 = telefono3;
    }

    public String getTelefono4() {
        return telefono4;
    }

    public void setTelefono4(String telefono4) {
        this.telefono4 = telefono4;
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

    public Integer getCodCorrRep1() {
        return codCorrRep1;
    }

    public void setCodCorrRep1(Integer codCorrRep1) {
        this.codCorrRep1 = codCorrRep1;
    }

    public Integer getCodCorrRep2() {
        return codCorrRep2;
    }

    public void setCodCorrRep2(Integer codCorrRep2) {
        this.codCorrRep2 = codCorrRep2;
    }

    public Integer getCodCorrRep3() {
        return codCorrRep3;
    }

    public void setCodCorrRep3(Integer codCorrRep3) {
        this.codCorrRep3 = codCorrRep3;
    }

    public Integer getCodCorrContador() {
        return codCorrContador;
    }

    public void setCodCorrContador(Integer codCorrContador) {
        this.codCorrContador = codCorrContador;
    }

    public Integer getCodCorrAuditor() {
        return codCorrAuditor;
    }

    public void setCodCorrAuditor(Integer codCorrAuditor) {
        this.codCorrAuditor = codCorrAuditor;
    }

    public PaisDepMuni getPaisDepMuni() {
        return paisDepMuni;
    }

    public void setPaisDepMuni(PaisDepMuni paisDepMuni) {
        this.paisDepMuni = paisDepMuni;
    }

    @XmlTransient
    public List<Bodegas> getSucurBodeList() {
        return sucurBodeList;
    }

    public void setSucurBodeList(List<Bodegas> sucurBodeList) {
        this.sucurBodeList = sucurBodeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (corrEmpresa != null ? corrEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empresas)) {
            return false;
        }
        Empresas other = (Empresas) object;
        if ((this.corrEmpresa == null && other.corrEmpresa != null) || (this.corrEmpresa != null && !this.corrEmpresa.equals(other.corrEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Empresas[ corrEmpresa=" + corrEmpresa + " ]";
    }
    
}
