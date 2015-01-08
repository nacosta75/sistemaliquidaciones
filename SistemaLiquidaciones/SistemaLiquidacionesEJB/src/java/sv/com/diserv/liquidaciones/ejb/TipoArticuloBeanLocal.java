/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.Local;
import sv.com.diserv.liquidaciones.entity.Tipoarticulo;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

/**
 *
 * @author abraham.acosta
 */
@Local
public interface TipoArticuloBeanLocal {
    
     public List<Tipoarticulo> loadAllTiposArticulos() throws DiservBusinessException;
}
