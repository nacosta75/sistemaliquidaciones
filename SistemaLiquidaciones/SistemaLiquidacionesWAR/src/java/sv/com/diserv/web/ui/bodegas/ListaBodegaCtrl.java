/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE FISCALIA GENERAL DE LA REPUBLICA  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author edwin.alvarenga@fgr.gob.sv
 * @author edwin alvarenga
 * Fiscalía General de la República 2013
 */
package sv.com.diserv.web.ui.bodegas;

/**
 *
 * @author edwin.alvarenga
 */
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
import sv.com.diserv.liquidaciones.ejb.BodegasBeanLocal;
import sv.com.diserv.liquidaciones.entity.Bodegas;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.liquidaciones.util.UtilFormat;
import sv.com.diserv.web.ui.bodegas.rendered.BodegaItemRenderer;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

public class ListaBodegaCtrl extends BaseController {

    static final Logger logger = Logger.getLogger(ListaBodegaCtrl.class.getCanonicalName());
    protected Window listaBodegaWindow;
    protected Button btnNuevoBodega;
    protected Button btnBusquedaBodega;
    protected Button btnRefresh;
    protected Paging pagingBodega;
    protected Listbox listBoxBodega;
    protected Listheader listheaderIdBodega;
    protected Listheader listheaderNombreBodega;
    protected Listheader listheaderBodegaTelefono;
    protected Listheader listheaderBodegaNumeroNit;
    protected Listbox listBoxListaTramiteBodega;
    protected Listheader listheaderIdOrdentrabajo;
    protected Listheader listheaderFechaOrdentrabajo;
    protected Listheader listheaderEstadoTramite;
    protected Listheader listheaderAduanaTramite;
    //contadores pagina
    private Integer totalBodegas;
    private Integer numeroPaginInicio = 1;
    private ServiceLocator serviceLocator;
    //@EJB
    private BodegasBeanLocal bodegaBean;
    private List<Bodegas> listaBodegas;
    private Bodegas bodegaSelected;
    //private OrdenTrabajoBeanLocal ordentrabajoBean;
    //private List<Ordentrabajo> listaOrdenesBodega;

    /**
     * default constructor.<br>
     */
    public ListaBodegaCtrl() {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            //ordentrabajoBean = serviceLocator.getService(Constants.JNDI_ORDENTRABAJO_BEAN);
            bodegaBean = serviceLocator.getService(Constants.JNDI_BODEGA_BEAN);
            numeroPaginInicio = 0;
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$listaBodegaWindow(Event event) throws Exception {
        logger.log(Level.INFO, "[onCreate$listaBodegaWindow]Event:{0}", event.toString());
        totalBodegas = bodegaBean.countAllBodegas();
        MensajeMultilinea.doSetTemplate();


        if (totalBodegas != null) {
            setTotalBodegas(totalBodegas);
        } else {
            logger.info("[onCreate$listaBodegaWindow]No se pudo obtener total registros");
        }
        pagingBodega.setPageSize(getUserLogin().getRegistrosLista());
        pagingBodega.setDetailed(true);
        refreshModel(numeroPaginInicio);
//        doCheckPermisos();
        setOrderListHeaderBodegas();
        setOrderListHeaderOrdentrabajo();
    }

    public void doRefreshModel(int activePage) {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl][doRefreshModel]Pagina activa:{0}", activePage);
        refreshModel(activePage);

    }

