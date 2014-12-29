
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

public class ListaProveedorCtrl extends BaseController {

    static final Logger logger = Logger.getLogger(ListaProveedorCtrl.class.getCanonicalName());
    protected Window listaProveedorWindow;
    protected Button btnNuevoProveedor;
    protected Button btnBusquedaProveedor;
    protected Button btnRefresh;
    protected Paging pagingProveedor;
    protected Listbox listBoxProveedor;
    protected Listheader listheaderIdProveedor;
    protected Listheader listheaderNombreProveedor;
    protected Listheader listheaderTelefonoProveedor;
    protected Listheader listheaderRegistroProveedor;
    //contadores pagina
    private Integer totalProveedores;
    private Integer numeroPaginInicio = 1;
    private ServiceLocator serviceLocator;
    //@EJB
    private PersonasBeanLocal personaBean;
    private List<Personas> listaProveedores;
    private Personas proveedorSelected;

    /**
     * default constructor.<br>
     */
    public ListaProveedorCtrl() {
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

    public void onCreate$listaProveedorWindow(Event event) throws Exception {
        logger.log(Level.INFO, "[onCreate$listaProveedorWindow]Event:{0}", event.toString());
        totalProveedores = personaBean.countAllPersonas(3);
        MensajeMultilinea.doSetTemplate();


        if (totalProveedores != null) {
            setTotalProveedores(totalProveedores);
        } else {
            logger.info("[onCreate$listaProveedorWindow]No se pudo obtener total registros");
        }
        pagingProveedor.setPageSize(getUserLogin().getRegistrosLista());
        pagingProveedor.setDetailed(true);
        refreshModel(numeroPaginInicio);
        setOrderListHeaderProveedores();
    }

    public void doRefreshModel(int activePage) {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl][doRefreshModel]Pagina activa:{0}", activePage);
        refreshModel(activePage);

    }

