/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE noe acosta  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * .com
 */
package sv.com.diserv.web.ui.menu.domain;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public abstract class MetaMenuFactory implements RootMenuDomainFactory {

    abstract protected RootMenuDomain createRootMenuDomain() throws JAXBException;

    protected static Unmarshaller createUnmarshaller() throws JAXBException {
        return JAXBContext.newInstance(RootMenuDomain.class).createUnmarshaller();
    }

    @Override
    public RootMenuDomain getRootMenuDomain() {
        try {
            return createRootMenuDomain();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public MetaMenuFactory() {
    }
}
