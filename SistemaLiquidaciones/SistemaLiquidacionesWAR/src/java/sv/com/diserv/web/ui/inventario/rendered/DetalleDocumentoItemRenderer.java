package sv.com.diserv.web.ui.inventario.rendered;

import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import sv.com.ats.business.entity.DetalleDocumentoCliente;

public class DetalleDocumentoItemRenderer implements ListitemRenderer {

    DetalleDocumentoCliente deta;

    @Override
    public void render(Listitem item, Object data) throws Exception {
        deta = (DetalleDocumentoCliente) data;
        Listcell lc = new Listcell(deta.getIdOrdenTrabajo() != null ? deta.getIdOrdenTrabajo().getIdOrdenTrabajo() : "");
        lc.setParent(item);

        lc = new Listcell(deta.getDescripcion() != null ? deta.getDescripcion() : "N/D");
        lc.setParent(item);

        lc = new Listcell(deta.getCantidad() != null ? deta.getCantidad().toString() : "N/D");
        lc.setParent(item);

        lc = new Listcell(deta.getPrecioUnitario() != null ? deta.getPrecioUnitario().toString() : "N/D");
        lc.setParent(item);

        lc = new Listcell(deta.getPrecioTotal() != null ? deta.getPrecioTotal().toString() : "N/D");
        lc.setParent(item);

        item.setAttribute("data", data);
//        ComponentsCtrl.applyForward(item, "onClick=onClickedDocumento");
//
        ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedDetalleDocumento");
    }
}
