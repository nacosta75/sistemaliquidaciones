/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.inventario;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import sv.com.diserv.liquidaciones.ejb.MovimientosBeanLocal;
import sv.com.diserv.liquidaciones.entity.Movimientos;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.util.UtilFormat;
import sv.com.diserv.web.ui.asignaciones.render.AsignacionItemRenderer;

/**
 *
 * @author abraham.acosta
 */
public class ListaDevolucionesCtrl extends BaseController{
    
    static final Logger logger = Logger.getLogger(ListaDevolucionesCtrl.class.getCanonicalName());
     
    protected Window listaDevolucionesWindow;
    protected Button btnNuevoDev;
    protected Button btnBusquedaDev;
    protected Button btnRefresh;
    protected Paging pagingDev;
    protected Listbox listBoxDev;
    protected Listheader listheaderIdDev;
    protected Listheader listheaderFechaDev;
    protected Listheader listheaderNoDocDev;
    protected Listheader listheaderVendedorDev;
    //contadores pagina
    private Integer totalDev;
    private Integer numeroPaginInicio = 1;
    private ServiceLocator serviceLocator;
    
     //@EJB
    private MovimientosBeanLocal movimientoBean;
    private List<Movimientos> listaDevoluciones;
    private Movimientos devolucionSelected;
    
      public ListaDevolucionesCtrl() {
        logger.log(Level.INFO, "[ListaDevolucionesCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            movimientoBean = serviceLocator.getService(Constants.JNDI_MOVIMIENTOS_BEAN);
            numeroPaginInicio = 0;
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public Integer getTotalDev() {
        return totalDev;
    }

    public void setTotalDev(Integer totalDev) {
        this.totalDev = totalDev;
    }

    public Integer getNumeroPaginInicio() {
        return numeroPaginInicio;
    }

    public void setNumeroPaginInicio(Integer numeroPaginInicio) {
        this.numeroPaginInicio = numeroPaginInicio;
    }

    public List<Movimientos> getListaDevoluciones() {
        return listaDevoluciones;
    }

    public void setListaDevoluciones(List<Movimientos> listaDevoluciones) {
        this.listaDevoluciones = listaDevoluciones;
    }

    public Movimientos getDevolucionSelected() {
        return devolucionSelected;
    }

    public void setDevolucionSelected(Movimientos devolucionSelected) {
        this.devolucionSelected = devolucionSelected;
    }

    public Listbox getListBoxDev() {
        return listBoxDev;
    }

    public void setListBoxDev(Listbox listBoxDev) {
        this.listBoxDev = listBoxDev;
    }

    
     public void onCreate$listaDevolucionesWindow(Event event) throws Exception {
        logger.log(Level.INFO, "[onCreate$listaDevolucionesWindow]Event:{0}", event.toString());
        totalDev = movimientoBean.countAllMovimientos(3);
        MensajeMultilinea.doSetTemplate();


        if (totalDev != null) {
            setTotalDev(totalDev);
        } else {
            logger.info("[onCreate$listaDevolucionesWindow]No se pudo obtener total registros");
        } 
     
        pagingDev.setPageSize(getUserLogin().getRegistrosLista());
        pagingDev.setDetailed(true);
        refreshModel(numeroPaginInicio);
        setOrderListHeaderDevs();
        
    }
     
      private void setOrderListHeaderDevs() {
        listheaderIdDev.setSortAscending(new FieldComparator("idmov", true));
        listheaderIdDev.setSortDescending(new FieldComparator("idmov", false));
        listheaderFechaDev.setSortAscending(new FieldComparator("fechamov", true));
        listheaderFechaDev.setSortDescending(new FieldComparator("fechamov", false));
        listheaderNoDocDev.setSortAscending(new FieldComparator("nodoc", true));
        listheaderNoDocDev.setSortDescending(new FieldComparator("nodoc", false));
        listheaderVendedorDev.setSortAscending(new FieldComparator("nombre", true));
        listheaderVendedorDev.setSortDescending(new FieldComparator("nombre", false));
    }
    
      public void refreshModel(int activePage) {
        logger.log(Level.INFO, "[ListaDevolucionesCtrl][refreshModel]Recargar clientes,Pagina activa:{0}", activePage);
        try {
            if (totalDev > 0) {
                listaDevoluciones = movimientoBean.loadAllMovimientos(activePage * getUserLogin().getRegistrosLista(), getUserLogin().getRegistrosLista(),3);
                if (listaDevoluciones.size() > 0) {
                    logger.log(Level.INFO, "Registros cargados=={0}", listaDevoluciones.size());
                    pagingDev.setTotalSize(getTotalDev());
                    listBoxDev.setModel(new ListModelList(listaDevoluciones));
                    listBoxDev.setItemRenderer(new AsignacionItemRenderer());
                } else {
                    logger.info("No se cargaron registros");
                }
            } else {
                listBoxDev.setEmptyMessage("No se encontraron registros para mostrar");
            }
        } catch (Exception ex) {
            logger.log(Level.INFO, "[ListaDevolucionesCtrl][refreshModel]Ocurrio Una exception :{0}", ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

      public void onDoubleClickedDevolucion(Event event) throws Exception {
        logger.log(Level.INFO, "[onDoubleClickedDevolucion]Event:{0}", event.toString());
        Listitem item = this.listBoxDev.getSelectedItem();
        if (item != null) {
            Movimientos movimiento = (Movimientos) item.getAttribute("data");
            HashMap map = new HashMap();
            map.put("devolucionSelected", movimiento);
            map.put("token", UtilFormat.getToken());
            map.put("listaAsignacionCtrl", this);
            Executions.createComponents("/WEB-INF/xhtml/devoluciones/detalleDevolucion.zul", null, map);
        }
    }
    
      public void onClick$btnNuevoDev(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnNuevoDev]Event:{0}", event.toString());
        HashMap map = new HashMap();
        map.put("token", UtilFormat.getToken());
        map.put("listaDevolucionCtrl", this);
        Executions.createComponents("/WEB-INF/xhtml/devoluciones/detalleDevolucion.zul", null, map);

    }

    public void onClick$btnRefresh(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnRefresh]Event:{0}", event.toString());

        refreshModel(0);

    }
}
