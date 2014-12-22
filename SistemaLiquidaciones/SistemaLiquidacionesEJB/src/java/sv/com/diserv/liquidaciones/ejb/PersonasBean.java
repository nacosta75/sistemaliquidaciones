/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.ArrayList;
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
import sv.com.diserv.liquidaciones.dto.BusquedaBodegaDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesPersonaDTO;
import sv.com.diserv.liquidaciones.entity.Bodegas;
import sv.com.diserv.liquidaciones.entity.Personas;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.util.Constants;

/**
 *
 * @author sonia.garcia
 */
@Stateless
public class PersonasBean implements PersonasBeanLocal {

    static final Logger logger = Logger.getLogger(PersonasBean.class.getName());
    @PersistenceContext(unitName = "SistemaLiquidacionesEJBPU")
    private EntityManager em;
    @EJB
    GenericDaoServiceBeanLocal genericDaoBean;

    /**
     * metodo para contar registros ingresados bodega
     *
     * @return Integer con la suma de los registros encontados
     * @throws DiservBusinessException
     */
    @Override
    public Integer countAllPersonas(int tipoPersona) throws DiservBusinessException {
        logger.log(Level.INFO, "[countAllPersona]INIT");
        int count = 0;
        Query query;
        try {
            query = em.createQuery("SELECT count(s) FROM Personas s where s.idtipopersona.idtipopersona="+tipoPersona);
            count = ((Long) query.getSingleResult()).intValue();
            logger.log(Level.INFO, "[Total de registros encontrados]" + count);
        } catch (Exception e) {
            logger.log(Level.INFO, "[Excepcion en countAllPersona]" + e.toString());
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Metodo para extraer todos los usuarios
     *
     * @param inicio: Primer registro a mostrar
     * @param fin: Ultimo registro a mostrar
     * @return List<Users>: Lista de usuarios desde la base de datos
     * @throws DesempenoBusinessException
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Personas> loadAllPersona(int inicio, int fin, int tipo) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadAllPersona] desde:" + inicio + " hasta:" + fin);
        List<Personas> personaList = null;
        Query query;
        try {
            query = em.createNamedQuery("Personas.findAllByTipo");
            query.setParameter("idtipopersona", tipo);
            query.setFirstResult(inicio);
            query.setMaxResults(fin);
            personaList = query.getResultList();
            if (personaList != null) {
                logger.log(Level.INFO, "[loadAllPersona] Se encontraron " + personaList.size() + " personas");
            }
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllPersona][NoResultException]No se encontraron personas");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron persona");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllPersona][Exception]Se mostro una excepcion al buscar persona");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return personaList;
    }

    @Override
    public List<Personas> loadAllPersonasByLike(String nombreLike) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadAllPersonaByLike] nombreLike:" + nombreLike);
        List<Personas> personaList = null;
        Query query;
        try {
            query = em.createNamedQuery("Personas.findByNombrePersonaLike");
            query.setParameter("nombrePersona", "%" + nombreLike.toUpperCase() + "%");
            query.setMaxResults(Constants.REGISTROS_A_MOSTRAR_LISTA);
            personaList = query.getResultList();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllBodegaByLike][NoResultException]No se encontraron usuarios");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron bodega");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllBodegaByLike][Exception]Se mostro una excepcion al buscar bodega");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return personaList;
    }

    @Override
    public OperacionesPersonaDTO guardarPersona(Personas persona) throws DiservBusinessException {
        OperacionesPersonaDTO response = new OperacionesPersonaDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo guardar persona");
        try {
            persona = genericDaoBean.create(persona);
            response = new OperacionesPersonaDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Persona creada satisfactoriamente");
            response.setPersona(persona);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }

    @Override
    public OperacionesPersonaDTO actualizarPersona(Personas persona) throws DiservBusinessException {
        OperacionesPersonaDTO response = new OperacionesPersonaDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo guardar persona");
        try {
            persona = genericDaoBean.update(persona);
            response = new OperacionesPersonaDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Persona actualizada satisfactoriamente");
            response.setPersona(persona);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }

    @Override
    public List<Bodegas> buscarBodegaByCriteria(BusquedaBodegaDTO re) throws DiservBusinessException {
        logger.info("[consultarBitacoraFinalizadasParametros]Parametros=" + re.toString());
        List<Bodegas> response = new ArrayList<>();
        Bodegas bodegas;
        List<String> condiciones = new ArrayList<>();
        if (re.getIdBodega()!= null) {
            condiciones.add(" UPPER(idBodega) LIKE UPPER('%" + re.getIdBodega() + "%') ");
        }
        if (re.getCodigo() != null) {
            condiciones.add(" UPPER(codigo) LIKE UPPER('%" + re.getCodigo() + "%') ");
        }
        if (re.getNombre() != null) {
            condiciones.add(" UPPER(nombre) LIKE UPPER('%" + re.getNombre() + "%') ");
        }
        if (re.getDireccion() != null) {
            condiciones.add(" UPPER(direccion) LIKE UPPER('%" + re.getDireccion() + "%') ");
        }
        if (re.getTelefono() != null) {
            condiciones.add(" UPPER(telefono) LIKE UPPER('%" + re.getTelefono() + "%') ");
        }
        if (re.getEncargado() != null) {
            condiciones.add(" UPPER(encargado) LIKE UPPER('%" + re.getEncargado() + "%') ");
        }
        if (re.getActiva() != null) {
            condiciones.add(" UPPER(activa) LIKE UPPER('%" + re.getActiva() + "%') ");
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM bodegas ");
            if (!condiciones.isEmpty()) {
                sb.append(" WHERE ");
                sb.append(condiciones.get(0));
                for (int i = 1; i < condiciones.size(); i++) {
                    sb.append(" AND ");
                    sb.append(condiciones.get(i));
                }
            }
            sb.append(" ORDER BY idBodega DESC ");
            System.out.println("SQL A EJECUTAR:--> " + sb.toString());
            System.out.println("PARAMETROS RECIBIDOS:-->" + re.toString());
            Query q = em.createNativeQuery(sb.toString());
            List<Object[]> lista = q.getResultList();
            if (lista.size() > 0) {
                for (Object[] item : lista) {
                    bodegas = new Bodegas();
                    bodegas.setIdbodega(Integer.parseInt(item[0] != null ? item[0].toString() : "0"));
                    //bodegas.setCodigo(item[1] != null ? item[1].toString() : "N/D");
                    bodegas.setNombre(item[2] != null ? item[2].toString() : "N/D");
                    bodegas.setDireccion(item[3] != null ? item[3].toString() : "N/D");
                    bodegas.setTelefono(item[4] != null ? item[4].toString() : "N/D");
                    bodegas.setEncargado(item[5] != null ? item[5].toString() : "N/D");
                   // bodegas.setIdempresa(item[6] != null ? item[6].toString() : "N/D");
                    //bodegas.setActiva(item[7] != null ? item[7].toString() : "N/D");
                   // bodega.setEstadoBodega(item[8] != null ? Boolean.valueOf(item[8].toString()) : false);
                    response.add(bodegas);
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

    /**
     *
     * @param likeNombre
     * @return
     * @throws DiservBusinessException
     */
    @Override
    public List<Bodegas> loadBodegaByNombreLike(String likeNombre) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadBodegaByNombreLike]Buscando:" + likeNombre);
        Query query;
        try {
            query = em.createQuery("Bodegas.findBySomeCriteria");
            query.setParameter("nombreBodega", "%" + likeNombre + "%");
            return query.getResultList();
        } catch (Exception e) {
            logger.log(Level.ERROR, "[getDelito]Exception={0}", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Metodo permite buscar bodega por su numero de id bodega
     *
     * @param idBodega
     * @return Bodegas encontrado, null en caso de no encontrar nada
     * @throws DiservBusinessException
     */
    @Override
    public Bodegas loadBodegaByID(Integer idBodega) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadBodegaByID] Idbodega:" + idBodega);
        Bodegas bodega = null;
        Query query;
        try {
            query = em.createNamedQuery("Bodegas.findByIdBodega");
            query.setParameter("idBodega", idBodega);
            bodega = (Bodegas) query.getSingleResult();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadBodegaByID][NoResultException]No se encontraron usuarios");
//            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron bodega");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadBodegaByID][Exception]Se mostro una excepcion al buscar bodega");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return bodega;
    }

}
