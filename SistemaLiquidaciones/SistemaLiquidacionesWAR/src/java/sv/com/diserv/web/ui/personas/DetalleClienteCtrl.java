package sv.com.diserv.web.ui.personas;

import java.math.BigDecimal;
import sv.com.diserv.web.ui.personas.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.SimpleListModel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ListModelList;
import sv.com.diserv.liquidaciones.dto.CatalogoDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesPersonaDTO;
import sv.com.diserv.liquidaciones.ejb.CatalogosBeanLocal;
import sv.com.diserv.liquidaciones.ejb.PersonasBeanLocal;
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

public class DetalleClienteCtrl extends BaseController {

    private static final Logger logger = Logger.getLogger(DetalleClienteCtrl.class.getName());
    private static final long serialVersionUID = -546886879998950467L;
    protected Window detalleClienteWindow;
    protected Intbox txtIdClientes;
    protected Textbox txtNombreClientes;
    protected Textbox txtCallePasaje;
    protected Textbox txtColonia;
    protected Textbox txtNIT;
    protected Textbox txtRegistro;
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
    protected Doublebox txtUltSaldo;
    protected Datebox txtfechaUltSaldo;
    protected Combobox cmbEstadoCivil;

    protected Button btnActualizar;
    protected Button btnNuevo;
    protected Button btnEditar;
    protected Button btnGuardar;
     protected Button btnEliminar;
    protected Button btnCerrar;
    private Personas clienteSelected;
    private ListaClienteCtrl listaClientesCtrl;
    private transient Integer token;
    private PersonasBeanLocal personaBean;
    private CatalogosBeanLocal catalogosBeanLocal;
    private ServiceLocator serviceLocator;
    private OperacionesPersonaDTO responseOperacion;
    private List<Personas> listaClientesLike;

    private static List<String> colors = new ArrayList<String>();
     
     static{
        colors.add("blue");
        colors.add("black");
        colors.add("white");
                
    }
     
    public static final List<String> getColors() {
        return new ArrayList<String>(colors);
    }

