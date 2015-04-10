package sv.com.diserv.web.ui.inventario.rendered;

import org.zkoss.zk.ui.sys.ComponentsCtrl;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import sv.com.diserv.liquidaciones.entity.Movimientos;
import sv.com.diserv.liquidaciones.util.UtilFormat;

public class MovimientoItemRenderer implements ListitemRenderer {

    private Movimientos movimiento;

    @Override
    public void render(Listitem item, Object data) throws Exception {
        movimiento = (Movimientos) data;
        Listcell lc = new Listcell(movimiento.getFechamov() != null ? UtilFormat.convertirFechaYYYYMMMDDD(movimiento.getFechamov()) : "N/D");
        lc.setParent(item);
        
        lc = new Listcell(movimiento.getNodoc() != null ? movimiento.getNodoc().toString() : "N/D");
        lc.setParent(item);
        
         lc = new Listcell(movimiento.getNoRegistro() != null ? movimiento.getNoRegistro() : "N/D");
        lc.setParent(item);

        lc = new Listcell(movimiento.getNombre() != null ? movimiento.getNombre() : "N/D");
        lc.setParent(item);


        lc = new Listcell(movimiento.getObserva1() != null ? movimiento.getObserva1() : "N/D");
        lc.setParent(item);

        

        item.setAttribute("data", data);
        ComponentsCtrl.applyForward(item, "onClick=onClickedMovimiento");
        ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedDetalleMovimiento");
        ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedCompra");
        
    }
}
