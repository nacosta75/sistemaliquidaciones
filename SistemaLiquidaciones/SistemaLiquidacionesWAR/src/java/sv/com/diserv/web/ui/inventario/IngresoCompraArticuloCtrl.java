package sv.com.diserv.web.ui.inventario;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.ejb.ArticulosBeanLocal;
import sv.com.diserv.liquidaciones.entity.Articulos;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.web.ui.inventario.rendered.ArticulosItemRendered;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;


public class IngresoCompraArticuloCtrl extends BaseController  {

    static final Logger logger = Logger.getLogger(IngresoCompraArticuloCtrl.class);
    private static final long serialVersionUID = -546886879998950467L;
    protected Window encabezadoCompraWindow;
    protected Button btnImprimir;
    protected Button btnNuevo;
    protected Button btnEditar;
    protected Button btnDelete;
    protected Button btnGuardar;
    //protected Button btnActualizar;
    protected Button btnCerrar;
    protected Button btnCancelar;
    protected Combobox comboProductos;
    protected Intbox txtNumeroTelefono;
    protected Textbox txtNumeroImei;
    protected Textbox txtNumeroIcc;
    protected Button btnAgregarItem;
    protected Button btnImportarArchivo;

    protected Listbox listBoxDetalleProductos;
    private Articulos articuloSelected;
    private ArticulosBeanLocal articulosBean;
    private ServiceLocator serviceLocator;
    private List<Articulos> listaArticulos;
    private Integer tokenOperation;
    private Textbox txtDescripcion;

    public IngresoCompraArticuloCtrl() {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
//            articulosBean = serviceLocator.getService(Constants.JNDI_CLIENTE_BEAN);
//            facturacionBean = serviceLocator.getService(Constants.JNDI_FACTURACION_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.log(Level.ERROR, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$encabezadoIngresoCompra(Event event) throws Exception {
        doOnCreateCommon(this.encabezadoCompraWindow, event);
        MensajeMultilinea.doSetTemplate();
        if (this.args.containsKey("token")) {
            tokenOperation = ((Integer) this.args.get("token"));
            setTokenOperation(tokenOperation);
        } else {
            setTokenOperation(0);
        }
        showCompra();
    }

    public void showCompra() {
//            try {
//                listaArticulos= articulosBean.loadAllArticulos(MODAL, MODAL)facturacionBean.loadAllEstadoFactura();
//                comboEstadoDocumento.setModel(new ListModelList(listaEstadoFactura));
//                comboEstadoDocumento.setItemRenderer(new EstadoFacturaItemRenderer());
//                if (detalleOrdenCtrl != null) {
//                    doMostrarDetalleFactura();
//                } else if (documentoSelected != null) {
//                    doReadOnly(Boolean.TRUE);
//                    doEditButton();
//                    loadDataFromEntity();
//                } else {
//                    doNew();
//                }
//                encabezadoCompraWindow.doModal();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
    }
    //

    public void onChanging$comboProductos(ForwardEvent e) {
        logger.log(Level.INFO, "[onChanging$comboProductos]");
        InputEvent input = (InputEvent) e.getOrigin();
        System.out.println("input value=" + input.getValue());
        try {
            listaArticulos = articulosBean.loadAllArticuloByLike(input.getValue().trim());
            if (listaArticulos.size() > 0) {
                comboProductos.setModel(new ListModelList(listaArticulos));
                comboProductos.setItemRenderer(new ArticulosItemRendered());
            }
        } catch (Exception ex) {
            logger.log(Level.INFO, "[onChanging$comboDelitos]No se pudo cargar delitos");
            ex.printStackTrace();
        }
    }

    public void onSelect$comboProductos(Event event) throws InterruptedException {
        logger.log(Level.INFO, "[onSelect$comboProductos]");
        Comboitem item = this.comboProductos.getSelectedItem();
        if (item != null) {
            articuloSelected = (Articulos) item.getAttribute("data");
            txtDescripcion.setValue(articuloSelected.getDescarticulo());
        } else {
            logger.log(Level.INFO, "[onSelect$comboNombreCliente]NO ha seleccionado cliente");
        }
    }

