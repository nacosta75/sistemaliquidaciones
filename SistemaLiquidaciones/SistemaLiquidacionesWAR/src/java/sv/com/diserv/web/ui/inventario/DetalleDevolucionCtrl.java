/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.inventario;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.dto.CatalogoDTO;
import sv.com.diserv.liquidaciones.ejb.MovimientosDetBeanLocal;
import sv.com.diserv.liquidaciones.ejb.PersonasBeanLocal;
import sv.com.diserv.liquidaciones.entity.Movimientos;
import sv.com.diserv.liquidaciones.entity.Personas;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.web.ui.personas.DetalleClienteCtrl;
import sv.com.diserv.web.ui.personas.rendered.CatalogoItemRenderer;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

/**
 *
 * @author abraham.acosta
 */
public class DetalleDevolucionCtrl extends BaseController{
    
     static final Logger logger = Logger.getLogger(DetalleDevolucionCtrl.class.getCanonicalName());
    private static final long serialVersionUID = -546886879998950489L;
    protected Window detalleDevolucionWindow;
    protected Panel panelInformacionDevolucion;
    protected Intbox txtNumDoc;
    protected Intbox txtNumDoc1;
    protected Datebox txtfechaDevolucion;
    protected Datebox txtfechaDevolucion1;
    protected Combobox cmbVendedor;
    protected Textbox nombreVendedor;
    protected Button btnActualizar;
    protected Button btnNuevo;
    protected Button btnEditar;
    protected Button btnGuardar;
    protected Button btnEliminar;
    protected Button btnCerrar;
    private ServiceLocator serviceLocator;
    private MovimientosDetBeanLocal movimientoDetBean;
    private PersonasBeanLocal personaBean;
    private Movimientos devolucionSelected;
    private transient Integer token;
    private ListaDevolucionesCtrl listaDevolucionesCtrl;
    
    public DetalleDevolucionCtrl()
    {
        logger.log(Level.INFO, "[DetalleDevolucionCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            movimientoDetBean = serviceLocator.getService(Constants.JNDI_MOVIMIENTOSDET_BEAN);
            
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
            
    }
    
     public void onCreate$detalleDevolucionWindow(Event event) throws Exception {
        doOnCreateCommon(this.detalleDevolucionWindow, event);
        MensajeMultilinea.doSetTemplate();
        if (this.args.containsKey("asignacionSelected")) {
            devolucionSelected = ((Movimientos) this.args.get("devolucionSelected"));
            setDevolucionSelected(devolucionSelected);
        }
        if (this.args.containsKey("token")) {
            this.token = ((Integer) this.args.get("token"));
            setToken(this.token);
        } else {
            setToken(Integer.valueOf(0));
        }
        if (this.args.containsKey("listaAsignacionCtrl")) {
            listaDevolucionesCtrl = ((ListaDevolucionesCtrl) this.args.get("listaDevolucionesCtrl"));
        }
        showDetalleDevoluciones();
        loadComboboxVendedor();
    }

    public Movimientos getDevolucionSelected() {
        return devolucionSelected;
    }

    public void setDevolucionSelected(Movimientos devolucionSelected) {
        this.devolucionSelected = devolucionSelected;
    }

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    private void showDetalleDevoluciones() {
        try {
            if (devolucionSelected != null) {
                doEditButton();
                loadDataFromEntity();
                
            } else {
                doNew();
            }
            detalleDevolucionWindow.doModal();
        } catch (Exception e) {
            e.printStackTrace();
        }
        btnGuardar.setVisible(false);
       // detalleDevolucionWindow.setHeight("370px");
    }

    private void loadComboboxVendedor() {
          List<Personas> listaVendedores;
         List<CatalogoDTO> listaCatalogoVendedores = new ArrayList<CatalogoDTO>();

      try {
                
                listaVendedores = personaBean.loadAllPersonaByTipoAndSucursal(2,1);
                List<Object> objectList = new ArrayList<Object>(listaVendedores);
                listaCatalogoVendedores = catalogosBeanLocal.loadAllElementosCatalogo(objectList, "idpersona", "nombre");
               
                if(listaCatalogoVendedores != null && listaCatalogoVendedores.size()>0){
                    ListModelList modelovendedor = new ListModelList(listaCatalogoVendedores);
                    cmbVendedor.setModel(modelovendedor);
                    cmbVendedor.setItemRenderer(new CatalogoItemRenderer());
                    cmbVendedor.setText("Seleccione un vendedor!!");
                    cmbVendedor.setReadonly(false);
                    cmbVendedor.setButtonVisible(true);
                }
                else{
                     cmbVendedor.setText("No existen vendedores registrados!!");
                     cmbVendedor.setReadonly(true);
                     cmbVendedor.setButtonVisible(false);
                     cmbVendedor.setDisabled(true);
                    }
                } catch (DiservBusinessException ex) {
                Logger.getLogger(DetalleClienteCtrl.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    private void doEditButton() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void loadDataFromEntity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void doNew() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     
}
