/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE DISERV,SA DE CV  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author alvarenga.miranda@gmail.com
 */
package sv.com.diserv.liquidaciones.util;

/**
 *
 *alvarenga.miranda@gmail.com
 */
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;

public class ServiceLocator {

    private Context context = null;
    private Map<String, Object> cache = null;
    private static ServiceLocator me;

    private ServiceLocator() throws ServiceLocatorException {
        try {
            Properties prop = new Properties();
            prop.put(Context.INITIAL_CONTEXT_FACTORY, Constants.INITIAL_CONTEXT_FACTORY);
            prop.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
            prop.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
            prop.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
            prop.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
            context = new InitialContext(prop);
            cache = Collections.synchronizedMap(new HashMap<String, Object>());
        } catch (NamingException ne) {
            throw new ServiceLocatorException("No se creo el contexto inicial. " + ne.getMessage(), ne);
        } catch (Exception e) {
            throw new ServiceLocatorException("Error no esperado. " + e.getMessage(), e);
        }
    }

    private synchronized static void createInstance() throws ServiceLocatorException {
        if (me == null) {
            me = new ServiceLocator();
        }
    }

    public static ServiceLocator getInstance() throws ServiceLocatorException {
        if (me == null) {
            createInstance();
        }
        return me;
    }

    @SuppressWarnings("unchecked")
    public <T> T getService(String serviceJndi) throws ServiceLocatorException {
        try {
            if (!cache.containsKey(serviceJndi)) {
                T service = (T) context.lookup(serviceJndi);
                cache.put(serviceJndi, service);
            }
            return (T) cache.get(serviceJndi);
        } catch (NamingException ne) {
            throw new ServiceLocatorException("No se encontro el JNDI: " + serviceJndi + " msg: " + ne.getMessage(), ne);
        } catch (Exception e) {
            throw new ServiceLocatorException("Error no esperado. " + e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getServiceLocal(String serviceJndi) throws ServiceLocatorException {
        try {
            if (!cache.containsKey(serviceJndi)) {
                T service = (T) context.lookup(serviceJndi);
                cache.put(serviceJndi, service);
            }
            return (T) cache.get(serviceJndi);
        } catch (NamingException ne) {
            throw new ServiceLocatorException("No se encontro el JNDI: " + serviceJndi + " msg: " + ne.getMessage(), ne);
        } catch (Exception e) {
            throw new ServiceLocatorException("Error no esperado. " + e.getMessage(), e);
        }
    }

    /**
     * Gets the lookup.
     *
     * @param <T> the generic type
     * @param context the context
     * @return the lookup
     */
    @SuppressWarnings("unchecked")
    public static <T> T getLookup(String context) {
        InitialContext ctx = null;
        T t = null;
        try {
            ctx = new InitialContext();
            t = (T) ctx.lookup(context);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return t;
    }
}
