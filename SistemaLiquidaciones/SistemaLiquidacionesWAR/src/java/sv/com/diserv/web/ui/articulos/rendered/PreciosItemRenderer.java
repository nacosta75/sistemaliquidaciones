/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.articulos.rendered;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import sv.com.diserv.liquidaciones.entity.DetListaPrecio;


/**
 *
 * @author abraham.acosta
 */
public class PreciosItemRenderer implements ListitemRenderer{

    DetListaPrecio precio;
    
    @Override
    public void render(Listitem item, Object data) throws Exception {
        precio = (DetListaPrecio) data;
        Listcell lb = new Listcell(String.valueOf(precio.getIdlistaenc().getIdlista()));
        lb.setParent(item);

        lb = new Listcell(String.valueOf(precio.getIdlistaenc().getDescripcionLista()));
        lb.setParent(item);
        
        lb = new Listcell(String.valueOf(precio.getPrecio()));
        lb.setParent(item);

        lb = new Listcell(String.valueOf(precio.getIdlistaenc().getFechaDesde()));
        lb.setParent(item);
        
        lb = new Listcell(String.valueOf(precio.getIdlistaenc().getDechaHasta()));
        lb.setParent(item);
        
        item.setAttribute("data", data);
    }
    
}
