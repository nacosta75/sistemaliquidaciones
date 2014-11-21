package sv.com.diserv.web.ui.bodegas.rendered;

import org.zkoss.zk.ui.sys.ComponentsCtrl;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import sv.com.diserv.liquidaciones.entity.Bodegas;


public class BodegaItemRenderer implements ListitemRenderer {

    Bodegas bodega;

    @Override
    public void render(Listitem item, Object data) throws Exception {
        bodega = (Bodegas) data;
        Listcell lb = new Listcell(String.valueOf(bodega.getIdbodega()));
        lb.setParent(item);

        lb = new Listcell(String.valueOf(bodega.getNombre()));
        lb.setParent(item);

//        lb = new Listcell(bodega.getIvaBodega());
//        lb.setParent(item);
//
//        lb = new Listcell(bodega.getNitBodega());
//        lb.setParent(item);

        item.setAttribute("data", data);

        ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedBodega");
        ComponentsCtrl.applyForward(item, "onClick=onClickedBodega");

    }
}
