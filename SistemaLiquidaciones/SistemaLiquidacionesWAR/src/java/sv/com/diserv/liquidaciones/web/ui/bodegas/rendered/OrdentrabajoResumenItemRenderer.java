package sv.com.diserv.liquidaciones.web.ui.bodegas.rendered;

import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import sv.com.ats.business.entity.Ordentrabajo;
import sv.com.ats.business.util.UtilFormat;

public class OrdentrabajoResumenItemRenderer implements ListitemRenderer {

    Ordentrabajo ordentrabajo;

    @Override
    public void render(Listitem item, Object data) throws Exception {
        ordentrabajo = (Ordentrabajo) data;

        Listcell lc = new Listcell(String.valueOf(ordentrabajo.getIdOrdenTrabajo()));
        lc.setParent(item);

        lc = new Listcell(UtilFormat.convertirFechaDDMMYYY(ordentrabajo.getFechaIngreso()));
        lc.setParent(item);

        lc = new Listcell(ordentrabajo.getIdEstado().getDescripcionEstado());
        lc.setParent(item);
        lc = new Listcell(String.valueOf(ordentrabajo.getAduana()));
        lc.setParent(item);

        item.setAttribute("data", data);
        ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedOrdentrabajo");
    }
}