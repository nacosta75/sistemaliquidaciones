/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.web.ui.sucursales;

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
import sv.com.diserv.liquidaciones.ejb.SucursalesBeanLocal;
import sv.com.diserv.liquidaciones.entity.Sucursales;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.liquidaciones.util.UtilFormat;
import sv.com.diserv.web.ui.sucursales.rendered.SucursalItemRenderer;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

/**
 *
 * @author abraham.acosta
 */
public class ListaSucursalCtrl extends BaseController{
    
    static final Logger logger = Logger.getLogger(ListaSucursalCtrl.class.getCanonicalName());
    
    protected Window listaSucursalesWindow;
    protected Button btnNuevoSucursal;
    protected Button btnBusquedaSucursal;
    protected Button btnRefresh;
    protected Paging pagingSucursal;
    protected Listbox listBoxSucursal;
    protected Listheader listheaderIdSucursal;
    protected Listheader listheaderSucDescripcion;
    protected Listheader listheaderSucDireccion;
    protected Listheader listheaderSucTelefono;
    protected Listheader listheaderSucEncargado;    
   
    //contadores pagina
    private Integer totalSucursales;
    private Integer numeroPaginInicio = 1;
    private ServiceLocator serviceLocator;
    private SucursalesBeanLocal sucursalBean;
    private List<Sucursales> listaSucursales;
    private Sucursales sucursalSelected;

    
    public ListaSucursalCtrl()
    {
    
        logger.log(Level.INFO, "[ListaSucursalCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            sucursalBean = serviceLocator.getService(Constants.JNDI_SUCURSAL_BEAN);
            numeroPaginInicio = 0;
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    
    }

    public Listbox getListBoxSucursal() {
        return listBoxSucursal;
    }

    public void setListBoxSucursal(Listbox listBoxSucursal) {
        this.listBoxSucursal = listBoxSucursal;
    }

    public Integer getTotalSucursales() {
        return totalSucursales;
    }

    public void setTotalSucursales(Integer totalSucursales) {
        this.totalSucursales = totalSucursales;
    }

    public Integer getNumeroPaginInicio() {
        return numeroPaginInicio;
    }

    public void setNumeroPaginInicio(Integer numeroPaginInicio) {
        this.numeroPaginInicio = numeroPaginInicio;
    }

    public List<Sucursales> getListaSucursales() {
        return listaSucursales;
    }

    public void setListaSucursales(List<Sucursales> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }

    public Sucursales getSucursalSelected() {
        return sucursalSelected;
    }

    public void setSucursalSelected(Sucursales sucursalSelected) {
        this.sucursalSelected = sucursalSelected;
    }
    
    
    
     public void onCreate$listaSucursalesWindow(Event event) throws Exception {
        logger.log(Level.INFO, "[onCreate$listaSucursalWindow]Event:{0}", event.toString());
        totalSucursales = sucursalBean.countAllSucursal();
        MensajeMultilinea.doSetTemplate();


        if (totalSucursales != null) {
            setTotalSucursales(totalSucursales);
        } else {
            logger.info("[onCreate$listaSucursalesWindow]No se pudo obtener total registros");
        }
        pagingSucursal.setPageSize(getUserLogin().getRegistrosLista());
        pagingSucursal.setDetailed(true);
        refreshModel(numeroPaginInicio); 
       //        doCheckPermisos();
       // setOrderListHeaderSucursales();
       // setOrderListHeaderOrdentrabajo();
    }

    public void doRefreshModel(int activePage) {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl][doRefreshModel]Pagina activa:{0}", activePage);
        refreshModel(activePage);

    }

