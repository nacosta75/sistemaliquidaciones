/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE FISCALIA GENERAL DE LA REPUBLICA  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author edwin.alvarenga@fgr.gob.sv.com
 */
package sv.com.diserv.web.ui.menu.dropdown;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Menupopup;
import sv.com.diserv.web.ui.menu.domain.IMenuDomain;
import sv.com.diserv.web.ui.menu.util.ILabelElement;
import sv.com.diserv.web.ui.menu.util.MenuFactory;
import sv.com.diserv.web.ui.menu.util.MenuFactoryDTO;

/**
 *
 * @author edwin.alvarenga
 */
public class DropDownMenuFactory extends MenuFactory {

    private static final long serialVersionUID = -6930474675371322560L;

    public DropDownMenuFactory() {
        super();
    }

    @Override
    protected MenuFactoryDTO createMenuComponent(Component parent, boolean open) {
        final DefaultDropDownMenu menu = new DefaultDropDownMenu();
        parent.appendChild(menu);
        final Menupopup menupopup = new Menupopup();
        menu.appendChild(menupopup);
        return new MenuFactoryDTO(menupopup, menu);
    }

    @Override
    protected ILabelElement createItemComponent(Component parent) {
        final DefaultDropDownMenuItem item = new DefaultDropDownMenuItem();
        parent.appendChild(item);
        return item;
    }

    @Override
    protected void setAttributes(IMenuDomain treecellValue, ILabelElement defaultTreecell) {
        super.setAttributes(treecellValue, defaultTreecell);
        defaultTreecell.setImage(treecellValue.getIconName());
        defaultTreecell.setLabel(treecellValue.getLabel());
    }
}
