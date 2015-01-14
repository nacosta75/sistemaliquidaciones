package sv.com.diserv.web.ui.asignaciones;

/**
 *
 * @author sonia.garcia
 */
import java.util.ArrayList;
import java.util.HashMap;
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
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.dto.BusquedaLoteExistenciaDTO;
import sv.com.diserv.liquidaciones.dto.ConsolidadoAsignacionesDTO;
import sv.com.diserv.liquidaciones.ejb.ArticulosBeanLocal;
import sv.com.diserv.liquidaciones.ejb.LotesExistenciasBeanLocal;
import sv.com.diserv.liquidaciones.entity.Articulos;
import sv.com.diserv.liquidaciones.entity.LotesExistencia;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.web.ui.asignaciones.render.ConsolidadoItemRenderer;
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
    private ArticulosBeanLocal articulosBean;
    private ServiceLocator serviceLocator;
    private List<LotesExistencia> listaExistencias;
    private Listbox listBoxAticulos;
    private DetalleAsignacionCtrl listaSeleccionados;
    
    private List<LotesExistencia> lotesPaginaAnterior = new ArrayList<LotesExistencia>();
    private List<ConsolidadoAsignacionesDTO> consolidadoPaginaAnterior = new ArrayList<ConsolidadoAsignacionesDTO>();
    
     //contadores pagina
    private Integer totalArticulos;
    private Integer numeroPaginInicio = 1;

    public BuscarArticuloCtrl() {

        logger.info("[BuscarArticuloCtrl]");
        try {
            serviceLocator = ServiceLocator.getInstance();
            loteExistenciaBean = serviceLocator.getService(Constants.JNDI_LOTESEXISTENCIAS_BEAN);
            articulosBean = serviceLocator.getService(Constants.JNDI_ARTICULOS_BEAN);
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
            listaSeleccionados = ((DetalleAsignacionCtrl) this.args.get("listaArticulosCtrl"));
            
             List<Listitem> articulos = listaSeleccionados.getListBoxAticulos().getItems(); 
             List<Listitem> consolidados = listaSeleccionados.getListBoxAsignacion().getItems(); 
            
             for(Listitem item:articulos) {
                 LotesExistencia lote = (LotesExistencia) item.getAttribute("data");
                 lotesPaginaAnterior.add(lote);
             }
             
             for(Listitem item:consolidados) {
                 ConsolidadoAsignacionesDTO consol = (ConsolidadoAsignacionesDTO) item.getAttribute("data");
                 consolidadoPaginaAnterior.add(consol);
             }
  
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
            
            String lotesExcluir = StringUtils.EMPTY;
            for(LotesExistencia lote:lotesPaginaAnterior){
                lotesExcluir = lotesExcluir+lote.getIdlote()+",";
            }
            
            if(StringUtils.isNotEmpty(lotesExcluir))
                request.setLotes(lotesExcluir.substring(0,lotesExcluir.length()-1));
                    
            listaExistencias = loteExistenciaBean.buscarLoteByCriteria(request);

            if (!listaExistencias.isEmpty()) {
                if (listaExistencias.size() > 0) {
                    setTotalArticulos(listaExistencias.size());
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

    public void onClick$btnAsignar(Event event) throws InterruptedException {
        List<Listitem> items =listBoxAticulos.getItems(); 
        List<LotesExistencia> lotes = new ArrayList<LotesExistencia>();
        List<LotesExistencia> suma = new ArrayList<LotesExistencia>();
        List<ConsolidadoAsignacionesDTO> consolidado = new ArrayList<ConsolidadoAsignacionesDTO>();
        HashMap<Integer,Integer> elementos = new HashMap<Integer, Integer>();
    for(Listitem item :items){
        if(item.isSelected()){
            LotesExistencia lote = (LotesExistencia) item.getAttribute("data"); 
            lotes.add(lote);
            if(!elementos.containsKey(lote.getIdarticulo().getIdarticulo())){
                elementos.put(lote.getIdarticulo().getIdarticulo(), 1);
                suma.add(lote);
            }
            else {
               Integer cantidad=  elementos.get(lote.getIdarticulo().getIdarticulo()).intValue();
               cantidad = cantidad+1;
               elementos.remove(lote.getIdarticulo().getIdarticulo());
               elementos.put(lote.getIdarticulo().getIdarticulo(), cantidad);
            }
                
        }
    }
    
    for(LotesExistencia lote1: lotesPaginaAnterior){
        
        if(!elementos.containsKey(lote1.getIdarticulo().getIdarticulo())){
                elementos.put(lote1.getIdarticulo().getIdarticulo(), 1);
                suma.add(lote1);
            }
            else {
               Integer cantidad=  elementos.get(lote1.getIdarticulo().getIdarticulo()).intValue();
               cantidad = cantidad+1;
               elementos.remove(lote1.getIdarticulo().getIdarticulo());
               elementos.put(lote1.getIdarticulo().getIdarticulo(), cantidad);
            }
        
         lotes.add(lote1);
    }
    
    for(LotesExistencia lote: suma){
        ConsolidadoAsignacionesDTO consol = new ConsolidadoAsignacionesDTO();
            try {
                Articulos articulo = articulosBean.loadArticuloByID(lote.getIdarticulo().getIdarticulo());
                consol.setIdArticulo(lote.getIdarticulo().getIdarticulo());
                consol.setCodigoArticulo(articulo.getCodarticulo());
                consol.setDescripcion(articulo.getDescarticulo());
                Integer cantidad=  elementos.get(lote.getIdarticulo().getIdarticulo()).intValue();
                consol.setCantidad(cantidad);
                if(lote.getIdarticulo().getCostopromact() != null)
                    consol.setPrecio(lote.getIdarticulo().getCostopromact().toString());
                consolidado.add(consol);
            } catch (DiservBusinessException ex) {
                java.util.logging.Logger.getLogger(BuscarArticuloCtrl.class.getName()).log(Level.SEVERE, null, ex);
            }
       
    }
    
    
    
    if (!lotes.isEmpty()) {
                getListaSeleccionados().setTotalArticulos(lotes.size());
                getListaSeleccionados().getListBoxAticulos().setModel(new ListModelList(lotes));
                getListaSeleccionados().getListBoxAticulos().setItemRenderer(new LotesItemRenderer());
                
                getListaSeleccionados().setTotalAsignaciones(consolidado.size());
                getListaSeleccionados().getListBoxAsignacion().setModel(new ListModelList(consolidado));
                getListaSeleccionados().getListBoxAsignacion().setItemRenderer(new ConsolidadoItemRenderer());
                doClose();
            } else {
                getListaSeleccionados().getListBoxAticulos().setEmptyMessage("No se han asignado articulos");
                MensajeMultilinea.show("No se han asignado articulos", Constants.MENSAJE_TIPO_ALERTA);
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

    /**
     * @return the listaSeleccionados
     */
    public DetalleAsignacionCtrl getListaSeleccionados() {
        return listaSeleccionados;
    }

    /**
     * @param listaSeleccionados the listaSeleccionados to set
     */
    public void setListaSeleccionados(DetalleAsignacionCtrl listaSeleccionados) {
        this.listaSeleccionados = listaSeleccionados;
    }

    /**
     * @return the totalArticulos
     */
    public Integer getTotalArticulos() {
        return totalArticulos;
    }

    /**
     * @param totalArticulos the totalArticulos to set
     */
    public void setTotalArticulos(Integer totalArticulos) {
        this.totalArticulos = totalArticulos;
    }

    /**
     * @return the numeroPaginInicio
     */
    public Integer getNumeroPaginInicio() {
        return numeroPaginInicio;
    }

    /**
     * @param numeroPaginInicio the numeroPaginInicio to set
     */
    public void setNumeroPaginInicio(Integer numeroPaginInicio) {
        this.numeroPaginInicio = numeroPaginInicio;
    }
    
    
}
