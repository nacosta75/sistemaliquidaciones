/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.articulos.rendered;

import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import sv.com.diserv.liquidaciones.entity.Articulos;

/**
 *
 * @author abraham.acosta
 */
public class ArticuloItemRenderer implements ListitemRenderer{

     Articulos articulos;
    
    @Override
    public void render(Listitem item, Object data) throws Exception {
        articulos = (Articulos) data;
        Listcell lb = new Listcell(String.valueOf(articulos.getIdarticulo()));
        lb.setParent(item);

        lb = new Listcell(String.valueOf(articulos.getCodarticulo()));
        lb.setParent(item);

        lb = new Listcell(String.valueOf(articulos.getDescarticulo()));
        lb.setParent(item);
//
//        lb = new Listcell(bodega.getNitBodega());
//        lb.setParent(item);

        item.setAttribute("data", data);

        ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedLinea");
        ComponentsCtrl.applyForward(item, "onClick=onClickedLinea");

    }
    
}
