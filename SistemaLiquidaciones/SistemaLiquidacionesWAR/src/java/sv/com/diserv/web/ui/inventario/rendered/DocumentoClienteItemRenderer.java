package sv.com.diserv.web.ui.inventario.rendered;

import org.zkoss.zk.ui.sys.ComponentsCtrl;

import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
//import sv.com.ats.business.entity.Documentocliente;
//import sv.com.ats.business.util.UtilFormat;

public class DocumentoClienteItemRenderer implements ListitemRenderer {

//    Documentocliente documento;

    @Override
    public void render(Listitem item, Object data) throws Exception {
//        documento = (Documentocliente) data;
//        Listcell lc = new Listcell(documento.getIdfactura() != null ? documento.getIdfactura().toString() : "N/D");
//        lc.setParent(item);
//
//        lc = new Listcell(documento.getIdCliente() != null ? documento.getIdCliente().getNombreCliente() : "N/D");
//        lc.setParent(item);
//
//        lc = new Listcell(documento.getFechaCreacion() != null ? UtilFormat.convertirFechaDDMMYYY(documento.getFechaCreacion()) : "N/D");
//        lc.setParent(item);
//
//        lc = new Listcell(documento.getIdEstado() != null ? documento.getIdEstado().getDesEstado() : "N/D");
//        lc.setParent(item);
//
//        lc = new Listcell(documento.getTipoDocumento() != null ? documento.getTipoDocumento() : "N/D");
//        lc.setParent(item);
//
//        lc = new Listcell(documento.getGravado() != null ? documento.getGravado().toString() : "0.00");
//        lc.setParent(item);
//
//        lc = new Listcell(documento.getValorExento() != null ? documento.getValorExento().toString() : "0.00");
//        lc.setParent(item);
//
//        lc = new Listcell(documento.getIva() != null ? documento.getIva().toString() : "0.00");
//        lc.setParent(item);
//
//        lc = new Listcell(documento.getTotal() != null ? documento.getTotal().toString() : "0.00");
//        lc.setParent(item);
//
//        item.setAttribute("data", data);
//        ComponentsCtrl.applyForward(item, "onClick=onClickedDocumento");
//
//        ComponentsCtrl.applyForward(item, "onDoubleClick=onDoubleClickedDocumentoCliente");
    }
}
