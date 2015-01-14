/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.existencia.rendered;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import sv.com.diserv.liquidaciones.entity.SaldoExistencia;

/**
 *
 * @author abraham.acosta
 */
public class ExistenciaItemRenderer implements ListitemRenderer{

    SaldoExistencia existencia;
    
    @Override
    public void render(Listitem item, Object data) throws Exception {
        
        existencia = (SaldoExistencia) data;
        Listcell lb = new Listcell(String.valueOf(existencia.getIdbodega().getIdbodega()));
        lb.setParent(item);

        lb = new Listcell(String.valueOf(existencia.getIdbodega().getNombre()));
        lb.setParent(item);
        
        lb = new Listcell(String.valueOf(existencia.getAyomes()));
        lb.setParent(item);

        lb = new Listcell(String.valueOf(existencia.getEntradasMes()));
        lb.setParent(item);
        
        lb = new Listcell(String.valueOf(existencia.getSalidasMes()));
        lb.setParent(item);
        
        lb = new Listcell(String.valueOf(existencia.getSaldoAct()));
        lb.setParent(item);


        item.setAttribute("data", data);
    }
    
}
