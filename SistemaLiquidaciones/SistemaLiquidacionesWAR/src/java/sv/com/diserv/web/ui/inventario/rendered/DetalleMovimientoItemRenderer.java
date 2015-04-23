package sv.com.diserv.web.ui.inventario.rendered;

import java.math.BigDecimal;
import org.zkoss.zk.ui.sys.ComponentsCtrl;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import sv.com.diserv.liquidaciones.entity.MovimientosDet;

public class DetalleMovimientoItemRenderer implements ListitemRenderer {

    private MovimientosDet detalleMovimiento;

    @Override
    public void render(Listitem item, Object data) throws Exception {
        detalleMovimiento = (MovimientosDet) data;
        Listcell lc = new Listcell(detalleMovimiento.getIdmovd() != null ? detalleMovimiento.getIdmovd().toString() : "N/D");
        lc.setParent(item);

        lc = new Listcell(detalleMovimiento.getIdarticulo() != null ? detalleMovimiento.getIdarticulo().getDescarticulo() : "N/D");
        lc.setParent(item);

        lc = new Listcell(detalleMovimiento.getCantidad() != null ? detalleMovimiento.getCantidad().toPlainString() : "N/D");
        lc.setParent(item);

        lc = new Listcell(detalleMovimiento.getPrecio() != null ? detalleMovimiento.getPrecio().toPlainString() : "N/D");
        lc.setParent(item);

        lc = new Listcell(detalleMovimiento.getCantidad().multiply(detalleMovimiento.getPrecio())!= null ? detalleMovimiento.getCantidad().multiply(detalleMovimiento.getPrecio()).toPlainString() : "0.00");
        lc.setParent(item);

        item.setAttribute("data", data);
        ComponentsCtrl.applyForward(item, "onClick=onClickedDetalleDocumento");

        ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedDetalleMovimiento");
    }
}
