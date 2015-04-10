/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
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
@Table(name = "MOVIMIENTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Movimientos.findAll", query = "SELECT m FROM Movimientos m"),
    @NamedQuery(name = "Movimientos.findByIdmov", query = "SELECT m FROM Movimientos m WHERE m.idmov = :idmov"),
    @NamedQuery(name = "Movimientos.findByFechamov", query = "SELECT m FROM Movimientos m WHERE m.fechamov = :fechamov"),
    @NamedQuery(name = "Movimientos.findByNodoc", query = "SELECT m FROM Movimientos m WHERE m.nodoc = :nodoc"),
    @NamedQuery(name = "Movimientos.findByIdcaja", query = "SELECT m FROM Movimientos m WHERE m.idcaja = :idcaja"),
    @NamedQuery(name = "Movimientos.findByNombre", query = "SELECT m FROM Movimientos m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Movimientos.findByNoRegistro", query = "SELECT m FROM Movimientos m WHERE m.noRegistro = :noRegistro"),
    @NamedQuery(name = "Movimientos.findByDireccion", query = "SELECT m FROM Movimientos m WHERE m.direccion = :direccion"),
    @NamedQuery(name = "Movimientos.findByObserva1", query = "SELECT m FROM Movimientos m WHERE m.observa1 = :observa1"),
    @NamedQuery(name = "Movimientos.findByEstado", query = "SELECT m FROM Movimientos m WHERE m.estado = :estado"),
    @NamedQuery(name = "Movimientos.findAllByTipo", query = "SELECT m FROM Movimientos m WHERE m.idtipomov.idtipomov = :idtipomov")})
public class Movimientos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDMOV")
    @SequenceGenerator(allocationSize =1,name = "SEQ_MOVIMIENTOS", sequenceName = "SEQ_MOVIMIENTOS")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MOVIMIENTOS")
    private Integer idmov;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAMOV")
    @Temporal(TemporalType.DATE)
    private Date fechamov;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NODOC")
    private Integer nodoc;
    @Column(name = "IDCAJA")
    private Integer idcaja;
    @Size(max = 80)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 20)
    @Column(name = "NO_REGISTRO")
    private String noRegistro;
    @Size(max = 100)
    @Column(name = "DIRECCION")
    private String direccion;
    @Size(max = 100)
    @Column(name = "OBSERVA1")
    private String observa1;
    @Size(max = 1)
    @Column(name = "ESTADO")
    private String estado;
    @JoinColumn(name = "IDTIPOMOV", referencedColumnName = "IDTIPOMOV")
    @ManyToOne(optional = false)
    private TiposMov idtipomov;
    @JoinColumn(name = "IDSUCURSAL", referencedColumnName = "IDSUCURSAL")
    @ManyToOne
    private Sucursales idsucursal;
    @JoinColumn(name = "IDPERSONA", referencedColumnName = "IDPERSONA")
    @ManyToOne
    private Personas idpersona;
    @JoinColumn(name = "IDBODEGAENTRADA", referencedColumnName = "IDBODEGA")
    @ManyToOne
    private Bodegas idbodegaentrada;
    @JoinColumn(name = "IDBODEGASALIDA", referencedColumnName = "IDBODEGA")
    @ManyToOne
    private Bodegas idbodegasalida;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idmov")
    private List<RelacionLiquidaciones> relacionLiquidacionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idmov")
    private List<Liquidaciones> liquidacionesList;
    @OneToMany(mappedBy = "idmov")
    private List<LotesExistencia> lotesExistenciaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idmov")
    private List<MovimientosDet> movimientosDetList;

    public Movimientos() {
    }

    public Movimientos(Integer idmov) {
        this.idmov = idmov;
    }

    public Movimientos(Integer idmov, Date fechamov, int nodoc) {
        this.idmov = idmov;
        this.fechamov = fechamov;
        this.nodoc = nodoc;
    }

    public Integer getIdmov() {
        return idmov;
    }

    public void setIdmov(Integer idmov) {
        this.idmov = idmov;
    }

    public Date getFechamov() {
        return fechamov;
    }

    public void setFechamov(Date fechamov) {
        this.fechamov = fechamov;
    }

    public Integer getNodoc() {
        return nodoc;
    }

    public void setNodoc(Integer nodoc) {
        this.nodoc = nodoc;
    }

    public Integer getIdcaja() {
        return idcaja;
    }

    public void setIdcaja(Integer idcaja) {
        this.idcaja = idcaja;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNoRegistro() {
        return noRegistro;
    }

    public void setNoRegistro(String noRegistro) {
        this.noRegistro = noRegistro;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getObserva1() {
        return observa1;
    }

    public void setObserva1(String observa1) {
        this.observa1 = observa1;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public TiposMov getIdtipomov() {
        return idtipomov;
    }

    public void setIdtipomov(TiposMov idtipomov) {
        this.idtipomov = idtipomov;
    }

    public Sucursales getIdsucursal() {
        return idsucursal;
    }

    public void setIdsucursal(Sucursales idsucursal) {
        this.idsucursal = idsucursal;
    }

    public Personas getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(Personas idpersona) {
        this.idpersona = idpersona;
    }

    public Bodegas getIdbodegaentrada() {
        return idbodegaentrada;
    }

    public void setIdbodegaentrada(Bodegas idbodegaentrada) {
        this.idbodegaentrada = idbodegaentrada;
    }

    public Bodegas getIdbodegasalida() {
        return idbodegasalida;
    }

    public void setIdbodegasalida(Bodegas idbodegasalida) {
        this.idbodegasalida = idbodegasalida;
    }

    @XmlTransient
    public List<RelacionLiquidaciones> getRelacionLiquidacionesList() {
        return relacionLiquidacionesList;
    }

    public void setRelacionLiquidacionesList(List<RelacionLiquidaciones> relacionLiquidacionesList) {
        this.relacionLiquidacionesList = relacionLiquidacionesList;
    }

    @XmlTransient
    public List<Liquidaciones> getLiquidacionesList() {
        return liquidacionesList;
    }

    public void setLiquidacionesList(List<Liquidaciones> liquidacionesList) {
        this.liquidacionesList = liquidacionesList;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmov != null ? idmov.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Movimientos)) {
            return false;
        }
        Movimientos other = (Movimientos) object;
        if ((this.idmov == null && other.idmov != null) || (this.idmov != null && !this.idmov.equals(other.idmov))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Movimientos[ idmov=" + idmov + " ]";
    }
    
}
