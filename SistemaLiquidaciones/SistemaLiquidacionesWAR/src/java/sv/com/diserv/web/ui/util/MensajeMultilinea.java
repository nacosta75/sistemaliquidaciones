/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE DISERV,SA DE CV  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author alvarenga.miranda@gmail.com
 */
package sv.com.diserv.web.ui.util;

/**
 *
 *alvarenga.miranda@gmail.com
 */
import java.io.Serializable;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import sv.com.diserv.liquidaciones.util.Constants;

public class MensajeMultilinea extends Messagebox implements Serializable {

    private static final long serialVersionUID = 1L;
    private static transient String _templ = "/WEB-INF/xhtml/util/multiMessagebox.zul";

    public static void doSetTemplate() {
        setTemplate(_templ);
    }

    public static final int show(String message, int tipo) {
        int response = 0;
        try {
            message = "\n" + message + "\n";
            if (tipo == Constants.MENSAJE_TIPO_INFO) {
                response = Messagebox.show(message, "Información " + Constants.MSG_SISTEMA, Messagebox.OK, Messagebox.INFORMATION);
            } else if (tipo == Constants.MENSAJE_TIPO_ALERTA) {
                response = Messagebox.show(message, "Alerta  " + Constants.MSG_SISTEMA, Messagebox.OK, Messagebox.EXCLAMATION);
            } else if (tipo == Constants.MENSAJE_TIPO_ERROR) {
                response = Messagebox.show(message, "Error " + Constants.MSG_SISTEMA, Messagebox.OK, Messagebox.ERROR);
            } else if (tipo == Constants.MENSAJE_TIPO_INTERRROGACION) {
                response = Messagebox.show(message, "Interrogante " + Constants.MSG_SISTEMA, Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;

    }

    public static int show(String message, int tipo, EventListener event) {
        int response = 0;
        try {
            message = "\n" + message + "\n";
            if (tipo == Constants.MENSAJE_TIPO_INFO) {
                response = Messagebox.show(message, "Información " + Constants.MSG_SISTEMA, Messagebox.OK, Messagebox.INFORMATION);
            } else if (tipo == Constants.MENSAJE_TIPO_ALERTA) {
                response = Messagebox.show(message, "Alerta  " + Constants.MSG_SISTEMA, Messagebox.OK, Messagebox.EXCLAMATION);
            } else if (tipo == Constants.MENSAJE_TIPO_ERROR) {
                response = Messagebox.show(message, "Error " + Constants.MSG_SISTEMA, Messagebox.OK, Messagebox.ERROR);
            } else if (tipo == Constants.MENSAJE_TIPO_INTERRROGACION) {
                response = Messagebox.show(message, "Interrogante " + Constants.MSG_SISTEMA, Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, event);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;

    }
}
