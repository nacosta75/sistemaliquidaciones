package sv.com.diserv.web.ui.asignaciones.render;

import java.text.SimpleDateFormat;
import sv.com.diserv.web.ui.asignaciones.render.*;
import org.zkoss.zk.ui.sys.ComponentsCtrl;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import sv.com.diserv.liquidaciones.dto.ConsolidadoAsignacionesDTO;
import sv.com.diserv.liquidaciones.entity.LotesExistencia;

public class ConsolidadoItemRenderer implements ListitemRenderer {

    ConsolidadoAsignacionesDTO consolidado;

    @Override
    public void render(Listitem item, Object data) throws Exception {
        consolidado = (ConsolidadoAsignacionesDTO) data;
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

        Listcell lb= new Listcell(String.valueOf(consolidado.getIdArticulo()));
        lb.setParent(item);
        
        lb = new Listcell(String.valueOf(consolidado.getCodigoArticulo()));
        lb.setParent(item);
        
        lb = new Listcell(String.valueOf(consolidado.getDescripcion()));
        lb.setParent(item);
        
        lb = new Listcell(String.valueOf(consolidado.getCantidad()));
        lb.setParent(item);
        
        lb = new Listcell(String.valueOf(consolidado.getPrecio()));
        lb.setParent(item);

        item.setAttribute("data", data);

//        ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedAsignacion");
//        ComponentsCtrl.applyForward(item, "onClick=onClickedAsignacion");

    }
}
