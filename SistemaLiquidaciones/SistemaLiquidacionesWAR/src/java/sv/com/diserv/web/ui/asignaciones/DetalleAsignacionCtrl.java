package sv.com.diserv.web.ui.asignaciones;

import java.math.BigDecimal;
import java.math.BigInteger;
import sv.com.diserv.web.ui.asignaciones.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.dto.BusquedaLoteExistenciaDTO;
import sv.com.diserv.liquidaciones.dto.CatalogoDTO;
import sv.com.diserv.liquidaciones.dto.ConsolidadoAsignacionesDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesMovimientoDTO;
import sv.com.diserv.liquidaciones.ejb.ArticulosBeanLocal;
import sv.com.diserv.liquidaciones.ejb.BodegaVendedorBeanLocal;
import sv.com.diserv.liquidaciones.ejb.CatalogosBeanLocal;
import sv.com.diserv.liquidaciones.ejb.DetListaPrecioBeanLocal;
import sv.com.diserv.liquidaciones.ejb.LotesExistenciasBeanLocal;
import sv.com.diserv.liquidaciones.ejb.MovimientosBeanLocal;
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
import sv.com.diserv.liquidaciones.entity.Sucursales;
import sv.com.diserv.liquidaciones.entity.TiposMov;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.exception.DiservWebException;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.liquidaciones.util.UtilFormat;
import sv.com.diserv.web.ui.asignaciones.render.ConsolidadoItemRenderer;
import sv.com.diserv.web.ui.asignaciones.render.LotesItemRenderer;
import sv.com.diserv.web.ui.personas.DetalleClienteCtrl;
import sv.com.diserv.web.ui.personas.rendered.CatalogoItemRenderer;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

public class DetalleAsignacionCtrl extends BaseController {

    private static final Logger logger = Logger.getLogger(DetalleAsignacionCtrl.class.getName());
    private static final long serialVersionUID = -546886879998950467L;
    protected Window detalleAsignacionWindow;
//    protected Intbox txtIdAsignacion;
    protected Intbox txtNumDoc;
    protected Intbox txtNumDoc1;
    protected Datebox txtfechaAsignacion;
    protected Datebox txtfechaAsignacion1;
    protected Combobox cmbVendedor;
    protected Textbox nombreVendedor;

    protected Button btnActualizar;
    protected Button btnNuevo;
    protected Button btnEditar;
    protected Button btnGuardar;
    protected Button btnEliminar;
    protected Button btnCerrar;
    protected Panel panelInformacionAsignacion;
    protected Panel panelOtrosDatos;
    protected Panel panelSelArt;
    protected Tabpanel tabpanel1;
    protected Tabpanel tabpanel2;
    protected Tabbox paneles;
    protected Tab tab1;
    protected Tab tab2;
    protected Tab tab3;
    
    private Movimientos asignacionSelected;
    private ListaAsignacionesCtrl listaAsignacionCtrl;
    private transient Integer token;
    private MovimientosBeanLocal movimientoBean;
    private PersonasBeanLocal personaBean;
    private BodegaVendedorBeanLocal bodegaVendedorBean;
    private LotesExistenciasBeanLocal lotesExistenciasBean;
    private RelacionAsignacionBeanLocal relAsignacionBean;
    private MovimientosDetBeanLocal movimientoDetBean;
    private ArticulosBeanLocal articuloBean;
    private DetListaPrecioBeanLocal detListaPrecioBean;
    private CatalogosBeanLocal catalogosBeanLocal;
    private ServiceLocator serviceLocator;
    private OperacionesMovimientoDTO responseOperacion;
    private List<Movimientos> listaAsignacionesLike;
    
    private Paging pagingArticulos;
    private Listbox listBoxAticulos;
    private Paging pagingAsignacion;
    private Listbox listBoxAsignacion;
    