    public void refreshModel(int activePage) {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl][refreshModel]Recargar personas,Pagina activa:{0}", activePage);
        try {
            if (totalProveedores > 0) {
                listaProveedores = personaBean.loadAllPersona(activePage * getUserLogin().getRegistrosLista(), getUserLogin().getRegistrosLista(),3);
                if (listaProveedores.size() > 0) {
                    logger.log(Level.INFO, "Registros cargados=={0}", listaProveedores.size());
                    pagingProveedor.setTotalSize(getTotalProveedores());
                    listBoxProveedor.setModel(new ListModelList(listaProveedores));
                    listBoxProveedor.setItemRenderer(new PersonaItemRenderer());
                } else {
                    logger.info("No se cargaron registros");
                }
            } else {
                listBoxProveedor.setEmptyMessage("No se encontraron registros para mostrar");
            }
        } catch (Exception ex) {
            logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl][refreshModel]Ocurrio Una exception :{0}", ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onDoubleClickedPersona(Event event) throws Exception {
        logger.log(Level.INFO, "[onDoubleClickedProveedor]Event:{0}", event.toString());
        Listitem item = this.listBoxProveedor.getSelectedItem();
        if (item != null) {
            Personas persona = (Personas) item.getAttribute("data");
            HashMap map = new HashMap();
            map.put("clienteSelected", persona);
            map.put("token", UtilFormat.getToken());
            map.put("listaProveedorCtrl", this);
            Executions.createComponents("/WEB-INF/xhtml/persona/detalleProveedor.zul", null, map);
        }
    }

    public void onClick$btnNuevoProveedor(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnNuevoProveedor]Event:{0}", event.toString());
        HashMap map = new HashMap();
        map.put("token", UtilFormat.getToken());
        map.put("listaProveedorCtrl", this);
        Executions.createComponents("/WEB-INF/xhtml/persona/detalleProveedor.zul", null, map);

    }

    public void onClick$btnRefresh(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnRefresh]Event:{0}", event.toString());

        refreshModel(0);

    }

    public void onClick$btnBusquedaProveedor(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnBusquedaPersona]Event:{0}", event.toString());
        HashMap map = new HashMap();
        map.put("token", UtilFormat.getToken());
        map.put("listaProveedorCtrl", this);
        Executions.createComponents("/WEB-INF/xhtml/persona/busquedaProveedor.zul", null, map);

    }

    public void onClickedPersona(Event event) throws Exception {
        logger.log(Level.INFO, "[onClickedProveedor]Event:{0}", event.toString());
        Listitem item = this.listBoxProveedor.getSelectedItem();
        if (item != null) {
            proveedorSelected = (Personas) item.getAttribute("data");
           // System.out.println("tramites:" + personaSelected.getOrdentrabajoList().size());
            if (proveedorSelected != null) {
              //  listaOrdenesBodega = ordentrabajoBean.loadOrdenesTrabajoByBodega(personaSelected.getIdBodega(), Constants.PAGINA_INICIO_CERO, Constants.REGISTROS_A_MOSTRAR_LISTA);
//                if (listaOrdenesBodega.size() > 0) {
//                    listBoxListaTramiteBodega.setModel(new ListModelList(listaOrdenesBodega));
//                    listBoxListaTramiteBodega.setItemRenderer(new OrdentrabajoResumenItemRenderer());
//                } else {
//                    listBoxListaTramiteBodega.setModel(new ListModelList(listaOrdenesBodega));
//                    listBoxListaTramiteBodega.setEmptyMessage("Bodega no Tiene Tramites Asociados en Sistema");
//                    logger.info("No se cargaron registros");
//                }
            }
        }
    }

    public void onDoubleClickedEvaluacionAuditoria(ForwardEvent event) {
        logger.log(Level.INFO, "[onDoubleClickedEvaluacionAuditoria]Event:{0}", event.toString());
    }

    public void onPaging$pagingProveedor(ForwardEvent event) {
        logger.log(Level.INFO, "[onPaging$pagingProveedor]Event:", event.getName());
        final PagingEvent pe = (PagingEvent) event.getOrigin();
        numeroPaginInicio = pe.getActivePage();
        refreshModel(numeroPaginInicio);
    }

    private void setOrderListHeaderProveedores() {
        listheaderIdProveedor.setSortAscending(new FieldComparator("idpersona", true));
        listheaderIdProveedor.setSortDescending(new FieldComparator("idpersona", false));
        listheaderNombreProveedor.setSortAscending(new FieldComparator("nombre", true));
        listheaderNombreProveedor.setSortDescending(new FieldComparator("nombre", false));
        listheaderTelefonoProveedor.setSortAscending(new FieldComparator("telefono1", true));
        listheaderTelefonoProveedor.setSortDescending(new FieldComparator("telefono1", false));
        listheaderRegistroProveedor.setSortAscending(new FieldComparator("noRegistroFiscal", true));
        listheaderRegistroProveedor.setSortDescending(new FieldComparator("noRegistroFiscal", false));
    }


    public Integer getTotalProveedores() {
        return totalProveedores;
    }

    public void setTotalProveedores(Integer totalProveedores) {
        this.totalProveedores = totalProveedores;
    }

    public Integer getNumeroPaginInicio() {
        return numeroPaginInicio;
    }

    public void setNumeroPaginInicio(Integer numeroPaginInicio) {
        this.numeroPaginInicio = numeroPaginInicio;
    }

    public List<Personas> getListaProveedores() {
        return listaProveedores;
    }

    public void setListaProveedores(List<Personas> listaProveedores) {
        this.listaProveedores = listaProveedores;
    }

    public Personas getProveedorSelected() {
        return proveedorSelected;
    }

    public void setProveedorSelected(Personas proveedorSelected) {
        this.proveedorSelected = proveedorSelected;
    }

    public Listbox getListProveedor() {
        return listBoxProveedor;
    }

    public void setListBoxProveedor(Listbox listBoxProveedor) {
        this.listBoxProveedor = listBoxProveedor;
    }
}
