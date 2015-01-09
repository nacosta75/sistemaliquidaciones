
package sv.com.diserv.web.ui.asignaciones;

/**
 *
 * @author sonia.garcia
 */
import sv.com.diserv.web.ui.personas.*;
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
import sv.com.diserv.liquidaciones.ejb.MovimientosBeanLocal;
import sv.com.diserv.liquidaciones.entity.Movimientos;
import sv.com.diserv.liquidaciones.entity.Personas;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.liquidaciones.util.UtilFormat;
import sv.com.diserv.web.ui.asignaciones.render.AsignacionItemRenderer;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

public class ListaAsignacionesCtrl extends BaseController {

    static final Logger logger = Logger.getLogger(ListaAsignacionesCtrl.class.getCanonicalName());
    protected Window listaAsignacionWindow;
    protected Button btnNuevoAsignacion;
    protected Button btnBusquedaAsignacion;
    protected Button btnRefresh;
    protected Paging pagingAsignacion;
    protected Listbox listBoxAsignacion;
    protected Listheader listheaderIdAsignacion;
    protected Listheader listheaderFechaAsignacion;
    protected Listheader listheaderNoDocAsignacion;
    protected Listheader listheaderVendedorAsignacion;
    //contadores pagina
    private Integer totalAsignaciones;
    private Integer numeroPaginInicio = 1;
    private ServiceLocator serviceLocator;
    //@EJB
    private MovimientosBeanLocal movimientoBean;
    private List<Movimientos> listaAsignaciones;
    private Movimientos asignacionSelected;

