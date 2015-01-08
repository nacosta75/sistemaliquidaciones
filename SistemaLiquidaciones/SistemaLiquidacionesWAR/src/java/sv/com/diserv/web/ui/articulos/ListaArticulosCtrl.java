/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.articulos;

import java.util.HashMap;
import java.util.List;
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
import sv.com.diserv.liquidaciones.ejb.ArticulosBeanLocal;
import sv.com.diserv.liquidaciones.entity.Articulos;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.liquidaciones.util.UtilFormat;
import sv.com.diserv.web.ui.articulos.rendered.ArticuloItemRenderer;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;


/**
 *
 * @author abraham,.acosta
 */
public class ListaArticulosCtrl extends BaseController{
    
    
    static final Logger logger = Logger.getLogger(ListaArticulosCtrl.class.getCanonicalName());
    protected Window listaArticuloWindow;
    protected Button btnNuevoArticulo;
    protected Button btnBusquedaArticulo;
    protected Button btnRefresh;
    protected Paging pagingArticulo;
    protected Listbox listBoxArticulo;
    protected Listheader listheaderIdArticulo;
    protected Listheader listheaderDescripcion;
    
     //contadores pagina
    private Integer totalArticulos;
    private Integer numeroPaginInicio = 1;
    private ServiceLocator serviceLocator;
    private ArticulosBeanLocal articulosBean;
    private List<Articulos> listaArticulos;
    private Articulos articuloSelected;
    
    
      public ListaArticulosCtrl()
    {
      logger.log(Level.INFO, "[ListaArticulosCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();           
            articulosBean = serviceLocator.getService(Constants.JNDI_ARTICULOS_BEAN);
            numeroPaginInicio = 0;
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    
    }
    
      public void onCreate$listaArticuloWindow(Event event) throws Exception {
        logger.log(Level.INFO, "[onCreate$listaArticuloWindow]Event:{0}", event.toString());
        totalArticulos = articulosBean.countAllArticulos();
        MensajeMultilinea.doSetTemplate();


        if (totalArticulos != null) {
            setTotalArticulos(totalArticulos);
        } else {
            logger.info("[onCreate$listaArticuloWindow]No se pudo obtener total registros");
        }
        pagingArticulo.setPageSize(getUserLogin().getRegistrosLista());
        pagingArticulo.setDetailed(true);
        refreshModel(numeroPaginInicio);
//        doCheckPermisos();
        
    }
      
        public void doRefreshModel(int activePage) {
        logger.log(Level.INFO, "[ListaArticulosCtrl ][doRefreshModel]Pagina activa:{0}", activePage);
        refreshModel(activePage);

    }

    public void refreshModel(int activePage) {
        logger.log(Level.INFO, "[ListaArticulosCtrl ][refreshModel]Recargar articulos,Pagina activa:{0}", activePage);
        try {
            if (totalArticulos > 0) {
                listaArticulos = articulosBean.loadAllArticulos(activePage * getUserLogin().getRegistrosLista(), getUserLogin().getRegistrosLista());
                if (listaArticulos.size() > 0) {
                    logger.log(Level.INFO, "Registros cargados=={0}", listaArticulos.size());
                    pagingArticulo.setTotalSize(getTotalArticulos());
                    listBoxArticulo.setModel(new ListModelList(listaArticulos));
                    listBoxArticulo.setItemRenderer(new ArticuloItemRenderer());
                } else {
                    logger.info("No se cargaron registros");
                }
            } else {
                listBoxArticulo.setEmptyMessage("No se encontraron registros para mostrar");
            }
        } catch (Exception ex) {
            logger.log(Level.INFO, "[ListaArticuloArticuloCtrl ][refreshModel]Ocurrio Una exception :{0}", ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }
    
    public void onDoubleClickedArticulo(Event event) throws Exception {
        logger.log(Level.INFO, "[**onDoubleClickedArticulo]Event:{0}", event.toString());
        Listitem item = this.listBoxArticulo.getSelectedItem();
        if (item != null) {
            Articulos articulo= (Articulos) item.getAttribute("data");
            HashMap map = new HashMap();
            map.put("articuloSelected", articulo);
            map.put("token", UtilFormat.getToken());
            map.put("ListaArticulosCtrl", this);
            Executions.createComponents("/WEB-INF/xhtml/articulos/detalleArticulo.zul", null, map);
        }
    }

    public void onClick$btnNuevoArticulo(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnNuevoArticulo]Event:{0}", event.toString());
        HashMap map = new HashMap();
        map.put("token", UtilFormat.getToken());
        map.put("ListaArticulosCtrl", this);
        Executions.createComponents("/WEB-INF/xhtml/articulos/detalleArticulo.zul", null, map);

    }

    public void onClick$btnRefresh(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnRefresh]Event:{0}", event.toString());

        refreshModel(0);

    }

    public void onClick$btnBusquedaArticulo(Event event) throws Exception {
        logger.log(Level.INFO, "[onClick$btnBusquedaArticulo]Event:{0}", event.toString());
        HashMap map = new HashMap();
        map.put("token", UtilFormat.getToken());
        map.put("listaArticulosCtrl", this);
        Executions.createComponents("/WEB-INF/xhtml/articulos/busquedaArticulo.zul", null, map);

    }


    public Listbox getListBoxArticulo() {
        return listBoxArticulo;
    }

    public void setListBoxArticulo(Listbox listBoxArticulo) {
        this.listBoxArticulo = listBoxArticulo;
    }
      

    public Integer getTotalArticulos() {
        return totalArticulos;
    }

    public void setTotalArticulos(Integer totalArticulos) {
        this.totalArticulos = totalArticulos;
    }

    public Integer getNumeroPaginInicio() {
        return numeroPaginInicio;
    }

    public void setNumeroPaginInicio(Integer numeroPaginInicio) {
        this.numeroPaginInicio = numeroPaginInicio;
    }

    public List<Articulos> getListaArticulos() {
        return listaArticulos;
    }

    public void setListaArticulos(List<Articulos> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }
      
      
}
