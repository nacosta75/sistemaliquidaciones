/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE noe acosta  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * .com
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
