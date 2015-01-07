/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.articulos;

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
import sv.com.diserv.liquidaciones.dto.OperacionesArticuloDTO;
import sv.com.diserv.liquidaciones.ejb.ArticulosBeanLocal;
import sv.com.diserv.liquidaciones.ejb.EmpresasBeanLocal;
import sv.com.diserv.liquidaciones.entity.Articulos;
import sv.com.diserv.liquidaciones.entity.Empresas;
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
public class DetalleArticuloCtrl extends BaseController {

    private static final Logger logger = Logger.getLogger(DetalleArticuloCtrl.class.getName());
    private static final long serialVersionUID = -546886879998950994L;
    private ArticulosBeanLocal articulosBean;
    private EmpresasBeanLocal empresasBean;
    private ServiceLocator serviceLocator;
    private Articulos articuloSelected;

    private transient Integer token;
    private ListaArticulosCtrl listaArticulosCtrl;

    protected Window detalleArticuloWindow ;
    protected Intbox txtIdArticulo;
    protected Textbox txtDescripcion;
    protected Textbox txtCodigo;
    protected Checkbox checkEstadoArticulo;
    private OperacionesArticuloDTO responseOperacion;
    protected Button btnActualizar;
    protected Button btnNuevo;
    protected Button btnEditar;
    protected Button btnGuardar;
    protected Button btnCerrar;
    private List<Articulos> listaArticulosLike;

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


      public DetalleArticuloCtrl() {
        logger.log(Level.INFO, "[DetalleArticuloCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            articulosBean = serviceLocator.getService(Constants.JNDI_ARTICULOS_BEAN);
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

            if (StringUtils.isEmpty(txtDescripcion.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar descripción para Articulos");
            }

//            if (checkEstadoArticulos.isChecked()) {
//                articuloSelected.setActiva("S");
//            } else {
//                articuloSelected.setActiva("N");
//            }

            //bodegaSelected.setDepartamento(txtDepartamento.getValue());
            articuloSelected.setCodarticulo(txtCodigo.getValue());
            articuloSelected.setDescarticulo(txtDescripcion.getValue());
            articuloSelected.setIdarticulo(txtIdArticulo.getValue());

            //bodegaSelected.setEncargado(txtEncargado.getValue());
            // sucursal
            //Sucursales obj =
     
          //  articuloSelected.setIdempresa(articuloSelected2.getIdempresa());

            //bodegaSelected.setIvaBodegas(txtRegistroIva.getValue());
            //bodegaSelected.setEmailBodegas(txtCorreoElectronico.getValue());
            //bodegaSelected.setTelefono(checkEstadoBodegas.isChecked());
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
                Empresas empresa= new Empresas(1,"1","DISERV, S.A. ");//empresasBean.loadEmpresaByID(1);
                
                articuloSelected.setIdempresa(empresa);
                
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
        checkEstadoArticulo.setChecked(false);

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
