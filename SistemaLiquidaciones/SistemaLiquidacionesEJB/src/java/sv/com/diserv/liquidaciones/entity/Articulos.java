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

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "Articulos.findAll", query = "SELECT a FROM Articulos a"),
    @NamedQuery(name = "Articulos.findByCorrArticulo", query = "SELECT a FROM Articulos a WHERE a.corrArticulo = :corrArticulo"),
    @NamedQuery(name = "Articulos.findByCodArticulo", query = "SELECT a FROM Articulos a WHERE a.codArticulo = :codArticulo"),
    @NamedQuery(name = "Articulos.findByDescripcion", query = "SELECT a FROM Articulos a WHERE a.descripcion = :descripcion"),
    @NamedQuery(name = "Articulos.findByTipoMovimiento", query = "SELECT a FROM Articulos a WHERE a.tipoMovimiento = :tipoMovimiento"),
    @NamedQuery(name = "Articulos.findByTipoInventario", query = "SELECT a FROM Articulos a WHERE a.tipoInventario = :tipoInventario"),
    @NamedQuery(name = "Articulos.findByCostoCompAct", query = "SELECT a FROM Articulos a WHERE a.costoCompAct = :costoCompAct"),
    @NamedQuery(name = "Articulos.findByCostoCompAnt", query = "SELECT a FROM Articulos a WHERE a.costoCompAnt = :costoCompAnt"),
    @NamedQuery(name = "Articulos.findByCostoFobAct", query = "SELECT a FROM Articulos a WHERE a.costoFobAct = :costoFobAct"),
    @NamedQuery(name = "Articulos.findByCostoFobAnt", query = "SELECT a FROM Articulos a WHERE a.costoFobAnt = :costoFobAnt"),
    @NamedQuery(name = "Articulos.findByCostoPromAct", query = "SELECT a FROM Articulos a WHERE a.costoPromAct = :costoPromAct"),
    @NamedQuery(name = "Articulos.findByCostoPromAnt", query = "SELECT a FROM Articulos a WHERE a.costoPromAnt = :costoPromAnt"),
    @NamedQuery(name = "Articulos.findByFechaUltCompra", query = "SELECT a FROM Articulos a WHERE a.fechaUltCompra = :fechaUltCompra"),
    @NamedQuery(name = "Articulos.findByFechaUltVenta", query = "SELECT a FROM Articulos a WHERE a.fechaUltVenta = :fechaUltVenta"),
    @NamedQuery(name = "Articulos.findByTipoArticulo", query = "SELECT a FROM Articulos a WHERE a.tipoArticulo = :tipoArticulo")})
