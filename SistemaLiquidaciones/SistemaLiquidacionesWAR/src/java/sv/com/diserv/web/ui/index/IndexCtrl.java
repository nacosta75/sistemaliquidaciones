package sv.com.diserv.web.ui.index;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;   
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.ComponentNotFoundException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zul.A;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;
import sv.com.diserv.web.ui.menu.MainMenuCtrl;
import sv.com.diserv.liquidaciones.dto.CustomUserDTO;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.UtilFormat;
import sv.com.diserv.web.ui.util.GFCBaseCtrl;
import sv.com.diserv.web.ui.util.MensajeMultilinea;
import sv.com.diserv.web.ui.util.UserWorkspace;

 

public class IndexCtrl extends GFCBaseCtrl implements Serializable {

    private static final long serialVersionUID = -3407055074703929527L;
    private final static Logger logger = Logger.getLogger(IndexCtrl.class);
    private CustomUserDTO userLogin;
    /*
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * All the components that are defined here and have a corresponding
     * component with the same 'id' in the zul-file are getting autowired by our
     * 'extends GFCBaseCtrl' GenericForwardComposer.
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */
    protected Menubar mainMenuBar; // autowired
    protected Label label_AppName; // autowired
    protected Intbox currentDesktopHeight; // autowired
    protected Intbox currentDesktopWidth; // autowired
    protected Checkbox CBtreeMenu; // autowired
    protected Tabs tabsIndexCenter; // autowired
    //  protected Timer timer;
    protected Checkbox chkbNotifications; // autowired
    protected Button btnCloseAllTabs; // autowired
    protected Button btnTestMain; // autowired
    protected A btnUserSettings; // autowired
    protected A btnAbout; // autowired
    // private transient OfficeService officeService;
    private final int centerAreaHeightOffset = 50;
    // private final String appName = ge"roleUser";
    // Controllers
    private MainMenuCtrl mainMenuCtrl;

    public IndexCtrl() {
        super();
    }

