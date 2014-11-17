/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE DISERV,SA DE CV  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author alvarenga.miranda@gmail.com
 */
package sv.com.diserv.liquidaciones.web.ui.usuarios.util;

/**
 *
 *alvarenga.miranda@gmail.com
 */
import java.io.Serializable;
import java.util.Random;

public class TokenGenerator implements Serializable{
 
    /**
     * Genera un token para validar que cada operacion no se pueda
     * repetir dos veces, evitando el doble procesamiento al hacer 
     * doble click en algun boton
     * @return 
     */
    public static Integer getTokenOperation() {
        Random r = new Random();
        Integer token = Integer.valueOf(0);
        token = Integer.valueOf(r.nextInt(10) + 1);
        return token;
    }

  
}