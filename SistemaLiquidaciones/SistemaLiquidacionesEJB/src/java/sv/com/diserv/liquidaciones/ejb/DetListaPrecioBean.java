package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sv.com.diserv.liquidaciones.dto.OperacionesMovimientoDetDTO;
import sv.com.diserv.liquidaciones.entity.DetListaPrecio;
import sv.com.diserv.liquidaciones.entity.MovimientosDet;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.util.Constants;

/**
 *
 * @author sonia.garcia
 */
@Stateless
public class DetListaPrecioBean implements DetListaPrecioBeanLocal {

    static final Logger logger = Logger.getLogger(DetListaPrecioBean.class.getName());
    @PersistenceContext(unitName = "SistemaLiquidacionesEJBPU")
    private EntityManager em;
    @EJB
    GenericDaoServiceBeanLocal genericDaoBean;

@Override
    public DetListaPrecio findDetPrecioByIdArticulo(Integer idEncListaPrecio, Integer IdArticulo) throws DiservBusinessException{
      logger.log(Level.INFO, "[findDetPrecioByIdArticulo] IdArticulo:" + IdArticulo);
        DetListaPrecio listaPrecio = null;
        Query query;
        try {
            query = em.createNamedQuery("DetListaPrecio.findByIdArticuloIdEnc");
            query.setParameter("idlista", idEncListaPrecio);
            query.setParameter("idarticulo",IdArticulo);
            listaPrecio = (DetListaPrecio) query.getSingleResult();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[findDetPrecioByIdArticulo][NoResultException]No se encontraron precios");
//            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron articulo");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[findDetPrecioByIdArticulo][Exception]Se mostro una excepcion al buscar precios");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return listaPrecio;   
    }
}
