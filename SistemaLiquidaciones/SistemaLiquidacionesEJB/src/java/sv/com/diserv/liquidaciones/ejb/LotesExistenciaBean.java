package sv.com.diserv.liquidaciones.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import sv.com.diserv.liquidaciones.dto.BusquedaLoteExistenciaDTO;
import sv.com.diserv.liquidaciones.dto.ConsolidadoAsignacionesDTO;
import sv.com.diserv.liquidaciones.entity.Articulos;
import sv.com.diserv.liquidaciones.entity.LotesExistencia;
import sv.com.diserv.liquidaciones.entity.Movimientos;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.util.Constants;

/**
 *
 * @author sonia.garcia
 */
@Stateless
public class LotesExistenciaBean implements LotesExistenciasBeanLocal {

    static final Logger logger = Logger.getLogger(LotesExistenciaBean.class.getName());
    @PersistenceContext(unitName = "SistemaLiquidacionesEJBPU")
    private EntityManager em;
    @EJB
    GenericDaoServiceBeanLocal genericDaoBean;

    @Override
    public List<LotesExistencia> buscarLoteByCriteria(BusquedaLoteExistenciaDTO re) throws DiservBusinessException {
        logger.info("[buscarLoteByCriteria]Parametros=" + re.toString());
        List<LotesExistencia> response = new ArrayList<>();
        LotesExistencia loteExistencia;
        List<String> condiciones = new ArrayList<>();
        if (re.getIdArticulo()!= null) {
            condiciones.add(" idarticulo=" + re.getIdArticulo()+ " ");
        }
        if (re.getIcc() != null) {
            condiciones.add(" UPPER(icc) LIKE UPPER('%" + re.getIcc()+ "%') ");
        }
        if (re.getImei()!= null) {
            condiciones.add(" UPPER(imei) LIKE UPPER('%" + re.getImei()+ "%') ");
        }
        if (re.getTelefono()!= null) {
            condiciones.add(" UPPER(telefono) LIKE UPPER('%" + re.getTelefono()+ "%') ");
        }
               
        condiciones.add("estado =1");
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM Lotes_Existencia ");
            if (!condiciones.isEmpty()) {
                sb.append(" WHERE ");
                sb.append(condiciones.get(0));
                for (int i = 1; i < condiciones.size(); i++) {
                    sb.append(" AND ");
                    sb.append(condiciones.get(i));
                }
            }
            if(re.getLotes()!=null)
                sb.append(" AND idlote not in ("+re.getLotes()+")");
            sb.append(" ORDER BY idlote DESC ");
            System.out.println("SQL A EJECUTAR:--> " + sb.toString());
            System.out.println("PARAMETROS RECIBIDOS:-->" + re.toString());
            Query q = em.createNativeQuery(sb.toString());
            List<Object[]> lista = q.getResultList();
            if (lista.size() > 0) {
                for (Object[] item : lista) {
                     loteExistencia  = new LotesExistencia();
                     loteExistencia.setIdlote(Integer.parseInt(item[0] != null ? item[0].toString() : "0"));
                     loteExistencia.setIdarticulo(new Articulos(Integer.parseInt(item[1] != null ? item[1].toString() : "0")));
                     loteExistencia.setIdmov(new Movimientos(Integer.parseInt(item[2] != null ? item[2].toString() : "0")));
                     loteExistencia.setIcc(item[3] != null ? item[3].toString() : "N/D");
                     loteExistencia.setImei(item[4] != null ? item[4].toString() : "N/D");
                     loteExistencia.setTelefono(Integer.parseInt(item[5] != null ? item[5].toString() : "0"));
                     
                    response.add(loteExistencia);
                }
            }
        } catch (NoResultException e) {
            logger.info("[consultarBitacoraFinalizadasParametros]No se encontraron registros con criterios recibidos");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron registros con parametros proporcionados");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    
    @Override
    public List<ConsolidadoAsignacionesDTO> buscarLoteByCriterias(BusquedaLoteExistenciaDTO re) throws DiservBusinessException {
        logger.info("[buscarLoteByCriteria]Parametros=" + re.toString());
        List<ConsolidadoAsignacionesDTO> response = new ArrayList<>();
        LotesExistencia loteExistencia;
        ConsolidadoAsignacionesDTO consolidado;
        List<String> condiciones = new ArrayList<>();
        if (re.getIdArticulo()!= null) {
            condiciones.add(" idarticulo=" + re.getIdArticulo()+ " ");
        }
        if (re.getIcc() != null) {
            condiciones.add(" UPPER(icc) LIKE UPPER('%" + re.getIcc()+ "%') ");
        }
        if (re.getImei()!= null) {
            condiciones.add(" UPPER(imei) LIKE UPPER('%" + re.getImei()+ "%') ");
        }
        if (re.getTelefono()!= null) {
            condiciones.add(" UPPER(telefono) LIKE UPPER('%" + re.getTelefono()+ "%') ");
        }
               
        condiciones.add("estado =1");
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM Lotes_Existencia ");
            if (!condiciones.isEmpty()) {
                sb.append(" WHERE ");
                sb.append(condiciones.get(0));
                for (int i = 1; i < condiciones.size(); i++) {
                    sb.append(" AND ");
                    sb.append(condiciones.get(i));
                }
            }
            if(re.getLotes()!=null)
                sb.append(" AND idlote not in ("+re.getLotes()+")");
            sb.append(" ORDER BY idlote DESC ");
            System.out.println("SQL A EJECUTAR:--> " + sb.toString());
            System.out.println("PARAMETROS RECIBIDOS:-->" + re.toString());
            Query q = em.createNativeQuery(sb.toString());
            List<Object[]> lista = q.getResultList();
            if (lista.size() > 0) {
                for (Object[] item : lista) {
                    
                    
                     loteExistencia  = new LotesExistencia();
                     consolidado = new ConsolidadoAsignacionesDTO();
                     loteExistencia.setIdlote(Integer.parseInt(item[0] != null ? item[0].toString() : "0"));
                     loteExistencia.setIdarticulo(new Articulos(Integer.parseInt(item[1] != null ? item[1].toString() : "0")));
                     loteExistencia.setIdmov(new Movimientos(Integer.parseInt(item[2] != null ? item[2].toString() : "0")));
                     loteExistencia.setIcc(item[3] != null ? item[3].toString() : "N/D");
                     loteExistencia.setImei(item[4] != null ? item[4].toString() : "N/D");
                     loteExistencia.setTelefono(Integer.parseInt(item[5] != null ? item[5].toString() : "0"));
                     
                     consolidado.setLote(loteExistencia);
                     consolidado.setSelected(Boolean.FALSE);
                             
                     
                    response.add(consolidado);
                }
            }
        } catch (NoResultException e) {
            logger.info("[consultarBitacoraFinalizadasParametros]No se encontraron registros con criterios recibidos");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron registros con parametros proporcionados");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    
    @Override
    public int actualizarLotes(List<LotesExistencia> lotes) throws DiservBusinessException{
        
        
         int i=0;        
         try {
             for(LotesExistencia lote:lotes){
                Query query;
                query = em.createQuery("UPDATE LotesExistencia SET estado='2' where idlote="+lote.getIdlote());
                i= query.executeUpdate();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }
}
