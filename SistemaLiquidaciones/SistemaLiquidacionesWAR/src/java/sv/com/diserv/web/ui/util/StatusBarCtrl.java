/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE DISERV,SA DE CV  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author alvarenga.miranda@gmail.com
 */
package sv.com.diserv.web.ui.util;

import java.io.Serializable;
//import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Window;

/**
 * =======================================================================<br>
 * StatusBarController. <br>
 * =======================================================================<br>
 * Works with the EventQueues mechanism of zk. ALl needed components are created
 * in this class. In the zul-template declare only this controller with 'apply'
 * to a winStatusBar window component.<br>
 * 2
 * Declaration in the zul-file:<br>
 *
 * <pre>
 * < borderlayout >
 * . . .
 * < !-- STATUS BAR AREA -- >
 * < south id="south" border="none" margins="1,0,0,0" height="20px"
 * splittable="false" flex="true" >
 * < div id="divSouth" >
 *
 * < !-- The StatusBar. Comps are created in the Controller -- >
 * < window id="winStatusBar" apply="${statusBarCtrl}" border="none"
 * width="100%" height="100%" />
 *
 * < /div >
 * < /south >
 * < /borderlayout >
 * </pre>
 *
 * call in java to actualize a columns label:
 *
 * <pre>
 * EventQueues.lookup(&quot;userNameEventQueue&quot;, EventQueues.DESKTOP, true).publish(new Event(&quot;onChangeSelectedObject&quot;, null, &quot;new Value&quot;));
 * </pre>
 *
 * Spring bean declaration:
 *
 * <pre>
 * < !-- StatusBarController -->
 * < bean id="statusBarCtrl" class="de.forsthaus.webui.util.StatusBarCtrl"
 * scope="prototype">
 * < /bean>
 * </pre>
 *
 *
 *
 * @author sgerth
 *
 */
public class StatusBarCtrl extends GenericForwardComposer implements Serializable {

    private static final long serialVersionUID = 1L;
//    private final static Logger logger = Logger.getLogger(StatusBarCtrl.class);

    /*
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * All the components that are defined here and have a corresponding
     * component with the same 'id' in the zul-file are getting autowired by our
     *   GenericForwardComposer.
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */
    protected Window winStatusBar; // autowired
    // Used Columns
    private Column statusBarSelectedObject;
    private Column statusBarAppVersion;
    private Column statusBarTableSchema;
    // Localized labels for the columns
    private final String _labelSelectedObject = "Operación ... >>>";
    private final String labelFechaHoraIngreso = "Fecha / Hora ingreso :";
    private final String _labelTableSchema = "Role  : ";

    /**
     * Default constructor.
     */
    public StatusBarCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        // Listener for selected Record
        EventQueues.lookup("selectedObjectEventQueue", EventQueues.DESKTOP, true).subscribe(new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                System.out.println("event:" + event.toString());
                final String msg = (String) event.getData();
             StatusBarCtrl.this.statusBarSelectedObject.setLabel(StatusBarCtrl.this._labelSelectedObject + msg);
            }
        });

        // Listener for applicationVersion
        EventQueues.lookup("ingresoAppEventQueue", EventQueues.DESKTOP, true).subscribe(new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                System.out.println("event:" + event.toString());
                final String msg = (String) event.getData();
                StatusBarCtrl.this.statusBarAppVersion.setLabel(StatusBarCtrl.this.labelFechaHoraIngreso + msg);
            }
        });

        // Listener for TableSchemaName
        EventQueues.lookup("tableSchemaEventQueue", EventQueues.DESKTOP, true).subscribe(new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                System.out.println("event:" + event.toString());
                final String msg = (String) event.getData();
                StatusBarCtrl.this.statusBarTableSchema.setLabel(StatusBarCtrl.this._labelTableSchema + msg);
            }
        });

    }

    /**
     * Automatically called method from zk.
     *
     * @param event
     */
    public void onCreate$winStatusBar(Event event) {

        final Grid grid = new Grid();
        grid.setHeight("22px");
        grid.setStyle("padding: 0px;");
        grid.setParent(this.winStatusBar);
        final Columns columns = new Columns();
        columns.setSizable(false);
        columns.setParent(grid);
        this.statusBarSelectedObject = new Column();
        this.statusBarSelectedObject.setHeight("22px");
        this.statusBarSelectedObject.setLabel(this._labelSelectedObject);
        this.statusBarSelectedObject.setWidth("50%");
        this.statusBarSelectedObject.setStyle("background-color: #D6DCDE; color: blue;");
        this.statusBarSelectedObject.setParent(columns);

        this.statusBarAppVersion = new Column();
        this.statusBarAppVersion.setHeight("22px");
        this.statusBarAppVersion.setLabel(labelFechaHoraIngreso);
        this.statusBarAppVersion.setWidth("35%");
        this.statusBarAppVersion.setStyle("background-color: #D6DCDE;  color: blue;");
        this.statusBarAppVersion.setParent(columns);

        this.statusBarTableSchema = new Column();
        this.statusBarTableSchema.setHeight("22px");
        this.statusBarTableSchema.setLabel(this._labelTableSchema);
        this.statusBarTableSchema.setWidth("15%");
        this.statusBarTableSchema.setStyle("background-color: #D6DCDE; color: blue;");
        this.statusBarTableSchema.setParent(columns);

    }
}
