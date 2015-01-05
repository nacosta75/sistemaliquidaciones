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

public class BuscarVendedorCtrl extends BaseController {

    private static final long serialVersionUID = -6102616129515843465L;
    private static final transient Logger logger = Logger.getLogger(BuscarVendedorCtrl.class);
    protected Window busquedaVendedorWindow;
    protected Intbox txtIdVendedor;
    protected Textbox txtNombreVendedor;
    protected Textbox txtNumeroNit;
    protected Button btnBuscar;
    protected Button btnCerrar;
    private BusquedaPersonaDTO request;
    private PersonasBeanLocal personaBean;
    private ServiceLocator serviceLocator;
    private ListaVendedorCtrl listaVendedorCtrl;
    private List<Personas> listaClientes;

    public BuscarVendedorCtrl() {

        logger.info("[BuscarVendedorCtrll]");
        try {
            serviceLocator = ServiceLocator.getInstance();
            personaBean = serviceLocator.getService(Constants.JNDI_PERSONA_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.error(ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$busquedaVendedorWindow(Event event)
            throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("--> " + event.toString());
        }
        doOnCreateCommon(this.busquedaVendedorWindow, event);
        if (this.args.containsKey("listaVendedorCtrl")) {
            listaVendedorCtrl = ((ListaVendedorCtrl) this.args.get("listaVendedorCtrl"));
        }
        MensajeMultilinea.doSetTemplate();
        showBuscarVendedorWindow();
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
        this.busquedaVendedorWindow.onClose();
    }

    private void showBuscarVendedorWindow()
            throws InterruptedException {
        try {
            this.busquedaVendedorWindow.doModal();
        } catch (Exception e) {
            MensajeMultilinea.show(e.toString(), Constants.MENSAJE_TIPO_ERROR);

        }
    }

    public void doBuscar() {
        try {
            request = new BusquedaPersonaDTO();
            if (StringUtils.isNotEmpty(txtIdVendedor.getText())) {
                request.setIdPersona(txtIdVendedor.getValue());
            }
            if (StringUtils.isNotEmpty(txtNombreVendedor.getValue())) {
                request.setNombre(txtNombreVendedor.getValue().toUpperCase());
            }
            if (StringUtils.isNotEmpty(txtNumeroNit.getValue())) {
                request.setNit(txtNumeroNit.getValue());
            }
            
            request.setTipoPersona(2);
            listaClientes = personaBean.buscarPersonaByCriteria(request);

            if (!listaClientes.isEmpty()) {
                listaVendedorCtrl.setTotalVendedores(listaClientes.size());
                listaVendedorCtrl.getListBoxVendedor().setModel(new ListModelList(listaClientes));
                 doClose();
            } else {
                listaVendedorCtrl.getListBoxVendedor().setEmptyMessage("No se encontraron registros con los criterios ingresados!!");
                MensajeMultilinea.show("No se encontraron vendedores con los criterios ingresados", Constants.MENSAJE_TIPO_ALERTA);
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

    public ListaVendedorCtrl getListaVendedorCtrl() {
        return listaVendedorCtrl;
    }

    public void setListaVendedorCtrl(ListaVendedorCtrl listaVendedorCtrl) {
        this.listaVendedorCtrl = listaVendedorCtrl;
    }

    public List<Personas> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Personas> listaClientes) {
        this.listaClientes = listaClientes;
    }
}
