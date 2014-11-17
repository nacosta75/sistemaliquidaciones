/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE DISERV,SA DE CV  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author alvarenga.miranda@gmail.com
 */
package sv.com.diserv.liquidaciones.exception;

/**
 *
 * @author edwin
 */
public class ServiceLocatorException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     * @author alvarenga.miranda@gmail.com
     * @param message Mensaje de la excepcion
     */
    public ServiceLocatorException(String message) {
        super(message);
    }

    /**
     * Constructor
     *@author alvarenga.miranda@gmail.com
     * @param message Mensaje de la excepcion
     * @param cause Causa de la excepcion
     */
    public ServiceLocatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
