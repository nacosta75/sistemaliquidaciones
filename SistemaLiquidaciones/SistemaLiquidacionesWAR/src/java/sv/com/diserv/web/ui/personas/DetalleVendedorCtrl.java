package sv.com.diserv.web.ui.personas;

import sv.com.diserv.web.ui.personas.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.dto.CatalogoDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesBodegaVendedorDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesPersonaDTO;
import sv.com.diserv.liquidaciones.ejb.BodegaVendedorBeanLocal;
import sv.com.diserv.liquidaciones.ejb.CatalogosBeanLocal;
import sv.com.diserv.liquidaciones.ejb.PersonasBeanLocal;
import sv.com.diserv.liquidaciones.entity.BodegaVendedor;
import sv.com.diserv.liquidaciones.entity.Bodegas;
import sv.com.diserv.liquidaciones.entity.Empresas;
import sv.com.diserv.liquidaciones.entity.Personas;
import sv.com.diserv.liquidaciones.entity.Sucursales;
import sv.com.diserv.liquidaciones.entity.TiposPersona;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.exception.DiservWebException;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.web.ui.personas.rendered.CatalogoItemRenderer;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

public class DetalleVendedorCtrl extends BaseController {

    private static final Logger logger = Logger.getLogger(DetalleVendedorCtrl.class.getName());
    private static final long serialVersionUID = -546886879998950467L;
    protected Window detalleVendedorWindow;
    protected Intbox txtIdVendedor;
    protected Intbox txtIdBodega;
    protected Textbox txtNombreVendedor;
    protected Textbox txtCallePasaje;
    protected Textbox txtColonia;
    protected Textbox txtNIT;
    protected Textbox txtTelefono1;
    protected Textbox txtExt1;
    protected Textbox txtTelefono2;
    protected Textbox txtExt2;
    protected Textbox txtTelefono3;
    protected Textbox txtExt3;
    protected Textbox txtFax;
    protected Checkbox checkCreditoActivo;
    protected Doublebox txtLimiteCredito;
    protected Textbox txtCorreo;
    protected Textbox txtBodega;
    protected Doublebox txtUltSaldo;
    protected Datebox txtfechaUltSaldo;
    protected Combobox cmbEstadoCivil;
    protected Combobox cmbBodegasAsignables;

    protected Button btnActualizar;
    protected Button btnNuevo;
    protected Button btnEditar;
    protected Button btnGuardar;
    protected Button btnEliminar;
    protected Button btnCerrar;
    protected Button btnAsignarBodega;
    private Personas clienteSelected;
    private ListaVendedorCtrl listaVendedoresCtrl;
    private transient Integer token;
    private PersonasBeanLocal personaBean;
    private CatalogosBeanLocal catalogosBeanLocal;
    private BodegaVendedorBeanLocal bodegaVendedorBean;
    private ServiceLocator serviceLocator;
    private OperacionesPersonaDTO responseOperacion;
    private OperacionesBodegaVendedorDTO responseOperacionBodega;
    private List<Personas> listaVendedoresLike;
    
    private static List<String> colors = new ArrayList<String>();
     
     static{
        colors.add("blue");
        colors.add("black");
        colors.add("white");
                
    }
     
    public static final List<String> getColors() {
        return new ArrayList<String>(colors);
    }

