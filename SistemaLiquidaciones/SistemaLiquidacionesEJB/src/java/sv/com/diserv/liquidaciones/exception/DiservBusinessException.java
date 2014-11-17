/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE DISERV,SA DE CV  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author alvarenga.miranda@gmail.com
 */
package sv.com.diserv.liquidaciones.exception;

/**
 *
 *alvarenga.miranda@gmail.com
 */
public class DiservBusinessException extends Exception {

    private int codigoException;
    private String mensaje;

    public DiservBusinessException(int codigoException, String mensaje) {
        super(mensaje);
        this.codigoException = codigoException;
        this.mensaje = mensaje;
    }

    public int getCodigoException() {
        return codigoException;
    }

    public void setCodigoException(int codigoException) {
        this.codigoException = codigoException;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
