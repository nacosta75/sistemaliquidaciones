package sv.com.diserv.web.ui.util;

import java.io.Serializable;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Window;

public class ReportManagerWindow extends Window implements Serializable {
 
    private static final long serialVersionUID = -2835062913651549699L;
    private static final transient Logger logger = Logger.getLogger(ReportManagerWindow.class.getName());
    private transient Iframe report;
    private transient Component parent;
    private transient boolean modal;
    private transient byte[] file;
    private transient String nameFile;
    private transient String titleReport;
    private transient AMedia amedia;

    public ReportManagerWindow(Component parent, boolean modal, String titulo, String nombreArchivo, byte[] bytesArchivo) {
        logger.log(Level.INFO, "[ReportManagerWindow]Create report");
        this.parent = parent;
        this.modal = modal;
        this.file = bytesArchivo;
        this.nameFile = nombreArchivo;
        this.titleReport = titulo;
        this.setBorder("normal");
        createReport();
    }

    private void createReport() {
        logger.log(Level.INFO, "[createReport]Load Report={0}"+ titleReport);
        if (Boolean.valueOf(this.modal) == null) {
            this.modal = true;
        }
        this.report = new Iframe();
        amedia = new AMedia(nameFile, "pdf", "application/pdf", file);
        report.setHeight("100%");
        report.setWidth("100%");
        report.setContent(amedia);
        setParent(this.parent);
        setVisible(true);
        setTitle(titleReport);//Constants.TITLE_PRINT_MANAGER + 
        setMaximizable(true);
        setMinimizable(true);
        setSizable(true);
        setClosable(true);
        setHeight("80%");
        setWidth("80%");
        this.setBorder("normal");
        addEventListener("onClose", new EventListener() {
            public void onEvent(Event event) throws Exception {
                ReportManagerWindow.this.closeReportWindow();
            }
        });
        appendChild(this.report);
        if (this.modal) {
            try {
                doModal();
            } catch (SuspendNotAllowedException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void closeReportWindow() {
        logger.log(Level.INFO, "cerrar report y window");
        this.report.removeEventListener("onClose", new EventListener() {

            public void onEvent(Event event) throws Exception {
                ReportManagerWindow.this.closeReportWindow();
            }
        });
        this.report.detach();
        onClose();
    }
}