    public void refreshModel(int activePage) {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl][refreshModel]Recargar sucursals,Pagina activa:{0}", activePage);
        try {
            if (totalSucursales > 0) {
                listaSucursales = sucursalBean.loadAllSucursal(activePage * getUserLogin().getRegistrosLista(), getUserLogin().getRegistrosLista());
                if (listaSucursales.size() > 0) {
                    logger.log(Level.INFO, "Registros cargados=={0}", listaSucursales.size());
                    pagingSucursal.setTotalSize(getTotalSucursales());
                    listBoxSucursal.setModel(new ListModelList(listaSucursales));
                    listBoxSucursal.setItemRenderer(new SucursalItemRenderer());
                } else {
                    logger.info("No se cargaron registros");
                }
            } else {
                listBoxSucursal.setEmptyMessage("No se encontraron registros para mostrar");
            }
        } catch (Exception ex) {
            logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl][refreshModel]Ocurrio Una exception :{0}", ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onDoubleClickedSucursal(Event event) throws Exception {
        logger.log(Level.INFO, "[onDoubleClickedSucursal]Event:{0}", event.toString());
        Listitem item = this.listBoxSucursal.getSelectedItem();
        if (item != null) {
            Sucursales sucursal = (Sucursales) item.getAttribute("data");
            HashMap map = new HashMap();
            map.put("sucursalSelected", sucursal);
            map.put("token", UtilFormat.getToken());
            map.put("listaSucursalCtrl", this);
            Executions.createComponents("/WEB-INF/xhtml/sucursal/detalleSucursal.zul", null, map);
        }
    }

    public void onClick$btnNuevoSucursal(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnNuevoSucursal]Event:{0}", event.toString());
        HashMap map = new HashMap();
        map.put("token", UtilFormat.getToken());
        map.put("listaSucursalCtrl", this);
        Executions.createComponents("/WEB-INF/xhtml/sucursal/detalleSucursal.zul", null, map);

    }

    public void onClick$btnRefresh(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnRefresh]Event:{0}", event.toString());

        refreshModel(0);

    }

    public void onClick$btnBusquedaSucursal(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnBusquedaSucursal]Event:{0}", event.toString());
        HashMap map = new HashMap();
        map.put("token", UtilFormat.getToken());
        map.put("listaSucursalCtrl", this);
        Executions.createComponents("/WEB-INF/xhtml/sucursal/busquedaSucursal.zul", null, map);

    }

    public void onClickedSucursal(Event event) throws Exception {
        logger.log(Level.INFO, "[onClickedSucursal]Event:{0}", event.toString());
        Listitem item = this.listBoxSucursal.getSelectedItem();
        if (item != null) {
            sucursalSelected = (Sucursales) item.getAttribute("data");
           // System.out.println("tramites:" + sucursalSelected.getOrdentrabajoList().size());
            if (sucursalSelected != null) {
              //  listaOrdenesSucursal = ordentrabajoBean.loadOrdenesTrabajoBySucursal(sucursalSelected.getIdSucursal(), Constants.PAGINA_INICIO_CERO, Constants.REGISTROS_A_MOSTRAR_LISTA);
//                if (listaOrdenesSucursal.size() > 0) {
//                    listBoxListaTramiteSucursal.setModel(new ListModelList(listaOrdenesSucursal));
//                    listBoxListaTramiteSucursal.setItemRenderer(new OrdentrabajoResumenItemRenderer());
//                } else {
//                    listBoxListaTramiteSucursal.setModel(new ListModelList(listaOrdenesSucursal));
//                    listBoxListaTramiteSucursal.setEmptyMessage("Sucursal no Tiene Tramites Asociados en Sistema");
//                    logger.info("No se cargaron registros");
//                }
            }
        }
    }

    public void onDoubleClickedEvaluacionAuditoria(ForwardEvent event) {
        logger.log(Level.INFO, "[onDoubleClickedEvaluacionAuditoria]Event:{0}", event.toString());
    }

    public void onPaging$pagingSucursal(ForwardEvent event) {
        logger.log(Level.INFO, "[onPaging$pagingSucursal]Event:", event.getName());
        final PagingEvent pe = (PagingEvent) event.getOrigin();
        numeroPaginInicio = pe.getActivePage();
        refreshModel(numeroPaginInicio);
    }

    private void setOrderListHeaderSucursales() {
        listheaderIdSucursal.setSortAscending(new FieldComparator("idsucursal", true));
        listheaderIdSucursal.setSortDescending(new FieldComparator("idsucursal", false));
        listheaderSucDescripcion.setSortAscending(new FieldComparator("Descripcion", true));
        listheaderSucDescripcion.setSortDescending(new FieldComparator("Descripcion", false));
        listheaderSucEncargado.setSortAscending(new FieldComparator("encargado", true));
        listheaderSucEncargado.setSortDescending(new FieldComparator("encargado", false));
//        listheaderSucursalDireccion.setSortAscending(new FieldComparator("encargado", true));
//        listheaderSucursalDireccion.setSortDescending(new FieldComparator("encargado", false));
    }

    
    
    
}
