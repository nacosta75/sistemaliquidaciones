/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.inventario;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.dto.BusquedaArticuloDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesMovimientoDetDTO;
import sv.com.diserv.liquidaciones.ejb.ArticulosBeanLocal;
import sv.com.diserv.liquidaciones.ejb.MovimientosDetBeanLocal;
import sv.com.diserv.liquidaciones.entity.Articulos;
import sv.com.diserv.liquidaciones.entity.EncListaPrecio;
import sv.com.diserv.liquidaciones.entity.Movimientos;
import sv.com.diserv.liquidaciones.entity.MovimientosDet;
import sv.com.diserv.liquidaciones.exception.DiservWebException;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.web.ui.articulos.rendered.ArticuloItemRenderer;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

/**
 *
 * @author abraham.acosta
 */
public class DetalleCompraDialogCtrl extends BaseController {

    static final Logger logger = Logger.getLogger(DetalleCompraDialogCtrl.class.toString());
    private static final long serialVersionUID = -5746886879998950489L;

    protected Window orderPositionDialogWindow;

    //busqueda de articulos
    protected Listbox listBoxArticleSearch;
    protected Listheader listheader_ArticleSearch_artId;
    protected Listheader listheader_ArticleSearch_artCod;
    protected Listheader listheader_ArticleSearch_artDesc;
    // bandbox searchProv
    protected Bandbox bandbox_OrderPositionDialog_ArticleSearch;
    private Object paging_ListBoxArticleSearch;
    private transient List<Articulos> searchObjProduct;
    private final int pageSizeSearchCustomers = 20;

    //detalle de movimiento
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnActualizar;
    protected Button btnDelete;
    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnClose;
    protected Button button_OrderPositionDialog_Calculate;

    protected Textbox txtCodigo;
    protected Textbox txtDescripcion;
    protected Decimalbox txtCantidad;
    protected Decimalbox txtPrecio;
    protected Decimalbox txtTotal;

    protected Textbox tb_OrderPosition_SearchArticleCodigo;
    protected Textbox tb_OrderPosition_SearchArticleDesc;

    private ServiceLocator serviceLocator;

    private Integer numeroPaginInicio;
    private BusquedaArticuloDTO request;

    private ArticulosBeanLocal articuloBean;
    private MovimientosDetBeanLocal movimientosDetBean;

    private MovimientosDet detalleMovimientoSelected;
    private transient Integer token;
    private Movimientos encabezadoCompra;
    private EncabezadoCompraCtrl encabezadoCompraCtrl;
    private Articulos articulo;
    private OperacionesMovimientoDetDTO responseOperacion;
    private Movimientos encabezadoMov;

    public DetalleCompraDialogCtrl() {

        logger.log(Level.INFO, "[DetalleCompraDialogCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            //movimientoBean = serviceLocator.getService(Constants.JNDI_MOVIMIENTOS_BEAN);
            movimientosDetBean = serviceLocator.getService(Constants.JNDI_MOVIMIENTOSDET_BEAN);
            articuloBean = serviceLocator.getService(Constants.JNDI_ARTICULOS_BEAN);
            numeroPaginInicio = 0;
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }

    }

    public void onCreate$orderPositionDialogWindow(Event event) throws Exception {
        doOnCreateCommon(this.orderPositionDialogWindow, event);
        MensajeMultilinea.doSetTemplate();
        if (this.args.containsKey("detalleMovimientoSelected")) {
            detalleMovimientoSelected = ((MovimientosDet) this.args.get("detalleMovimientoSelected"));
            setDetalleMovimientoSelected(detalleMovimientoSelected);
        }
        if (this.args.containsKey("encabezadoCompra")) {
            encabezadoCompra = ((Movimientos) this.args.get("encabezadoCompra"));
            setEncabezadoCompra(encabezadoCompra);
        }
        if (this.args.containsKey("token")) {
            this.token = ((Integer) this.args.get("token"));
            setToken(this.token);
        } else {
            setToken(Integer.valueOf(0));
        }
        if (this.args.containsKey("encabezadoCompraCtrl")) {
            encabezadoCompraCtrl = ((EncabezadoCompraCtrl) this.args.get("encabezadoCompraCtrl"));
        }

        showDetalleLineas();

    }

    public Movimientos getEncabezadoCompra() {
        return encabezadoCompra;
    }

