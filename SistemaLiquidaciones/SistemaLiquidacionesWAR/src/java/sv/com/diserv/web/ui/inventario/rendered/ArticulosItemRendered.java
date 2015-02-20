/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.inventario.rendered;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import sv.com.diserv.liquidaciones.entity.Articulos;

/**
 *
 * @author edwin.alvarenga
 */
public class ArticulosItemRendered implements ComboitemRenderer {

    private Articulos articulo;

    @Override
    public void render(Comboitem item, Object data) throws Exception {
        if (data instanceof Articulos) {
            articulo = (Articulos) data;
            item.setLabel(articulo.getDescarticulo());
            item.setValue(articulo);
        }
        item.setAttribute("data", data);
    }
}
