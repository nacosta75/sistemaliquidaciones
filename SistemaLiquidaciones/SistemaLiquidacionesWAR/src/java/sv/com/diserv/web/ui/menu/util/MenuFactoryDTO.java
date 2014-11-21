/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE FISCALIA GENERAL DE LA REPUBLICA  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author edwin.alvarenga@fgr.gob.sv.com
 */
package sv.com.diserv.web.ui.menu.util;

import org.zkoss.zk.ui.Component;

/**
 *
 * @author edwin.alvarenga
 */
public class MenuFactoryDTO {

    public MenuFactoryDTO(Component parent, ILabelElement node) {
        super();
        this.parent = parent;
        this.node = node;
    }

    public MenuFactoryDTO(ILabelElement node) {
        this(node, node);
    }

    /**
     * @return the parent
     */
    public Component getParent() {
        return this.parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(Component parent) {
        this.parent = parent;
    }

    /**
     * @return the node
     */
    public ILabelElement getNode() {
        return this.node;
    }

    /**
     * @param node the node to set
     */
    public void setNode(ILabelElement node) {
        this.node = node;
    }
    private Component parent;
    private ILabelElement node;
}