    public void setEncabezadoCompra(Movimientos encabezadoCompra) {
        this.encabezadoCompra = encabezadoCompra;
    }

    public Movimientos getEncabezadoMov() {
        return encabezadoMov;
    }

    public void setEncabezadoMov(Movimientos encabezadoMov) {
        this.encabezadoMov = encabezadoMov;
    }

    public Integer getNumeroPaginInicio() {
        return numeroPaginInicio;
    }

    public void setNumeroPaginInicio(Integer numeroPaginInicio) {
        this.numeroPaginInicio = numeroPaginInicio;
    }

    public void onClick$button_bbox_ArticleSearch_Close(Event event) {
        // logger.debug(event.toString());

        bandbox_OrderPositionDialog_ArticleSearch.close();
    }

    public void onClick$button_OrderPositionDialog_Calculate(Event event) {
        txtTotal.setValue(txtCantidad.getValue().multiply(txtPrecio.getValue()));
    }

    public void onOpen$bandbox_OrderPositionDialog_ArticleSearch(Event event) throws Exception {
		// logger.debug(event.toString());

        // not used listheaders must be declared like ->
        // lh.setSortAscending(""); lh.setSortDescending("")
        listheader_ArticleSearch_artId.setSortAscending(new FieldComparator("kunNr", true));
        listheader_ArticleSearch_artId.setSortDescending(new FieldComparator("kunNr", false));
        listheader_ArticleSearch_artCod.setSortAscending(new FieldComparator("kunMatchcode", true));
        listheader_ArticleSearch_artCod.setSortDescending(new FieldComparator("kunMatchcode", false));
        listheader_ArticleSearch_artDesc.setSortAscending(new FieldComparator("kunName1", true));
        listheader_ArticleSearch_artDesc.setSortDescending(new FieldComparator("kunName1", false));

        BuscarProdutos();
    }

    public void onClick$button_bbox_ArticleSearch_Search(Event event) {
        BuscarProdutos();
    }

    private void BuscarProdutos() {
        logger.log(Level.INFO, "[BuscarProdutos][refreshModel] Listar productos");
        try {
            request = new BusquedaArticuloDTO();

            //codigo del producto
            if (StringUtils.isNotEmpty(tb_OrderPosition_SearchArticleCodigo.getText())) {
                request.setCodarticulo(tb_OrderPosition_SearchArticleCodigo.getValue());
            }

            // descripcion del producto
            if (StringUtils.isNotEmpty(tb_OrderPosition_SearchArticleDesc.getValue())) {
                request.setDescarticulo(tb_OrderPosition_SearchArticleDesc.getValue().toUpperCase());
            }

//            if (StringUtils.isNotEmpty(tb_Orders_CustSearchMatchcode.getValue())) {
//                request.setNumeroRegistro(tb_Orders_CustSearchMatchcode.getValue());
//            }
//            
//            request.setTipoPersona(3);
            searchObjProduct = articuloBean.buscarArticuloByCriteria(request);

            if (!searchObjProduct.isEmpty()) {

                listBoxArticleSearch.setModel(new ListModelList(searchObjProduct));
                listBoxArticleSearch.setItemRenderer(new ArticuloItemRenderer());

            } else {

                listBoxArticleSearch.setEmptyMessage("No se encontraron registros con los criterios ingresados!!");
                MensajeMultilinea.show("No se encontraron proveedores con los criterios ingresados", Constants.MENSAJE_TIPO_ALERTA);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.toString(), Constants.MENSAJE_TIPO_ERROR);
        }
    }

    public BusquedaArticuloDTO getRequest() {
        return request;
    }

    public void setRequest(BusquedaArticuloDTO request) {
        this.request = request;
    }

    public MovimientosDet getDetalleMovimientoSelected() {
        return detalleMovimientoSelected;
    }

