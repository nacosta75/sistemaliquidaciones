/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.inventario;


import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Priority;

import org.zkoss.zk.ui.Executions;
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
import sv.com.diserv.liquidaciones.dto.BusquedaPersonaDTO;
import sv.com.diserv.liquidaciones.ejb.PersonasBeanLocal;
import sv.com.diserv.liquidaciones.entity.Personas;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.liquidaciones.util.UtilFormat;
import static sv.com.diserv.web.ui.inventario.ListaComprasCtrl.logger;
import sv.com.diserv.web.ui.personas.rendered.PersonaItemRenderer;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.com.diserv.liquidaciones.entity.Movimientos;

/**
 *
 * @author abraham.acosta
 */
public class EncabezadoCompraCtrl extends BaseController{
    
    static final Logger logger = Logger.getLogger(EncabezadoCompraCtrl.class.toString());
    private static final long serialVersionUID = -5546886879998950489L;
    
    protected Window encabezadoCompraWindow;
    protected Button btnNew;
    protected Button btnEdit;
    protected Button btnDelete;
    protected Button btnSave;
    protected Button btnCancel;
    protected Button btnClose;
    protected Button btnImprimir;
    protected Textbox txtPersonaName;
    protected Textbox txtPersonaCod;
    protected Textbox txtFacturaNo;
    protected Textbox txtObservaciones;
    
    private BusquedaPersonaDTO request;
    
    //busqueda de proveedor
    protected Listbox listBoxCustomerSearch;
    protected Listheader listheader_CustNo;
    protected Listheader listheader_CustMatchcode;
    protected Listheader listheader_CustName1;
    protected Listheader listheader_CustCity;
    protected Listheader lhNoRegistro;
    
    // bandbox searchCustomer
    protected Bandbox bandbox_OrderDialog_CustomerSearch;
    private Object paging_OrderDialog_CustomerSearchList;
    private transient List<Personas> searchObjCustomer;
    private final int pageSizeSearchCustomers = 20;
    protected Intbox tb_Orders_SearchCustNo;
    protected Textbox tb_Orders_CustSearchMatchcode;
    protected Textbox tb_Orders_SearchCustName1;
    protected Button button_OrderList_OrderNameSearch;
    protected Button button_OrderList_NewOrder;
    
    private Integer totalMovimiento;
    private Integer numeroPaginInicio;
    private transient Integer token;
    
    private PersonasBeanLocal personaBean;
    private ServiceLocator serviceLocator;
    
