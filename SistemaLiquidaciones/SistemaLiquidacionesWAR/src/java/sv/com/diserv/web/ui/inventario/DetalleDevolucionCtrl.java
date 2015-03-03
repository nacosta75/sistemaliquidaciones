/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.inventario;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.dto.CatalogoDTO;
import sv.com.diserv.liquidaciones.ejb.ArticulosBeanLocal;
import sv.com.diserv.liquidaciones.ejb.CatalogosBeanLocal;
import sv.com.diserv.liquidaciones.ejb.MovimientosDetBeanLocal;
import sv.com.diserv.liquidaciones.ejb.PersonasBeanLocal;
import sv.com.diserv.liquidaciones.entity.Articulos;
import sv.com.diserv.liquidaciones.entity.Movimientos;
import sv.com.diserv.liquidaciones.entity.Personas;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.web.ui.articulos.rendered.ArticuloComboitemRenderer;
import sv.com.diserv.web.ui.personas.DetalleClienteCtrl;
import sv.com.diserv.web.ui.personas.rendered.CatalogoItemRenderer;
import sv.com.diserv.web.ui.articulos.rendered.ArticuloItemRenderer;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

/**
 *
 * @author abraham.acosta
 */
public class DetalleDevolucionCtrl extends BaseController{
    
     static final Logger logger = Logger.getLogger(DetalleDevolucionCtrl.class.getCanonicalName());
    private static final long serialVersionUID = -546886879998950489L;
    protected Window detalleDevolucionWindow;
    protected Panel panelInformacionDevolucion;
    protected Intbox txtNumDoc;
    protected Intbox txtNumDoc1;
    protected Datebox txtfechaDevolucion;
    protected Datebox txtfechaDevolucion1;
    protected Combobox cmbVendedor;
    protected Combobox cmbArticulo;
    protected Textbox nombreVendedor;
    protected Button btnAdd;
    protected Button btnBuscar;
    protected Row rowICC;
//    protected Button btnEditar;
//    protected Button btnGuardar;
//    protected Button btnEliminar;
//    protected Button btnCerrar;
    private ServiceLocator serviceLocator;
    private MovimientosDetBeanLocal movimientoDetBean;
    private PersonasBeanLocal personaBean;
    private Movimientos devolucionSelected;
    private transient Integer token;
    private ListaDevolucionesCtrl listaDevolucionesCtrl;
    private CatalogosBeanLocal catalogosBeanLocal;
    private ArticulosBeanLocal articulosBean;
    private Articulos articuloSelected;
    private List<Articulos> listaArticulos;

    public List<Articulos> getListaArticulos() {
        return listaArticulos;
    }

    public void setListaArticulos(List<Articulos> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }
    
    
    
