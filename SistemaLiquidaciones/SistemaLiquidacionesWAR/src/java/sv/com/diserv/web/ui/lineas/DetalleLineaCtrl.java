/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.lineas;

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
import sv.com.diserv.liquidaciones.dto.OperacionesLineaArticuloDTO;
import sv.com.diserv.liquidaciones.ejb.EmpresasBeanLocal;
import sv.com.diserv.liquidaciones.ejb.LineaArticuloBeanLocal;
import sv.com.diserv.liquidaciones.entity.Empresas;
import sv.com.diserv.liquidaciones.entity.LineaArticulo;
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
public class DetalleLineaCtrl extends BaseController {

    private static final Logger logger = Logger.getLogger(DetalleLineaCtrl.class.getName());
    private static final long serialVersionUID = -546886879998950477L;
    private LineaArticuloBeanLocal lineasBean;
    private EmpresasBeanLocal empresasBean;
    private ServiceLocator serviceLocator;
    private LineaArticulo lineaSelected;

    private transient Integer token;
    private ListaLineaArticuloCtrl listaLineasCtrl;

    protected Window detalleLineaWindow ;
    protected Intbox txtIdLinea;
    protected Textbox txtDescripcion;
    protected Checkbox checkEstadoLineas;
    private OperacionesLineaArticuloDTO responseOperacion;
    protected Button btnActualizar;
    protected Button btnNuevo;
    protected Button btnEditar;
    protected Button btnGuardar;
    protected Button btnCerrar;
    private List<LineaArticulo> listaLineasLike;

    public List<LineaArticulo> getListaLineasLike() {
        return listaLineasLike;
    }

    public void setListaLineasLike(List<LineaArticulo> listaLineasLike) {
        this.listaLineasLike = listaLineasLike;
    }
    
    public DetalleLineaCtrl() {
        logger.log(Level.INFO, "[DetalleLineaCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            lineasBean = serviceLocator.getService(Constants.JNDI_LINEAS_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }

    }

    public void onCreate$detalleLineaWindow(Event event) throws Exception {
        doOnCreateCommon(this.detalleLineaWindow, event);
        MensajeMultilinea.doSetTemplate();
        if (this.args.containsKey("lineaSelected")) {
            lineaSelected = ((LineaArticulo) this.args.get("lineaSelected"));
            setLineaSelected(lineaSelected);
        }
        if (this.args.containsKey("token")) {
            this.token = ((Integer) this.args.get("token"));
            setToken(this.token);
        } else {
            setToken(Integer.valueOf(0));
        }
        if (this.args.containsKey("listaLineasCtrl")) {
            listaLineasCtrl = ((ListaLineaArticuloCtrl) this.args.get("listaLineasCtrl"));
        }
      //  checkPermisos();
      showDetalleLineas();
      
         //userLogin.getUsuario().getIdusuario();
    }
    
    public void showDetalleLineas() {
        try {
            if (lineaSelected != null) {
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
            detalleLineaWindow.doModal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromEntity() {

        txtIdLinea.setValue(lineaSelected.getIdlinea());
        txtDescripcion.setValue(lineaSelected.getDesclinea());
            // txtIdBodegas.setValue(bodegaSelected.getIdbodega());
        // txtNombreBodegas.setText(bodegaSelected.getNombre());
        // txtTelefono.setValue(bodegaSelected.getTelefono());
        //cmbSucursal.
        //checkEstadoBodegas.setChecked((boolean) (bodegaSelected.getActiva() != null ? bodegaSelected.getActiva() : false));
        if (lineaSelected.getActiva().equals("S")) {
            checkEstadoLineas.setChecked(true);
        } else {
            checkEstadoLineas.setChecked(false);
        }

    }

    private void loadDataFromTextboxs() {
        try {
            LineaArticulo lineaSelected2 = lineaSelected;
            lineaSelected = new LineaArticulo();
            //validamos los campos

            if (StringUtils.isEmpty(txtDescripcion.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar descripción para Lineas");
            }

            if (checkEstadoLineas.isChecked()) {
                lineaSelected.setActiva("S");
            } else {
                lineaSelected.setActiva("N");
            }

            //bodegaSelected.setDepartamento(txtDepartamento.getValue());
            lineaSelected.setDesclinea(txtDescripcion.getValue());
            lineaSelected.setIdlinea(txtIdLinea.getValue());
            

            //bodegaSelected.setEncargado(txtEncargado.getValue());
            // sucursal
            //Sucursales obj =
     
          //  lineaSelected.setIdempresa(lineaSelected2.getIdempresa());

            //bodegaSelected.setIvaBodegas(txtRegistroIva.getValue());
            //bodegaSelected.setEmailBodegas(txtCorreoElectronico.getValue());
            //bodegaSelected.setTelefono(checkEstadoBodegas.isChecked());
        } catch (DiservWebException ex) {
            MensajeMultilinea.show(ex.getMensaje(), Constants.MENSAJE_TIPO_ERROR);
        }

    }

    public void doReadOnly(Boolean opt) {

        // txtCorreoElectronico.setReadonly(opt);
        txtDescripcion.setReadonly(opt);
        checkEstadoLineas.setDisabled(opt);
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
                Empresas empresa= new Empresas(1,"1","DISERV, S.A. ");//empresasBean.loadEmpresaByID(1);
                
                lineaSelected.setIdempresa(empresa);
                
                responseOperacion = lineasBean.guardarLinea(lineaSelected);
                if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                    MensajeMultilinea.show(responseOperacion.getMensajeRespuesta() + " Id Linea:" + responseOperacion.getLineaArticulo().getIdlinea(), Constants.MENSAJE_TIPO_INFO);
                    lineaSelected = responseOperacion.getLineaArticulo();
                    loadDataFromEntity();
                    doReadOnly(Boolean.TRUE);
                    doEditButton();
                    listaLineasCtrl.refreshModel(0);
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
        this.detalleLineaWindow.onClose();
    }
      
    public void doClear() {

       // txtCorreoElectronico.setValue(null);
        //txtEncargado.setValue(null);
        txtDescripcion.setValue(null);
        txtIdLinea.setValue(null);
        checkEstadoLineas.setChecked(false);

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
        this.checkEstadoLineas.setChecked(Boolean.FALSE);
        this.btnEditar.setVisible(false);
        this.btnNuevo.setVisible(false);
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
            
            
            responseOperacion = lineasBean.actualizarLinea(lineaSelected);
            if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta() + " Id linea:" + responseOperacion.getLineaArticulo().getIdlinea(), Constants.MENSAJE_TIPO_INFO);
                lineaSelected = responseOperacion.getLineaArticulo();
                loadDataFromEntity();
                doReadOnly(Boolean.TRUE);
                doEditButton();
                listaLineasCtrl.refreshModel(Constants.PAGINA_INICIO_DEFAULT);
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

    public LineaArticulo getLineaSelected() {
        return lineaSelected;
    }

    public void setLineaSelected(LineaArticulo lineaSelected) {
        this.lineaSelected = lineaSelected;
    }

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    public ListaLineaArticuloCtrl getListaLineasCtrl() {
        return listaLineasCtrl;
    }

    public void setListaLineasCtrl(ListaLineaArticuloCtrl listaLineasCtrl) {
        this.listaLineasCtrl = listaLineasCtrl;
    }

}
