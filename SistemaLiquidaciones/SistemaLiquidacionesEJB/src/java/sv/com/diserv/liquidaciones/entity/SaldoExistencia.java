/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(name = "SALDO_EXISTENCIA", catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "SaldoExistencia.findAll", query = "SELECT s FROM SaldoExistencia s"),
    @NamedQuery(name = "SaldoExistencia.findByCorrSaldoExt", query = "SELECT s FROM SaldoExistencia s WHERE s.corrSaldoExt = :corrSaldoExt"),
    @NamedQuery(name = "SaldoExistencia.findByCorrArticulo", query = "SELECT s FROM SaldoExistencia s WHERE s.corrArticulo = :corrArticulo"),
    @NamedQuery(name = "SaldoExistencia.findByCorrSucur", query = "SELECT s FROM SaldoExistencia s WHERE s.corrSucur = :corrSucur"),
    @NamedQuery(name = "SaldoExistencia.findByFecAm", query = "SELECT s FROM SaldoExistencia s WHERE s.fecAm = :fecAm"),
    @NamedQuery(name = "SaldoExistencia.findBySaldoMesAnt", query = "SELECT s FROM SaldoExistencia s WHERE s.saldoMesAnt = :saldoMesAnt"),
    @NamedQuery(name = "SaldoExistencia.findByEntradasMes", query = "SELECT s FROM SaldoExistencia s WHERE s.entradasMes = :entradasMes"),
    @NamedQuery(name = "SaldoExistencia.findBySalidasMes", query = "SELECT s FROM SaldoExistencia s WHERE s.salidasMes = :salidasMes"),
    @NamedQuery(name = "SaldoExistencia.findBySaldoAct", query = "SELECT s FROM SaldoExistencia s WHERE s.saldoAct = :saldoAct")})
public class SaldoExistencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CORR_SALDO_EXT", nullable = false)
    private Integer corrSaldoExt;
    @Basic(optional = false)
    @Column(name = "CORR_ARTICULO", nullable = false)
    private int corrArticulo;
    @Column(name = "CORR_SUCUR")
    private Integer corrSucur;
    @Column(name = "FEC_AM")
    private Integer fecAm;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SALDO_MES_ANT", precision = 12, scale = 2)
    private BigDecimal saldoMesAnt;
    @Column(name = "ENTRADAS_MES", precision = 12, scale = 2)
    private BigDecimal entradasMes;
    @Column(name = "SALIDAS_MES", precision = 12, scale = 2)
    private BigDecimal salidasMes;
    @Column(name = "SALDO_ACT", precision = 12, scale = 2)
    private BigDecimal saldoAct;

    public SaldoExistencia() {
    }

    public SaldoExistencia(Integer corrSaldoExt) {
        this.corrSaldoExt = corrSaldoExt;
    }

    public SaldoExistencia(Integer corrSaldoExt, int corrArticulo) {
        this.corrSaldoExt = corrSaldoExt;
        this.corrArticulo = corrArticulo;
    }

    public Integer getCorrSaldoExt() {
        return corrSaldoExt;
    }

    public void setCorrSaldoExt(Integer corrSaldoExt) {
        this.corrSaldoExt = corrSaldoExt;
    }

    public int getCorrArticulo() {
        return corrArticulo;
    }

    public void setCorrArticulo(int corrArticulo) {
        this.corrArticulo = corrArticulo;
    }

    public Integer getCorrSucur() {
        return corrSucur;
    }

    public void setCorrSucur(Integer corrSucur) {
        this.corrSucur = corrSucur;
    }

    public Integer getFecAm() {
        return fecAm;
    }

    public void setFecAm(Integer fecAm) {
        this.fecAm = fecAm;
    }

    public BigDecimal getSaldoMesAnt() {
        return saldoMesAnt;
    }

    public void setSaldoMesAnt(BigDecimal saldoMesAnt) {
        this.saldoMesAnt = saldoMesAnt;
    }

    public BigDecimal getEntradasMes() {
        return entradasMes;
    }

    public void setEntradasMes(BigDecimal entradasMes) {
        this.entradasMes = entradasMes;
    }

    public BigDecimal getSalidasMes() {
        return salidasMes;
    }

    public void setSalidasMes(BigDecimal salidasMes) {
        this.salidasMes = salidasMes;
    }

    public BigDecimal getSaldoAct() {
        return saldoAct;
    }

    public void setSaldoAct(BigDecimal saldoAct) {
        this.saldoAct = saldoAct;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (corrSaldoExt != null ? corrSaldoExt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaldoExistencia)) {
            return false;
        }
        SaldoExistencia other = (SaldoExistencia) object;
        if ((this.corrSaldoExt == null && other.corrSaldoExt != null) || (this.corrSaldoExt != null && !this.corrSaldoExt.equals(other.corrSaldoExt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.SaldoExistencia[ corrSaldoExt=" + corrSaldoExt + " ]";
    }
    
}
