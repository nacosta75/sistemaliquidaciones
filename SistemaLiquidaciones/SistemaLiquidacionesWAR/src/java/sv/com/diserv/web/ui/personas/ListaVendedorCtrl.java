
package sv.com.diserv.web.ui.personas;

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

public class ListaVendedorCtrl extends BaseController {

    static final Logger logger = Logger.getLogger(ListaVendedorCtrl.class.getCanonicalName());
    protected Window listaVendedorWindow;
    protected Button btnNuevoVendedor;
    protected Button btnBusquedaVendedor;
    protected Button btnRefresh;
    protected Paging pagingVendedor;
    protected Listbox listBoxVendedor;
    protected Listheader listheaderIdVendedor;
    protected Listheader listheaderNombreVendedor;
    protected Listheader listheaderTelefono;
    protected Listheader listheaderPrueba;
    
    //contadores pagina
    private Integer totalVendedores;
    private Integer numeroPaginInicio = 1;
    private ServiceLocator serviceLocator;
    //@EJB
    private PersonasBeanLocal personaBean;
    private List<Personas> listaVendedores;
    private Personas clienteSelected;
 

    /**
     * default constructor.<br>
     */
    public ListaVendedorCtrl() {
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

    public void onCreate$listaVendedorWindow(Event event) throws Exception {
        logger.log(Level.INFO, "[onCreate$listaVendedorWindow]Event:{0}", event.toString());
        totalVendedores = personaBean.countAllPersonas(2);
        MensajeMultilinea.doSetTemplate();


        if (totalVendedores != null) {
            setTotalVendedores(totalVendedores);
        } else {
            logger.info("[onCreate$listaVendedorWindow]No se pudo obtener total registros");
        }
        pagingVendedor.setPageSize(getUserLogin().getRegistrosLista());
        pagingVendedor.setDetailed(true);
        refreshModel(numeroPaginInicio);
        setOrderListHeaderVendedores();
    }

    public void doRefreshModel(int activePage) {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl][doRefreshModel]Pagina activa:{0}", activePage);
        refreshModel(activePage);

    }

    public void refreshModel(int activePage) {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl][refreshModel]Recargar clientes,Pagina activa:{0}", activePage);
        try {
            if (totalVendedores > 0) {
                listaVendedores = personaBean.loadAllPersona(activePage * getUserLogin().getRegistrosLista(), getUserLogin().getRegistrosLista(),2);
                if (listaVendedores.size() > 0) {
                    logger.log(Level.INFO, "Registros cargados=={0}", listaVendedores.size());
                    pagingVendedor.setTotalSize(getTotalVendedores());
                    listBoxVendedor.setModel(new ListModelList(listaVendedores));
                    listBoxVendedor.setItemRenderer(new PersonaItemRenderer());
                } else {
                    logger.info("No se cargaron registros");
                }
            } else {
                listBoxVendedor.setEmptyMessage("No se encontraron registros para mostrar");
            }
        } catch (Exception ex) {
            logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl][refreshModel]Ocurrio Una exception :{0}", ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onDoubleClickedPersona(Event event) throws Exception {
        logger.log(Level.INFO, "[onDoubleClickedVendedor]Event:{0}", event.toString());
        Listitem item = this.listBoxVendedor.getSelectedItem();
        if (item != null) {
            Personas persona = (Personas) item.getAttribute("data");
            HashMap map = new HashMap();
            map.put("clienteSelected", persona);
            map.put("token", UtilFormat.getToken());
            map.put("listaVendedorCtrl", this);
            Executions.createComponents("/WEB-INF/xhtml/persona/detalleVendedor.zul", null, map);
        }
    }

    public void onClick$btnNuevoVendedor(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnNuevoVendedor]Event:{0}", event.toString());
        HashMap map = new HashMap();
        map.put("token", UtilFormat.getToken());
        map.put("listaVendedorCtrl", this);
        Executions.createComponents("/WEB-INF/xhtml/persona/detalleVendedor.zul", null, map);

    }

    public void onClick$btnRefresh(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnRefresh]Event:{0}", event.toString());

        refreshModel(0);

    }

    public void onClick$btnBusquedaVendedor(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnBusquedaPersona]Event:{0}", event.toString());
        HashMap map = new HashMap();
        map.put("token", UtilFormat.getToken());
        map.put("listaVendedorCtrl", this);
        Executions.createComponents("/WEB-INF/xhtml/persona/busquedaVendedor.zul", null, map);

    }

    public void onClickedPersona(Event event) throws Exception {
        logger.log(Level.INFO, "[onClickedVendedor]Event:{0}", event.toString());
        Listitem item = this.listBoxVendedor.getSelectedItem();
    }

    public void onDoubleClickedEvaluacionAuditoria(ForwardEvent event) {
        logger.log(Level.INFO, "[onDoubleClickedEvaluacionAuditoria]Event:{0}", event.toString());
    }

    public void onPaging$pagingVendedor(ForwardEvent event) {
        logger.log(Level.INFO, "[onPaging$pagingVendedor]Event:", event.getName());
        final PagingEvent pe = (PagingEvent) event.getOrigin();
        numeroPaginInicio = pe.getActivePage();
        refreshModel(numeroPaginInicio);
    }

    private void setOrderListHeaderVendedores() {
        listheaderIdVendedor.setSortAscending(new FieldComparator("idpersona", true));
        listheaderIdVendedor.setSortDescending(new FieldComparator("idpersona", false));
        listheaderNombreVendedor.setSortAscending(new FieldComparator("nombre", true));
        listheaderNombreVendedor.setSortDescending(new FieldComparator("nombre", false));
        listheaderPrueba.setSortAscending(new FieldComparator("telefono1", true));
        listheaderPrueba.setSortDescending(new FieldComparator("telefono1", false));
    }


    public Integer getTotalVendedores() {
        return totalVendedores;
    }

    public void setTotalVendedores(Integer totalVendedores) {
        this.totalVendedores = totalVendedores;
    }

    public Integer getNumeroPaginInicio() {
        return numeroPaginInicio;
    }

    public void setNumeroPaginInicio(Integer numeroPaginInicio) {
        this.numeroPaginInicio = numeroPaginInicio;
    }

    public List<Personas> getListaPersonas() {
        return listaVendedores;
    }

    public void setListaVendedores(List<Personas> listaVendedores) {
        this.listaVendedores = listaVendedores;
    }

    public Personas getClienteSelected() {
        return clienteSelected;
    }

    public void setClienteSelected(Personas personaSelected) {
        this.clienteSelected = personaSelected;
    }

    public Listbox getListBoxVendedor() {
        return listBoxVendedor;
    }

    public void setListBoxVendedor(Listbox listBoxVendedor) {
        this.listBoxVendedor = listBoxVendedor;
    }
}
