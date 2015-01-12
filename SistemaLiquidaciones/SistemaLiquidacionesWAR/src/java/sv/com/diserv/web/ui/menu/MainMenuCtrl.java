/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE noe acosta  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * .com
 */
package sv.com.diserv.web.ui.menu;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.ComponentNotFoundException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.North;
import org.zkoss.zul.Panelchildren;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Space;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.West;
import org.zkoss.zul.Window;
import sv.com.diserv.web.ui.index.IndexCtrl;
import sv.com.diserv.web.ui.menu.util.Menu;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.UserWorkspace;

/**
 *
 * Main menu controller. <br> <br> Added the buttons for expanding/closing the
 * menu tree. Calls the menu factory.
 *
 * 
 *
 *
 */
public class MainMenuCtrl extends BaseController implements Serializable {

    private static final long serialVersionUID = -909795057747345551L;
    private static final Logger logger = Logger.getLogger(MainMenuCtrl.class);

    /*
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     * All the components that are defined here and have a corresponding
     * component with the same 'id' in the zul-file are getting autowired by our
     * 'extends BaseCtrl' class wich extends Window and implements AfterCompose.
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */
    private Window mainMenuWindow; // autowire the IDSpace
    private Tree mainMenuTree;
    private Menu treeMainMenuFactory;
    private Menu dropDownMenuFactory;
    private static String bgColorInner = "white";
    // Controllers
    private IndexCtrl indexCtrl;
    private UserWorkspace workspace = getUserWorkspace();

    public void onCreate$mainMenuWindow(Event event) throws Exception {
        doOnCreateCommon(this.mainMenuWindow, event); // do the autowire stuff
        /*
         * Overhand this controller to the caller.
         */
        if (args.containsKey("indexController")) {
            setIndexCtrl((IndexCtrl) args.get("indexController"));
            // SET THIS CONTROLLER TO THE module's Parent/MainController
            getIndexCtrl().setMainMenuCtrl(this);
        }

        createMenu();
//        changeMenuPosition();
    }

