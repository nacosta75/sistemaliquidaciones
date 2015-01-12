/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE noe acosta  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * .com
 */
package sv.com.diserv.web.ui.menu.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

/**
 *
 * @author edwin.alvarenga
 */
public class MenuDomain extends MenuItemDomain {

    private boolean open = true;

    @XmlElements({
        @XmlElement(name = "menu", type = MenuDomain.class),
        @XmlElement(name = "menuItem", type = MenuItemDomain.class)})
    public List<IMenuDomain> getItems() {
        return this.items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<IMenuDomain> items) {
        this.items = items;
    }
    private List<IMenuDomain> items = new ArrayList<IMenuDomain>();

    @XmlAttribute
    public boolean isOpen() {
        return this.open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
