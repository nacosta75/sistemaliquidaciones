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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
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
@Table(name = "ARTICULOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Articulos.findAll", query = "SELECT a FROM Articulos a"),
    @NamedQuery(name = "Articulos.findByIdarticulo", query = "SELECT a FROM Articulos a WHERE a.idarticulo = :idarticulo"),
    @NamedQuery(name = "Articulos.findByCodarticulo", query = "SELECT a FROM Articulos a WHERE a.codarticulo = :codarticulo"),
    @NamedQuery(name = "Articulos.findByDescarticulo", query = "SELECT a FROM Articulos a WHERE a.descarticulo = :descarticulo"),
    @NamedQuery(name = "Articulos.findByIdtipoarticulo", query = "SELECT a FROM Articulos a WHERE a.idtipoarticulo = :idtipoarticulo"),
    @NamedQuery(name = "Articulos.findByCostocompact", query = "SELECT a FROM Articulos a WHERE a.costocompact = :costocompact"),
    @NamedQuery(name = "Articulos.findByCostocompant", query = "SELECT a FROM Articulos a WHERE a.costocompant = :costocompant"),
    @NamedQuery(name = "Articulos.findByCostofobact", query = "SELECT a FROM Articulos a WHERE a.costofobact = :costofobact"),
    @NamedQuery(name = "Articulos.findByCostofobant", query = "SELECT a FROM Articulos a WHERE a.costofobant = :costofobant"),
    @NamedQuery(name = "Articulos.findByCostopromact", query = "SELECT a FROM Articulos a WHERE a.costopromact = :costopromact"),
    @NamedQuery(name = "Articulos.findByCostopromant", query = "SELECT a FROM Articulos a WHERE a.costopromant = :costopromant"),
    @NamedQuery(name = "Articulos.findByFechaultcompra", query = "SELECT a FROM Articulos a WHERE a.fechaultcompra = :fechaultcompra"),
    @NamedQuery(name = "Articulos.findByFechaultventa", query = "SELECT a FROM Articulos a WHERE a.fechaultventa = :fechaultventa"),
    @NamedQuery(name = "Articulos.findByIdusuariocrea", query = "SELECT a FROM Articulos a WHERE a.idusuariocrea = :idusuariocrea"),
    @NamedQuery(name = "Articulos.findByFechacrea", query = "SELECT a FROM Articulos a WHERE a.fechacrea = :fechacrea"),
    @NamedQuery(name = "Articulos.findByIdusuariomod", query = "SELECT a FROM Articulos a WHERE a.idusuariomod = :idusuariomod"),
    @NamedQuery(name = "Articulos.findByFechamod", query = "SELECT a FROM Articulos a WHERE a.fechamod = :fechamod")})
