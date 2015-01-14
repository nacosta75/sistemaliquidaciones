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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author abraham.acosta
 */
@Entity
@Table(name = "SALDO_EXISTENCIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SaldoExistencia.findAll", query = "SELECT s FROM SaldoExistencia s"),
    @NamedQuery(name = "SaldoExistencia.findByIdsaldoext", query = "SELECT s FROM SaldoExistencia s WHERE s.idsaldoext = :idsaldoext"),
    @NamedQuery(name = "SaldoExistencia.findByAyomes", query = "SELECT s FROM SaldoExistencia s WHERE s.ayomes = :ayomes"),
    @NamedQuery(name = "SaldoExistencia.findBySaldoMesAnt", query = "SELECT s FROM SaldoExistencia s WHERE s.saldoMesAnt = :saldoMesAnt"),
    @NamedQuery(name = "SaldoExistencia.findByEntradasMes", query = "SELECT s FROM SaldoExistencia s WHERE s.entradasMes = :entradasMes"),
    @NamedQuery(name = "SaldoExistencia.findByIdArticulo", query = "SELECT s FROM SaldoExistencia s inner join Articulos a on a.idarticulo=s.idarticulo.idarticulo WHERE a.idarticulo = :idarticulo"),
    @NamedQuery(name = "SaldoExistencia.findBySalidasMes", query = "SELECT s FROM SaldoExistencia s WHERE s.salidasMes = :salidasMes"),
    @NamedQuery(name = "SaldoExistencia.findByIdArticulo", query = "SELECT s FROM SaldoExistencia s inner join Articulos a on a.idarticulo=s.idarticulo.idarticulo WHERE a.idarticulo = :idarticulo"),
    @NamedQuery(name = "SaldoExistencia.findBySaldoAct", query = "SELECT s FROM SaldoExistencia s WHERE s.saldoAct = :saldoAct")})
public class SaldoExistencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDSALDOEXT")
    private Integer idsaldoext;
    @Column(name = "AYOMES")
    private Integer ayomes;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SALDO_MES_ANT")
    private BigDecimal saldoMesAnt;
    @Column(name = "ENTRADAS_MES")
    private BigDecimal entradasMes;
    @Column(name = "SALIDAS_MES")
    private BigDecimal salidasMes;
    @Column(name = "SALDO_ACT")
    private BigDecimal saldoAct;
    @JoinColumn(name = "IDBODEGA", referencedColumnName = "IDBODEGA")
    @ManyToOne
    private Bodegas idbodega;
    @JoinColumn(name = "IDARTICULO", referencedColumnName = "IDARTICULO")
    @ManyToOne(optional = false)
    private Articulos idarticulo;

    public SaldoExistencia() {
    }

    public SaldoExistencia(Integer idsaldoext) {
        this.idsaldoext = idsaldoext;
    }

    public Integer getIdsaldoext() {
        return idsaldoext;
    }

    public void setIdsaldoext(Integer idsaldoext) {
        this.idsaldoext = idsaldoext;
    }

    public Integer getAyomes() {
        return ayomes;
    }

    public void setAyomes(Integer ayomes) {
        this.ayomes = ayomes;
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

    public Bodegas getIdbodega() {
        return idbodega;
    }

    public void setIdbodega(Bodegas idbodega) {
        this.idbodega = idbodega;
    }

    public Articulos getIdarticulo() {
        return idarticulo;
    }

    public void setIdarticulo(Articulos idarticulo) {
        this.idarticulo = idarticulo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsaldoext != null ? idsaldoext.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SaldoExistencia)) {
            return false;
        }
        SaldoExistencia other = (SaldoExistencia) object;
        if ((this.idsaldoext == null && other.idsaldoext != null) || (this.idsaldoext != null && !this.idsaldoext.equals(other.idsaldoext))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SaldoExistencia[ idsaldoext=" + idsaldoext + " ]";
    }
    
}
