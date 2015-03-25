/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.inventario;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.dto.CatalogoDTO;
import sv.com.diserv.liquidaciones.dto.ConsolidadoAsignacionesDTO;
import sv.com.diserv.liquidaciones.ejb.ArticulosBeanLocal;
import sv.com.diserv.liquidaciones.ejb.BodegaVendedorBeanLocal;
import sv.com.diserv.liquidaciones.ejb.CatalogosBeanLocal;
import sv.com.diserv.liquidaciones.ejb.DetListaPrecioBeanLocal;
import sv.com.diserv.liquidaciones.ejb.LotesExistenciasBeanLocal;
import sv.com.diserv.liquidaciones.ejb.MovimientosDetBeanLocal;
import sv.com.diserv.liquidaciones.ejb.PersonasBeanLocal;
import sv.com.diserv.liquidaciones.ejb.RelacionAsignacionBeanLocal;
import sv.com.diserv.liquidaciones.entity.Articulos;
import sv.com.diserv.liquidaciones.entity.BodegaVendedor;
import sv.com.diserv.liquidaciones.entity.DetListaPrecio;
import sv.com.diserv.liquidaciones.entity.LotesExistencia;
import sv.com.diserv.liquidaciones.entity.Movimientos;
import sv.com.diserv.liquidaciones.entity.MovimientosDet;
import sv.com.diserv.liquidaciones.entity.Personas;
import sv.com.diserv.liquidaciones.entity.RelacionAsignaciones;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.exception.DiservWebException;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
//import sv.com.diserv.web.ui.articulos.rendered.ArticuloComboitemRenderer;
import sv.com.diserv.web.ui.personas.rendered.CatalogoItemRenderer;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

/**
 *
 * @author abraham.acosta
 */
public class DetalleDevolucionCtrl extends BaseController {

    static final Logger logger = Logger.getLogger(DetalleDevolucionCtrl.class.getCanonicalName());
    private static final long serialVersionUID = -546886879998950489L;
    protected Window detalleDevolucionWindow;
    protected Panel panelInformacionDevolucion;
    protected Intbox txtNumDoc;
    protected Intbox txtNumDoc1;
    protected Textbox txtArticulo;
    protected Datebox txtfechaDevolucion;
    protected Datebox txtfechaDevolucion1;
    protected Combobox cmbVendedor;
    protected Combobox cmbArticulo;
    protected Textbox nombreVendedor;
    //protected Button btnAdd;
    protected Button btnBuscArt;
    protected Button btnBuscICC;
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
    private LotesExistenciasBeanLocal lotesExistenciasBean;
    private RelacionAsignacionBeanLocal relAsignacionBean;
    private Articulos articuloSelected;
    private List<Articulos> listaArticulos;
    private List<ConsolidadoAsignacionesDTO> consolidadoPaginaAnterior = new ArrayList<ConsolidadoAsignacionesDTO>();
    private List<MovimientosDet> movDet = new ArrayList<MovimientosDet>(); 
    private BodegaVendedor bodegaVendedor = new BodegaVendedor();
    private DetListaPrecioBeanLocal detListaPrecioBean;
    private BodegaVendedorBeanLocal bodegaVendedorBean;
    

    public List<Articulos> getListaArticulos() {
        return listaArticulos;
    }

    public void setListaArticulos(List<Articulos> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }

