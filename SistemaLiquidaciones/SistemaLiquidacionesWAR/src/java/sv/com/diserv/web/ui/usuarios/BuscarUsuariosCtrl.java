/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE DISERV,SA DE CV  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author programador@fgr.gob.sv
 * @author Carlos Godoy
 * Fiscalía General de la República 2013
 */
package sv.com.diserv.liquidaciones.web.ui.usuarios;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;
import sv.com.diserv.web.ui.util.UserWorkspace;
import sv.com.diserv.liquidaciones.dto.BusquedaUserDTO;
import sv.com.diserv.liquidaciones.ejb.ManejadorUsuarioBeanLocal;
import sv.com.diserv.liquidaciones.entity.Usuarios;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;

/**
 *
 * @author programador
 */
public class BuscarUsuariosCtrl extends BaseController {

    private static final long serialVersionUID = -6102616129515843465L;
    private static final transient Logger logger = Logger.getLogger(BuscarUsuariosCtrl.class.getCanonicalName());
    protected Window buscarUsuariosWindow;
    protected Row rowCarnetUsuarioBuscar;
    protected Row rowUsuarioBuscar;
    protected Row rowNombreUsuarioBuscar;
    protected Row rowFechaIngresoUsuarioBuscar;
    protected Row rowBotonesBuscarUsuario;
    protected Intbox intNumeroCarnetBuscar;
    protected Textbox txtUsuarioBuscar;
    protected Textbox txtNombreBuscar;
    protected Button btnBuscarUsuario;
    protected Button btnCerrarBuscaUsuario;
    private ManejadorUsuarioBeanLocal usuariosBean;
    private ServiceLocator serviceLocator;
    private BusquedaUserDTO request;
    private ListaUsuariosCtrl listaUsuariosCtrl;

    public BuscarUsuariosCtrl() {
        logger.log(Level.INFO, "[BuscarUsuariosCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            usuariosBean = serviceLocator.getService(Constants.JNDI_USUARIOS_BEAN);
        } catch (Exception ex) {
            logger.log(Level.ERROR, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$buscarUsuariosWindow(Event event) throws Exception {
        logger.log(Level.INFO, "[onCreate$buscarUsuariosWindow]");
        doOnCreateCommon(this.buscarUsuariosWindow, event);
        MensajeMultilinea.doSetTemplate();
        if (this.args.containsKey("listaUsuariosCtrl")) {
            listaUsuariosCtrl = (ListaUsuariosCtrl) this.args.get("listaUsuariosCtrl");
        }
        showBuscarUsuariosWindow();

        checkPermisos();
    }

    public void showBuscarUsuariosWindow() {
        logger.log(Level.INFO, "[showBuscarUsuariosWindow]INIT");
        try {
            buscarUsuariosWindow.doModal();

        } catch (SuspendNotAllowedException ex) {
            logger.log(Level.ERROR, "[SuspendNotAllowedException]Exception={0}", ex);
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void onClick$btnBuscarUsuario(Event event) throws InterruptedException {
        logger.log(Level.INFO, "[onClick$btnBuscarUsuario]:{0}" + event.toString());
        doBuscarUsuarios();

    }

    public void onClick$btnCerrarBuscaUsuario(Event event) throws InterruptedException {
        logger.log(Level.INFO, "[onClick$btnCerrarBuscaUsuario]:{0}" + event.toString());

        this.buscarUsuariosWindow.onClose();
    }

    /**
     * Metodo para agregar al DTO los datos ingresados para realizar la busqueda
     */
    public void doBuscarUsuarios() {
        logger.log(Level.INFO, "[doBuscarUsuarios]Realizando la busqueda.");
        request = new BusquedaUserDTO();
        try {
            if (intNumeroCarnetBuscar.getValue() != null) {
                request.setCarnetUsuario(intNumeroCarnetBuscar.getValue());
            }
            if (StringUtils.isNotEmpty(txtUsuarioBuscar.getText())) {
                request.setUsuarioUsuario(txtUsuarioBuscar.getText());
            }
            if (StringUtils.isNotEmpty(txtNombreBuscar.getText())) {
                request.setNombreUsuario(txtNombreBuscar.getText());
            }
            List<Usuarios> listaResponse = usuariosBean.buscarUsuariosPorCriterios(request);
//            if (listaResponse != null) {
//                listaUsuariosCtrl.getListBoxUsuario().setModel(new ListModelList(listaResponse));
//                listaUsuariosCtrl.getListBoxUsuario().setItemRenderer(new UsuarioItemRendered());
//            } else {
//                listaUsuariosCtrl.getListBoxUsuario().setEmptyMessage("No se encontraron registros con los criterios ingresados!!");
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkPermisos() {
        final UserWorkspace workspace = getUserWorkspace();
        rowCarnetUsuarioBuscar.setVisible(workspace.isAllowed("rowCarnetUsuarioBuscar"));
        rowUsuarioBuscar.setVisible(workspace.isAllowed("rowUsuarioBuscar"));
        rowNombreUsuarioBuscar.setVisible(workspace.isAllowed("rowNombreUsuarioBuscar"));
        rowBotonesBuscarUsuario.setVisible(workspace.isAllowed("rowBotonesBuscarUsuario"));
        btnBuscarUsuario.setVisible(workspace.isAllowed("btnBuscarUsuario"));
        btnCerrarBuscaUsuario.setVisible(workspace.isAllowed("btnCerrarBuscaUsuario"));
    }

    public BusquedaUserDTO getRequest() {
        return request;
    }

    public void setRequest(BusquedaUserDTO request) {
        this.request = request;
    }

    public ServiceLocator getServiceLocator() {
        return serviceLocator;
    }

    public void setServiceLocator(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    public ListaUsuariosCtrl getListaUsuariosCtrl() {
        return listaUsuariosCtrl;
    }

    public void setListaUsuariosCtrl(ListaUsuariosCtrl listaUsuariosCtrl) {
        this.listaUsuariosCtrl = listaUsuariosCtrl;
    }
}
