/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE FISCALIA GENERAL DE LA REPUBLICA  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author edwin.alvarenga@fgr.gob.sv.com
 */
package sv.com.diserv.web.ui.menu.util;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.zkoss.zk.ui.Component;
import sv.com.diserv.web.ui.menu.domain.IMenuDomain;
import sv.com.diserv.web.ui.menu.domain.MenuDomain;
import sv.com.diserv.web.ui.menu.domain.RootMenuDomain;
import sv.com.diserv.web.ui.menu.domain.RootMenuDomainFactory;
import sv.com.diserv.web.ui.util.UserWorkspace;


 

/**
 *
 * @author edwin.alvarenga
 */
abstract public class MenuFactory implements Serializable, Menu {

    private static final long serialVersionUID = 142621423557135573L;
    private final Log loger = LogFactory.getLog(getClass());
    final private LinkedList<Component> stack = new LinkedList<Component>();
    private UserWorkspace workspace;
    private RootMenuDomainFactory rootMenuDomainFactory;
    private RootMenuDomain rootMenuDomain;

    protected MenuFactory() {
        super();
    }

    @Override
    public void addMenu(Component component) {
        assert component != null : "Parent component is null!";

        this.workspace = UserWorkspace.getInstance();
        assert this.workspace != null : "No UserWorkspace exists!";

        stack.clear();
        push(component);
        createMenu(getRootMenuDomain().getItems());
    }

    private void createMenu(List<IMenuDomain> items) {
        if (items.isEmpty()) {
            return;
        }
        for (final IMenuDomain menuDomain : items) {
            if (menuDomain instanceof MenuDomain) {
                final MenuDomain menu = (MenuDomain) menuDomain;
                if (addSubMenuImpl(menu)) {
                    createMenu(menu.getItems());
                    ebeneHoch();
                }
            } else {
                addItemImpl(menuDomain);
            }
        }
    }

    private void addItemImpl(IMenuDomain itemDomain) {
        if (isAllowed(itemDomain)) {
            setAttributes(itemDomain, createItemComponent(getCurrentComponent()));
        }
    }

    abstract protected ILabelElement createItemComponent(Component parent);

    private boolean addSubMenuImpl(MenuDomain menu) {
        if (isAllowed(menu)) {
            final MenuFactoryDTO dto = createMenuComponent(getCurrentComponent(), menu.isOpen());
            setAttributes(menu, dto.getNode());
            push(dto.getParent());
            return true;
        }
        return false;
    }

    abstract protected MenuFactoryDTO createMenuComponent(Component parent, boolean open);

    private boolean isAllowed(IMenuDomain treecellValue) {
        return isAllowed(treecellValue.getRightName());
    }

    private void ebeneHoch() {
        poll();
    }

    private Component getCurrentComponent() {
        return peek();
    }

    private Log getLogger() {
        return this.loger;
    }

    private boolean isAllowed(String rightName) {
        if (StringUtils.isEmpty(rightName)) {
            return true;
        }
        return workspace.isAllowed(rightName);
    }

    private Component peek() {
        return this.stack.peek();
    }

    private Component poll() {
        try {
            return this.stack.poll();
        } finally {
            if (this.stack.isEmpty()) {
                throw new RuntimeException("Root no longer exists!");
            }
        }
    }

    private void push(Component e) {
        this.stack.push(e);
    }

    protected void setAttributes(IMenuDomain treecellValue, ILabelElement defaultTreecell) {
        if (treecellValue.isWithOnClickAction() == null || treecellValue.isWithOnClickAction().booleanValue()) {
            defaultTreecell.setZulNavigation(treecellValue.getZulNavigation());

            if (!StringUtils.isEmpty(treecellValue.getIconName())) {
                defaultTreecell.setImage(treecellValue.getIconName());
            }
        }

        setAttributesWithoutAction(treecellValue, defaultTreecell);
    }

    private void setAttributesWithoutAction(IMenuDomain treecellValue, ILabelElement defaultTreecell) {
        assert treecellValue.getId() != null : "In mainmenu.xml file is a node who's ID is missing!";
        defaultTreecell.setId(treecellValue.getId());
        defaultTreecell.setLabel(" " + treecellValue.getLabel());
    }

    public void setRootMenuDomainFactory(RootMenuDomainFactory rootMenuDomainFactory) {
        this.rootMenuDomainFactory = rootMenuDomainFactory;
    }

    private RootMenuDomain getRootMenuDomain() {
        if (rootMenuDomain == null) {
            rootMenuDomain = rootMenuDomainFactory.getRootMenuDomain();
        }
        return rootMenuDomain;
    }
}
