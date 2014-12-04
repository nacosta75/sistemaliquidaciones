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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(name = "SUCUR_BODE", catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "SucurBode.findAll", query = "SELECT s FROM SucurBode s"),
    @NamedQuery(name = "SucurBode.findByCorrSucur", query = "SELECT s FROM SucurBode s WHERE s.corrSucur = :corrSucur"),
    @NamedQuery(name = "SucurBode.findByCorrSucurHijo", query = "SELECT s FROM SucurBode s WHERE s.corrSucurHijo = :corrSucurHijo"),
    @NamedQuery(name = "SucurBode.findByCorrEmpresa", query = "SELECT s FROM SucurBode s WHERE s.corrEmpresa = :corrEmpresa"),
    @NamedQuery(name = "SucurBode.findByNombreSucBod", query = "SELECT s FROM SucurBode s WHERE s.nombreSucBod = :nombreSucBod"),
    @NamedQuery(name = "SucurBode.findByCalleOPasaje", query = "SELECT s FROM SucurBode s WHERE s.calleOPasaje = :calleOPasaje"),
    @NamedQuery(name = "SucurBode.findByColonia", query = "SELECT s FROM SucurBode s WHERE s.colonia = :colonia"),
    @NamedQuery(name = "SucurBode.findByCiudad", query = "SELECT s FROM SucurBode s WHERE s.ciudad = :ciudad"),
    @NamedQuery(name = "SucurBode.findByTelefono1", query = "SELECT s FROM SucurBode s WHERE s.telefono1 = :telefono1"),
    @NamedQuery(name = "SucurBode.findByTelefono2", query = "SELECT s FROM SucurBode s WHERE s.telefono2 = :telefono2"),
    @NamedQuery(name = "SucurBode.findByTelefono3", query = "SELECT s FROM SucurBode s WHERE s.telefono3 = :telefono3"),
    @NamedQuery(name = "SucurBode.findByTelefono4", query = "SELECT s FROM SucurBode s WHERE s.telefono4 = :telefono4"),
    @NamedQuery(name = "SucurBode.findByFax", query = "SELECT s FROM SucurBode s WHERE s.fax = :fax"),
    @NamedQuery(name = "SucurBode.findByCodCorrJefe", query = "SELECT s FROM SucurBode s WHERE s.codCorrJefe = :codCorrJefe"),
    @NamedQuery(name = "SucurBode.findByCodCorrSubjefe", query = "SELECT s FROM SucurBode s WHERE s.codCorrSubjefe = :codCorrSubjefe")})
public class SucurBode implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CORR_SUCUR", nullable = false)
    private Integer corrSucur;
    @Basic(optional = false)
    @Column(name = "CORR_SUCUR_HIJO", nullable = false)
    private int corrSucurHijo;
    @Basic(optional = false)
    @Column(name = "CORR_EMPRESA", nullable = false)
    private int corrEmpresa;
    @Basic(optional = false)
    @Column(name = "NOMBRE_SUC_BOD", nullable = false, length = 80)
    private String nombreSucBod;
    @Basic(optional = false)
    @Column(name = "CALLE_O_PASAJE", nullable = false, length = 100)
    private String calleOPasaje;
    @Basic(optional = false)
    @Column(nullable = false, length = 80)
    private String colonia;
    @Basic(optional = false)
    @Column(nullable = false, length = 80)
    private String ciudad;
    @Basic(optional = false)
    @Column(name = "TELEFONO1_", nullable = false, length = 13)
    private String telefono1;
    @Column(length = 13)
    private String telefono2;
    @Column(length = 13)
    private String telefono3;
    @Column(length = 13)
    private String telefono4;
    @Column(length = 13)
    private String fax;
    @Basic(optional = false)
    @Column(name = "COD_CORR_JEFE", nullable = false)
    private int codCorrJefe;
    @Basic(optional = false)
    @Column(name = "COD_CORR_SUBJEFE", nullable = false)
    private int codCorrSubjefe;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "corrSucur")
    private List<LotesExistencia> lotesExistenciaList;
    @OneToMany(mappedBy = "corrSucur")
    private List<Existencia> existenciaList;

    public SucurBode() {
    }

    public SucurBode(Integer corrSucur) {
        this.corrSucur = corrSucur;
    }

    public SucurBode(Integer corrSucur, int corrSucurHijo, int corrEmpresa, String nombreSucBod, String calleOPasaje, String colonia, String ciudad, String telefono1, int codCorrJefe, int codCorrSubjefe) {
        this.corrSucur = corrSucur;
        this.corrSucurHijo = corrSucurHijo;
        this.corrEmpresa = corrEmpresa;
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

    public int getCorrSucurHijo() {
        return corrSucurHijo;
    }

    public void setCorrSucurHijo(int corrSucurHijo) {
        this.corrSucurHijo = corrSucurHijo;
    }

    public int getCorrEmpresa() {
        return corrEmpresa;
    }

    public void setCorrEmpresa(int corrEmpresa) {
        this.corrEmpresa = corrEmpresa;
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

    public List<LotesExistencia> getLotesExistenciaList() {
        return lotesExistenciaList;
    }

    public void setLotesExistenciaList(List<LotesExistencia> lotesExistenciaList) {
        this.lotesExistenciaList = lotesExistenciaList;
    }

    public List<Existencia> getExistenciaList() {
        return existenciaList;
    }

    public void setExistenciaList(List<Existencia> existenciaList) {
        this.existenciaList = existenciaList;
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
        if (!(object instanceof SucurBode)) {
            return false;
        }
        SucurBode other = (SucurBode) object;
        if ((this.corrSucur == null && other.corrSucur != null) || (this.corrSucur != null && !this.corrSucur.equals(other.corrSucur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.SucurBode[ corrSucur=" + corrSucur + " ]";
    }
    
}
