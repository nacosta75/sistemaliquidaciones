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
public class Constants {

    private static final PropertiesLoader properties = PropertiesLoader.getInstance();
    public static final String JNDI_USUARIOS_BEAN = "java:comp/env/ejb/ManejadorUsuarioBean";
    public static final String INITIAL_CONTEXT_FACTORY = "com.sun.enterprise.naming.SerialInitContextFactory";
    public static final int CODE_OPERACION_SATISFACTORIA = 100;
    public static final int CODE_OPERATION_FALLIDA = 98;
    public static final int REGISTROS_A_MOSTRAR_LISTA = 15;
    public static final String MSG_FALLO_EN_OPERACION = "Ocurrio un error al realizar operacion";
    public static final String MSG_OPERACION_SATISFACTORIA = "Operacion ejecutada satisfactoriamente";
    public static final short MENSAJE_TIPO_INFO = 1;
    public static final short MENSAJE_TIPO_ALERTA = 2;
    public static final short MENSAJE_TIPO_ERROR = 3;
    public static final short MENSAJE_TIPO_INTERRROGACION = 4;
    public static final String TITLE_PRINT_MANAGER = ".::Manejador Impresiones::.Sistema Liquidaciones";
    public static final String RUTA_REPORTE_TELEDESPACHO = "reportes//nombreReporteCualquiera.jasper";
    public static final String MSG_SISTEMA = properties.getProperty("enlace.default.mensaje.sistema");
    public static int REGISTROS_A_MOSTRAR_LISTA_CON_CHECKBOX=15;
    public static String JNDI_BODEGA_BEAN= "java:comp/env/ejb/BodegasBean";
    public static String JNDI_PERSONA_BEAN= "java:comp/env/ejb/PersonasBean";
    public static String JNDI_CATALOGO_BEAN= "java:comp/env/ejb/CatalogosBean";
    public static int PAGINA_INICIO_DEFAULT;
    public static String JNDI_SUCURSAL_BEAN= "java:comp/env/ejb/SucursalesBean";
    public static String JNDI_LINEAS_BEAN= "java:comp/env/ejb/LineaArticuloBean";
    public static String[] estadosCiviles = new String[] {"SOLTER@","DIVORCIAD@","VIUD@"};
    public static int[] idsEstadosCiviles = new int[] {1,2,3};
    public static String JNDI_MARCAS_BEAN="java:comp/env/ejb/MarcaArticuloBean";
    public static String JNDI_ARTICULOS_BEAN="java:comp/env/ejb/ArticulosBean";
    public static String JNDI_BODEGAVENDEDOR_BEAN= "java:comp/env/ejb/BodegavendedorBean";
    
    
}
