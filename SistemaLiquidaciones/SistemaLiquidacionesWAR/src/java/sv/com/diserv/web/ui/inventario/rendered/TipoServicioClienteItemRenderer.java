package sv.com.diserv.web.ui.inventario.rendered;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import sv.com.ats.business.entity.ServiciosPrestados;

public class TipoServicioClienteItemRenderer implements ComboitemRenderer {

    ServiciosPrestados servicio;

    @Override
    public void render(Comboitem item, Object data) throws Exception {
        servicio = (ServiciosPrestados) data;
        item.setLabel(servicio.getDescripcion());
        item.setValue(servicio);
        item.setAttribute("data", data);
    }
}
