package sv.com.diserv.liquidaciones.web.ui.bodegas.rendered;

import org.zkoss.zk.ui.sys.ComponentsCtrl;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import sv.com.ats.business.entity.Clientes;

public class BodegaItemRenderer implements ListitemRenderer {

    Clientes cliente;

    @Override
    public void render(Listitem item, Object data) throws Exception {
        cliente = (Clientes) data;
        Listcell lc = new Listcell(String.valueOf(cliente.getIdCliente()));
        lc.setParent(item);

        lc = new Listcell(String.valueOf(cliente.getNombreCliente()));
        lc.setParent(item);

        lc = new Listcell(cliente.getIvaCliente());
        lc.setParent(item);

        lc = new Listcell(cliente.getNitCliente());
        lc.setParent(item);

        item.setAttribute("data", data);

        ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedCliente");
        ComponentsCtrl.applyForward(item, "onClick=onClickedCliente");

    }
}
