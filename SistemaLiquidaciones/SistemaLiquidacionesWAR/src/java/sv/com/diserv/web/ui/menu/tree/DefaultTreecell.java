/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE FISCALIA GENERAL DE LA REPUBLICA  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author edwin.alvarenga@fgr.gob.sv.com
 */
package sv.com.diserv.web.ui.menu.tree;

import java.io.Serializable;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.ComponentNotFoundException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Treecell;
import sv.com.diserv.web.ui.menu.util.ILabelElement;

/**
 *
 * @author edwin.alvarenga
 */
public class DefaultTreecell extends Treecell implements EventListener, Serializable, ILabelElement {

    private static final long serialVersionUID = 5221385297281381652L;
    private static final Logger logger = Logger.getLogger(DefaultTreecell.class);
    private String zulNavigation;

    @Override
    public void onEvent(Event event) throws Exception {

        try {
            // TODO get the parameter for working with tabs from the application
            // params
            final int workWithTabs = 1;

            if (workWithTabs == 1) {

                /* get an instance of the borderlayout defined in the zul-file */
                final Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");
                /* get an instance of the searched CENTER layout area */
                final Center center = bl.getCenter();

                final Tabs tabs = (Tabs) center.getFellow("divCenter").getFellow("tabBoxIndexCenter").getFellow("tabsIndexCenter");

                // Check if the tab is already open, if not than create them
                Tab checkTab = null;
                System.out.println("id:" + this.getId());
                try {
                    checkTab = (Tab) tabs.getFellow("tab_" + this.getId());
                    checkTab.setSelected(true);
                } catch (final ComponentNotFoundException ex) {
                    //ex.printStackTrace();
                    // Ignore if can not get tab.
                }
                if (checkTab == null) {
                    final Tab tab = new Tab();
                    tab.setId("tab_" + this.getId());
                    tab.setLabel(this.getLabel().trim());
                    tab.setClosable(true);
                    tab.setParent(tabs);
                    final Tabpanels tabpanels = (Tabpanels) center.getFellow("divCenter").getFellow("tabBoxIndexCenter").getFellow("tabsIndexCenter").getFellow("tabpanelsBoxIndexCenter");
                    final Tabpanel tabpanel = new Tabpanel();
                    tabpanel.setHeight("100%");
                    tabpanel.setStyle("padding: 0px;");
                    tabpanel.setParent(tabpanels);
                    Executions.createComponents(getZulNavigation(), tabpanel, null);
                    tab.setSelected(true);
                }
            } else {
                /* get an instance of the borderlayout defined in the zul-file */
                final Borderlayout bl = (Borderlayout) Path.getComponent("/outerIndexWindow/borderlayoutMain");
                /* get an instance of the searched CENTER layout area */
                final Center center = bl.getCenter();
                /* clear the center child comps */
                center.getChildren().clear();
                /*
                 * create the page and put it in the center layout area
                 */
                Executions.createComponents(getZulNavigation(), center, null);
            }
            //logger.  logger.debug("-->[" + getId() + "] calling zul-file: " + getZulNavigation());
        } catch (final Exception e) {
            e.printStackTrace();
            Messagebox.show(e.toString());
        }
    }

    private String getZulNavigation() {
        return this.zulNavigation;
    }

    @Override
    public void setZulNavigation(String zulNavigation) {
        this.zulNavigation = zulNavigation;
        if (!StringUtils.isEmpty(zulNavigation)) {
            addEventListener("onClick", this);
        }
    }
}