    public void refreshModel(int activePage) {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl][refreshModel]Recargar bodegas,Pagina activa:{0}", activePage);
        try {
            if (totalBodegas > 0) {
                listaBodegas = bodegaBean.loadAllBodega(activePage * getUserLogin().getRegistrosLista(), getUserLogin().getRegistrosLista());
                if (listaBodegas.size() > 0) {
                    logger.log(Level.INFO, "Registros cargados=={0}", listaBodegas.size());
                    pagingBodega.setTotalSize(getTotalBodegas());
                    listBoxBodega.setModel(new ListModelList(listaBodegas));
                    listBoxBodega.setItemRenderer(new BodegaItemRenderer());
                } else {
                    logger.info("No se cargaron registros");
                }
            } else {
                listBoxBodega.setEmptyMessage("No se encontraron registros para mostrar");
            }
        } catch (Exception ex) {
            logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl][refreshModel]Ocurrio Una exception :{0}", ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onDoubleClickedBodega(Event event) throws Exception {
        logger.log(Level.INFO, "[onDoubleClickedBodega]Event:{0}", event.toString());
        Listitem item = this.listBoxBodega.getSelectedItem();
        if (item != null) {
            Bodegas bodega = (Bodegas) item.getAttribute("data");
            HashMap map = new HashMap();
            map.put("bodegaSelected", bodega);
            map.put("token", UtilFormat.getToken());
            map.put("listaBodegaCtrl", this);
            Executions.createComponents("/WEB-INF/xhtml/bodega/detalleBodega.zul", null, map);
        }
    }

    public void onClick$btnNuevoBodega(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnNuevoBodega]Event:{0}", event.toString());
        HashMap map = new HashMap();
        map.put("token", UtilFormat.getToken());
        map.put("listaBodegaCtrl", this);
        Executions.createComponents("/WEB-INF/xhtml/bodega/detalleBodega.zul", null, map);

    }

    public void onClick$btnRefresh(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnRefresh]Event:{0}", event.toString());

        refreshModel(0);

    }

    public void onClick$btnBusquedaBodega(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnBusquedaBodega]Event:{0}", event.toString());
        HashMap map = new HashMap();
        map.put("token", UtilFormat.getToken());
        map.put("listaBodegaCtrl", this);
        Executions.createComponents("/WEB-INF/xhtml/bodega/busquedaBodega.zul", null, map);

    }

    public void onClickedBodega(Event event) throws Exception {
        logger.log(Level.INFO, "[onClickedBodega]Event:{0}", event.toString());
        Listitem item = this.listBoxBodega.getSelectedItem();
        if (item != null) {
            bodegaSelected = (Bodegas) item.getAttribute("data");
           // System.out.println("tramites:" + bodegaSelected.getOrdentrabajoList().size());
            if (bodegaSelected != null) {
              //  listaOrdenesBodega = ordentrabajoBean.loadOrdenesTrabajoByBodega(bodegaSelected.getIdBodega(), Constants.PAGINA_INICIO_CERO, Constants.REGISTROS_A_MOSTRAR_LISTA);
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

    public void onPaging$pagingBodega(ForwardEvent event) {
        logger.log(Level.INFO, "[onPaging$pagingBodega]Event:", event.getName());
        final PagingEvent pe = (PagingEvent) event.getOrigin();
        numeroPaginInicio = pe.getActivePage();
        refreshModel(numeroPaginInicio);
    }

    private void setOrderListHeaderBodegas() {
        listheaderIdBodega.setSortAscending(new FieldComparator("idBodega", true));
        listheaderIdBodega.setSortDescending(new FieldComparator("idBodega", false));
        listheaderNombreBodega.setSortAscending(new FieldComparator("nombreBodega", true));
        listheaderNombreBodega.setSortDescending(new FieldComparator("nombreBodega", false));
        listheaderBodegaTelefono.setSortAscending(new FieldComparator("telefono1", true));
        listheaderBodegaTelefono.setSortDescending(new FieldComparator("telefono1", false));
        listheaderBodegaNumeroNit.setSortAscending(new FieldComparator("telefono2", true));
        listheaderBodegaNumeroNit.setSortDescending(new FieldComparator("telefono2", false));
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

    public Integer getTotalBodegas() {
        return totalBodegas;
    }

    public void setTotalBodegas(Integer totalBodegas) {
        this.totalBodegas = totalBodegas;
    }

    public Integer getNumeroPaginInicio() {
        return numeroPaginInicio;
    }

    public void setNumeroPaginInicio(Integer numeroPaginInicio) {
        this.numeroPaginInicio = numeroPaginInicio;
    }

    public List<Bodegas> getListaBodegas() {
        return listaBodegas;
    }

    public void setListaBodegas(List<Bodegas> listaBodegas) {
        this.listaBodegas = listaBodegas;
    }

    public Bodegas getBodegaSelected() {
        return bodegaSelected;
    }

    public void setBodegaSelected(Bodegas bodegaSelected) {
        this.bodegaSelected = bodegaSelected;
    }

//    public List<Ordentrabajo> getListaOrdenesBodega() {
//        return listaOrdenesBodega;
//    }
//
//    public void setListaOrdenesBodega(List<Ordentrabajo> listaOrdenesBodega) {
//        this.listaOrdenesBodega = listaOrdenesBodega;
//    }

    public Listbox getListBoxBodega() {
        return listBoxBodega;
    }

    public void setListBoxBodega(Listbox listBoxBodega) {
        this.listBoxBodega = listBoxBodega;
    }
}
