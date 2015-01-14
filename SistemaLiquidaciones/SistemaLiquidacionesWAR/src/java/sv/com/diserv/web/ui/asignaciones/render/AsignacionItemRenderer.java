package sv.com.diserv.web.ui.asignaciones.render;

import java.text.SimpleDateFormat;
import sv.com.diserv.web.ui.personas.rendered.*;
import org.zkoss.zk.ui.sys.ComponentsCtrl;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import sv.com.diserv.liquidaciones.entity.Movimientos;
import sv.com.diserv.liquidaciones.entity.Personas;

public class AsignacionItemRenderer implements ListitemRenderer {

    Movimientos asignacion;

    @Override
    public void render(Listitem item, Object data) throws Exception {
        asignacion = (Movimientos) data;
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

        Listcell lb= new Listcell(String.valueOf(asignacion.getIdmov()));
        lb.setParent(item);
        
        if(asignacion.getFechamov() != null)
            lb = new Listcell(formateador.format(asignacion.getFechamov()));
        else
           lb = new Listcell("");
        
        lb.setParent(item);
        
        lb = new Listcell(String.valueOf(asignacion.getNodoc()));
        lb.setParent(item);
        
        if(asignacion.getIdpersona().getNombre()!=null){
            lb = new Listcell(String.valueOf(asignacion.getIdpersona().getNombre()));
            lb.setParent(item);
        }
        

        item.setAttribute("data", data);

        ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedAsignacion");
        ComponentsCtrl.applyForward(item, "onClick=onClickedAsignacion");

    }
}
