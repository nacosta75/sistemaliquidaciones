/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.marcas.rendered;

import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import sv.com.diserv.liquidaciones.entity.MarcaArticulo;

/**
 *
 * @author abraham.acosta
 */
public class MarcaItemRenderer implements ListitemRenderer{

    MarcaArticulo marca;
    
    @Override
    public void render(Listitem item, Object data) throws Exception {
        marca = (MarcaArticulo) data;
        Listcell lb = new Listcell(String.valueOf(marca.getIdmarca()));
        lb.setParent(item);

        lb = new Listcell(String.valueOf(marca.getDescmarca()));
        lb.setParent(item);

//        lb = new Listcell(bodega.getIvaBodega());
//        lb.setParent(item);
//
//        lb = new Listcell(bodega.getNitBodega());
//        lb.setParent(item);

        item.setAttribute("data", data);

        ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedMarca");
        ComponentsCtrl.applyForward(item, "onClick=onClickedMarca");

    }


    
}
