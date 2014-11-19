package sv.com.diserv.liquidaciones.web.ui.bodegas;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.web.ui.usuarios.util.BaseController;

public class DetalleBodegaCtrl extends BaseController {

    private static final Logger logger = Logger.getLogger(DetalleBodegaCtrl.class.getName());
    private static final long serialVersionUID = -546886879998950467L;
    protected Window detalleClienteWindow;
    protected Intbox txtIdCliente;
    protected Textbox txtNombreCliente;
    protected Textbox txtDireccion;
    protected Textbox txtNumeroNit;
    protected Textbox txtRegistroIva;
    protected Textbox txtCorreoElectronico;
    protected Textbox txtDepartamento;
    protected Textbox txtMunicipio;
    protected Checkbox checkEstadoCliente;
    protected Button btnActualizar;
    protected Button btnNuevo;
    protected Button btnEditar;
    protected Button btnGuardar;
    protected Button btnCerrar;
    private Clientes clienteSelected;
    private ListaBodegaCtrl listaClientesCtrl;
    private transient Integer token;
    private ClienteBeanLocal clienteBean;
    private ServiceLocator serviceLocator;
    private OperacionesClienteDTO responseOperacion;
    private List<Clientes> listaClientesLike;

    public DetalleBodegaCtrl() {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            clienteBean = serviceLocator.getService(Constants.JNDI_CLIENTE_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$detalleClienteWindow(Event event) throws Exception {
        doOnCreateCommon(this.detalleClienteWindow, event);
        MensajeMultilinea.doSetTemplate();
        if (this.args.containsKey("clienteSelected")) {
            clienteSelected = ((Clientes) this.args.get("clienteSelected"));
            setClienteSelected(clienteSelected);
        }
        if (this.args.containsKey("token")) {
            this.token = ((Integer) this.args.get("token"));
            setToken(this.token);
        } else {
            setToken(Integer.valueOf(0));
        }
        if (this.args.containsKey("listaClienteCtrl")) {
            listaClientesCtrl = ((ListaBodegaCtrl) this.args.get("listaClienteCtrl"));
        }
        checkPermisos();
        showDetalleCliente();
    }

    public void showDetalleCliente() {
        try {
            if (clienteSelected != null) {
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
            detalleClienteWindow.doModal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * cargamos los textboxs desde entity
     */
    private void loadDataFromEntity() {
        txtCorreoElectronico.setValue("testing");
        txtDepartamento.setValue(clienteSelected.getDepartamento());
        txtDireccion.setValue(clienteSelected.getDireccion());
        txtIdCliente.setValue(clienteSelected.getIdCliente());
        txtMunicipio.setValue(clienteSelected.getMunicipio());
        txtNombreCliente.setText(clienteSelected.getNombreCliente());
        txtNumeroNit.setValue(clienteSelected.getNitCliente());
        txtRegistroIva.setValue(clienteSelected.getIvaCliente());
        txtCorreoElectronico.setValue(clienteSelected.getEmailCliente());
        checkEstadoCliente.setChecked(clienteSelected.getEstadoCliente() != null ? clienteSelected.getEstadoCliente() : false);
    }

    private void loadDataFromTextboxs() {
        try {
            clienteSelected = new Clientes();
            //validamos los campos
            if (StringUtils.isEmpty(txtDepartamento.getValue())) {
                throw new AtsWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar departamento cliente");
            }
            if (StringUtils.isEmpty(txtDireccion.getValue())) {
                throw new AtsWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar dirección para cliente");
            }
            if (StringUtils.isEmpty(txtMunicipio.getValue())) {
                throw new AtsWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar municipio cliente");
            }
            if (StringUtils.isEmpty(txtNombreCliente.getValue())) {
                throw new AtsWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar  Nombre Cliente cliente");
            }
            if (StringUtils.isEmpty(txtNumeroNit.getValue())) {
                throw new AtsWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar   Numero Nit  para  cliente");
            }
            if (StringUtils.isEmpty(txtRegistroIva.getValue())) {
                throw new AtsWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar   registro iva para cliente");
            }
            if (checkEstadoCliente.isChecked()) {
                clienteSelected.setEstadoCliente(checkEstadoCliente.isChecked());
            }
            clienteSelected.setDepartamento(txtDepartamento.getValue());
            clienteSelected.setDireccion(txtDireccion.getValue());
            clienteSelected.setIdCliente(txtIdCliente.getValue());
            clienteSelected.setMunicipio(txtMunicipio.getValue());
            clienteSelected.setNombreCliente(txtNombreCliente.getValue());
            clienteSelected.setNitCliente(txtNumeroNit.getValue());
            clienteSelected.setIvaCliente(txtRegistroIva.getValue());
            clienteSelected.setEmailCliente(txtCorreoElectronico.getValue());
            clienteSelected.setEstadoCliente(checkEstadoCliente.isChecked());
        } catch (AtsWebException ex) {
            MensajeMultilinea.show(ex.getMensaje(), Constants.MENSAJE_TIPO_ERROR);
        }

    }

    public void onClick$btnGuardar(Event event) {
        try {
            if (getToken().intValue() > 0) {
                loadDataFromTextboxs();
                responseOperacion = clienteBean.guardarCliente(clienteSelected);
                if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                    MensajeMultilinea.show(responseOperacion.getMensajeRespuesta() + " Id cliente:" + responseOperacion.getCliente().getIdCliente(), Constants.MENSAJE_TIPO_INFO);
                    clienteSelected = responseOperacion.getCliente();
                    loadDataFromEntity();
                    doReadOnly(Boolean.TRUE);
                    doEditButton();
                    listaClientesCtrl.refreshModel(0);
                } else {
                    MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
                }
                setToken(0);
            } else if (getToken().intValue() == 0) {
                throw new AtsWebException(Constants.CODE_OPERATION_FALLIDA, "Se intento guardar el mismo cliente dos veces, por seguridad solo se proceso una vez ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.getMessage(), Constants.MENSAJE_TIPO_ERROR);
        }
    }

    public void onClick$btnEditar(Event event) {
        doReadOnly(Boolean.FALSE);
        this.btnActualizar.setVisible(true);
        this.btnEditar.setVisible(false);
    }

    public void onClick$btnNuevo(Event event) {
        doNew();
    }

    public void onClick$btnCerrar(Event event) throws InterruptedException {
        doClose();
    }

    public void onClick$btnActualizar(Event event) throws InterruptedException {
        doActualizar();
        this.btnActualizar.setVisible(false);
    }

    public void onBlur$txtNombreCliente(Event event) throws AtsWebException {
        try {
            if (StringUtils.isEmpty(txtNombreCliente.getValue())) {
                throw new AtsWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar nombre cliente");
            }
            listaClientesLike = clienteBean.loadAllClienteByLike(txtNombreCliente.getValue());
            if (listaClientesLike.size() > 0) {
                throw new AtsWebException(Constants.CODE_OPERATION_FALLIDA, "Ya existe un cliente con un nombre similar:\n" + listaClientesLike.get(0).getNombreCliente() + "\nId Cliente:" + listaClientesLike.get(0).getIdCliente());
            }
        } catch (AtsWebException web) {
            MensajeMultilinea.show(web.getMensaje(), Constants.MENSAJE_TIPO_ALERTA);
        } catch (AtsBusinessException ex) {
            ex.printStackTrace();
        }
    }

    private void doClose() {
        this.detalleClienteWindow.onClose();
    }

    public void onDoubleClicked(Event event) throws Exception {
    logger.info("[onDoubleClicked]");
//        Listitem item = this.listBoxOrdentrabajoClientes.getSelectedItem();
//
//        if (item != null) {
//            Ordentrabajo ordentrabajo = (Ordentrabajo) item.getAttribute("data");
//
//            HashMap map = new HashMap();
//            map.put("ordentrabajo", ordentrabajo);
//
//            map.put("lbOrdentabajo", this.listBoxOrdentrabajoClientes);
//            map.put("ordentrabajoCtrl", getOrdencontroller());
//
//            Executions.createComponents("/html/ordentrabajo/detalleordentrabajo.zul",
//                    null, map);
//        }
    }

    private void doNew() {
        doClear();
        doReadOnly(Boolean.FALSE);
        this.btnGuardar.setVisible(true);
        this.btnCerrar.setVisible(true);
        this.btnActualizar.setVisible(false);
        this.checkEstadoCliente.setChecked(Boolean.FALSE);
        this.btnEditar.setVisible(false);
        this.btnNuevo.setVisible(false);
    }

    private void doEditButton() {
        this.btnCerrar.setVisible(true);
        this.btnEditar.setVisible(true);
        this.btnNuevo.setVisible(true);
        this.btnGuardar.setVisible(false);
        this.btnActualizar.setVisible(false);
    }

    public void doReadOnly(Boolean opt) {
        txtCorreoElectronico.setReadonly(opt);
        txtDepartamento.setReadonly(opt);
        txtDireccion.setReadonly(opt);
        txtMunicipio.setReadonly(opt);
        txtNombreCliente.setReadonly(opt);
        txtNumeroNit.setReadonly(opt);
        txtRegistroIva.setReadonly(opt);
    }

    public void doClear() {
        txtCorreoElectronico.setValue(null);
        txtDepartamento.setValue(null);
        txtDireccion.setValue(null);
        txtIdCliente.setValue(null);
        txtMunicipio.setValue(null);
        txtNombreCliente.setValue(null);
        txtNumeroNit.setValue(null);
        txtRegistroIva.setValue(null);
        txtNombreCliente.setFocus(true);
    }

    public void doActualizar() {
        loadDataFromTextboxs();
        try {
            responseOperacion = clienteBean.actualizarCliente(clienteSelected);
            if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta() + " Id cliente:" + responseOperacion.getCliente().getIdCliente(), Constants.MENSAJE_TIPO_INFO);
                clienteSelected = responseOperacion.getCliente();
                loadDataFromEntity();
                doReadOnly(Boolean.TRUE);
                doEditButton();
                listaClientesCtrl.refreshModel(Constants.PAGINA_INICIO_DEFAULT);
            } else {
                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
            }
        } catch (AtsBusinessException bex) {
            bex.printStackTrace();
            logger.severe(_zclass);
            MensajeMultilinea.show(bex.toString(), Constants.MENSAJE_TIPO_ERROR);

        }
//        ListModelList lml = (ListModelList) getLbCliente().getListModel();
//        if (lml.indexOf(cliente) == -1) {
//            lml.add(cliente);
//        } else {
//            lml.set(lml.indexOf(cliente), cliente);
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

    public Clientes getClienteSelected() {
        return clienteSelected;
    }

    public void setClienteSelected(Clientes clienteSelected) {
        this.clienteSelected = clienteSelected;
    }

    public ListaBodegaCtrl getListaClientesCtrl() {
        return listaClientesCtrl;
    }

    public void setListaClientesCtrl(ListaBodegaCtrl listaClientesCtrl) {
        this.listaClientesCtrl = listaClientesCtrl;
    }

    public List<Clientes> getListaClientesLike() {
        return listaClientesLike;
    }

    public void setListaClientesLike(List<Clientes> listaClientesLike) {
        this.listaClientesLike = listaClientesLike;
    }
}
