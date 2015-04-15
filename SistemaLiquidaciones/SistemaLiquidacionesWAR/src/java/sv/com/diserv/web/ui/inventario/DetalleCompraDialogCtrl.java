/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.inventario;

import java.util.List;
import java.util.logging.Logger;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.entity.Articulos;
import sv.com.diserv.web.ui.util.BaseController;

/**
 *
 * @author abraham.acosta
 */
public class DetalleCompraDialogCtrl extends BaseController{
    
    static final Logger logger = Logger.getLogger(DetalleCompraDialogCtrl.class.getName());
    private static final long serialVersionUID = -5746886879998950489L;
    
    protected Window borderlayoutOrderPositionDialog;
    
    //busqueda de articulos
    protected Listbox listBoxArticleSearch;
    protected Listheader listheader_ArticleSearch_artcodigo;
    protected Listheader listheader_ArticleSearch_artDesc;
    protected Listheader listheader_ArticleSearch_artPrecio;  
    // bandbox searchCustomer
    protected Bandbox bandbox_OrderPositionDialog_ArticleSearch;
    private Object paging_ListBoxArticleSearch;
    private transient List<Articulos> searchObjCustomer;
    private final int pageSizeSearchCustomers = 20;
    protected Textbox tb_OrderPosition_SearchArticlelNo;
    protected Textbox tb_OrderPosition_SearchArticleDesc;

    
    public DetalleCompraDialogCtrl()
    {
    
    }
    
}
