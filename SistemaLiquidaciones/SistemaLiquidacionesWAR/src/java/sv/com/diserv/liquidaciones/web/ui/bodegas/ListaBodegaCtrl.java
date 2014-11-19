/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE FISCALIA GENERAL DE LA REPUBLICA  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author edwin.alvarenga@fgr.gob.sv
 * @author edwin alvarenga
 * Fiscalía General de la República 2013
 */
package sv.com.diserv.liquidaciones.web.ui.bodegas;

/**
 *
 * @author edwin.alvarenga
 */
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;
import sv.com.diserv.liquidaciones.web.ui.usuarios.util.BaseController;

public class ListaBodegaCtrl extends BaseController {

    static final Logger logger = Logger.getLogger(ListaBodegaCtrl.class.getCanonicalName());
    protected Window listaClienteWindow;
    protected Button btnNuevoCliente;
    protected Button btnBusquedaCliente;
    protected Button btnRefresh;
    protected Paging pagingCliente;
    protected Listbox listBoxCliente;
    protected Listheader listheaderIdCliente;
    protected Listheader listheaderNombreCliente;
    protected Listheader listheaderClienteRegistroIva;
    protected Listheader listheaderClienteNumeroNit;
    protected Listbox listBoxListaTramiteCliente;
    protected Listheader listheaderIdOrdentrabajo;
    protected Listheader listheaderFechaOrdentrabajo;
    protected Listheader listheaderEstadoTramite;
    protected Listheader listheaderAduanaTramite;
    //contadores pagina
    private Integer totalClientes;
    private Integer numeroPaginInicio = 1;
    private ServiceLocator serviceLocator;
    private ClienteBeanLocal clienteBean;
    private List<Clientes> listaClientes;
    private Clientes clienteSelected;
    private OrdenTrabajoBeanLocal ordentrabajoBean;
    private List<Ordentrabajo> listaOrdenesCliente;

    /**
     * default constructor.<br>
     */
    public ListaBodegaCtrl() {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            ordentrabajoBean = serviceLocator.getService(Constants.JNDI_ORDENTRABAJO_BEAN);
            clienteBean = serviceLocator.getService(Constants.JNDI_CLIENTE_BEAN);
            numeroPaginInicio = 0;
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$listaClienteWindow(Event event) throws Exception {
        logger.log(Level.INFO, "[onCreate$listaClienteWindow]Event:{0}", event.toString());
        totalClientes = clienteBean.countAllCliente();
        MensajeMultilinea.doSetTemplate();


        if (totalClientes != null) {
            setTotalClientes(totalClientes);
        } else {
            logger.info("[onCreate$listaClienteWindow]No se pudo obtener total registros");
        }
        pagingCliente.setPageSize(getUserLogin().getRegistrosLista());
        pagingCliente.setDetailed(true);
        refreshModel(numeroPaginInicio);
//        doCheckPermisos();
        setOrderListHeaderClientes();
        setOrderListHeaderOrdentrabajo();
    }

    public void doRefreshModel(int activePage) {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl][doRefreshModel]Pagina activa:{0}", activePage);
        refreshModel(activePage);

    }

