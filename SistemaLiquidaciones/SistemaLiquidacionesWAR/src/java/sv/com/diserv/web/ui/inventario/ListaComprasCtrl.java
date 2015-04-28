/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE noe acosta  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * 
 * @author edwin alvarenga
 *  2013
 */
package sv.com.diserv.web.ui.inventario;

/**
 *
 * @author abraham.acosta
 */
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.jfree.text.TextBox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;
import sv.com.diserv.liquidaciones.dto.BusquedaMovimientoDTO;
import sv.com.diserv.liquidaciones.dto.BusquedaPersonaDTO;
import sv.com.diserv.liquidaciones.ejb.MovimientosBeanLocal;
import sv.com.diserv.liquidaciones.ejb.PersonasBeanLocal;
import sv.com.diserv.liquidaciones.entity.Movimientos;
import sv.com.diserv.liquidaciones.entity.Personas;
import sv.com.diserv.liquidaciones.entity.MovimientosDet;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.liquidaciones.util.TokenGenerator;
import sv.com.diserv.liquidaciones.util.UtilFormat;
import sv.com.diserv.web.ui.inventario.rendered.DetalleMovimientoItemRenderer;
import sv.com.diserv.web.ui.inventario.rendered.MovimientoItemRenderer;
import sv.com.diserv.web.ui.personas.rendered.PersonaItemRenderer;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;
import sv.com.diserv.web.ui.util.UserWorkspace;

public class ListaComprasCtrl extends BaseController {

    static final Logger logger = Logger.getLogger(ListaComprasCtrl.class.getCanonicalName());
    protected Window listaMovimientoWindow;

    protected Button btnHelp;
    protected Button btnRefresh;
    protected Button button_bbox_CustomerSearch_Search;
    protected Button button_bbox_CustomerSearch_Close;
    protected Button btnNuevoMovimiento;
    
    protected Button btnBusquedaMovimiento;
    protected Button btnImprimir;
    protected Paging paging_OrderList;
    
    //busqueda de proveedor
    protected Listbox listBoxCustomerSearch;
    protected Listheader listheader_CustNo;
    protected Listheader listheader_CustMatchcode;
    protected Listheader listheader_CustName1;
    protected Listheader listheader_CustCity;
    protected Listheader lhNoRegistro;
    
    
    protected Listbox listBoxOrder;
    protected Listheader lhIdMovimiento;
    protected Listheader lhProveedor;
    protected Listheader lhRegistro;
    protected Listheader lhObservaciones;
    protected Listheader lhFechaMovimiento;
    protected Listheader lhNumero;
    protected Listbox listBoxDetalleCompra;
    //contadores pagina
    private ServiceLocator serviceLocator;
    private Movimientos movimientoSelected;
    private MovimientosDet detalleMovimientoSelected;
    private MovimientosBeanLocal movimientoBean;
    private List<Movimientos> listaMovimiento;
    private Integer totalMovimiento;
    private Integer numeroPaginInicio;
    private List<MovimientosDet> listaDetalleMovimiento;
    private Integer tipoMovimientoSelected;
    private BusquedaPersonaDTO request;
    private BusquedaMovimientoDTO busquedaMovimientoDTO;
     
    
    // bandbox searchCustomer
    protected Bandbox bandbox_OrderList_CustomerSearch;
    private Object paging_OrderList_CustomerSearchList;
    private transient List<Personas> searchObjCustomer;
    private final int pageSizeSearchCustomers = 20;
    protected Intbox tb_Orders_SearchCustNo;
    protected Textbox tb_Orders_CustSearchMatchcode;
    protected Textbox tb_Orders_SearchCustName1;
    protected Button button_OrderList_OrderNameSearch;
    protected Button button_OrderList_NewOrder;
    
    private PersonasBeanLocal personaBean;
    private Personas personas;
    // dialog control
    protected Button button_OrderDialog_NewOrderPosition;


