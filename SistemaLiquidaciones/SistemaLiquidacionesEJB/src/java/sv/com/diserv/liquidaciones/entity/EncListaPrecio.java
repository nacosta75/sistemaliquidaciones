/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author edwin.alvarenga
 */
@Entity
@Table(name = "ENC_LISTA_PRECIO", catalog = "", schema = "")
@NamedQueries({
    @NamedQuery(name = "EncListaPrecio.findAll", query = "SELECT e FROM EncListaPrecio e"),
    @NamedQuery(name = "EncListaPrecio.findByCorrLista", query = "SELECT e FROM EncListaPrecio e WHERE e.corrLista = :corrLista"),
    @NamedQuery(name = "EncListaPrecio.findByDescripcionLista", query = "SELECT e FROM EncListaPrecio e WHERE e.descripcionLista = :descripcionLista"),
    @NamedQuery(name = "EncListaPrecio.findByBanEstado", query = "SELECT e FROM EncListaPrecio e WHERE e.banEstado = :banEstado"),
    @NamedQuery(name = "EncListaPrecio.findByFechaDesde", query = "SELECT e FROM EncListaPrecio e WHERE e.fechaDesde = :fechaDesde"),
    @NamedQuery(name = "EncListaPrecio.findByDechaHasta", query = "SELECT e FROM EncListaPrecio e WHERE e.dechaHasta = :dechaHasta")})
public class EncListaPrecio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "CORR_LISTA", nullable = false)
    private Integer corrLista;
    @Basic(optional = false)
    @Column(name = "DESCRIPCION_LISTA", nullable = false, length = 40)
    private String descripcionLista;
    @Basic(optional = false)
    @Column(name = "BAN_ESTADO", nullable = false, length = 1)
    private String banEstado;
    @Basic(optional = false)
    @Column(name = "FECHA_DESDE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaDesde;
    @Basic(optional = false)
    @Column(name = "DECHA_HASTA", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dechaHasta;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "encListaPrecio")
    private DetListaPrecio detListaPrecio;

    public EncListaPrecio() {
    }

    public EncListaPrecio(Integer corrLista) {
        this.corrLista = corrLista;
    }

    public EncListaPrecio(Integer corrLista, String descripcionLista, String banEstado, Date fechaDesde, Date dechaHasta) {
        this.corrLista = corrLista;
        this.descripcionLista = descripcionLista;
        this.banEstado = banEstado;
        this.fechaDesde = fechaDesde;
        this.dechaHasta = dechaHasta;
    }

    public Integer getCorrLista() {
        return corrLista;
    }

    public void setCorrLista(Integer corrLista) {
        this.corrLista = corrLista;
    }

    public String getDescripcionLista() {
        return descripcionLista;
    }

    public void setDescripcionLista(String descripcionLista) {
        this.descripcionLista = descripcionLista;
    }

    public String getBanEstado() {
        return banEstado;
    }

    public void setBanEstado(String banEstado) {
        this.banEstado = banEstado;
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

    public DetListaPrecio getDetListaPrecio() {
        return detListaPrecio;
    }

    public void setDetListaPrecio(DetListaPrecio detListaPrecio) {
        this.detListaPrecio = detListaPrecio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (corrLista != null ? corrLista.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EncListaPrecio)) {
            return false;
        }
        EncListaPrecio other = (EncListaPrecio) object;
        if ((this.corrLista == null && other.corrLista != null) || (this.corrLista != null && !this.corrLista.equals(other.corrLista))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.diserv.liquidaciones.entity.EncListaPrecio[ corrLista=" + corrLista + " ]";
    }
    
}