    public void onCreate$outerIndexWindow(Event event) throws Exception {
        this.mainMenuBar.setVisible(true);
        createMainTreeMenu(event);
        doDemoMode();
        MensajeMultilinea.doSetTemplate();
        userLogin = ((CustomUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        final String userName = userLogin.getUsername();
        final String fechaIngreso = UtilFormat.getFechaSistema();
        final String idUsuario = userLogin.getIpConnect();
        EventQueues.lookup("userNameEventQueue", EventQueues.DESKTOP, true).publish(new Event("onChangeUser", null, userName));
        EventQueues.lookup("tenantIdEventQueue", EventQueues.DESKTOP, true).publish(new Event("onChangeTenant", null, idUsuario));
        EventQueues.lookup("ingresoAppEventQueue", EventQueues.DESKTOP, true).publish(new Event("onChangeIngresoApp", null, fechaIngreso));
        EventQueues.lookup("tableSchemaEventQueue", EventQueues.DESKTOP, true).publish(new Event("onChangeTableSchema", null, "sdfasfas"));
        doCheckPermisos();
    }

    private void doCheckPermisos() {
        final UserWorkspace workspace = getUserWorkspace();
        btnCloseAllTabs.setVisible(workspace.isAllowed("btnCloseAllTabs"));
        btnTestMain.setVisible(workspace.isAllowed("btnTestMain"));
        btnAbout.setVisible(workspace.isAllowed("btnAbout"));
        btnUserSettings.setVisible(workspace.isAllowed("btnUserSettings"));
    }

    /**
     * Gets the current desktop height and width and <br> stores it in two
     * hidden intboxes components. <br> We use these values for calculating the
     * count of rows in the listboxes. <br>
     *
     * @param event
     * @throws Exception
     */
    public void onClientInfo(ClientInfoEvent event) throws Exception {
        setCurrentDesktopHeight(event.getDesktopHeight() - this.centerAreaHeightOffset);
        setCurrentDesktopWidth(event.getDesktopWidth());
    }

    public String doGetLoggedInUser() {
        final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userName;
    }

    /**
     * When the 'Logout' button is clicked.<br>
     *
     * @throws IOException
     */
    public void onClick$btnLogout() throws IOException {
        getUserWorkspace().doLogout();
    }

    /**
     * When the 'close all tabs' button is clicked.<br> 1. Get a list of all
     * open 'Tab'.<br> 2. Iterate through it and close the Tab if it's not the
     * Dashboard.<br> 3. The Dashboard itself is modified after creating to not
     * closable.<br>
     *
     * @throws IOException
     * @throws InterruptedException
     * @author Stephan Gerth
     */
    public void onClick$btnCloseAllTabs() throws IOException, InterruptedException {
        List<AbstractComponent> list = tabsIndexCenter.getChildren();
        try {
            while (!list.isEmpty()) {
                // get the sum of all Tab
                int i = list.size();
                // close all tabs, beginning with the last
                // because Dashboard is all times the first
                if (list.get(i - 1) instanceof Tab) {
                    if (StringUtils.equals(((Tab) list.get(i - 1)).getId(), "tab_menu_Item_Home")) {
                        break;
                    } else {
                        ((Tab) list.get(i - 1)).onClose();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.toString(), Constants.MENSAJE_TIPO_ERROR);
        }
    }

    /**
     * For DEMO MODE we set the office number manually.<br>
     */
    private void doDemoMode() {
        //   Office office = getOfficeService().getOfficeByID(Long.valueOf(1));
        // getUserWorkspace().setOffice(office);
    }

    /**
     * Creates the MainMenu as TreeMenu as default. <br>
     */
    private void createMainTreeMenu(Event event) {

        // get an instance of the borderlayout defined in the index.zul-file
        Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");

        // get an instance of the searched west layout area
        West west = bl.getWest();
        west.setFlex(true);

        // clear the WEST child comps
        west.getChildren().clear();

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("indexController", this);
        map.put("westValue", west);

        // create the components from the src/main/resources/mainmenu.xml and
        // put it in the WEST layout area
        // Overhand this controller self in a map
        Executions.createComponents("/WEB-INF/xhtml/mainTreeMenu.zul", west, map);
    }

    /**
     * Shows the welcome page in the borderlayouts CENTER area.<br>
     *
     * @throws InterruptedException
     */
    public void showWelcomePage() throws InterruptedException {
        // get an instance of the borderlayout defined in the zul-file
        Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");
        // get an instance of the searched CENTER layout area
        Center center = bl.getCenter();
        // clear the center child comps
        center.getChildren().clear();
        // call the zul-file and put it in the center layout area
        Executions.createComponents("/WEB-INF/pages/welcome.zul", center, null);
    }

    /**
     * When the 'My Settings' toolbarButton is clicked.<br>
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void onClick$btnUserSettings() throws IOException, InterruptedException {
        Window win = null;
        Window parentWin = (Window) Path.getComponent("/outerIndexWindow");

        try {
            win = (Window) Executions.createComponents("/WEB-INF/xhtml/sec_user/mySettings.zul", parentWin, null);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            MensajeMultilinea.show(e.getLocalizedMessage(), Constants.MENSAJE_TIPO_ERROR);
            win.detach();
        }
    }

    /**
     * When the 'My Settings' toolbarButton is clicked.<br>
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void onClick$btnTestMain() throws InterruptedException {
        Window win = null;
        Window parentWin = (Window) Path.getComponent("/outerIndexWindow");
        try {
            win = (Window) Executions.createComponents("/WEB-INF/pages/test/testMain.zul", parentWin, null);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            MensajeMultilinea.show(e.getLocalizedMessage(), Constants.MENSAJE_TIPO_ERROR);
            win.detach();
        }
    }

    /**
     * When the 'about' toolbarButton is clicked.<br>
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void onClick$btnAbout(Event event) throws IOException, InterruptedException {
        /* get an instance of the borderlayout defined in the zul-file */
        Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");
        /* get an instance of the searched CENTER layout area */
        Center center = bl.getCenter();
        Executions.createComponents("/WEB-INF/pages/about/aboutZksample2.zul", null, null);
    }

    /**
     * Creates a page from a zul-file in a tab in the center area of the
     * borderlayout. Checks if the tab is opened before. If yes than it selects
     * this tab.
     *
     * @param zulFilePathName The ZulFile Name with path.
     * @param tabName The tab name.
     * @throws InterruptedException
     */
    private void showPage(String zulFilePathName, String tabName) throws InterruptedException {

        try {
            // TODO get the parameter for working with tabs from the application
            // params
            final int workWithTabs = 1;
            if (workWithTabs == 1) {
                /* get an instance of the borderlayout defined in the zul-file */
                Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");
                /* get an instance of the searched CENTER layout area */
                Center center = bl.getCenter();
                // get the tabs component
                Tabs tabs = (Tabs) center.getFellow("divCenter").getFellow("tabBoxIndexCenter").getFellow("tabsIndexCenter");
                /**
                 * Check if the tab is already opened than select them and<br>
                 * go out of here. If not than create them.<br>
                 */
                Tab checkTab = null;
                try {
                    // checkTab = (Tab) tabs.getFellow(tabName);
                    checkTab = (Tab) tabs.getFellow("tab_" + tabName.trim());
                    checkTab.setSelected(true);
                } catch (final ComponentNotFoundException ex) {
                    // Ignore if can not get tab.
                    ex.printStackTrace();
                    System.out.println("eee");
                }
                if (checkTab == null) {
                    Tab tab = new Tab();
                    tab.setId("tab_" + tabName.trim());
                    tab.setLabel(tabName.trim());
                    tab.setClosable(true);
                    tab.setParent(tabs);
                    Tabpanels tabpanels = (Tabpanels) center.getFellow("divCenter").getFellow("tabBoxIndexCenter").getFellow("tabsIndexCenter").getFellow("tabpanelsBoxIndexCenter");
                    Tabpanel tabpanel = new Tabpanel();
                    tabpanel.setHeight("100%");
                    tabpanel.setStyle("padding: 0px;");
                    tabpanel.setParent(tabpanels);
                    /**
                     * Create the page and put it in the tabs area. If zul-file
                     * is not found, detach the created tab
                     */
                    try {
                        Executions.createComponents(zulFilePathName, tabpanel, null);
                        tab.setSelected(true);
                    } catch (final Exception e) {
                        tab.detach();
                        e.printStackTrace();
                    }
                }
            } else {
                /* get an instance of the borderlayout defined in the zul-file */
                Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");
                /* get an instance of the searched CENTER layout area */
                Center center = bl.getCenter();
                /* clear the center child comps */
                center.getChildren().clear();
                /**
                 * create the page and put it in the center layout area
                 */
                Executions.createComponents(zulFilePathName, center, null);
            }

            if (logger.isDebugEnabled()) {
                logger.debug("--> calling zul-file: " + zulFilePathName);
            }
        } catch (final Exception e) {
            e.printStackTrace();
            Messagebox.show(e.toString());
        }
    }

    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++//
    // ++++++++++++++++++ getter / setter +++++++++++++++++++//
    // ++++++++++++++++++++++++++++++++++++++++++++++++++++++//
    public void setCurrentDesktopHeight(int desktopHeight) {
        if (isTreeMenu() == true) {
            this.currentDesktopHeight.setValue(Integer.valueOf(desktopHeight));
        } else {
            this.currentDesktopHeight.setValue(Integer.valueOf(desktopHeight - 30));
        }
    }

    public int getCurrentDesktopHeight() {
        return this.currentDesktopHeight.getValue().intValue();
    }

    public void setCurrentDesktopWidth(int currentDesktopWidth) {
        this.currentDesktopWidth.setValue(Integer.valueOf(currentDesktopWidth));
    }

    public int getCurrentDesktopWidth() {
        return this.currentDesktopWidth.getValue().intValue();
    }

    public void setTreeMenu(boolean treeMenu) {
        this.CBtreeMenu.setChecked(treeMenu);
    }

    public boolean isTreeMenu() {
        return this.CBtreeMenu.isChecked();
    }

    public void setMainMenuCtrl(MainMenuCtrl mainMenuCtrl) {
        this.mainMenuCtrl = mainMenuCtrl;
    }

    public MainMenuCtrl getMainMenuCtrl() {
        return mainMenuCtrl;
    }
}
