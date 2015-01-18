package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
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
    
     // retorna los precios del articulo
    public List<DetListaPrecio> listDetPrecioByIdArticulo(Integer IdArticulo,Integer inicio,Integer fin) throws DiservBusinessException;
    
}
