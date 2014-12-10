/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sv.com.diserv.liquidaciones.dto.BusquedaUserDTO;
import sv.com.diserv.liquidaciones.dto.CustomUserDTO;
import sv.com.diserv.liquidaciones.dto.GenericResponse;
import sv.com.diserv.liquidaciones.entity.Authorities;
import sv.com.diserv.liquidaciones.entity.Groupauthorities;
import sv.com.diserv.liquidaciones.entity.Groupmembers;
import sv.com.diserv.liquidaciones.entity.Groups;
import sv.com.diserv.liquidaciones.entity.Usuarios;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;
import sv.com.diserv.liquidaciones.util.Constants;

/**
 *
 * @author edwin.alvarenga
 */
@Stateless
public class ManejadorUsuarioBean implements ManejadorUsuarioBeanLocal {

    static final Logger logger = Logger.getLogger(ManejadorUsuarioBean.class.getName());
    @EJB
    GenericDaoServiceBeanLocal genericDaoBean;
    @PersistenceContext(unitName = "SistemaLiquidacionesEJBPU")
    private EntityManager em;

    /**
     *
     * @param name
     * @return
     * @throws BadCredentialsException
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public CustomUserDTO findByName(String name) throws BadCredentialsException {
        logger.log(Level.INFO, "[findByName]load user={0}" + name);
        Usuarios us;
        Boolean enable;
        Query query;
        CustomUserDTO user = new CustomUserDTO();
        try {
            query = em.createNamedQuery("Usuarios.findByNombreUsuario");
            query.setParameter("nombreUsuario", name);
            us = (Usuarios) query.getSingleResult();
            if (us != null) {
                enable = true;//us.getStatus();
                user.setAccountNonExpired(enable);
                us.getGroupmembersList();
                user.setAccountNonLocked(enable);
                user.setCredentialsNonExpired(enable);
                user.setEnabled(enable);
                user.setUsername(us.getNombreusuario());
                user.setPassword(us.getContrasena());
                user.setPerfiles(getGrantedAuthority(us.getNombreusuario()));
                user.setUsuario(us);
                user.setRegistrosLista(us.getRegistroslista() != null ? us.getRegistroslista() : Constants.REGISTROS_A_MOSTRAR_LISTA);
                logger.log(Level.INFO, "[findByName] Se recupero usuario={0}" + us.getNombreCompleto());
            }
        } catch (NoResultException ex) {
            logger.fatal("Exception:" + ex);
            logger.log(Level.INFO, "[findByName][NoResultException]No se encontro usuario");
            //throw new UsernameNotFoundException("Usuario no encontrado");
            //       throws UsernameNotFoundException, DataAccessException
            throw new BadCredentialsException("No se encontro usuario con las credenciales ingresadas");
        } catch (UsernameNotFoundException ex) {
            throw new BadCredentialsException("No se encontro usuario con las credenciales ingresadas," + ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadCredentialsException("Ocurrio una exception al validar usuario," + e.getMessage());
        }
        return user;
    }

    /**
     * Metodo para obtener el numero de usuarios
     *
     * @return Cantidad de usuarios encontrados
     */
    @Override
    public int countUsers() throws DiservBusinessException {
        logger.log(Level.INFO, "[countUsers]INIT");
        int count = 0;
        Query query;
        try {
            query = em.createNamedQuery("Usuarios.countAll");
            count = ((Long) query.getSingleResult()).intValue();
            logger.log(Level.INFO, "[Total de registros encontrados]" + count);
        } catch (NoResultException e) {
            e.printStackTrace();
        } catch (Exception e) {
            logger.log(Level.INFO, "[Excepcion en countUsers]" + e.toString());
            //throw new EnlaceBusinessException(Constants.CODE_OPERATION_FALLIDA, "");
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
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public List<Usuarios> loadAllUser(Integer inicio, Integer fin) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadAllUser] desde:" + inicio + " hasta:" + fin);
        List<Usuarios> userList = null;
        Query query;
        try {
            query = em.createNamedQuery("Usuarios.findAll");
            query.setFirstResult(inicio);
            query.setMaxResults(fin);
            userList = query.getResultList();
            if (userList != null) {
                //cargamos los roles de usuario
                for (Usuarios usuario : userList) {
                    usuario.getGroupmembersList();
                }
                logger.log(Level.INFO, "[loadAllUser] Se encontraron " + userList.size() + " usuarios");
            }
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllUser][NoResultException]No se encontraron usuarios");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron usuarios");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllUser][Exception]Se mostro una excepcion al buscar usuarios");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return userList;
    }

    /**
     *
     * @return @throws
     * sv.com.diserv.liquidaciones.exception.DiservBusinessException
     */
    @Override
    public List<Usuarios> loadAllUserSinFiltros() throws DiservBusinessException {
        logger.log(Level.INFO, "[loadAllUserSinFiltros] ");
        List<Usuarios> userList = null;
        Query query;
        try {
            query = em.createNamedQuery("Usuarios.findAll");
            userList = query.getResultList();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllUser][NoResultException]No se encontraron usuarios");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron usuarios");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllUser][Exception]Se mostro una excepcion al buscar usuarios");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return userList;
    }

    /**
     * Metodo para obtener el numero de roles
     *
     * @return Cantidad de roles encontrados
     */
    @Override
    public int countRoles() throws DiservBusinessException {
        logger.log(Level.INFO, "[countRoles]INIT");
        int count = 0;
        Query query;
        try {
            query = em.createNamedQuery("Groups.countAll");
            count = ((Long) query.getSingleResult()).intValue();
            logger.log(Level.INFO, "[Total de registros encontrados]" + count);
        } catch (Exception e) {
            logger.log(Level.ERROR, "[Excepcion en countRoles]" + e.toString());
            //throw new DesempenoBusinessException(Constants.CODE_OPERATION_FALLIDA, "");
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Metodo para extraer todos los roles desde la DB
     *
     * @param inicio: Primer registro a mostrar
     * @param fin: Ultimo registro a mostrar
     * @return List<Roles>: Lista de roles desde la base de datos
     */
    @Override
    public List<Groups> loadAllRoles(Integer inicio, Integer fin) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadAllRoles] desde:" + inicio + " hasta:" + fin);
        List<Groups> rolList = null;
        Query query;
        try {
            query = em.createNamedQuery("Groups.findAll");
            query.setFirstResult(inicio);
            query.setMaxResults(fin);
            rolList = query.getResultList();
            if (rolList != null) {
                logger.log(Level.INFO, "[loadAllUser] Se encontraron " + rolList.size() + " roles");
            }
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllRoles][NoResultException]No se encontraron roles");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron roles");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllRoles][Exception]Se mostro una excepcion al buscar roles");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return rolList;
    }

    /**
     * Metodo para buscar los roles asignados a un usuario especifico
     *
     * @return List<UserRole>
     */
    @Override
    public List<Groupmembers> findUserRoleByNumeroCarnet(String nombreUsuario) throws DiservBusinessException {
        logger.log(Level.INFO, "[findUserRoleByNumeroCarnet] numero de usuario:" + nombreUsuario);
        List<Groupmembers> userRolList = null;
        Query query;
        try {
            query = em.createNamedQuery("GroupMembers.findByUserId");
            query.setParameter("idUsuario", nombreUsuario);
            userRolList = query.getResultList();
            if (userRolList != null) {
                logger.log(Level.INFO, "[findUserRoleByNumeroCarnet] Se encontraron " + userRolList.size() + " roles asignados al usuarios:" + nombreUsuario);
            }
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[findUserRoleByNumeroCarnet][NoResultException]No se encontraron roles para este usuario");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron roles para este usuario");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[findUserRoleByNumeroCarnet][Exception]Se mostro una excepcion al buscar roles de usuario");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return userRolList;
    }

    /**
     * Metodo para extraer una combinacion RolUsuario partiendo del id de
     * usuario y de rol
     *
     * @param idUsuario
     * @param idRole
     * @return UserRole
     */
    @Override
    public Groupmembers getUserRoleByUserAndRole(String idUsuario, Integer idRole) throws DiservBusinessException {
        logger.log(Level.INFO, "[getUserRoleByUserAndRole] usuario:" + idUsuario + " and rolId:" + idRole);
        Groupmembers userRol;
        Query query;
        try {
            query = em.createNamedQuery("GroupMembers.findByUserIdAndGroupId");
            query.setParameter("idUsuario", idUsuario);
            query.setParameter("idGrupo", idRole);
            userRol = (Groupmembers) query.getSingleResult();
            if (userRol != null) {
                logger.log(Level.INFO, "[getUserRoleByUserAndRole] Se encontro el rol " + userRol.getId() + " asignado al usuario:" + idUsuario);
            }
        } catch (NoResultException ex) {
            userRol = null;
            logger.log(Level.INFO, "[getUserRoleByUserAndRole][NoResultException]No se encontraron coincidencias para este usuario");
//            throw new DesempenoBusinessException(Constants.CODE_OPERATION_FALLIDA,"No se encontraron coincidencias para este usuario");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[getUserRoleByUserAndRole][Exception]Se mostro una excepcion al buscar coincidencias de roles de usuario");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return userRol;
    }

    /**
     * Metodo para guardar una relacion Usuario-Rol
     *
     * @param data: Objeto de tipo UserRole a guardar
     * @return GenericResponse
     */
    @Override
    public GenericResponse saveUserRole(Groupmembers data) throws DiservBusinessException {
        logger.info("[saveUserRole]Usuario:" + data.getIdusuario().getNombreCompleto() + ", Rol:" + data.getGroupid().getGroupname());
        GenericResponse response = new GenericResponse(Constants.CODE_OPERATION_FALLIDA, "No se pudo agregar rol a usuario");
        try {
            genericDaoBean.create(data);
            response = new GenericResponse(Constants.CODE_OPERACION_SATISFACTORIA, "[saveUserRole]Rol asignado satisfactoriamente");
        } catch (Exception e) {
            System.out.println("Causa:" + e.getCause() + ", mensaje:" + e.getLocalizedMessage());
            e.printStackTrace();
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, e.getMessage());
        }
        return response;
    }