    /**
     * Creates the mainMenu. <br>
     *
     * @throws InterruptedException
     */
    private void createMenu() {
        try {
            Toolbarbutton toolbarbutton;
            final Panelchildren gb = (Panelchildren) getMainMenuWindow().getFellowIfAny("panelChildren_menu");
            Space space = new Space();
            space.setHeight("5px");
            space.setParent(gb);
            // Hbox for the expand/collapse buttons
            final Hbox hbox = new Hbox();
            // hbox.setStyle("backgound-color: " + bgColorInner);
            hbox.setParent(gb);

            // ToolbarButton for expanding the menutree
            toolbarbutton = new Toolbarbutton();
            hbox.appendChild(toolbarbutton);
            toolbarbutton.setId("btnMainMenuExpandAll");
            toolbarbutton.setImage("/images/folder_open_16x16.gif");
            toolbarbutton.setTooltiptext(Labels.getLabel("btnFolderExpand.tooltiptext"));
            toolbarbutton.addEventListener("onClick", new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    onClick$btnMainMenuExpandAll(event);
                }
            });
            toolbarbutton = new Toolbarbutton();
            hbox.appendChild(toolbarbutton);
            toolbarbutton.setId("btnMainMenuCollapseAll");
            toolbarbutton.setImage("/images/folder_closed2_16x16.gif");
            toolbarbutton.setTooltiptext(Labels.getLabel("btnFolderCollapse.tooltiptext"));
            toolbarbutton.addEventListener("onClick", new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    onClick$btnMainMenuCollapseAll(event);
                }
            });

            toolbarbutton = new Toolbarbutton();
            hbox.appendChild(toolbarbutton);
            toolbarbutton.setId("btnMainMenuChange");
            toolbarbutton.setImage("/images/menu_16x16.gif");
            // toolbarbutton.setImage("/images/icons/combobox_16x16.gif");
            toolbarbutton.setTooltiptext(Labels.getLabel("btnMainMenuChange.tooltiptext"));
            toolbarbutton.addEventListener("onClick", new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    onClick$btnMainMenuChange(event);
                }
            });
            Separator separator = createSeparator(false);
            separator.setWidth("97%");
            separator.setStyle("background-color: " + bgColorInner);
            separator.setBar(false);
            separator.setParent(gb);

            separator = createSeparator(false);
            separator.setWidth("97%");
            separator.setBar(true);
            separator.setParent(gb);

            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++//
            // the menuTree
            mainMenuTree = new Tree();
            // tree.setSizedByContent(true);
            // tree.setZclass("z-dottree");
            mainMenuTree.setStyle("overflow:auto; border: none;");
            mainMenuTree.setParent(gb);
            final Treechildren treechildren = new Treechildren();
            mainMenuTree.appendChild(treechildren);
            // generate the treeMenu from the menuXMLFile
            getTreeMainMenuFactory().addMenu(treechildren);
            final Separator sep1 = new Separator();
            sep1.setWidth("97%");
            sep1.setBar(false);
            sep1.setParent(gb);
            /* as standard, call the menu principal  page */
            doCheckPermisos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doCheckPermisos() throws InterruptedException {
        workspace = getUserWorkspace();
        // get an instance of the borderlayout defined in the index.zul-file
        final Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");
        // get an instance of the searched west layout area
        final West west = bl.getWest();
        //        west.setVisible(workspace.isAllowed("west"));
//        if (workspace.isAllowed("menu_Administration")) {//Digitadores
//            showPage("/WEB-INF/xhtml/votacion/recoleccionVotacion.zul", "menu_Item_ingresoActas", "Ingreso de Actas");
//            showPage("/WEB-INF/xhtml/votacion/confirmacionVotacion.zul", "menu_Item_confirmacionActas", "Confirmación de Actas");
//            showPage("/WEB-INF/xhtml/usuarioJrvs/reporteUserJrv.zul", "menu_Item_reporteRecepcion", "Reporte Recepción de Actas");
//        }
//        if (workspace.isAllowed("MostrarTabDigitador")) {//Digitadores
//            showPage("/WEB-INF/xhtml/votacion/recoleccionVotacion.zul", "menu_Item_ingresoActas", "Ingreso de Actas");
//            showPage("/WEB-INF/xhtml/votacion/confirmacionVotacion.zul", "menu_Item_confirmacionActas", "Confirmación de Actas");
//            showPage("/WEB-INF/xhtml/usuarioJrvs/reporteUserJrv.zul", "menu_Item_reporteRecepcion", "Reporte Recepción de Actas");
//        } else if (workspace.isAllowed("MostrarTabAdminOficinaInformacion")) {//Administradores de oficina
//            showPage("/WEB-INF/xhtml/informacion/actasPorOficina.zul", "menu_Item_informacionActasOficina", "Cuadro de Mandos");
//            showPage("/WEB-INF/xhtml/usuarioJrvs/reporteUserJrv.zul", "menu_Item_reporteRecepcion", "Reporte Recepción de Actas");
//        } else if (workspace.isAllowed("MostrarTabAdminOficina")) {//Administradores de oficina
//            showPage("/WEB-INF/xhtml/configuraciones/usuarioJuntaReceptora.zul", "menu_Item_usuario_jrv", "Configurador JRV-Usuarios");
//            showPage("/WEB-INF/xhtml/votacion/recoleccionVotacion.zul", "menu_Item_ingresoActas", "Ingreso de Actas");
//            showPage("/WEB-INF/xhtml/votacion/confirmacionVotacion.zul", "menu_Item_confirmacionActas", "Confirmación de Actas");
//            showPage("/WEB-INF/xhtml/votacion/modificacionVotacion.zul", "menu_Item_modificacionActas", "Modificación de Actas confirmadas");
//        } else if (workspace.isAllowed("MostrarTabFiscalGeneral")) {//Fiscal general
//            showPage("/WEB-INF/xhtml/graficas/graficasResumen.zul", "menu_Item_informacionGrafica", "Tablero de Control");
//            showPage("/WEB-INF/xhtml/auditoria/actasPorOficinaPorZona.zul", "menu_Item_ControldeMandos", "Control de Mandos");
//        } else if (workspace.isAllowed("MostrarTabAuditor")) {//Auditor general
//            showPage("/WEB-INF/xhtml/auditoria/actasPorOficinaPorZona.zul", "menu_Item_ControldeMandos", "Control de Mandos");
//        } else if (workspace.isAllowed("MostrarTabDirector")) {//Director
//            showPage("/WEB-INF/xhtml/auditoria/actasPorOficinaPorZona.zul", "menu_Item_ControldeMandos", "Control de Mandos");
//        } else if (workspace.isAllowed("MostrarTabSoporteTecnicoInformacion")) {//Soporte tecnico informacion
//            showPage("/WEB-INF/xhtml/informacion/actasPorOficina.zul", "menu_Item_informacionActasOficina", "Cuadro de Mandos");
//            showPage("/WEB-INF/xhtml/usuarioJrvs/reporteUserJrv.zul", "menu_Item_reporteRecepcion", "Reporte Recepción de Actas");
//        } else if (workspace.isAllowed("MostrarTabSoporteTecnico")) {//Soporte tecnico
//            showPage("/WEB-INF/xhtml/votacion/recoleccionVotacion.zul", "menu_Item_ingresoActas", "Ingreso de Actas");
//            showPage("/WEB-INF/xhtml/votacion/confirmacionVotacion.zul", "menu_Item_confirmacionActas", "Confirmación de Actas");
//            showPage("/WEB-INF/xhtml/contingencias/administracionContingencias.zul", "menu_Item_generarRespaldos", "Contingencias");
//        } else if (workspace.isAllowed("MostrarTabDigitadorNacional")) {//Digitador nacional
//            showPage("/WEB-INF/xhtml/votacion/recoleccionVotacion.zul", "menu_Item_ingresoActas", "Ingreso de Actas");
//            showPage("/WEB-INF/xhtml/votacion/confirmacionVotacion.zul", "menu_Item_confirmacionActas", "Confirmación de Actas");
//            //showPage("/WEB-INF/xhtml/usuarioJrvs/reporteUserJrv.zul", "menu_Item_reporteRecepcion", "Reporte Recepción de Actas");
//        } else {//Administradores del sistema -- y directores varias zonas?
//            showPage("/WEB-INF/xhtml/menuPrincipal.zul", "menu_Item_Home", "Menu Inicio");
//        }
    }

    /**
     * Creates a seperator. <br>
     *
     * @param withBar <br> true=with Bar <br> false = without Bar <br>
     * @return Separator
     */
    private static Separator createSeparator(boolean withBar) {
        final Separator sep = new Separator();
        sep.setStyle("backgound-color: " + bgColorInner);
        sep.setBar(withBar);
        return sep;
    }

    /**
     * Inner class for the GuestBookListener.
     */
    public final class GuestBookListener implements EventListener {

        @Override
        public void onEvent(Event event) throws Exception {
            showPage("/WEB-INF/pages/guestbook/guestBookList.zul", "Guestbook", null);
        }
    }

    /**
     * Creates a page from a zul-file in a tab in the center area of the
     * borderlayout. Checks if the tab is opened before. If yes than it selects
     * this tab, if not than create it new.<br>
     *
     * @param zulFilePathName The ZulFile Name with path.
     * @param tabID The tab name ID.
     * @param differentLabel needed for the home/start/dashboard page
     * @throws InterruptedException
     */
    private void showPage(String zulFilePathName, String tabID, String tabLabel) throws InterruptedException {
        try {
            // TODO get the parameter for working with tabs from the application
            // params
            final int workWithTabs = 1;

            if (workWithTabs == 1) {

                /* get an instance of the borderlayout defined in the zul-file */
                final Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");
                /* get an instance of the searched CENTER layout area */
                final Center center = bl.getCenter();
                // get the tabs component
                final Tabs tabs = (Tabs) center.getFellow("divCenter").getFellow("tabBoxIndexCenter").getFellow("tabsIndexCenter");

                /**
                 * Check if the tab is already opened than select them and<br>
                 * go out of here. If not than create them.<br>
                 */
                Tab checkTab = null;
                try {
                    // checkTab = (Tab) tabs.getFellow(tabName);
                    checkTab = (Tab) tabs.getFellow("tab_" + tabID.trim());
                    checkTab.setSelected(true);
                } catch (final ComponentNotFoundException ex) {
                    // Ignore if can not get tab.
                }
                if (checkTab == null) {
                    final Tab tab = new Tab();
                    tab.setId("tab_" + tabID.trim());
                    if (tabLabel != null) {
                        tab.setLabel(tabLabel.trim());
                    }
//                    if (workspace.isAllowed("MostrarTabDigitador")) {
//                        tab.setClosable(false);
//                    } else if (workspace.isAllowed("MostrarTabSoporteTecnico") || workspace.isAllowed("MostrarTabSoporteTecnicoInformacion")) {
//                        tab.setClosable(false);
//                    } else if (workspace.isAllowed("MostrarTabAdminOficina") || workspace.isAllowed("MostrarTabAdminOficinaInformacion")) {
//                        tab.setClosable(false);
//                    } else if (workspace.isAllowed("MostrarTabFiscalGeneral")) {
//                        tab.setClosable(false);
//                    } else if (workspace.isAllowed("MostrarTabAuditor")) {
//                        tab.setClosable(false);
//                    } else if (workspace.isAllowed("MostrarTabDirector")) {
//                        tab.setClosable(false);
//                    } else {
//                        tab.setClosable(true);
//                    }
                    tab.setParent(tabs);
                    final Tabpanels tabpanels = (Tabpanels) center.getFellow("divCenter").getFellow("tabBoxIndexCenter").getFellow("tabsIndexCenter")
                            .getFellow("tabpanelsBoxIndexCenter");
                    final Tabpanel tabpanel = new Tabpanel();
                    tabpanel.setHeight("100%");
                    tabpanel.setStyle("padding: 0px;");
                    tabpanel.setParent(tabpanels);
                    /*
                     * create the page and put it in the tabs area
                     */
                    Executions.createComponents(zulFilePathName, tabpanel, null);
                    //tab.setSelected(true);

                } else {

                    /*
                     * create the page and put it in the center layout area
                     */
                    Executions.createComponents(zulFilePathName, center, null);
                }

                if (logger.isDebugEnabled()) {
                    logger.debug("--> calling zul-file: " + zulFilePathName);
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
            Messagebox.show(e.toString());
        }
    }

    /**
     * Expand the menuTree.<br>
     *
     * @param event
     * @throws Exception
     */
    public void onClick$btnMainMenuExpandAll(Event event) throws Exception {

        if (logger.isDebugEnabled()) {
            logger.debug("--> " + event.toString());
        }
        doCollapseExpandAll(getMainMenuWindow(), true);
    }

    /**
     * Collapse the menuTree.<br>
     *
     * @param event
     * @throws Exception
     */
    public void onClick$btnMainMenuCollapseAll(Event event) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("--> " + event.toString());
        }
        try {
            doCollapseExpandAll(getMainMenuWindow(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Changed the TreeMenu to a BarMenu depending on the same 'mainmenu.xml'
     * file.
     *
     * @param event
     * @throws Exception
     */
    public void onClick$btnMainMenuChange(Event event) {
        changeMenuPosition();
    }

    private void changeMenuPosition() {
        try {
            // set the MenuOffset for correct calculating content height.
            final Checkbox cb = (Checkbox) Path.getComponent("/outerIndexWindow/CBtreeMenu");
            cb.setChecked(false);
            UserWorkspace.getInstance().setTreeMenu(false);

            // get an instance of the borderlayout defined in the index.zul-file
            final Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");
            // get an instance of the searched west layout area
            final West west = bl.getWest();
            west.setVisible(false);

            final North north = bl.getNorth();
            north.setHeight(north.getHeight() + "px");

            final Div div = (Div) north.getFellow("divDropDownMenu");

            final Menubar menuBar = (Menubar) div.getFellow("mainMenuBar");
            menuBar.setVisible(true);

            // generate the menu from the menuXMLFile
            getDropDownMenuFactory().addMenu(menuBar);

            final Menuitem changeToTreeMenu = new Menuitem();
            changeToTreeMenu.setLabel(Labels.getLabel("menu_Item_backToTree"));
            changeToTreeMenu.setImage("/images/refresh_yellow_16x16.gif");
            changeToTreeMenu.setParent(menuBar);
            changeToTreeMenu.addEventListener("onClick", new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    // get an instance of the borderlayout defined in the index.zul-file
                    final Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");
                    // get an instance of the searched west layout area
                    final West west = bl.getWest();
                    west.setVisible(true);
                    final North north = bl.getNorth();
                    final Div div = (Div) north.getFellow("divDropDownMenu");
                    final Menubar menuBar = (Menubar) div.getFellow("mainMenuBar");
                    menuBar.getChildren().clear();
                    menuBar.setVisible(false);

                    // set the MenuOffset for correct calculating content height.
                    final Checkbox cb = (Checkbox) Path.getComponent("/outerIndexWindow/CBtreeMenu");
                    cb.setChecked(true);
                    UserWorkspace.getInstance().setTreeMenu(true);

                    // Refresh the whole page for setting correct sizes of the components
                    final Window win = (Window) Path.getComponent("/outerIndexWindow");
                    win.invalidate();

                }
            });
            // Refresh the whole page for setting correct sizes of the components
            final Window win = (Window) Path.getComponent("/outerIndexWindow");
            win.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doCollapseExpandAll(Component component, boolean aufklappen) {
        if (component instanceof Treeitem) {
            final Treeitem treeitem = (Treeitem) component;
            treeitem.setOpen(aufklappen);
        }
        final Collection<?> com = component.getChildren();
        if (com != null) {
            for (final Iterator<?> iterator = com.iterator(); iterator.hasNext();) {
                doCollapseExpandAll((Component) iterator.next(), aufklappen);
            }
        }
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    public Window getMainMenuWindow() {
        return this.mainMenuWindow;
    }

    public void setMainMenuWindow(Window mainMenuWindow) {
        this.mainMenuWindow = mainMenuWindow;
    }

    public void setIndexCtrl(IndexCtrl indexCtrl) {
        this.indexCtrl = indexCtrl;
    }

    public IndexCtrl getIndexCtrl() {
        return indexCtrl;
    }

    public Menu getTreeMainMenuFactory() {
        return treeMainMenuFactory;
    }

    public void setTreeMainMenuFactory(Menu treeMainMenuFactory) {
        this.treeMainMenuFactory = treeMainMenuFactory;
    }

    public Menu getDropDownMenuFactory() {
        return dropDownMenuFactory;
    }

    public void setDropDownMenuFactory(Menu dropDownMenuFactory) {
        this.dropDownMenuFactory = dropDownMenuFactory;
    }
}