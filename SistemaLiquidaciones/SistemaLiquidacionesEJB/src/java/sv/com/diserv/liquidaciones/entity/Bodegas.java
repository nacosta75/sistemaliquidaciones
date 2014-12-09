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
@Table(name = "SUCUR_BODE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bodegas.findAll", query = "SELECT s FROM Bodegas s"),
    @NamedQuery(name = "Bodegas.findByCorrSucur", query = "SELECT s FROM Bodegas s WHERE s.corrSucur = :corrSucur"),
    @NamedQuery(name = "Bodegas.findByNombreSucBod", query = "SELECT s FROM Bodegas s WHERE s.nombreSucBod = :nombreSucBod"),
    @NamedQuery(name = "Bodegas.findByCalleOPasaje", query = "SELECT s FROM Bodegas s WHERE s.calleOPasaje = :calleOPasaje"),
    @NamedQuery(name = "Bodegas.findByColonia", query = "SELECT s FROM Bodegas s WHERE s.colonia = :colonia"),
    @NamedQuery(name = "Bodegas.findByCiudad", query = "SELECT s FROM Bodegas s WHERE s.ciudad = :ciudad"),
    @NamedQuery(name = "Bodegas.findByTelefono1", query = "SELECT s FROM Bodegas s WHERE s.telefono1 = :telefono1"),
    @NamedQuery(name = "Bodegas.findByTelefono2", query = "SELECT s FROM Bodegas s WHERE s.telefono2 = :telefono2"),
    @NamedQuery(name = "Bodegas.findByTelefono3", query = "SELECT s FROM Bodegas s WHERE s.telefono3 = :telefono3"),
    @NamedQuery(name = "Bodegas.findByTelefono4", query = "SELECT s FROM Bodegas s WHERE s.telefono4 = :telefono4"),
    @NamedQuery(name = "Bodegas.findByFax", query = "SELECT s FROM Bodegas s WHERE s.fax = :fax"),
    @NamedQuery(name = "Bodegas.findByCodCorrJefe", query = "SELECT s FROM Bodegas s WHERE s.codCorrJefe = :codCorrJefe"),
    @NamedQuery(name = "Bodegas.findByCodCorrSubjefe", query = "SELECT s FROM Bodegas s WHERE s.codCorrSubjefe = :codCorrSubjefe")})
public class Bodegas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CORR_SUCUR")
    private Integer corrSucur;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "NOMBRE_SUC_BOD")
    private String nombreSucBod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "CALLE_O_PASAJE")
    private String calleOPasaje;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "COLONIA")
    private String colonia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "CIUDAD")
    private String ciudad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "TELEFONO1_")
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "COD_CORR_JEFE")
    private int codCorrJefe;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COD_CORR_SUBJEFE")
    private int codCorrSubjefe;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "corrSucurHijo")
    private List<Bodegas> sucurBodeList;
    @JoinColumn(name = "CORR_SUCUR_HIJO", referencedColumnName = "CORR_SUCUR")
    @ManyToOne(optional = false)
    private Bodegas corrSucurHijo;
    @JoinColumn(name = "CORR_EMPRESA", referencedColumnName = "CORR_EMPRESA")
    @ManyToOne(optional = false)
    private Empresas corrEmpresa;

    public Bodegas() {
    }

    public Bodegas(Integer corrSucur) {
        this.corrSucur = corrSucur;
    }

    public Bodegas(Integer corrSucur, String nombreSucBod, String calleOPasaje, String colonia, String ciudad, String telefono1, int codCorrJefe, int codCorrSubjefe) {
        this.corrSucur = corrSucur;
        this.nombreSucBod = nombreSucBod;
        this.calleOPasaje = calleOPasaje;
        this.colonia = colonia;
        this.ciudad = ciudad;
        this.telefono1 = telefono1;
        this.codCorrJefe = codCorrJefe;
        this.codCorrSubjefe = codCorrSubjefe;
    }

    public Integer getCorrSucur() {
        return corrSucur;
    }

    public void setCorrSucur(Integer corrSucur) {
        this.corrSucur = corrSucur;
    }

    public String getNombreSucBod() {
        return nombreSucBod;
    }

    public void setNombreSucBod(String nombreSucBod) {
        this.nombreSucBod = nombreSucBod;
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

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
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

    public int getCodCorrJefe() {
        return codCorrJefe;
    }

    public void setCodCorrJefe(int codCorrJefe) {
        this.codCorrJefe = codCorrJefe;
    }

    public int getCodCorrSubjefe() {
        return codCorrSubjefe;
    }

    public void setCodCorrSubjefe(int codCorrSubjefe) {
        this.codCorrSubjefe = codCorrSubjefe;
    }

    @XmlTransient
    public List<Bodegas> getBodegasList() {
        return sucurBodeList;
    }

    public void setBodegasList(List<Bodegas> sucurBodeList) {
        this.sucurBodeList = sucurBodeList;
    }

    public Bodegas getCorrSucurHijo() {
        return corrSucurHijo;
    }

    public void setCorrSucurHijo(Bodegas corrSucurHijo) {
        this.corrSucurHijo = corrSucurHijo;
    }

    public Empresas getCorrEmpresa() {
        return corrEmpresa;
    }

    public void setCorrEmpresa(Empresas corrEmpresa) {
        this.corrEmpresa = corrEmpresa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (corrSucur != null ? corrSucur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bodegas)) {
            return false;
        }
        Bodegas other = (Bodegas) object;
        if ((this.corrSucur == null && other.corrSucur != null) || (this.corrSucur != null && !this.corrSucur.equals(other.corrSucur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Bodegas[ corrSucur=" + corrSucur + " ]";
    }
    
}
