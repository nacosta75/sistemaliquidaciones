/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.articulos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.ejb.EncListaPrecioBeanLocal;
import sv.com.diserv.liquidaciones.ejb.DetListaPrecioBeanLocal;
import sv.com.diserv.liquidaciones.entity.DetListaPrecio;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.web.ui.util.BaseController;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Datebox;

import org.zkoss.zul.ListModelList;
import sv.com.diserv.liquidaciones.dto.CatalogoDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesPreciosDTO;
import sv.com.diserv.liquidaciones.ejb.CatalogosBeanLocal;
import sv.com.diserv.liquidaciones.entity.Articulos;
import sv.com.diserv.liquidaciones.entity.EncListaPrecio;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.exception.DiservWebException;
import sv.com.diserv.web.ui.personas.rendered.CatalogoItemRenderer;
import sv.com.diserv.web.ui.util.MensajeMultilinea;
/**
 *
 * @author abraham.acosta
 */
public class DetallePreciosCtrl extends BaseController {

 private static final Logger logger = Logger.getLogger(DetallePreciosCtrl.class.getName());
    private static final long serialVersionUID = -546886879998950944L;

    protected Window detallePrecioWindow;
    protected Intbox txtIdPrecio;
    protected Decimalbox txtPrecio;
    private OperacionesPreciosDTO responseOperacion;
    protected Button btnActualizar;
    // protected Button btnNuevo;
    protected Button btnEditar;
    // protected Button btnGuardar;
    protected Button btnCerrar;
    protected Combobox cmbCanal;
    protected Datebox dbDesde;
    protected Datebox dbHasta;
    private List<DetListaPrecio> listaPreciosLike;

    private ServiceLocator serviceLocator;
    private DetListaPrecio precioSelected;
    private EncListaPrecioBeanLocal encListaPrecioBean;
    private DetListaPrecioBeanLocal detListaPrecioBean;
    private CatalogosBeanLocal catalogosBeanLocal;
    private transient Integer token;
    private DetalleArticuloCtrl detalleArticuloCtrl;

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    public DetListaPrecio getPrecioSelected() {
        return precioSelected;
    }

    public void setPrecioSelected(DetListaPrecio precioSelected) {
        this.precioSelected = precioSelected;
    }

    public DetallePreciosCtrl() {
        logger.log(Level.INFO, "[DetallePreciosCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();

            catalogosBeanLocal = serviceLocator.getService(Constants.JNDI_CATALOGO_BEAN);
            encListaPrecioBean = serviceLocator.getService(Constants.JNDI_ENCLISTAPRECIO_BEAN);
            detListaPrecioBean = serviceLocator.getService(Constants.JNDI_DETLISTAPRECIO_BEAN);

        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }

    }

    public void onCreate$detallePrecioWindow(Event event) throws Exception {
        doOnCreateCommon(this.detallePrecioWindow, event);
        MensajeMultilinea.doSetTemplate();
        if (this.args.containsKey("precioSelected")) {
            precioSelected = ((DetListaPrecio) this.args.get("precioSelected"));
            setPrecioSelected(precioSelected);
        }
        if (this.args.containsKey("token")) {
            this.token = ((Integer) this.args.get("token"));
            setToken(this.token);
        } else {
            setToken(Integer.valueOf(0));
        }
        if (this.args.containsKey("detalleArticuloCtrl")) {
            detalleArticuloCtrl = ((DetalleArticuloCtrl) this.args.get("detalleArticuloCtrl"));
        }

        showDetallePrecios();

    }

