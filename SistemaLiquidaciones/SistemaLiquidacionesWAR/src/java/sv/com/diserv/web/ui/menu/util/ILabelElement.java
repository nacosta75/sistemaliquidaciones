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
public interface ILabelElement extends Component {

    void setZulNavigation(String zulNavigation);

    void setLabel(String string);

    void setImage(String image);
}