public class Articulos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CORR_ARTICULO", nullable = false)
    private Integer corrArticulo;
    @Basic(optional = false)
    @Column(name = "COD_ARTICULO", nullable = false, length = 40)
    private String codArticulo;
    @Basic(optional = false)
    @Column(nullable = false, length = 80)
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "TIPO_MOVIMIENTO", nullable = false, length = 1)
    private String tipoMovimiento;
    @Basic(optional = false)
    @Column(name = "TIPO_INVENTARIO", nullable = false, length = 1)
    private String tipoInventario;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "COSTO_COMP_ACT", nullable = false, precision = 14, scale = 4)
    private BigDecimal costoCompAct;
    @Basic(optional = false)
    @Column(name = "COSTO_COMP_ANT", nullable = false, precision = 14, scale = 4)
    private BigDecimal costoCompAnt;
    @Column(name = "COSTO_FOB_ACT", precision = 14, scale = 4)
    private BigDecimal costoFobAct;
    @Column(name = "COSTO_FOB_ANT", precision = 14, scale = 4)
    private BigDecimal costoFobAnt;
    @Column(name = "COSTO_PROM_ACT", precision = 14, scale = 4)
    private BigDecimal costoPromAct;
    @Column(name = "COSTO_PROM_ANT", precision = 14, scale = 4)
    private BigDecimal costoPromAnt;
    @Column(name = "FECHA_ULT_COMPRA", precision = 14, scale = 4)
    private BigDecimal fechaUltCompra;
    @Column(name = "FECHA_ULT_VENTA", precision = 14, scale = 4)
    private BigDecimal fechaUltVenta;
    @Basic(optional = false)
    @Column(name = "TIPO_ARTICULO", nullable = false, length = 1)
    private String tipoArticulo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "corrArticulo")
    private List<Detalle> detalleList;
    @JoinColumn(name = "CORR_UNIDAD", referencedColumnName = "CORR_UNIDAD", nullable = false)
    @ManyToOne(optional = false)
    private UnidadesMed corrUnidad;
    @JoinColumn(name = "CORR_GRUPO", referencedColumnName = "CORR_GRUPO")
    @ManyToOne
    private Grupo corrGrupo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "corrArticulo")
    private List<LotesExistencia> lotesExistenciaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "corrArticulo")
    private List<DetListaPrecio> detListaPrecioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "corrArticulo")
    private List<Existencia> existenciaList;

    public Articulos() {
    }

    public Articulos(Integer corrArticulo) {
        this.corrArticulo = corrArticulo;
    }

    public Articulos(Integer corrArticulo, String codArticulo, String descripcion, String tipoMovimiento, String tipoInventario, BigDecimal costoCompAct, BigDecimal costoCompAnt, String tipoArticulo) {
        this.corrArticulo = corrArticulo;
        this.codArticulo = codArticulo;
        this.descripcion = descripcion;
        this.tipoMovimiento = tipoMovimiento;
        this.tipoInventario = tipoInventario;
        this.costoCompAct = costoCompAct;
        this.costoCompAnt = costoCompAnt;
        this.tipoArticulo = tipoArticulo;
    }

    public Integer getCorrArticulo() {
        return corrArticulo;
    }

    public void setCorrArticulo(Integer corrArticulo) {
        this.corrArticulo = corrArticulo;
    }

    public String getCodArticulo() {
        return codArticulo;
    }

    public void setCodArticulo(String codArticulo) {
        this.codArticulo = codArticulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getTipoInventario() {
        return tipoInventario;
    }

    public void setTipoInventario(String tipoInventario) {
        this.tipoInventario = tipoInventario;
    }

    public BigDecimal getCostoCompAct() {
        return costoCompAct;
    }

    public void setCostoCompAct(BigDecimal costoCompAct) {
        this.costoCompAct = costoCompAct;
    }

    public BigDecimal getCostoCompAnt() {
        return costoCompAnt;
    }

    public void setCostoCompAnt(BigDecimal costoCompAnt) {
        this.costoCompAnt = costoCompAnt;
    }

    public BigDecimal getCostoFobAct() {
        return costoFobAct;
    }

    public void setCostoFobAct(BigDecimal costoFobAct) {
        this.costoFobAct = costoFobAct;
    }

    public BigDecimal getCostoFobAnt() {
        return costoFobAnt;
    }

    public void setCostoFobAnt(BigDecimal costoFobAnt) {
        this.costoFobAnt = costoFobAnt;
    }

    public BigDecimal getCostoPromAct() {
        return costoPromAct;
    }

    public void setCostoPromAct(BigDecimal costoPromAct) {
        this.costoPromAct = costoPromAct;
    }

    public BigDecimal getCostoPromAnt() {
        return costoPromAnt;
    }

    public void setCostoPromAnt(BigDecimal costoPromAnt) {
        this.costoPromAnt = costoPromAnt;
    }

    public BigDecimal getFechaUltCompra() {
        return fechaUltCompra;
    }

    public void setFechaUltCompra(BigDecimal fechaUltCompra) {
        this.fechaUltCompra = fechaUltCompra;
    }

    public BigDecimal getFechaUltVenta() {
        return fechaUltVenta;
    }

    public void setFechaUltVenta(BigDecimal fechaUltVenta) {
        this.fechaUltVenta = fechaUltVenta;
    }

    public String getTipoArticulo() {
        return tipoArticulo;
    }

    public void setTipoArticulo(String tipoArticulo) {
        this.tipoArticulo = tipoArticulo;
    }

    public List<Detalle> getDetalleList() {
        return detalleList;
    }

    public void setDetalleList(List<Detalle> detalleList) {
        this.detalleList = detalleList;
    }

    public UnidadesMed getCorrUnidad() {
        return corrUnidad;
    }

    public void setCorrUnidad(UnidadesMed corrUnidad) {
        this.corrUnidad = corrUnidad;
    }

    public Grupo getCorrGrupo() {
        return corrGrupo;
    }

    public void setCorrGrupo(Grupo corrGrupo) {
        this.corrGrupo = corrGrupo;
    }

    public List<LotesExistencia> getLotesExistenciaList() {
        return lotesExistenciaList;
    }

    public void setLotesExistenciaList(List<LotesExistencia> lotesExistenciaList) {
        this.lotesExistenciaList = lotesExistenciaList;
    }

    public List<DetListaPrecio> getDetListaPrecioList() {
        return detListaPrecioList;
    }

    public void setDetListaPrecioList(List<DetListaPrecio> detListaPrecioList) {
        this.detListaPrecioList = detListaPrecioList;
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
        hash += (corrArticulo != null ? corrArticulo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Articulos)) {
            return false;
        }
        Articulos other = (Articulos) object;
        if ((this.corrArticulo == null && other.corrArticulo != null) || (this.corrArticulo != null && !this.corrArticulo.equals(other.corrArticulo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.Articulos[ corrArticulo=" + corrArticulo + " ]";
    }
    
}
