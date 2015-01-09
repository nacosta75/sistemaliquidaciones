
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
import sv.com.diserv.liquidaciones.ejb.PersonasBeanLocal;
import sv.com.diserv.liquidaciones.entity.Personas;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.liquidaciones.util.UtilFormat;
import sv.com.diserv.web.ui.personas.rendered.PersonaItemRenderer;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

public class ListaAsignacionesCtrl extends BaseController {

    static final Logger logger = Logger.getLogger(ListaAsignacionesCtrl.class.getCanonicalName());
    protected Window listaClienteWindow;
    protected Button btnNuevoCliente;
    protected Button btnBusquedaCliente;
    protected Button btnRefresh;
    protected Paging pagingCliente;
    protected Listbox listBoxCliente;
    protected Listheader listheaderIdCliente;
    protected Listheader listheaderNombreCliente;
    protected Listheader listheaderTelefonoCliente;
    protected Listheader listheaderRegistroCliente;
    //contadores pagina
    private Integer totalClientes;
    private Integer numeroPaginInicio = 1;
    private ServiceLocator serviceLocator;
    //@EJB
    private PersonasBeanLocal personaBean;
    private List<Personas> listaClientes;
    private Personas clienteSelected;

    /**
     * default constructor.<br>
     */
    public ListaAsignacionesCtrl() {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            personaBean = serviceLocator.getService(Constants.JNDI_PERSONA_BEAN);
            numeroPaginInicio = 0;
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$listaClienteWindow(Event event) throws Exception {
        logger.log(Level.INFO, "[onCreate$listaClienteWindow]Event:{0}", event.toString());
        totalClientes = personaBean.countAllPersonas(1);
        MensajeMultilinea.doSetTemplate();


        if (totalClientes != null) {
            setTotalClientes(totalClientes);
        } else {
            logger.info("[onCreate$listaClienteWindow]No se pudo obtener total registros");
        }
        pagingCliente.setPageSize(getUserLogin().getRegistrosLista());
        pagingCliente.setDetailed(true);
        refreshModel(numeroPaginInicio);
        setOrderListHeaderClientes();
        
    }

    public void doRefreshModel(int activePage) {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl][doRefreshModel]Pagina activa:{0}", activePage);
        refreshModel(activePage);

    }

    public void refreshModel(int activePage) {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl][refreshModel]Recargar clientes,Pagina activa:{0}", activePage);
        try {
            if (totalClientes > 0) {
                listaClientes = personaBean.loadAllPersona(activePage * getUserLogin().getRegistrosLista(), getUserLogin().getRegistrosLista(),1);
                if (listaClientes.size() > 0) {
                    logger.log(Level.INFO, "Registros cargados=={0}", listaClientes.size());
                    pagingCliente.setTotalSize(getTotalClientes());
                    listBoxCliente.setModel(new ListModelList(listaClientes));
                    listBoxCliente.setItemRenderer(new PersonaItemRenderer());
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

    public void onDoubleClickedPersona(Event event) throws Exception {
        logger.log(Level.INFO, "[onDoubleClickedCliente]Event:{0}", event.toString());
        Listitem item = this.listBoxCliente.getSelectedItem();
        if (item != null) {
            Personas persona = (Personas) item.getAttribute("data");
            HashMap map = new HashMap();
            map.put("clienteSelected", persona);
            map.put("token", UtilFormat.getToken());
            map.put("listaClienteCtrl", this);
            Executions.createComponents("/WEB-INF/xhtml/persona/detalleCliente.zul", null, map);
        }
    }

    public void onClick$btnNuevoCliente(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnNuevoCliente]Event:{0}", event.toString());
        HashMap map = new HashMap();
        map.put("token", UtilFormat.getToken());
        map.put("listaClienteCtrl", this);
        Executions.createComponents("/WEB-INF/xhtml/persona/detalleCliente.zul", null, map);

    }

    public void onClick$btnRefresh(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnRefresh]Event:{0}", event.toString());

        refreshModel(0);

    }

    public void onClick$btnBusquedaCliente(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnBusquedaPersona]Event:{0}", event.toString());
        HashMap map = new HashMap();
        map.put("token", UtilFormat.getToken());
        map.put("listaClienteCtrl", this);
        Executions.createComponents("/WEB-INF/xhtml/persona/busquedaCliente.zul", null, map);

    }

    public void onClickedPersona(Event event) throws Exception {
        logger.log(Level.INFO, "[onClickedCliente]Event:{0}", event.toString());
        Listitem item = this.listBoxCliente.getSelectedItem();
        if (item != null) {
            clienteSelected = (Personas) item.getAttribute("data");
            if (clienteSelected != null) {
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
        listheaderIdCliente.setSortAscending(new FieldComparator("idpersona", true));
        listheaderIdCliente.setSortDescending(new FieldComparator("idpersona", false));
        listheaderNombreCliente.setSortAscending(new FieldComparator("nombre", true));
        listheaderNombreCliente.setSortDescending(new FieldComparator("nombre", false));
        listheaderTelefonoCliente.setSortAscending(new FieldComparator("telefono1", true));
        listheaderTelefonoCliente.setSortDescending(new FieldComparator("telefono1", false));
        listheaderRegistroCliente.setSortAscending(new FieldComparator("noRegistroFiscal", true));
        listheaderRegistroCliente.setSortDescending(new FieldComparator("noRegistroFiscal", false));
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

    public List<Personas> getListaPersonas() {
        return listaClientes;
    }

    public void setListaClientes(List<Personas> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public Personas getClienteSelected() {
        return clienteSelected;
    }

    public void setClienteSelected(Personas personaSelected) {
        this.clienteSelected = personaSelected;
    }

    public Listbox getListBoxCliente() {
        return listBoxCliente;
    }

    public void setListBoxCliente(Listbox listBoxCliente) {
        this.listBoxCliente = listBoxCliente;
    }
}
