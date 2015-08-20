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
@Table(name = "LOTES_EXISTENCIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LotesExistencia.findAll", query = "SELECT l FROM LotesExistencia l"),
    @NamedQuery(name = "LotesExistencia.findByIdlote", query = "SELECT l FROM LotesExistencia l WHERE l.idlote = :idlote"),
    @NamedQuery(name = "LotesExistencia.findByIdMov", query = "SELECT l FROM LotesExistencia l WHERE l.idmov.idmov = :idmov"),
    @NamedQuery(name = "LotesExistencia.findByIcc", query = "SELECT l FROM LotesExistencia l WHERE l.icc = :icc"),
    @NamedQuery(name = "LotesExistencia.findByImei", query = "SELECT l FROM LotesExistencia l WHERE l.imei = :imei"),
    @NamedQuery(name = "LotesExistencia.findByTelefono", query = "SELECT l FROM LotesExistencia l WHERE l.telefono = :telefono"),
    @NamedQuery(name = "LotesExistencia.findByEstado", query = "SELECT l FROM LotesExistencia l WHERE l.estado = :estado"),
    @NamedQuery(name = "LotesExistencia.findByFechaMov", query = "SELECT l FROM LotesExistencia l WHERE l.fechaMov = :fechaMov"),
    @NamedQuery(name = "LotesExistencia.findByFechacrea", query = "SELECT l FROM LotesExistencia l WHERE l.fechacrea = :fechacrea")})
public class LotesExistencia implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDLOTE")
     @SequenceGenerator(name = "SEQ_LOTES_EXISTENCIA", sequenceName = "SEQ_LOTES_EXISTENCIA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LOTES_EXISTENCIA")
    private Integer idlote;
    
    @Size(max = 19)
    @Column(name = "ICC")
    private String icc;
    @Size(max = 15)
    @Column(name = "IMEI")
    private String imei;
    @Column(name = "TELEFONO")
    private Integer telefono;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "ESTADO")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_MOV")
    @Temporal(TemporalType.DATE)
    private Date fechaMov;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHACREA")
    @Temporal(TemporalType.DATE)
    private Date fechacrea;
    @JoinColumn(name = "IDUSUARIOCREA", referencedColumnName = "IDUSUARIO")
    @ManyToOne(optional = false)
    private Usuarios idusuariocrea;
    @JoinColumn(name = "IDMOV", referencedColumnName = "IDMOV")
    @ManyToOne
    private Movimientos idmov;
    @JoinColumn(name = "IDARTICULO", referencedColumnName = "IDARTICULO")
    @ManyToOne(optional = false)
    private Articulos idarticulo;

    public LotesExistencia() {
    }

    public LotesExistencia(Integer idlote) {
        this.idlote = idlote;
    }

    public LotesExistencia(Integer idlote, String estado, Date fechaMov, Date fechacrea) {
        this.idlote = idlote;
        this.estado = estado;
        this.fechaMov = fechaMov;
        this.fechacrea = fechacrea;
    }

    public Integer getIdlote() {
        return idlote;
    }

    public void setIdlote(Integer idlote) {
        this.idlote = idlote;
    }

    public String getIcc() {
        return icc;
    }

    public void setIcc(String icc) {
        this.icc = icc;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaMov() {
        return fechaMov;
    }

    public void setFechaMov(Date fechaMov) {
        this.fechaMov = fechaMov;
    }

    public Date getFechacrea() {
        return fechacrea;
    }

    public void setFechacrea(Date fechacrea) {
        this.fechacrea = fechacrea;
    }

    public Usuarios getIdusuariocrea() {
        return idusuariocrea;
    }

    public void setIdusuariocrea(Usuarios idusuariocrea) {
        this.idusuariocrea = idusuariocrea;
    }

    public Movimientos getIdmov() {
        return idmov;
    }

    public void setIdmov(Movimientos idmov) {
        this.idmov = idmov;
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
        hash += (idlote != null ? idlote.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LotesExistencia)) {
            return false;
        }
        LotesExistencia other = (LotesExistencia) object;
        if ((this.idlote == null && other.idlote != null) || (this.idlote != null && !this.idlote.equals(other.idlote))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.LotesExistencia[ idlote=" + idlote + " ]";
    }
    
}
