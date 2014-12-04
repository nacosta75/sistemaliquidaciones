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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(catalog = "", schema = "")
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
    @Column(name = "CORR_EMPRESA", nullable = false)
    private Integer corrEmpresa;
    @Column(name = "COD_EMPRESA")
    private Integer codEmpresa;
    @Column(name = "NOMBRE_EMPRESA", length = 80)
    private String nombreEmpresa;
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
    @Column(name = "GIRO_EMPRESA", length = 200)
    private String giroEmpresa;
    @Column(length = 13)
    private String telefono1;
    @Column(length = 13)
    private String telefono2;
    @Column(length = 13)
    private String telefono3;
    @Column(length = 13)
    private String telefono4;
    @Column(length = 13)
    private String fax;
    @Column(name = "DIA_QUEDAN", length = 1)
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
    @JoinColumn(name = "CORR_EMPRESA", referencedColumnName = "CORR", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private PaisDepMuni paisDepMuni;
    @OneToMany(mappedBy = "corrEmpresa")
    private List<Correlativos> correlativosList;

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

    public List<Correlativos> getCorrelativosList() {
        return correlativosList;
    }

    public void setCorrelativosList(List<Correlativos> correlativosList) {
        this.correlativosList = correlativosList;
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
        return "sv.com.diserv.liquidaciones.entity.Empresas[ corrEmpresa=" + corrEmpresa + " ]";
    }
    
}
