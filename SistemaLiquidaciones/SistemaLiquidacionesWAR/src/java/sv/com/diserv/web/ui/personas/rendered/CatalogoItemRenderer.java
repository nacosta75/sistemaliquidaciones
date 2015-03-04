package sv.com.diserv.web.ui.personas.rendered;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import sv.com.diserv.liquidaciones.dto.CatalogoDTO;
import sv.com.diserv.liquidaciones.entity.Articulos;

public class CatalogoItemRenderer implements ComboitemRenderer {

    private Articulos art;
    private CatalogoDTO catalogo;

    @Override
    public void render(Comboitem item, Object data) {
        try {
            if (data instanceof CatalogoDTO) {
                catalogo = (CatalogoDTO) data;
                item.setLabel(catalogo.getDescripcionCatalogo());
                item.setValue(catalogo);
            } else if (data instanceof Articulos) {
                art = (Articulos) data;
                item.setLabel(art.getDescarticulo());
                item.setValue(art);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        item.setAttribute("data", data);

    }
}
