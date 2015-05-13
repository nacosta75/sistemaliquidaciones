/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.inventario;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Priority;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.dto.BusquedaPersonaDTO;
import sv.com.diserv.liquidaciones.ejb.PersonasBeanLocal;
import sv.com.diserv.liquidaciones.entity.Personas;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.liquidaciones.util.UtilFormat;
import sv.com.diserv.web.ui.personas.rendered.PersonaItemRenderer;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import sv.com.diserv.liquidaciones.dto.OperacionesMovimientoDetDTO;
import sv.com.diserv.liquidaciones.ejb.MovimientosBeanLocal;
import sv.com.diserv.liquidaciones.ejb.MovimientosDetBeanLocal;
import sv.com.diserv.liquidaciones.entity.Movimientos;
import sv.com.diserv.liquidaciones.entity.MovimientosDet;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.util.TokenGenerator;
import sv.com.diserv.web.ui.inventario.rendered.DetalleMovimientoItemRenderer;

/**
 *
 * @author abraham.acosta
 */
public class EncabezadoCompraCtrl extends BaseController {

    static final Logger logger = Logger.getLogger(EncabezadoCompraCtrl.class.toString());
    private static final long serialVersionUID = -5546886879998950489L;

    protected Window encabezadoCompraWindow;
    protected Button btnNuevo;
    protected Button btnEditar;
    protected Button btnDelete;
    protected Button button_OrderDialog_btnDelete;
    protected Button btnSave;
    protected Button btnCancelar;
    protected Button btnCerrar;
    protected Button btnImprimir;
    protected Button btnActualizar;
    protected Textbox txtPersonaName;
    protected Textbox txtPersonaCod;
    protected Textbox txtFacturaNo;
    protected Textbox txtObservaciones;

    private BusquedaPersonaDTO request;

    //busqueda de proveedor
    protected Listbox listBoxCustomerSearch;
    protected Listheader listheader_CustNo;
    protected Listheader listheader_CustMatchcode;
    protected Listheader listheader_CustName1;
    protected Listheader listheader_CustTel;
   // protected Listheader lhNoRegistro;

    // bandbox searchCustomer
    protected Bandbox bandbox_OrderDialog_CustomerSearch;
    private Object paging_OrderDialog_CustomerSearchList;
    private transient List<Personas> searchObjCustomer;
    private final int pageSizeSearchCustomers = 20;
    protected Intbox tb_Orders_SearchCustNo;
    protected Textbox tb_Orders_CustSearchMatchcode;
    protected Textbox tb_Orders_SearchCustName1;
    //protected Button button_OrderList_OrderNameSearch;
    //protected Button button_OrderList_NewOrder;
    protected Listbox listBoxDetalleCompra;

    //detalle
    protected Paging paging_ListBoxOrderOrderPositions2;
    private Integer totalMovimiento;
    private Integer numeroPaginInicio;
    private transient Integer token;

    private PersonasBeanLocal personaBean;
    private MovimientosBeanLocal movimientoBean;
    private ServiceLocator serviceLocator;
    private Personas personas;

    private Movimientos compraSelected;
    private MovimientosDet detalleMovimientoSelected;
    private MovimientosDetBeanLocal movimientosDetBean;
    private OperacionesMovimientoDetDTO responseOperacion;

    private ListaComprasCtrl listaComprasCtrl;
    private List<MovimientosDet> listaDetalleMovimiento;