    /**
     * default constructor.<br>
     */
    public ListaAsignacionesCtrl() {
        logger.log(Level.INFO, "[ListaAsignacionesCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            movimientoBean = serviceLocator.getService(Constants.JNDI_MOVIMIENTOS_BEAN);
            numeroPaginInicio = 0;
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$listaAsignacionWindow(Event event) throws Exception {
        logger.log(Level.INFO, "[onCreate$listaAsignacionWindow]Event:{0}", event.toString());
        totalAsignaciones = movimientoBean.countAllPersonas(1);
        MensajeMultilinea.doSetTemplate();


        if (totalAsignaciones != null) {
            setTotalClientes(totalAsignaciones);
        } else {
            logger.info("[onCreate$listaAsignacionWindow]No se pudo obtener total registros");
        }
        pagingAsignacion.setPageSize(getUserLogin().getRegistrosLista());
        pagingAsignacion.setDetailed(true);
        refreshModel(numeroPaginInicio);
        setOrderListHeaderClientes();
        
    }

    public void doRefreshModel(int activePage) {
        logger.log(Level.INFO, "[ListaAsignacionesCtrl][doRefreshModel]Pagina activa:{0}", activePage);
        refreshModel(activePage);

    }

    public void refreshModel(int activePage) {
        logger.log(Level.INFO, "[ListaAsignacionesCtrl][refreshModel]Recargar clientes,Pagina activa:{0}", activePage);
        try {
            if (totalAsignaciones > 0) {
                listaAsignaciones = movimientoBean.loadAllMovimientos(activePage * getUserLogin().getRegistrosLista(), getUserLogin().getRegistrosLista(),2);
                if (listaAsignaciones.size() > 0) {
                    logger.log(Level.INFO, "Registros cargados=={0}", listaAsignaciones.size());
                    pagingAsignacion.setTotalSize(getTotalClientes());
                    listBoxAsignacion.setModel(new ListModelList(listaAsignaciones));
                    listBoxAsignacion.setItemRenderer(new AsignacionItemRenderer());
                } else {
                    logger.info("No se cargaron registros");
                }
            } else {
                listBoxAsignacion.setEmptyMessage("No se encontraron registros para mostrar");
            }
        } catch (Exception ex) {
            logger.log(Level.INFO, "[ListaAsignacionesCtrl][refreshModel]Ocurrio Una exception :{0}", ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onDoubleClickedAsignacion(Event event) throws Exception {
        logger.log(Level.INFO, "[onDoubleClickedAsignacion]Event:{0}", event.toString());
        Listitem item = this.listBoxAsignacion.getSelectedItem();
        if (item != null) {
            Personas persona = (Personas) item.getAttribute("data");
            HashMap map = new HashMap();
            map.put("asignacionSelected", persona);
            map.put("token", UtilFormat.getToken());
            map.put("listaClienteCtrl", this);
            Executions.createComponents("/WEB-INF/xhtml/asignaciones/detalleAsignacion.zul", null, map);
        }
    }

    public void onClick$btnNuevoAsignacion(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnNuevoAsignacion]Event:{0}", event.toString());
        HashMap map = new HashMap();
        map.put("token", UtilFormat.getToken());
        map.put("listaClienteCtrl", this);
        Executions.createComponents("/WEB-INF/xhtml/asignaciones/detalleAsignacion.zul", null, map);

    }

    public void onClick$btnRefresh(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnRefresh]Event:{0}", event.toString());

        refreshModel(0);

    }

    public void onClick$btnBusquedaAsignacion(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnBusquedaPersona]Event:{0}", event.toString());
        HashMap map = new HashMap();
        map.put("token", UtilFormat.getToken());
        map.put("listaClienteCtrl", this);
        Executions.createComponents("/WEB-INF/xhtml/asignaciones/busquedaAsignacion.zul", null, map);

    }

    public void onClickedAsignacion(Event event) throws Exception {
        logger.log(Level.INFO, "[onClickedAsignacion]Event:{0}", event.toString());
        Listitem item = this.listBoxAsignacion.getSelectedItem();
        if (item != null) {
            asignacionSelected = (Movimientos) item.getAttribute("data");
            if (asignacionSelected != null) {
            }
        }
    }

    public void onDoubleClickedEvaluacionAuditoria(ForwardEvent event) {
        logger.log(Level.INFO, "[onDoubleClickedEvaluacionAuditoria]Event:{0}", event.toString());
    }

    public void onPaging$pagingAsignacion(ForwardEvent event) {
        logger.log(Level.INFO, "[onPaging$pagingAsignacion]Event:", event.getName());
        final PagingEvent pe = (PagingEvent) event.getOrigin();
        numeroPaginInicio = pe.getActivePage();
        refreshModel(numeroPaginInicio);
    }

    private void setOrderListHeaderClientes() {
        listheaderIdAsignacion.setSortAscending(new FieldComparator("idmov", true));
        listheaderIdAsignacion.setSortDescending(new FieldComparator("idmov", false));
        listheaderFechaAsignacion.setSortAscending(new FieldComparator("fechamov", true));
        listheaderFechaAsignacion.setSortDescending(new FieldComparator("fechamov", false));
        listheaderNoDocAsignacion.setSortAscending(new FieldComparator("nodoc", true));
        listheaderNoDocAsignacion.setSortDescending(new FieldComparator("nodoc", false));
        listheaderVendedorAsignacion.setSortAscending(new FieldComparator("nombre", true));
        listheaderVendedorAsignacion.setSortDescending(new FieldComparator("nombre", false));
    }

    public Integer getTotalClientes() {
        return totalAsignaciones;
    }

    public void setTotalClientes(Integer totalAsignaciones) {
        this.totalAsignaciones = totalAsignaciones;
    }

    public Integer getNumeroPaginInicio() {
        return numeroPaginInicio;
    }

    public void setNumeroPaginInicio(Integer numeroPaginInicio) {
        this.numeroPaginInicio = numeroPaginInicio;
    }

    public List<Movimientos> getListaAsignaciones() {
        return listaAsignaciones;
    }

    public void setListaAsignaciones(List<Movimientos> listaAsignaciones) {
        this.listaAsignaciones = listaAsignaciones;
    }

    public Movimientos getAsignacionSelected() {
        return asignacionSelected;
    }

    public void setAsignacionSelected(Movimientos asignacionSelected) {
        this.asignacionSelected = asignacionSelected;
    }

    public Listbox getListBoxCliente() {
        return listBoxAsignacion;
    }

    public void setListBoxCliente(Listbox listBoxAsignacion) {
        this.listBoxAsignacion = listBoxAsignacion;
    }
}
