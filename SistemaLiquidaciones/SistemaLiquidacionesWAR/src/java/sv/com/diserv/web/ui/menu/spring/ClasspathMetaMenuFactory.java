/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE FISCALIA GENERAL DE LA REPUBLICA  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author edwin.alvarenga@fgr.gob.sv.com
 */
package sv.com.diserv.web.ui.menu.spring;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import sv.com.diserv.web.ui.menu.domain.MetaMenuFactory;
import sv.com.diserv.web.ui.menu.domain.RootMenuDomain;

/**
 *
 * @author edwin.alvarenga
 */
public class ClasspathMetaMenuFactory extends MetaMenuFactory {

    public ClasspathMetaMenuFactory() {
    }
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected RootMenuDomain createRootMenuDomain() throws JAXBException {
        Unmarshaller unmarshaller = createUnmarshaller();
        return (RootMenuDomain) unmarshaller.unmarshal(ClasspathMetaMenuFactory.class.getResource(name));
    }
}
