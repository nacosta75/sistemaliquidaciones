package sv.com.diserv.web.ui.inventario.rendered;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import sv.com.ats.business.entity.Ordentrabajo;

public class BusquedaOrdenTrabajoItemRenderer implements ComboitemRenderer {

    Ordentrabajo orden;

    @Override
    public void render(Comboitem item, Object data) throws Exception {
        orden = (Ordentrabajo) data;
        item.setLabel(orden.getIdOrdenTrabajo());
        item.setValue(orden);
        item.setAttribute("data", data);
    }
}
