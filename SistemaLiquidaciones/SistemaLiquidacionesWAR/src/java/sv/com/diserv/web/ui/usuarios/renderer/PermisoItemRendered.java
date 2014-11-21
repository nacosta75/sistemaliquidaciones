/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE DISERV,SA DE CV  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author programador@fgr.gob.sv
 * @author Carlos Godoy
 * Fiscalía General de la República 2013
 */
package sv.com.diserv.web.ui.usuarios.renderer;

import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import sv.com.diserv.liquidaciones.entity.Authorities;

/**
 *
 * @author programador
 */
public class PermisoItemRendered implements ListitemRenderer {

    Authorities datos = null;

    @Override
    public void render(Listitem item, Object data) throws Exception {
        datos = (Authorities) data;

        Checkbox check = new Checkbox();
        check.setChecked(false);
//        check.setChecked(datos.getAuthoritiSelected() != null ? datos.getAuthoritiSelected() : false);

        Listcell lc = new Listcell("");
        lc.appendChild(check);
        lc.setParent(item);

        lc = new Listcell(datos.getNombre() != null ? datos.getNombre() : "N/D");
        lc.setParent(item);

        lc = new Listcell(datos.getDescripcion() != null ? datos.getDescripcion() : "N/D");
        lc.setParent(item);

        lc = new Listcell(datos.getDescripcion() != null ? datos.getDescripcion() : "N/D");
        lc.setParent(item);

        item.setAttribute("data", data);
        ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedDerecho");
    }
}
