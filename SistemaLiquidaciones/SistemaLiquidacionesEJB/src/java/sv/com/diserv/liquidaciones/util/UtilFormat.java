package sv.com.diserv.liquidaciones.util;
/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE DISERV,SA DE CV  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author alvarenga.miranda@gmail.com
 */

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class UtilFormat {

    static final Logger logger = Logger.getLogger(UtilFormat.class.getCanonicalName());

    static NumeroALetras numeroToLetras = new NumeroALetras();

    public static Date getFechaActual() {
        return new Date();

    }

    public static String convertirFechaDDMMYYYHHMMSS(Date p) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a ");
        if (p == null) {
            return "N/D";
        }
        return formato.format(p);
    }

    public static String convertirFechaDDMMYYY(Date p) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        if (p == null) {
            return "N/D";
        }
        return formato.format(p);
    }

    public static String convertirFechaYYYYMMMDDD(Date p) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        if (p == null) {
            return null;
        }
        return formato.format(p);
    }

    public static String getFechaString() {
        Date fr = new Date();
        long milisegundos = fr.getTime();
        Timestamp fecha = new Timestamp(milisegundos);
        return convertirFechaDDMMYYY(fecha);
    }

    public static String getFechaStringSistema() {
        Date fr = new Date();
        long milisegundos = fr.getTime();
        Timestamp fecha = new Timestamp(milisegundos);
        return convertirFechaDDMMYYYHHMMSS(fecha);
    }

    public static boolean validateNumber(String value) {
        return StringUtils.isNumeric(value);
    }

    public static String getCadenaAlfanumAleatoria(int longitud) {
        String cadenaAleatoria = "";
        long milis = new java.util.GregorianCalendar().getTimeInMillis();
        Random r = new Random(milis);
        int i = 0;
        while (i < longitud) {
            char c = (char) r.nextInt(255);
            if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z')) {
                cadenaAleatoria += c;
                i++;
            }
        }
        //   System.out.println("Id Generado:" + cadenaAleatoria);
        return cadenaAleatoria;
    }

    public static Integer getToken() {
        Random r = new Random();
        return Integer.valueOf(r.nextInt(10) + 1);
    }

    /**
     *
     *
     * @return @retur
     */
    public static String getFechaSistema() {
        Date fecha = new Date();
        return convertirFechaDDMMYYYHHMMSS(fecha);
    }

    public static Integer getAnioActual() {
        try {
            Date date = new Date();
            SimpleDateFormat dfAnio = new SimpleDateFormat("yy");
            return Integer.valueOf(dfAnio.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer getMesActual() {
        try {
            Date date = new Date();
            SimpleDateFormat dfMes = new SimpleDateFormat("MM");
            return Integer.valueOf(dfMes.format(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertirNumeroLetras(Double total) {
        try {
            return numeroToLetras.convertirLetras(total);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date restarDiaFecha(Integer cantidadDias) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -cantidadDias);
        System.out.println("fecha:" + UtilFormat.convertirFechaDDMMYYYHHMMSS(calendar.getTime()));
        return calendar.getTime();
    }

    /**
     * *
     *
     * @param fecha
     * @return
     */
    public static Date fechaBusquedaJpaFinal(Date fecha) {
        System.out.println("[fechaBusquedaJpaFinal]fecha Inicial:" + UtilFormat.convertirFechaDDMMYYYHHMMSS(fecha));
        try {
            Calendar cale = Calendar.getInstance();
            cale.setTime(fecha);
            cale.set(Calendar.HOUR_OF_DAY, 23);
            cale.set(Calendar.MINUTE, 59);
            System.out.println("[fechaBusquedaJpaFinal]fecha final:" + UtilFormat.convertirFechaDDMMYYYHHMMSS(cale.getTime()));
            return cale.getTime();
            //System.out.println("fecha final:" + UtilFormat.convertirFechaDDMMYYYHHMMSS(cale.getTime()))
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
