package sv.com.diserv.web.ui.asignaciones;

import sv.com.diserv.web.ui.asignaciones.*;
import java.math.BigDecimal;
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
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.dto.CatalogoDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesMovimientoDTO;
import sv.com.diserv.liquidaciones.ejb.CatalogosBeanLocal;
import sv.com.diserv.liquidaciones.ejb.MovimientosBeanLocal;
import sv.com.diserv.liquidaciones.ejb.PersonasBeanLocal;
import sv.com.diserv.liquidaciones.entity.Movimientos;
import sv.com.diserv.liquidaciones.entity.Personas;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.exception.DiservWebException;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.liquidaciones.util.UtilFormat;
import static sv.com.diserv.web.ui.asignaciones.ListaAsignacionesCtrl.logger;
import sv.com.diserv.web.ui.personas.DetalleClienteCtrl;
import sv.com.diserv.web.ui.personas.rendered.CatalogoItemRenderer;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

public class DetalleAsignacionCtrl extends BaseController {

    private static final Logger logger = Logger.getLogger(DetalleAsignacionCtrl.class.getName());
    private static final long serialVersionUID = -546886879998950467L;
    protected Window detalleAsignacionWindow;
    protected Intbox txtIdAsignacion;
    protected Intbox txtNumDoc;
    protected Datebox txtfechaAsignacion;
    protected Combobox cmbVendedor;

    protected Button btnActualizar;
    protected Button btnNuevo;
    protected Button btnEditar;
    protected Button btnGuardar;
    protected Button btnEliminar;
    protected Button btnCerrar;
    private Movimientos asignacionSelected;
    private ListaAsignacionesCtrl listaAsignacionCtrl;
    private transient Integer token;
    private MovimientosBeanLocal movimientoBean;
    private PersonasBeanLocal personaBean;
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

    private static List<String> colors = new ArrayList<String>();
     
     static{
        colors.add("blue");
        colors.add("black");
        colors.add("white");
                
    }
     
    public static final List<String> getColors() {
        return new ArrayList<String>(colors);
    }

    public DetalleAsignacionCtrl() {
        logger.log(Level.INFO, "[DetalleAsignacionCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            personaBean = serviceLocator.getService(Constants.JNDI_PERSONA_BEAN);
            catalogosBeanLocal = serviceLocator.getService(Constants.JNDI_CATALOGO_BEAN);
            movimientoBean = serviceLocator.getService(Constants.JNDI_MOVIMIENTOS_BEAN);
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
    }

    /**
     * cargamos los textboxs desde entity
     */
    private void loadDataFromEntity() {

//    protected ComboBox cmbEstadoCivil;
    
        txtIdAsignacion.setValue(asignacionSelected.getIdmov());
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
//        try {
            asignacionSelected = new Movimientos();
            //validamos los campos

//            if (StringUtils.isEmpty(txtNombreVendedor.getValue())) {
//                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe seleccionar un vendedor");
//            }

           
            asignacionSelected.setIdmov(txtIdAsignacion.getValue());
//            asignacionSelected.setNombre(txtNombreVendedor.getValue());
            
            if(txtfechaAsignacion.getValue() != null)
                asignacionSelected.setFechamov(txtfechaAsignacion.getValue());

          
            
//            asignacionSelected.setIdtipopersona(new TiposPersona(1));
//            asignacionSelected.setIdempresa(new Empresas(1));
//            asignacionSelected.setIdsucursal(new Sucursales(1));
//            asignacionSelected.setIdusuariocrea(userLogin.getUsuario().getIdusuario());
            
//        } catch (DiservWebException ex) {
//            MensajeMultilinea.show(ex.getMensaje(), Constants.MENSAJE_TIPO_ERROR);
//        }

    }

    public void onClick$btnGuardar(Event event) {
        try {
            if (getToken().intValue() > 0) {
                loadDataFromTextboxs();
                responseOperacion = movimientoBean.guardarMovimiento(asignacionSelected);
                if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                    MensajeMultilinea.show(responseOperacion.getMensajeRespuesta() + " Id Asignacion:" + responseOperacion.getMovimiento().getIdmov(), Constants.MENSAJE_TIPO_INFO);
                    asignacionSelected = responseOperacion.getMovimiento();
                    loadDataFromEntity();
                    doEditButton();
                    listaAsignacionCtrl.refreshModel(0);
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
