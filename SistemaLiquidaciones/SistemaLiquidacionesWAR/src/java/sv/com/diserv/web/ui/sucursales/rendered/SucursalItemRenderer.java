/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.web.ui.sucursales.rendered;

import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import sv.com.diserv.liquidaciones.entity.Sucursales;

/**
 *
 * @author abraham.acosta
 */
public class SucursalItemRenderer implements ListitemRenderer {

   Sucursales sucursal;
   
    @Override
    public void render(Listitem item, Object data) throws Exception {
        sucursal = (Sucursales) data;
        Listcell lb = new Listcell(String.valueOf(sucursal.getIdsucursal()));
        lb.setParent(item);

        lb = new Listcell(String.valueOf(sucursal.getDescripcion()));
        lb.setParent(item);

        item.setAttribute("data", data);

        ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedSucursal");
        ComponentsCtrl.applyForward(item, "onClick=onClickedSucursal");

    }

    
}