    public void refreshModel(int activePage) {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl][refreshModel]Recargar clientes,Pagina activa:{0}", activePage);
        try {
            if (totalClientes > 0) {
                listaClientes = clienteBean.loadAllCliente(activePage * getUserLogin().getRegistrosLista(), getUserLogin().getRegistrosLista());
                if (listaClientes.size() > 0) {
                    logger.log(Level.INFO, "Registros cargados=={0}", listaClientes.size());
                    pagingCliente.setTotalSize(getTotalClientes());
                    listBoxCliente.setModel(new ListModelList(listaClientes));
                    listBoxCliente.setItemRenderer(new ClienteItemRenderer());
                } else {
                    logger.info("No se cargaron registros");
                }
            } else {
                listBoxCliente.setEmptyMessage("No se encontraron registros para mostrar");
            }
        } catch (Exception ex) {
            logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl][refreshModel]Ocurrio Una exception :{0}", ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onDoubleClickedCliente(Event event) throws Exception {
        logger.log(Level.INFO, "[onDoubleClickedCliente]Event:{0}", event.toString());
        Listitem item = this.listBoxCliente.getSelectedItem();
        if (item != null) {
            Clientes cliente = (Clientes) item.getAttribute("data");
            HashMap map = new HashMap();
            map.put("clienteSelected", cliente);
            map.put("token", UtilFormat.getToken());
            map.put("listaClienteCtrl", this);
            Executions.createComponents("/WEB-INF/xhtml/cliente/detalleCliente.zul", null, map);
        }
    }

    public void onClick$btnNuevoCliente(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnNuevoCliente]Event:{0}", event.toString());
        HashMap map = new HashMap();
        map.put("token", UtilFormat.getToken());
        map.put("listaClienteCtrl", this);
        Executions.createComponents("/WEB-INF/xhtml/cliente/detalleCliente.zul", null, map);

    }

    public void onClick$btnRefresh(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnRefresh]Event:{0}", event.toString());

        refreshModel(0);

    }

    public void onClick$btnBusquedaCliente(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnBusquedaCliente]Event:{0}", event.toString());
        HashMap map = new HashMap();
        map.put("token", UtilFormat.getToken());
        map.put("listaClienteCtrl", this);
        Executions.createComponents("/WEB-INF/xhtml/cliente/busquedaCliente.zul", null, map);

    }

    public void onClickedCliente(Event event) throws Exception {
        logger.log(Level.INFO, "[onClickedCliente]Event:{0}", event.toString());
        Listitem item = this.listBoxCliente.getSelectedItem();
        if (item != null) {
            clienteSelected = (Clientes) item.getAttribute("data");
            System.out.println("tramites:" + clienteSelected.getOrdentrabajoList().size());
            if (clienteSelected != null) {
                listaOrdenesCliente = ordentrabajoBean.loadOrdenesTrabajoByCliente(clienteSelected.getIdCliente(), Constants.PAGINA_INICIO_CERO, Constants.REGISTROS_A_MOSTRAR_LISTA);
                if (listaOrdenesCliente.size() > 0) {
                    listBoxListaTramiteCliente.setModel(new ListModelList(listaOrdenesCliente));
                    listBoxListaTramiteCliente.setItemRenderer(new OrdentrabajoResumenItemRenderer());
                } else {
                    listBoxListaTramiteCliente.setModel(new ListModelList(listaOrdenesCliente));
                    listBoxListaTramiteCliente.setEmptyMessage("Cliente no Tiene Tramites Asociados en Sistema");
                    logger.info("No se cargaron registros");
                }
            }
        }
    }

    public void onDoubleClickedEvaluacionAuditoria(ForwardEvent event) {
        logger.log(Level.INFO, "[onDoubleClickedEvaluacionAuditoria]Event:{0}", event.toString());
    }

    public void onPaging$pagingCliente(ForwardEvent event) {
        logger.log(Level.INFO, "[onPaging$pagingCliente]Event:", event.getName());
        final PagingEvent pe = (PagingEvent) event.getOrigin();
        numeroPaginInicio = pe.getActivePage();
        refreshModel(numeroPaginInicio);
    }

    private void setOrderListHeaderClientes() {
        listheaderIdCliente.setSortAscending(new FieldComparator("idCliente", true));
        listheaderIdCliente.setSortDescending(new FieldComparator("idCliente", false));
        listheaderNombreCliente.setSortAscending(new FieldComparator("nombreCliente", true));
        listheaderNombreCliente.setSortDescending(new FieldComparator("nombreCliente", false));
        listheaderClienteRegistroIva.setSortAscending(new FieldComparator("ivaCliente", true));
        listheaderClienteRegistroIva.setSortDescending(new FieldComparator("ivaCliente", false));
        listheaderClienteNumeroNit.setSortAscending(new FieldComparator("nitCliente", true));
        listheaderClienteNumeroNit.setSortDescending(new FieldComparator("nitCliente", false));
    }

    private void setOrderListHeaderOrdentrabajo() {
        listheaderIdOrdentrabajo.setSortAscending(new FieldComparator("idOrdenTrabajo", true));
        listheaderIdOrdentrabajo.setSortDescending(new FieldComparator("idOrdenTrabajo", false));
        listheaderFechaOrdentrabajo.setSortAscending(new FieldComparator("fechaIngreso", true));
        listheaderFechaOrdentrabajo.setSortDescending(new FieldComparator("fechaIngreso", false));
        listheaderEstadoTramite.setSortAscending(new FieldComparator("idEstado.descripcionEstado", true));
        listheaderEstadoTramite.setSortDescending(new FieldComparator("idEstado.descripcionEstado", false));
        listheaderAduanaTramite.setSortAscending(new FieldComparator("aduana", true));
        listheaderAduanaTramite.setSortDescending(new FieldComparator("aduana", false));

    }

    public Integer getTotalClientes() {
        return totalClientes;
    }

    public void setTotalClientes(Integer totalClientes) {
        this.totalClientes = totalClientes;
    }

    public Integer getNumeroPaginInicio() {
        return numeroPaginInicio;
    }

    public void setNumeroPaginInicio(Integer numeroPaginInicio) {
        this.numeroPaginInicio = numeroPaginInicio;
    }

    public List<Clientes> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Clientes> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public Clientes getClienteSelected() {
        return clienteSelected;
    }

    public void setClienteSelected(Clientes clienteSelected) {
        this.clienteSelected = clienteSelected;
    }

    public List<Ordentrabajo> getListaOrdenesCliente() {
        return listaOrdenesCliente;
    }

    public void setListaOrdenesCliente(List<Ordentrabajo> listaOrdenesCliente) {
        this.listaOrdenesCliente = listaOrdenesCliente;
    }

    public Listbox getListBoxCliente() {
        return listBoxCliente;
    }

    public void setListBoxCliente(Listbox listBoxCliente) {
        this.listBoxCliente = listBoxCliente;
    }
}