    /**
     * default constructor.<br>
     */
    public ListaComprasCtrl() {
        logger.log(Level.INFO, "[ListaOrdentrabajoRevisionCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            movimientoBean = serviceLocator.getService(Constants.JNDI_MOVIMIENTOS_BEAN);
            personaBean = serviceLocator.getService(Constants.JNDI_PERSONA_BEAN);
            numeroPaginInicio = 0;
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public BusquedaPersonaDTO getRequest() {
        return request;
    }

    public void setRequest(BusquedaPersonaDTO request) {
        this.request = request;
    }
    
    

    public void onCreate$listaMovimientoWindow(Event event) throws Exception {
        logger.log(Level.INFO, "[onCreate$listaMovimientoWindow]Event:{0}", event.toString());
        doOnCreateCommon(listaMovimientoWindow);
        MensajeMultilinea.doSetTemplate();
        paging_OrderList.setPageSize(getUserLogin().getRegistrosLista());
        paging_OrderList.setDetailed(true);
        doCheckPermisos();
        reloadTotal();
        refreshModel(numeroPaginInicio);
//        setOrderDataByListHeaderAuditoriaEvaluaciones();
//        setOrderDataByListHeaderAuditoriaIndicadores();
    }

    private void doCheckPermisos() {
        final UserWorkspace workspace = getUserWorkspace();
//        btnNuevaTeledespacho.setVisible(workspace.isAllowed("btnNuevaTeledespacho"));
    }

    public void reloadTotal() {
        try {
            totalMovimiento = movimientoBean.countAllMovimientos(Constants.CODIGO_MOVIMIENTO_TIPO_COMPRA);
            if (totalMovimiento != null) {
                setTotalMovimiento(totalMovimiento);
            } else {
                logger.info("[onCreate$listaClienteWindow]No se pudo obtener total registros");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doRefreshModel(int activePage) {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl][doRefreshModel]Pagina activa:{0}", activePage);
        refreshModel(activePage);
    }

    private void refreshModel(int activePage) {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl][refreshModel]Recargar clientes,Pagina activa:{0}", activePage);
        try {
            if (totalMovimiento > 0) {
                listaMovimiento = movimientoBean.loadAllMovimientos(activePage * getUserLogin().getRegistrosLista(), getUserLogin().getRegistrosLista(),Constants.CODIGO_MOVIMIENTO_TIPO_COMPRA );
                if (listaMovimiento.size() > 0) {
                    logger.log(Level.INFO, "Registros cargados=={0}", listaMovimiento.size());
                    paging_OrderList.setTotalSize(getTotalMovimiento());
                    listBoxOrder.setModel(new ListModelList(listaMovimiento));
                    listBoxOrder.setItemRenderer(new MovimientoItemRenderer());
                } else {
                    logger.info("No se encontraron registros con los parametros ingresados");
                }
            } else {
                listBoxOrder.setEmptyMessage("No se encontraron registros para mostrar");
            }
        } catch (Exception ex) {
            logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl][refreshModel]Ocurrio Una exception :{0}", ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onDoubleClickedDetalleMovimiento(Event event) throws Exception {
        logger.log(Level.INFO, "[onDoubleClickedDetalleMovimiento]Event:{0}", event.toString());
         Listitem item = this.listBoxCustomerSearch.getSelectedItem();
		if (item != null) {

			/* clear the listboxes from older stuff */
			if ((ListModelList) listBoxOrder.getModel() != null) {
				((ListModelList) listBoxOrder.getModel()).clear();
			}
			if ((ListModelList) listBoxDetalleCompra.getModel() != null) {
				((ListModelList) listBoxDetalleCompra.getModel()).clear();
			}

			Personas persona = (Personas) item.getAttribute("data");

			if (persona != null)
				setPersonas(persona);

			bandbox_OrderList_CustomerSearch.setValue(persona.getNombre() + ", " + persona.getIdpersona());
  
                        busquedaMovimientoDTO = new BusquedaMovimientoDTO();
                        busquedaMovimientoDTO.setIdpersona(persona.getIdpersona());
                        busquedaMovimientoDTO.setIdtipomov(Constants.CODIGO_MOVIMIENTO_TIPO_COMPRA);
                        
                        
			listaMovimiento = movimientoBean.buscarMovimientoByCriteria(busquedaMovimientoDTO);
                        
                if (listaMovimiento.size() > 0) {
                    logger.log(Level.INFO, "Registros cargados=={0}", listaMovimiento.size());
                    paging_OrderList.setTotalSize(getTotalMovimiento());
                    listBoxOrder.setModel(new ListModelList(listaMovimiento));
                    listBoxOrder.setItemRenderer(new MovimientoItemRenderer());
                } else {
                    logger.info("No se encontraron registros con los parametros ingresados");
                }
		}

		// close the bandbox
		bandbox_OrderList_CustomerSearch.close();

    //    Listitem item = this.listBoxOrder.getSelectedItem();
//        if (item != null) {
//            movimientoSelected = (Movimientos) item.getAttribute("data");
//            HashMap map = new HashMap();
//            map.put("movimientoSelected", movimientoSelected);
//            map.put("token", TokenGenerator.getTokenOperation());
//            map.put("listaComprasCtrl", this);
//
//            Executions.createComponents("/WEB-INF/xhtml/inventario/encabezadoCompra.zul", null, map);
//        }
    }
    
     public void onDoubleClickedCompra(Event event) throws Exception {
        logger.log(Level.INFO, "[**onDoubleClickedCompra]Event:{0}", event.toString());
        Listitem item = this.listBoxOrder.getSelectedItem();
        if (item != null) {
            Movimientos compra = (Movimientos) item.getAttribute("data");
            HashMap map = new HashMap();
            map.put("compraSelected", compra);
            map.put("token", UtilFormat.getToken());
            map.put("ListaComprasCtrl", this);
            Executions.createComponents("/WEB-INF/xhtml/inventario/encabezadoCompra.zul", null, map);
        }
    }

    public void onClickedMovimiento(Event event) throws Exception {
        logger.log(Level.INFO, "[onClickedMovimiento]Event:{0}", event.toString());
        Listitem item = this.listBoxOrder.getSelectedItem();
        if (item != null) {  
            movimientoSelected = (Movimientos) item.getAttribute("data");
//            System.out.println("tramites:" + documentoSelected.getIdfactura());
            if (movimientoSelected != null) {
                listaDetalleMovimiento = movimientoBean.loadDetalleMovimientoByIdMovimento(movimientoSelected.getIdmov());
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
    }

    public void onClick$button_OrderList_NewOrder(Event event) {
        logger.log(Level.INFO, "[onClick$btnNuevoDocumento]Event:{0}", event.toString());
        try {
            HashMap map = new HashMap();
            map.put("token", UtilFormat.getToken());
            map.put("listaComprasCtrl", this);
            Executions.createComponents("/WEB-INF/xhtml/inventario/encabezadoCompra.zul", null, map);
        } catch (Exception a) {
            a.printStackTrace();
        }
    }

    public void onClick$button_OrderDialog_NewOrderPosition(Event event)
    {
         logger.log(Level.INFO, "[onclick$button_OrderDialog_NewOrderPosition]Event:{0}", event.toString());
        try {
            HashMap map = new HashMap();
            map.put("token", UtilFormat.getToken());
            map.put("listaComprasCtrl", this);
            Executions.createComponents("/WEB-INF/xhtml/inventario/orderPositionDialog.zul", null, map);
        } catch (Exception a) {
            a.printStackTrace();
        }
        
    }
    
    
    public void onClick$btnRefresh(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnRefresh]Event:{0}", event.toString());

        reloadTotal();
        refreshModel(0);
    }

    public void onClick$btnBusquedaDocumento(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnBusquedaDocumento]Event:{0}", event.toString());
        HashMap map = new HashMap();
        map.put("token", UtilFormat.getToken());
        map.put("listaDocumentosCtrl", this);
        Executions.createComponents("/WEB-INF/xhtml/facturacion/busquedaDocumentoCliente.zul", null, map);
    }

    public void onPaging$pagingDocumentoCliente(ForwardEvent event) {
        logger.log(Level.INFO, "[onPaging$pagingDocumentoCliente]Event:", event.getName());
        final PagingEvent pe = (PagingEvent) event.getOrigin();
        numeroPaginInicio = pe.getActivePage();
        refreshModel(numeroPaginInicio);
    }

    public void onClick$btnImprimir(Event event) throws InterruptedException {
//        logger.info("[onClick$btnImprimir]");
//        try {
//            Component parent = getRoot();
//            listaDocumentoImpresion = facturaBean.findDocumentoClienteByRequest(request, null, null);
//            if (listaDocumentoImpresion != null) {
//                listaFacturaImpresion = Assemble.loadFromEntity(listaDocumentoImpresion);
//                ManejadorReporte.generarReporteDocumento(listaFacturaImpresion, request, parent, Constants.TITULO_REPORTE_FACTURAS_FECHA, Constants.RUTA_REPORTE_FACTURAS_FECHA, "ReporteFacturaFechas");
//            }
//        } catch (Exception e) {
//            logger.log(Level.INFO, "[onClick$btnImprimirReporte]Exception:{0}", e.toString());
//            e.printStackTrace();
//        }
    }

    public Movimientos getMovimientoSelected() {
        return movimientoSelected;
    }

    public void setMovimientoSelected(Movimientos movimientoSelected) {
        this.movimientoSelected = movimientoSelected;
    }

    public List<Movimientos> getListaMovimiento() {
        return listaMovimiento;
    }

    public void setListaMovimiento(List<Movimientos> listaMovimiento) {
        this.listaMovimiento = listaMovimiento;
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
    
    public void onClick$button_bbox_CustomerSearch_Close(Event event) {
         bandbox_OrderList_CustomerSearch.close();
    }

    public BusquedaMovimientoDTO getBusquedaMovimientoDTO() {
        return busquedaMovimientoDTO;
    }

    public void setBusquedaMovimientoDTO(BusquedaMovimientoDTO busquedaMovimientoDTO) {
        this.busquedaMovimientoDTO = busquedaMovimientoDTO;
    }
    
    


    public void onOpen$bandbox_OrderList_CustomerSearch(Event event) throws Exception {
		// logger.debug(event.toString());

		// not used listheaders must be declared like ->
        // lh.setSortAscending(""); lh.setSortDescending("")
        listheader_CustNo.setSortAscending(new FieldComparator("kunNr", true));
        listheader_CustNo.setSortDescending(new FieldComparator("kunNr", false));
        listheader_CustMatchcode.setSortAscending(new FieldComparator("kunMatchcode", true));
        listheader_CustMatchcode.setSortDescending(new FieldComparator("kunMatchcode", false));
        listheader_CustName1.setSortAscending(new FieldComparator("kunName1", true));
        listheader_CustName1.setSortDescending(new FieldComparator("kunName1", false));
		//listheader_CustCity.setSortAscending(new FieldComparator("kunOrt", true));
        //listheader_CustCity.setSortDescending(new FieldComparator("kunOrt", false));

		// set the paging params
        //paging_OrderList_CustomerSearchList.setPageSize(pageSizeSearchCustomers);
        //paging_OrderList_CustomerSearchList.setDetailed(true);
        // ++ create the searchObject and init sorting ++ //
//		if (getSearchObjCustomer() == null) {
//			setSearchObjCustomer(pageSizeSearchCustomers);
//			getSearchObjCustomer().addSort("kunMatchcode", false);
//			setSearchObjCustomer(searchObjCustomer);
//		}
//
//		// Set the ListModel.
//		//getPlwCustomers().init(getSearchObjCustomer(), listBoxCustomerSearch, paging_OrderList_CustomerSearchList);
//		// set the itemRenderer
//		listBoxCustomerSearch.setItemRenderer(new OrderSearchCustomerListModelItemRenderer());
        BuscarProveedor();
    }

    public void onClick$button_bbox_CustomerSearch_Search(Event event) {
               BuscarProveedor();
    }

    
    private void BuscarProveedor() {
        logger.log(Level.INFO, "[BuscarProveedor][refreshModel]Recargar clientes");
        try {
            request = new BusquedaPersonaDTO();
            
            if (StringUtils.isNotEmpty(tb_Orders_SearchCustNo.getText())) {
                request.setIdPersona(tb_Orders_SearchCustNo.getValue());
            }
            if (StringUtils.isNotEmpty(tb_Orders_SearchCustName1.getValue())) {
                request.setNombre(tb_Orders_SearchCustName1.getValue().toUpperCase());
            }
//            if (StringUtils.isNotEmpty(txtNumeroNit.getValue())) {
//                request.setNit(txtNumeroNit.getValue());
//            }
            if (StringUtils.isNotEmpty(tb_Orders_CustSearchMatchcode.getValue())) {
                request.setNumeroRegistro(tb_Orders_CustSearchMatchcode.getValue());
            }
            
            request.setTipoPersona(3);
            searchObjCustomer = personaBean.buscarPersonaByCriteria(request);

            if (!searchObjCustomer.isEmpty()) {
                //setSearchObjCustomer(searchObjCustomer);
                //paging_OrderList.setTotalSize(getTotalMovimiento());
                listBoxCustomerSearch.setModel(new ListModelList(searchObjCustomer));
                listBoxCustomerSearch.setItemRenderer(new PersonaItemRenderer());
                //setSearchObjCustomer(searchObjCustomer);
                //listaProveedorCtrl.setTotalProveedores(listaClientes.size());
                //listaProveedorCtrl.getListBoxProveedor().setModel(new ListModelList(listaClientes));
                // doClose();
            } else {
                //this.getListaProveedores().setEmptyMessage("No se encontraron registros con los criterios ingresados!!");
                //listaProveedorCtrl.getListBoxProveedor().setEmptyMessage("No se encontraron registros con los criterios ingresados!!");
                listBoxCustomerSearch.setEmptyMessage("No se encontraron registros con los criterios ingresados!!");
                MensajeMultilinea.show("No se encontraron proveedores con los criterios ingresados", Constants.MENSAJE_TIPO_ALERTA);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.toString(), Constants.MENSAJE_TIPO_ERROR);
        }
    }

    public List<Personas> getSearchObjCustomer() {
        return searchObjCustomer;
    }

    public void setSearchObjCustomer(List<Personas> searchObjCustomer) {
        this.searchObjCustomer = searchObjCustomer;
    }
    
     public void onClick$btnCerrar(Event event) throws InterruptedException {
        doClose();
    }

    private void doClose() {
        this.listaMovimientoWindow.onClose();
    }

    public Personas getPersonas() {
        return personas;
    }

    public void setPersonas(Personas personas) {
        this.personas = personas;
    }
    
    
    
    public void onDoubleClickedPersona(Event event) throws DiservBusinessException {
		// logger.debug(event.toString());

		// get the customer
		Listitem item = this.listBoxCustomerSearch.getSelectedItem();
		if (item != null) {

			/* clear the listboxes from older stuff */
			if ((ListModelList) listBoxOrder.getModel() != null) {
				((ListModelList) listBoxOrder.getModel()).clear();
			}
			if ((ListModelList) listBoxDetalleCompra.getModel() != null) {
				((ListModelList) listBoxDetalleCompra.getModel()).clear();
			}

			Personas persona = (Personas) item.getAttribute("data");

			if (persona != null)
				setPersonas(persona);

			bandbox_OrderList_CustomerSearch.setValue(persona.getNombre() + ", " + persona.getIdpersona());
  
                        busquedaMovimientoDTO = new BusquedaMovimientoDTO();
                        busquedaMovimientoDTO.setIdpersona(persona.getIdpersona());
                        busquedaMovimientoDTO.setIdtipomov(Constants.CODIGO_MOVIMIENTO_TIPO_COMPRA);
                        
                        
			listaMovimiento = movimientoBean.buscarMovimientoByCriteria(busquedaMovimientoDTO);
                        
                if (listaMovimiento.size() > 0) {
                    logger.log(Level.INFO, "Registros cargados=={0}", listaMovimiento.size());
                    paging_OrderList.setTotalSize(getTotalMovimiento());
                    listBoxOrder.setModel(new ListModelList(listaMovimiento));
                    listBoxOrder.setItemRenderer(new MovimientoItemRenderer());
                } else {
                    logger.info("No se encontraron registros con los parametros ingresados");
                }
		}

		// close the bandbox
		bandbox_OrderList_CustomerSearch.close();

	}

}
