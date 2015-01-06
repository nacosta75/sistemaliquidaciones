/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.marcas;

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
import sv.com.diserv.liquidaciones.dto.OperacionesMarcaArticuloDTO;
import sv.com.diserv.liquidaciones.ejb.EmpresasBeanLocal;
import sv.com.diserv.liquidaciones.ejb.MarcaArticuloBeanLocal;
import sv.com.diserv.liquidaciones.entity.Empresas;
import sv.com.diserv.liquidaciones.entity.MarcaArticulo;
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
public class DetalleMarcaCtrl extends BaseController {
    
    private static final Logger logger = Logger.getLogger(DetalleMarcaCtrl.class.getName());
    private static final long serialVersionUID = -546886879998950478L;
    private MarcaArticuloBeanLocal marcasBean;
    private EmpresasBeanLocal empresasBean;
    private ServiceLocator serviceLocator;
    private MarcaArticulo marcaSelected;

    private transient Integer token;
    private ListaMarcaArticuloCtrl listaMarcasCtrl;

    protected Window detalleMarcaWindow ;
    protected Intbox txtIdMarca;
    protected Textbox txtDescripcion;
    protected Checkbox checkEstadoMarcas;
    private OperacionesMarcaArticuloDTO responseOperacion;
    protected Button btnActualizar;
    protected Button btnNuevo;
    protected Button btnEditar;
    protected Button btnGuardar;
    protected Button btnCerrar;
    private List<MarcaArticulo> listaMarcasLike;

    public List<MarcaArticulo> getListaMarcasLike() {
        return listaMarcasLike;
    }

    public void setListaMarcasLike(List<MarcaArticulo> listaMarcasLike) {
        this.listaMarcasLike = listaMarcasLike;
    }
    
    public DetalleMarcaCtrl() {
        logger.log(Level.INFO, "[DetalleMarcaCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            marcasBean = serviceLocator.getService(Constants.JNDI_MARCAS_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }

    }

    public void onCreate$detalleMarcaWindow(Event event) throws Exception {
        doOnCreateCommon(this.detalleMarcaWindow, event);
        MensajeMultilinea.doSetTemplate();
        if (this.args.containsKey("marcaSelected")) {
            marcaSelected = ((MarcaArticulo) this.args.get("marcaSelected"));
            setMarcaSelected(marcaSelected);
        }
        if (this.args.containsKey("token")) {
            this.token = ((Integer) this.args.get("token"));
            setToken(this.token);
        } else {
            setToken(Integer.valueOf(0));
        }
        if (this.args.containsKey("listaMarcasCtrl")) {
            listaMarcasCtrl = ((ListaMarcaArticuloCtrl) this.args.get("listaMarcasCtrl"));
        }
      //  checkPermisos();
      showDetalleMarcas();
    }
    
    public void showDetalleMarcas() {
        try {
            if (marcaSelected != null) {
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
            detalleMarcaWindow.doModal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromEntity() {

        txtIdMarca.setValue(marcaSelected.getIdmarca());
        txtDescripcion.setValue(marcaSelected.getDescmarca());
            // txtIdBodegas.setValue(bodegaSelected.getIdbodega());
        // txtNombreBodegas.setText(bodegaSelected.getNombre());
        // txtTelefono.setValue(bodegaSelected.getTelefono());
        //cmbSucursal.
        //checkEstadoBodegas.setChecked((boolean) (bodegaSelected.getActiva() != null ? bodegaSelected.getActiva() : false));
        if (marcaSelected.getActiva().equals("S")) {
            checkEstadoMarcas.setChecked(true);
        } else {
            checkEstadoMarcas.setChecked(false);
        }

    }

    private void loadDataFromTextboxs() {
        try {
            MarcaArticulo marcaSelected2 = marcaSelected;
            marcaSelected = new MarcaArticulo();
            //validamos los campos

            if (StringUtils.isEmpty(txtDescripcion.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar descripción para Marcas");
            }

            if (checkEstadoMarcas.isChecked()) {
                marcaSelected.setActiva("S");
            } else {
                marcaSelected.setActiva("N");
            }

            //bodegaSelected.setDepartamento(txtDepartamento.getValue());
            marcaSelected.setDescmarca(txtDescripcion.getValue());
            marcaSelected.setIdmarca(txtIdMarca.getValue());

            //bodegaSelected.setEncargado(txtEncargado.getValue());
            // sucursal
            //Sucursales obj =
     
          //  marcaSelected.setIdempresa(marcaSelected2.getIdempresa());

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
        checkEstadoMarcas.setDisabled(opt);
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
               // Empresas empresa= new Empresas(1,"1","DISERV, S.A. ");//empresasBean.loadEmpresaByID(1);
                
                //marcaSelected.setIdempresa(empresa);
                marcaSelected.setIdempresa(1);
                responseOperacion = marcasBean.guardarMarca(marcaSelected);
                if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                    MensajeMultilinea.show(responseOperacion.getMensajeRespuesta() + " Id Marca:" + responseOperacion.getMarcaArticulo().getIdmarca(), Constants.MENSAJE_TIPO_INFO);
                    marcaSelected = responseOperacion.getMarcaArticulo();
                    loadDataFromEntity();
                    doReadOnly(Boolean.TRUE);
                    doEditButton();
                    listaMarcasCtrl.refreshModel(0);
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
        this.detalleMarcaWindow.onClose();
    }
      
    public void doClear() {

       // txtCorreoElectronico.setValue(null);
        //txtEncargado.setValue(null);
        txtDescripcion.setValue(null);
        txtIdMarca.setValue(null);
        checkEstadoMarcas.setChecked(false);

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
        this.checkEstadoMarcas.setChecked(Boolean.FALSE);
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
            
            responseOperacion = marcasBean.actualizarMarca(marcaSelected);
            if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta() + " Id marca:" + responseOperacion.getMarcaArticulo().getIdmarca(), Constants.MENSAJE_TIPO_INFO);
                marcaSelected = responseOperacion.getMarcaArticulo();
                loadDataFromEntity();
                doReadOnly(Boolean.TRUE);
                doEditButton();
                listaMarcasCtrl.refreshModel(Constants.PAGINA_INICIO_DEFAULT);
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

    public MarcaArticulo getMarcaSelected() {
        return marcaSelected;
    }

    public void setMarcaSelected(MarcaArticulo marcaSelected) {
        this.marcaSelected = marcaSelected;
    }

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    public ListaMarcaArticuloCtrl getListaMarcasCtrl() {
        return listaMarcasCtrl;
    }

    public void setListaMarcasCtrl(ListaMarcaArticuloCtrl listaMarcasCtrl) {
        this.listaMarcasCtrl = listaMarcasCtrl;
    }

    
}
