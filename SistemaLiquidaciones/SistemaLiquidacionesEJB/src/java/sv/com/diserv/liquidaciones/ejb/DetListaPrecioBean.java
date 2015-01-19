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


    @Override
    public List<DetListaPrecio> listDetPrecioByIdArticulo(Integer IdArticulo,Integer inicio,Integer fin) throws DiservBusinessException {
         logger.log(Level.INFO, "[listDetPrecioByIdArticulo] desde:" + inicio + " hasta:" + fin);
        List<DetListaPrecio> preciosList = null;
        Query query;
        try {
            query = em.createNamedQuery("DetListaPrecio.findByIdArticulo");
            query.setParameter("idarticulo", IdArticulo);
            query.setFirstResult(inicio);
            query.setMaxResults(fin);
            preciosList = query.getResultList();
            if (preciosList != null) {
                logger.log(Level.INFO, "[listDetPrecioByIdArticulo] Se encontraron " + preciosList.size() + " precios");
            }
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[listDetPrecioByIdArticulo][NoResultException]No se encontraron precios");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron precios");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[listDetPrecioByIdArticulo][Exception]Se mostro una excepcion al buscar Precios");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return preciosList;
    }


}