    public void setDetalleMovimientoSelected(MovimientosDet detalleMovimientoSelected) {
        this.detalleMovimientoSelected = detalleMovimientoSelected;
    }

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    private void showDetalleLineas() {
        try {
            if (detalleMovimientoSelected != null) {
                doReadOnly(Boolean.TRUE);
                doEditButton();
                loadDataFromEntity();

            } else {
                doNew();
            }
            //encabezadoCompraWindow.doModal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick$btnEdit(Event event) {
        doReadOnly(Boolean.FALSE);
        this.btnActualizar.setVisible(true);
        this.btnEdit.setVisible(false);
    }

    private void doEditButton() {
        this.btnClose.setVisible(true);
        this.btnEdit.setVisible(true);
        this.btnNew.setVisible(false);
        this.btnSave.setVisible(false);
        this.btnActualizar.setVisible(false);
        this.btnDelete.setVisible(false);
        this.btnCancel.setVisible(true);
    }

    private void doNew() {

        doClear();
        doReadOnly(Boolean.FALSE);

        this.btnClose.setVisible(false);
        this.btnEdit.setVisible(false);
        this.btnNew.setVisible(false);
        this.btnSave.setVisible(true);
        this.btnDelete.setVisible(false);
        this.btnCancel.setVisible(true);
    }

    private void doReadOnly(Boolean opt) {

        txtCodigo.setReadonly(opt);
        txtDescripcion.setReadonly(opt);
        txtCantidad.setReadonly(opt);
        txtPrecio.setReadonly(opt);
        txtTotal.setReadonly(opt);

    }

    private void loadDataFromEntity() {
        try {
            txtCodigo.setValue(detalleMovimientoSelected.getIdarticulo().getCodarticulo());
            txtDescripcion.setValue(detalleMovimientoSelected.getIdarticulo().getDescarticulo());
            txtCantidad.setValue(detalleMovimientoSelected.getCantidad());
            txtPrecio.setValue(detalleMovimientoSelected.getPrecio());
            txtTotal.setValue(detalleMovimientoSelected.getCantidad().multiply(detalleMovimientoSelected.getPrecio()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doClear() {
        txtCodigo.setValue(null);
        txtDescripcion.setValue(null);
        txtCantidad.setValue(BigDecimal.ZERO);
        txtPrecio.setValue(BigDecimal.ZERO);
        txtTotal.setValue(BigDecimal.ZERO);
    }

    public void onClick$btnClose(Event event) throws InterruptedException {
        doClose();
    }

    private void doClose() {
        this.orderPositionDialogWindow.onClose();
    }

    public void onDoubleClickedArticulo(Event event) throws Exception {
        logger.log(Level.INFO, "[**onDoubleClickedArticulo]Event:{0}", event.toString());
        Listitem item = this.listBoxArticleSearch.getSelectedItem();
        if (item != null) {
            Articulos articulo = (Articulos) item.getAttribute("data");
            if (articulo != null) {
                txtCodigo.setValue(articulo.getCodarticulo());
                txtDescripcion.setValue(articulo.getDescarticulo());
                txtCantidad.setValue(BigDecimal.ZERO);
                if (articulo.getCostopromant() != null) {
                    txtPrecio.setValue(articulo.getCostopromant());
                } else {
                    txtPrecio.setValue(BigDecimal.ZERO);
                }

                txtTotal.setValue(txtCantidad.getValue().multiply(txtPrecio.getValue()));

                setArticulo(articulo);
            }

            bandbox_OrderPositionDialog_ArticleSearch.close();
        }
    }

    public Articulos getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulos articulo) {
        this.articulo = articulo;
    }

    public void onClick$btnActualizar(Event event) throws InterruptedException {
        doActualizar();
        this.btnActualizar.setVisible(false);
    }

    public void doActualizar() {
        
        try {

            if (getToken().intValue() > 0) {
                loadDataFromTextboxs();

                detalleMovimientoSelected.setClaseOperacion("E");
                if (articulo.getCostopromact() != null) {
                    detalleMovimientoSelected.setCostoProm(articulo.getCostopromact());
                } else {
                    detalleMovimientoSelected.setCostoProm(BigDecimal.ZERO);
                }
                detalleMovimientoSelected.setFechaMov(encabezadoCompra.getFechamov());
                detalleMovimientoSelected.setIdmov(encabezadoCompra);
                detalleMovimientoSelected.setNoDoc(encabezadoCompra.getNodoc());
                if (articulo.getCostocompant() != null) {
                    detalleMovimientoSelected.setUltCosto(articulo.getCostocompant());
                } else {
                    detalleMovimientoSelected.setUltCosto(BigDecimal.ZERO);
                }
                detalleMovimientoSelected.setValorImpuesto(Constants.VALOR_IMPUESTO_IVA);
                detalleMovimientoSelected.setIdlista(new EncListaPrecio(1));

                responseOperacion = movimientosDetBean.guardarMovimientoDet(detalleMovimientoSelected);
                if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                    //MensajeMultilinea.show(responseOperacion.getMensajeRespuesta() + " Id movimiento:" + responseOperacion.getMovimiento().getIdmovd(), Constants.MENSAJE_TIPO_INFO);
                    detalleMovimientoSelected = responseOperacion.getMovimiento();
                    loadDataFromEntity();
                    doReadOnly(Boolean.TRUE);
                    doEditButton();
                    encabezadoCompraCtrl.refreshModel(0);
                    doClose();
                } else {
                    MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
                }
                setToken(0);
            } else if (getToken().intValue() == 0) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Se intento guardar el mismo articulo dos veces, por seguridad solo se proceso una vez ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.getMessage(), Constants.MENSAJE_TIPO_ERROR);
        }
    }

    public void onClick$btnSave(Event event) {
        try {

            if (getToken().intValue() > 0) {
                loadDataFromTextboxs();

                detalleMovimientoSelected.setClaseOperacion("E");
                if (articulo.getCostopromact() != null) {
                    detalleMovimientoSelected.setCostoProm(articulo.getCostopromact());
                } else {
                    detalleMovimientoSelected.setCostoProm(BigDecimal.ZERO);
                }
                detalleMovimientoSelected.setFechaMov(encabezadoCompra.getFechamov());
                detalleMovimientoSelected.setIdmov(encabezadoCompra);
                detalleMovimientoSelected.setNoDoc(encabezadoCompra.getNodoc());
                if (articulo.getCostocompant() != null) {
                    detalleMovimientoSelected.setUltCosto(articulo.getCostocompant());
                } else {
                    detalleMovimientoSelected.setUltCosto(BigDecimal.ZERO);
                }
                detalleMovimientoSelected.setValorImpuesto(Constants.VALOR_IMPUESTO_IVA);
                detalleMovimientoSelected.setIdlista(new EncListaPrecio(1));

                responseOperacion = movimientosDetBean.guardarMovimientoDet(detalleMovimientoSelected);
                if (responseOperacion.getCodigoRespuesta() == Constants.CODE_OPERACION_SATISFACTORIA) {
                    //MensajeMultilinea.show(responseOperacion.getMensajeRespuesta() + " Id movimiento:" + responseOperacion.getMovimiento().getIdmovd(), Constants.MENSAJE_TIPO_INFO);
                    detalleMovimientoSelected = responseOperacion.getMovimiento();
                    loadDataFromEntity();
                    doReadOnly(Boolean.TRUE);
                    doEditButton();
                    encabezadoCompraCtrl.refreshModel(0);
                    doClose();
                } else {
                    MensajeMultilinea.show(responseOperacion.getMensajeRespuesta(), Constants.MENSAJE_TIPO_ERROR);
                }
                setToken(0);
            } else if (getToken().intValue() == 0) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Se intento guardar el mismo articulo dos veces, por seguridad solo se proceso una vez ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.getMessage(), Constants.MENSAJE_TIPO_ERROR);
        }
    }

    private void loadDataFromTextboxs() {
        try {

            MovimientosDet oldMovimientosDet = detalleMovimientoSelected;

            detalleMovimientoSelected = new MovimientosDet();

            if (StringUtils.isEmpty(txtCodigo.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar codigo de producto valido");
            }

            if (StringUtils.isEmpty(txtDescripcion.getValue())) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Debe ingresar descripcion de producto valido");
            }

            if (!(txtCantidad.getValue().intValue() > 0)) {
                throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "cantidad de articulos debe ser mayor que cero");
            }

            if (oldMovimientosDet != null) {
                detalleMovimientoSelected.setIdarticulo(oldMovimientosDet.getIdarticulo());
            } else {
                detalleMovimientoSelected.setIdarticulo(articulo);
            }
            detalleMovimientoSelected.setCantidad(txtCantidad.getValue());
            detalleMovimientoSelected.setPrecio(txtPrecio.getValue());

        } catch (DiservWebException ex) {
            MensajeMultilinea.show(ex.getMensaje(), Constants.MENSAJE_TIPO_ERROR);
        }

    }

    public EncabezadoCompraCtrl getEncabezadoCompraCtrl() {
        return encabezadoCompraCtrl;
    }

    public void setEncabezadoCompraCtrl(EncabezadoCompraCtrl encabezadoCompraCtrl) {
        this.encabezadoCompraCtrl = encabezadoCompraCtrl;
    }

}
