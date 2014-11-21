/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE FISCALIA GENERAL DE LA REPUBLICA  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author edwin.alvarenga@fgr.gob.sv
 * @author edwin alvarenga
 * Fiscalía General de la República 2014
 */
package sv.com.diserv.web.ui.bodegas;

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
import sv.com.diserv.liquidaciones.dto.BusquedaBodegaDTO;
import sv.com.diserv.liquidaciones.ejb.BodegasBeanLocal;
import sv.com.diserv.liquidaciones.entity.Bodegas;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

public class BuscarBodegaCtrll extends BaseController {

    private static final long serialVersionUID = -6102616129515843465L;
    private static final transient Logger logger = Logger.getLogger(BuscarBodegaCtrll.class);
    protected Window busquedaBodegaWindow;
    protected Intbox txtIdBodega;
    protected Textbox txtNombreBodega;
    protected Textbox txtNumeroNit;
    protected Textbox txtRegistroIva;
    protected Textbox txtCorreoElectronico;
    protected Textbox txtDepartamento;
    protected Textbox txtMunicipio;
    private BusquedaBodegaDTO request;
    private BodegasBeanLocal bodegaBean;
    private ServiceLocator serviceLocator;
    private ListaBodegaCtrl listaBodegaCtrl;
    private List<Bodegas> listaBodegas;

    public BuscarBodegaCtrll() {

        logger.info("[BuscarBodegaCtrll]");
        try {
            serviceLocator = ServiceLocator.getInstance();
            bodegaBean = serviceLocator.getService(Constants.JNDI_BODEGA_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.error(ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$busquedaBodegaWindow(Event event)
            throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("--> " + event.toString());
        }
        doOnCreateCommon(this.busquedaBodegaWindow, event);
        if (this.args.containsKey("listaBodegaCtrl")) {
            listaBodegaCtrl = ((ListaBodegaCtrl) this.args.get("listaBodegaCtrl"));
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
        this.busquedaBodegaWindow.onClose();
    }

    private void showBuscarClinteWindow()
            throws InterruptedException {
        try {
            this.busquedaBodegaWindow.doModal();
        } catch (Exception e) {
            MensajeMultilinea.show(e.toString(), Constants.MENSAJE_TIPO_ERROR);

        }
    }

    public void doBuscar() {
        try {
            request = new BusquedaBodegaDTO();
            if (StringUtils.isEmpty(txtIdBodega.getText())) {
                request.setIdBodega(txtIdBodega.getValue());
            }
            if (StringUtils.isNotEmpty(txtNombreBodega.getValue())) {
                request.setNombre(txtNombreBodega.getValue().toUpperCase());
            }
            if (StringUtils.isNotEmpty(txtRegistroIva.getValue())) {
                request.setDireccion(txtRegistroIva.getValue());
            }
            if (StringUtils.isNotEmpty(txtDepartamento.getValue())) {
                request.setEncargado(txtDepartamento.getValue());
            }
            if (StringUtils.isNotEmpty(txtMunicipio.getValue())) {
                request.setTelefono(txtMunicipio.getValue());
            }
            listaBodegas = bodegaBean.buscarBodegaByCriteria(request);

            if (!listaBodegas.isEmpty()) {
                listaBodegaCtrl.setTotalBodegas(listaBodegas.size());
                listaBodegaCtrl.getListBoxBodega().setModel(new ListModelList(listaBodegas));
                //         listaBodegaCtrl.getListBoxBodega().setItemRenderer(new BodegaItemRenderer());
            } else {
                listaBodegaCtrl.getListBoxBodega().setEmptyMessage("No se encontraron registros con los criterios ingresados!!");
                MensajeMultilinea.show("No se encontraron clientes con los criterios ingresados", Constants.MENSAJE_TIPO_ALERTA);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.toString(), Constants.MENSAJE_TIPO_ERROR);
        }

    }

    public BusquedaBodegaDTO getRequest() {
        return request;
    }

    public void setRequest(BusquedaBodegaDTO request) {
        this.request = request;
    }

    public ListaBodegaCtrl getListaBodegaCtrl() {
        return listaBodegaCtrl;
    }

    public void setListaBodegaCtrl(ListaBodegaCtrl listaBodegaCtrl) {
        this.listaBodegaCtrl = listaBodegaCtrl;
    }

    public List<Bodegas> getListaBodegas() {
        return listaBodegas;
    }

    public void setListaBodegas(List<Bodegas> listaBodegas) {
        this.listaBodegas = listaBodegas;
    }
}
