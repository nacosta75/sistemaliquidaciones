/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 *   Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * 
 * @author edwin alvarenga
 * 
 */
package sv.com.diserv.web.ui.reportes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
//import sv.com.ats.business.dto.BusquedaFacturaDTO;
//import sv.com.ats.business.dto.ConsultaFacturaDTO;
//import sv.com.ats.business.dto.EncabezadoFacturaDTO;
//import sv.com.ats.business.dto.FacturasEntreFechasDTO;
//import sv.com.ats.business.dto.ListaBitacoraUsuarioReportDTO;
//import sv.com.ats.business.entity.ControlTeledespacho;
//import sv.com.ats.business.entity.Usuarios;
//import sv.com.ats.business.util.Constants;
//import sv.com.ats.business.util.UtilFormat;
//import sv.com.ats.web.ui.util.ReportManagerWindow;

/**
 *
 * @author programador
 */
    public class ManejadorReporte {

    private static final transient Logger logger = Logger.getLogger(ManejadorReporte.class.getCanonicalName());
//
//    /**
//     *
//     * @param lista
//     * @param parent
//     * @param tituloReporte
//     * @param rutaReporte
//     * @param nombreArchivo
//     */
//    public static void generarReporte(List<ControlTeledespacho> lista, Component parent, String tituloReporte, String rutaReporte, String nombreArchivo) {
//        logger.info("[generarReporte][generarReporte]INIT");
//        try {
//            String REPORT_PATH = Sessions.getCurrent().getWebApp().getRealPath(rutaReporte);
//            HashMap pars = new HashMap();
//            pars.put("REPORT_TITTLE", tituloReporte);
//            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(REPORT_PATH, pars, dataSource);
//            byte[] rep = JasperExportManager.exportReportToPdf(jasperPrint);
//            ReportManagerWindow jrw = new ReportManagerWindow(parent, true, tituloReporte, nombreArchivo += System.currentTimeMillis(), rep);
//        } catch (Exception e) {
//            logger.log(Level.INFO, "[reportes][generarReporte]Excepcion:" + e.toString());
//            e.printStackTrace();
//
//        }
//    }
//
//    public static void generarReporteDocumento(List<FacturasEntreFechasDTO> lista, BusquedaFacturaDTO request, Component parent, String tituloReporte, String rutaReporte, String nombreArchivo) {
//        logger.info("[generarReporte][documento cliente]INIT");
//        try {
//            String REPORT_PATH = Sessions.getCurrent().getWebApp().getRealPath(rutaReporte);
//            HashMap pars = new HashMap();
//            pars.put("REPORT_TITTLE", tituloReporte);
//            pars.put("fechaInicio", request.getFechaInicio());
//            pars.put("fechaFinal", request.getFechaFinal());
//            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(lista);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(REPORT_PATH, pars, dataSource);
//            byte[] rep = JasperExportManager.exportReportToPdf(jasperPrint);
//            ReportManagerWindow jrw = new ReportManagerWindow(parent, true, tituloReporte, nombreArchivo += System.currentTimeMillis(), rep);
//        } catch (Exception e) {
//            logger.log(Level.INFO, "[reportes][generarReporte]Excepcion:" + e.toString());
//            e.printStackTrace();
//
//        }
//    }
//
//    /**
//     *
//     * @param deta
//     * @param parent
//     * @param tituloReporte
//     * @param rutaReporte
//     * @param nombreArchivo
//     */
//    public static void generarReporte(ListaBitacoraUsuarioReportDTO deta, Component parent, String tituloReporte, String rutaReporte, String nombreArchivo) {
//        logger.info("[generarReporte][generarReporte]INIT");
//        try {
//            String REPORT_PATH = Sessions.getCurrent().getWebApp().getRealPath(rutaReporte);
//            HashMap pars = new HashMap();
//            pars.put("REPORT_TITTLE", tituloReporte);
//            pars.put("fechaInicio", deta.getFechaInicio());
//            pars.put("FechaFinal", deta.getFechaFinal());
//            pars.put("total_revision", deta.getTotalRevisiones());
//            pars.put("total_tramite", deta.getTotaltramites());
//            pars.put("total_elaboracion", deta.getTotalElaboracion());
//            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(deta.getListaOperaciones());
//            JasperPrint jasperPrint = JasperFillManager.fillReport(REPORT_PATH, pars, dataSource);
//            byte[] rep = JasperExportManager.exportReportToPdf(jasperPrint);
//            ReportManagerWindow jrw = new ReportManagerWindow(parent, true, tituloReporte, nombreArchivo += System.currentTimeMillis(), rep);
//        } catch (Exception e) {
//            logger.log(Level.INFO, "[reportes][generarReporte]Excepcion:" + e.toString());
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * *
//     *
//     * @param userLogin
//     * @param enca
//     * @param listaDetalle
//     * @param parent
//     * @param tituloReporte
//     * @param rutaReporte
//     * @param nombreArchivo
//     */
//    public static void loadReporteFactura(ConsultaFacturaDTO factura, Usuarios userLogin, Component parent, String tituloReporte, String nombreArchivo) {
//        logger.info("[generarReporte][loadReporteFactura]INIT");
//        List<EncabezadoFacturaDTO> listaDoc;
//        String letras;
////        String rutaSubReporte = null;
//        String rutaReporte;
//        try {
//            listaDoc = new ArrayList<EncabezadoFacturaDTO>();
//            if (factura.getDocumentoCliente().getTipoDocumento().equalsIgnoreCase(Constants.CODE_TIPO_DOCUMENTO_CCF)) {
//                letras = UtilFormat.convertirNumeroLetras(factura.getDocumentoCliente().getGravado());
////                rutaSubReporte = Sessions.getCurrent().getWebApp().getRealPath(Constants.RUTA_REPORTE_CCFF_SUBREPORT);
//                rutaReporte = Sessions.getCurrent().getWebApp().getRealPath(Constants.RUTA_REPORTE_CCFF);
//            } else {
//                letras = UtilFormat.convertirNumeroLetras(factura.getDocumentoCliente().getTotalFactura());
////                rutaSubReporte = Sessions.getCurrent().getWebApp().getRealPath(Constants.RUTA_REPORTE_FACT_EXPO_SUBREPORT);
//                rutaReporte = Sessions.getCurrent().getWebApp().getRealPath(Constants.RUTA_REPORTE_FACT_EXPO);
//            }
//            Map params = new HashMap();
//            params.put("totalFactura", factura.getDocumentoCliente().getGravado());
//            params.put("cantidaEnletras", letras);
//            params.put("nombreUsuario", userLogin.getNombreCompleto());
//            params.put("idfactura", factura.getDocumentoCliente().getIdFactura());
//            params.put("facturaDS", factura.getListaDetalle());
////            params.put("rutaSubreport", rutaSubReporte);
//            listaDoc.add(factura.getDocumentoCliente());
//            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaDoc);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(rutaReporte, params, dataSource);
//            byte[] rep = JasperExportManager.exportReportToPdf(jasperPrint);
//            ReportManagerWindow jrw = new ReportManagerWindow(parent, true, tituloReporte, nombreArchivo += System.currentTimeMillis(), rep);
//        } catch (Exception e) {
//            logger.log(Level.INFO, "[reportes][loadReporteFactura]Excepcion:" + e.toString());
//            e.printStackTrace();
//        }
//    }
}
