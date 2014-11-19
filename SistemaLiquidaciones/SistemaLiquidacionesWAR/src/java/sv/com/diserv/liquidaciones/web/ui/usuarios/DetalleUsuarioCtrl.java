package sv.com.diserv.liquidaciones.web.ui.usuarios;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import sv.com.diserv.liquidaciones.web.ui.usuarios.util.BaseController;
import sv.com.diserv.liquidaciones.web.ui.usuarios.util.MensajeMultilinea;
import sv.com.diserv.liquidaciones.web.ui.usuarios.util.UserWorkspace;
import sv.com.diserv.liquidaciones.dto.GenericResponse;
import sv.com.diserv.liquidaciones.ejb.ManejadorUsuarioBeanLocal;
import sv.com.diserv.liquidaciones.entity.Usuarios;
import sv.com.diserv.liquidaciones.exception.DiservWebException;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;

public class DetalleUsuarioCtrl extends BaseController {

    private static final Logger logger = Logger.getLogger(DetalleUsuarioCtrl.class.getName());
    protected Window detalleUsuarioWindow;
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnSave;
    protected Button btnClose;
    protected Button btnUpdate;
    protected Intbox txtRegistrosLista;
    protected Textbox txtNombreUsuario;
    protected Textbox txtUsuarioSistema;
    protected Textbox txtClaveUsuario;
    protected Textbox txtClaveUsuario2;
    protected Textbox txtNumerocarne;
    protected Checkbox checkEstadoUsuario;
    private Usuarios usuarioSelected;
    private ListaUsuariosCtrl listaUsuarioCtrl;
    private transient Integer token;
    private ManejadorUsuarioBeanLocal usuarioBean;
    private ServiceLocator serviceLocator;
    private GenericResponse responseOperacion;
    private UserWorkspace workspace;

