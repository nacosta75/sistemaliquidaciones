/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.web.ui.inventario;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;
import sv.com.diserv.liquidaciones.ejb.LotesExistenciasBeanLocal;
import sv.com.diserv.liquidaciones.ejb.MovimientosBeanLocal;
import sv.com.diserv.liquidaciones.entity.LotesExistencia;
import sv.com.diserv.liquidaciones.entity.MovimientosDet;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.web.ui.inventario.rendered.LstLotesItemRendered;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;

/**
 *
 * @author noe
 */
public class ListaLotesCtrl extends BaseController{
    
    static final Logger logger = Logger.getLogger(ListaLotesCtrl.class.getCanonicalName());
    protected Window listaLoteWindow;
    protected Paging pagingLote;
    protected Listbox listBoxLote;
    protected Listheader listheaderId;
    protected Listheader listheaderTelefono;
    protected Listheader listheaderIcc;
    protected Listheader listheaderImei;
    
    private ServiceLocator serviceLocator;
    
    private MovimientosBeanLocal movimientoBean;
    private Integer numeroPaginInicio;
    private List<LotesExistencia> listaLoteExistencia;
    private LotesExistenciasBeanLocal lotesBean;
    private Integer totalLote;
    private MovimientosDet detalleMovimientoSelected;
    
     public ListaLotesCtrl() {
        logger.log(Level.INFO, "[ListaLotesCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            movimientoBean = serviceLocator.getService(Constants.JNDI_MOVIMIENTOS_BEAN);
            lotesBean  = serviceLocator.getService(Constants.JNDI_LOTESEXISTENCIAS_BEAN);
            numeroPaginInicio = 0;
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }
     
      public void onCreate$listaLoteWindow(Event event) throws Exception {
        logger.log(Level.INFO, "[onCreate$listaLoteWindow]Event:{0}", event.toString());
        doOnCreateCommon(listaLoteWindow,event);
        MensajeMultilinea.doSetTemplate();
         if (this.args.containsKey("detalleMovimientoSelected")) {
            detalleMovimientoSelected = ((MovimientosDet) this.args.get("detalleMovimientoSelected"));
            setDetalleMovimientoSelected(detalleMovimientoSelected);
        }
        pagingLote.setPageSize(getUserLogin().getRegistrosLista());
        pagingLote.setDetailed(true);

       
        //doCheckPermisos();
        reloadTotal();
        refreshModel(numeroPaginInicio);
//        setOrderDataByListHeaderAuditoriaEvaluaciones();
//        setOrderDataByListHeaderAuditoriaIndicadores();
    }
      
      public void reloadTotal() {
        try {
            totalLote = lotesBean.countAllLotesByMovArticulo(detalleMovimientoSelected.getIdarticulo().getIdarticulo(), detalleMovimientoSelected.getIdmov().getIdmov());
            if (totalLote != null) {
                setTotalLote(totalLote);
            } else {
                logger.info("[onCreate$listaClienteWindow]No se pudo obtener total registros");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshModel(int activePage) throws DiservBusinessException {
       logger.log(Level.INFO, "[showDetalleCompra][refreshModel]Recargar detalle");
        if (detalleMovimientoSelected != null) {
            listaLoteExistencia = lotesBean.loadAllLoteByMovimiento(activePage * getUserLogin().getRegistrosLista(), getUserLogin().getRegistrosLista(), detalleMovimientoSelected.getIdmov().getIdmov());
            if (listaLoteExistencia.size() > 0) {
                listBoxLote.setModel(new ListModelList(listaLoteExistencia));
                listBoxLote.setItemRenderer(new LstLotesItemRendered());
                pagingLote.setTotalSize(getTotalLote());
            } else {
                logger.info("No se cargaron registros");
                listBoxLote.setModel(new ListModelList(listaLoteExistencia));
                listBoxLote.setEmptyMessage("lote no tiene items agregados");
            }
        }
    }


    public List<LotesExistencia> getListaLoteExistencia() {
        return listaLoteExistencia;
    }

    public void setListaLoteExistencia(List<LotesExistencia> listaLoteExistencia) {
        this.listaLoteExistencia = listaLoteExistencia;
    }

    public Integer getTotalLote() {
        return totalLote;
    }

    public void setTotalLote(Integer totalLote) {
        this.totalLote = totalLote;
    }

    public MovimientosDet getDetalleMovimientoSelected() {
        return detalleMovimientoSelected;
    }

    public void setDetalleMovimientoSelected(MovimientosDet detalleMovimientoSelected) {
        this.detalleMovimientoSelected = detalleMovimientoSelected;
    }

    public Integer getNumeroPaginInicio() {
        return numeroPaginInicio;
    }

    public void setNumeroPaginInicio(Integer numeroPaginInicio) {
        this.numeroPaginInicio = numeroPaginInicio;
    }
    
    
    
}
