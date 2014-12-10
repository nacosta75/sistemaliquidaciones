package sv.com.diserv.web.ui.bodegas;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.dto.OperacionesBodegaDTO;
import sv.com.diserv.liquidaciones.ejb.BodegasBeanLocal;
import sv.com.diserv.liquidaciones.entity.Bodegas;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.exception.DiservWebException;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

public class DetalleBodegaCtrl extends BaseController {

    private static final Logger logger = Logger.getLogger(DetalleBodegaCtrl.class.getName());
    private static final long serialVersionUID = -546886879998950467L;
    protected Window detalleBodegaWindow;
    protected Intbox txtIdBodegas;
    protected Textbox txtNombreBodegas;
    protected Textbox txtDireccion;
    protected Textbox txtTelefono;
    protected Textbox txtTelefono2;
    protected Textbox txtCorreoElectronico;
    protected Textbox txtDepartamento;
    protected Textbox txtMunicipio;
    protected Checkbox checkEstadoBodegas;
    protected Button btnActualizar;
    protected Button btnNuevo;
    protected Button btnEditar;
    protected Button btnGuardar;
    protected Button btnCerrar;
    private Bodegas bodegaSelected;
    private ListaBodegaCtrl listaBodegasCtrl;
    private transient Integer token;
    private BodegasBeanLocal bodegaBean;
    private ServiceLocator serviceLocator;
    private OperacionesBodegaDTO responseOperacion;
    private List<Bodegas> listaBodegasLike;

