package sv.com.diserv.web.ui.asignaciones.render;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import sv.com.diserv.liquidaciones.dto.ConsolidadoAsignacionesDTO;

public class LotesItemRenderer implements ListitemRenderer {

    ConsolidadoAsignacionesDTO lote;

    @Override
    public void render(Listitem item, Object data) throws Exception {
        lote = (ConsolidadoAsignacionesDTO) data;

        Listcell lb= new Listcell(String.valueOf(lote.getLote().getIdlote()));
        lb.setParent(item);
        
        lb= new Listcell(String.valueOf(lote.getLote().getIdarticulo().getIdarticulo()));
        lb.setParent(item);
        
        lb = new Listcell(String.valueOf(lote.getLote().getIcc()));
        lb.setParent(item);
        
        lb = new Listcell(String.valueOf(lote.getLote().getImei()));
        lb.setParent(item);
        
        lb = new Listcell(String.valueOf(lote.getLote().getTelefono()));
        lb.setParent(item);

        item.setAttribute("data", data);
        
        if(lote.getSelected())
            item.setSelected(true);
        

//        ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedAsignacion");
//        ComponentsCtrl.applyForward(item, "onClick=onClickedAsignacion");

    }
}
