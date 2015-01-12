package sv.com.diserv.web.ui.asignaciones;

/**
 *
 * @author sonia.garcia
 */
import sv.com.diserv.web.ui.asignaciones.*;
import java.util.List;
import java.util.logging.Level;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Button;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.dto.BusquedaArticuloDTO;
import sv.com.diserv.liquidaciones.dto.BusquedaLoteExistenciaDTO;
import sv.com.diserv.liquidaciones.dto.BusquedaPersonaDTO;
import sv.com.diserv.liquidaciones.ejb.LotesExistenciasBeanLocal;
import sv.com.diserv.liquidaciones.ejb.PersonasBeanLocal;
import sv.com.diserv.liquidaciones.entity.LotesExistencia;
import sv.com.diserv.liquidaciones.entity.Personas;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.web.ui.articulos.ListaArticulosCtrl;
import static sv.com.diserv.web.ui.asignaciones.ListaAsignacionesCtrl.logger;
import sv.com.diserv.web.ui.asignaciones.render.AsignacionItemRenderer;
import sv.com.diserv.web.ui.asignaciones.render.LotesItemRenderer;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

public class BuscarArticuloCtrl extends BaseController {

    private static final long serialVersionUID = -6102616129515843465L;
    private static final transient Logger logger = Logger.getLogger(BuscarArticuloCtrl.class);
    protected Window busquedaArticuloWindow;
    protected Intbox txtIdArticulo;
    protected Textbox txtIcc;
    protected Textbox txtImei;
    protected Textbox txtTelefono;
    protected Button btnBuscar;
    protected Button btnCerrar;
    private BusquedaLoteExistenciaDTO request;
    private LotesExistenciasBeanLocal loteExistenciaBean;
    private ServiceLocator serviceLocator;
    private List<LotesExistencia> listaExistencias;
    private Paging pagingArticulos;
    private Listbox listBoxAticulos;
    
     //contadores pagina
    private Integer totalArticulos;
    private Integer numeroPaginInicio = 1;

    public BuscarArticuloCtrl() {

        logger.info("[BuscarArticuloCtrl]");
        try {
            serviceLocator = ServiceLocator.getInstance();
            loteExistenciaBean = serviceLocator.getService(Constants.JNDI_LOTESEXISTENCIAS_BEAN);
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
//            listaArticulosCtrl = ((ListaArticulosCtrl) this.args.get("listaArticulosCtrl"));
        }
        MensajeMultilinea.doSetTemplate();
        showBuscarClienteWindow();
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

    private void showBuscarClienteWindow()
            throws InterruptedException {
        try {
            this.busquedaArticuloWindow.doModal();
        } catch (Exception e) {
            MensajeMultilinea.show(e.toString(), Constants.MENSAJE_TIPO_ERROR);

        }
    }

    public void doBuscar() {
        try {
            request = new BusquedaLoteExistenciaDTO();
            if (StringUtils.isNotEmpty(txtIdArticulo.getText())) {
                request.setIdArticulo(txtIdArticulo.getValue());
            }
            if (StringUtils.isNotEmpty(txtIcc.getValue())) {
                request.setIcc(txtIcc.getValue().toUpperCase());
            }
            if (StringUtils.isNotEmpty(txtImei.getValue())) {
                request.setImei(txtImei.getValue());
            }
            if (StringUtils.isNotEmpty(txtTelefono.getValue())) {
                request.setTelefono(txtTelefono.getValue());
            }
            
            listaExistencias = loteExistenciaBean.buscarLoteByCriteria(request);

            if (!listaExistencias.isEmpty()) {
                if (listaExistencias.size() > 0) {
//                    logger.log(Level.INFO, "Registros cargados=={0}", listaExistencias.size());
                    pagingArticulos.setTotalSize(listaExistencias.size());
                    listBoxAticulos.setModel(new ListModelList(listaExistencias));
                    listBoxAticulos.setItemRenderer(new LotesItemRenderer());
                } else {
                    logger.info("No se cargaron registros");
                }
               
            } else {
                getListBoxAticulos().setEmptyMessage("No se encontraron registros con los criterios ingresados!!");
                MensajeMultilinea.show("No se encontraron clientes con los criterios ingresados", Constants.MENSAJE_TIPO_ALERTA);
            }
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.toString(), Constants.MENSAJE_TIPO_ERROR);
        }

    }

    public BusquedaLoteExistenciaDTO getRequest() {
        return request;
    }

    public void setRequest(BusquedaLoteExistenciaDTO request) {
        this.request = request;
    }

    public List<LotesExistencia> getListaExistencias() {
        return listaExistencias;
    }

    public void setListaExistencias(List<LotesExistencia> listaExistencias) {
        this.listaExistencias = listaExistencias;
    }

    /**
     * @return the pagingArticulos
     */
    public Paging getPagingArticulos() {
        return pagingArticulos;
    }

    /**
     * @param pagingArticulos the pagingArticulos to set
     */
    public void setPagingArticulos(Paging pagingArticulos) {
        this.pagingArticulos = pagingArticulos;
    }

    /**
     * @return the listBoxAticulos
     */
    public Listbox getListBoxAticulos() {
        return listBoxAticulos;
    }

    /**
     * @param listBoxAticulos the listBoxAticulos to set
     */
    public void setListBoxAticulos(Listbox listBoxAticulos) {
        this.listBoxAticulos = listBoxAticulos;
    }
    
}
