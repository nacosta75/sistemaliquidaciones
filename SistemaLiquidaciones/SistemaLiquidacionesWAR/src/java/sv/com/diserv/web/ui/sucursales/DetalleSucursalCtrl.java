/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.web.ui.sucursales;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.dto.OperacionesSucursalDTO;
import sv.com.diserv.liquidaciones.ejb.SucursalesBeanLocal;
import sv.com.diserv.liquidaciones.entity.Sucursales;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.exception.DiservWebException;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

/**
 *
 * @author abraham.acosta
 */
public class DetalleSucursalCtrl extends BaseController {
    
     private static final Logger logger = Logger.getLogger(DetalleSucursalCtrl.class.getName());
     private static final long serialVersionUID = -546886879998950446L;
     
     protected Window detalleSucursalWindow;
     protected Intbox txtIdSucursal;
     protected Textbox txtDescSucursal;
     protected Textbox txtDireccion;
     protected Textbox txtEncargado;
     protected Textbox txtTelefono;
     protected Checkbox checkEstadoSucursal;
     
     protected Button btnActualizar;
    protected Button btnNuevo;
    protected Button btnEditar;
    protected Button btnGuardar;
    protected Button btnCerrar;
    private Sucursales sucursalSelected;
    private ListaSucursalCtrl listaSucursalCtrl;
    private transient Integer token;
    private SucursalesBeanLocal sucursalBean;
    private ServiceLocator serviceLocator;
    private OperacionesSucursalDTO responseOperacion;
    private List<Sucursales> listaSucursalesLike;
    
  
    public DetalleSucursalCtrl() {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            sucursalBean = serviceLocator.getService(Constants.JNDI_SUCURSAL_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$detalleSucursalWindow(Event event) throws Exception {
        doOnCreateCommon(this.detalleSucursalWindow, event);
        MensajeMultilinea.doSetTemplate();
        if (this.args.containsKey("sucursalSelected")) {
            sucursalSelected = ((Sucursales) this.args.get("sucursalSelected"));
            setSucursalsSelected(sucursalSelected);
        }
        if (this.args.containsKey("token")) {
            this.token = ((Integer) this.args.get("token"));
            setToken(this.token);
        } else {
            setToken(Integer.valueOf(0));
        }
        if (this.args.containsKey("listaSucursalCtrl")) {
            listaSucursalCtrl = ((ListaSucursalCtrl) this.args.get("listaSucursalCtrl"));
        }
        checkPermisos();
        showDetalleSucursals();
    }

    public void showDetalleSucursals() {
        try {
            if (sucursalSelected != null) {
                doReadOnly(Boolean.TRUE);
                doEditButton();
                loadDataFromEntity();
//            if (sucursal.getIdSucursals().intValue() >= 1) {
//                this.listBoxOrdentrabajoSucursals.setModel(new ListModelList(
//                        this.ordentrabajoDao.buscarOrdenesPorsucursal(
//                        sucursal.getIdSucursals())));
//                this.listBoxOrdentrabajoSucursals.setItemRenderer(new SucursalsOrdentrabajoListModelItemRenderer());
//                this.panel_tramites_sucursal.setTitle(
//                        "Ultimos tramites del sucursal :"
//                        + sucursal.getNombreSucursals());
//            }
            } else {
                doNew();
            }
            detalleSucursalWindow.doModal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * cargamos los textboxs desde entity
     */
    private void loadDataFromEntity() {

        txtEncargado.setValue(sucursalSelected.getEncargado());
        txtDireccion.setValue(sucursalSelected.getDireccion());
        txtIdSucursal.setValue(sucursalSelected.getIdsucursal());
        txtDescSucursal.setText(sucursalSelected.getDescripcion());
        txtTelefono.setValue(sucursalSelected.getTelefono());
    
        if (sucursalSelected.getActiva().equals("S"))
        {
           checkEstadoSucursal.setChecked(true);
        } else { checkEstadoSucursal.setChecked(false);}
           
    }

    private void loadDataFromTextboxs() {
        try {
            
            Sucursales sucursalSelected2 = sucursalSelected;
            sucursalSelected = new Sucursales();
            //validamos los campos
//            if (StringUtils.isEmpty(txtDepartamento.getValue())) {
//                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar departamento sucursal");
//            }

            if (StringUtils.isEmpty(txtDireccion.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar dirección para sucursal");
            }

            if (StringUtils.isEmpty(txtDescSucursal.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar  Descripcion para sucursal");
            }

            if (StringUtils.isEmpty(txtTelefono.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar   Numero Telefono para sucursal");
            }
            
            
            if (checkEstadoSucursal.isChecked()) {
               sucursalSelected.setActiva("S");
            }
            else
            {
               sucursalSelected.setActiva("N");
            }
                
            //sucursalSelected.setDepartamento(txtDepartamento.getValue());
            sucursalSelected.setDireccion(txtDireccion.getValue());
            sucursalSelected.setIdsucursal(txtIdSucursal.getValue());

            sucursalSelected.setTelefono(txtTelefono.getValue());
            sucursalSelected.setDescripcion(txtDescSucursal.getValue());
            sucursalSelected.setEncargado(txtEncargado.getValue());

            // sucursal
            //Sucursales obj =
            sucursalSelected.setIdsucursal( sucursalSelected2.getIdsucursal());
            sucursalSelected.setIdempresa(sucursalSelected2.getIdempresa());
            //sucursalSelected.setIvaSucursals(txtRegistroIva.getValue());
            //sucursalSelected.setEmailSucursals(txtCorreoElectronico.getValue());
            //sucursalSelected.setTelefono(checkEstadoSucursals.isChecked());
        } catch (DiservWebException ex) {
            MensajeMultilinea.show(ex.getMensaje(), Constants.MENSAJE_TIPO_ERROR);
        }

    }

    public void onClick$btnGuardar(Event event) {
        try {
            if (getToken().intValue() > 0) {
                loadDataFromTextboxs();
                responseOperacion = sucursalBean.guardarSucursal(sucursalSelected);
                if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                    MensajeMultilinea.show(responseOperacion.getMensajeRespuesta() + " Id sucursal:" + responseOperacion.getSucursales().getIdsucursal(), Constants.MENSAJE_TIPO_INFO);
                    sucursalSelected = responseOperacion.getSucursales();
                    loadDataFromEntity();
                    doReadOnly(Boolean.TRUE);
                    doEditButton();
                    listaSucursalCtrl.refreshModel(0);
                } else {
                    MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
                }
                setToken(0);
            } else if (getToken().intValue() == 0) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Se intento guardar el mismo sucursal dos veces, por seguridad solo se proceso una vez ");
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

    public void onBlur$txtDescSucursal(Event event) throws DiservWebException, DiservBusinessException {
        try {
            if (StringUtils.isEmpty(txtDescSucursal.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar nombre sucursal");
            }
            listaSucursalesLike = sucursalBean.loadAllSucursalsByLike(txtDescSucursal.getValue());
            if (listaSucursalesLike.size() > 0) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Ya existe un sucursal con un nombre similar:\n" + listaSucursalesLike.get(0).getIdsucursal() + "\nId Sucursals:" + listaSucursalesLike.get(0).getIdsucursal());
            }
        } catch (DiservWebException web) {
            MensajeMultilinea.show(web.getMensaje(), Constants.MENSAJE_TIPO_ALERTA);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void doClose() {
        this.detalleSucursalWindow.onClose();
    }

    public void onDoubleClicked(Event event) throws Exception {
    logger.info("[onDoubleClicked]");
//        Listitem item = this.listBoxOrdentrabajoSucursals.getSelectedItem();
//
//        if (item != null) {
//            Ordentrabajo ordentrabajo = (Ordentrabajo) item.getAttribute("data");
//
//            HashMap map = new HashMap();
//            map.put("ordentrabajo", ordentrabajo);
//
//            map.put("lbOrdentabajo", this.listBoxOrdentrabajoSucursals);
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
        this.checkEstadoSucursal.setChecked(Boolean.FALSE);
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

       // txtCorreoElectronico.setReadonly(opt);
        txtEncargado.setReadonly(opt);
        txtDireccion.setReadonly(opt);
        txtDescSucursal.setReadonly(opt);
        txtTelefono.setReadonly(opt);
        checkEstadoSucursal.setDisabled(opt);
    }

    public void doClear() {

       // txtCorreoElectronico.setValue(null);
        txtEncargado.setValue(null);
        txtDireccion.setValue(null);
        txtIdSucursal.setValue(null);
        txtDescSucursal.setValue(null);
        txtTelefono.setValue(null);
        txtDescSucursal.setFocus(true);
    }

    public void doActualizar() {
        loadDataFromTextboxs();
        try {
            
            responseOperacion = sucursalBean.actualizarSucursal(sucursalSelected);
            if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta() + " Id sucursal:" + responseOperacion.getSucursales().getIdsucursal(), Constants.MENSAJE_TIPO_INFO);
                sucursalSelected = responseOperacion.getSucursales();
                loadDataFromEntity();
                doReadOnly(Boolean.TRUE);
                doEditButton();
                listaSucursalCtrl.refreshModel(Constants.PAGINA_INICIO_DEFAULT);
            } else {
                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
            }
        } catch (Exception bex) {
            bex.printStackTrace();
            logger.severe(_zclass);
            MensajeMultilinea.show(bex.toString(), Constants.MENSAJE_TIPO_ERROR);

        }
//        ListModelList lml = (ListModelList) getLbSucursals().getListModel();
//        if (lml.indexOf(sucursal) == -1) {
//            lml.add(sucursal);
//        } else {
//            lml.set(lml.indexOf(sucursal), sucursal);
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

    public Sucursales getSucursalsSelected() {
        return sucursalSelected;
    }

    public void setSucursalsSelected(Sucursales sucursalSelected) {
        this.sucursalSelected = sucursalSelected;
    }

    public ListaSucursalCtrl getListaSucursalsCtrl() {
        return listaSucursalCtrl;
    }

    public void setListaSucursalsCtrl(ListaSucursalCtrl listaSucursalsCtrl) {
        this.listaSucursalCtrl = listaSucursalsCtrl;
    }

    public List<Sucursales> getListaSucursalsLike() {
        return listaSucursalesLike;
    }

    public void setListaSucursalsLike(List<Sucursales> listaSucursalsLike) {
        this.listaSucursalesLike = listaSucursalsLike;
    }
     
}
