/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE DISERV,SA DE CV  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author alvarenga.miranda@gmail.com
 */
package sv.com.diserv.liquidaciones.exception;

import javax.ejb.ApplicationException;

/**
 *
 *alvarenga.miranda@gmail.com
 */
@ApplicationException(rollback = true)
public class DiservBusinessRolledbackException extends RuntimeException {

    private Integer codigoException;
    private String mensaje;

    public DiservBusinessRolledbackException(Integer codigoException, String mensaje) {
        this.codigoException = codigoException;
        this.mensaje = mensaje;
    }

    public Integer getCodigoException() {
        return codigoException;
    }

    public void setCodigoException(Integer codigoException) {
        this.codigoException = codigoException;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
