package sv.com.diserv.web.ui.inventario;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.web.ui.util.BaseController;
//import sv.com.ats.business.dto.BusquedaOrdentrabajoDTO;
//import sv.com.ats.business.dto.ConsultaFacturaDTO;
//import sv.com.ats.business.dto.OperacionesDocumentoClienteDTO;
//import sv.com.ats.business.ejb.FacturacionBeanLocal;
//import sv.com.ats.business.ejb.OrdenTrabajoBeanLocal;
//import sv.com.ats.business.entity.DetalleDocumentoCliente;
//import sv.com.ats.business.entity.Documentocliente;
//import sv.com.ats.business.entity.Ordentrabajo;
//import sv.com.ats.business.entity.ServiciosPrestados;
//import sv.com.ats.business.exception.AtsBusinessException;
//import sv.com.ats.business.exception.AtsWebException;
//import sv.com.ats.business.exception.ServiceLocatorException;
//import sv.com.ats.business.util.Constants;
//import sv.com.ats.business.util.ServiceLocator;
//import sv.com.ats.business.util.UtilFormat;
//import sv.com.ats.ui.facturacion.rendered.BusquedaOrdenTrabajoItemRenderer;
//import sv.com.ats.ui.facturacion.rendered.TipoServicioClienteItemRenderer;
//import sv.com.ats.web.ui.util.BaseController;
//import sv.com.ats.web.ui.util.MensajeMultilinea;

public class DetalleFacturaCtrl extends BaseController {

    static final Logger logger = Logger.getLogger(DetalleFacturaCtrl.class.getCanonicalName());
    private static final long serialVersionUID = -546886879998950467L;
    protected Window detalleFacturaWindow;
    protected Button btnActualizar;
    protected Button btnNuevo;
    protected Button btnEditar;
    protected Button btnGuardar;
    protected Button btnCerrar;
    protected Button btnVerGastos;
    protected Button btnEliminarItem;
    protected Combobox comboIdOrdentrabajo;
    protected Checkbox checkCambiarEstado;
    protected Textbox txtReferenciaAduana;
    protected Textbox txtNumeroDeclaracion;
    protected Textbox txtNombreCliente;
    protected Combobox comboTipoServicio;
    protected Intbox txtCantidad;
    protected Doublebox txtPrecioUnitario;
    protected Doublebox txtPrecioTotal;
//    private Documentocliente documentoSelected;
//    private DetalleDocumentoCliente detalleDocumentoSelected;
//    private EncabezadoFacturaCtrl encabezadoFacturaCtrl;
//    private ServiceLocator serviceLocator;
//    private List<Ordentrabajo> listaOrdentrabajo;
//    private Ordentrabajo ordenSelected;
//    private FacturacionBeanLocal facturacionBean;
//    private List<ServiciosPrestados> listaServicios;
//    private ServiciosPrestados servicioSelected;
//    private BusquedaOrdentrabajoDTO requestOrdentrabajo;
//    private OrdenTrabajoBeanLocal ordentrabajoBean;
//    private OperacionesDocumentoClienteDTO responseOperacion;
//    private Boolean tieneGastos;
//    private ConsultaFacturaDTO facturaImpresionSelected;

//    public DetalleFacturaCtrl() {
//        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl]INIT");
//        try {
//            serviceLocator = ServiceLocator.getInstance();
//            ordentrabajoBean = serviceLocator.getService(Constants.JNDI_ORDENTRABAJO_BEAN);
//            facturacionBean = serviceLocator.getService(Constants.JNDI_FACTURACION_BEAN);
//        } catch (ServiceLocatorException ex) {
//            logger.log(Level.SEVERE, ex.getLocalizedMessage());
//            ex.printStackTrace();
//        }
//    }

