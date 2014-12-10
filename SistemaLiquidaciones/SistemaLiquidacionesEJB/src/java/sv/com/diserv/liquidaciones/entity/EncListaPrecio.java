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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "ENC_LISTA_PRECIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EncListaPrecio.findAll", query = "SELECT e FROM EncListaPrecio e"),
    @NamedQuery(name = "EncListaPrecio.findByIdlista", query = "SELECT e FROM EncListaPrecio e WHERE e.idlista = :idlista"),
    @NamedQuery(name = "EncListaPrecio.findByDescripcionLista", query = "SELECT e FROM EncListaPrecio e WHERE e.descripcionLista = :descripcionLista"),
    @NamedQuery(name = "EncListaPrecio.findByFechaDesde", query = "SELECT e FROM EncListaPrecio e WHERE e.fechaDesde = :fechaDesde"),
    @NamedQuery(name = "EncListaPrecio.findByDechaHasta", query = "SELECT e FROM EncListaPrecio e WHERE e.dechaHasta = :dechaHasta"),
    @NamedQuery(name = "EncListaPrecio.findByIdusuariocrea", query = "SELECT e FROM EncListaPrecio e WHERE e.idusuariocrea = :idusuariocrea"),
    @NamedQuery(name = "EncListaPrecio.findByFechacrea", query = "SELECT e FROM EncListaPrecio e WHERE e.fechacrea = :fechacrea"),
    @NamedQuery(name = "EncListaPrecio.findByIdusuariomod", query = "SELECT e FROM EncListaPrecio e WHERE e.idusuariomod = :idusuariomod"),
    @NamedQuery(name = "EncListaPrecio.findByFechamod", query = "SELECT e FROM EncListaPrecio e WHERE e.fechamod = :fechamod"),
    @NamedQuery(name = "EncListaPrecio.findByActivo", query = "SELECT e FROM EncListaPrecio e WHERE e.activo = :activo")})
public class EncListaPrecio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDLISTA")
    private Integer idlista;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "DESCRIPCION_LISTA")
    private String descripcionLista;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_DESDE")
    @Temporal(TemporalType.DATE)
    private Date fechaDesde;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DECHA_HASTA")
    @Temporal(TemporalType.DATE)
    private Date dechaHasta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDUSUARIOCREA")
    private int idusuariocrea;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHACREA")
    @Temporal(TemporalType.DATE)
    private Date fechacrea;
    @Column(name = "IDUSUARIOMOD")
    private Integer idusuariomod;
    @Column(name = "FECHAMOD")
    @Temporal(TemporalType.DATE)
    private Date fechamod;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ACTIVO")
    private char activo;
    @JoinColumn(name = "IDEMPRESA", referencedColumnName = "IDEMPRESA")
    @ManyToOne(optional = false)
    private Empresas idempresa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idlista")
    private List<MovimientosDet> movimientosDetList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idlistaenc")
    private List<DetListaPrecio> detListaPrecioList;

    public EncListaPrecio() {
    }

    public EncListaPrecio(Integer idlista) {
        this.idlista = idlista;
    }

    public EncListaPrecio(Integer idlista, String descripcionLista, Date fechaDesde, Date dechaHasta, int idusuariocrea, Date fechacrea, char activo) {
        this.idlista = idlista;
        this.descripcionLista = descripcionLista;
        this.fechaDesde = fechaDesde;
        this.dechaHasta = dechaHasta;
        this.idusuariocrea = idusuariocrea;
        this.fechacrea = fechacrea;
        this.activo = activo;
    }

    public Integer getIdlista() {
        return idlista;
    }

    public void setIdlista(Integer idlista) {
        this.idlista = idlista;
    }

    public String getDescripcionLista() {
        return descripcionLista;
    }

    public void setDescripcionLista(String descripcionLista) {
        this.descripcionLista = descripcionLista;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getDechaHasta() {
        return dechaHasta;
    }

    public void setDechaHasta(Date dechaHasta) {
        this.dechaHasta = dechaHasta;
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

    public char getActivo() {
        return activo;
    }

    public void setActivo(char activo) {
        this.activo = activo;
    }

    public Empresas getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(Empresas idempresa) {
        this.idempresa = idempresa;
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
        hash += (idlista != null ? idlista.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EncListaPrecio)) {
            return false;
        }
        EncListaPrecio other = (EncListaPrecio) object;
        if ((this.idlista == null && other.idlista != null) || (this.idlista != null && !this.idlista.equals(other.idlista))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.EncListaPrecio[ idlista=" + idlista + " ]";
    }
    
}
