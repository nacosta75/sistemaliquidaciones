/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE DISERV,SA DE CV  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 *alvarenga.miranda@gmail.com
 * @author edwin alvarenga
 * DISERV,SA DE CV
 */
package sv.com.diserv.liquidaciones.web.ui.usuarios.util;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;

/**
 *
 * @author edwin.alvarenga
 */
public class ManejadorMensajes {

    public static void mostraMensajeOperacion(String mensaje) {
        try {
            EventQueues.lookup("selectedObjectEventQueue", EventQueues.DESKTOP, true).publish(new Event("onChangeSelectedObject", null, mensaje));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}