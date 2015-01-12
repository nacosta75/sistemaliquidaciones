package sv.com.diserv.web.ui.asignaciones.render;

import java.text.SimpleDateFormat;
import sv.com.diserv.web.ui.asignaciones.render.*;
import org.zkoss.zk.ui.sys.ComponentsCtrl;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import sv.com.diserv.liquidaciones.entity.LotesExistencia;

public class LotesItemRenderer implements ListitemRenderer {

    LotesExistencia lote;

    @Override
    public void render(Listitem item, Object data) throws Exception {
        lote = (LotesExistencia) data;
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

        Listcell lb= new Listcell(String.valueOf(lote.getIdarticulo().getIdarticulo()));
        lb.setParent(item);
        
        lb = new Listcell(String.valueOf(lote.getIcc()));
        lb.setParent(item);
        
        lb = new Listcell(String.valueOf(lote.getImei()));
        lb.setParent(item);
        
        lb = new Listcell(String.valueOf(lote.getTelefono()));
        lb.setParent(item);

        item.setAttribute("data", data);

//        ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedAsignacion");
//        ComponentsCtrl.applyForward(item, "onClick=onClickedAsignacion");

    }
}
