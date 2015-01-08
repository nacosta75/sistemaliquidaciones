package sv.com.diserv.web.ui.personas;

import java.math.BigDecimal;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
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
    protected Textbox txtRegistro;
    protected Intbox txtTelefono1;
    protected Intbox txtExt1;
    protected Intbox txtTelefono2;
    protected Intbox txtExt2;
    protected Intbox txtTelefono3;
    protected Intbox txtExt3;
    protected Intbox txtFax;
    protected Checkbox checkCreditoActivo;
    protected Doublebox txtLimiteCredito;
    protected Textbox txtCorreo;
    protected Doublebox txtUltSaldo;
    protected Datebox txtfechaUltSaldo;

    protected Button btnActualizar;
    protected Button btnNuevo;
    protected Button btnEditar;
    protected Button btnGuardar;
     protected Button btnEliminar;
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
        showDetalleProveedores();
    }

    public void showDetalleProveedores() {
        try {
            if (clienteSelected != null) {
                doEditButton();
                loadDataFromEntity();
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
        txtRegistro.setValue(clienteSelected.getNoRegistroFiscal());
       
        if(clienteSelected.getTelefono1()!= null)
            txtTelefono1.setValue(Integer.parseInt(clienteSelected.getTelefono1()));
        
        txtExt1.setValue(clienteSelected.getExt1());
        
        if(clienteSelected.getTelefono2()!= null)
            txtTelefono2.setValue(Integer.parseInt(clienteSelected.getTelefono2()));
        
        txtExt2.setValue(clienteSelected.getExt2());
        
        if(clienteSelected.getTelefono3()!= null)
            txtTelefono3.setValue(Integer.parseInt(clienteSelected.getTelefono3()));
        
        txtExt3.setValue(clienteSelected.getExt3());
        
        if(clienteSelected.getFax()!= null)
            txtFax.setValue(Integer.parseInt(clienteSelected.getFax()));
        
        if (clienteSelected.getCreditoActivo().equals("S"))
        {
          checkCreditoActivo.setChecked(true);
        } else { checkCreditoActivo.setChecked(false);}
        
        txtLimiteCredito.setValue(clienteSelected.getLimiteCredito().doubleValue());
        txtCorreo.setValue(clienteSelected.getCorreo());
        txtUltSaldo.setValue(clienteSelected.getUltSaldo().doubleValue());
        txtfechaUltSaldo.setValue(clienteSelected.getFechaUltSaldo());
        
           
    }

    private void loadDataFromTextboxs() {
        try {
            clienteSelected = new Personas();
            //validamos los campos

            if (StringUtils.isEmpty(txtNombreProveedor.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar  Nombre Proveedor");
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
            
             if (StringUtils.isEmpty(txtRegistro.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar Numero de Registro");
            }

             if (!StringUtils.isEmpty(txtCorreo.getValue()))  {
                
                Pattern pat = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                Matcher mat = pat.matcher(txtCorreo.getValue());
                if (!mat.find()) {
                    throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar Correo Electronico Valido");
                } 
     
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
            clienteSelected.setNoRegistroFiscal(txtRegistro.getValue());
            
            //Debido a que estos campos no son obligatorios de evalua
            // que no esten vacios para setearlos en la entidad
            if(txtTelefono1.getValue() > 0)
                clienteSelected.setTelefono1(txtTelefono1.getValue()+"");
            if(txtExt1.getValue()> 0)
                clienteSelected.setExt1(Integer.parseInt(txtExt1.getValue()+""));
            
            if(txtTelefono2.getValue()> 0)
                clienteSelected.setTelefono2(txtTelefono2.getValue()+"");
            if(txtExt2.getValue()> 0)
                clienteSelected.setExt2(txtExt2.getValue());
            
            if(txtTelefono3.getValue()> 0)
                clienteSelected.setTelefono3(txtTelefono3.getValue()+"");
            if(txtExt3.getValue()> 0)
                clienteSelected.setExt3(txtExt3.getValue());
            
            if(txtFax.getValue()> 0)
                clienteSelected.setFax(txtFax.getValue()+"");
            
            if(txtLimiteCredito.getValue() > 0)
                clienteSelected.setLimiteCredito(new BigDecimal(txtLimiteCredito.getValue()));
            
            if(!StringUtils.isEmpty(txtCorreo.getValue()))
                clienteSelected.setCorreo(txtCorreo.getValue());
            
            if(txtUltSaldo.getValue() > 0)
                clienteSelected.setUltSaldo(new BigDecimal(txtUltSaldo.getValue()));
            
            if(txtfechaUltSaldo.getValue() != null)
                clienteSelected.setFechaUltSaldo(txtfechaUltSaldo.getValue());
            
            clienteSelected.setIdtipopersona(new TiposPersona(3));
            clienteSelected.setIdempresa(new Empresas(1));
            clienteSelected.setIdsucursal(new Sucursales(1));
            clienteSelected.setIdusuariocrea(userLogin.getUsuario().getIdusuario());
                    
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
                    doEditButton();
                    listaProveedoresCtrl.refreshModel(0);
                } else {
                //    MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
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
                doEditButton();
                doClose();
                listaProveedoresCtrl.refreshModel(0);
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
        this.detalleProveedorWindow.onClose();
    }

    private void doNew() {
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

    public void doActualizar() {
        loadDataFromTextboxs();
        try {
            
            responseOperacion = personaBean.actualizarPersona(clienteSelected);
            if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta() + " Id persona:" + responseOperacion.getPersona().getIdpersona(), Constants.MENSAJE_TIPO_INFO);
                clienteSelected = responseOperacion.getPersona();
                loadDataFromEntity();
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
        doEditButton();
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