    public DetalleClienteCtrl() {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            personaBean = serviceLocator.getService(Constants.JNDI_PERSONA_BEAN);
            catalogosBeanLocal = serviceLocator.getService(Constants.JNDI_CATALOGO_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$detalleClienteWindow(Event event) throws Exception {
        doOnCreateCommon(this.detalleClienteWindow, event);
        MensajeMultilinea.doSetTemplate();
        if (this.args.containsKey("clienteSelected")) {
            clienteSelected = ((Personas) this.args.get("clienteSelected"));
            setClienteSelected(clienteSelected);
        }
        if (this.args.containsKey("token")) {
            this.token = ((Integer) this.args.get("token"));
            setToken(this.token);
        } else {
            setToken(Integer.valueOf(0));
        }
        if (this.args.containsKey("listaClienteCtrl")) {
            listaClientesCtrl = ((ListaClienteCtrl) this.args.get("listaClienteCtrl"));
        }
        checkPermisos();
        showDetalleClientes();
        loadCombobox();

    }

    public void showDetalleClientes() {
        try {
            if (clienteSelected != null) {
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
            detalleClienteWindow.doModal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * cargamos los textboxs desde entity
     */
    private void loadDataFromEntity() {

//    protected ComboBox cmbEstadoCivil;
    
        txtIdClientes.setValue(clienteSelected.getIdpersona());
        txtNombreClientes.setText(clienteSelected.getNombre());
        txtCallePasaje.setValue(clienteSelected.getCalleOPasaje());
        txtColonia.setValue(clienteSelected.getColonia());
        txtNIT.setValue(clienteSelected.getNit());
        txtRegistro.setValue(clienteSelected.getNoRegistroFiscal());
        txtTelefono1.setValue(clienteSelected.getTelefono1());
        txtExt1.setValue(clienteSelected.getExt1()+"");
        txtTelefono2.setValue(clienteSelected.getTelefono2());
        txtExt2.setValue(clienteSelected.getExt2()+"");
        txtTelefono3.setValue(clienteSelected.getTelefono3());
        txtExt3.setValue(clienteSelected.getExt3()+"");
        txtFax.setValue(clienteSelected.getFax());
        
        if (clienteSelected.getCreditoActivo().equals("S"))
        {
          checkCreditoActivo.setChecked(true);
        } else { checkCreditoActivo.setChecked(false);}
        
        txtLimiteCredito.setValue(clienteSelected.getLimiteCredito().doubleValue());
        txtCorreo.setValue(clienteSelected.getCorreo());
        txtUltSaldo.setValue(clienteSelected.getUltSaldo().doubleValue());
        txtfechaUltSaldo.setValue(clienteSelected.getFechaUltSaldo());
        loadCombobox();
                    
    }

    private void loadCombobox(){
        List<CatalogoDTO> listaCatalogo = new ArrayList<CatalogoDTO>();
            try {
                listaCatalogo = catalogosBeanLocal.loadAllElementosCatalogo(Constants.idsEstadosCiviles,Constants.estadosCiviles);
                ListModelList modelo = new ListModelList(listaCatalogo);
                if(clienteSelected !=null && clienteSelected.getEstadoCivil() != null){
                    modelo.addSelection(catalogosBeanLocal.findCatalogoBySelected(listaCatalogo,Integer.parseInt(clienteSelected.getEstadoCivil())));
                }
                cmbEstadoCivil.setModel(modelo);
                cmbEstadoCivil.setItemRenderer(new CatalogoItemRenderer());
                
            } catch (DiservBusinessException ex) {
                Logger.getLogger(DetalleClienteCtrl.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private void loadDataFromTextboxs() {
        try {
            clienteSelected = new Personas();
            //validamos los campos

            if (StringUtils.isEmpty(txtNombreClientes.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar Nombre Cliente");
            }

            if (StringUtils.isEmpty(txtCallePasaje.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar Calle o Pasaje");
            }

            if (StringUtils.isEmpty(txtColonia.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar Colonia");
            }
            
            if (StringUtils.isEmpty(txtNIT.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar NIT");
            }
            
//            if (StringUtils.isEmpty(txtRegistro.getValue())) {
//                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar Numero de Registro");
//            }
//            
//            if (StringUtils.isEmpty(txtTelefono1.getValue())) {
//                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar Numero Telefono para cliente");
//            }
//            
//            if (txtLimiteCredito.getValue() == 0) {
//                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar Limite de Credito");
//            }
//            
//            if (txtfechaUltSaldo.getValue() == null) {
//                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar Fecha Ultimo Saldo");
//            }
            if(!StringUtils.isEmpty(txtCorreo.getValue())) {
                
                Pattern pat = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                Matcher mat = pat.matcher(txtCorreo.getValue());
                if (!mat.find()) {
                    throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar Correo Electronico Valido");
                } 
     
            }
            
//            if (txtUltSaldo.getValue() == 0) {
//                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar Ultimo Saldo");
//            }
//            
//            if (StringUtils.isEmpty(txtCorreo.getValue())) {
//                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar Correo Electronico");
//            }

            if (checkCreditoActivo.isChecked()) {
               clienteSelected.setCreditoActivo("S");
            }
            else
            {
               clienteSelected.setCreditoActivo("N");
            }
            clienteSelected.setIdpersona(txtIdClientes.getValue());
            clienteSelected.setNombre(txtNombreClientes.getValue());
            clienteSelected.setCalleOPasaje(txtCallePasaje.getValue());
            clienteSelected.setColonia(txtColonia.getValue());
            clienteSelected.setNit(txtNIT.getValue());
            
            //Debido a que estos campos no son obligatorios de evalua
            // que no esten vacios para setearlos en la entidad
            if(!StringUtils.isEmpty(txtRegistro.getValue()))
                clienteSelected.setNoRegistroFiscal(txtRegistro.getValue());
            
            if(!StringUtils.isEmpty(txtTelefono1.getValue()))
                clienteSelected.setTelefono1(txtTelefono1.getValue());
            if(!StringUtils.isEmpty(txtExt1.getValue()))
                clienteSelected.setExt1(Integer.parseInt(txtExt1.getValue()));
            
            if(!StringUtils.isEmpty(txtTelefono2.getValue()))
                clienteSelected.setTelefono2(txtTelefono2.getValue());
            if(!StringUtils.isEmpty(txtExt2.getValue()))
                clienteSelected.setExt2(Integer.parseInt(txtExt2.getValue()));
            
            if(!StringUtils.isEmpty(txtTelefono3.getValue()))
                clienteSelected.setTelefono3(txtTelefono3.getValue());
            if(!StringUtils.isEmpty(txtExt3.getValue()))
                clienteSelected.setExt3(Integer.parseInt(txtExt3.getValue()));
            
            if(!StringUtils.isEmpty(txtFax.getValue()))
                clienteSelected.setFax(txtFax.getValue());
            
            if(txtLimiteCredito.getValue() > 0)
                clienteSelected.setLimiteCredito(new BigDecimal(txtLimiteCredito.getValue()));
            
            if(!StringUtils.isEmpty(txtCorreo.getValue()))
                clienteSelected.setCorreo(txtCorreo.getValue());
            
            if(txtUltSaldo.getValue() > 0)
                clienteSelected.setUltSaldo(new BigDecimal(txtUltSaldo.getValue()));
            
            if(txtfechaUltSaldo.getValue() != null)
                clienteSelected.setFechaUltSaldo(txtfechaUltSaldo.getValue());

            clienteSelected.setEstadoCivil(cmbEstadoCivil.getSelectedItem().getValue()+"");
            
            clienteSelected.setIdtipopersona(new TiposPersona(1));
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
                    listaClientesCtrl.refreshModel(0);
                } else {
//                    MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
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
//                clienteSelected = responseOperacion.getPersona();
//                loadDataFromEntity();
                doReadOnly(Boolean.TRUE);
                doEditButton();
                doClose();
                listaClientesCtrl.refreshModel(0);
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

    public void onBlur$txtNombreClientes(Event event) throws DiservWebException, DiservBusinessException {
//        try {
//            if (StringUtils.isEmpty(txtNombreClientes.getValue())) {
//                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar nombre cliente");
//            }
//            listaClientesLike = personaBean.loadAllPersonasByLike(txtNombreClientes.getValue());
//            if (listaClientesLike.size() > 0) {
//                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Ya existe un cliente con un nombre similar:\n" + listaClientesLike.get(0).getIdpersona()+ "\nId Persona:" + listaClientesLike.get(0).getIdpersona());
//            }
//        } catch (DiservWebException web) {
//            MensajeMultilinea.show(web.getMensaje(), Constants.MENSAJE_TIPO_ALERTA);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }

    private void doClose() {
        this.detalleClienteWindow.onClose();
    }

    public void onDoubleClicked(Event event) throws Exception {
    logger.info("[onDoubleClicked]");
//        Listitem item = this.listBoxOrdentrabajoBodegas.getSelectedItem();
//
//        if (item != null) {
//            Ordentrabajo ordentrabajo = (Ordentrabajo) item.getAttribute("data");
//
//            HashMap map = new HashMap();
//            map.put("ordentrabajo", ordentrabajo);
//
//            map.put("lbOrdentabajo", this.listBoxOrdentrabajoBodegas);
//            map.put("ordentrabajoCtrl", getOrdencontroller());
//
//            Executions.createComponents("/html/ordentrabajo/detalleordentrabajo.zul",
//                    null, map);
//        }
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

//       // txtCorreoElectronico.setReadonly(opt);
//        txtEncargado.setReadonly(opt);
//        txtDireccion.setReadonly(opt);
//        //txtMunicipio.setReadonly(opt);
//        txtNombreBodegas.setReadonly(opt);
//
//       // txtTelefono2.setReadonly(opt);
//        txtTelefono.setReadonly(opt);
    }

    public void doClear() {

       // txtCorreoElectronico.setValue(null);
//        txtEncargado.setValue(null);
//        txtDireccion.setValue(null);
//        txtIdBodegas.setValue(null);
//
//       // txtMunicipio.setValue(null);
//        txtNombreBodegas.setValue(null);
//
//       // txtTelefono2.setValue(null);
//        txtTelefono.setValue(null);
//        txtNombreBodegas.setFocus(true);
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
                listaClientesCtrl.refreshModel(Constants.PAGINA_INICIO_DEFAULT);
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

    private void checkPermisos() {
//        this.btnEliminar.setVisible(SecurityUtil.isAnyGranted(
//                "ROLE_ADMINISTRADOR"));
//        this.btnActualizar.setVisible(SecurityUtil.isAnyGranted(
//                "ROLE_ADMINISTRADOR"));
//        this.btnEditar.setVisible(SecurityUtil.isAnyGranted(
//                "ROLE_ADMINISTRADOR,VERIFICAR_FACTURA_COBRO"));
//        this.btnNuevo.setVisible(SecurityUtil.isAnyGranted(
//                "ROLE_ADMINISTRADOR,AGREGAR_CLIENTE"));
    }

    public Integer getToken() {
        return this.token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    public Personas getClienteSelected() {
        return clienteSelected;
    }

    public void setClienteSelected(Personas clienteSelected) {
        this.clienteSelected = clienteSelected;
    }

    public ListaClienteCtrl getListaClientesCtrl() {
        return listaClientesCtrl;
    }

    public void setListaClientesCtrl(ListaClienteCtrl listaClientesCtrl) {
        this.listaClientesCtrl = listaClientesCtrl;
    }

    public List<Personas> getListaClientesLike() {
        return listaClientesLike;
    }

    public void setListaClientesLike(List<Personas> listaClientesLike) {
        this.listaClientesLike = listaClientesLike;
    }
}
