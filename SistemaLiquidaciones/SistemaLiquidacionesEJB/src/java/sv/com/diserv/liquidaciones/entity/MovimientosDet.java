/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author abraham.acosta
 */
@Entity
@Table(name = "MOVIMIENTOS_DET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MovimientosDet.findAll", query = "SELECT m FROM MovimientosDet m"),
    @NamedQuery(name = "MovimientosDet.findByIdmovd", query = "SELECT m FROM MovimientosDet m WHERE m.idmovd = :idmovd"),
     @NamedQuery(name = "MovimientosDet.findByIdMovimiento", query = "SELECT m FROM MovimientosDet m WHERE m.idmov.idmov = :idMovimiento"),
    @NamedQuery(name = "MovimientosDet.findByCantidad", query = "SELECT m FROM MovimientosDet m WHERE m.cantidad = :cantidad"),
    @NamedQuery(name = "MovimientosDet.findByPrecio", query = "SELECT m FROM MovimientosDet m WHERE m.precio = :precio"),
    @NamedQuery(name = "MovimientosDet.findByValorImpuesto", query = "SELECT m FROM MovimientosDet m WHERE m.valorImpuesto = :valorImpuesto"),
    @NamedQuery(name = "MovimientosDet.findByClaseOperacion", query = "SELECT m FROM MovimientosDet m WHERE m.claseOperacion = :claseOperacion"),
    @NamedQuery(name = "MovimientosDet.findByFechaMov", query = "SELECT m FROM MovimientosDet m WHERE m.fechaMov = :fechaMov"),
    @NamedQuery(name = "MovimientosDet.findByNoDoc", query = "SELECT m FROM MovimientosDet m WHERE m.noDoc = :noDoc"),
    @NamedQuery(name = "MovimientosDet.findByUltCosto", query = "SELECT m FROM MovimientosDet m WHERE m.ultCosto = :ultCosto"),
    @NamedQuery(name = "MovimientosDet.findByCostoProm", query = "SELECT m FROM MovimientosDet m WHERE m.costoProm = :costoProm")})
public class MovimientosDet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDMOVD")
    @SequenceGenerator(allocationSize =1,name = "SEQ_MOVIMIENTOS_DET", sequenceName = "SEQ_MOVIMIENTOS_DET")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MOVIMIENTOS_DET")
    private Integer idmovd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CANTIDAD")
    private BigDecimal cantidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRECIO")
    private BigDecimal precio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VALOR_IMPUESTO")
    private BigDecimal valorImpuesto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "CLASE_OPERACION")
    private String claseOperacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_MOV")
    @Temporal(TemporalType.DATE)
    private Date fechaMov;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NO_DOC")
    private int noDoc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ULT_COSTO")
    private BigDecimal ultCosto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COSTO_PROM")
    private BigDecimal costoProm;
    @JoinColumn(name = "IDMOV", referencedColumnName = "IDMOV")
    @ManyToOne(optional = false)
    private Movimientos idmov;
    @JoinColumn(name = "IDLISTA", referencedColumnName = "IDLISTA")
    @ManyToOne(optional = false)
    private EncListaPrecio idlista;
    @JoinColumn(name = "IDARTICULO", referencedColumnName = "IDARTICULO")
    @ManyToOne(optional = false)
    private Articulos idarticulo;

    public MovimientosDet() {
    }

    public MovimientosDet(Integer idmovd) {
        this.idmovd = idmovd;
    }

    public MovimientosDet(Integer idmovd, BigDecimal cantidad, BigDecimal precio, BigDecimal valorImpuesto, String claseOperacion, Date fechaMov, int noDoc, BigDecimal ultCosto, BigDecimal costoProm) {
        this.idmovd = idmovd;
        this.cantidad = cantidad;
        this.precio = precio;
        this.valorImpuesto = valorImpuesto;
        this.claseOperacion = claseOperacion;
        this.fechaMov = fechaMov;
        this.noDoc = noDoc;
        this.ultCosto = ultCosto;
        this.costoProm = costoProm;
    }

    public Integer getIdmovd() {
        return idmovd;
    }

    public void setIdmovd(Integer idmovd) {
        this.idmovd = idmovd;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getValorImpuesto() {
        return valorImpuesto;
    }

    public void setValorImpuesto(BigDecimal valorImpuesto) {
        this.valorImpuesto = valorImpuesto;
    }

    public String getClaseOperacion() {
        return claseOperacion;
    }

    public void setClaseOperacion(String claseOperacion) {
        this.claseOperacion = claseOperacion;
    }

    public Date getFechaMov() {
        return fechaMov;
    }

    public void setFechaMov(Date fechaMov) {
        this.fechaMov = fechaMov;
    }

    public int getNoDoc() {
        return noDoc;
    }

    public void setNoDoc(int noDoc) {
        this.noDoc = noDoc;
    }

    public BigDecimal getUltCosto() {
        return ultCosto;
    }

    public void setUltCosto(BigDecimal ultCosto) {
        this.ultCosto = ultCosto;
    }

    public BigDecimal getCostoProm() {
        return costoProm;
    }

    public void setCostoProm(BigDecimal costoProm) {
        this.costoProm = costoProm;
    }

    public Movimientos getIdmov() {
        return idmov;
    }

    public void setIdmov(Movimientos idmov) {
        this.idmov = idmov;
    }

    public EncListaPrecio getIdlista() {
        return idlista;
    }

    public void setIdlista(EncListaPrecio idlista) {
        this.idlista = idlista;
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
        hash += (idmovd != null ? idmovd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovimientosDet)) {
            return false;
        }
        MovimientosDet other = (MovimientosDet) object;
        if ((this.idmovd == null && other.idmovd != null) || (this.idmovd != null && !this.idmovd.equals(other.idmovd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.MovimientosDet[ idmovd=" + idmovd + " ]";
    }

}