     //contadores pagina
    private Integer totalArticulos;
    private Integer totalAsignaciones;
    private Integer numeroPaginInicio = 1;
    
    
    //------Variables tab Busqueda de articulos-----
    protected Intbox txtIdArticulo;
    protected Textbox txtIcc;
    protected Textbox txtImei;
    protected Textbox txtTelefono;
    protected Button btnBuscar;
    private BusquedaLoteExistenciaDTO request;
    private LotesExistenciasBeanLocal loteExistenciaBean;
    private ArticulosBeanLocal articulosBean;
   //    private Listbox listBoxAticulos;
    private DetalleAsignacionCtrl listaSeleccionados;
    
    private List<LotesExistencia> lotesPaginaAnterior = new ArrayList<LotesExistencia>();
    private List<ConsolidadoAsignacionesDTO> consolidadoPaginaAnterior = new ArrayList<ConsolidadoAsignacionesDTO>();
    
     //contadores pagina
//    private Integer totalArticulos;
//    private Integer numeroPaginInicio = 1;
    //-------------------------------------

    
    private List<Listitem> articulos = new ArrayList<Listitem>(); 
    private List<LotesExistencia> lotes = new ArrayList<LotesExistencia>(); 
    private List<LotesExistencia> suma = new ArrayList<LotesExistencia>(); ; 
    private List<ConsolidadoAsignacionesDTO> consolidado = new ArrayList<ConsolidadoAsignacionesDTO>(); 
    private BodegaVendedor bodegaVendedor = new BodegaVendedor();
   
