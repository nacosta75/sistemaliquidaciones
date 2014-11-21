/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE FISCALIA GENERAL DE LA REPUBLICA  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author edwin.alvarenga@fgr.gob.sv.com
 */
package sv.com.diserv.web.ui.menu.domain;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author edwin.alvarenga
 */
@XmlRootElement(name = "XMLRootMenu",
        namespace = "http://www.fiscalia.fgr.gob.sv/menu")
public class RootMenuDomain {

    private List<IMenuDomain> items;

    /**
     * @return the items
     */
    @XmlElements({
        @XmlElement(name = "menu",
                type = MenuDomain.class),
        @XmlElement(name = "menuItem",
                type = MenuItemDomain.class)})
    public List<IMenuDomain> getItems() {
        return this.items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<IMenuDomain> items) {
        this.items = items;
    }
}
