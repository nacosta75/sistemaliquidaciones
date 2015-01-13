/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.articulos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.dto.CatalogoDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesArticuloDTO;
import sv.com.diserv.liquidaciones.ejb.ArticulosBeanLocal;

import sv.com.diserv.liquidaciones.ejb.CatalogosBeanLocal;
import sv.com.diserv.liquidaciones.ejb.EmpresasBeanLocal;
import sv.com.diserv.liquidaciones.ejb.LineaArticuloBeanLocal;
import sv.com.diserv.liquidaciones.ejb.MarcaArticuloBeanLocal;
import sv.com.diserv.liquidaciones.ejb.TipoArticuloBeanLocal;
import sv.com.diserv.liquidaciones.ejb.UmedidaArticuloBeanLocal;
import sv.com.diserv.liquidaciones.entity.Articulos;
import sv.com.diserv.liquidaciones.entity.Empresas;
import sv.com.diserv.liquidaciones.entity.LineaArticulo;
import sv.com.diserv.liquidaciones.entity.MarcaArticulo;
import sv.com.diserv.liquidaciones.entity.Tipoarticulo;
import sv.com.diserv.liquidaciones.entity.UnidadesMed;

import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.exception.DiservWebException;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.liquidaciones.util.UtilFormat;
import sv.com.diserv.web.ui.personas.rendered.CatalogoItemRenderer;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

/**
 *
 * @author abraham.acosta
 */
public class DetalleArticuloCtrl extends BaseController {

    private static final Logger logger = Logger.getLogger(DetalleArticuloCtrl.class.getName());
    private static final long serialVersionUID = -546886879998950994L;
    private ArticulosBeanLocal articulosBean;
    private EmpresasBeanLocal empresasBean;
    private ServiceLocator serviceLocator;
    private Articulos articuloSelected;
    

    private transient Integer token;
    private ListaArticulosCtrl listaArticulosCtrl;

    protected Window detalleArticuloWindow;
    protected Intbox txtIdArticulo;
    protected Textbox txtDescripcion;
    protected Textbox txtCodigo;
    protected Decimalbox txtCostoProm;
    protected Decimalbox txtCostoAnt;
    //protected Checkbox checkEstadoArticulo;
    private OperacionesArticuloDTO responseOperacion;
    protected Button btnActualizar;
    protected Button btnNuevo;
    protected Button btnEditar;
    protected Button btnGuardar;
    protected Button btnCerrar;
    protected Combobox cmbTipoArticulo;
    protected Combobox cmbLineaArticulo;
    protected Combobox cmbMarcaArticulo;
    protected Combobox cmbMedidaArticulo;
    private List<Articulos> listaArticulosLike;
    private CatalogosBeanLocal catalogosBeanLocal;
    private TipoArticuloBeanLocal tipoArticuloBean;
    private LineaArticuloBeanLocal lineasBean;
    private MarcaArticuloBeanLocal marcasBean;
    private UmedidaArticuloBeanLocal umedidaBean;

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    public List<Articulos> getListaArticulosLike() {
        return listaArticulosLike;
    }

    public void setListaArticulosLike(List<Articulos> listaArticulosLike) {
        this.listaArticulosLike = listaArticulosLike;
    }

    public Articulos getArticuloSelected() {
        return articuloSelected;
    }

    public void setArticuloSelected(Articulos articuloSelected) {
        this.articuloSelected = articuloSelected;
    }

