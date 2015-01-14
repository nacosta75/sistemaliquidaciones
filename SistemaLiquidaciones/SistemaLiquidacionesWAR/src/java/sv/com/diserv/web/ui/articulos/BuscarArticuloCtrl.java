/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.articulos;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.dto.BusquedaArticuloDTO;
import sv.com.diserv.liquidaciones.ejb.ArticulosBeanLocal;
import sv.com.diserv.liquidaciones.entity.Articulos;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

/**
 *
 * @author abraham.acosta
 */
public class BuscarArticuloCtrl extends BaseController{
    
    private static final long serialVersionUID = -6102616129515843444L;
    private static final transient Logger logger = Logger.getLogger(BuscarArticuloCtrl.class);
    protected Window busquedaArticuloWindow;
    protected Intbox txtIdArticulo;
    protected Textbox txtDescripcion;
    protected Textbox txtCodigo;
  
    protected Button btnBuscar;
    protected Button btnCerrar;
    private BusquedaArticuloDTO request;
    private ArticulosBeanLocal articuloBean;
    private ServiceLocator serviceLocator;
    private ListaArticulosCtrl listaArticuloCtrl;
    private List<Articulos> listaArticulos;

    public BuscarArticuloCtrl() {

        logger.info("[BuscarArticuloCtrll]");
        try {
            serviceLocator = ServiceLocator.getInstance();
            articuloBean = serviceLocator.getService(Constants.JNDI_ARTICULOS_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.error(ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$busquedaArticuloWindow(Event event)
            throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("--> " + event.toString());
        }
        doOnCreateCommon(this.busquedaArticuloWindow, event);
        if (this.args.containsKey("listaArticulosCtrl")) {
            listaArticuloCtrl = ((ListaArticulosCtrl) this.args.get("listaArticulosCtrl"));
        }
        MensajeMultilinea.doSetTemplate();
        showBuscarArticuloWindow();
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
        this.busquedaArticuloWindow.onClose();
    }

    private void showBuscarArticuloWindow()
            throws InterruptedException {
        try {
            this.busquedaArticuloWindow.doModal();
        } catch (Exception e) {
            MensajeMultilinea.show(e.toString(), Constants.MENSAJE_TIPO_ERROR);

        }
    }

    public void doBuscar() {
        try {
            request = new BusquedaArticuloDTO();
            if (StringUtils.isEmpty(txtIdArticulo.getText())) {
                request.setIdarticulo(txtIdArticulo.getValue());
            }
            if (StringUtils.isNotEmpty(txtCodigo.getValue())) {
                request.setCodarticulo(txtCodigo.getValue().toUpperCase());
            }
            if (StringUtils.isNotEmpty(txtDescripcion.getValue())) {
                request.setDescarticulo(txtDescripcion.getValue());
            }
           
            listaArticulos = articuloBean.buscarArticuloByCriteria(request);

            if (!listaArticulos.isEmpty()) {
                listaArticuloCtrl.setTotalArticulos(listaArticulos.size());
                listaArticuloCtrl.getListBoxArticulo().setModel(new ListModelList(listaArticulos));
                doClose();
            } else {
                listaArticuloCtrl.getListBoxArticulo().setEmptyMessage("No se encontraron registros con los criterios ingresados!!");
                MensajeMultilinea.show("No se encontraron Articulos con los criterios ingresados", Constants.MENSAJE_TIPO_ALERTA);
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

    public ListaArticulosCtrl getListaArticuloCtrl() {
        return listaArticuloCtrl;
    }

    public void setListaArticuloCtrl(ListaArticulosCtrl listaArticuloCtrl) {
        this.listaArticuloCtrl = listaArticuloCtrl;
    }

    public List<Articulos> getListaArticulos() {
        return listaArticulos;
    }

    public void setListaArticulos(List<Articulos> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }
    
}
