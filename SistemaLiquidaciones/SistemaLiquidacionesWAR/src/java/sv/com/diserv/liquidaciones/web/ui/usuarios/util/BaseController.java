/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE DISERV,SA DE CV  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author alvarenga.miranda@gmail.com
 */
package sv.com.diserv.liquidaciones.web.ui.usuarios.util;

/**
 *
 * alvarenga.miranda@gmail.com
 */
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.zkoss.spring.security.SecurityUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.event.CreateEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.dto.CustomUserDTO;

public class BaseController extends Window implements AfterCompose, Serializable {

    private static final long serialVersionUID = -2179229704315045689L;
    protected transient AnnotateDataBinder binder;
    protected transient Map<String, Object> args;
    protected CustomUserDTO userLogin;
    private transient UserWorkspace userWorkspace;

    public void doOnCreateCommon(Window w) throws Exception {
        this.binder = new AnnotateDataBinder(w);
        setUserLogin((CustomUserDTO) SecurityUtil.getAuthentication().getPrincipal());
    }

    public void doOnCreateCommon(Window w, Event fe) throws Exception {
        doOnCreateCommon(w);
        CreateEvent ce = (CreateEvent) ((ForwardEvent) fe).getOrigin();
        this.args = (Map<String, Object>) ce.getArg();
        setUserLogin((CustomUserDTO) SecurityUtil.getAuthentication().getPrincipal());
    }

    /**
     * *
     *
     */
    @Override
    public void afterCompose() {
        processRecursive(this, this);
        Components.wireVariables(this, this);
        Components.addForwards(this, this);
    }

    private void processRecursive(Window main, Window child) {
        Components.wireVariables(main, child);
        Components.addForwards(main, this);
        List<Component> winList = (List<Component>) child.getChildren();
        for (Component window : winList) {
            if (window instanceof Window) {
                processRecursive(main, (Window) window);
            }
        }
    }

    public CustomUserDTO getUserLogin() {
        if (userLogin == null) {
            userLogin = (CustomUserDTO) SecurityUtil.getAuthentication().getPrincipal();
            setUserLogin(userLogin);
        }
        return userLogin;
    }

    public void setUserLogin(CustomUserDTO userLogin) {
        this.userLogin = userLogin;
    }

    final protected UserWorkspace getUserWorkspace() {
        return this.userWorkspace;
    }

    public void setUserWorkspace(UserWorkspace userWorkspace) {
        this.userWorkspace = userWorkspace;
    }
}