    private void loadCombobox() {
        
        
        List<CatalogoDTO> listaCatalogo = new ArrayList<CatalogoDTO>();
        List<CatalogoDTO> listaCatalogoTipoArticulo = new ArrayList<CatalogoDTO>();
        List<Tipoarticulo> listaTipoArticulo;
        
        // lineas
        List<CatalogoDTO> listaCatalogoLineas = new ArrayList<CatalogoDTO>();
        List<LineaArticulo> listaLineas;
        
        //marcas
        List<CatalogoDTO> listaCatalogoMarcas = new ArrayList<CatalogoDTO>();
        List<MarcaArticulo> listaMarcas;
        
        //umedida
        List<CatalogoDTO> listaCatalogoUmedida = new ArrayList<CatalogoDTO>();
        List<UnidadesMed> listaUmedida;
        
        try {

            listaTipoArticulo = tipoArticuloBean.loadAllTiposArticulos();
            List<Object> objectList = new ArrayList<Object>(listaTipoArticulo);
            listaCatalogoTipoArticulo = catalogosBeanLocal.loadAllElementosCatalogo(objectList, "idtipoarticulo", "descripcion");

            if (listaCatalogoTipoArticulo != null && listaCatalogoTipoArticulo.size() > 0) {
                ListModelList modelotipo = new ListModelList(listaCatalogoTipoArticulo);
                cmbTipoArticulo.setModel(modelotipo);
                cmbTipoArticulo.setItemRenderer(new CatalogoItemRenderer());
            } else {
                cmbTipoArticulo.setValue("No existen tipos articulos!!");
                cmbTipoArticulo.setReadonly(true);
                cmbTipoArticulo.setButtonVisible(false);
                cmbTipoArticulo.setDisabled(true);
            }
            
           //****************************** lineas de articulos *********************/ 
            listaLineas = lineasBean.loadAllLineas();
            List<Object> objectListLinea = new ArrayList<Object>(listaLineas);
            listaCatalogoLineas = catalogosBeanLocal.loadAllElementosCatalogo(objectListLinea, "idlinea", "desclinea");

            if (listaCatalogoLineas != null && listaCatalogoLineas.size() > 0) {
                ListModelList modelotipo = new ListModelList(listaCatalogoLineas);
                cmbLineaArticulo.setModel(modelotipo);
                cmbLineaArticulo.setItemRenderer(new CatalogoItemRenderer());
            } else {
                cmbLineaArticulo.setValue("No existen Lineas!!");
                cmbLineaArticulo.setReadonly(true);
                cmbLineaArticulo.setButtonVisible(false);
                cmbLineaArticulo.setDisabled(true);
            }
            
            
            
            //****************************** marcas de articulos *********************/ 
            listaMarcas = marcasBean.loadAllMarcas();
            List<Object> objectListMarca = new ArrayList<Object>(listaMarcas);
            listaCatalogoMarcas = catalogosBeanLocal.loadAllElementosCatalogo(objectListMarca, "idmarca", "descmarca");

            if (listaCatalogoMarcas != null && listaCatalogoMarcas.size() > 0) {
                ListModelList modelotipo = new ListModelList(listaCatalogoMarcas);
                cmbMarcaArticulo.setModel(modelotipo);
                cmbMarcaArticulo.setItemRenderer(new CatalogoItemRenderer());
            } else {
                cmbMarcaArticulo.setValue("No existen Marcas!!");
                cmbMarcaArticulo.setReadonly(true);
                cmbMarcaArticulo.setButtonVisible(false);
                cmbMarcaArticulo.setDisabled(true);
            }
            
             //****************************** unidades de medida *********************/ 
            listaUmedida = umedidaBean.loadAllUmedidaArticulos();
            List<Object> objectListMedida = new ArrayList<Object>(listaUmedida);
            listaCatalogoUmedida = catalogosBeanLocal.loadAllElementosCatalogo(objectListMedida, "idumedida", "descumedida");

            if (listaCatalogoUmedida != null && listaCatalogoUmedida.size() > 0) {
                ListModelList modelotipo = new ListModelList(listaCatalogoUmedida);
                cmbMedidaArticulo.setModel(modelotipo);
                cmbMedidaArticulo.setItemRenderer(new CatalogoItemRenderer());
            } else {
                cmbMedidaArticulo.setValue("No existen Umedida!!");
                cmbMedidaArticulo.setReadonly(true);
                cmbMedidaArticulo.setButtonVisible(false);
                cmbMedidaArticulo.setDisabled(true);
            }
            

        } catch (DiservBusinessException ex) {
            Logger.getLogger(DetalleArticuloCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DetalleArticuloCtrl() {
        logger.log(Level.INFO, "[DetalleArticuloCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            articulosBean = serviceLocator.getService(Constants.JNDI_ARTICULOS_BEAN);
            catalogosBeanLocal = serviceLocator.getService(Constants.JNDI_CATALOGO_BEAN);
            tipoArticuloBean = serviceLocator.getService(Constants.JNDI_TIPOARTICULOS_BEAN);
            lineasBean = serviceLocator.getService(Constants.JNDI_LINEAS_BEAN);
            marcasBean = serviceLocator.getService(Constants.JNDI_MARCAS_BEAN);
            umedidaBean = serviceLocator.getService(Constants.JNDI_UMEDIDA_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }

    }

    public void onCreate$detalleArticuloWindow(Event event) throws Exception {
        doOnCreateCommon(this.detalleArticuloWindow, event);
        MensajeMultilinea.doSetTemplate();
        if (this.args.containsKey("articuloSelected")) {
            articuloSelected = ((Articulos) this.args.get("articuloSelected"));
            setArticuloSelected(articuloSelected);
        }
        if (this.args.containsKey("token")) {
            this.token = ((Integer) this.args.get("token"));
            setToken(this.token);
        } else {
            setToken(Integer.valueOf(0));
        }
        if (this.args.containsKey("listaArticulosCtrl")) {
            listaArticulosCtrl = ((ListaArticulosCtrl) this.args.get("listaArticulosCtrl"));
        }
        //  checkPermisos();
        showDetalleArticulos();
        //this.userLogin.getUsuario().getIdusuario();
    }

    public void showDetalleArticulos() {
        try {
            if (articuloSelected != null) {
                doReadOnly(Boolean.TRUE);
                doEditButton();
                loadDataFromEntity();
//            if (bodega.getIdBodegas().intValue() >= 1) {
//                this.listBoxOrdentrabajoBodegas.setModel(new ListModelList(
//                        this.ordentrabajoDao.buscarOrdenesPorbodega(
//                        bodega.getIdBodegas())));
//                this.listBoxOrdentrabajoBodegas.setItemRenderer(new BodegasOrdentrabajoListModelItemRenderer());
//                this.panel_tramites_bodega.setTitle(
//                        "Ultimos tramites del bodega :"
//                        + bodega.getNombreBodegas());
//            }
            } else {
                doNew();
            }
            detalleArticuloWindow.doModal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromEntity() {

        txtIdArticulo.setValue(articuloSelected.getIdarticulo());
        txtCodigo.setValue(articuloSelected.getCodarticulo());
        txtDescripcion.setValue(articuloSelected.getDescarticulo());
        
        loadCombobox();
       
        if (articuloSelected.getCostopromact().compareTo(BigDecimal.ZERO)>0)
        {
            txtCostoProm.setValue(articuloSelected.getCostocompact());
        }
        else
        {
          txtCostoProm.setValue(BigDecimal.ZERO);
        }
        if (articuloSelected.getCostopromant().compareTo(BigDecimal.ZERO)>0)
        {
            txtCostoAnt.setValue(articuloSelected.getCostocompant());
        }
        else
        {
          txtCostoAnt.setValue(BigDecimal.ZERO);
        }
        
       
        // txtIdBodegas.setValue(bodegaSelected.getIdbodega());
        // txtNombreBodegas.setText(bodegaSelected.getNombre());
        // txtTelefono.setValue(bodegaSelected.getTelefono());
        //cmbSucursal.
        //checkEstadoBodegas.setChecked((boolean) (bodegaSelected.getActiva() != null ? bodegaSelected.getActiva() : false));
//        if (articuloSelected.ge.equals("S")) {
//            checkEstadoArticulos.setChecked(true);
//        } else {
//            checkEstadoArticulos.setChecked(false);
//        }

    }

    private void loadDataFromTextboxs() {
        try {
            Articulos articuloSelected2 = articuloSelected;
            articuloSelected = new Articulos();        

            //validamos los campos
             if (StringUtils.isEmpty(txtCodigo.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar codigo para Articulos");
            }

            if (StringUtils.isEmpty(txtDescripcion.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar descripción para Articulos");
            }
            
            articuloSelected.setIdtipoarticulo(new Tipoarticulo((Integer) cmbTipoArticulo.getSelectedItem().getValue()));
            articuloSelected.setIdlinea(new LineaArticulo((Integer) cmbLineaArticulo.getSelectedItem().getValue()));
            articuloSelected.setIdmarca(new MarcaArticulo((Integer) cmbMarcaArticulo.getSelectedItem().getValue()));
            articuloSelected.setIdumedida(new UnidadesMed((Integer) cmbMedidaArticulo.getSelectedItem().getValue()));
            articuloSelected.setIdempresa(new Empresas(1));
            articuloSelected.setCostopromact(BigDecimal.ZERO);
            articuloSelected.setCostopromant(BigDecimal.ZERO);
            articuloSelected.setCostocompact(BigDecimal.ZERO);
            articuloSelected.setCostocompant(BigDecimal.ZERO);
            
            articuloSelected.setDescarticulo(txtDescripcion.getValue());
            articuloSelected.setCodarticulo(txtCodigo.getValue());            
            articuloSelected.setIdarticulo(txtIdArticulo.getValue());

        } catch (DiservWebException ex) {
            MensajeMultilinea.show(ex.getMensaje(), Constants.MENSAJE_TIPO_ERROR);
        }

    }

    public void doReadOnly(Boolean opt) {

        // txtCorreoElectronico.setReadonly(opt);
        txtCodigo.setReadonly(opt);
        txtDescripcion.setReadonly(opt);
       // checkEstadoArticulos.setDisabled(opt);
        //txtMunicipio.setReadonly(opt);
        // txtNombreBodegas.setReadonly(opt);

        // txtTelefono2.setReadonly(opt);
        //txtTelefono.setReadonly(opt);
    }

    private void doEditButton() {
        this.btnCerrar.setVisible(true);
        this.btnEditar.setVisible(true);
        this.btnNuevo.setVisible(true);
        this.btnGuardar.setVisible(false);
        this.btnActualizar.setVisible(false);
    }

    public void onClick$btnGuardar(Event event) {
        try {

            if (getToken().intValue() > 0) {
                loadDataFromTextboxs();
               
                articuloSelected.setIdusuariocrea(userLogin.getUsuario().getIdusuario());
                articuloSelected.setFechacrea(new Date());
                
                responseOperacion = articulosBean.guardarArticulo(articuloSelected);
                if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                    MensajeMultilinea.show(responseOperacion.getMensajeRespuesta() + " Id Articulo:" + responseOperacion.getArticulo().getIdarticulo(), Constants.MENSAJE_TIPO_INFO);
                    articuloSelected = responseOperacion.getArticulo();
                    loadDataFromEntity();
                    doReadOnly(Boolean.TRUE);
                    doEditButton();
                    listaArticulosCtrl.refreshModel(0);
                } else {
                    MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
                }
                setToken(0);
            } else if (getToken().intValue() == 0) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Se intento guardar el mismo bodega dos veces, por seguridad solo se proceso una vez ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.getMessage(), Constants.MENSAJE_TIPO_ERROR);
        }
    }

    public void onClick$btnEditar(Event event) {
        doReadOnly(Boolean.FALSE);
        this.btnActualizar.setVisible(true);
        // this.btnGuardar.setVisible(true);
        this.btnEditar.setVisible(false);
    }

    private void doClose() {
        this.detalleArticuloWindow.onClose();
    }

    public void doClear() {

        // txtCorreoElectronico.setValue(null);
        //txtEncargado.setValue(null);
        txtCodigo.setValue(null);
        txtDescripcion.setValue(null);
        txtIdArticulo.setValue(null);
       // checkEstadoArticulo.setChecked(false);

       // txtMunicipio.setValue(null);
        // txtNombreBodegas.setValue(null);
       // txtTelefono2.setValue(null);
        // txtTelefono.setValue(null);
        // txtNombreBodegas.setFocus(true);
    }

    private void doNew() {
        doClear();
        doReadOnly(Boolean.FALSE);
        this.btnGuardar.setVisible(true);
        this.btnCerrar.setVisible(true);
        this.btnActualizar.setVisible(false);
        //this.checkEstadoArticulos.setChecked(Boolean.FALSE);
        this.btnEditar.setVisible(false);
        this.btnNuevo.setVisible(false);
        loadCombobox();
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
    }

    public void doActualizar() {
        loadDataFromTextboxs();
        try {

            responseOperacion = articulosBean.actualizarArticulo(articuloSelected);
            if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta() + " Id articulo:" + responseOperacion.getArticulo().getIdarticulo(), Constants.MENSAJE_TIPO_INFO);
                articuloSelected = responseOperacion.getArticulo();
                loadDataFromEntity();
                doReadOnly(Boolean.TRUE);
                doEditButton();
                listaArticulosCtrl.refreshModel(Constants.PAGINA_INICIO_DEFAULT);
            } else {
                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
            }
        } catch (Exception bex) {
            bex.printStackTrace();
            logger.severe(_zclass);
            MensajeMultilinea.show(bex.toString(), Constants.MENSAJE_TIPO_ERROR);

        }
//        ListModelList lml = (ListModelList) getLbBodegas().getListModel();
//        if (lml.indexOf(bodega) == -1) {
//            lml.add(bodega);
//        } else {
//            lml.set(lml.indexOf(bodega), bodega);
//        }
        doReadOnly(Boolean.TRUE);
        doEditButton();
    }


}