    public DetalleDevolucionCtrl()
    {
        logger.log(Level.INFO, "[DetalleDevolucionCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            movimientoDetBean = serviceLocator.getService(Constants.JNDI_MOVIMIENTOSDET_BEAN);
            personaBean = serviceLocator.getService(Constants.JNDI_PERSONA_BEAN); 
            catalogosBeanLocal = serviceLocator.getService(Constants.JNDI_CATALOGO_BEAN);
            articulosBean = serviceLocator.getService(Constants.JNDI_ARTICULOS_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
            
    }
    
    public void onSelect$cmbArticulo(Event event) throws Exception {
        logger.log(Level.INFO, "[onSelect$cmbArticulo]");
       Comboitem item = this.cmbArticulo.getSelectedItem();
       articuloSelected = null;
       if (item != null)
       {  
          articuloSelected = (Articulos) item.getAttribute("data");
          if (articuloSelected.getIdtipoarticulo().getIdtipoarticulo()==1)
          {
            this.rowICC.setVisible(true);
          }
       }
    }
    
     public void onCreate$detalleDevolucionWindow(Event event) throws Exception {
        doOnCreateCommon(this.detalleDevolucionWindow, event);
        MensajeMultilinea.doSetTemplate();
        if (this.args.containsKey("devolucionSelected")) {
            devolucionSelected = ((Movimientos) this.args.get("devolucionSelected"));
            setDevolucionSelected(devolucionSelected);
        }
        if (this.args.containsKey("token")) {
            this.token = ((Integer) this.args.get("token"));
            setToken(this.token);
        } else {
            setToken(Integer.valueOf(0));
        }
        if (this.args.containsKey("listaDevolucionCtrl")) {
            listaDevolucionesCtrl = ((ListaDevolucionesCtrl) this.args.get("listaDevolucionesCtrl"));
        }
        showDetalleDevoluciones();
        loadComboboxVendedor();
       // loadDataInicial();
    }

    public Movimientos getDevolucionSelected() {
        return devolucionSelected;
    }

    public void setDevolucionSelected(Movimientos devolucionSelected) {
        this.devolucionSelected = devolucionSelected;
    }

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    private void showDetalleDevoluciones() {
        try {
            if (devolucionSelected != null) {
                doEditButton();
                loadDataFromEntity();
                
            } else {
                doNew();
            }
            detalleDevolucionWindow.doModal();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(DetalleDevolucionCtrl.class.getName()).log(Level.SEVERE, null, e);
        }
       // btnGuardar.setVisible(false);
       // detalleDevolucionWindow.setHeight("370px");
    }

    private void loadComboboxVendedor() {
        
        //vendedores
         List<Personas> listaVendedores =null;
         List<CatalogoDTO> listaCatalogoVendedores = new ArrayList<CatalogoDTO>();
         
          //articulos
         List<CatalogoDTO> listaCatalogoArticulos = new ArrayList<CatalogoDTO>();
         


      try {
                
                listaVendedores = personaBean.loadAllPersonaByTipoAndSucursal(2,1);
                List<Object> objectList = new ArrayList<Object>(listaVendedores);
                listaCatalogoVendedores = catalogosBeanLocal.loadAllElementosCatalogo(objectList, "idpersona", "nombre");
                
               
                if(listaCatalogoVendedores != null && listaCatalogoVendedores.size()>0){
                    ListModelList modelovendedor = new ListModelList(listaCatalogoVendedores);
                    cmbVendedor.setModel(modelovendedor);
                    cmbVendedor.setItemRenderer(new CatalogoItemRenderer());
                    cmbVendedor.setText("Seleccione un vendedor!!");
                    cmbVendedor.setReadonly(false);
                    cmbVendedor.setButtonVisible(true);
                }
                else{
                     cmbVendedor.setText("No existen vendedores registrados!!");
                     cmbVendedor.setReadonly(true);
                     cmbVendedor.setButtonVisible(false);
                     cmbVendedor.setDisabled(true);
                    }
                
                listaArticulos = articulosBean.loadAllArticulos(0 * getUserLogin().getRegistrosLista(), getUserLogin().getRegistrosLista());
                objectList = new ArrayList<Object>(listaArticulos);
                listaCatalogoArticulos = catalogosBeanLocal.loadAllElementosCatalogo(objectList, "idarticulo", "descarticulo");
                               
                if(listaCatalogoArticulos != null && listaCatalogoArticulos.size()>0){
                    ListModelList modeloArticulo = new ListModelList(listaCatalogoArticulos);
                    cmbArticulo.setModel(modeloArticulo);
                    cmbArticulo.setItemRenderer(new CatalogoItemRenderer());
                    cmbArticulo.setText("Seleccione un Producto!!");
                    cmbArticulo.setReadonly(false);
                    cmbArticulo.setButtonVisible(true);
                }
                else{
                     cmbArticulo.setText("No existen productos registrados!!");
                     cmbArticulo.setReadonly(true);
                     cmbArticulo.setButtonVisible(false);
                     cmbArticulo.setDisabled(true);
                    }
                
                
           } catch (DiservBusinessException ex) {
                Logger.getLogger(DetalleDevolucionCtrl.class.getName()).log(Level.SEVERE, null, ex);
           }
    }
    
    
    public void loadDataInicial() {
        try {
            listaArticulos = articulosBean.loadAllArticulos(0 * getUserLogin().getRegistrosLista(), getUserLogin().getRegistrosLista());
            if (listaArticulos .size() > 0) {
                logger.log(Level.INFO, "Registros cargados=={0}", listaArticulos .size());
                cmbArticulo.setModel(new ListModelList(listaArticulos));
                cmbArticulo.setItemRenderer((ComboitemRenderer) new ArticuloItemRenderer());
            } else {
                logger.info("No se cargaron registros");
                cmbArticulo.setText("No se cargaron registros para mostrar");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doEditButton() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void loadDataFromEntity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void doNew() {
//        this.btnGuardar.setVisible(true);
//        this.btnCerrar.setVisible(true);
//        this.btnActualizar.setVisible(false);
//        this.btnEliminar.setVisible(false);
        this.btnAdd.setVisible(true);
        this.btnBuscar.setVisible(true);
        this.rowICC.setVisible(false);
    }
    
     
}
