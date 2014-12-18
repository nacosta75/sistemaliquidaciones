/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.web.ui.sucursales;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.dto.BusquedaSucursalDTO;
import sv.com.diserv.liquidaciones.ejb.SucursalesBeanLocal;
import sv.com.diserv.liquidaciones.entity.Sucursales;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.web.ui.sucursales.ListaSucursalCtrl;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

/**
 *
 * @author abraham.acosta
 */
public class BuscarSucursalCtrl extends BaseController {
    
    
    private static final long serialVersionUID = -6102616129515843466L;
    private static final transient Logger logger = Logger.getLogger(BuscarSucursalCtrl.class);
    protected Window busquedaSucursalWindow;
    protected Intbox txtIdSucursal;
    protected Textbox txtDescSucursal;
    protected Textbox txtEncargado;
    protected Button btnBuscar;
    protected Button btnCerrar;
    private BusquedaSucursalDTO request;
    private SucursalesBeanLocal sucursalBean;
    private ServiceLocator serviceLocator;
    private ListaSucursalCtrl listaSucursalCtrl;
    private List<Sucursales> listaSucursales;

    public BuscarSucursalCtrl() {

        logger.info("[BuscarSucursalCtrll]");
        try {
            serviceLocator = ServiceLocator.getInstance();
            sucursalBean = serviceLocator.getService(Constants.JNDI_SUCURSAL_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.error(ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$busquedaSucursalWindow(Event event)
            throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("--> " + event.toString());
        }
        doOnCreateCommon(this.busquedaSucursalWindow, event);
        if (this.args.containsKey("listaSucursalCtrl")) {
            listaSucursalCtrl = ((ListaSucursalCtrl) this.args.get("listaSucursalCtrl"));
        }
        MensajeMultilinea.doSetTemplate();
        showBuscarClinteWindow();
    }

    public void onClick$btnBuscar(Event event) throws InterruptedException {
        if (logger.isDebugEnabled()) {
            logger.debug("--> " + event.toString());
        }
        doBuscar();
    }

    public void onClick$btnCerrar(Event event)
            throws InterruptedException {
        if (logger.isDebugEnabled()) {
            logger.debug("--> " + event.toString());
        }
        doClose();
    }

    private void doClose() {
        this.busquedaSucursalWindow.onClose();
    }

    private void showBuscarClinteWindow()
            throws InterruptedException {
        try {
            this.busquedaSucursalWindow.doModal();
        } catch (Exception e) {
            MensajeMultilinea.show(e.toString(), Constants.MENSAJE_TIPO_ERROR);

        }
    }

    public void doBuscar() {
        try {
            request = new BusquedaSucursalDTO();
            if (StringUtils.isEmpty(txtIdSucursal.getText())) {
                request.setIdsucursal(txtIdSucursal.getValue());
            }
            if (StringUtils.isNotEmpty(txtDescSucursal.getValue())) {
                request.setDescripcion(txtDescSucursal.getValue().toUpperCase());
            }
            if (StringUtils.isNotEmpty(txtEncargado.getValue())) {
                request.setDireccion(txtEncargado.getValue());
            }
           
            listaSucursales = sucursalBean.buscarSucursalByCriteria(request);

            if (!listaSucursales.isEmpty()) {
                listaSucursalCtrl.setTotalSucursales(listaSucursales.size());
                listaSucursalCtrl.getListBoxSucursal().setModel(new ListModelList(listaSucursales));
                //         listaSucursalCtrl.getListBoxSucursal().setItemRenderer(new SucursalItemRenderer());
            } else {
                listaSucursalCtrl.getListBoxSucursal().setEmptyMessage("No se encontraron registros con los criterios ingresados!!");
                MensajeMultilinea.show("No se encontraron sucursales con los criterios ingresados", Constants.MENSAJE_TIPO_ALERTA);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.toString(), Constants.MENSAJE_TIPO_ERROR);
        }

    }

    public BusquedaSucursalDTO getRequest() {
        return request;
    }

    public void setRequest(BusquedaSucursalDTO request) {
        this.request = request;
    }

    public ListaSucursalCtrl getListaSucursalCtrl() {
        return listaSucursalCtrl;
    }

    public void setListaSucursalCtrl(ListaSucursalCtrl listaSucursalCtrl) {
        this.listaSucursalCtrl = listaSucursalCtrl;
    }

    public List<Sucursales> getListaSucursals() {
        return listaSucursales;
    }

    public void setListaSucursals(List<Sucursales> listaSucursales) {
        this.listaSucursales = listaSucursales;
    }
}
