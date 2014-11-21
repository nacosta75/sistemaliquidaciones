/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE DISERV,SA DE CV  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 *alvarenga.miranda@gmail.com
 * @author edwin alvarenga
 * DISERV,SA DE CV
 */
package sv.com.diserv.web.ui.util;

import java.awt.GraphicsEnvironment;
import java.util.Arrays;

/**
 *
 * @author edwin.alvarenga
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        System.out.println(Arrays.asList(GraphicsEnvironment
                .getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
    }
}