    //    private void doMostrarDetalleFactura() {
    //        try {
    //            if (detalleOrdenCtrl.getOrdenSelected() != null) {
    //                documentoSelected = facturacionBean.findDocumentoClienteByIdDFacturaAndSerie(detalleOrdenCtrl.getOrdenSelected().getSerieFactura(), detalleOrdenCtrl.getOrdenSelected().getNumFact());
    //                if (documentoSelected != null) {
    //                    doReadOnly(Boolean.TRUE);
    //                    btnCerrar.setVisible(true);
    //                    btnEditar.setVisible(false);
    //                    btnNuevo.setVisible(false);
    //                    btnGuardar.setVisible(false);
    //                    btnActualizar.setVisible(false);
    //                    btnAgregarItem.setVisible(false);
    //                    loadDataFromEntity();
    //                }
    //            }
    //        } catch (Exception e) {
    //            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
    //            e.printStackTrace();
    //            MensajeMultilinea.show("No se pudo cargar documento Cliente", Constants.MENSAJE_TIPO_ERROR);
    //        }
    //    }
    //
    //    /**
    //     *
    //     * @param event
    //     * @throws InterruptedException
    //     */
    //    public void onSelect$comboTipoDocumento(Event event) throws InterruptedException {
    //        logger.log(Level.INFO, "[onSelect$listboxTipoDocumento]");
    //        Comboitem item = this.comboTipoDocumento.getSelectedItem();
    //        if (item != null) {
    //            tipoDocumentoSelected = (TipoDocumentoDTO) item.getValue();
    //            System.out.println("tipo:" + tipoDocumentoSelected);
    //            //cargamos el numero de serie para el documento seleccionado;
    //        }
    //    }
    //
    //    /**
    //     * cargamos los textboxs desde entity
    //     */
    //    private void loadDataFromEntity() {
    //        comboEstadoDocumento.setText(documentoSelected.getIdEstado() != null ? documentoSelected.getIdEstado().getDesEstado() : "");
    //        txtIdCliente.setValue(documentoSelected.getIdCliente().getIdCliente());
    //        txtNumeroDocumento.setValue(documentoSelected.getIdfactura());
    //        txtNumeroNIT.setValue(documentoSelected.getIdCliente().getNitCliente());
    //        comboTipoDocumento.setText(documentoSelected.getTipoDocumento());
    //        txtRegistroIVA.setValue(documentoSelected.getIdCliente().getIvaCliente());
    //        comboNombreCliente.setReadonly(true);
    //        comboNombreCliente.setText(documentoSelected.getIdCliente().getNombreCliente());
    //        txtIdCliente.setReadonly(true);
    //        comboEstadoDocumento.setText(documentoSelected.getIdEstado() != null ? documentoSelected.getIdEstado().getDesEstado() : "");
    //        //cargamos detalle de factura
    //        loadDetalleDocumento();
    //        //llenamos combo estados posibles
    //    }
    //
    //    public void loadDetalleDocumento() {
    //        try {
    //            listaDetalleDocumento = facturacionBean.findDetalleDocumentoByRequest(documentoSelected.getIdfactura(), documentoSelected.getSerieFactura());
    //            if (listaDetalleDocumento != null) {
    //                listBoxDetalleDocumento.setModel(new ListModelList(listaDetalleDocumento));
    //                listBoxDetalleDocumento.setItemRenderer(new DetalleDocumentoItemRenderer());
    //            }
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //    }
    //
    //    public void onDoubleClickedDetalleDocumento(Event event) {
    //        logger.log(Level.INFO, "[onDoubleClickedDetalleDocumento]Event:{0}", event.toString());
    //        try {
    //            loadDetalleFactura();
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //    }
    //
    //    private void loadDataFromTextboxs() {
    //        try {
    //            if (documentoSelected == null) {
    //                documentoSelected = new Documentocliente();
    //            }
    //            //validamos los campos
    //            if (clienteSelected == null) {
    //                throw new AtsWebException(Constants.CODE_OPERATION_FALLIDA, "Debe seleccionar cliente antes de crear documento cobro");
    //            }
    //            if (txtNumeroDocumento.getValue() == null) {
    //                throw new AtsWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar número de documento para poder crear documento");
    //            }
    //            if (tipoDocumentoSelected == null) {
    //                throw new AtsWebException(Constants.CODE_OPERATION_FALLIDA, "Debe seleccionar tipo documento");
    //            }
    //            documentoSelected.setTipoDocumento(tipoDocumentoSelected.getIdTipoDocumento());
    //            documentoSelected.setIdfactura(txtNumeroDocumento.getValue());
    //            documentoSelected.setFechaCreacion(UtilFormat.getFechaActual());
    //            documentoSelected.setIdfactura(txtNumeroDocumento.getValue());
    //            documentoSelected.setIdusuarioIngresa(getUserLogin().getUsuario().getIdUsuario());
    //            if (tipoDocumentoSelected.getIdTipoDocumento().equalsIgnoreCase(Constants.CODE_TIPO_DOCUMENTO_CCF)) {
    //                documentoSelected.setSerieFactura(CONFIGURACION_SISTEMA.getSerieCreditoFiscal());
    //                documentoSelected.setEsexento(Constants.NO_ES_EXENTO);
    //            } else if (tipoDocumentoSelected.getIdTipoDocumento().equalsIgnoreCase(Constants.CODE_TIPO_DOCUMENTO_EXPO)) {
    //                documentoSelected.setSerieFactura(CONFIGURACION_SISTEMA.getSerieFacturaExportacion());
    //                documentoSelected.setEsexento(Constants.ES_EXENTO);
    //            }
    //            documentoSelected.setIdCliente(clienteSelected);
    //            if (estadoFacturaSelected != null) {
    //                documentoSelected.setIdEstado(estadoFacturaSelected);
    //            }
    //        } catch (AtsWebException ex) {
    //            MensajeMultilinea.show(ex.getMensaje(), Constants.MENSAJE_TIPO_ERROR);
    //        }
    //    }
    //
    //    public void onClick$btnGuardar(Event event) {
    //        try {
    //            if (getToken().intValue() > 0) {
    //                loadDataFromTextboxs();
    //                documentoSelected.setIdEstado(new EstadoFactura(Constants.ESTADO_ELABORADO));
    //                documentoSelected.setImpreso(Constants.DOCUMENTO_NO_IMPRESO);
    //                responseOperacion = facturacionBean.guardarDocumetoCliente(documentoSelected, Boolean.TRUE);
    //                if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
    //                    MensajeMultilinea.show(responseOperacion.getMensajeRespuesta() + " Id documento Cliente:" + responseOperacion.getDocumento().getIdfactura());
    //                    reloadInformacionDocumento();
    //                    btnAgregarItem.setVisible(Boolean.TRUE);
    //                } else {
    //                    MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
    //                }
    //                setToken(0);
    //            } else if (getToken().intValue() == 0) {
    //                throw new AtsWebException(Constants.CODE_OPERATION_FALLIDA, "Se intento guardar el mismo cliente dos veces, por seguridad solo se proceso una vez ");
    //            }
    //        } catch (AtsBusinessBusinessRolledbackException e) {
    //            MensajeMultilinea.show(e.getMensaje(), Constants.MENSAJE_TIPO_ERROR);
    //        } catch (AtsWebException excep) {
    //            MensajeMultilinea.show(excep.getMensaje(), Constants.MENSAJE_TIPO_ERROR);
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //            MensajeMultilinea.show(e.toString(), Constants.MENSAJE_TIPO_ERROR);
    //        }
    //    }
    //
    //    private void reloadInformacionDocumento() {
    //        documentoSelected = null;
    //        documentoSelected = responseOperacion.getDocumento();
    //        loadDataFromEntity();
    //        doReadOnly(Boolean.TRUE);
    //        doEditButton();
    //        btnImprimir.setVisible(true);
    //        listaDocumentoClienteCtrl.doRefreshModel(Constants.PAGINA_INICIO_CERO);
    //    }
    //
    //    public void onClick$btnEditar(Event event) {
    //        doReadOnly(Boolean.FALSE);
    //        //VALIDAMOS SI LA FACTURA ESTA IMPRESA O NO
    //        if (documentoSelected.getImpreso().equalsIgnoreCase(Constants.DOCUMENTO_NO_IMPRESO)) {
    //            comboNombreCliente.setDisabled(Boolean.FALSE);
    //            comboNombreCliente.setReadonly(Boolean.FALSE);
    //            txtIdCliente.setReadonly(Boolean.FALSE);
    //        } else {
    //            comboNombreCliente.setDisabled(Boolean.TRUE);
    //            txtIdCliente.setReadonly(Boolean.TRUE);
    //            txtNumeroDocumento.setReadonly(Boolean.TRUE);
    //            comboEstadoDocumento.setDisabled(Boolean.FALSE);
    //        }
    //        comboTipoDocumento.setDisabled(true);
    //        this.btnActualizar.setVisible(true);
    //        this.btnEditar.setVisible(false);
    //    }
    //
    //    public void onClick$btnNuevo(Event event) {
    //        doNew();
    //    }
    //
    //    public void onClick$btnCerrar(Event event) throws InterruptedException {
    //        doClose();
    //    }
    //
    //    public void onClick$btnActualizar(Event event) throws InterruptedException {
    //        doActualizar();
    //        this.btnActualizar.setVisible(false);
    //    }
    //
    //    public void onClick$btnAgregarGastoTramite(Event event) {
    //        logger.log(Level.INFO, "[onClick$btnAgregarGastoTramite]Event:{0}", event.toString());
    //        try {
    //            HashMap map = new HashMap();
    ////            map.put("ordenSelected", ordenSelected);
    //            map.put("token", UtilFormat.getToken());
    //            map.put("detalleOrdenCtrl", this);
    //            Executions.createComponents("/WEB-INF/xhtml/opempleado/detalleGastosOrdentrabajo.zul", null, map);
    //        } catch (Exception e) {
    //            e.printStackTrace();
    //        }
    //    }
    //
    //    public void onBlur$txtIdCliente(Event event) {
    //        try {
    //            if (StringUtils.isNotEmpty(txtIdCliente.getText())) {
    //                clienteSelected = clienteBean.loadClienteByID(txtIdCliente.getValue());
    //                if (clienteSelected != null) {
    //                    txtIdCliente.setValue(clienteSelected.getIdCliente());
    //                    txtNumeroNIT.setValue(clienteSelected.getNitCliente());
    //                    txtRegistroIVA.setValue(clienteSelected.getIvaCliente());
    //                    comboNombreCliente.setText(clienteSelected.getNombreCliente());
    //                } else {
    //                    txtIdCliente.setValue(null);
    //                    txtNumeroNIT.setValue(null);
    //                    txtRegistroIVA.setValue(null);
    //                    comboNombreCliente.setText(null);
    //                    throw new AtsWebException(Constants.CODE_OPERACION_SATISFACTORIA, "No se recupero cliente para el id ingresado");
    //                }
    //            }
    //        } catch (AtsWebException web) {
    //            MensajeMultilinea.show(web.getMensaje(), Constants.MENSAJE_TIPO_ALERTA);
    //        } catch (AtsBusinessException ex) {
    //            MensajeMultilinea.show(ex.getMensaje(), Constants.MENSAJE_TIPO_ERROR);
    //            ex.printStackTrace();
    //        }
    //    }
    //
    //    public void onClick$btnImprimir(Event event) throws InterruptedException {
    //        logger.info("[onClick$btnImprimir]");
    //        try {
    //            ConsultaFacturaDTO printer;
    //            Component parent = getRoot();
    //            if (documentoSelected != null) {
    //                //cargamos lista detalle factura para impresion
    //                printer = facturacionBean.cargarInformacionFactura(documentoSelected);
    //                if (printer.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
    //                    ManejadorReporte.loadReporteFactura(printer, getUserLogin().getUsuario(), parent, "Impresion Documento Cliente", "reporteFactura");
    //                    documentoSelected.setImpreso(Constants.DOCUMENTO_IMPRESO);
    //                    documentoSelected.setFechaImpresion(new Date());
    //                    documentoSelected.setIdEstado(new EstadoFactura(Constants.CODE_DOCUMENTO_IMPRESO));
    //                    responseOperacion = facturacionBean.guardarDocumetoCliente(documentoSelected, Boolean.FALSE);
    //                    documentoSelected = null;
    //                    documentoSelected = responseOperacion.getDocumento();
    //                    loadDataFromEntity();
    //                    doReadOnly(Boolean.TRUE);
    //                    doEditButton();
    //                    listaDocumentoClienteCtrl.doRefreshModel(Constants.PAGINA_INICIO_CERO);
    //                } else {
    //                    MensajeMultilinea.show("No se recupero detalle documento cliente", Constants.MENSAJE_TIPO_ERROR);
    //                }
    //            }
    //        } catch (AtsBusinessBusinessRolledbackException e) {
    //            MensajeMultilinea.show(e.getMensaje(), Constants.MENSAJE_TIPO_ERROR);
    //        } catch (Exception e) {
    //            logger.log(Level.INFO, "[onClick$btnImprimirReporte]Exception:{0}", e.toString());
    //            e.printStackTrace();
    //            MensajeMultilinea.show(e.getMessage(), Constants.MENSAJE_TIPO_ERROR);
    //        }
    //    }
    //
    //    private void doClose() {
    //        this.encabezadoFacturaWindow.onClose();
    //    }
    //
    //    public void onDoubleClicked(Event event) throws Exception {
    ////        Listitem item = this.listBoxOrdentrabajoClientes.getSelectedItem();
    ////
    ////        if (item != null) {
    ////            Ordentrabajo ordentrabajo = (Ordentrabajo) item.getAttribute("data");
    ////
    ////            HashMap map = new HashMap();
    ////            map.put("ordentrabajo", ordentrabajo);
    ////
    ////            map.put("lbOrdentabajo", this.listBoxOrdentrabajoClientes);
    ////            map.put("ordentrabajoCtrl", getOrdencontroller());
    ////
    ////            Executions.createComponents("/html/ordentrabajo/detalleordentrabajo.zul",
    ////                    null, map);
    ////        }
    //    }
    //
    //    public void onClick$btnAgregarItem(Event event) {
    //        logger.log(Level.INFO, "[onClick$btnAgregarItem]Event:{0}", event.toString());
    //        try {
    //            loadDetalleFactura();
    //        } catch (Exception a) {
    //            a.printStackTrace();
    //        }
    //    }
    //
    //    private void loadDetalleFactura() {
    //        logger.log(Level.INFO, "[loadDetalleFactura]");
    //        try {
    //            HashMap map = new HashMap();
    //            Listitem item = this.listBoxDetalleDocumento.getSelectedItem();
    //            if (item != null) {
    //                detalleDocSelected = (DetalleDocumentoCliente) item.getAttribute("data");
    //                map.put("detalleSelected", detalleDocSelected);
    //            }
    //            map.put("token", UtilFormat.getToken());
    //            map.put("encabezadoFacturaCtrl", this);
    //            map.put("encabezado", documentoSelected);
    //            Executions.createComponents("/WEB-INF/xhtml/facturacion/detalleFactura.zul", null, map);
    //        } catch (Exception a) {
    //            a.printStackTrace();
    //        }
    //    }
    //
    //    private void doNew() {
    //        doClear();
    //        doReadOnly(Boolean.FALSE);
    //        btnGuardar.setVisible(true);
    //        btnCerrar.setVisible(true);
    //        btnActualizar.setVisible(false);
    //        btnEditar.setVisible(false);
    //        btnNuevo.setVisible(false);
    //        btnImprimir.setVisible(false);
    //        btnAgregarItem.setVisible(false);
    //    }
    //
    //    private void doEditButton() {
    //        this.btnCerrar.setVisible(true);
    //        this.btnEditar.setVisible(true);
    //        this.btnNuevo.setVisible(true);
    //        this.btnGuardar.setVisible(false);
    //        this.btnActualizar.setVisible(false);
    //    }
    //
    //    public void doReadOnly(Boolean opt) {
    ////        comboEstadoDocumento.setReadonly(opt);
    //        txtIdCliente.setReadonly(opt);
    //        txtNumeroDocumento.setReadonly(opt);
    //        comboNombreCliente.setDisabled(opt);
    //        comboTipoDocumento.setDisabled(opt);
    //    }
    //
    //    public void doClear() {
    ////        txtEstadoDocumento.setValue(null);
    //        txtIdCliente.setValue(null);
    //        txtNumeroDocumento.setValue(null);
    ////        txtNumeroNIT.setValue(null);
    //        //        txtRegistroIVA.setValue(null);
    //        comboNombreCliente.setSelectedIndex(-1);
    //        comboTipoDocumento.setSelectedIndex(-1);
    //
    //    }
    //
    //    public void doActualizar() {
    //        logger.info("[doActualizar]");
    //        try {
    //            if (clienteSelected != null) {
    //                documentoSelected.setIdCliente(clienteSelected);
    //            }
    //            if (StringUtils.isEmpty(txtNumeroDocumento.getText())) {
    //                throw new AtsWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar numero documento valido");
    //            } else {
    //                documentoSelected.setIdfactura(txtNumeroDocumento.getValue());
    //            }
    //            if (estadoFacturaSelected != null) {
    //                documentoSelected.setIdEstado(estadoFacturaSelected);
    //            }
    //            responseOperacion = facturacionBean.guardarDocumetoCliente(documentoSelected, Boolean.FALSE);
    //            if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
    //                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta() + ",Documeneto Cliente Actualizado Satisfactoriamente", Constants.MENSAJE_TIPO_INFO);
    //                reloadInformacionDocumento();
    //            } else {
    //                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
    //            }
    //        } catch (AtsWebException ex) {
    //            MensajeMultilinea.show(ex.getMensaje(), Constants.MENSAJE_TIPO_ERROR);
    //        } catch (Exception bex) {
    //            bex.printStackTrace();
    //            MensajeMultilinea.show(bex.toString(), Constants.MENSAJE_TIPO_ERROR);
    //        }
    //    }
    //
    //    private void checkPermisos() {
    ////        this.btnEliminar.setVisible(SecurityUtil.isAnyGranted(
    ////                "ROLE_ADMINISTRADOR"));
    ////        this.btnActualizar.setVisible(SecurityUtil.isAnyGranted(
    ////                "ROLE_ADMINISTRADOR"));
    ////        this.btnEditar.setVisible(SecurityUtil.isAnyGranted(
    ////                "ROLE_ADMINISTRADOR,VERIFICAR_FACTURA_COBRO"));
    ////        this.btnNuevo.setVisible(SecurityUtil.isAnyGranted(
    ////                "ROLE_ADMINISTRADOR,AGREGAR_CLIENTE"));
    //    }
    //
    //    public void onChanging$comboNombreCliente(ForwardEvent e) {
    //        logger.log(Level.INFO, "[onChanging$comboNombreCliente]");
    //        InputEvent input = (InputEvent) e.getOrigin();
    //        System.out.println("input value=" + input.getValue());
    //        try {
    //            listaClientes = clienteBean.loadAllClienteByLike(input.getValue().trim());
    //            if (listaClientes.size() > 0) {
    //                comboNombreCliente.setModel(new ListModelList(listaClientes));
    //                comboNombreCliente.setItemRenderer(new BusquedaClienteItemRenderer());
    //            }
    //        } catch (Exception ex) {
    //            logger.log(Level.INFO, "[onChanging$comboDelitos]No se pudo cargar delitos");
    //            ex.printStackTrace();
    //        }
    //    }
    //
    //    public void onSelect$comboNombreCliente(Event event) throws InterruptedException {
    //        logger.log(Level.INFO, "[onSelect$comboNombreCliente]");
    //        Comboitem item = this.comboNombreCliente.getSelectedItem();
    //        if (item != null) {
    //            clienteSelected = (Clientes) item.getAttribute("data");
    //            txtIdCliente.setValue(clienteSelected.getIdCliente());
    //            txtNumeroNIT.setValue(clienteSelected.getNitCliente());
    //            txtRegistroIVA.setValue(clienteSelected.getIvaCliente());
    //        } else {
    //            logger.log(Level.INFO, "[onSelect$comboNombreCliente]NO ha seleccionado cliente");
    //        }
    //    }
    //
    //    public void onSelect$comboEstadoDocumento(Event event) throws InterruptedException {
    //        logger.log(Level.INFO, "[onSelect$comboNombreCliente]");
    //        Comboitem item = this.comboEstadoDocumento.getSelectedItem();
    //        if (item != null) {
    //            estadoFacturaSelected = (EstadoFactura) item.getAttribute("data");
    //        } else {
    //            logger.log(Level.INFO, "[onSelect$comboNombreCliente]NO ha seleccionado cliente");
    //        }
    //    }
    //
    //    public Integer getToken() {
    //        return this.token;
    //    }
    //
    //    public void setToken(Integer token) {
    //        this.token = token;
    //    }
    //
    //    public Clientes getClienteSelected() {
    //        return clienteSelected;
    //    }
    //
    //    public void setClienteSelected(Clientes clienteSelected) {
    //        this.clienteSelected = clienteSelected;
    //    }
    //
    //    public List<Clientes> getListaClientes() {
    //        return listaClientes;
    //    }
    //
    //    public void setListaClientes(List<Clientes> listaClientes) {
    //        this.listaClientes = listaClientes;
    //    }
    //
    //    public Documentocliente getDocumentoSelected() {
    //        return documentoSelected;
    //    }
    //
    //    public void setDocumentoSelected(Documentocliente documentoSelected) {
    //        this.documentoSelected = documentoSelected;
    //    }
    //
    //    public ListaDocumentoClienteCtrl getListaDocumentoClienteCtrl() {
    //        return listaDocumentoClienteCtrl;
    //    }
    //
    //    public void setListaDocumentoClienteCtrl(ListaDocumentoClienteCtrl listaDocumentoClienteCtrl) {
    //        this.listaDocumentoClienteCtrl = listaDocumentoClienteCtrl;
    //    }
    //
    //    public List<DetalleDocumentoCliente> getListaDetalleDocumento() {
    //        return listaDetalleDocumento;
    //    }
    //
    //    public void setListaDetalleDocumento(List<DetalleDocumentoCliente> listaDetalleDocumento) {
    //        this.listaDetalleDocumento = listaDetalleDocumento;
    //    }
    //
    //    public List<TipoDocumentoDTO> getListaTipoDocumento() {
    //        return listaTipoDocumento;
    //    }
    //
    //    public void setListaTipoDocumento(List<TipoDocumentoDTO> listaTipoDocumento) {
    //        this.listaTipoDocumento = listaTipoDocumento;
    //    }
    //
    //    public DetalleOrdenTrabajoCtrl getDetalleOrdenCtrl() {
    //        return detalleOrdenCtrl;
    //    }
    //
    //    public void setDetalleOrdenCtrl(DetalleOrdenTrabajoCtrl detalleOrdenCtrl) {
    //    }
    //    }
    public Articulos getArticuloSelected() {
        return articuloSelected;
    }

    public void setArticuloSelected(Articulos articuloSelected) {
        this.articuloSelected = articuloSelected;
    }

    public List<Articulos> getListaArticulos() {
        return listaArticulos;
    }

    public void setListaArticulos(List<Articulos> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }

    public Integer getTokenOperation() {
        return tokenOperation;
    }

    public void setTokenOperation(Integer tokenOperation) {
        this.tokenOperation = tokenOperation;
    }
}
