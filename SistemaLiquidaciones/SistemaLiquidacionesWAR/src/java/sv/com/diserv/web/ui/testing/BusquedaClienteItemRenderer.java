package sv.com.diserv.web.ui.testing;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

;

public class BusquedaClienteItemRenderer implements ComboitemRenderer {

    Clientes cliente;

    @Override
    public void render(Comboitem item, Object data) throws Exception {
        cliente = (Clientes) data;
        item.setLabel(cliente.getNombreCliente());
        item.setValue(cliente);
        item.setAttribute("data", data);
    }
}
