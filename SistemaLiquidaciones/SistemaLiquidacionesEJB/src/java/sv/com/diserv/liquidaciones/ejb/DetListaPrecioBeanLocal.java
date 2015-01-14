package sv.com.diserv.liquidaciones.ejb;

import javax.ejb.Local;
import sv.com.diserv.liquidaciones.entity.DetListaPrecio;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

/**
 *
 * @author sonia.garcia
 */
@Local
public interface DetListaPrecioBeanLocal {

    public DetListaPrecio findDetPrecioByIdArticulo(Integer idEncListaPrecio, Integer IdArticulo) throws DiservBusinessException;    
}
