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
import sv.com.diserv.liquidaciones.dto.OperacionesBodegaDTO;
import sv.com.diserv.liquidaciones.entity.Bodegas;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.util.Constants;

/**
 *
 * @author edwin.alvarenga
 */
@Stateless
public class BodegasBean implements BodegasBeanLocal {

    static final Logger logger = Logger.getLogger(BodegasBean.class.getName());
    @PersistenceContext(unitName = "SistemaLiquidacionesEJBPU")
    private EntityManager em;
    @EJB
    GenericDaoServiceBeanLocal genericDaoBean;

    /**
     * metodo para contar registros ingresados cliente
     *
     * @return Integer con la suma de los registros encontados
     * @throws DiservBusinessException
     */
    @Override
    public Integer countAllBodegas() throws DiservBusinessException {
        logger.log(Level.INFO, "[countAllCliente]INIT");
        int count = 0;
        Query query;
        try {
            query = em.createNamedQuery("Clientes.CountAll");
            count = ((Long) query.getSingleResult()).intValue();
            logger.log(Level.INFO, "[Total de registros encontrados]" + count);
        } catch (Exception e) {
            logger.log(Level.INFO, "[Excepcion en countAllCliente]" + e.toString());
            //throw new DesempenoBusinessException(Constants.CODE_OPERATION_FALLIDA, "");
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
    public List<Bodegas> loadAllBodega(int inicio, int fin) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadAllCliente] desde:" + inicio + " hasta:" + fin);
        List<Bodegas> clienteList = null;
        Query query;
        try {
            query = em.createNamedQuery("Clientes.findAll");
            query.setFirstResult(inicio);
            query.setMaxResults(fin);
            clienteList = query.getResultList();
            if (clienteList != null) {
                logger.log(Level.INFO, "[loadAllCliente] Se encontraron " + clienteList.size() + " clientes");
            }
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllCliente][NoResultException]No se encontraron usuarios");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron cliente");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllCliente][Exception]Se mostro una excepcion al buscar cliente");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return clienteList;
    }

    @Override
    public List<Bodegas> loadAllBodegasByLike(String nombreLike) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadAllClienteByLike] nombreLike:" + nombreLike);
        List<Bodegas> clienteList = null;
        Query query;
        try {
            query = em.createNamedQuery("Clientes.findByNombreClienteLike");
            query.setParameter("nombreCliente", "%" + nombreLike.toUpperCase() + "%");
            query.setMaxResults(Constants.REGISTROS_A_MOSTRAR_LISTA);
            clienteList = query.getResultList();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllClienteByLike][NoResultException]No se encontraron usuarios");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron cliente");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllClienteByLike][Exception]Se mostro una excepcion al buscar cliente");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return clienteList;
    }

    @Override
    public OperacionesBodegaDTO guardarBodega(Bodegas cliente) throws DiservBusinessException {
        OperacionesBodegaDTO response = new OperacionesBodegaDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo guardar cliente");
        try {
            cliente = genericDaoBean.create(cliente);
            response = new OperacionesBodegaDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Cliente creado satisfactoriamente");
            response.setBodega(cliente);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }

    @Override
    public OperacionesBodegaDTO actualizarBodega(Bodegas cliente) throws DiservBusinessException {
        OperacionesBodegaDTO response = new OperacionesBodegaDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo guardar cliente");
        try {
            cliente = genericDaoBean.update(cliente);
            response = new OperacionesBodegaDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Cliente actualizado satisfactoriamente");
            response.setBodega(cliente);
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
        Bodegas cliente;
        List<String> condiciones = new ArrayList<>();
        if (re.getIdCliente() != null) {
            condiciones.add(" UPPER(idCliente) LIKE UPPER('%" + re.getIdCliente() + "%') ");
        }
        if (re.getDepartamento() != null) {
            condiciones.add(" UPPER(departamento) LIKE UPPER('%" + re.getDepartamento() + "%') ");
        }
        if (re.getEmail() != null) {
            condiciones.add(" UPPER(emailCliente) LIKE UPPER('%" + re.getEmail() + "%') ");
        }
        if (re.getMunicipio() != null) {
            condiciones.add(" UPPER(municipio) LIKE UPPER('%" + re.getMunicipio() + "%') ");
        }
        if (re.getNombreCliente() != null) {
            condiciones.add(" UPPER(nombreCliente) LIKE UPPER('%" + re.getNombreCliente() + "%') ");
        }
        if (re.getNumeroIva() != null) {
            condiciones.add(" UPPER(ivaCliente) LIKE UPPER('%" + re.getNumeroIva() + "%') ");
        }
        if (re.getNumeroNit() != null) {
            condiciones.add(" UPPER(nitCliente) LIKE UPPER('%" + re.getNumeroNit() + "%') ");
        }
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM clientes ");
            if (!condiciones.isEmpty()) {
                sb.append(" WHERE ");
                sb.append(condiciones.get(0));
                for (int i = 1; i < condiciones.size(); i++) {
                    sb.append(" AND ");
                    sb.append(condiciones.get(i));
                }
            }
            sb.append(" ORDER BY idCliente DESC ");
            System.out.println("SQL A EJECUTAR:--> " + sb.toString());
            System.out.println("PARAMETROS RECIBIDOS:-->" + re.toString());
            Query q = em.createNativeQuery(sb.toString());
            List<Object[]> lista = q.getResultList();
            if (lista.size() > 0) {
                for (Object[] item : lista) {
//                    cliente = new Clientes();
//                    cliente.setIdCliente(Integer.parseInt(item[0] != null ? item[0].toString() : "0"));
//                    cliente.setNombreCliente(item[1] != null ? item[1].toString() : "N/D");
//                    cliente.setIvaCliente(item[2] != null ? item[2].toString() : "N/D");
//                    cliente.setDireccion(item[3] != null ? item[3].toString() : "N/D");
//                    cliente.setNitCliente(item[4] != null ? item[4].toString() : "N/D");
//                    cliente.setMunicipio(item[5] != null ? item[5].toString() : "N/D");
//                    cliente.setDepartamento(item[6] != null ? item[6].toString() : "N/D");
//                    cliente.setEmailCliente(item[7] != null ? item[7].toString() : "N/D");
//                    cliente.setEstadoCliente(item[8] != null ? Boolean.valueOf(item[8].toString()) : false);
//                    response.add(cliente);
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
        logger.log(Level.INFO, "[loadClienteByNombreLike]Buscando:" + likeNombre);
        Query query;
        try {
            query = em.createQuery("Clientes.findBySomeCriteria");
            query.setParameter("nombreCliente", "%" + likeNombre + "%");
            return query.getResultList();
        } catch (Exception e) {
            logger.log(Level.ERROR, "[getDelito]Exception={0}", e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Metodo permite buscar cliente por su numero de id cliente
     *
     * @param idCliente
     * @return Clientes encontrado, null en caso de no encontrar nada
     * @throws DiservBusinessException
     */
    @Override
    public Bodegas loadBodegaByID(Integer idCliente) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadClienteByID] Idcliente:" + idCliente);
        Bodegas cliente = null;
        Query query;
        try {
            query = em.createNamedQuery("Clientes.findByIdCliente");
            query.setParameter("idCliente", idCliente);
            cliente = (Bodegas) query.getSingleResult();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadClienteByID][NoResultException]No se encontraron usuarios");
//            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron cliente");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadClienteByID][Exception]Se mostro una excepcion al buscar cliente");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return cliente;
    }

}
