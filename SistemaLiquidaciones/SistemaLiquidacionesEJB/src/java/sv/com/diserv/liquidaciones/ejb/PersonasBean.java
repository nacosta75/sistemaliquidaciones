/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import sv.com.diserv.liquidaciones.dto.BusquedaPersonaDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesPersonaDTO;
import sv.com.diserv.liquidaciones.entity.Bodegas;
import sv.com.diserv.liquidaciones.entity.Empresas;
import sv.com.diserv.liquidaciones.entity.Personas;
import sv.com.diserv.liquidaciones.entity.Sucursales;
import sv.com.diserv.liquidaciones.entity.TiposPersona;
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
     * metodo para contar registros ingresados persona
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
            query = em.createQuery("SELECT count(s) FROM Personas s where s.idtipopersona.idtipopersona=" + tipoPersona);
            count = ((Long) query.getSingleResult()).intValue();
            logger.log(Level.INFO, "[Total de registros encontrados]" + count);
        } catch (Exception e) {
            logger.log(Level.INFO, "[Excepcion en countAllPersona]" + e.toString());
            e.printStackTrace();
        }
        return count;
    }

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
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron persona");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllBodegaByLike][Exception]Se mostro una excepcion al buscar persona");
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
    public OperacionesPersonaDTO eliminarPersona(Personas persona) throws DiservBusinessException {
        OperacionesPersonaDTO response = new OperacionesPersonaDTO(Constants.CODE_OPERATION_FALLIDA, "no se pudo eliminar persona");
        try {
            Boolean respuesta = genericDaoBean.delete(persona);
            response = new OperacionesPersonaDTO(Constants.CODE_OPERACION_SATISFACTORIA, "Persona eliminada satisfactoriamente");
        } catch (Exception e) {
            e.printStackTrace();
            response.setMensajeRespuesta(e.toString());
        }
        return response;
    }

    @Override
    public List<Personas> buscarPersonaByCriteria(BusquedaPersonaDTO re) throws DiservBusinessException {
        logger.info("[consultarBitacoraFinalizadasParametros]Parametros=" + re.toString());
        List<Personas> response = new ArrayList<>();
        Personas personas;
        List<String> condiciones = new ArrayList<>();
        if (re.getIdPersona() != null) {
            condiciones.add(" idPersona=" + re.getIdPersona() + " ");
        }
        if (re.getNombre() != null) {
            condiciones.add(" UPPER(nombre) LIKE UPPER('%" + re.getNombre() + "%') ");
        }
        if (re.getNit() != null) {
            condiciones.add(" UPPER(nit) LIKE UPPER('%" + re.getNit() + "%') ");
        }
        if (re.getNumeroRegistro() != null) {
            condiciones.add(" UPPER(no_Registro_Fiscal) LIKE UPPER('%" + re.getNumeroRegistro() + "%') ");
        }
        if (re.getTipoPersona() != 0) {
            condiciones.add(" idtipopersona = " + re.getTipoPersona() + " ");
        }

        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" SELECT * FROM personas ");
            if (!condiciones.isEmpty()) {
                sb.append(" WHERE ");
                sb.append(condiciones.get(0));
                for (int i = 1; i < condiciones.size(); i++) {
                    sb.append(" AND ");
                    sb.append(condiciones.get(i));
                }
            }
            sb.append(" ORDER BY idPersona DESC ");
            System.out.println("SQL A EJECUTAR:--> " + sb.toString());
            System.out.println("PARAMETROS RECIBIDOS:-->" + re.toString());
            Query q = em.createNativeQuery(sb.toString());
            List<Object[]> lista = q.getResultList();
            if (lista.size() > 0) {
                for (Object[] item : lista) {
                    personas = new Personas();
                    personas.setIdpersona(Integer.parseInt(item[0] != null ? item[0].toString() : "0"));
                    personas.setIdtipopersona(new TiposPersona(Integer.parseInt(item[1] != null ? item[1].toString() : "0")));
                    personas.setIdempresa(new Empresas(Integer.parseInt(item[2] != null ? item[2].toString() : "0")));
                    personas.setIdsucursal(new Sucursales(Integer.parseInt(item[3] != null ? item[3].toString() : "0")));
                    personas.setNombre(item[4] != null ? item[4].toString() : "N/D");
                    personas.setRazonSocial(item[5] != null ? item[5].toString() : "N/D");
                    personas.setCalleOPasaje(item[6] != null ? item[6].toString() : "N/D");
                    personas.setColonia(item[7] != null ? item[7].toString() : "N/D");
                    personas.setNit(item[8] != null ? item[8].toString() : "N/D");
                    personas.setNoRegistroFiscal(item[9] != null ? item[9].toString() : "N/D");
                    personas.setTelefono1(item[10] != null ? item[10].toString() : "N/D");
                    personas.setExt1(Integer.parseInt(item[11] != null ? item[11].toString() : "0"));
                    personas.setTelefono2(item[12] != null ? item[12].toString() : "N/D");
                    personas.setExt2(Integer.parseInt(item[13] != null ? item[13].toString() : "0"));
                    personas.setTelefono3(item[14] != null ? item[14].toString() : "N/D");
                    personas.setExt3(Integer.parseInt(item[15] != null ? item[15].toString() : "0"));
                    personas.setFax(item[16] != null ? item[16].toString() : "N/D");
                    personas.setCreditoActivo(item[17] != null ? item[17].toString() : "N/D");
                    personas.setLimiteCredito(new BigDecimal(item[18] != null ? item[18].toString() : "0"));
                    personas.setCorreo(item[19] != null ? item[19].toString() : "N/D");
                    personas.setUltSaldo(new BigDecimal(item[20] != null ? item[20].toString() : "0"));
                    SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
                    String strFecha = item[21] != null ? item[21].toString() : "";
                    Date fecha = null;
                    try {

                        fecha = formatoDelTexto.parse(strFecha);

                    } catch (ParseException ex) {

                        ex.printStackTrace();

                    }
                    personas.setFechaUltSaldo(fecha);
                    personas.setEstadoCivil(item[22] != null ? item[22].toString() : "N/D");
                    personas.setIdusuariocrea(Integer.parseInt(item[23] != null ? item[23].toString() : "0"));

                    response.add(personas);
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
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Personas> loadAllPersonaByTipoAndSucursal(int tipo,int sucursal) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadAllPersonaByTipo]");
        List<Personas> personaList = null;
        Query query;
        try {
            query = em.createNamedQuery("Personas.findAllByTipoSuc");
            query.setParameter("idtipopersona", tipo);
            query.setParameter("idsucursal", sucursal);
            personaList = query.getResultList();
            if (personaList != null) {
                logger.log(Level.INFO, "[loadAllPersonaByTipo] Se encontraron " + personaList.size() + " personas");
            }
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllPersonaByTipo][NoResultException]No se encontraron personas");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron persona");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllPersona][Exception]Se mostro una excepcion al buscar persona");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return personaList;
    }

    @Override
    public Personas buscarPersonaById(int id) throws DiservBusinessException  {
          logger.log(Level.INFO, "[buscarPersonaById] " );
        Personas personas = null;
        Query query;
        try {
            query = em.createNamedQuery("Personas.findByIdpersona");
            query.setParameter("idpersona", id);
            personas = (Personas) query.getSingleResult();
            if (personas!= null) {
                logger.log(Level.INFO, "[buscarPersonaById] Se encontraron " + personas.getNombre() + " personas");
            }
            
        }
         catch (Exception e) {
            logger.log(Level.INFO, "[Excepcion en buscarPersonaById]" + e.toString());
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return personas;
    }

}
