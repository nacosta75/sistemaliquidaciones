/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "LOTES_EXISTENCIA", catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "LotesExistencia.findAll", query = "SELECT l FROM LotesExistencia l"),
    @NamedQuery(name = "LotesExistencia.findByCorrLote", query = "SELECT l FROM LotesExistencia l WHERE l.corrLote = :corrLote"),
    @NamedQuery(name = "LotesExistencia.findByCorrMov", query = "SELECT l FROM LotesExistencia l WHERE l.corrMov = :corrMov"),
    @NamedQuery(name = "LotesExistencia.findByCorrSucursal", query = "SELECT l FROM LotesExistencia l WHERE l.corrSucursal = :corrSucursal"),
    @NamedQuery(name = "LotesExistencia.findBySim", query = "SELECT l FROM LotesExistencia l WHERE l.sim = :sim"),
    @NamedQuery(name = "LotesExistencia.findByImei", query = "SELECT l FROM LotesExistencia l WHERE l.imei = :imei"),
    @NamedQuery(name = "LotesExistencia.findByDim", query = "SELECT l FROM LotesExistencia l WHERE l.dim = :dim"),
    @NamedQuery(name = "LotesExistencia.findByBanderaEstado", query = "SELECT l FROM LotesExistencia l WHERE l.banderaEstado = :banderaEstado"),
    @NamedQuery(name = "LotesExistencia.findByFechaMov", query = "SELECT l FROM LotesExistencia l WHERE l.fechaMov = :fechaMov")})
public class LotesExistencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CORR_LOTE", nullable = false)
    private Integer corrLote;
    @Column(name = "CORR_MOV")
    private Integer corrMov;
    @Basic(optional = false)
    @Column(name = "CORR_SUCURSAL", nullable = false)
    private int corrSucursal;
    @Column(length = 30)
    private String sim;
    @Column(length = 30)
    private String imei;
    @Column(length = 30)
    private String dim;
    @Basic(optional = false)
    @Column(name = "BANDERA_ESTADO", nullable = false, length = 1)
    private String banderaEstado;
    @Basic(optional = false)
    @Column(name = "FECHA_MOV", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaMov;
    @JoinColumn(name = "CORR_SUCUR", referencedColumnName = "CORR_SUCUR", nullable = false)
    @ManyToOne(optional = false)
    private SucurBode corrSucur;
    @JoinColumn(name = "CORR_ARTICULO", referencedColumnName = "CORR_ARTICULO", nullable = false)
    @ManyToOne(optional = false)
    private Articulos corrArticulo;

    public LotesExistencia() {
    }

    public LotesExistencia(Integer corrLote) {
        this.corrLote = corrLote;
    }

    public LotesExistencia(Integer corrLote, int corrSucursal, String banderaEstado, Date fechaMov) {
        this.corrLote = corrLote;
        this.corrSucursal = corrSucursal;
        this.banderaEstado = banderaEstado;
        this.fechaMov = fechaMov;
    }

    public Integer getCorrLote() {
        return corrLote;
    }

    public void setCorrLote(Integer corrLote) {
        this.corrLote = corrLote;
    }

    public Integer getCorrMov() {
        return corrMov;
    }

    public void setCorrMov(Integer corrMov) {
        this.corrMov = corrMov;
    }

    public int getCorrSucursal() {
        return corrSucursal;
    }

    public void setCorrSucursal(int corrSucursal) {
        this.corrSucursal = corrSucursal;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDim() {
        return dim;
    }

    public void setDim(String dim) {
        this.dim = dim;
    }

    public String getBanderaEstado() {
        return banderaEstado;
    }

    public void setBanderaEstado(String banderaEstado) {
        this.banderaEstado = banderaEstado;
    }

    public Date getFechaMov() {
        return fechaMov;
    }

    public void setFechaMov(Date fechaMov) {
        this.fechaMov = fechaMov;
    }

    public SucurBode getCorrSucur() {
        return corrSucur;
    }

    public void setCorrSucur(SucurBode corrSucur) {
        this.corrSucur = corrSucur;
    }

    public Articulos getCorrArticulo() {
        return corrArticulo;
    }

    public void setCorrArticulo(Articulos corrArticulo) {
        this.corrArticulo = corrArticulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (corrLote != null ? corrLote.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LotesExistencia)) {
            return false;
        }
        LotesExistencia other = (LotesExistencia) object;
        if ((this.corrLote == null && other.corrLote != null) || (this.corrLote != null && !this.corrLote.equals(other.corrLote))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.LotesExistencia[ corrLote=" + corrLote + " ]";
    }
    
}
