package sv.com.diserv.web.ui.personas;

import java.math.BigDecimal;
import sv.com.diserv.web.ui.personas.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.biff.drawing.ComboBox;
import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.dto.OperacionesPersonaDTO;
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
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

public class DetalleProveedorCtrl extends BaseController {

    private static final Logger logger = Logger.getLogger(DetalleProveedorCtrl.class.getName());
    private static final long serialVersionUID = -546886879998950467L;
    protected Window detalleProveedorWindow;
    protected Intbox txtIdProveedor;
    protected Textbox txtNombreProveedor;
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
    protected Textbox txtLimiteCredito;
    protected Textbox txtCorreo;
    protected Textbox txtUltSaldo;
    protected Textbox txtfechaUltSaldo;
    protected ComboBox cmbEstadoCivil;

    protected Button btnActualizar;
    protected Button btnNuevo;
    protected Button btnEditar;
    protected Button btnGuardar;
    protected Button btnCerrar;
    private Personas clienteSelected;
    private ListaProveedorCtrl listaProveedoresCtrl;
    private transient Integer token;
    private PersonasBeanLocal personaBean;
    private ServiceLocator serviceLocator;
    private OperacionesPersonaDTO responseOperacion;
    private List<Personas> listaProveedoresLike;

    private static List<String> colors = new ArrayList<String>();
     
     static{
        colors.add("blue");
        colors.add("black");
        colors.add("white");
                
    }
     
    public static final List<String> getColors() {
        return new ArrayList<String>(colors);
    }

    public DetalleProveedorCtrl() {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            personaBean = serviceLocator.getService(Constants.JNDI_PERSONA_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$detalleProveedorWindow(Event event) throws Exception {
        doOnCreateCommon(this.detalleProveedorWindow, event);
        MensajeMultilinea.doSetTemplate();
        if (this.args.containsKey("clienteSelected")) {
            clienteSelected = ((Personas) this.args.get("clienteSelected"));
            setProveedorSelected(clienteSelected);
        }
        if (this.args.containsKey("token")) {
            this.token = ((Integer) this.args.get("token"));
            setToken(this.token);
        } else {
            setToken(Integer.valueOf(0));
        }
        if (this.args.containsKey("listaProveedorCtrl")) {
            listaProveedoresCtrl = ((ListaProveedorCtrl) this.args.get("listaProveedorCtrl"));
        }
        checkPermisos();
        showDetalleProveedores();
    }

    public void showDetalleProveedores() {
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
            detalleProveedorWindow.doModal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * cargamos los textboxs desde entity
     */
    private void loadDataFromEntity() {

//    protected ComboBox cmbEstadoCivil;
    
        txtIdProveedor.setValue(clienteSelected.getIdpersona());
        txtNombreProveedor.setText(clienteSelected.getNombre());
        txtCallePasaje.setValue(clienteSelected.getCalleOPasaje());
        txtColonia.setValue(clienteSelected.getColonia());
        txtNIT.setValue(clienteSelected.getNit());
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
        
        txtLimiteCredito.setValue(clienteSelected.getLimiteCredito()+"");
        txtCorreo.setValue(clienteSelected.getCorreo());
        txtUltSaldo.setValue(clienteSelected.getUltSaldo()+"");
        txtfechaUltSaldo.setValue(clienteSelected.getFechaUltSaldo()+"");
        
           
    }

    private void loadDataFromTextboxs() {
        try {
            Personas clienteSelected2 = clienteSelected;
            clienteSelected = new Personas();
            //validamos los campos

            if (StringUtils.isEmpty(txtNombreProveedor.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar  Nombre Proveedor");
            }

            if (StringUtils.isEmpty(txtTelefono1.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar   Numero Telefono para cliente");
            }
            
            
            if (checkCreditoActivo.isChecked()) {
               clienteSelected.setCreditoActivo("S");
            }
            else
            {
               clienteSelected.setCreditoActivo("N");
            }
            clienteSelected.setIdpersona(txtIdProveedor.getValue());
            clienteSelected.setNombre(txtNombreProveedor.getValue());
            clienteSelected.setCalleOPasaje(txtCallePasaje.getValue());
            clienteSelected.setColonia(txtColonia.getValue());
            clienteSelected.setNit(txtNIT.getValue());
            clienteSelected.setTelefono1(txtTelefono1.getValue());
            clienteSelected.setExt1(Integer.parseInt(txtExt1.getValue()));
            clienteSelected.setTelefono2(txtTelefono2.getValue());
            clienteSelected.setExt2(Integer.parseInt(txtExt2.getValue()));
            clienteSelected.setTelefono3(txtTelefono3.getValue());
            clienteSelected.setExt3(Integer.parseInt(txtExt3.getValue()));
            clienteSelected.setFax(txtFax.getValue());
            clienteSelected.setLimiteCredito(new BigDecimal(txtLimiteCredito.getValue()));
            clienteSelected.setCorreo(txtCorreo.getValue());
            clienteSelected.setUltSaldo(new BigDecimal(txtUltSaldo.getValue()));
            clienteSelected.setFechaUltSaldo(new Date(txtfechaUltSaldo.getValue()));
//            clienteSelected.setEstadoCivil(cmbEstadoCivil.toString());
            
            clienteSelected.setIdtipopersona(new TiposPersona(3));
            clienteSelected.setIdempresa(new Empresas(1));
            clienteSelected.setIdsucursal(new Sucursales(1));
            clienteSelected.setIdusuariocrea(1);
            clienteSelected.setIdpersona(4);
                    
            
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
                    listaProveedoresCtrl.refreshModel(0);
                } else {
                    MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
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
        this.btnEditar.setVisible(false);
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

    public void onBlur$txtNombreProveedores(Event event) throws DiservWebException, DiservBusinessException {
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
        this.detalleProveedorWindow.onClose();
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
                listaProveedoresCtrl.refreshModel(Constants.PAGINA_INICIO_DEFAULT);
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

    public Personas getProveedorSelected() {
        return clienteSelected;
    }

    public void setProveedorSelected(Personas clienteSelected) {
        this.clienteSelected = clienteSelected;
    }

    public ListaProveedorCtrl getListaProveedoresCtrl() {
        return listaProveedoresCtrl;
    }

    public void setListaProveedoresCtrl(ListaProveedorCtrl listaProveedoresCtrl) {
        this.listaProveedoresCtrl = listaProveedoresCtrl;
    }

    public List<Personas> getListaProveedoresLike() {
        return listaProveedoresLike;
    }

    public void setListaProveedoresLike(List<Personas> listaProveedoresLike) {
        this.listaProveedoresLike = listaProveedoresLike;
    }
}
