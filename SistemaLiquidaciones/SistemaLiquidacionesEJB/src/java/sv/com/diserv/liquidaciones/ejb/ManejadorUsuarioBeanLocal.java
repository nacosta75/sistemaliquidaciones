/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.Local;
import org.springframework.security.authentication.BadCredentialsException;
import sv.com.diserv.liquidaciones.dto.BusquedaUserDTO;
import sv.com.diserv.liquidaciones.dto.CustomUserDTO;
import sv.com.diserv.liquidaciones.dto.GenericResponse;
import sv.com.diserv.liquidaciones.entity.Authorities;
import sv.com.diserv.liquidaciones.entity.GroupAuthorities;
import sv.com.diserv.liquidaciones.entity.GroupMembers;
import sv.com.diserv.liquidaciones.entity.Groups;
import sv.com.diserv.liquidaciones.entity.Usuarios;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

/**
 *
 * @author edwin.alvarenga
 */
@Local
public interface ManejadorUsuarioBeanLocal {

    /**
     * Metodo para autenticar usuario con spring security, devuelve usuario
     * autenticado con todos los permisos asignados
     *
     * @param name (nombre usuario a autenticar)
     * @return CustomUserDTO
     * @throws BadCredentialsException
     */
    public CustomUserDTO findByName(String name) throws BadCredentialsException;

    /**
     * Metodo para obtener el numero de usuarios
     *
     * @return Cantidad de usuarios encontrados
     * @throws sv.com.diserv.liquidaciones.exception.DiservBusinessException
     */
    public int countUsers() throws DiservBusinessException;

    /**
     * Metodo para extraer todos los usuarios
     *
     * @param inicio: Primer registro a mostrar
     * @param fin: Ultimo registro a mostrar
     * @return List<Users>: Lista de usuarios desde la base de datos
     * @throws sv.com.diserv.liquidaciones.exception.DiservBusinessException
     */
    public List<Usuarios> loadAllUser(Integer inicio, Integer fin) throws DiservBusinessException;

    /**
     *
     * @return @throws
     * sv.com.diserv.liquidaciones.exception.DiservBusinessException
     */
    public List<Usuarios> loadAllUser() throws DiservBusinessException;

    /**
     * Metodo para obtener el numero de roles
     *
     * @return Cantidad de roles encontrados
     * @throws sv.com.diserv.liquidaciones.exception.DiservBusinessException
     */
    public int countRoles() throws DiservBusinessException;

    /**
     * Metodo para extraer todos los roles desde la DB
     *
     * @param inicio: Primer registro a mostrar
     * @param fin: Ultimo registro a mostrar
     * @return List<Roles>: Lista de roles desde la base de datos
     * @throws sv.com.diserv.liquidaciones.exception.DiservBusinessException
     */
    public List<Groups> loadAllRoles(Integer inicio, Integer fin) throws DiservBusinessException;

    /**
     *
     * @param nombreUsuario
     * @return
     * @throws sv.com.diserv.liquidaciones.exception.DiservBusinessException
     */
    public List<GroupMembers> findUserRoleByNumeroCarnet(String nombreUsuario) throws DiservBusinessException;

    /**
     * Metodo para extraer una combinacion RolUsuario partiendo del id de
     * usuario y de rol
     *
     * @param nombreUsuario
     * @param rolId: Id del rol a buscar
     * @return UserRole
     * @throws sv.com.diserv.liquidaciones.exception.DiservBusinessException
     */
    public GroupMembers getUserRoleByUserAndRole(String nombreUsuario, Integer rolId) throws DiservBusinessException;

    /**
     * Metodo para guardar una relacion Usuario-Rol
     *
     * @param data: Objeto de tipo UserRole a guardar
     * @return GenericResponse
     * @throws sv.com.diserv.liquidaciones.exception.DiservBusinessException
     */
    public GenericResponse saveUserRole(GroupMembers data) throws DiservBusinessException;

    /**
     * Metodo para borrar una relacion Usuario-Rol
     *
     * @param data: Objeto de tipo UserRole a borrar
     * @return GenericResponse
     * @throws sv.com.diserv.liquidaciones.exception.DiservBusinessException
     */
    public GenericResponse deleteUserRole(GroupMembers data) throws DiservBusinessException;

    /**
     * Metodo para obtener el numero de grupos
     *
     * @return Cantidad de grupos encontrados
     * @throws DesempenoBusinessException
     */
//    public int countGroups() throws EnlaceBusinessException;
    /**
     * Metodo para obtener el numero de Derechos
     *
     * @return Cantidad de derechos encontrados
     * @throws sv.com.diserv.liquidaciones.exception.DiservBusinessException
     */
    public Integer countRights() throws DiservBusinessException;

    /**
     * Metodo para extraer todos los derechos
     *
     * @param inicio: Primer registro a mostrar
     * @param cantReg: Cantidad de registros a mostrar
     * @return List<Rights>: Lista de derechos desde la base de datos
     * @throws sv.com.diserv.liquidaciones.exception.DiservBusinessException
     */
    public List<Authorities> loadAllRights(Integer inicio, Integer cantReg) throws DiservBusinessException;

    /**
     * Metodo para obtener el listado de usuarios pertenecientes a una misma
     * oficina
     *
     * @param codigoOficina: codigo de la oficina a buscar usuarios
     * @return List<User>
     * @throws sv.com.diserv.liquidaciones.exception.DiservBusinessException
     */
    public List<Usuarios> obtenerUsuariosPorOficina(Integer codigoOficina) throws DiservBusinessException;

    /**
     *
     * @param request
     * @return
     * @throws sv.com.diserv.liquidaciones.exception.DiservBusinessException
     */
    public List<Usuarios> buscarUsuariosPorCriterios(BusquedaUserDTO request) throws DiservBusinessException;
// 

    /**
     *
     * @param idRol
     * @return
     * @throws sv.com.diserv.liquidaciones.exception.DiservBusinessException
     */
    public List<GroupAuthorities> findPermisoRolByUdRole(Integer idRol) throws DiservBusinessException;

    /**
     *
     * @return
     */
    public List<Usuarios> loadAllUserSinFiltros() throws DiservBusinessException;

    /**
     *
     * @param groupId
     * @param rightId
     * @return
     * @throws sv.com.diserv.liquidaciones.exception.DiservBusinessException
     */
    public GroupAuthorities getGroupRightByGroupAndRight(int groupId, int rightId) throws DiservBusinessException;

    /**
     *
     * @param data
     * @return
     * @throws sv.com.diserv.liquidaciones.exception.DiservBusinessException
     */
    public GenericResponse saveGroupRight(GroupAuthorities data) throws DiservBusinessException;

    /**
     *
     * @param data
     * @return
     * @throws sv.com.diserv.liquidaciones.exception.DiservBusinessException
     */
    public GenericResponse deleteGroupRight(GroupAuthorities data) throws DiservBusinessException;

}
