/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE noe acosta  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * .com
 */
package sv.com.diserv.web.ui.menu.domain;

import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author edwin.alvarenga
 */
public class MenuItemDomain implements IMenuDomain {

    private String id;
    private String zulNavigation;
    private String label;
    private String rightName = null;
    private Boolean withOnClickAction = null;
    private String iconName;

    /**
     * @return the iconName
     */
    @XmlAttribute
    public String getIconName() {
        return this.iconName;
    }

    /**
     * @return the id
     */
    @XmlAttribute(required = true)
    public String getId() {
        return this.id;
    }

    /**
     * @return the label
     */
    @XmlAttribute
    public String getLabel() {
        return this.label;
    }

    /**
     * @return the rightName
     */
    @XmlAttribute
    public String getRightName() {
        return this.rightName;
    }

    /**
     * @return the zulNavigation
     */
    @XmlAttribute
    public String getZulNavigation() {
        return this.zulNavigation;
    }

    /**
     * @return the withOnClickAction
     */
    @XmlAttribute
    public Boolean isWithOnClickAction() {
        return this.withOnClickAction;
    }

    /**
     * @param iconName the iconName to set
     */
    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @param rightName the rightName to set
     */
    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    /**
     * @param withOnClickAction the withOnClickAction to set
     */
    public void setWithOnClickAction(Boolean withOnClickAction) {
        this.withOnClickAction = withOnClickAction;
    }

    /**
     * @param zulNavigation the zulNavigation to set
     */
    public void setZulNavigation(String zulNavigation) {
        this.zulNavigation = zulNavigation;
    }
}