    public void onCreate$detalleFacturaWindow(Event event) throws Exception {
//        doOnCreateCommon(this.detalleFacturaWindow, event);
//        MensajeMultilinea.doSetTemplate();
//        if (this.args.containsKey("encabezadoFacturaCtrl")) {
//            encabezadoFacturaCtrl = ((EncabezadoFacturaCtrl) this.args.get("encabezadoFacturaCtrl"));
//            setEncabezadoFacturaCtrl(encabezadoFacturaCtrl);
//        }
//        if (this.args.containsKey("encabezado")) {
//            documentoSelected = ((Documentocliente) this.args.get("encabezado"));
//            setDocumentoSelected(documentoSelected);
//        }
//        if (this.args.containsKey("detalleSelected")) {
//            detalleDocumentoSelected = ((DetalleDocumentoCliente) this.args.get("detalleSelected"));
//            setDetalleDocumentoSelected(detalleDocumentoSelected);
//        }
//        checkPermisos();
//        loadTipoServicioCliente();
//        showDetalleOrdentrabajo();
    }

//    private void loadTipoServicioCliente() {
//        try {
//            listaServicios = facturacionBean.loadAllServiciosCliente();
//            if (listaServicios.size() > 0) {
//                comboTipoServicio.setModel(new ListModelList(listaServicios));
//                comboTipoServicio.setItemRenderer(new TipoServicioClienteItemRenderer());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void showDetalleOrdentrabajo() {
//        try {
//            if (detalleDocumentoSelected != null) {
//                doReadOnly(Boolean.TRUE);
//                doEditButton();
//                loadDataFromEntity();
//            } else {
//                doNew();
//            }
//            detalleFacturaWindow.doModal();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void onSelect$comboTipoServicio(Event event) throws InterruptedException {
//        logger.log(Level.INFO, "[onSelect$comboTipoServicio]");
//        Comboitem item = comboTipoServicio.getSelectedItem();
//        if (item != null) {
//            servicioSelected = (ServiciosPrestados) item.getAttribute("data");
//            logger.info("[onSelect$listBoxTipoServicio]Servicio:" + servicioSelected.getDescripcion());
//        }
//    }
//
//    /**
//     * cargamos los textboxs desde entity
//     */
//    private void loadDataFromEntity() {
//        comboIdOrdentrabajo.setText(detalleDocumentoSelected.getIdOrdenTrabajo().getIdOrdenTrabajo());
//        txtCantidad.setValue(detalleDocumentoSelected.getCantidad());
//        txtNombreCliente.setValue(detalleDocumentoSelected.getIdOrdenTrabajo().getIdCliente().getNombreCliente());
//        txtNumeroDeclaracion.setValue(detalleDocumentoSelected.getIdOrdenTrabajo().getDeclaracion());
//        txtPrecioTotal.setValue(detalleDocumentoSelected.getPrecioTotal());
//        txtPrecioUnitario.setValue(detalleDocumentoSelected.getPrecioUnitario());
//        txtReferenciaAduana.setValue(detalleDocumentoSelected.getIdOrdenTrabajo().getReferencia());
//        comboTipoServicio.setText(detalleDocumentoSelected.getIdservicio().getDescripcion());
//
//    }
//
//    private void loadDataFromTextboxs() {
//        try {
//            if (detalleDocumentoSelected == null) {
//                detalleDocumentoSelected = new DetalleDocumentoCliente();
//            }
////            //validamos los campos
//            if (ordenSelected == null) {
//                throw new AtsWebException(Constants.CODE_OPERATION_FALLIDA, "Debe seleccionar una ordentrabajo para asociar servicio");
//            }
//            if (servicioSelected == null) {
//                throw new AtsWebException(Constants.CODE_OPERATION_FALLIDA, "Debe seleccionar una un servicio para poder facturar");
//            }
//            if (StringUtils.isEmpty(txtCantidad.getText())) {
//                throw new AtsWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar cantidad a facuturar");
//            }
//            if (StringUtils.isEmpty(txtPrecioUnitario.getText())) {
//                throw new AtsWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar precio unitario para poder cobrar servicio");
//            }
//            if (checkCambiarEstado.isChecked()) {
//                detalleDocumentoSelected.setCambiarEstado(Boolean.TRUE);
//            }
//            detalleDocumentoSelected.setCantidad(txtCantidad.getValue());
//            detalleDocumentoSelected.setDescripcion(servicioSelected.getDescripcion());
//            if (documentoSelected.getTipoDocumento().equalsIgnoreCase("CF")) {
//                detalleDocumentoSelected.setEsExento('N');
//            } else {
//                detalleDocumentoSelected.setEsExento('S');
//            }
//            detalleDocumentoSelected.setSerieFactura(documentoSelected.getSerieFactura());
//            detalleDocumentoSelected.setIdfactura(documentoSelected.getIdfactura());
//            detalleDocumentoSelected.setIdOrdenTrabajo(ordenSelected);
//            detalleDocumentoSelected.setIdservicio(servicioSelected);
//            detalleDocumentoSelected.setPrecioUnitario(txtPrecioUnitario.getValue().doubleValue());
//            detalleDocumentoSelected.setPrecioTotal(detalleDocumentoSelected.getCantidad() * detalleDocumentoSelected.getPrecioUnitario());
//        } catch (AtsWebException ex) {
//            MensajeMultilinea.show(ex.getMensaje(), Constants.MENSAJE_TIPO_ERROR);
//        }
//    }
//
//    public void onClick$btnGuardar(Event event) {
//        try {
//            loadDataFromTextboxs();
//            responseOperacion = facturacionBean.guardarDetalleDocumentoCliente(detalleDocumentoSelected, documentoSelected, Boolean.TRUE);
//            if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
//                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_INFO);
//                detalleDocumentoSelected = responseOperacion.getDetalleDocumento();
//                loadDataFromEntity();
//                doReadOnly(Boolean.TRUE);
//                doEditButton();
//                //refrescamos la lista detalle factura
//                encabezadoFacturaCtrl.loadDetalleDocumento();
//                limpiarTextos();
//                btnEliminarItem.setVisible(Boolean.TRUE);
//            } else {
//                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            MensajeMultilinea.show(e.getMessage(), Constants.MENSAJE_TIPO_ERROR);
//        }
//    }
//
//    public void onBlur$txtCantidad(Event event) {
//        logger.info("[onBlur$txtCantidad]");
//        if (txtCantidad.getValue() != null && txtPrecioUnitario.getValue() != null) {
//            txtPrecioTotal.setValue(txtCantidad.getValue() * txtPrecioUnitario.getValue());
//        }
//    }
//
//    public void onBlur$txtPrecioUnitario(Event event) {
//        logger.info("[onBlur$txtPrecioUnitario]");
//        if (txtCantidad.getValue() != null && txtPrecioUnitario.getValue() != null) {
//            txtPrecioTotal.setValue(txtCantidad.getValue() * txtPrecioUnitario.getValue());
//        }
//    }
//
//    private void limpiarTextos() {
//        txtCantidad.setValue(null);
//        comboTipoServicio.setSelectedIndex(-1);
//        comboTipoServicio.setText("Seleccione:.");
//        txtPrecioUnitario.setValue(null);
//        txtPrecioTotal.setValue(null);
//
//    }
//
//    public void onClick$btnEditar(Event event) {
//        doReadOnly(Boolean.FALSE);
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
//            map.put("ordenSelected", ordenSelected);
//            map.put("token", UtilFormat.getToken());
//            map.put("detalleOrdenCtrl", this);
//            Executions.createComponents("/WEB-INF/xhtml/opempleado/detalleGastosOrdentrabajo.zul", null, map);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void onClick$btnEliminarItem(Event event) {
//        logger.log(Level.INFO, "[onClick$btnEliminarItem]Event:{0}", event.toString());
//        try {
//            doDeleteDetalleDocumento();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void doDeleteDetalleDocumento() {
//        logger.info("[doDeleteDetalleDocumento]");
//        try {
//            MensajeMultilinea.show(Constants.MSG_ELIMINAR_REGISTRO, Constants.MENSAJE_TIPO_INTERRROGACION, new EventListener() {
//                @Override
//                public void onEvent(Event evt) {
//                    try {
//                        Integer opSelected = (((Integer) evt.getData()).intValue());
//                        System.out.println("opselected;" + opSelected);
//                        if (opSelected == Messagebox.OK) {
//                            responseOperacion = facturacionBean.eliminarDetalleDocumentoCliente(detalleDocumentoSelected, documentoSelected);
//                            if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
//                                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_INFO);
//                                detalleDocumentoSelected = responseOperacion.getDetalleDocumento();
//                                loadDataFromEntity();
//                                doReadOnly(Boolean.TRUE);
//                                doEditButton();
//                                //refrescamos la lista detalle factura
//                                encabezadoFacturaCtrl.loadDetalleDocumento();
//                                limpiarTextos();
//                                btnEliminarItem.setVisible(Boolean.FALSE);
//                            } else {
//                                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
//                            }
//                        } else {
//                            doClose();
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        MensajeMultilinea.show("Ocurrió un error al eliminar el registro", Constants.MENSAJE_TIPO_ALERTA);
//                    }
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//            MensajeMultilinea.show("Ocurrio un error al cargar los componentes", Constants.MENSAJE_TIPO_ALERTA);
//        }
//    }
//
//    public void onBlur$txtReferenciaAduana(Event event) {
//        logger.info("[onBlur$txtReferenciaAduana]");
//        try {
//            if (StringUtils.isNotEmpty(txtReferenciaAduana.getValue())) {
//                ordenSelected = null;
//                requestOrdentrabajo = new BusquedaOrdentrabajoDTO();
//                requestOrdentrabajo.setReferenciaAduana(txtReferenciaAduana.getValue().trim());
//                listaOrdentrabajo = ordentrabajoBean.findOrdentrabajoByRequest(requestOrdentrabajo, 0, 1);
//                if (listaOrdentrabajo.size() > 0) {
//                    ordenSelected = listaOrdentrabajo.get(0);
//                    reloadDataOrdenSelected(Boolean.TRUE);
//                } else {
//                    throw new AtsWebException(Constants.CODE_OPERACION_SATISFACTORIA, "No se recupero ordentrabajo para el id ingresado");
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
//    public void onBlur$txtNumeroDeclaracion(Event event) {
//        logger.info("[onBlur$txtNumeroDeclaracion]");
//        try {
//            if (StringUtils.isNotEmpty(txtNumeroDeclaracion.getValue())) {
//                ordenSelected = null;
//                requestOrdentrabajo = new BusquedaOrdentrabajoDTO();
//                requestOrdentrabajo.setNumeroDeclaracion(txtNumeroDeclaracion.getValue().trim());
//                listaOrdentrabajo = ordentrabajoBean.findOrdentrabajoByRequest(requestOrdentrabajo, 0, 1);
//                if (listaOrdentrabajo.size() > 0) {
//                    ordenSelected = listaOrdentrabajo.get(0);
//                    reloadDataOrdenSelected(Boolean.TRUE);
//                } else {
//                    throw new AtsWebException(Constants.CODE_OPERACION_SATISFACTORIA, "No se recupero ordentrabajo para el id ingresado");
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
//    private void doClose() {
//        this.detalleFacturaWindow.onClose();
//    }
//
//    private void doNew() {
//        doClear();
//        doReadOnly(Boolean.FALSE);
//        this.btnGuardar.setVisible(true);
//        this.btnCerrar.setVisible(true);
//        this.btnActualizar.setVisible(false);
//        this.btnEditar.setVisible(false);
//        this.btnNuevo.setVisible(false);
//        this.btnEliminarItem.setVisible(false);
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
//        comboIdOrdentrabajo.setDisabled(opt);
//        txtReferenciaAduana.setReadonly(opt);
//        txtNumeroDeclaracion.setReadonly(opt);
//        comboTipoServicio.setDisabled(opt);
//        txtCantidad.setReadonly(opt);
//        txtPrecioUnitario.setReadonly(opt);
//    }
//
//    public void doClear() {
////        txtEstadoDocumento.setValue(null);
////        txtIdCliente.setValue(null);
////        txtNumeroDocumento.setValue(null);
//////        txtNumeroNIT.setValue(null);
////        txtNumeroSerie.setValue(null);
//////        txtRegistroIVA.setValue(null);
////        comboNombreCliente.setSelectedIndex(-1);
////        listboxTipoDocumento.setSelectedIndex(-1);
//    }
//
//    public void doActualizar() {
////        loadDataFromTextboxs();
//        try {
//            if (checkCambiarEstado.isChecked()) {
//                detalleDocumentoSelected.setCambiarEstado(Boolean.TRUE);
//            }
//            detalleDocumentoSelected.setCantidad(txtCantidad.getValue());
//            detalleDocumentoSelected.setDescripcion(servicioSelected.getDescripcion());
//            if (documentoSelected.getTipoDocumento().equalsIgnoreCase("CF")) {
//                detalleDocumentoSelected.setEsExento('N');
//            } else {
//                detalleDocumentoSelected.setEsExento('S');
//            }
////            detalleDocumentoSelected.setSerieFactura(documentoSelected.getSerieFactura());
//            detalleDocumentoSelected.setIdfactura(documentoSelected.getIdfactura());
//            if (ordenSelected != null) {
//                detalleDocumentoSelected.setIdOrdenTrabajo(ordenSelected);
//            }
//            if (servicioSelected != null) {
//                detalleDocumentoSelected.setIdservicio(servicioSelected);
//            }
//            detalleDocumentoSelected.setPrecioUnitario(txtPrecioUnitario.getValue().doubleValue());
//            detalleDocumentoSelected.setPrecioTotal(detalleDocumentoSelected.getCantidad() * detalleDocumentoSelected.getPrecioUnitario());
//            responseOperacion = facturacionBean.guardarDetalleDocumentoCliente(detalleDocumentoSelected, documentoSelected, Boolean.FALSE);
//            if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
//                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta());
//                detalleDocumentoSelected = responseOperacion.getDetalleDocumento();
//                loadDataFromEntity();
//                doReadOnly(Boolean.TRUE);
//                doEditButton();
//                //refrescamos la lista detalle factura
//                encabezadoFacturaCtrl.loadDetalleDocumento();
//                limpiarTextos();
//            } else {
//                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            MensajeMultilinea.show(e.toString(), Constants.MENSAJE_TIPO_ERROR);
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
//    public void onChanging$comboIdOrdentrabajo(ForwardEvent e) {
//        logger.log(Level.INFO, "[onChanging$comboIdOrdentrabajo]");
//        InputEvent input = (InputEvent) e.getOrigin();
//        System.out.println("input value=" + input.getValue());
//        try {
//            requestOrdentrabajo = new BusquedaOrdentrabajoDTO();
//            requestOrdentrabajo.setIdOrdentrabajo(input.getValue().trim());
//            listaOrdentrabajo = ordentrabajoBean.findOrdentrabajoByRequest(requestOrdentrabajo, 0, Constants.CANTIDAD_REGISTROS_COMBO);
//            if (listaOrdentrabajo.size() > 0) {
//                comboIdOrdentrabajo.setModel(new ListModelList(listaOrdentrabajo));
//                comboIdOrdentrabajo.setItemRenderer(new BusquedaOrdenTrabajoItemRenderer());
//            }
//        } catch (Exception ex) {
//            logger.log(Level.INFO, "[onChanging$comboDelitos]No se pudo cargar delitos");
//            ex.printStackTrace();
//        }
//    }
//
//    public void onSelect$comboIdOrdentrabajo(Event event) throws InterruptedException {
//        logger.log(Level.INFO, "[onSelect$comboIdOrdentrabajo]");
//        Comboitem item = this.comboIdOrdentrabajo.getSelectedItem();
//        ordenSelected = null;
//        if (item != null) {
//            ordenSelected = (Ordentrabajo) item.getAttribute("data");
//            reloadDataOrdenSelected(Boolean.FALSE);
//            //cargamos gastos si tiene
//            validateTieneGastos();
//        } else {
//            logger.info("[onSelect$comboIdOrdentrabajo]NO ha seleccionado ordentrabajo");
//        }
//    }
//
//    private void validateTieneGastos() {
//        logger.info("[validateTieneGastos]");
//        try {
//            tieneGastos = ordentrabajoBean.countAllGastosOrdentrabajoByIdOrdentrabajo(ordenSelected.getIdOrdenTrabajo());
//            btnVerGastos.setVisible(tieneGastos);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void onClick$btnVerGastos(Event event) {
//        logger.log(Level.INFO, "[onClick$btnVerGastos]Event:{0}", event.toString());
//        try {
//            HashMap map = new HashMap();
//            map.put("ordenSelected", ordenSelected);
//            map.put("token", UtilFormat.getToken());
//            map.put("detalleOrdenCtrl", this);
//            Executions.createComponents("/WEB-INF/xhtml/opempleado/detalleGastosOrdentrabajo.zul", null, map);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void reloadDataOrdenSelected(Boolean reloadCombo) {
//        if (reloadCombo) {
//            comboIdOrdentrabajo.setText(ordenSelected.getIdOrdenTrabajo());
//        }
//        txtNombreCliente.setValue(ordenSelected.getIdCliente().getNombreCliente());
//        txtNumeroDeclaracion.setValue(ordenSelected.getDeclaracion());
//        txtReferenciaAduana.setValue(ordenSelected.getReferencia());
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
//    public DetalleDocumentoCliente getDetalleDocumentoSelected() {
//        return detalleDocumentoSelected;
//    }
//
//    public void setDetalleDocumentoSelected(DetalleDocumentoCliente detalleDocumentoSelected) {
//        this.detalleDocumentoSelected = detalleDocumentoSelected;
//    }
//
//    public List<Ordentrabajo> getListaOrdentrabajo() {
//        return listaOrdentrabajo;
//    }
//
//    public void setListaOrdentrabajo(List<Ordentrabajo> listaOrdentrabajo) {
//        this.listaOrdentrabajo = listaOrdentrabajo;
//    }
//
//    public List<ServiciosPrestados> getListaServicios() {
//        return listaServicios;
//    }
//
//    public void setListaServicios(List<ServiciosPrestados> listaServicios) {
//        this.listaServicios = listaServicios;
//    }
//
//    public ServiciosPrestados getServicioSelected() {
//        return servicioSelected;
//    }
//
//    public void setServicioSelected(ServiciosPrestados servicioSelected) {
//        this.servicioSelected = servicioSelected;
//    }
//
//    public EncabezadoFacturaCtrl getEncabezadoFacturaCtrl() {
//        return encabezadoFacturaCtrl;
//    }
//
//    public void setEncabezadoFacturaCtrl(EncabezadoFacturaCtrl encabezadoFacturaCtrl) {
//        this.encabezadoFacturaCtrl = encabezadoFacturaCtrl;
//    }
}
