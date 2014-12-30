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
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.ejb.MarcaArticuloBeanLocal;
import sv.com.diserv.liquidaciones.entity.MarcaArticulo;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;

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
    private MarcaArticulo lineaSelected;
    
    
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