    public DetalleAsignacionCtrl() {
        logger.log(Level.INFO, "[DetalleAsignacionCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            personaBean = serviceLocator.getService(Constants.JNDI_PERSONA_BEAN);
            catalogosBeanLocal = serviceLocator.getService(Constants.JNDI_CATALOGO_BEAN);
            movimientoBean = serviceLocator.getService(Constants.JNDI_MOVIMIENTOS_BEAN);
            relAsignacionBean = serviceLocator.getService(Constants.JNDI_RELASIGNACION_BEAN);
            bodegaVendedorBean = serviceLocator.getService(Constants.JNDI_BODEGAVENDEDOR_BEAN);
            lotesExistenciasBean = serviceLocator.getService(Constants.JNDI_LOTESEXISTENCIAS_BEAN);
            movimientoDetBean = serviceLocator.getService(Constants.JNDI_MOVIMIENTOSDET_BEAN);
            articuloBean = serviceLocator.getService(Constants.JNDI_ARTICULOS_BEAN);
            detListaPrecioBean = serviceLocator.getService(Constants.JNDI_DETLISTAPRECIO_BEAN);
            loteExistenciaBean = serviceLocator.getService(Constants.JNDI_LOTESEXISTENCIAS_BEAN);
            articulosBean = serviceLocator.getService(Constants.JNDI_ARTICULOS_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$detalleAsignacionWindow(Event event) throws Exception {
        doOnCreateCommon(this.detalleAsignacionWindow, event);
        MensajeMultilinea.doSetTemplate();
        if (this.args.containsKey("asignacionSelected")) {
            asignacionSelected = ((Movimientos) this.args.get("asignacionSelected"));
            setAsignacionSelected(asignacionSelected);
        }
        if (this.args.containsKey("token")) {
            this.token = ((Integer) this.args.get("token"));
            setToken(this.token);
        } else {
            setToken(Integer.valueOf(0));
        }
        if (this.args.containsKey("listaAsignacionCtrl")) {
            listaAsignacionCtrl = ((ListaAsignacionesCtrl) this.args.get("listaAsignacionCtrl"));
        }
        showDetalleAsignaciones();
         loadComboboxVendedor();
    }

    public void showDetalleAsignaciones() {
        try {
            if (asignacionSelected != null) {
                doEditButton();
                loadDataFromEntity();
                
            } else {
                doNew();
            }
            detalleAsignacionWindow.doModal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        btnGuardar.setVisible(false);
        detalleAsignacionWindow.setHeight("370px");
    }

    /**
     * cargamos los textboxs desde entity
     */
    private void loadDataFromEntity() {

//    protected ComboBox cmbEstadoCivil;
    
//        txtIdAsignacion.setValue(asignacionSelected.getIdmov());
        txtfechaAsignacion.setValue(asignacionSelected.getFechamov());
        loadComboboxVendedor();
    }

    private void loadComboboxVendedor(){
         List<Personas> listaVendedores;
         List<CatalogoDTO> listaCatalogoVendedores = new ArrayList<CatalogoDTO>();

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
                } catch (DiservBusinessException ex) {
                Logger.getLogger(DetalleClienteCtrl.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private void loadDataFromTextboxs() {
            asignacionSelected = new Movimientos();
//            asignacionSelected.setIdmov(txtIdAsignacion.getValue());
            if(txtfechaAsignacion.getValue() != null)
                asignacionSelected.setFechamov(txtfechaAsignacion.getValue());
            
            if(txtNumDoc.getValue() >0)
                asignacionSelected.setNodoc(txtNumDoc.getValue());
            
            if(cmbVendedor.getSelectedItem().getValue() != null && Integer.parseInt(cmbVendedor.getSelectedItem().getValue().toString()) != 0)
                asignacionSelected.setIdpersona(new Personas((Integer) cmbVendedor.getSelectedItem().getValue()));
            
            
             asignacionSelected.setIdsucursal(new Sucursales(1));
             asignacionSelected.setIdtipomov(new TiposMov(2));
             asignacionSelected.setEstado("2");
            
    }

    public void onClick$btnGuardar(Event event) {
        try {
            if (getToken().intValue() > 0) {
                loadDataFromTextboxs();
               
                LotesExistencia movimientoInicial = lotes.get(0);
                Movimientos bodegaSalida = movimientoBean.findMovimientoById(movimientoInicial.getIdmov().getIdmov());
                asignacionSelected.setIdbodegasalida(bodegaSalida.getIdbodegaentrada());
                
                asignacionSelected.setIdbodegaentrada(bodegaVendedor.getIdbodega());
                
                
                responseOperacion = movimientoBean.guardarMovimiento(asignacionSelected);
                if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                     lotesExistenciasBean.actualizarLotes(lotes);
                     relAsignacionBean.guardarRelacionAsignacion(responseOperacion.getMovimiento(),lotes);
                     for(ConsolidadoAsignacionesDTO consolida:consolidado){
                         Articulos articulo = articuloBean.loadArticuloByID(consolida.getIdArticulo());
                         DetListaPrecio listaPrecio = detListaPrecioBean.findDetPrecioByIdArticulo(bodegaVendedor.getIdlista().getIdlista(), consolida.getIdArticulo());
                         MovimientosDet movimientoDet = new MovimientosDet();
                         movimientoDet.setIdmov(responseOperacion.getMovimiento());
                         movimientoDet.setCantidad(new BigDecimal(consolida.getCantidad()));
                         movimientoDet.setFechaMov(asignacionSelected.getFechamov());
                         movimientoDet.setIdarticulo(articulo);
                         movimientoDet.setIdlista(bodegaVendedor.getIdlista());
                         movimientoDet.setNoDoc(asignacionSelected.getNodoc());
                         movimientoDet.setClaseOperacion("E");
                         movimientoDet.setCostoProm(articulo.getCostocompact());
                         movimientoDet.setPrecio(listaPrecio.getPrecio());
                         movimientoDet.setUltCosto(articulo.getCostocompant());
                         movimientoDet.setValorImpuesto(BigDecimal.ZERO);
                         
                         movimientoDetBean.guardarMovimientoDet(movimientoDet);
                     }
                     
                      
                    MensajeMultilinea.show(responseOperacion.getMensajeRespuesta() + " Id Asignacion:" + responseOperacion.getMovimiento().getIdmov(), Constants.MENSAJE_TIPO_INFO);
                    asignacionSelected = responseOperacion.getMovimiento();
                    loadDataFromEntity();
                    doEditButton();
                    listaAsignacionCtrl.refreshModel(0);
                    doClose();
                } else {
//                    MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
                }
                setToken(0);
            } else if (getToken().intValue() == 0) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Se intento guardar el mismo movimiento dos veces, por seguridad solo se proceso una vez ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.getMessage(), Constants.MENSAJE_TIPO_ERROR);
        }
    }

    public void onClick$btnEditar(Event event) {
        this.btnActualizar.setVisible(true);
        this.btnEliminar.setVisible(true);
        this.btnEditar.setVisible(false);
    }

    public void onClick$btnEliminar(Event event) {
       loadDataFromTextboxs();
            
     try {
            
           responseOperacion = movimientoBean.eliminarMovimiento(asignacionSelected);
            if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_INFO);
                doEditButton();
                doClose();
                listaAsignacionCtrl.refreshModel(0);
            } else {
                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
            }
        } catch (Exception bex) {
            bex.printStackTrace();
            logger.severe(_zclass);
            MensajeMultilinea.show(bex.toString(), Constants.MENSAJE_TIPO_ERROR);

        }
     }
    
    public void onClick$btnNuevo(Event event) {
        doNew();
    }

    public void onClick$btnCerrar(Event event) throws InterruptedException {
        doClose();
    }
    
     public void onClick$btnSiguiente(Event event) throws InterruptedException {
       try {
           int idVendedor =0;
            if (cmbVendedor != null && cmbVendedor.getSelectedItem() != null) {
                   idVendedor = (Integer) cmbVendedor.getSelectedItem().getValue();
               }

            if (idVendedor == 0) {
                   throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe seleccionar un vendedor");
               }
            
            panelInformacionAsignacion.setVisible(false);
            panelSelArt.setVisible(true);
            tab3.setSelected(true);
            btnGuardar.setVisible(false);
            detalleAsignacionWindow.setHeight("560px");
            detalleAsignacionWindow.setPosition("center");
            
        
        } catch (DiservWebException ex) {
            MensajeMultilinea.show(ex.getMensaje(), Constants.MENSAJE_TIPO_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.getMessage(), Constants.MENSAJE_TIPO_ERROR);
        }
    }
     
     public void onClick$btnSiguiente1(Event event) throws InterruptedException {
         
         List<LotesExistencia> lotesAnterior = lotes;
         
            lotes = new ArrayList<LotesExistencia>();
            suma = new ArrayList<LotesExistencia>();
            articulos = new ArrayList<Listitem>();
            consolidado = new ArrayList<ConsolidadoAsignacionesDTO>();
            //Si se selecciono un vendedor correctamente realizamos la asignacion en memoria
             articulos = getListBoxAticulos().getItems(); 
             HashMap<Integer,Integer> elementos = new HashMap<Integer, Integer>();
             HashMap<Integer,Integer> lotesIds = new HashMap<Integer, Integer>();
             List<ConsolidadoAsignacionesDTO> allLotes = new ArrayList<ConsolidadoAsignacionesDTO>();
             
         try {
                bodegaVendedor = bodegaVendedorBean.findBodegaVendedorByIdVendedor((Integer) cmbVendedor.getSelectedItem().getValue());
                
                for(Listitem item :articulos){
                    ConsolidadoAsignacionesDTO lote = (ConsolidadoAsignacionesDTO) item.getAttribute("data"); 
                    if(item.isSelected()){
                        lotes.add(lote.getLote());
                        lote.setSelected(Boolean.TRUE);
                        lotesIds.put(lote.getLote().getIdlote(),1);
                        if(!elementos.containsKey(lote.getLote().getIdarticulo().getIdarticulo())){
                            elementos.put(lote.getLote().getIdarticulo().getIdarticulo(), 1);
                            suma.add(lote.getLote());
                        }
                        else {
                           Integer cantidad=  elementos.get(lote.getLote().getIdarticulo().getIdarticulo()).intValue();
                           cantidad = cantidad+1;
                           elementos.remove(lote.getLote().getIdarticulo().getIdarticulo());
                           elementos.put(lote.getLote().getIdarticulo().getIdarticulo(), cantidad);
                        }

                    }else{
                        if(lote != null)
                            lote.setSelected(Boolean.FALSE);
                    }
                    allLotes.add(lote);
                }
    
                
                for(LotesExistencia lote1: lotesAnterior){
                   int agregar=1;
                    for(ConsolidadoAsignacionesDTO consolAnt:allLotes)
                        {
                            if(consolAnt != null && consolAnt.getLote().getIdlote() == lote1.getIdlote() && consolAnt.getSelected()== false)
                               {
                                   agregar = 0;
                                   break;
                               }
                    }
                        
                    
                    if(!lotesIds.containsKey(lote1.getIdlote()) && agregar == 1){
                        if(!elementos.containsKey(lote1.getIdarticulo().getIdarticulo())){
                                elementos.put(lote1.getIdarticulo().getIdarticulo(), 1);
                                suma.add(lote1);
                            }
                            else {
                               Integer cantidad=  elementos.get(lote1.getIdarticulo().getIdarticulo()).intValue();
                               cantidad = cantidad+1;
                               elementos.remove(lote1.getIdarticulo().getIdarticulo());
                               elementos.put(lote1.getIdarticulo().getIdarticulo(), cantidad);
                            }

                         lotes.add(lote1);
                     }
                     
                }
                
                if(lotes == null || lotes.size()==0) {
                   throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe seleccionar al menos un articulo para asignar");
               }
                
                for(LotesExistencia lote: suma){
                    ConsolidadoAsignacionesDTO consol = new ConsolidadoAsignacionesDTO();
                        try {
                            Articulos articulo = articulosBean.loadArticuloByID(lote.getIdarticulo().getIdarticulo());
                            DetListaPrecio listaPrecio = detListaPrecioBean.findDetPrecioByIdArticulo(bodegaVendedor.getIdlista().getIdlista(), lote.getIdarticulo().getIdarticulo());
                            consol.setIdArticulo(lote.getIdarticulo().getIdarticulo());
                            consol.setCodigoArticulo(articulo.getCodarticulo());
                            consol.setDescripcion(articulo.getDescarticulo());
                            Integer cantidad=  elementos.get(lote.getIdarticulo().getIdarticulo()).intValue();
                            consol.setCantidad(cantidad);
                            if(listaPrecio != null)
                                consol.setPrecio(listaPrecio.getPrecio().toString());
                            consolidado.add(consol);
                        } catch (DiservBusinessException ex) {
                            java.util.logging.Logger.getLogger(BuscarArticuloCtrl.class.getName()).log(Level.SEVERE, null, ex);
                        }

                }

    
    
                if (!lotes.isEmpty()) {
//                            setTotalArticulos(lotes.size());
//                            getListBoxAticulos().setModel(new ListModelList(lotes));
//                            getListBoxAticulos().setItemRenderer(new LotesItemRenderer());

                            setTotalAsignaciones(consolidado.size());
                            getListBoxAsignacion().setModel(new ListModelList(consolidado));
                            getListBoxAsignacion().setItemRenderer(new ConsolidadoItemRenderer());
                        } else {
                            getListBoxAticulos().setEmptyMessage("No se han asignado articulos");
                            MensajeMultilinea.show("No se han asignado articulos", Constants.MENSAJE_TIPO_ALERTA);
                        }
        panelSelArt.setVisible(false);
        panelOtrosDatos.setVisible(true);
        tab2.setSelected(true);
        btnGuardar.setVisible(true);
        detalleAsignacionWindow.setHeight("640px");
        nombreVendedor.setValue(cmbVendedor.getSelectedItem().getLabel());
        txtNumDoc1.setValue(txtNumDoc.getValue());
        txtfechaAsignacion1.setValue(txtfechaAsignacion.getValue());
        
         } catch (DiservWebException ex) {
            MensajeMultilinea.show(ex.getMensaje(), Constants.MENSAJE_TIPO_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.getMessage(), Constants.MENSAJE_TIPO_ERROR);
        }
    }
     
    public void onClick$btnAnterior(Event event) throws InterruptedException {
        panelInformacionAsignacion.setVisible(true);
        panelOtrosDatos.setVisible(false);
        tab1.setSelected(true);
        btnGuardar.setVisible(false);
        detalleAsignacionWindow.setHeight("370px");
    }
    
    public void onClick$btnAnterior1(Event event) throws InterruptedException {
        panelSelArt.setVisible(true);
        panelOtrosDatos.setVisible(false);
        tab3.setSelected(true);
        btnGuardar.setVisible(false);
        detalleAsignacionWindow.setHeight("560px");
    }

    public void onClick$btnActualizar(Event event) throws InterruptedException {
        doActualizar();
        this.btnActualizar.setVisible(false);
        this.btnEliminar.setVisible(false);
    }

    private void doClose() {
        this.detalleAsignacionWindow.onClose();
    }

    private void doNew() {
        this.btnGuardar.setVisible(true);
        this.btnCerrar.setVisible(true);
        this.btnActualizar.setVisible(false);
        this.btnEliminar.setVisible(false);
        this.btnEditar.setVisible(false);
        this.btnNuevo.setVisible(false);
    }

    private void doEditButton() {
        this.btnCerrar.setVisible(true);
        this.btnEditar.setVisible(true);
        this.btnNuevo.setVisible(true);
        this.btnGuardar.setVisible(false);
        this.btnActualizar.setVisible(false);
        this.btnEliminar.setVisible(false);
    }

    public void doActualizar() {
        loadDataFromTextboxs();
        try {
            
            responseOperacion = movimientoBean.actualizarMovimiento(asignacionSelected);
            if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta() + " Id asignacion:" + responseOperacion.getMovimiento().getIdmov(), Constants.MENSAJE_TIPO_INFO);
                asignacionSelected = responseOperacion.getMovimiento();
                loadDataFromEntity();
                doEditButton();
                listaAsignacionCtrl.refreshModel(Constants.PAGINA_INICIO_DEFAULT);
            } else {
                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
            }
        } catch (Exception bex) {
            bex.printStackTrace();
            logger.severe(_zclass);
            MensajeMultilinea.show(bex.toString(), Constants.MENSAJE_TIPO_ERROR);

        }
        doEditButton();
    }
    