    public void showDetallePrecios() {
        try {
            if (precioSelected != null) {
                doReadOnly(Boolean.TRUE);
                doEditButton();
                loadDataFromEntity();
            } else {
                doNew();
            }
            detallePrecioWindow.doModal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromEntity() {

        try {
            txtIdPrecio.setValue(precioSelected.getIdlistadet());
            loadCombobox();
            cmbCanal.setValue(precioSelected.getIdlistaenc().getDescripcionLista());

            if (precioSelected.getPrecio() != null) {
                txtPrecio.setValue(precioSelected.getPrecio());
            } else {
                txtPrecio.setValue(BigDecimal.ZERO);
            }

            dbDesde.setValue(precioSelected.getIdlistaenc().getFechaDesde());
            dbHasta.setValue(precioSelected.getIdlistaenc().getDechaHasta());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadDataFromTextboxs() {
        try {
            DetListaPrecio precio = precioSelected;
            Articulos articulo = precioSelected.getIdarticulo();
            precioSelected = new DetListaPrecio();

            //validamos los campos
            if (cmbCanal.getSelectedItem() == null) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar Canal");
            }

            precioSelected.setIdarticulo(articulo);
            precioSelected.setIdlistadet(txtIdPrecio.getValue());
            precioSelected.setIdlistaenc((EncListaPrecio) cmbCanal.getSelectedItem().getAttribute("data"));
            precioSelected.setPrecio(txtPrecio.getValue());

        } catch (DiservWebException ex) {
            MensajeMultilinea.show(ex.getMensaje(), Constants.MENSAJE_TIPO_ERROR);
        }

    }

    private void loadCombobox() {
        
        List<EncListaPrecio> listaCanales;
        try {
            //****************************** unidades de medida *********************/ 
            listaCanales = encListaPrecioBean.loadAllCanales();          
            if (listaCanales != null) {
                cmbCanal.setModel(new ListModelList(listaCanales));
                cmbCanal.setItemRenderer(new CatalogoItemRenderer());
            } else {
                cmbCanal.setValue("No existen Canales!!");
                cmbCanal.setReadonly(true);
                cmbCanal.setButtonVisible(false);
                cmbCanal.setDisabled(true);
            }

        } catch (DiservBusinessException ex) {
            Logger.getLogger(DetallePreciosCtrl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void doReadOnly(Boolean opt) {
        txtPrecio.setReadonly(opt);
        cmbCanal.setReadonly(opt);
        dbDesde.setReadonly(opt);
        dbHasta.setReadonly(opt);
    }

    private void doEditButton() {
        this.btnCerrar.setVisible(true);
        this.btnEditar.setVisible(true);
       // this.btnNuevo.setVisible(true);
        // this.btnGuardar.setVisible(false);
        this.btnActualizar.setVisible(false);
    }

    public void onClick$btnEditar(Event event) {
        doReadOnly(Boolean.FALSE);
        this.btnActualizar.setVisible(true);
        // this.btnGuardar.setVisible(true);
        this.btnEditar.setVisible(false);
    }

    private void doNew() {
        doClear();
        doReadOnly(Boolean.FALSE);
        //  this.btnGuardar.setVisible(true);
        this.btnCerrar.setVisible(true);
        this.btnActualizar.setVisible(false);
        this.btnEditar.setVisible(false);
        //   this.btnNuevo.setVisible(false);
        loadCombobox();
    }

    private void doClear() {
        txtPrecio.setValue(BigDecimal.ZERO);
        txtIdPrecio.setValue(null);
        dbDesde.setValue(null);
        dbHasta.setValue(null);
    }

    public void onClick$btnActualizar(Event event) throws InterruptedException {
        doActualizar();
        this.btnActualizar.setVisible(false);
    }

    public void doActualizar() {
        loadDataFromTextboxs();
        try {

            responseOperacion = detListaPrecioBean.actualizarPrecio(precioSelected);
            if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                MensajeMultilinea.show(responseOperacion.getMensajeRespuesta() + " Id Precio:" + responseOperacion.getDetListaPrecio().getIdlistadet(), Constants.MENSAJE_TIPO_INFO);
                precioSelected = responseOperacion.getDetListaPrecio();
                loadDataFromEntity();
                doReadOnly(Boolean.TRUE);
                doEditButton();
                detalleArticuloCtrl.refreshModel(Constants.PAGINA_INICIO_DEFAULT);
                
                doClose();
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

    private void doClose() {
        this.detallePrecioWindow.onClose();
    }

    public DetalleArticuloCtrl getDetalleArticuloCtrl() {
        return detalleArticuloCtrl;
    }

    public void setDetalleArticuloCtrl(DetalleArticuloCtrl detalleArticuloCtrl) {
        this.detalleArticuloCtrl = detalleArticuloCtrl;
    }


    }
