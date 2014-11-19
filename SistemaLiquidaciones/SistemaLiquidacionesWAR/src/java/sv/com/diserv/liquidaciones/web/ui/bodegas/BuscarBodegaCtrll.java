/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE FISCALIA GENERAL DE LA REPUBLICA  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author edwin.alvarenga@fgr.gob.sv
 * @author edwin alvarenga
 * Fiscalía General de la República 2014
 */
package sv.com.diserv.liquidaciones.web.ui.bodegas;

/**
 *
 * @author edwin.alvarenga
 */
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class BuscarBodegaCtrll extends BaseController {

    private static final long serialVersionUID = -6102616129515843465L;
    private static final transient Logger logger = Logger.getLogger(BuscarBodegaCtrll.class);
    protected Window busquedaClienteWindow;
    protected Intbox txtIdCliente;
    protected Textbox txtNombreCliente;
    protected Textbox txtNumeroNit;
    protected Textbox txtRegistroIva;
    protected Textbox txtCorreoElectronico;
    protected Textbox txtDepartamento;
    protected Textbox txtMunicipio;
    private BusquedaClienteDTO request;
    private ClienteBeanLocal clienteBean;
    private ServiceLocator serviceLocator;
    private ListaBodegaCtrl listaClienteCtrl;
    private List<Clientes> listaClientes;

    public BuscarBodegaCtrll() {

        logger.info("[BuscarClienteController]");
        try {
            serviceLocator = ServiceLocator.getInstance();
            clienteBean = serviceLocator.getService(Constants.JNDI_CLIENTE_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.error(ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$busquedaClienteWindow(Event event)
            throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("--> " + event.toString());
        }
        doOnCreateCommon(this.busquedaClienteWindow, event);
        if (this.args.containsKey("listaClienteCtrl")) {
            listaClienteCtrl = ((ListaBodegaCtrl) this.args.get("listaClienteCtrl"));
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
        this.busquedaClienteWindow.onClose();
    }

    private void showBuscarClinteWindow()
            throws InterruptedException {
        try {
            this.busquedaClienteWindow.doModal();
        } catch (Exception e) {
            MensajeMultilinea.show(e.toString(), Constants.MENSAJE_TIPO_ERROR);

        }
    }

    public void doBuscar() {
        try {
            request = new BusquedaClienteDTO();
            if (StringUtils.isEmpty(txtIdCliente.getText())) {
                request.setIdCliente(txtIdCliente.getValue());
            }
            if (StringUtils.isNotEmpty(txtNombreCliente.getValue())) {
                request.setNombreCliente(txtNombreCliente.getValue().toUpperCase());
            }
            if (StringUtils.isNotEmpty(txtRegistroIva.getValue())) {
                request.setNumeroIva(txtRegistroIva.getValue());
            }
            if (StringUtils.isNotEmpty(txtDepartamento.getValue())) {
                request.setDepartamento(txtDepartamento.getValue());
            }
            if (StringUtils.isNotEmpty(txtMunicipio.getValue())) {
                request.setMunicipio(txtMunicipio.getValue());
            }
            listaClientes = clienteBean.buscarClientesByCriteria(request);
            if (!listaClientes.isEmpty()) {
                listaClienteCtrl.setTotalClientes(listaClientes.size());
                listaClienteCtrl.getListBoxCliente().setModel(new ListModelList(listaClientes));
                listaClienteCtrl.getListBoxCliente().setItemRenderer(new ClienteItemRenderer());
            } else {
                listaClienteCtrl.getListBoxCliente().setEmptyMessage("No se encontraron registros con los criterios ingresados!!");
                MensajeMultilinea.show("No se encontraron clientes con los criterios ingresados", Constants.MENSAJE_TIPO_ALERTA);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.toString(), Constants.MENSAJE_TIPO_ERROR);
        }

    }

    public BusquedaClienteDTO getRequest() {
        return request;
    }

    public void setRequest(BusquedaClienteDTO request) {
        this.request = request;
    }

    public ListaBodegaCtrl getListaClienteCtrl() {
        return listaClienteCtrl;
    }

    public void setListaClienteCtrl(ListaBodegaCtrl listaClienteCtrl) {
        this.listaClienteCtrl = listaClienteCtrl;
    }

    public List<Clientes> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Clientes> listaClientes) {
        this.listaClientes = listaClientes;
    }
}