    public DetalleDevolucionCtrl() {
        logger.log(Level.INFO, "[DetalleDevolucionCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            movimientoDetBean = serviceLocator.getService(Constants.JNDI_MOVIMIENTOSDET_BEAN);
            personaBean = serviceLocator.getService(Constants.JNDI_PERSONA_BEAN);
            catalogosBeanLocal = serviceLocator.getService(Constants.JNDI_CATALOGO_BEAN);
            articulosBean = serviceLocator.getService(Constants.JNDI_ARTICULOS_BEAN);
            lotesExistenciasBean = serviceLocator.getService(Constants.JNDI_LOTESEXISTENCIAS_BEAN);
            relAsignacionBean = serviceLocator.getService(Constants.JNDI_RELASIGNACION_BEAN);
            detListaPrecioBean = serviceLocator.getService(Constants.JNDI_DETLISTAPRECIO_BEAN);
            bodegaVendedorBean = serviceLocator.getService(Constants.JNDI_BODEGAVENDEDOR_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }

    }

    public void onSelect$cmbArticulo(Event event) throws Exception {
        logger.log(Level.INFO, "[onSelect$cmbArticulo]");
        Comboitem item = this.cmbArticulo.getSelectedItem();
        //cmbArticulo.addEventListener("onEnter", this.cmbArticulo);
        articuloSelected = null;
        if (item != null) {
            articuloSelected = (Articulos) item.getAttribute("data");
          if (articuloSelected.getIdtipoarticulo().getLote()==1)
          {
            activarBusqueda(true);
          }
          else
          {
            activarBusqueda(false);
          }
        }
    }

    public void activarBusqueda(Boolean estado)
    {
            this.rowICC.setVisible(estado);
            this.btnBuscArt.setVisible(!estado);
            this.btnBuscICC.setVisible(estado);
    }
    
    public void onClick$btnBuscICC(Event event) throws Exception {
        
        logger.log(Level.INFO, "[onClick$btnBuscICC]");
       
    
        Comboitem item = this.cmbVendedor.getSelectedItem();
        if (item == null) {
                   throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe seleccionar un vendedor");
            }
          else
          {
            
             Personas vendedorSelected = (Personas) item.getAttribute("data");
                item = this.cmbArticulo.getSelectedItem();
                articuloSelected = null;
                if (item != null) {
                    articuloSelected = (Articulos) item.getAttribute("data");
                    //buscar icc
                    RelacionAsignaciones relAsignacion = (RelacionAsignaciones) relAsignacionBean.loadRelacionByVendedor(vendedorSelected.getIdpersona(), txtArticulo.getValue());
                    //LotesExistencia lotesExistencia = lotesExistenciasBean.buscarLoteByCriteria(null);

                  if (relAsignacion==null)
                  {
                    MensajeMultilinea.show(Constants.MSG_ICC_NO_ASIGNADO);  
                    txtArticulo.setFocus(true);
                  }
                  else
                  {
                        bodegaVendedor = bodegaVendedorBean.findBodegaVendedorByIdVendedor(vendedorSelected.getIdpersona());
                        //Articulos articulo = articuloBean.loadArticuloByID(consolida.getIdArticulo());
                        DetListaPrecio listaPrecio = detListaPrecioBean.findDetPrecioByIdArticulo(bodegaVendedor.getIdlista().getIdlista(), articuloSelected.getIdarticulo());
                        MovimientosDet movimientoDet = new MovimientosDet();
                         //movimientoDet.setIdmov(responseOperacion.getMovimiento());
                         movimientoDet.setCantidad(new BigDecimal(1));
                         movimientoDet.setFechaMov(new Date());
                         movimientoDet.setIdarticulo(articuloSelected);
                         movimientoDet.setIdlista(bodegaVendedor.getIdlista());
                         //movimientoDet.setNoDoc(asignacionSelected.getNodoc());
                         movimientoDet.setClaseOperacion("S");
                         movimientoDet.setCostoProm(articuloSelected.getCostocompact());
                         movimientoDet.setPrecio(listaPrecio.getPrecio());
                         movimientoDet.setUltCosto(articuloSelected.getCostocompant());
                         movimientoDet.setValorImpuesto(BigDecimal.ZERO);
                        movDet.add(movimientoDet);
                  }
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
        //loadComboboxVendedor();
        loadDataInicial();
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

//    private void loadComboboxVendedor() {
//
//        //vendedores
//        List<Personas> listaVendedores = null;
//        List<CatalogoDTO> listaCatalogoVendedores = new ArrayList<CatalogoDTO>();
//
//        try {
//
//            listaVendedores = personaBean.loadAllPersonaByTipoAndSucursal(2, 1);
//            List<Object> objectList = new ArrayList<Object>(listaVendedores);
//            listaCatalogoVendedores = catalogosBeanLocal.loadAllElementosCatalogo(objectList, "idpersona", "nombre");
//
//
//            if (listaCatalogoVendedores != null && listaCatalogoVendedores.size() > 0) {
//                ListModelList modelovendedor = new ListModelList(listaCatalogoVendedores);
//                cmbVendedor.setModel(modelovendedor);
//                cmbVendedor.setItemRenderer(new CatalogoItemRenderer());
//                cmbVendedor.setText("Seleccione un vendedor!!");
//                cmbVendedor.setReadonly(false);
//                cmbVendedor.setButtonVisible(true);
//            } else {
//                cmbVendedor.setText("No existen vendedores registrados!!");
//                cmbVendedor.setReadonly(true);
//                cmbVendedor.setButtonVisible(false);
//                cmbVendedor.setDisabled(true);
//            }
//
//
//        } catch (DiservBusinessException ex) {
//            Logger.getLogger(DetalleDevolucionCtrl.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public void loadDataInicial() {
        
        // combo articulos
       
        try {
            listaArticulos = articulosBean.loadAllArticulos();
            if (listaArticulos.size() > 0) {
                logger.log(Level.INFO, "Registros cargados=={0}", listaArticulos.size());
                cmbArticulo.setModel(new ListModelList(listaArticulos));
                cmbArticulo.setItemRenderer(new CatalogoItemRenderer());
            } else {
                logger.info("No se cargaron registros");
                cmbArticulo.setText("No se cargaron registros para mostrar");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //combo vendedores
         List<Personas> listaVendedores = null;
        try {
            listaVendedores = personaBean.loadAllPersonaByTipoAndSucursal(2, 1);
            if (listaVendedores.size() > 0) {
                logger.log(Level.INFO, "Registros cargados=={0}", listaVendedores.size());
                cmbVendedor.setModel(new ListModelList(listaVendedores));
                cmbVendedor.setItemRenderer(new CatalogoItemRenderer());
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
     //   this.btnAdd.setVisible(true);
        this.btnBuscArt.setVisible(false);
        this.btnBuscICC.setVisible(false);
        this.rowICC.setVisible(false);
    }
}
