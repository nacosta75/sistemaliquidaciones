/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE DISERV,SA DE CV  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author alvarenga.miranda@gmail.com
 */
package sv.com.diserv.liquidaciones.web.ui.usuarios.util;

import java.io.Serializable;
//import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Space;
import org.zkoss.zul.Window;

/**
 * =======================================================================<br>
 * StatusBarController. <br>
 * =======================================================================<br>
 * Works with the EventQueues mechanism of zk. ALl needed components are created
 * in this class. In the zul-template declare only this controller with 'apply'
 * to a winStatusBar window component.<br>
 *
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
 *
 *
 */
public class UserBarCtrl extends GenericForwardComposer implements Serializable {

    private static final long serialVersionUID = 1L;
//    private final static Logger logger = Logger.getLogger(UserBarCtrl.class);

    /*
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * All the components that are defined here and have a corresponding
     * component with the same 'id' in the zul-file are getting autowired by our
     *   GenericForwardComposer.
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */
    protected Window winUserBar; // autowired
    // Used Labels
    private Label userLabel;
    private Label tenantLabel;
    private Label officeLabel;
    // Localized labels for the columns
    //private final String _UserLabel = Labels.getLabel("common.User") + ": ";
    //private final String _TenantIdLabel = Labels.getLabel("common.Tenant.ID") + ": ";
//    private final String _OfficeIdLabel = Labels.getLabel("common.Office.ID") + ": ";
    // Used Labels
    private Label userLabelText;
    private Label tenantLabelText;
    private Label officeLabelText;
    private String _UserText = "";
    private String _TenantIdText = "";
    private String _OfficeIdText = "";

    /**
     * Default constructor.
     */
    public UserBarCtrl() {
        super();
    }

    @Override
    public void doAfterCompose(Component window) throws Exception {
        super.doAfterCompose(window);

        // Listener for user
        EventQueues.lookup("userNameEventQueue", EventQueues.DESKTOP, true).subscribe(new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                final String msg = (String) event.getData();
                set_UserText(msg);
                doShowLabel();
            }
        });

        // Listener for TenantId
        EventQueues.lookup("tenantIdEventQueue", EventQueues.DESKTOP, true).subscribe(new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                final String msg = (String) event.getData();
                set_TenantIdText(msg);
                doShowLabel();
            }
        });

        // Listener for OfficeId
        EventQueues.lookup("officeIdEventQueue", EventQueues.DESKTOP, true).subscribe(new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                final String msg = (String) event.getData();
                set_OfficeIdText(msg);
                doShowLabel();
            }
        });

    }

    /**
     * Automatically called method from zk.
     *
     * @param event
     */
    public void onCreate$winUserBar(Event event) {

        Space space;

        winUserBar.setBorder("none");

        Hbox hbox = new Hbox();
        hbox.setParent(winUserBar);

        userLabel = new Label();
        userLabel.setStyle("text-align: right; font-size: 10px;");
        userLabel.setStyle("padding-left: 2px; text-align: right; color: white; font-size: 10px;");

        userLabel.setParent(hbox);
        userLabelText = new Label();
        userLabelText.setStyle("padding-left: 2px; text-align: right; color: white; font-size: 10px;");
        userLabelText.setParent(hbox);

        tenantLabel = new Label();
        tenantLabel.setStyle("text-align: right; font-size: 10px;");
        tenantLabel.setStyle("padding-left: 2px; text-align: right; color: white; font-size: 10px;");

        tenantLabel.setParent(hbox);
        tenantLabelText = new Label();
        tenantLabelText.setStyle("padding-left: 2px; text-align: right; color: white; font-size: 10px;");
        tenantLabelText.setParent(hbox);

        officeLabel = new Label();
        officeLabel.setStyle("text-align: right; font-size: 10px;");
        officeLabel.setParent(hbox);
        officeLabelText = new Label();
        officeLabelText.setStyle("padding-left: 2px; text-align: right; color: blue; font-size: 10px;");
        officeLabelText.setParent(hbox);

        space = new Space();
        space.setWidth("5px");
        space.setParent(hbox);
    }

    /**
     * Shows the labels with values.<br>
     */
    private void doShowLabel() {

        this.userLabel.setValue("User :");
        this.userLabelText.setValue(get_UserText());

        this.tenantLabel.setValue(" / Ip Conexion:");
        this.tenantLabelText.setValue(get_TenantIdText());

//        this.officeLabel.setValue(" / Id Unidad ");
//        this.officeLabelText.setValue(get_OfficeIdText());

    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++//
    // ++++++++++++++++++ getter / setter +++++++++++++++++++//
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++//
    public void set_UserText(String _UserText) {
        this._UserText = _UserText;
    }

    public String get_UserText() {
        return this._UserText;
    }

    public void set_TenantIdText(String _TenantIdText) {
        this._TenantIdText = _TenantIdText;
    }

    public String get_TenantIdText() {
        return this._TenantIdText;
    }

    public void set_OfficeIdText(String _OfficeIdText) {
        this._OfficeIdText = _OfficeIdText;
    }

    public String get_OfficeIdText() {
        return this._OfficeIdText;
    }
}
