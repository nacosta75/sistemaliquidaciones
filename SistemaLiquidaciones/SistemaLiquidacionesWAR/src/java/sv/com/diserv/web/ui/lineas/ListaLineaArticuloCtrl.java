/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.lineas;

import java.util.HashMap;
import java.util.List;
import sv.com.diserv.web.ui.util.BaseController;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.ejb.LineaArticuloBeanLocal;
import sv.com.diserv.liquidaciones.entity.LineaArticulo;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.liquidaciones.util.UtilFormat;
import sv.com.diserv.web.ui.lineas.lineas.rendered.LineaItemRenderer;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

/**
 *
 * @author abraham.acosta
 */
public class ListaLineaArticuloCtrl extends BaseController {
    
    static final Logger logger = Logger.getLogger(ListaLineaArticuloCtrl.class.getCanonicalName());
    protected Window listaLineaWindow;
    protected Button btnNuevaLinea;
    protected Button btnBusquedaLinea;
    protected Button btnRefresh;
    protected Paging pagingLinea;
    protected Listbox listBoxLinea;
    protected Listheader listheaderIdLinea;
    protected Listheader listheaderDescripcion;
    
     //contadores pagina
    private Integer totalLineas;
    private Integer numeroPaginInicio = 1;
    private ServiceLocator serviceLocator;
    private LineaArticuloBeanLocal lineaBean;
    private List<LineaArticulo> listaLineas;
    private LineaArticulo lineaSelected;

    public Integer getTotalLineas() {
        return totalLineas;
    }

    public void setTotalLineas(Integer totalLineas) {
        this.totalLineas = totalLineas;
    }

    public Integer getNumeroPaginInicio() {
        return numeroPaginInicio;
    }

    public void setNumeroPaginInicio(Integer numeroPaginInicio) {
        this.numeroPaginInicio = numeroPaginInicio;
    }

    public List<LineaArticulo> getListaLineas() {
        return listaLineas;
    }

    public void setListaLineas(List<LineaArticulo> listaLineas) {
        this.listaLineas = listaLineas;
    }

    public LineaArticulo getLineaSelected() {
        return lineaSelected;
    }

    public void setLineaSelected(LineaArticulo lineaSelected) {
        this.lineaSelected = lineaSelected;
    }
    
    public ListaLineaArticuloCtrl()
    {
      logger.log(Level.INFO, "[ListaLineaArticuloCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();           
            lineaBean = serviceLocator.getService(Constants.JNDI_LINEAS_BEAN);
            numeroPaginInicio = 0;
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    
    }
    
     public void onCreate$listaLineaWindow(Event event) throws Exception {
        logger.log(Level.INFO, "[onCreate$listaLineaWindow]Event:{0}", event.toString());
        totalLineas = lineaBean.countAllLineaArticulo();
        MensajeMultilinea.doSetTemplate();


        if (totalLineas != null) {
            setTotalLineas(totalLineas);
        } else {
            logger.info("[onCreate$listaLineaWindow]No se pudo obtener total registros");
        }
        pagingLinea.setPageSize(getUserLogin().getRegistrosLista());
        pagingLinea.setDetailed(true);
        refreshModel(numeroPaginInicio);
//        doCheckPermisos();
  
        
    }

    public void doRefreshModel(int activePage) {
        logger.log(Level.INFO, "[ListaLineaArticuloCtrl ][doRefreshModel]Pagina activa:{0}", activePage);
        refreshModel(activePage);

    }

    public void refreshModel(int activePage) {
        logger.log(Level.INFO, "[ListaLineaArticuloCtrl ][refreshModel]Recargar bodegas,Pagina activa:{0}", activePage);
        try {
            if (totalLineas > 0) {
                listaLineas = lineaBean.loadAllLineas(activePage * getUserLogin().getRegistrosLista(), getUserLogin().getRegistrosLista());
                if (listaLineas.size() > 0) {
                    logger.log(Level.INFO, "Registros cargados=={0}", listaLineas.size());
                    pagingLinea.setTotalSize(getTotalLineas());
                    listBoxLinea.setModel(new ListModelList(listaLineas));
                    listBoxLinea.setItemRenderer(new LineaItemRenderer());
                } else {
                    logger.info("No se cargaron registros");
                }
            } else {
                listBoxLinea.setEmptyMessage("No se encontraron registros para mostrar");
            }
        } catch (Exception ex) {
            logger.log(Level.INFO, "[ListaLineaArticuloCtrl ][refreshModel]Ocurrio Una exception :{0}", ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onDoubleClickedLinea(Event event) throws Exception {
        logger.log(Level.INFO, "[onDoubleClickedLinea]Event:{0}", event.toString());
        Listitem item = this.listBoxLinea.getSelectedItem();
        if (item != null) {
            LineaArticulo linea = (LineaArticulo) item.getAttribute("data");
            HashMap map = new HashMap();
            map.put("lineaSelected", linea);
            map.put("token", UtilFormat.getToken());
            map.put("ListaLineaArticuloCtrl", this);
            Executions.createComponents("/WEB-INF/xhtml/lineas/detalleLinea.zul", null, map);
        }
    }

    public void onClick$btnNuevoLinea(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnNuevaLinea]Event:{0}", event.toString());
        HashMap map = new HashMap();
        map.put("token", UtilFormat.getToken());
        map.put("listaLineaCtrl", this);
        Executions.createComponents("/WEB-INF/xhtml/lineas/detalleLinea.zul", null, map);

    }

    public void onClick$btnRefresh(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnRefresh]Event:{0}", event.toString());

        refreshModel(0);

    }

    public void onClick$btnBusquedaLinea(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnBusquedaLinea]Event:{0}", event.toString());
        HashMap map = new HashMap();
        map.put("token", UtilFormat.getToken());
        map.put("listaLineaCtrl", this);
        Executions.createComponents("/WEB-INF/xhtml/lineas/busquedaLinea.zul", null, map);

    }

    
    
    
}
