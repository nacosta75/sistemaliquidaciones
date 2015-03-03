/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.articulos.rendered;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import sv.com.diserv.liquidaciones.entity.Articulos;

/**
 *
 * @author abraham.acosta
 */
public class ArticuloComboitemRenderer implements ComboitemRenderer{
    
    
      Articulos articulos;
       
      public void render(Comboitem item, Object data) throws Exception {
        articulos = (Articulos) data;
        item.setLabel(articulos.getDescarticulo());
        item.setValue(articulos);
        item.setAttribute("data", data);

    }

}
