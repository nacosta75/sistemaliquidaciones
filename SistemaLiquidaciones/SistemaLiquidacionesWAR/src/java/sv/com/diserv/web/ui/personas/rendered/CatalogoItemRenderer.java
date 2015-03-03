package sv.com.diserv.web.ui.personas.rendered;

import sv.com.diserv.web.ui.personas.rendered.*;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

import org.zkoss.zul.Listcell;
import sv.com.diserv.liquidaciones.dto.CatalogoDTO;
import sv.com.diserv.liquidaciones.entity.Personas;

public class CatalogoItemRenderer implements ComboitemRenderer {

    CatalogoDTO catalogo;


    @Override
    public void render(Comboitem item, Object data) throws Exception {
     
     catalogo = (CatalogoDTO) data;
     
     item.setLabel(catalogo.getDescripcionCatalogo());
     item.setValue(catalogo);
     item.setAttribute("data", data);
          
    }
}
