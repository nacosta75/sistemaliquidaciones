/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sv.com.diserv.liquidaciones.dto.CatalogoDTO;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

/**
 *
 * @author sonia.garcia
 */
@Stateless
public class CatalogosBean implements CatalogosBeanLocal {

    static final Logger logger = Logger.getLogger(CatalogosBean.class.getName());
    /**
     * Metodo para extraer todos los usuarios
     *
     * @param inicio: Primer registro a mostrar
     * @param fin: Ultimo registro a mostrar
     * @return List<Users>: Lista de usuarios desde la base de datos
     * @throws DesempenoBusinessException
     */
   
    public List<CatalogoDTO> loadAllElementosCatalogo(int[] ids, String[] opciones) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadAllElementosCatalogo] ");
        List<CatalogoDTO> catalogoList = new ArrayList<CatalogoDTO>();
         for (int i=0; i<ids.length; i++)
            {
                CatalogoDTO catalogo = new CatalogoDTO();
                catalogo.setIdCatalogo(ids[i]);
                catalogo.setDescripcionCatalogo(opciones[i]);
                catalogoList.add(catalogo);
            }
        
           if (catalogoList != null) {
                logger.log(Level.INFO, "[loadAllElementosCatalogo] Se encontraron " + catalogoList.size() + " elementos");
            }
        
        return catalogoList;
    }

    
}
