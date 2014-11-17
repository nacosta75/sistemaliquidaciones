package sv.com.diserv.liquidaciones.web.ui.usuarios.renderer;

import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import sv.com.diserv.liquidaciones.entity.Usuarios;

public class UsuarioItemRendered implements ListitemRenderer {

    private Usuarios usuario = null;

    @Override
    public void render(Listitem item, Object data) throws Exception {
        usuario = (Usuarios) data;
        Listcell lc = new Listcell(usuario.getCodigoEmpleado() != null ? usuario.getCodigoEmpleado() : "N/D");
        lc.setParent(item);

        lc = new Listcell(usuario.getNombreUsuario() != null ? usuario.getNombreUsuario() : "N/D");
        lc.setParent(item);

        lc = new Listcell(usuario.getNombreCompleto() != null ? usuario.getNombreCompleto() : "N/D");
        lc.setParent(item);

        lc = new Listcell(usuario.getStatus() != null ? usuario.getStatus() ? "Activo" : "Inactivo" : "N/D");
        lc.setParent(item);

        item.setAttribute("data", data);
        ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedUsuario");
    }
}
