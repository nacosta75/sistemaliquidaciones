/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.inventario;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.dto.BusquedaArticuloDTO;
import sv.com.diserv.liquidaciones.dto.BusquedaPersonaDTO;
import sv.com.diserv.liquidaciones.entity.Articulos;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.web.ui.articulos.rendered.ArticuloItemRenderer;
import static sv.com.diserv.web.ui.inventario.EncabezadoCompraCtrl.logger;
import sv.com.diserv.web.ui.personas.rendered.PersonaItemRenderer;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

/**
 *
 * @author abraham.acosta
 */
public class DetalleCompraDialogCtrl extends BaseController{
    
    static final Logger logger = Logger.getLogger(DetalleCompraDialogCtrl.class.toString());
    private static final long serialVersionUID = -5746886879998950489L;
    
    protected Window borderlayoutOrderPositionDialog;
    
    //busqueda de articulos
    protected Listbox listBoxArticleSearch;
    protected Listheader listheader_ArticleSearch_artcodigo;
    protected Listheader listheader_ArticleSearch_artDesc;
    protected Listheader listheader_ArticleSearch_artPrecio;  
    // bandbox searchProv
    protected Bandbox bandbox_OrderPositionDialog_ArticleSearch;
    private Object paging_ListBoxArticleSearch;
    private transient List<Articulos> searchObjCustomer;
    private final int pageSizeSearchCustomers = 20;
    protected Textbox tb_OrderPosition_SearchArticlelNo;
    protected Textbox tb_OrderPosition_SearchArticleDesc;
    
    private ServiceLocator serviceLocator;
    
    private Integer numeroPaginInicio;
    private BusquedaArticuloDTO request;

    
    public DetalleCompraDialogCtrl()
    {
        
        logger.log(Level.INFO, "[DetalleCompraDialogCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            //movimientoBean = serviceLocator.getService(Constants.JNDI_MOVIMIENTOS_BEAN);
            //personaBean = serviceLocator.getService(Constants.JNDI_PERSONA_BEAN);
            numeroPaginInicio = 0;
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    
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


    public void onOpen$bandbox_OrderPositionDialog_ArticleSearch(Event event) throws Exception {
		// logger.debug(event.toString());

		// not used listheaders must be declared like ->
        // lh.setSortAscending(""); lh.setSortDescending("")
        listheader_ArticleSearch_artcodigo.setSortAscending(new FieldComparator("kunNr", true));
        listheader_ArticleSearch_artcodigo.setSortDescending(new FieldComparator("kunNr", false));
        listheader_ArticleSearch_artDesc.setSortAscending(new FieldComparator("kunMatchcode", true));
        listheader_ArticleSearch_artDesc.setSortDescending(new FieldComparator("kunMatchcode", false));
        listheader_ArticleSearch_artPrecio.setSortAscending(new FieldComparator("kunName1", true));
        listheader_ArticleSearch_artPrecio.setSortDescending(new FieldComparator("kunName1", false));

        BuscarProdutos();
    }

    public void onClick$button_bbox_CustomerSearch_Search(Event event) {
               BuscarProdutos();
    }

    
    private void BuscarProdutos() {
        logger.log(Level.INFO, "[BuscarProdutos][refreshModel] Listar productos");
        try {
            request = new BusquedaArticuloDTO();
            
            if (StringUtils.isNotEmpty(tb_OrderPosition_SearchArticlelNo.getText())) {
                request.setCodarticulo(tb_OrderPosition_SearchArticlelNo.getValue());
            }
            if (StringUtils.isNotEmpty(tb_OrderPosition_SearchArticleDesc.getValue())) {
                request.setDescarticulo(tb_OrderPosition_SearchArticleDesc.getValue().toUpperCase());
            }

//            if (StringUtils.isNotEmpty(tb_Orders_CustSearchMatchcode.getValue())) {
//                request.setNumeroRegistro(tb_Orders_CustSearchMatchcode.getValue());
//            }
//            
//            request.setTipoPersona(3);
//            searchObjCustomer = personaBean.buscarPersonaByCriteria(request);

            if (!searchObjCustomer.isEmpty()) {

                listBoxArticleSearch.setModel(new ListModelList(searchObjCustomer));
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
    
    
}