//    /**
//     *
//     * @param user
//     * @param isNew
//     * @return
//     * @throws AtsBusinessException
//     */
//    @Override
//    public OperacionesUsuarioDTO guardarUsuario(Usuarios user, Boolean isNew) throws DiservBusinessException {
//        logger.info("[saveUserRole]guardarUsuario:");
//        OperacionesUsuarioDTO response = new OperacionesUsuarioDTO(Constants.CODE_OPERATION_FALLIDA, "No se pudo guardar-actualizar usuario");
//        Usuarios us;
//        try {
//            if (isNew) {
//                user = genericDaoBean.create(user);
//                response.setUsuario(user);
//            } else {
//                us = em.getReference(Usuarios.class, user.getIdUsuario());
//                us.setNombreCompleto(user.getNombreCompleto());
//                us.setCodigoEmpleado(user.getCodigoEmpleado());
//                us.setContrasena(user.getContrasena());
//                us.setNombreUsuario(user.getNombreUsuario());
////                us.setRegistrosLista(user.getRegistrosLista());
//                us.setStatus(user.getStatus());
//                em.merge(us);
//                em.flush();
//                em.refresh(us);
//                response.setUsuario(us);
//            }
//            response.setCodigoRespuesta(Constants.CODE_OPERACION_SATISFACTORIA);
//            response.setMensajeRespuesta("Usuario guardado satisfactoriamente");
//        } catch (Exception e) {
//            System.out.println("Causa:" + e.getCause() + ", mensaje:" + e.getLocalizedMessage());
//            e.printStackTrace();
//            response.setMensajeRespuesta(e.toString());
//            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, e.getMessage());
//        }
//        return response;
//    }
    /**
     * Metodo para borrar una relacion Usuario-Rol
     *
     * @param data: Objeto de tipo UserRole a borrar
     * @return GenericResponse
     */
    @Override
    public GenericResponse deleteUserRole(Groupmembers data) throws DiservBusinessException {
        logger.info("[deleteUserRole]Usuario:" + data.getIdusuario().getNombreCompleto() + ", Rol:" + data.getGroupid().getGroupname());
        GenericResponse response = new GenericResponse(Constants.CODE_OPERATION_FALLIDA, "No se pudo eliminar rol a usuario");
        try {
            genericDaoBean.delete(data);
            response = new GenericResponse(Constants.CODE_OPERACION_SATISFACTORIA, "Eliminacion satisfactoria");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     *
     * @param idUsuario
     * @return
     * @throws DesempenoBusinessException
     */
    private Collection<Authorities> loadPermisosByUserid(String idUsuario) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadPermisosByUserid] userId:" + idUsuario);
        List<Authorities> listapermisos = null;
        Query query;
        try {
            query = em.createNamedQuery("Authorities.findByIdNombreUsuario");
            query.setParameter("idUsuario", idUsuario);
            listapermisos = query.getResultList();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadPermisosByUserid][NoResultException]No se recuperaron permisos para usuario");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadPermisosByUserid][Exception]Se mostro una excepcion al buscar coincidencias de roles de usuario");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return listapermisos;
    }

    /**
     *
     * @param IdUsuario
     * @return
     */
    private List<GrantedAuthority> getGrantedAuthority(String nombreUsuario) {
        ArrayList<GrantedAuthority> rightsGrantedAuthorities = null;
        try {
            // get the list of rights for a specified user from db.
            final Collection<Authorities> rights = loadPermisosByUserid(nombreUsuario);
            if (rights != null) {
                rightsGrantedAuthorities = new ArrayList<GrantedAuthority>(rights.size());
                // now create for all rights a GrantedAuthority entry
                // and fill the GrantedAuthority List with these authorities.
                for (final Authorities right : rights) {
                    rightsGrantedAuthorities.add(new GrantedAuthorityImpl(right.getNombre()));
                }
            }
            // create the list for the spring grantedRights
        } catch (Exception e) {
            logger.error("[getGrantedAuthority]Exception:" + e);
            e.printStackTrace();
        }
        return rightsGrantedAuthorities;
    }

    /**
     * /**
     * Metodo para obtener el numero de Derechos
     *
     * @return Cantidad de derechos encontrados
     */
    @Override
    public Integer countRights() throws DiservBusinessException {
        logger.log(Level.INFO, "[countRights]INIT");
        int count = 0;
        Query query;
        try {
            query = em.createNamedQuery("Authorities.countdAll");
            count = ((Long) query.getSingleResult()).intValue();
            logger.log(Level.INFO, "[Total de registros encontrados]" + count);
        } catch (Exception e) {
            logger.log(Level.ERROR, "[Excepcion en countRights]" + e.toString());
            //throw new DesempenoBusinessException(Constants.CODE_OPERATION_FALLIDA, "");
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Metodo para extraer todos los derechos
     *
     * @param inicio: Primer registro a mostrar
     * @param cantReg: Cantidad de registros a mostrar
     * @return List<Rights>: Lista de derechos desde la base de datos
     */
    @Override
    public List<Authorities> loadAllRights(Integer inicio, Integer cantReg) throws DiservBusinessException {
        logger.log(Level.INFO, "[loadAllRights] desde:" + inicio + ", mostrar: " + cantReg + " registros");
        List<Authorities> rightList = null;
        Query query;
        try {
            query = em.createNamedQuery("Authorities.findAll");
            query.setFirstResult(inicio);
            query.setMaxResults(cantReg);
            rightList = query.getResultList();
            if (rightList != null) {
                logger.log(Level.INFO, "[loadAllRights] Se encontraron " + rightList.size() + " registros");
            }
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllRights][NoResultException]No se encontraron registros");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron registros");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllRights][Exception]Se mostro una excepcion al buscar derechos");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return rightList;
    }

    /**
     * Metodo para buscar los derechos asignados a un grupo especifico
     *
     * @return List<GroupRight>
     */
    @Override
            public List<Groupauthorities> findPermisoRolByUdRole(Integer idRol) throws DiservBusinessException {
        logger.log(Level.INFO, "[findGroupRightByGroupId] id de grupo:" + idRol);
        List<Groupauthorities> groupRightList = null;
        Query query;
        try {
            query = em.createNamedQuery("GroupAuthorities.findByIdGroup");
            query.setParameter("idGroup", idRol);
            groupRightList = query.getResultList();
            if (groupRightList != null) {
                logger.log(Level.INFO, "[findGroupRightByGroupId] Se encontraron " + groupRightList.size() + " derechos asignados al rol:" + idRol);
            }
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[findGroupRightByGroupId][NoResultException]No se encontraron permiso para este grupo");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron permisos para este grupo");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.ERROR, "[findGroupRightByGroupId][Exception]Se mostro una excepcion al buscar permisos de grupo");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return groupRightList;
    }

    /**
     * Metodo para extraer una combinacion Grupo-Derecho partiendo del id de
     * grupo y de derecho
     *
     * @param groupId: Id del grupo a buscar
     * @param rightId: Id del derecho a buscar
     * @return GroupRight
     */
    @Override
    public Groupauthorities getGroupRightByGroupAndRight(int groupId, int rightId) throws DiservBusinessException {
        logger.log(Level.INFO, "[getGroupRightByGroupAndRight] groupId:" + groupId + " and rightId:" + rightId);
        Groupauthorities groupRight = new Groupauthorities();
        Query query;
        try {
            query = em.createNamedQuery("GroupAuthorities.findByIdGroupAndAuthority");
            query.setParameter("idGroup", groupId);
            query.setParameter("idPermiso", rightId);
            groupRight = (Groupauthorities) query.getSingleResult();
        } catch (NoResultException ex) {
            groupRight = null;
            logger.log(Level.INFO, "[getGroupRightByGroupAndRight][NoResultException]No se encontraron coincidencias para este grupo");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[getGroupRightByGroupAndRight][Exception]Se mostro una excepcion al buscar coincidencias de derechos de grupo");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return groupRight;
    }

    /**
     * Metodo para guardar una relacion Grupo-Derecho
     *
     * @param data: Objeto de tipo GroupRight a guardar
     * @return GenericResponse
     */
    @Override
    public GenericResponse saveGroupRight(Groupauthorities data) throws DiservBusinessException {
        logger.info("[saveGroupRight]Grupo:" + data.getGroupid().getGroupname() + ", Derecho:" + data.getIdauthority().getNombre());
        GenericResponse response = new GenericResponse(Constants.CODE_OPERATION_FALLIDA, "Fallo al guardar permiso grupo");
        try {
            genericDaoBean.create(data);
            response = new GenericResponse(Constants.CODE_OPERACION_SATISFACTORIA, "Registro insertado ok");
        } catch (Exception e) {
            System.out.println("Causa:" + e.getCause() + ", mensaje:" + e.getLocalizedMessage());
            e.printStackTrace();
        }
        return response;
    }

    /**
     * Metodo para borrar una relacion Grupo-Derecho
     *
     * @param data: Objeto de tipo GroupRight a borrar
     * @return GenericResponse
     */
    @Override
    public GenericResponse deleteGroupRight(Groupauthorities data) throws DiservBusinessException {
        logger.info("[deleteGroupRight]Grupo:" + data.getGroupid().getGroupname());
        GenericResponse response = new GenericResponse(Constants.CODE_OPERATION_FALLIDA, "Operacion fallida");
        try {
            genericDaoBean.delete(data);
            response = new GenericResponse(Constants.CODE_OPERACION_SATISFACTORIA, "Operacion ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * *
     *
     * @param request
     * @return
     */
    @Override
    public List<Usuarios> buscarUsuariosPorCriterios(BusquedaUserDTO request) throws DiservBusinessException {
        List<Usuarios> response = null;
        Query query;
        try {
            query = em.createNamedQuery("Usuario.findBySomeCriteria");
            query.setParameter("carnet", request.getCarnetUsuario());
            query.setParameter("usuario", request.getUsuarioUsuario() != null ? "%" + request.getUsuarioUsuario() + "%" : "%%");
            query.setParameter("nombre", request.getNombreUsuario() != null ? "%" + request.getNombreUsuario() + "%" : "%%");
            response = query.getResultList();
        } catch (NoResultException nre) {
            logger.log(Level.ERROR, "[buscarUsuariosPorCriterios][NoResultException]No se encontraron registros");
            nre.printStackTrace();
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se entontraron registros con los criterios recibidos");
        } catch (Exception e) {
            logger.fatal("[buscarUsuariosPorCriterios][Exception]:" + e);
            e.printStackTrace();
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, e.getLocalizedMessage());
        }
        return response;
    }

    /**
     * *
     *
     * @return
     */
    private static String getIpRequest() {
        String remoteAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();
        if (remoteAddress != null) {
            System.err.println("remote address=" + remoteAddress);
        } else {
            System.err.println("No se pudo cargar remote address");
        }
        return remoteAddress;
    }

    /**
     * Metodo para obtener el listado de usuarios pertenecientes a una misma
     * oficina
     *
     * @param codigoOficina: codigo de la oficina a buscar usuarios
     * @return List<User>
     */
    @Override
    public List<Usuarios> obtenerUsuariosPorOficina(Integer codigoOficina) throws DiservBusinessException {
        logger.log(Level.INFO, "[obtenerUsuariosPorOficina]codigoOficina:" + codigoOficina);
        List<Usuarios> response = null;
        Query query;
        try {
            query = em.createNamedQuery("Usuario.findByIdOficina");
            query.setParameter("idOficina", codigoOficina);
            response = query.getResultList();
        } catch (NoResultException nre) {
            logger.log(Level.ERROR, "[obtenerUsuariosPorOficina][NoResultException]No se encontraron registros");
            nre.printStackTrace();
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se entontraron registros");
        } catch (Exception e) {
            logger.fatal("[obtenerUsuariosPorOficina][Exception]:" + e);
            e.printStackTrace();
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, e.getLocalizedMessage());
        }
        return response;
    }

   /***
    * 
    * @return
    * @throws DiservBusinessException 
    */
    @Override
    public List<Usuarios> loadAllUser() throws DiservBusinessException {
        logger.log(Level.INFO, "[loadAllUser] ");
        List<Usuarios> userList = null;
        Query query;
        try {
            query = em.createNamedQuery("Usuarios.findAll");
            userList = query.getResultList();
        } catch (NoResultException ex) {
            logger.log(Level.INFO, "[loadAllUser][NoResultException]No se encontraron usuarios");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "No se encontraron usuarios");
        } catch (Exception e) {
            e.printStackTrace();
            logger.log(Level.INFO, "[loadAllUser][Exception]Se mostro una excepcion al buscar usuarios");
            throw new DiservBusinessException(Constants.CODE_OPERATION_FALLIDA, "Excepcion desconocida:" + e.toString());
        }
        return userList;
    }
}
