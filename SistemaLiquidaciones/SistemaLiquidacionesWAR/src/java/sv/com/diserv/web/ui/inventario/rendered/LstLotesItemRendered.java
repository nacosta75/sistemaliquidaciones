/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.inventario.rendered;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import sv.com.diserv.liquidaciones.entity.LotesExistencia;

/**
 *
 * @author noe
 */
public class LstLotesItemRendered implements ListitemRenderer{

    private LotesExistencia lote;
    
    @Override
    public void render(Listitem item, Object data) throws Exception {
        lote = (LotesExistencia) data;

        Listcell lb= new Listcell(String.valueOf(lote.getIdlote()));
        lb.setParent(item);
        
        lb= new Listcell(String.valueOf(lote.getIdarticulo()));
        lb.setParent(item);
        
        lb = new Listcell(String.valueOf(lote.getIcc()));
        lb.setParent(item);
        
        lb = new Listcell(String.valueOf(lote.getImei()));
        lb.setParent(item);
        
        lb = new Listcell(String.valueOf(lote.getTelefono()));
        lb.setParent(item);

        item.setAttribute("data", data);
        
        
    }
    
}