public class Articulos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDARTICULO")
    @SequenceGenerator(name = "SEQ_ARTICULOS", sequenceName = "SEQ_ARTICULOS")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ARTICULOS")
    private Integer idarticulo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "CODARTICULO")
    private String codarticulo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "DESCARTICULO")
    private String descarticulo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDTIPOARTICULO")
    private int idtipoarticulo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "COSTOCOMPACT")
    private BigDecimal costocompact;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COSTOCOMPANT")
    private BigDecimal costocompant;
    @Column(name = "COSTOFOBACT")
    private BigDecimal costofobact;
    @Column(name = "COSTOFOBANT")
    private BigDecimal costofobant;
    @Column(name = "COSTOPROMACT")
    private BigDecimal costopromact;
    @Column(name = "COSTOPROMANT")
    private BigDecimal costopromant;
    @Column(name = "FECHAULTCOMPRA")
    @Temporal(TemporalType.DATE)
    private Date fechaultcompra;
    @Column(name = "FECHAULTVENTA")
    @Temporal(TemporalType.DATE)
    private Date fechaultventa;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idarticulo")
    private List<SaldoExistencia> saldoExistenciaList;
    @JoinColumn(name = "IDUMEDIDA", referencedColumnName = "IDUMEDIDA")
    @ManyToOne(optional = false)
    private UnidadesMed idumedida;
    @JoinColumn(name = "IDMARCA", referencedColumnName = "IDMARCA")
    @ManyToOne(optional = false)
    private MarcaArticulo idmarca;
    @JoinColumn(name = "IDLINEA", referencedColumnName = "IDLINEA")
    @ManyToOne(optional = false)
    private LineaArticulo idlinea;
    @JoinColumn(name = "IDEMPRESA", referencedColumnName = "IDEMPRESA")
    @ManyToOne(optional = false)
    private Empresas idempresa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idarticulo")
    private List<LotesExistencia> lotesExistenciaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idarticulo")
    private List<MovimientosDet> movimientosDetList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idarticulo")
    private List<DetListaPrecio> detListaPrecioList;

    public Articulos() {
    }

    public Articulos(Integer idarticulo) {
        this.idarticulo = idarticulo;
    }

    public Articulos(Integer idarticulo, String codarticulo, String descarticulo, int idtipoarticulo, BigDecimal costocompact, BigDecimal costocompant, int idusuariocrea) {
        this.idarticulo = idarticulo;
        this.codarticulo = codarticulo;
        this.descarticulo = descarticulo;
        this.idtipoarticulo = idtipoarticulo;
        this.costocompact = costocompact;
        this.costocompant = costocompant;
        this.idusuariocrea = idusuariocrea;
    }

    public Integer getIdarticulo() {
        return idarticulo;
    }

    public void setIdarticulo(Integer idarticulo) {
        this.idarticulo = idarticulo;
    }

    public String getCodarticulo() {
        return codarticulo;
    }

    public void setCodarticulo(String codarticulo) {
        this.codarticulo = codarticulo;
    }

    public String getDescarticulo() {
        return descarticulo;
    }

    public void setDescarticulo(String descarticulo) {
        this.descarticulo = descarticulo;
    }

    public int getIdtipoarticulo() {
        return idtipoarticulo;
    }

    public void setIdtipoarticulo(int idtipoarticulo) {
        this.idtipoarticulo = idtipoarticulo;
    }

    public BigDecimal getCostocompact() {
        return costocompact;
    }

    public void setCostocompact(BigDecimal costocompact) {
        this.costocompact = costocompact;
    }

    public BigDecimal getCostocompant() {
        return costocompant;
    }

    public void setCostocompant(BigDecimal costocompant) {
        this.costocompant = costocompant;
    }

    public BigDecimal getCostofobact() {
        return costofobact;
    }

    public void setCostofobact(BigDecimal costofobact) {
        this.costofobact = costofobact;
    }

    public BigDecimal getCostofobant() {
        return costofobant;
    }

    public void setCostofobant(BigDecimal costofobant) {
        this.costofobant = costofobant;
    }

    public BigDecimal getCostopromact() {
        return costopromact;
    }

    public void setCostopromact(BigDecimal costopromact) {
        this.costopromact = costopromact;
    }

    public BigDecimal getCostopromant() {
        return costopromant;
    }

    public void setCostopromant(BigDecimal costopromant) {
        this.costopromant = costopromant;
    }

    public Date getFechaultcompra() {
        return fechaultcompra;
    }

    public void setFechaultcompra(Date fechaultcompra) {
        this.fechaultcompra = fechaultcompra;
    }

    public Date getFechaultventa() {
        return fechaultventa;
    }

    public void setFechaultventa(Date fechaultventa) {
        this.fechaultventa = fechaultventa;
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
    public List<SaldoExistencia> getSaldoExistenciaList() {
        return saldoExistenciaList;
    }

    public void setSaldoExistenciaList(List<SaldoExistencia> saldoExistenciaList) {
        this.saldoExistenciaList = saldoExistenciaList;
    }

    public UnidadesMed getIdumedida() {
        return idumedida;
    }

    public void setIdumedida(UnidadesMed idumedida) {
        this.idumedida = idumedida;
    }

    public MarcaArticulo getIdmarca() {
        return idmarca;
    }

    public void setIdmarca(MarcaArticulo idmarca) {
        this.idmarca = idmarca;
    }

    public LineaArticulo getIdlinea() {
        return idlinea;
    }

    public void setIdlinea(LineaArticulo idlinea) {
        this.idlinea = idlinea;
    }

    public Empresas getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(Empresas idempresa) {
        this.idempresa = idempresa;
    }

    @XmlTransient
    public List<LotesExistencia> getLotesExistenciaList() {
        return lotesExistenciaList;
    }

    public void setLotesExistenciaList(List<LotesExistencia> lotesExistenciaList) {
        this.lotesExistenciaList = lotesExistenciaList;
    }

    @XmlTransient
    public List<MovimientosDet> getMovimientosDetList() {
        return movimientosDetList;
    }

    public void setMovimientosDetList(List<MovimientosDet> movimientosDetList) {
        this.movimientosDetList = movimientosDetList;
    }

    @XmlTransient
    public List<DetListaPrecio> getDetListaPrecioList() {
        return detListaPrecioList;
    }

    public void setDetListaPrecioList(List<DetListaPrecio> detListaPrecioList) {
        this.detListaPrecioList = detListaPrecioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idarticulo != null ? idarticulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Articulos)) {
            return false;
        }
        Articulos other = (Articulos) object;
        if ((this.idarticulo == null && other.idarticulo != null) || (this.idarticulo != null && !this.idarticulo.equals(other.idarticulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Articulos[ idarticulo=" + idarticulo + " ]";
    }
    
}
