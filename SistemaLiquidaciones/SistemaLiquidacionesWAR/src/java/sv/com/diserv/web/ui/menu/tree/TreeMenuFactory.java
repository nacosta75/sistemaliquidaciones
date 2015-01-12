/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE noe acosta  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * .com
 */
package sv.com.diserv.web.ui.menu.tree;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import sv.com.diserv.web.ui.menu.util.ILabelElement;
import sv.com.diserv.web.ui.menu.util.MenuFactory;
import sv.com.diserv.web.ui.menu.util.MenuFactoryDTO;
 

/**
 *
 * @author edwin.alvarenga
 */
public class TreeMenuFactory extends MenuFactory {
 
    private static final long serialVersionUID = -1601202637698812546L;

    public TreeMenuFactory() {
        super();
    }

    @Override
    protected MenuFactoryDTO createMenuComponent(Component parent, boolean open) {
        final Treeitem treeitem = new Treeitem();
        parent.appendChild(treeitem);
        treeitem.setOpen(open);
        final ILabelElement item = insertTreeCell(treeitem);
        final Treechildren treechildren = new Treechildren();
        treeitem.appendChild(treechildren);
        return new MenuFactoryDTO(treechildren, item);
    }

    @Override
    protected ILabelElement createItemComponent(Component parent) {
        final Treeitem treeitem = new Treeitem();
        parent.appendChild(treeitem);
        final ILabelElement item = insertTreeCell(treeitem);
        return item;
    }

    private ILabelElement insertTreeCell(Component parent) {
        final Treerow treerow = new Treerow();
        parent.appendChild(treerow);
        final DefaultTreecell treecell = new DefaultTreecell();
        treerow.appendChild(treecell);
        return treecell;
    }
}
