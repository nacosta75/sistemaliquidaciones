/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.inventario;


import org.apache.log4j.Logger;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;
import sv.com.diserv.web.ui.util.BaseController;

/**
 *
 * @author abraham.acosta
 */
public class DetalleCompraCtrl extends BaseController{
    
    static final Logger logger = Logger.getLogger(DetalleCompraCtrl.class);
    private static final long serialVersionUID = -5546886879998950489L;
    
    protected Window orderPositionDialogWindow;
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnDelete;
    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnClose;
    
    public DetalleCompraCtrl()
    {
    
    }
    
}
