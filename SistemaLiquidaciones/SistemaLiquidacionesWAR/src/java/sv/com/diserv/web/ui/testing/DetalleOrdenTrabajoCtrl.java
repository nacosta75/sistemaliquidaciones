package sv.com.diserv.web.ui.testing;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

public class DetalleOrdenTrabajoCtrl extends BaseController {

    private static final Logger logger = Logger.getLogger(DetalleOrdenTrabajoCtrl.class.getName());
    private static final long serialVersionUID = -546886879998950467L;
    protected Window detalleOrdentrabajoWindow;
    protected Button btnActualizar;
    protected Button btnNuevo;
    protected Button btnEditar;
    protected Button btnGuardar;
    protected Button btnCerrar;
    protected Button btnVerFactura;
    protected Button btnGuardarRevision;
    protected Button btnGuardarTramite;
    protected Button btnEliminarTramite;
    protected Intbox txtIdCliente;
    protected Combobox comboNombreCliente;
    private Integer token;
    private ServiceLocator serviceLocator;
    private List<Clientes> listaClientes;
    private Clientes clienteSelected;

    public DetalleOrdenTrabajoCtrl() {
        logger.log(Level.INFO, "[ListaEvaluacionesAuditoriaCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            clientesBean = serviceLocator.getService(Constants.JNDI_CLIENTE_BEAN);
            ordenTrabajoBean = serviceLocator.getService(Constants.JNDI_ORDENTRABAJO_BEAN);
            bitacoraUsuarioBean = serviceLocator.getService(Constants.JNDI_BITACORA_USUARIO_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$detalleOrdentrabajoWindow(Event event) throws Exception {
        doOnCreateCommon(this.detalleOrdentrabajoWindow, event);
        MensajeMultilinea.doSetTemplate();
        btnGuardarRevision.setVisible(false);
        btnGuardarTramite.setVisible(false);


    }

    public void onChanging$comboNombreCliente(ForwardEvent e) {
        logger.log(Level.INFO, "[onChanging$comboNombreCliente]");
        InputEvent input = (InputEvent) e.getOrigin();
        System.out.println("input value=" + input.getValue());
        try {
            listaClientes = clientesBean.loadAllClienteByLike(input.getValue().trim());
            if (listaClientes.size() > 0) {
                comboNombreCliente.setModel(new ListModelList(listaClientes));
                comboNombreCliente.setItemRenderer(new BusquedaClienteItemRenderer());
            }
        } catch (Exception ex) {
            logger.log(Level.INFO, "[onChanging$comboDelitos]No se pudo cargar delitos");
            ex.printStackTrace();
        }
    }

    public void onSelect$comboNombreCliente(Event event) throws InterruptedException {
        logger.log(Level.INFO, "[onSelect$comboNombreCliente]");
        Comboitem item = this.comboNombreCliente.getSelectedItem();
        if (item != null) {
            clienteSelected = (Clientes) item.getAttribute("data");
            txtIdCliente.setValue(clienteSelected.getIdCliente());
        } else {
            logger.log(Level.INFO, "[onSelect$comboNombreCliente]NO ha seleccionado cliente");
        }
    }
}
