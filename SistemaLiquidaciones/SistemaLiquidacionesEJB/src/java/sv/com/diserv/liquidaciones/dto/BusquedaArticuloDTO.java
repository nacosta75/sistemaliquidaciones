/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.dto;

/**
 *
 * @author abraham.acosta
 */
public class BusquedaArticuloDTO {
    
     private Integer idarticulo;
     private String codarticulo;
     private String descarticulo;
     private int idtipoarticulo;

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
     
     
     
     
}
