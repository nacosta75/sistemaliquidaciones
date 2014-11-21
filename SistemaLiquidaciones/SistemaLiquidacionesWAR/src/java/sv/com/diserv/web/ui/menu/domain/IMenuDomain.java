/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE FISCALIA GENERAL DE LA REPUBLICA  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author edwin.alvarenga@fgr.gob.sv.com
 */
package sv.com.diserv.web.ui.menu.domain;

/**
 *
 *
 * @author edwin.alvarenga
 */
public interface IMenuDomain {
 
    String getRightName();

    String getId();

    String getLabel();

    Boolean isWithOnClickAction();

    String getZulNavigation();

    String getIconName();
}