    public EncabezadoCompraCtrl() {
        logger.log(Level.INFO, "[EncabezadoCompraCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            movimientoBean = serviceLocator.getService(Constants.JNDI_MOVIMIENTOS_BEAN);
            personaBean = serviceLocator.getService(Constants.JNDI_PERSONA_BEAN);
            movimientosDetBean = serviceLocator.getService(Constants.JNDI_MOVIMIENTOSDET_BEAN);
            numeroPaginInicio = 0;
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$encabezadoCompraWindow(Event event) throws Exception {
        doOnCreateCommon(this.encabezadoCompraWindow, event);
        MensajeMultilinea.doSetTemplate();
        if (this.args.containsKey("compraSelected")) {
            compraSelected = ((Movimientos) this.args.get("compraSelected"));
            setCompraSelected(compraSelected);
        }
        if (this.args.containsKey("token")) {
            this.token = ((Integer) this.args.get("token"));
            setToken(this.token);
        } else {
            setToken(Integer.valueOf(0));
        }
        if (this.args.containsKey("listaComprasCtrl")) {
            listaComprasCtrl = ((ListaComprasCtrl) this.args.get("listaComprasCtrl"));
        }
        //  checkPermisos();
        showDetalleLineas();

    }

    public void onClick$button_bbox_CustomerSearch_Close(Event event) {
        // logger.debug(event.toString());

        bandbox_OrderDialog_CustomerSearch.close();
    }

    public void onOpen$bandbox_OrderDialog_CustomerSearch(Event event) throws Exception {

        listheader_CustNo.setSortAscending(new FieldComparator("facturaNo", true));
        listheader_CustNo.setSortDescending(new FieldComparator("facturaNo", false));
        listheader_CustMatchcode.setSortAscending(new FieldComparator("codPersona", true));
        listheader_CustMatchcode.setSortDescending(new FieldComparator("codPersona", false));
        listheader_CustName1.setSortAscending(new FieldComparator("personaName", true));
        listheader_CustName1.setSortDescending(new FieldComparator("personaName", false));

        BuscarProveedor();
    }

    public void onClick$button_bbox_CustomerSearch_Search(Event event) {
        BuscarProveedor();
    }

    private void BuscarProveedor() {
        logger.log(Level.INFO, "[BuscarProveedor][refreshModel]Recargar proveedores");
        try {
            request = new BusquedaPersonaDTO();

            if (StringUtils.isNotEmpty(tb_Orders_SearchCustNo.getText())) {
                request.setIdPersona(tb_Orders_SearchCustNo.getValue());
            }
            if (StringUtils.isNotEmpty(tb_Orders_SearchCustName1.getValue())) {
                request.setNombre(tb_Orders_SearchCustName1.getValue().toUpperCase());
            }

            if (StringUtils.isNotEmpty(tb_Orders_CustSearchMatchcode.getValue())) {
                request.setNumeroRegistro(tb_Orders_CustSearchMatchcode.getValue());
            }

            request.setTipoPersona(3);
            searchObjCustomer = personaBean.buscarPersonaByCriteria(request);

            if (!searchObjCustomer.isEmpty()) {
                listBoxCustomerSearch.setModel(new ListModelList(searchObjCustomer));
                listBoxCustomerSearch.setItemRenderer(new PersonaItemRenderer());
            } else {

                listBoxCustomerSearch.setEmptyMessage("No se encontraron registros con los criterios ingresados!!");
                MensajeMultilinea.show("No se encontraron proveedores con los criterios ingresados", Constants.MENSAJE_TIPO_ALERTA);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.toString(), Constants.MENSAJE_TIPO_ERROR);
        }
    }

    public void onClick$button_OrderDialog_NewOrderPosition(Event event) {
        logger.log(Level.INFO, "[onclick$button_OrderDialog_NewOrderPosition]Event:{0}", event.toString());

        if (getCompraSelected() == null || getCompraSelected().getIdmov()==null) {
            MensajeMultilinea.show("Debe Guardar Primero el Encabezado de la Compra!!!", Constants.MENSAJE_TIPO_ALERTA);
            //doCancel();
            return;
        }

        HashMap map = new HashMap();
        map.put("token", UtilFormat.getToken());
        map.put("encabezadoCompra", compraSelected);
        map.put("encabezadoCompraCtrl", this);

        try {
            Executions.createComponents("/WEB-INF/xhtml/inventario/detalleCompraDialog.zul", null, map);
        } catch (Exception a) {
            a.printStackTrace();
            logger.warning("NewOrder:: error opening window / " + a.getMessage());
            // Show a error box
            String msg = a.getMessage();
            MensajeMultilinea.doSetTemplate();
            MensajeMultilinea.show(msg, Constants.MENSAJE_TIPO_ALERTA);

        }

    }

    public void onDoubleClickedDetalleMovimiento(Event event) throws Exception {
        logger.log(Level.INFO, "[onDoubleClickedDetalleMovimiento]Event:{0}", event.toString());
        Listitem item = this.listBoxDetalleCompra.getSelectedItem();
        if (item != null) {
            detalleMovimientoSelected = (MovimientosDet) item.getAttribute("data");

            HashMap map = new HashMap();
            map.put("articulo", detalleMovimientoSelected.getIdarticulo());
            map.put("encabezadoCompra", compraSelected);
            map.put("detalleMovimientoSelected", detalleMovimientoSelected);
            map.put("token", TokenGenerator.getTokenOperation());
            map.put("encabezadoCompraCtrl", this);

            Executions.createComponents("/WEB-INF/xhtml/inventario/detalleCompraDialog.zul", null, map);
        }
    }

    public BusquedaPersonaDTO getRequest() {
        return request;
    }

    public void setRequest(BusquedaPersonaDTO request) {
        this.request = request;
    }

    public Integer getNumeroPaginInicio() {
        return numeroPaginInicio;
    }

    public void setNumeroPaginInicio(Integer numeroPaginInicio) {
        this.numeroPaginInicio = numeroPaginInicio;
    }

    public Movimientos getCompraSelected() {
        return compraSelected;
    }

    public void setCompraSelected(Movimientos compraSelected) {
        this.compraSelected = compraSelected;
    }

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    public Integer getTotalMovimiento() {
        return totalMovimiento;
    }

    public void setTotalMovimiento(Integer totalMovimiento) {
        this.totalMovimiento = totalMovimiento;
    }

    public List<MovimientosDet> getListaDetalleMovimiento() {
        return listaDetalleMovimiento;
    }

    public void setListaDetalleMovimiento(List<MovimientosDet> listaDetalleMovimiento) {
        this.listaDetalleMovimiento = listaDetalleMovimiento;
    }

    private void showDetalleLineas() {

        try {
            if (compraSelected != null) {
                doReadOnly(Boolean.TRUE);
                doEditButton();
                loadDataFromEntity();

            } else {
                doNew();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromEntity() {

        try {
            txtPersonaName.setValue(compraSelected.getIdpersona().getNombre());
            txtPersonaCod.setValue(compraSelected.getIdpersona().getNoRegistroFiscal());
            txtFacturaNo.setValue(compraSelected.getNodoc().toString());
            txtObservaciones.setValue(compraSelected.getObserva1());

            showDetalleCompra();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showDetalleCompra() throws Exception {
        logger.log(Level.INFO, "[showDetalleCompra][refreshModel]Recargar detalle");
        if (compraSelected != null) {
            listaDetalleMovimiento = movimientoBean.loadDetalleMovimientoByIdMovimento(compraSelected.getIdmov());
            if (listaDetalleMovimiento.size() > 0) {
                listBoxDetalleCompra.setModel(new ListModelList(listaDetalleMovimiento));
                listBoxDetalleCompra.setItemRenderer(new DetalleMovimientoItemRenderer());
            } else {
                logger.info("No se cargaron registros");
                listBoxDetalleCompra.setModel(new ListModelList(listaDetalleMovimiento));
                listBoxDetalleCompra.setEmptyMessage("Factura no tiene items agregados");
            }
        }
    }

    private void doReadOnly(Boolean opt) {
        txtPersonaName.setReadonly(opt);
        txtFacturaNo.setReadonly(opt);
        txtPersonaCod.setReadonly(opt);
        txtObservaciones.setReadonly(opt);
    }

    private void doEditButton() {
        this.btnCerrar.setVisible(true);
        this.btnEditar.setVisible(false);
        this.btnNuevo.setVisible(true);
        this.btnSave.setVisible(false);
        this.btnDelete.setVisible(false);
        this.btnImprimir.setVisible(false);
        this.btnCancelar.setVisible(false);
        this.btnActualizar.setVisible(false);
    }

    private void doNew() {

        doClear();
        doReadOnly(Boolean.FALSE);

        this.btnCerrar.setVisible(false);
        this.btnEditar.setVisible(false);
        this.btnNuevo.setVisible(false);
        this.btnSave.setVisible(true);
        this.btnDelete.setVisible(false);
        this.btnImprimir.setVisible(true);
        this.btnCancelar.setVisible(true);
        this.btnActualizar.setVisible(false);
    }

    private void doClear() {

        txtPersonaName.setValue(null);
        txtFacturaNo.setValue(null);
    }

    public void onClick$btnEditar(Event event) {
        doEditButton();
        doReadOnly(Boolean.FALSE);
        this.btnActualizar.setVisible(true);
        this.btnEditar.setVisible(false);
        this.btnCancelar.setVisible(true);
    }

    public void onClick$btnCerrar(Event event) throws InterruptedException {
        doClose();
    }

    public void eliminarArticulo() {
        try {
            MensajeMultilinea.show(Constants.MSG_ELIMINAR_REGISTRO, Constants.MENSAJE_TIPO_INTERRROGACION, new EventListener() {
                @Override
                public void onEvent(Event evt) throws DiservBusinessException {
                    try {
                        Integer opSelected = (((Integer) evt.getData()).intValue());
                        if (opSelected == Messagebox.OK) {
                            Listitem item = listBoxDetalleCompra.getSelectedItem();
                            detalleMovimientoSelected = (MovimientosDet) item.getAttribute("data");
                            if (item != null) {
                                responseOperacion = movimientosDetBean.eliminarMovimientoDet(detalleMovimientoSelected);
                                if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                                    doEditButton();
                                    refreshModel(0);
                                } else {
                                    MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
                                }

                            }// no null                       
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        MensajeMultilinea.show("Ocurrió un error al eliminar el registro", Constants.MENSAJE_TIPO_ALERTA);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show("Ocurrio un error al cargar los componentes", Constants.MENSAJE_TIPO_ALERTA);
        }
    }

    public void onClick$button_OrderDialog_btnDelete(Event event) throws InterruptedException {
        eliminarArticulo();
    }

    private void doClose() {
        this.encabezadoCompraWindow.onClose();
    }

    public void onDoubleClickedPersona(Event event) throws DiservBusinessException {

        Listitem item = this.listBoxCustomerSearch.getSelectedItem();
        if (item != null) {
            Personas persona = (Personas) item.getAttribute("data");
            if (persona != null) {
                setPersonas(persona);
            }
            txtPersonaCod.setValue(persona.getNoRegistroFiscal());
            txtPersonaName.setValue(persona.getNombre());
            // close the bandbox
            bandbox_OrderDialog_CustomerSearch.close();
        }
    }

    public Personas getPersonas() {
        return personas;
    }

    public void setPersonas(Personas personas) {
        this.personas = personas;
    }

    public void refreshModel(int activePage) {
        logger.log(Level.INFO, "[EncabezadoCompraCtrl ][refreshModel]Recargar articulos,Pagina activa:{0}", activePage);
        try {
            //if (totalMovimiento > 0) {
            listaDetalleMovimiento = movimientoBean.loadDetalleMovimientoByIdMovimento(compraSelected.getIdmov());
            if (listaDetalleMovimiento.size() > 0) {
                logger.log(Level.INFO, "Registros cargados=={0}", listaDetalleMovimiento.size());
                //      paging_ListBoxOrderOrderPositions2.setTotalSize(getTotalMovimiento());
                listBoxDetalleCompra.setModel(new ListModelList(listaDetalleMovimiento));
                listBoxDetalleCompra.setItemRenderer(new DetalleMovimientoItemRenderer());
            } else {
                logger.info("No se cargaron registros");
            }
//            } else {
//                listBoxDetalleCompra.setEmptyMessage("No se encontraron registros para mostrar");
//            }
        } catch (Exception ex) {
            logger.log(Level.INFO, "[EncabezadoCompraCtrl ][refreshModel] Ocurrio Una exception :{0}", ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    private void loadDataFromTextboxs() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