    public DetalleVendedorCtrl() {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            personaBean = serviceLocator.getService(Constants.JNDI_PERSONA_BEAN);
            catalogosBeanLocal = serviceLocator.getService(Constants.JNDI_CATALOGO_BEAN);
            bodegaVendedorBean = serviceLocator.getService(Constants.JNDI_BODEGAVENDEDOR_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$detalleVendedorWindow(Event event) throws Exception {
        doOnCreateCommon(this.detalleVendedorWindow, event);
        MensajeMultilinea.doSetTemplate();
        if (this.args.containsKey("clienteSelected")) {
            clienteSelected = ((Personas) this.args.get("clienteSelected"));
            setVendedorSelected(clienteSelected);
        }
        if (this.args.containsKey("token")) {
            this.token = ((Integer) this.args.get("token"));
            setToken(this.token);
        } else {
            setToken(Integer.valueOf(0));
        }
        if (this.args.containsKey("listaVendedorCtrl")) {
            listaVendedoresCtrl = ((ListaVendedorCtrl) this.args.get("listaVendedorCtrl"));
        }
        checkPermisos();
        showDetalleVendedores();
        loadCombobox();
    }

    public void showDetalleVendedores() {
        try {
            if (clienteSelected != null) {
                doReadOnly(Boolean.TRUE);
                doEditButton();
                loadDataFromEntity();
            } else {
                doNew();
            }
            detalleVendedorWindow.doModal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * cargamos los textboxs desde entity
     */
    private void loadDataFromEntity() {

        try {
            //    protected ComboBox cmbEstadoCivil;
            txtIdVendedor.setValue(clienteSelected.getIdpersona());
            txtNombreVendedor.setText(clienteSelected.getNombre());
            txtCallePasaje.setValue(clienteSelected.getCalleOPasaje());
            txtColonia.setValue(clienteSelected.getColonia());
            txtNIT.setValue(clienteSelected.getNit());
            txtTelefono1.setValue(clienteSelected.getTelefono1());
            txtExt1.setValue(clienteSelected.getExt1()+"");
            txtCorreo.setValue(clienteSelected.getCorreo());
            
            if(clienteSelected != null &&  clienteSelected.getIdpersona()!= null){
                Bodegas bodega = bodegaVendedorBean.findBodegaAsignada(clienteSelected.getIdpersona());
                if(bodega != null && bodega.getIdbodega() != null){
                    txtBodega.setValue(bodega.getNombre());
                    txtIdBodega.setValue(bodega.getIdbodega());
                 }
            }
            
            loadCombobox();
            
        } catch (DiservBusinessException ex) {
            Logger.getLogger(DetalleVendedorCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    private void loadCombobox(){
        List<CatalogoDTO> listaCatalogo = new ArrayList<CatalogoDTO>();
        List<CatalogoDTO> listaCatalogoBodegas = new ArrayList<CatalogoDTO>();
        List<Bodegas> listaBodegas;
            try {
                
                listaCatalogo = catalogosBeanLocal.loadAllElementosCatalogo(Constants.idsEstadosCiviles,Constants.estadosCiviles);
                ListModelList modelo = new ListModelList(listaCatalogo);
                if(clienteSelected !=null && clienteSelected.getEstadoCivil() != null){
                    modelo.addSelection(catalogosBeanLocal.findCatalogoBySelected(listaCatalogo,Integer.parseInt(clienteSelected.getEstadoCivil())));
                }
                cmbEstadoCivil.setModel(modelo);
                cmbEstadoCivil.setItemRenderer(new CatalogoItemRenderer());
                
                listaBodegas = bodegaVendedorBean.loadAllBodegasAsignables();
                List<Object> objectList = new ArrayList<Object>(listaBodegas);
                listaCatalogoBodegas = catalogosBeanLocal.loadAllElementosCatalogo(objectList, "idpersona", "nombre");
               
                ListModelList modelobodegas = new ListModelList(listaCatalogoBodegas);
//                if(clienteSelected !=null && clienteSelected.getEstadoCivil() != null){
//                    modelo.addSelection(catalogosBeanLocal.findCatalogoBySelected(listaCatalogo,Integer.parseInt(clienteSelected.getEstadoCivil())));
//                }
                cmbBodegasAsignables.setModel(modelobodegas);
                cmbBodegasAsignables.setItemRenderer(new CatalogoItemRenderer());
               
                
            } catch (DiservBusinessException ex) {
                Logger.getLogger(DetalleClienteCtrl.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private void loadDataFromTextboxs() {
        try {
            clienteSelected = new Personas();
            //validamos los campos

            if (StringUtils.isEmpty(txtNombreVendedor.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar  Nombre Vendedor");
            }
            
            if (StringUtils.isEmpty(txtTelefono1.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar   Numero Telefono para cliente");
            }
             if (!StringUtils.isEmpty(txtCorreo.getValue())) {
                
                Pattern pat = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                Matcher mat = pat.matcher(txtCorreo.getValue());
                if (!mat.find()) {
                    throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar Correo Electronico Valido");
                } 
     
            }
            
            clienteSelected.setIdpersona(txtIdVendedor.getValue());
            clienteSelected.setNombre(txtNombreVendedor.getValue());
            clienteSelected.setTelefono1(txtTelefono1.getValue());
            
            //Debido a que estos campos no son obligatorios de evalua
            // que no esten vacios para setearlos en la entidad
            if(!StringUtils.isEmpty(txtCallePasaje.getValue()))
                clienteSelected.setCalleOPasaje(txtCallePasaje.getValue());
            if(!StringUtils.isEmpty(txtColonia.getValue()))
                clienteSelected.setColonia(txtColonia.getValue());
            if(!StringUtils.isEmpty(txtNIT.getValue()))
                clienteSelected.setNit(txtNIT.getValue());     
            
            if(!StringUtils.isEmpty(txtExt1.getValue()))
                clienteSelected.setExt1(Integer.parseInt(txtExt1.getValue()));
            if(!StringUtils.isEmpty(txtCorreo.getValue()))
                clienteSelected.setCorreo(txtCorreo.getValue());
            
            
            clienteSelected.setEstadoCivil(cmbEstadoCivil.getSelectedItem().getValue()+"");            
            clienteSelected.setIdtipopersona(new TiposPersona(2));
            clienteSelected.setIdempresa(new Empresas(1));
            clienteSelected.setIdsucursal(new Sucursales(1));
            clienteSelected.setIdusuariocrea(1);
                    
        } catch (DiservWebException ex) {
            MensajeMultilinea.show(ex.getMensaje(), Constants.MENSAJE_TIPO_ERROR);
        }

    }

    public void onClick$btnGuardar(Event event) {
        try {
            if (getToken().intValue() > 0) {
                loadDataFromTextboxs();
                responseOperacion = personaBean.guardarPersona(clienteSelected);
                if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                    MensajeMultilinea.show(responseOperacion.getMensajeRespuesta() + " Id persona:" + responseOperacion.getPersona().getIdpersona(), Constants.MENSAJE_TIPO_INFO);
                    clienteSelected = responseOperacion.getPersona();
                    loadDataFromEntity();
                    doReadOnly(Boolean.TRUE);
                    doEditButton();
                    listaVendedoresCtrl.refreshModel(0);
                } else {
                   // MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
                }
                setToken(0);
            } else if (getToken().intValue() == 0) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Se intento guardar la misma persona dos veces, por seguridad solo se proceso una vez ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.getMessage(), Constants.MENSAJE_TIPO_ERROR);
        }
    }

    public void onClick$btnEditar(Event event) {
        doReadOnly(Boolean.FALSE);
        this.btnActualizar.setVisible(true);
        this.btnEliminar.setVisible(true);
        this.btnEditar.setVisible(false);
    }

    public void onClick$btnEliminar(Event event) {
       loadDataFromTextboxs();
            
     try {
            
           responseOperacion = personaBean.eliminarPersona(clienteSelected);
            if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_INFO);
                doReadOnly(Boolean.TRUE);
                doEditButton();
                doClose();
                listaVendedoresCtrl.refreshModel(0);
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

      public void onClick$btnAsignarBodega(Event event) {
        try {
            if (getToken().intValue() > 0) {
                
                Bodegas bodega = bodegaVendedorBean.findBodegaByID((Integer) cmbBodegasAsignables.getSelectedItem().getValue());
                responseOperacionBodega = bodegaVendedorBean.asignarBodega(bodega);
                if (responseOperacionBodega.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                    MensajeMultilinea.show(responseOperacionBodega.getMensajeRespuesta() + " Id Bodega:" + responseOperacionBodega.getBodegaVendedor().getIdbodega(), Constants.MENSAJE_TIPO_INFO);
                     txtBodega.setValue(bodega.getNombre());
                     txtIdBodega.setValue(bodega.getIdbodega());
                } else {
                   // MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
                }
                setToken(0);
            } else if (getToken().intValue() == 0) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Se intento guardar la misma persona dos veces, por seguridad solo se proceso una vez ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.getMessage(), Constants.MENSAJE_TIPO_ERROR);
        }
    }
      
    public void onClick$btnDesasignarBodega(Event event) {
        try {
              BodegaVendedor bodegaVendedor = bodegaVendedorBean.findBodegaVendedorByIdVendedorBodega(txtIdVendedor.getValue(), txtIdBodega.getValue());
                responseOperacionBodega = bodegaVendedorBean.desasignarBodega(bodegaVendedor);
                if (responseOperacionBodega.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                    MensajeMultilinea.show(responseOperacionBodega.getMensajeRespuesta(), Constants.MENSAJE_TIPO_INFO);
                     txtBodega.setValue("No hay bodega Asignada");
                     txtIdBodega.setValue(0);
                } else {
                   // MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
                }
               
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.getMessage(), Constants.MENSAJE_TIPO_ERROR);
        }
    }
    
   
    private void doClose() {
        this.detalleVendedorWindow.onClose();
    }

    public void onDoubleClicked(Event event) throws Exception {
    logger.info("[onDoubleClicked]");
    }

    private void doNew() {
        doClear();
        doReadOnly(Boolean.FALSE);
        this.btnGuardar.setVisible(true);
        this.btnCerrar.setVisible(true);
        this.btnActualizar.setVisible(false);
        this.btnEliminar.setVisible(false);
        this.checkCreditoActivo.setChecked(Boolean.FALSE);
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

    public void doReadOnly(Boolean opt) {
    }

    public void doClear() {

    }

    public void doActualizar() {
        loadDataFromTextboxs();
        try {
            
            responseOperacion = personaBean.actualizarPersona(clienteSelected);
            if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta() + " Id persona:" + responseOperacion.getPersona().getIdpersona(), Constants.MENSAJE_TIPO_INFO);
                clienteSelected = responseOperacion.getPersona();
                loadDataFromEntity();
                doReadOnly(Boolean.TRUE);
                doEditButton();
                listaVendedoresCtrl.refreshModel(Constants.PAGINA_INICIO_DEFAULT);
            } else {
                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
            }
        } catch (Exception bex) {
            bex.printStackTrace();
            logger.severe(_zclass);
            MensajeMultilinea.show(bex.toString(), Constants.MENSAJE_TIPO_ERROR);

        }

        doReadOnly(Boolean.TRUE);
        doEditButton();
    }

    private void checkPermisos() {
    }

    public Integer getToken() {
        return this.token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    public Personas getVendedorSelected() {
        return clienteSelected;
    }

    public void setVendedorSelected(Personas clienteSelected) {
        this.clienteSelected = clienteSelected;
    }

    public ListaVendedorCtrl getListaVendedoresCtrl() {
        return listaVendedoresCtrl;
    }

    public void setListaVendedoresCtrl(ListaVendedorCtrl listaVendedoresCtrl) {
        this.listaVendedoresCtrl = listaVendedoresCtrl;
    }

    public List<Personas> getListaVendedoresLike() {
        return listaVendedoresLike;
    }

    public void setListaVendedoresLike(List<Personas> listaVendedoresLike) {
        this.listaVendedoresLike = listaVendedoresLike;
    }
}
