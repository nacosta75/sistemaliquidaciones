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
 * @author edwin.alvarenga
 */
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;
import sv.com.diserv.liquidaciones.ejb.MovimientosBeanLocal;
import sv.com.diserv.liquidaciones.entity.Movimientos;
import sv.com.diserv.liquidaciones.entity.MovimientosDet;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.liquidaciones.util.TokenGenerator;
import sv.com.diserv.liquidaciones.util.UtilFormat;
import sv.com.diserv.web.ui.inventario.rendered.DetalleMovimientoItemRenderer;
import sv.com.diserv.web.ui.inventario.rendered.MovimientoItemRenderer;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;
import sv.com.diserv.web.ui.util.UserWorkspace;

public class ListaComprasCtrl extends BaseController {

    static final Logger logger = Logger.getLogger(ListaComprasCtrl.class.getCanonicalName());
    protected Window listaMovimientoWindow;
    protected Button btnNuevoMovimiento;
    protected Button btnRefresh;
    protected Button btnBusquedaMovimiento;
    protected Button btnImprimir;
    protected Paging pagingMovimientosCompra;
    protected Listbox listBoxMovimientos;
    protected Listheader lhIdMovimiento;
    protected Listheader lhIdProveedor;
    protected Listheader lhProveedor;
    protected Listheader lhRegistro;
    protected Listheader lhEstado;
    protected Listheader lhObservacioens;
    protected Listheader lhFechaMovimiento;
    protected Listbox listBoxDetalleMovimiento;
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

    /**
     * default constructor.<br>
     */
    public ListaComprasCtrl() {
        logger.log(Level.INFO, "[ListaOrdentrabajoRevisionCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            movimientoBean = serviceLocator.getService(Constants.JNDI_MOVIMIENTOS_BEAN);
            numeroPaginInicio = 0;
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$listaMovimientoWindow(Event event) throws Exception {
        logger.log(Level.INFO, "[onCreate$listaMovimientoWindow]Event:{0}", event.toString());
        doOnCreateCommon(listaMovimientoWindow);
        MensajeMultilinea.doSetTemplate();
        pagingMovimientosCompra.setPageSize(getUserLogin().getRegistrosLista());
        pagingMovimientosCompra.setDetailed(true);
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
            totalMovimiento = movimientoBean.countAllMovimientos(tipoMovimientoSelected);
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
                listaMovimiento = movimientoBean.loadAllMovimientos(activePage * getUserLogin().getRegistrosLista(), getUserLogin().getRegistrosLista(), tipoMovimientoSelected);
                if (listaMovimiento.size() > 0) {
                    logger.log(Level.INFO, "Registros cargados=={0}", listaMovimiento.size());
                    pagingMovimientosCompra.setTotalSize(getTotalMovimiento());
                    listBoxMovimientos.setModel(new ListModelList(listaMovimiento));
                    listBoxMovimientos.setItemRenderer(new MovimientoItemRenderer());
                } else {
                    logger.info("No se encontraron registros con los parametros ingresados");
                }
            } else {
                listBoxMovimientos.setEmptyMessage("No se encontraron registros para mostrar");
            }
        } catch (Exception ex) {
            logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl][refreshModel]Ocurrio Una exception :{0}", ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onDoubleClickedDetalleMovimiento(Event event) throws Exception {
        logger.log(Level.INFO, "[onDoubleClickedDetalleMovimiento]Event:{0}", event.toString());
        Listitem item = this.listBoxDetalleMovimiento.getSelectedItem();
        if (item != null) {
            detalleMovimientoSelected = (MovimientosDet) item.getAttribute("data");
            HashMap map = new HashMap();
            map.put("movimientoSelected", detalleMovimientoSelected);
            map.put("token", TokenGenerator.getTokenOperation());
            map.put("listaFacturaCtrl", this);

            Executions.createComponents("/WEB-INF/xhtml/facturacion/encabezadoFactura.zul", null, map);
        }
    }

    public void onClickedMovimiento(Event event) throws Exception {
        logger.log(Level.INFO, "[onClickedMovimiento]Event:{0}", event.toString());
        Listitem item = this.listBoxMovimientos.getSelectedItem();
        if (item != null) {
            movimientoSelected = (Movimientos) item.getAttribute("data");
//            System.out.println("tramites:" + documentoSelected.getIdfactura());
            if (movimientoSelected != null) {
                listaDetalleMovimiento = movimientoBean.loadDetalleMovimientoByIdMovimento(movimientoSelected.getIdmov());
                if (listaDetalleMovimiento.size() > 0) {
                    listBoxDetalleMovimiento.setModel(new ListModelList(listaDetalleMovimiento));
                    listBoxDetalleMovimiento.setItemRenderer(new DetalleMovimientoItemRenderer());
                } else {
                    logger.info("No se cargaron registros");
                    listBoxDetalleMovimiento.setModel(new ListModelList(listaDetalleMovimiento));
                    listBoxDetalleMovimiento.setEmptyMessage("Factura no tiene items agregados");
                }
            }
        }
    }

    public void onClick$btnNuevoDocumento(Event event) {
        logger.log(Level.INFO, "[onClick$btnNuevoDocumento]Event:{0}", event.toString());
        try {
            HashMap map = new HashMap();
            map.put("token", UtilFormat.getToken());
            map.put("listaFacturaCtrl", this);
            Executions.createComponents("/WEB-INF/xhtml/facturacion/encabezadoFactura.zul", null, map);
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

}
