package sv.com.diserv.liquidaciones.dto;

import java.io.Serializable;

/**
 *
 * @author sonia.garcia
 */
public class BusquedaLoteExistenciaDTO implements Serializable {
    
    
    private Integer idArticulo;
    private String icc;
    private String imei;
    private String telefono;

    /**
     * @return the idArticulo
     */
    public Integer getIdArticulo() {
        return idArticulo;
    }

    /**
     * @param idArticulo the idArticulo to set
     */
    public void setIdArticulo(Integer idArticulo) {
        this.idArticulo = idArticulo;
    }

    /**
     * @return the icc
     */
    public String getIcc() {
        return icc;
    }

    /**
     * @param icc the icc to set
     */
    public void setIcc(String icc) {
        this.icc = icc;
    }

    /**
     * @return the imei
     */
    public String getImei() {
        return imei;
    }

    /**
     * @param imei the imei to set
     */
    public void setImei(String imei) {
        this.imei = imei;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    
}