    public DetalleBodegaCtrl() {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            bodegaBean = serviceLocator.getService(Constants.JNDI_BODEGA_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$detalleBodegaWindow(Event event) throws Exception {
        doOnCreateCommon(this.detalleBodegaWindow, event);
        MensajeMultilinea.doSetTemplate();
        if (this.args.containsKey("bodegaSelected")) {
            bodegaSelected = ((Bodegas) this.args.get("bodegaSelected"));
            setBodegasSelected(bodegaSelected);
        }
        if (this.args.containsKey("token")) {
            this.token = ((Integer) this.args.get("token"));
            setToken(this.token);
        } else {
            setToken(Integer.valueOf(0));
        }
        if (this.args.containsKey("listaBodegasCtrl")) {
            listaBodegasCtrl = ((ListaBodegaCtrl) this.args.get("listaBodegasCtrl"));
        }
        checkPermisos();
        showDetalleBodegas();
    }

    public void showDetalleBodegas() {
        try {
            if (bodegaSelected != null) {
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
            detalleBodegaWindow.doModal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * cargamos los textboxs desde entity
     */
    private void loadDataFromEntity() {
        txtCorreoElectronico.setValue("testing");
        txtDepartamento.setValue(bodegaSelected.getDireccion());
        txtDireccion.setValue(bodegaSelected.getDireccion());
        txtIdBodegas.setValue(bodegaSelected.getIdbodega());
        txtMunicipio.setValue(bodegaSelected.getTelefono());
        txtNombreBodegas.setText(bodegaSelected.getNombre());
        txtTelefono2.setValue(bodegaSelected.getTelefono());
     //   txtRegistroIva.setValue(bodegaSelected.getIvaBodegas());
      //  txtCorreoElectronico.setValue(bodegaSelected.getEmailBodegas());
        //checkEstadoBodegas.setChecked((boolean) (bodegaSelected.getActiva() != null ? bodegaSelected.getActiva() : false));
    }

    private void loadDataFromTextboxs() {
        try {
            bodegaSelected = new Bodegas();
            //validamos los campos
            if (StringUtils.isEmpty(txtDepartamento.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar departamento bodega");
            }
            if (StringUtils.isEmpty(txtDireccion.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar dirección para bodega");
            }
            if (StringUtils.isEmpty(txtMunicipio.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar municipio bodega");
            }
            if (StringUtils.isEmpty(txtNombreBodegas.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar  Nombre Bodegas bodega");
            }
            if (StringUtils.isEmpty(txtTelefono2.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar   Numero Telefonos  para  bodega");
            }
            if (StringUtils.isEmpty(txtTelefono.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar   Numero Telefono para bodega");
            }
            if (checkEstadoBodegas.isChecked()) {
              //  bodegaSelected.setActiva("S");
            }
            //bodegaSelected.setDepartamento(txtDepartamento.getValue());
            bodegaSelected.setDireccion(txtDireccion.getValue());
            bodegaSelected.setIdbodega(txtIdBodegas.getValue());
            bodegaSelected.setTelefono(txtMunicipio.getValue());
            bodegaSelected.setNombre(txtNombreBodegas.getValue());
            bodegaSelected.setTelefono(txtTelefono2.getValue());
            //bodegaSelected.setIvaBodegas(txtRegistroIva.getValue());
            //bodegaSelected.setEmailBodegas(txtCorreoElectronico.getValue());
            //bodegaSelected.setTelefono(checkEstadoBodegas.isChecked());
        } catch (DiservWebException ex) {
            MensajeMultilinea.show(ex.getMensaje(), Constants.MENSAJE_TIPO_ERROR);
        }

    }

    public void onClick$btnGuardar(Event event) {
        try {
            if (getToken().intValue() > 0) {
                loadDataFromTextboxs();
                responseOperacion = bodegaBean.guardarBodega(bodegaSelected);
                if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                    MensajeMultilinea.show(responseOperacion.getMensajeRespuesta() + " Id bodega:" + responseOperacion.getBodega().getIdbodega(), Constants.MENSAJE_TIPO_INFO);
                    bodegaSelected = responseOperacion.getBodega();
                    loadDataFromEntity();
                    doReadOnly(Boolean.TRUE);
                    doEditButton();
                    listaBodegasCtrl.refreshModel(0);
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

    public void onBlur$txtNombreBodegas(Event event) throws DiservWebException, DiservBusinessException {
        try {
            if (StringUtils.isEmpty(txtNombreBodegas.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar nombre bodega");
            }
            listaBodegasLike = bodegaBean.loadAllBodegasByLike(txtNombreBodegas.getValue());
            if (listaBodegasLike.size() > 0) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Ya existe un bodega con un nombre similar:\n" + listaBodegasLike.get(0).getIdbodega() + "\nId Bodegas:" + listaBodegasLike.get(0).getIdbodega());
            }
        } catch (DiservWebException web) {
            MensajeMultilinea.show(web.getMensaje(), Constants.MENSAJE_TIPO_ALERTA);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void doClose() {
        this.detalleBodegaWindow.onClose();
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
        this.checkEstadoBodegas.setChecked(Boolean.FALSE);
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
        txtCorreoElectronico.setReadonly(opt);
        txtDepartamento.setReadonly(opt);
        txtDireccion.setReadonly(opt);
        txtMunicipio.setReadonly(opt);
        txtNombreBodegas.setReadonly(opt);
        txtTelefono2.setReadonly(opt);
        txtTelefono.setReadonly(opt);
    }

    public void doClear() {
        txtCorreoElectronico.setValue(null);
        txtDepartamento.setValue(null);
        txtDireccion.setValue(null);
        txtIdBodegas.setValue(null);
        txtMunicipio.setValue(null);
        txtNombreBodegas.setValue(null);
        txtTelefono2.setValue(null);
        txtTelefono.setValue(null);
        txtNombreBodegas.setFocus(true);
    }

    public void doActualizar() {
        loadDataFromTextboxs();
        try {
            responseOperacion = bodegaBean.actualizarBodega(bodegaSelected);
            if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta() + " Id bodega:" + responseOperacion.getBodega().getIdbodega(), Constants.MENSAJE_TIPO_INFO);
                bodegaSelected = responseOperacion.getBodega();
                loadDataFromEntity();
                doReadOnly(Boolean.TRUE);
                doEditButton();
                listaBodegasCtrl.refreshModel(Constants.PAGINA_INICIO_DEFAULT);
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

    public Bodegas getBodegasSelected() {
        return bodegaSelected;
    }

    public void setBodegasSelected(Bodegas bodegaSelected) {
        this.bodegaSelected = bodegaSelected;
    }

    public ListaBodegaCtrl getListaBodegasCtrl() {
        return listaBodegasCtrl;
    }

    public void setListaBodegasCtrl(ListaBodegaCtrl listaBodegasCtrl) {
        this.listaBodegasCtrl = listaBodegasCtrl;
    }

    public List<Bodegas> getListaBodegasLike() {
        return listaBodegasLike;
    }

    public void setListaBodegasLike(List<Bodegas> listaBodegasLike) {
        this.listaBodegasLike = listaBodegasLike;
    }
}
