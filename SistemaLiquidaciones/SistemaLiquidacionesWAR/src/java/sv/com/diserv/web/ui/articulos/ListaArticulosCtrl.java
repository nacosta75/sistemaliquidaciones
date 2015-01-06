/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.articulos;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.ejb.ArticulosBeanLocal;
import sv.com.diserv.liquidaciones.entity.Articulos;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.web.ui.util.BaseController;

/**
 *
 * @author abraham,.acosta
 */
public class ListaArticulosCtrl extends BaseController{
    
    
    static final Logger logger = Logger.getLogger(ListaArticulosCtrl.class.getCanonicalName());
    protected Window listaArticuloWindow;
    protected Button btnNuevaArticulo;
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
}
