package sv.com.diserv.web.ui.usuarios.renderer;

import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import sv.com.diserv.liquidaciones.entity.Groups;

public class RolItemRendered implements ListitemRenderer {

    private Groups group = null;

    @Override
    public void render(Listitem item, Object data) throws Exception {
        group = (Groups) data;

        Checkbox check = new Checkbox();
        check.setChecked(false);
//        if (group.getGroupSelected() != null) {
//            check.setChecked(group.getGroupSelected());
//        }
        Listcell lc = new Listcell("");
        lc.appendChild(check);
        lc.setParent(item);

        lc = new Listcell(group.getGroupname() != null ? group.getGroupname() : "N/D");
        lc.setParent(item);

        lc = new Listcell(group.getId() != null ? group.getId().toString() : "N/D");
        lc.setParent(item);

        item.setAttribute("data", data);
        ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedRol");

    }
}
