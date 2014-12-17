/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.lineas.lineas.rendered;

import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import sv.com.diserv.liquidaciones.entity.LineaArticulo;

/**
 *
 * @author vitual-lubuntu
 */
public class LineaItemRenderer implements ListitemRenderer{

    LineaArticulo linea;
    
    @Override
    public void render(Listitem item, Object data) throws Exception {
        linea = (LineaArticulo) data;
        Listcell lb = new Listcell(String.valueOf(linea.getIdlinea()));
        lb.setParent(item);

        lb = new Listcell(String.valueOf(linea.getDesclinea()));
        lb.setParent(item);

//        lb = new Listcell(bodega.getIvaBodega());
//        lb.setParent(item);
//
//        lb = new Listcell(bodega.getNitBodega());
//        lb.setParent(item);

        item.setAttribute("data", data);

        ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedLinea");
        ComponentsCtrl.applyForward(item, "onClick=onClickedLinea");

    }
    
}
