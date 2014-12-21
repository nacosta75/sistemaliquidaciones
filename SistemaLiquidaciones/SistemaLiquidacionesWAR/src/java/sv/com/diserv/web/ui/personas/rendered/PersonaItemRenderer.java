package sv.com.diserv.web.ui.personas.rendered;

import sv.com.diserv.web.ui.personas.rendered.*;
import org.zkoss.zk.ui.sys.ComponentsCtrl;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import sv.com.diserv.liquidaciones.entity.Personas;

public class PersonaItemRenderer implements ListitemRenderer {

    Personas persona;

    @Override
    public void render(Listitem item, Object data) throws Exception {
        persona = (Personas) data;
        Listcell lb = new Listcell(String.valueOf(persona.getIdpersona()));
        lb.setParent(item);

        lb = new Listcell(String.valueOf(persona.getNombre()));
        lb.setParent(item);

//        lb = new Listcell(bodega.getIvaBodega());
//        lb.setParent(item);
//
//        lb = new Listcell(bodega.getNitBodega());
//        lb.setParent(item);

        item.setAttribute("data", data);

        ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedPersona");
        ComponentsCtrl.applyForward(item, "onClick=onClickedPersona");

    }
}
