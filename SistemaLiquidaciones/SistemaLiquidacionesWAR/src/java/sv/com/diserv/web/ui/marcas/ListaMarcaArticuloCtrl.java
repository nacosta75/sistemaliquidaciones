/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.marcas;

import java.util.List;
import sv.com.diserv.web.ui.util.BaseController;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.ejb.MarcaArticuloBeanLocal;
import sv.com.diserv.liquidaciones.entity.MarcaArticulo;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.web.ui.marcas.rendered.MarcaItemRenderer;
import sv.com.diserv.web.ui.util.MensajeMultilinea;


/**
 *
 * @author abraham.acosta
 */
public class ListaMarcaArticuloCtrl extends BaseController {
    
    static final Logger logger = Logger.getLogger(ListaMarcaArticuloCtrl.class.getCanonicalName());
    protected Window listaMarcaWindow;
    protected Button btnNuevaMarca;
    protected Button btnBusquedaMarca;
    protected Button btnRefresh;
    protected Paging pagingMarca;
    protected Listbox listBoxMarca;
    protected Listheader listheaderIdMarca;
    protected Listheader listheaderDescripcion;
    
     //contadores pagina
    private Integer totalMarcas;
    private Integer numeroPaginInicio = 1;
    private ServiceLocator serviceLocator;
    private MarcaArticuloBeanLocal marcaBean;
    private List<MarcaArticulo> listaMarcas;
    private MarcaArticulo marcaSelected;
    
    
      public ListaMarcaArticuloCtrl()
    {
      logger.log(Level.INFO, "[ListaMarcaArticuloCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();           
            marcaBean = serviceLocator.getService(Constants.JNDI_MARCAS_BEAN);
            numeroPaginInicio = 0;
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    
    }
      
       public void doRefreshModel(int activePage) {
        logger.log(Level.INFO, "[ListaMarcaArticuloCtrl ][doRefreshModel]Pagina activa:{0}", activePage);
        refreshModel(activePage);

    }
      
          public void onCreate$listaMarcaWindow(Event event) throws Exception {
        logger.log(Level.INFO, "[onCreate$listaMarcaWindow]Event:{0}", event.toString());
        totalMarcas = marcaBean.countAllMarcaArticulo();
        MensajeMultilinea.doSetTemplate();


        if (totalMarcas != null) {
            setTotalMarcas(totalMarcas);
        } else {
            logger.info("[onCreate$listaMarcaWindow]No se pudo obtener total registros");
        }
        pagingMarca.setPageSize(getUserLogin().getRegistrosLista());
        pagingMarca.setDetailed(true);
        refreshModel(numeroPaginInicio);
//        doCheckPermisos();
  
        
    }
          
       public void refreshModel(int activePage) {
        logger.log(Level.INFO, "[ListaMarcaArticuloCtrl ][refreshModel]Recargar bodegas,Pagina activa:{0}", activePage);
        try {
            if (totalMarcas > 0) {
                listaMarcas = marcaBean.loadAllMarcas(activePage * getUserLogin().getRegistrosLista(), getUserLogin().getRegistrosLista());
                if (listaMarcas.size() > 0) {
                    logger.log(Level.INFO, "Registros cargados=={0}", listaMarcas.size());
                    pagingMarca.setTotalSize(getTotalMarcas());
                    listBoxMarca.setModel(new ListModelList(listaMarcas));
                    listBoxMarca.setItemRenderer(new MarcaItemRenderer());
                } else {
                    logger.info("No se cargaron registros");
                }
            } else {
                listBoxMarca.setEmptyMessage("No se encontraron registros para mostrar");
            }
        } catch (Exception ex) {
            logger.log(Level.INFO, "[ListaMarcaArticuloCtrl ][refreshModel]Ocurrio Una exception :{0}", ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }      

    public Listbox getListBoxMarca() {
        return listBoxMarca;
    }

    public void setListBoxMarca(Listbox listBoxMarca) {
        this.listBoxMarca = listBoxMarca;
    }
    

    public Integer getTotalMarcas() {
        return totalMarcas;
    }

    public void setTotalMarcas(Integer totalMarcas) {
        this.totalMarcas = totalMarcas;
    }

    public Integer getNumeroPaginInicio() {
        return numeroPaginInicio;
    }

    public void setNumeroPaginInicio(Integer numeroPaginInicio) {
        this.numeroPaginInicio = numeroPaginInicio;
    }

    public List<MarcaArticulo> getListaMarcas() {
        return listaMarcas;
    }

    public void setListaMarcas(List<MarcaArticulo> listaMarcas) {
        this.listaMarcas = listaMarcas;
    }
    
    
    
}