    public DetalleUsuarioCtrl() {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            usuarioBean = serviceLocator.getService(Constants.JNDI_USUARIOS_BEAN);
            workspace = (UserWorkspace) SpringUtil.getBean("userWorkspace");
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$detalleUsuarioWindow(Event event) throws Exception {
        doOnCreateCommon(this.detalleUsuarioWindow, event);
        MensajeMultilinea.doSetTemplate();
        if (this.args.containsKey("usuarioSelected")) {
            usuarioSelected = ((Usuarios) this.args.get("usuarioSelected"));
            setUsuarioSelected(usuarioSelected);
            if (!workspace.isAllowed("mostrar_password_user")) {
                txtClaveUsuario.setType("password");
                txtClaveUsuario2.setType("password");
            }
        } else {
            txtClaveUsuario.setType("password");
            txtClaveUsuario2.setType("password");
        }
        if (this.args.containsKey("token")) {
            this.token = ((Integer) this.args.get("token"));
            setToken(this.token);
        } else {
            setToken(Integer.valueOf(0));
        }
        if (this.args.containsKey("listaUsuariosCtrl")) {
            listaUsuarioCtrl = ((ListaUsuariosCtrl) this.args.get("listaUsuariosCtrl"));
        }

        checkPermisos();
        showDetalleCliente();
    }

    public void showDetalleCliente() {
        try {
            if (usuarioSelected != null) {
                doReadOnly(Boolean.TRUE);
                doEditButton();
                loadDataFromEntity();
//            if (cliente.getIdCliente().intValue() >= 1) {
//                this.listBoxOrdentrabajoClientes.setModel(new ListModelList(
//                        this.ordentrabajoDao.buscarOrdenesPorcliente(
//                        cliente.getIdCliente())));
//                this.listBoxOrdentrabajoClientes.setItemRenderer(new ClientesOrdentrabajoListModelItemRenderer());
//                this.panel_tramites_cliente.setTitle(
//                        "Ultimos tramites del cliente :"
//                        + cliente.getNombreCliente());
//            }
            } else {
                doNew();
            }
            detalleUsuarioWindow.doModal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * cargamos los textboxs desde entity
     */
    private void loadDataFromEntity() {
        txtClaveUsuario.setValue(usuarioSelected.getContrasena());
        txtClaveUsuario2.setValue(usuarioSelected.getContrasena());
        txtNombreUsuario.setValue(usuarioSelected.getNombreCompleto());
        txtNumerocarne.setValue(usuarioSelected.getCodigoEmpleado());
        txtRegistrosLista.setValue(usuarioSelected.getRegistrosLista());
        txtUsuarioSistema.setValue(usuarioSelected.getNombreCompleto());
        checkEstadoUsuario.setChecked(usuarioSelected.isStatus());
    }

    private void loadDataFromTextboxs() {
        try {
            usuarioSelected = new Usuarios();
            //validamos los campos
            if (StringUtils.isEmpty(txtClaveUsuario.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar password valido para usuario");
            }
            if (StringUtils.isEmpty(txtClaveUsuario2.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar password para usuario");
            }

            if (!txtClaveUsuario.getValue().equals(txtClaveUsuario2.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Password no coincide   ");

            }
            if (StringUtils.isEmpty(txtNombreUsuario.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar nombre usuarios");
            }
            if (StringUtils.isEmpty(txtUsuarioSistema.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar  usuario con el que se conectara a sistema");
            }
            if (txtRegistrosLista.getValue() == null) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar cantida de registros por pagina");
            }
            //if (checkEstadoUsuario.isChecked()) {
            usuarioSelected.setStatus(checkEstadoUsuario.isChecked());
            usuarioSelected.setCodigoEmpleado(txtNumerocarne.getValue());
            usuarioSelected.setContrasena(txtClaveUsuario2.getValue());
            usuarioSelected.setNombreCompleto(txtNombreUsuario.getValue());
            usuarioSelected.setNombreUsuario(txtUsuarioSistema.getValue());
            usuarioSelected.setRegistrosLista(txtRegistrosLista.getValue());

        } catch (DiservWebException ex) {
            MensajeMultilinea.show(ex.getMensaje(), Constants.MENSAJE_TIPO_ERROR);
        }

    }

    public void onClick$btnSave(Event event) {
        try {
            if (getToken().intValue() > 0) {
                loadDataFromTextboxs();
//                responseOperacion = usuarioBean.guardarUsuario(usuarioSelected, Boolean.TRUE);
                if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
//                    MensajeMultilinea.show(responseOperacion.getMensajeRespuesta() + " Id Usuario:" + responseOperacion.getUsuario().getNombreUsuario(), Constants.MENSAJE_TIPO_INFO);
//                    usuarioSelected = responseOperacion.getUsuario();
                    loadDataFromEntity();
                    doReadOnly(Boolean.TRUE);
                    doEditButton();
                    listaUsuarioCtrl.refresTotalRegistros();
                    listaUsuarioCtrl.refreshModel(0);
                } else {
                    MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
                }
                setToken(0);
            } else if (getToken().intValue() == 0) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Se intento guardar el mismo cliente dos veces, por seguridad solo se proceso una vez ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.getMessage(), Constants.MENSAJE_TIPO_ERROR);
        }
    }

    public void onClick$btnEdit(Event event) {
        doReadOnly(Boolean.FALSE);
        btnUpdate.setVisible(true);
        btnEdit.setVisible(false);
    }

    public void onClick$btnNew(Event event) {
        doNew();
    }

    public void onClick$btnClose(Event event) throws InterruptedException {
        doClose();
    }

    public void onClick$btnUpdate(Event event) throws InterruptedException {
        doActualizar();
        this.btnUpdate.setVisible(false);
    }

    private void doClose() {
        this.detalleUsuarioWindow.onClose();
    }

    private void doNew() {
        doClear();
        doReadOnly(Boolean.FALSE);
        btnSave.setVisible(true);
        btnClose.setVisible(true);
        btnUpdate.setVisible(false);
        checkEstadoUsuario.setChecked(Boolean.FALSE);
        btnEdit.setVisible(false);
        btnNew.setVisible(false);
    }

    private void doEditButton() {
        this.btnClose.setVisible(true);
        this.btnEdit.setVisible(true);
        this.btnNew.setVisible(true);
        this.btnSave.setVisible(false);
        this.btnUpdate.setVisible(false);
    }

    public void doReadOnly(Boolean opt) {
        txtClaveUsuario.setReadonly(opt);
        txtClaveUsuario2.setReadonly(opt);
        txtNombreUsuario.setReadonly(opt);
        txtNumerocarne.setReadonly(opt);
        txtRegistrosLista.setReadonly(opt);
        txtUsuarioSistema.setReadonly(opt);

    }

    public void doClear() {
        txtClaveUsuario.setValue(null);
        txtClaveUsuario2.setValue(null);
        txtNombreUsuario.setValue(null);
        txtNumerocarne.setValue(null);
        txtRegistrosLista.setValue(null);
        txtUsuarioSistema.setValue(null);

    }

    public void doActualizar() {
        usuarioSelected.setStatus(checkEstadoUsuario.isChecked());
        usuarioSelected.setCodigoEmpleado(txtNumerocarne.getValue());
        usuarioSelected.setContrasena(txtClaveUsuario2.getValue());
        usuarioSelected.setNombreCompleto(txtNombreUsuario.getValue());
        usuarioSelected.setNombreUsuario(txtUsuarioSistema.getValue());
        usuarioSelected.setRegistrosLista(txtRegistrosLista.getValue());
//        try {
//            responseOperacion = usuarioBean.guardarUsuario(usuarioSelected, Boolean.FALSE);
        if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
//                MensajeMultilinea.show("Usuario actualizado Satisfactoriamente", Constants.MENSAJE_TIPO_INFO);
//                usuarioSelected = responseOperacion.getUsuario();
            loadDataFromEntity();
            doReadOnly(Boolean.TRUE);
            doEditButton();
            listaUsuarioCtrl.refresTotalRegistros();
            listaUsuarioCtrl.refreshModel(0);
        } else {
            MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
        }
//        } catch (DiservWebException bex) {
//            bex.printStackTrace();
//            MensajeMultilinea.show(bex.toString(), Constants.MENSAJE_TIPO_ERROR);
//        }
        doReadOnly(Boolean.TRUE);
        doEditButton();
    }

    private void checkPermisos() {
//        this.btnEliminar.setVisible(SecurityUtil.isAnyGranted(
//                "ROLE_ADMINISTRADOR"));
//        this.btnActualizar.setVisible(SecurityUtil.isAnyGranted(
//                "ROLE_ADMINISTRADOR"));
//        this.btnEditar.setVisible(SecurityUtil.isAnyGranted(
//                "ROLE_ADMINISTRADOR,VERIFICAR_FACTURA_COBRO"));
//        this.btnNuevo.setVisible(SecurityUtil.isAnyGranted(
//                "ROLE_ADMINISTRADOR,AGREGAR_CLIENTE"));
    }

    public Integer getToken() {
        return this.token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    public Usuarios getUsuarioSelected() {
        return usuarioSelected;
    }

    public void setUsuarioSelected(Usuarios usuarioSelected) {
        this.usuarioSelected = usuarioSelected;
    }

    public ListaUsuariosCtrl getListaUsuarioCtrl() {
        return listaUsuarioCtrl;
    }

    public void setListaUsuarioCtrl(ListaUsuariosCtrl listaUsuarioCtrl) {
        this.listaUsuarioCtrl = listaUsuarioCtrl;
    }
}
