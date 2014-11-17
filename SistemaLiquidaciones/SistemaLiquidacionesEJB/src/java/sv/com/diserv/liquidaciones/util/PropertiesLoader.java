package sv.com.diserv.liquidaciones.util;
/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE DISERV,SA DE CV  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author alvarenga.miranda@gmail.com
 */

import java.io.FileInputStream;
import java.util.Properties;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class PropertiesLoader {

    private static final Logger logger = Logger.getLogger(PropertiesLoader.class.getSimpleName());

    public PropertiesLoader() {
        load();
    }

    public static PropertiesLoader getInstance() {
        if (instance == null) {
            synchronized (PropertiesLoader.class) {
                if (instance == null) {
                    instance = new PropertiesLoader();
                }
            }
        }
        return instance;
    }

    public Properties load() {
        try {
            final String instanceRoot = System.getProperty("com.sun.aas.instanceRoot");
            logger.info("[PropertiesLoader]route to server=" + instanceRoot);
            properties.load(new FileInputStream(instanceRoot + "//projectoenlace//enlaceproject.properties"));
        } catch (Exception e) {
            logger.log(Level.ERROR, "Error cargando el archivo de propiedades", e);
        }
        return properties;
    }

    public String getProperty(String prop) {
        logger.info("[PropertiesLoader]Property=" + prop + ",Value=" + properties.getProperty(prop));
        return properties.getProperty(prop);
    }
    private static PropertiesLoader instance;
    private static Properties properties = new Properties();
}
