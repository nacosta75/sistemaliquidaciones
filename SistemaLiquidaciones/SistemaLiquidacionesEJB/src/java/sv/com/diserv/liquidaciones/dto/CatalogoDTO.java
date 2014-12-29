package sv.com.diserv.liquidaciones.dto;

import java.io.Serializable;

/**
 *
 * @author sonia.garcia
 */
public class CatalogoDTO implements Serializable {
    
    
    private Integer idCatalogo;
    private String descripcionCatalogo;

    /**
     * @return the idCatalogo
     */
    public Integer getIdCatalogo() {
        return idCatalogo;
    }

    /**
     * @param idCatalogo the idCatalogo to set
     */
    public void setIdCatalogo(Integer idCatalogo) {
        this.idCatalogo = idCatalogo;
    }

    /**
     * @return the descripcionCatalogo
     */
    public String getDescripcionCatalogo() {
        return descripcionCatalogo;
    }

    /**
     * @param descripcionCatalogo the descripcionCatalogo to set
     */
    public void setDescripcionCatalogo(String descripcionCatalogo) {
        this.descripcionCatalogo = descripcionCatalogo;
    }
   
    
    
}
