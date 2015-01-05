package sv.com.diserv.web.ui.personas;

/**
 *
 * @author sonia.garcia
 */
import sv.com.diserv.web.ui.personas.*;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.dto.BusquedaPersonaDTO;
import sv.com.diserv.liquidaciones.ejb.PersonasBeanLocal;
import sv.com.diserv.liquidaciones.entity.Personas;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

public class BuscarProveedorCtrl extends BaseController {

    private static final long serialVersionUID = -6102616129515843465L;
    private static final transient Logger logger = Logger.getLogger(BuscarProveedorCtrl.class);
    protected Window busquedaProveedorWindow;
    protected Intbox txtIdProveedor;
    protected Textbox txtNombreProveedor;
    protected Textbox txtNumeroNit;
    protected Textbox txtRegistroFiscal;
    protected Button btnBuscar;
    protected Button btnCerrar;
    private BusquedaPersonaDTO request;
    private PersonasBeanLocal personaBean;
    private ServiceLocator serviceLocator;
    private ListaProveedorCtrl listaProveedorCtrl;
    private List<Personas> listaClientes;

    public BuscarProveedorCtrl() {

        logger.info("[BuscarProveedorCtrll]");
        try {
            serviceLocator = ServiceLocator.getInstance();
            personaBean = serviceLocator.getService(Constants.JNDI_PERSONA_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.error(ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$busquedaProveedorWindow(Event event)
            throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("--> " + event.toString());
        }
        doOnCreateCommon(this.busquedaProveedorWindow, event);
        if (this.args.containsKey("listaProveedorCtrl")) {
            listaProveedorCtrl = ((ListaProveedorCtrl) this.args.get("listaProveedorCtrl"));
        }
        MensajeMultilinea.doSetTemplate();
        showBuscarProveedorWindow();
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
        this.busquedaProveedorWindow.onClose();
    }

    private void showBuscarProveedorWindow()
            throws InterruptedException {
        try {
            this.busquedaProveedorWindow.doModal();
        } catch (Exception e) {
            MensajeMultilinea.show(e.toString(), Constants.MENSAJE_TIPO_ERROR);

        }
    }

    public void doBuscar() {
        try {
            request = new BusquedaPersonaDTO();
            if (StringUtils.isNotEmpty(txtIdProveedor.getText())) {
                request.setIdPersona(txtIdProveedor.getValue());
            }
            if (StringUtils.isNotEmpty(txtNombreProveedor.getValue())) {
                request.setNombre(txtNombreProveedor.getValue().toUpperCase());
            }
            if (StringUtils.isNotEmpty(txtNumeroNit.getValue())) {
                request.setNit(txtNumeroNit.getValue());
            }
            if (StringUtils.isNotEmpty(txtRegistroFiscal.getValue())) {
                request.setNumeroRegistro(txtRegistroFiscal.getValue());
            }
            
            request.setTipoPersona(3);
            listaClientes = personaBean.buscarPersonaByCriteria(request);

            if (!listaClientes.isEmpty()) {
                listaProveedorCtrl.setTotalProveedores(listaClientes.size());
                listaProveedorCtrl.getListBoxProveedor().setModel(new ListModelList(listaClientes));
                 doClose();
            } else {
                listaProveedorCtrl.getListBoxProveedor().setEmptyMessage("No se encontraron registros con los criterios ingresados!!");
                MensajeMultilinea.show("No se encontraron proveedores con los criterios ingresados", Constants.MENSAJE_TIPO_ALERTA);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.toString(), Constants.MENSAJE_TIPO_ERROR);
        }

    }

    public BusquedaPersonaDTO getRequest() {
        return request;
    }

    public void setRequest(BusquedaPersonaDTO request) {
        this.request = request;
    }

    public ListaProveedorCtrl getListaProveedorCtrl() {
        return listaProveedorCtrl;
    }

    public void setListaProveedorCtrl(ListaProveedorCtrl listaProveedorCtrl) {
        this.listaProveedorCtrl = listaProveedorCtrl;
    }

    public List<Personas> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Personas> listaClientes) {
        this.listaClientes = listaClientes;
    }
}
