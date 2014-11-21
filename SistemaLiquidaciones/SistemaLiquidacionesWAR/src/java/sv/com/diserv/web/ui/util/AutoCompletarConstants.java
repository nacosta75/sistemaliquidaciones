/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE DISERV SA,DE CV  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author 
 */
package sv.com.diserv.web.ui.util;

/**
 *
 *alvarenga.miranda@gmail.com
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
//import sv.gob.fgr.enlace.business.entity.Delito;
//import sv.gob.fgr.enlace.business.entity.Usuario;

public class AutoCompletarConstants {

    static final Logger log = Logger.getLogger(AutoCompletarConstants.class.getName());
    /**
     * Comparator de etiquetas para filtros por codigo de nodo
    //     */
//    public static final Comparator LABEL_COMPARATOR_FILTRO_COMP_CODE = new Comparator() {
//
//        String s1;
//        String s2;
//
//        public int compare(Object o1, Object o2) {
//
//            if (o1 instanceof SelectItem) {
//                s1 = ((SelectItem) o1).getLabel().split("__")[0];
//            } else {
//                s1 = o1.toString();
//            }
//
//            if (o2 instanceof SelectItem) {
//                s2 = ((SelectItem) o2).getLabel().split("__")[0];
//            } else {
//                s2 = o2.toString();
//            }
//
//            if (s1.startsWith("#")) {
//                s1 = s1.replaceFirst("#", "");
//            }
//
//            if (s2.startsWith("#")) {
//                s2 = s2.replaceFirst("#", "");
//            }
//
//
//
//            return s1.compareToIgnoreCase(s2);
//
//        }
//    };
    /**
     * Comparator de etiquetas para filtros por nombre de nodo.
     */
//    public static final Comparator LABEL_COMPARATOR_FILTRO_COMP_NAME = new Comparator() {
//
//        String s1;
//        String s2;
//
//        public int compare(Object o1, Object o2) {
//
//            if (o1 instanceof SelectItem) {
//                s1 = ((SelectItem) o1).getLabel().split("__")[1];
//            } else {
//                s1 = o1.toString();
//            }
//
//            if (o2 instanceof SelectItem) {
//                s2 = ((SelectItem) o2).getLabel().split("__")[1];
//            } else {
//                s2 = o2.toString();
//            }
//
//            return s1.compareToIgnoreCase(s2);
//
//        }
//    };
    /**
     * Comparator de etiquetas para filtros por nombre de nodo.
     */
//    public static final Comparator LABEL_COMPARATOR_LABEL = new Comparator() {
//
//        String s1;
//        String s2;
//
//        public int compare(Object o1, Object o2) {
//            //autocompletar delitos
//            if (o1 instanceof Delito) {
//                s1 = ((Delito) o1).getNombre();
//            } else {
//                s1 = o1.toString();
//            }
//            if (o2 instanceof Delito) {
//                s2 = ((Delito) o2).getNombre();
//            } else {
//                s2 = o2.toString();
//            }
//            //autocompletar usuario
//            if (o1 instanceof Usuario) {
//                s1 = ((Usuario) o1).getNombre();
//            } else {
//                s1 = o1.toString();
//            }
//            if (o2 instanceof Usuario) {
//                s2 = ((Usuario) o2).getNombre();
//            } else {
//                s2 = o2.toString();
//            }
//        //    log.log(Level.INFO, "S1 - = | S2 - {1} | COMP - {2}", new String[]{s1, s2, s1.compareToIgnoreCase(s2) + ""});
//            return s1.compareToIgnoreCase(s2);
//
//        }
//    };
//
//    public static List<Usuario> buscarCoincidenciasUsuario(String query, List<Usuario> lstDiccionario) {
//        List<Usuario> lstCoincidencias = new ArrayList<Usuario>();
//        for (Usuario user : lstDiccionario) {
//            String cadena = user.getNombre().toLowerCase();
//            query = query.toLowerCase();
//            if (cadena.indexOf(query) >= 0) {
//                lstCoincidencias.add(user);
//            }
//        }
//        return lstCoincidencias;
//    }
//    
//      public static List<Delito> buscarCoincidenciasDelito(String query, List<Delito> lstDiccionario) {
//        List<Delito> lstCoincidencias = new ArrayList<Delito>();
//        for (Delito user : lstDiccionario) {
//            String cadena = user.getNombre().toLowerCase();
//            query = query.toLowerCase();
//            if (cadena.indexOf(query) >= 0) {
//                lstCoincidencias.add(user);
//            }
//        }
//        return lstCoincidencias;
//    }
    
//    /**
//     * Metodo para definir las coincidencias en autocompletado
//     * SelectInputText component.
//     *
//     * @param event
//     */
//    public static void buscarCoincidencias(ValueChangeEvent event, Comparator comparator, List<SelectItem> lstDiccionario, List<SelectItem> lstCarga) {
//        Object searchWord = event.getNewValue();
//        int maxMatches = ((SelectInputText) event.getComponent()).getRows();
//        List<SelectItem> lstCoincidencias = new ArrayList<SelectItem>(maxMatches);
//
//        try {
//            int insert = Collections.binarySearch(lstDiccionario, searchWord, comparator);
//
//            //log.log(Level.INFO, "insert auto pre =", insert);
//
//            if (insert < 0) {
//                insert = Math.abs(insert) - 1;
//            }
//            //log.log(Level.INFO, "insert auto post =", insert);
//
//            for (int i = 0; i < maxMatches; i++) {
//
//                if ((insert + i) >= lstDiccionario.size() || i >= maxMatches) {
//                    break;
//                }
//                lstCoincidencias.add(lstDiccionario.get(insert + i));
//            }
//
//        } catch (Exception e) {
//            log.log(Level.WARNING, "Error cargando las coincidencias", e);
//
//        }
//
//        lstCarga.clear();
//        lstCarga.addAll(lstCoincidencias);
//
//    }
    /**
    //     * @param lst
    //     * @param value
    //     * @return match
    //     */
//    public static <T> T getMatch(List<SelectItem> lst, String value) {
//        T oSelect = null;
//        if (lst != null) {
//            SelectItem si;
//            Iterator iter = lst.iterator();
//            while (iter.hasNext()) {
//                si = (SelectItem) iter.next();
//                if (value.equals(String.valueOf(si.getValue()))) {
//                    oSelect = (T) si.getValue();
//                }
//            }
//        }
//        return oSelect;
//    }
//    /**
//     * @param lst
//     * @param value
//     * @return match
//     */
//    public static <T> T getMatchCode(List<SelectItem> lst, String value) {
//        T oSelect = null;
//        value = value.startsWith("#") ? value.replaceFirst("#", value) : value;
//        if (lst != null) {
//            SelectItem si;
//            Iterator iter = lst.iterator();
//            while (iter.hasNext()) {
//                si = (SelectItem) iter.next();
//                if (value.equals(String.valueOf(si.getValue()))) {
//                    oSelect = (T) si.getValue();
//                }
//            }
//        }
//        return oSelect;
//    }
}
