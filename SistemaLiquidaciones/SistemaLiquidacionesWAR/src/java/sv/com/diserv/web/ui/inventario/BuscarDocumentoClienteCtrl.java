/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 *   Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * 
 * @author edwin alvarenga
 * 
 */
package sv.com.diserv.web.ui.inventario;

/**
 *
 * @author edwin.alvarenga
 */
import sv.com.ats.ui.cliente.*;
import java.util.List;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.ats.business.dto.BusquedaFacturaDTO;
import sv.com.ats.business.ejb.ClienteBeanLocal;
import sv.com.ats.business.ejb.FacturacionBeanLocal;
import sv.com.ats.business.entity.Clientes;
import sv.com.ats.business.entity.Documentocliente;
import sv.com.ats.business.exception.ServiceLocatorException;
import sv.com.ats.business.util.Constants;
import sv.com.ats.business.util.ServiceLocator;
import sv.com.ats.ui.ordentrabajo.rendered.BusquedaClienteItemRenderer;
import sv.com.ats.web.ui.util.BaseController;
import sv.com.ats.web.ui.util.MensajeMultilinea;

public class BuscarDocumentoClienteCtrl extends BaseController {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(BuscarDocumentoClienteCtrl.class.getName());
    protected Window busquedaDocumentoClienteWindow;
    protected Combobox comboNombreCliente;
    protected Textbox txtIdCliente;
    protected Intbox txtIdDocumento;
    protected Datebox txtFechaInicio;
    protected Datebox txtFechaFinal;
    private BusquedaFacturaDTO request;
    private FacturacionBeanLocal facturacionBean;
    private ServiceLocator serviceLocator;
    private ListaDocumentoClienteCtrl listaDocumentoCtrl;
    private List<Documentocliente> listaDocumentoCliente;
    private List<Clientes> listaCliente;
    private Clientes clienteSelected;
    private ClienteBeanLocal clienteBean;

    public BuscarDocumentoClienteCtrl() {
        logger.info("[BuscarClienteController]");
        try {
            serviceLocator = ServiceLocator.getInstance();
            facturacionBean = serviceLocator.getService(Constants.JNDI_FACTURACION_BEAN);
            clienteBean = serviceLocator.getService(Constants.JNDI_CLIENTE_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.severe(ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$busquedaDocumentoClienteWindow(Event event) {
        logger.info("[onCreate$busquedaDocumentoClienteWindow]");
        try {
            doOnCreateCommon(this.busquedaDocumentoClienteWindow, event);
            MensajeMultilinea.doSetTemplate();
            if (this.args.containsKey("listaDocumentosCtrl")) {
                listaDocumentoCtrl = ((ListaDocumentoClienteCtrl) this.args.get("listaDocumentosCtrl"));
            }
            busquedaDocumentoClienteWindow.doModal();
        } catch (Exception ex) {
            MensajeMultilinea.show(ex.toString(), Constants.MENSAJE_TIPO_ERROR);
        }
    }

    public void onClick$btnBuscar(Event event) throws InterruptedException {
        logger.info("[onClick$btnBuscar]");
        doBuscar();
    }

    public void onClick$btnCerrar(Event event)
            throws InterruptedException {
        logger.log(Level.INFO, "--> {0}", event.toString());
        doClose();
    }

    private void doClose() {
        this.busquedaDocumentoClienteWindow.onClose();
    }

    public void doBuscar() {
        try {
            request = new BusquedaFacturaDTO();
            if (txtFechaFinal.getValue() != null) {
                request.setFechaFinal(txtFechaFinal.getValue());
            }
            if (txtFechaInicio.getValue() != null) {
                request.setFechaInicio(txtFechaInicio.getValue());
            }
            if (clienteSelected != null) {
                request.setIdCliente(clienteSelected.getIdCliente());
            }
            if (txtIdDocumento.getValue() != null) {
                request.setIdFactura(txtIdDocumento.getValue());
            }
            listaDocumentoCtrl.setRequest(request);
            listaDocumentoCtrl.reloadTotal();
            listaDocumentoCtrl.doRefreshModel(0);
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.toString(), Constants.MENSAJE_TIPO_ERROR);
        }
    }

    public void onChanging$comboNombreCliente(ForwardEvent e) {
        logger.info("[onChanging$comboNombreCliente]");
        InputEvent input = (InputEvent) e.getOrigin();
//        System.out.println("input value=" + input.getValue());
        try {
            listaCliente = clienteBean.loadAllClienteByLike(input.getValue().trim().toUpperCase());
            if (listaCliente.size() > 0) {
                comboNombreCliente.setModel(new ListModelList(listaCliente));
                comboNombreCliente.setItemRenderer(new BusquedaClienteItemRenderer());
            }
        } catch (Exception ex) {
            logger.info("[onChanging$comboNombreCliente]No se pudo cargar delitos");
            ex.printStackTrace();
        }
    }

    public void onSelect$comboNombreCliente(Event event) throws InterruptedException {
        logger.info("[onSelect$comboNombreCliente]");
        Comboitem item = this.comboNombreCliente.getSelectedItem();
        if (item != null) {
            clienteSelected = (Clientes) item.getAttribute("data");
            txtIdCliente.setValue(clienteSelected.getIdCliente().toString());
        } else {
            logger.info("[onSelect$comboNombreCliente]NO ha seleccionado cliente");
        }
    }

    public List<Clientes> getListaCliente() {
        return listaCliente;
    }

    public void setListaCliente(List<Clientes> listaCliente) {
        this.listaCliente = listaCliente;
    }

    public Clientes getClienteSelected() {
        return clienteSelected;
    }

    public void setClienteSelected(Clientes clienteSelected) {
        this.clienteSelected = clienteSelected;
    }
}