    private Movimientos movimientoSelected;
    private ListaComprasCtrl listaComprasCtrl;
    
    
    public EncabezadoCompraCtrl()
    {
      logger.log(Level.INFO, "[EncabezadoCompraCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            //movimientoBean = serviceLocator.getService(Constants.JNDI_MOVIMIENTOS_BEAN);
            personaBean = serviceLocator.getService(Constants.JNDI_PERSONA_BEAN);
            numeroPaginInicio = 0;
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }
    
     public void onCreate$encabezadoCompraWindow(Event event) throws Exception {
        doOnCreateCommon(this.encabezadoCompraWindow, event);
        MensajeMultilinea.doSetTemplate();
        if (this.args.containsKey("movimientoSelected")) {
            movimientoSelected = ((Movimientos) this.args.get("movimientoSelected"));
            setMovimientoSelected(movimientoSelected);
        }
        if (this.args.containsKey("token")) {
            this.token = ((Integer) this.args.get("token"));
            setToken(this.token);
        } else {
            setToken(Integer.valueOf(0));
        }
        if (this.args.containsKey("listaComprasCtrl")) {
            listaComprasCtrl = ((ListaComprasCtrl) this.args.get("listaComprasCtrl"));
        }
      //  checkPermisos();
      showDetalleLineas();
      
         //userLogin.getUsuario().getIdusuario();
    }
    
    public void onClick$button_bbox_CustomerSearch_Close(Event event) {
        // logger.debug(event.toString());

        bandbox_OrderDialog_CustomerSearch.close();
    }


    public void onOpen$bandbox_OrderDialog_CustomerSearch(Event event) throws Exception {
		// logger.debug(event.toString());

		// not used listheaders must be declared like ->
        // lh.setSortAscending(""); lh.setSortDescending("")
        listheader_CustNo.setSortAscending(new FieldComparator("facturaNo", true));
        listheader_CustNo.setSortDescending(new FieldComparator("facturaNo", false));
        listheader_CustMatchcode.setSortAscending(new FieldComparator("codPersona", true));
        listheader_CustMatchcode.setSortDescending(new FieldComparator("codPersona", false));
        listheader_CustName1.setSortAscending(new FieldComparator("personaName", true));
        listheader_CustName1.setSortDescending(new FieldComparator("personaName", false));

        BuscarProveedor();
    }

    public void onClick$button_bbox_CustomerSearch_Search(Event event) {
               BuscarProveedor();
    }

    
    private void BuscarProveedor() {
        logger.log(Level.INFO, "[BuscarProveedor][refreshModel]Recargar proveedores");
        try {
            request = new BusquedaPersonaDTO();
            
            if (StringUtils.isNotEmpty(tb_Orders_SearchCustNo.getText())) {
                request.setIdPersona(tb_Orders_SearchCustNo.getValue());
            }
            if (StringUtils.isNotEmpty(tb_Orders_SearchCustName1.getValue())) {
                request.setNombre(tb_Orders_SearchCustName1.getValue().toUpperCase());
            }

            if (StringUtils.isNotEmpty(tb_Orders_CustSearchMatchcode.getValue())) {
                request.setNumeroRegistro(tb_Orders_CustSearchMatchcode.getValue());
            }
            
            request.setTipoPersona(3);
            searchObjCustomer = personaBean.buscarPersonaByCriteria(request);

            if (!searchObjCustomer.isEmpty()) {

                listBoxCustomerSearch.setModel(new ListModelList(searchObjCustomer));
                listBoxCustomerSearch.setItemRenderer(new PersonaItemRenderer());

            } else {
                
                listBoxCustomerSearch.setEmptyMessage("No se encontraron registros con los criterios ingresados!!");
                MensajeMultilinea.show("No se encontraron proveedores con los criterios ingresados", Constants.MENSAJE_TIPO_ALERTA);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.toString(), Constants.MENSAJE_TIPO_ERROR);
        }
    }
    
     public void onClick$button_OrderDialog_NewOrderPosition(Event event)
    {
         logger.log(Level.INFO, "[onclick$button_OrderDialog_NewOrderPosition]Event:{0}", event.toString());
         
        try {
            HashMap map = new HashMap();
            map.put("token", UtilFormat.getToken());
            map.put("listaComprasCtrl", this);
            Executions.createComponents("/WEB-INF/xhtml/inventario/detalleCompraDialog.zul", null, map);
        } catch (Exception a) {
            a.printStackTrace();
        }
        
    }

    public BusquedaPersonaDTO getRequest() {
        return request;
    }

    public void setRequest(BusquedaPersonaDTO request) {
        this.request = request;
    }

    public Integer getNumeroPaginInicio() {
        return numeroPaginInicio;
    }

    public void setNumeroPaginInicio(Integer numeroPaginInicio) {
        this.numeroPaginInicio = numeroPaginInicio;
    }

    public Movimientos getMovimientoSelected() {
        return movimientoSelected;
    }

    public void setMovimientoSelected(Movimientos movimientoSelected) {
        this.movimientoSelected = movimientoSelected;
    }

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    public Integer getTotalMovimiento() {
        return totalMovimiento;
    }

    public void setTotalMovimiento(Integer totalMovimiento) {
        this.totalMovimiento = totalMovimiento;
    }

    private void showDetalleLineas() {
          
      try {
            if (movimientoSelected != null) {
                doReadOnly(Boolean.TRUE);
                doEditButton();
                loadDataFromEntity();

            } else {
                doNew();
            }
            encabezadoCompraWindow.doModal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataFromEntity() {

        try {
            txtPersonaName.setValue(movimientoSelected.getIdpersona().getNombre());
            txtPersonaCod.setValue(movimientoSelected.getIdpersona().getNoRegistroFiscal());
            txtFacturaNo.setValue(movimientoSelected.getNodoc().toString());

//            loadCombobox();
//
//            cmbMarcaArticulo.setValue(articuloSelected.getIdmarca().getDescmarca());
//            cmbLineaArticulo.setValue(articuloSelected.getIdlinea().getDesclinea());
//            cmbTipoArticulo.setValue(articuloSelected.getIdtipoarticulo().getDescripcion());
//            cmbMedidaArticulo.setValue(articuloSelected.getIdumedida().getDescumedida());
//
//            if (articuloSelected.getCostopromact() != null) {
//                txtCostoProm.setValue(articuloSelected.getCostopromact());
//            } else {
//                txtCostoProm.setValue(BigDecimal.ZERO);
//            }
//
//            if (articuloSelected.getCostopromant() != null) {
//                txtCostoAnt.setValue(articuloSelected.getCostocompant());
//            } else {
//                txtCostoAnt.setValue(BigDecimal.ZERO);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void doReadOnly(Boolean TRUE) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void doEditButton() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void doNew() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
}