/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.lineas;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.dto.BusquedaLineaArticuloDTO;
import sv.com.diserv.liquidaciones.ejb.LineaArticuloBeanLocal;
import sv.com.diserv.liquidaciones.entity.LineaArticulo;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;

import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

/**
 *
 * @author abraham.acosta
 */
public class BuscarLineaCtrl extends BaseController{
    
    private static final long serialVersionUID = -6102616129515843469L;
    private static final transient Logger logger = Logger.getLogger(BuscarLineaCtrl.class);
    
    protected Window busquedaLineaWindow;
    protected Intbox txtIdLinea;
    protected Textbox txtDescripcion;
    protected Checkbox checkActiva;
    protected Button btnBuscar;
    protected Button btnCerrar;
    private BusquedaLineaArticuloDTO request;
    private LineaArticuloBeanLocal lineasBean;
    private ServiceLocator serviceLocator;
    private ListaLineaArticuloCtrl listaLineasCtrl;
    private List<LineaArticulo> listaLineas;
    
    public BuscarLineaCtrl()
    {
    
        logger.info("[BuscarLineaCtrll]");
        try {
            serviceLocator = ServiceLocator.getInstance();
            lineasBean = serviceLocator.getService(Constants.JNDI_LINEAS_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.error(ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }
    
    
     public void onCreate$busquedaLineaWindow(Event event)
            throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("--> " + event.toString());
        }
        doOnCreateCommon(this.busquedaLineaWindow, event);
        if (this.args.containsKey("listaLineasCtrl")) {
            listaLineasCtrl = ((ListaLineaArticuloCtrl) this.args.get("listaSucursalCtrl"));
        }
        MensajeMultilinea.doSetTemplate();
        showBuscarLineaWindow();
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
        this.busquedaLineaWindow.onClose();
    }

    private void showBuscarLineaWindow()
            throws InterruptedException {
        try {
            this.busquedaLineaWindow.doModal();
        } catch (Exception e) {
            MensajeMultilinea.show(e.toString(), Constants.MENSAJE_TIPO_ERROR);

        }
    }

    public void doBuscar() {
        try {
            request = new BusquedaLineaArticuloDTO();
            if (StringUtils.isEmpty(txtIdLinea.getText())) {
                request.setIdlinea(txtIdLinea.getValue());
            }
            if (StringUtils.isNotEmpty(txtDescripcion.getValue())) {
                request.setDesclinea(txtDescripcion.getValue().toUpperCase());
            }
           
           
            listaLineas = lineasBean.buscarLineaByCriteria(request);

            if (!listaLineas.isEmpty()) {
                listaLineasCtrl.setTotalLineas(listaLineas.size());
                listaLineasCtrl.getListBoxLinea().setModel(new ListModelList(listaLineas));
           
            } else {
                listaLineasCtrl.getListBoxLinea().setEmptyMessage("No se encontraron registros con los criterios ingresados!!");
                MensajeMultilinea.show("No se encontraron lineas con los criterios ingresados", Constants.MENSAJE_TIPO_ALERTA);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.toString(), Constants.MENSAJE_TIPO_ERROR);
        }

    }

    public ListaLineaArticuloCtrl getListaLineasCtrl() {
        return listaLineasCtrl;
    }

    public void setListaLineasCtrl(ListaLineaArticuloCtrl listaLineasCtrl) {
        this.listaLineasCtrl = listaLineasCtrl;
    }

    public List<LineaArticulo> getListaLineas() {
        return listaLineas;
    }

    public void setListaLineas(List<LineaArticulo> listaLineas) {
        this.listaLineas = listaLineas;
    }

    public BusquedaLineaArticuloDTO getRequest() {
        return request;
    }

    public void setRequest(BusquedaLineaArticuloDTO request) {
        this.request = request;
    }
    
    
    
}