     public void onChange$cmbVendedor(Event event) throws Exception {
        logger.log(Level.INFO, "[onChangeCombo]Event:{0}", event.toString());
        int numeroDoc = movimientoBean.maxNumDocByVendedorAndTipoMov(Integer.parseInt(cmbVendedor.getSelectedItem().getValue().toString()), 2);
        txtNumDoc.setValue(numeroDoc+1);
    }

    public void onClick$btnNuevoArticulo(Event event) throws Exception {
        logger.log(Level.INFO, "[btnNuevoArticulo]Event:{0}", event.toString());
        HashMap map = new HashMap();
        map.put("token", UtilFormat.getToken());
        map.put("listaArticulosCtrl", this);
        Executions.createComponents("/WEB-INF/xhtml/asignaciones/busquedaArticulo.zul", null, map);

    }
    
    //---------------------Logica pantalla de busqueda de articulos----------------------
    
    public void onClick$btnBuscar(Event event) throws InterruptedException {
//        if (logger.isDebugEnabled()) {
//            logger.debug("--> " + event.toString());
//        }
        doBuscar();
    }

    
    public void doBuscar() {
        try {
            request = new BusquedaLoteExistenciaDTO();
            List<ConsolidadoAsignacionesDTO> listaExistencias;
            
            if (StringUtils.isNotEmpty(txtIdArticulo.getText())) {
                request.setIdArticulo(txtIdArticulo.getValue());
            }
            if (StringUtils.isNotEmpty(txtIcc.getValue())) {
                request.setIcc(txtIcc.getValue().toUpperCase());
            }
            if (StringUtils.isNotEmpty(txtImei.getValue())) {
                request.setImei(txtImei.getValue());
            }
            if (StringUtils.isNotEmpty(txtTelefono.getValue())) {
                request.setTelefono(txtTelefono.getValue());
            }
            
            String lotesExcluir = StringUtils.EMPTY;
            for(LotesExistencia lote:lotesPaginaAnterior){
                lotesExcluir = lotesExcluir+lote.getIdlote()+",";
            }
            
            if(StringUtils.isNotEmpty(lotesExcluir))
                request.setLotes(lotesExcluir.substring(0,lotesExcluir.length()-1));
                    
            listaExistencias = loteExistenciaBean.buscarLoteByCriterias(request);
            List<ConsolidadoAsignacionesDTO> listaHija = new ArrayList<ConsolidadoAsignacionesDTO>();
            
           if(lotes != null && lotes.size()>0){
                for(ConsolidadoAsignacionesDTO consol: listaExistencias){
                    for(LotesExistencia lotesSeleccionados:lotes){
                        if(consol.getLote().getIdlote() == lotesSeleccionados.getIdlote())
                            consol.setSelected(Boolean.TRUE);
                        if(!listaHija.contains(consol))
                        listaHija.add(consol);

                    }
                }
                listaExistencias = new ArrayList<ConsolidadoAsignacionesDTO>();
                listaExistencias= listaHija;
            }

           
            if (!listaExistencias.isEmpty()) {
                if (listaExistencias.size() > 0) {
                    setTotalArticulos(listaExistencias.size());
                    listBoxAticulos.setModel(new ListModelList(listaExistencias));
                    listBoxAticulos.setItemRenderer(new LotesItemRenderer());
                } else {
                    logger.info("No se cargaron registros");
                }
               
            } else {
                getListBoxAticulos().setEmptyMessage("No se encontraron registros con los criterios ingresados!!");
                MensajeMultilinea.show("No se encontraron clientes con los criterios ingresados", Constants.MENSAJE_TIPO_ALERTA);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.toString(), Constants.MENSAJE_TIPO_ERROR);
        }

    }

    
    public Integer getToken() {
        return this.token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    public Movimientos getAsignacionSelected() {
        return asignacionSelected;
    }

    public void setAsignacionSelected(Movimientos asignacionSelected) {
        this.asignacionSelected = asignacionSelected;
    }

    public ListaAsignacionesCtrl getListaAsignacionCtrl() {
        return listaAsignacionCtrl;
    }

    public void setListaAsignacionCtrl(ListaAsignacionesCtrl listaAsignacionCtrl) {
        this.listaAsignacionCtrl = listaAsignacionCtrl;
    }

    public List<Movimientos> getListaAsignacionesLike() {
        return listaAsignacionesLike;
    }

    public void setListaClientesLike(List<Movimientos> listaAsignacionesLike) {
        this.listaAsignacionesLike = listaAsignacionesLike;
    }

    /**
     * @return the pagingArticulos
     */
    public Paging getPagingArticulos() {
        return pagingArticulos;
    }

    /**
     * @param pagingArticulos the pagingArticulos to set
     */
    public void setPagingArticulos(Paging pagingArticulos) {
        this.pagingArticulos = pagingArticulos;
    }

    /**
     * @return the listBoxAticulos
     */
    public Listbox getListBoxAticulos() {
        return listBoxAticulos;
    }

    /**
     * @param listBoxAticulos the listBoxAticulos to set
     */
    public void setListBoxAticulos(Listbox listBoxAticulos) {
        this.listBoxAticulos = listBoxAticulos;
    }

    /**
     * @return the totalArticulos
     */
    public Integer getTotalArticulos() {
        return totalArticulos;
    }

    /**
     * @param totalArticulos the totalArticulos to set
     */
    public void setTotalArticulos(Integer totalArticulos) {
        this.totalArticulos = totalArticulos;
    }

    /**
     * @return the listBoxAsignacion
     */
    public Listbox getListBoxAsignacion() {
        return listBoxAsignacion;
    }

    /**
     * @param listBoxAsignacion the listBoxAsignacion to set
     */
    public void setListBoxAsignacion(Listbox listBoxAsignacion) {
        this.listBoxAsignacion = listBoxAsignacion;
    }

    /**
     * @return the totalAsignaciones
     */
    public Integer getTotalAsignaciones() {
        return totalAsignaciones;
    }

    /**
     * @param totalAsignaciones the totalAsignaciones to set
     */
    public void setTotalAsignaciones(Integer totalAsignaciones) {
        this.totalAsignaciones = totalAsignaciones;
    }

    /**
     * @return the numeroPaginInicio
     */
    public Integer getNumeroPaginInicio() {
        return numeroPaginInicio;
    }
    
    
}
