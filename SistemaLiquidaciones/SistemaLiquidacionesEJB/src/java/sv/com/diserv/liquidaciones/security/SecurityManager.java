/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE DISERV,SA DE CV  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author alvarenga.miranda@gmail.com
 */
package sv.com.diserv.liquidaciones.security;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sv.com.diserv.liquidaciones.dto.CustomUserDTO;
import sv.com.diserv.liquidaciones.ejb.ManejadorUsuarioBeanLocal;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;

/**
 *
 *alvarenga.miranda@gmail.com
 */
public class SecurityManager implements UserDetailsService {

    static final Logger logger = Logger.getLogger(SecurityManager.class.getName());
    private ManejadorUsuarioBeanLocal usuarioBean;
    private ServiceLocator serviceLocator;

    public SecurityManager() {
        logger.log(Level.INFO, "[SecurityManager]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            usuarioBean = serviceLocator.getService(Constants.JNDI_USUARIOS_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.log(Level.ERROR, "[ServiceLocatorException][SecurityManager]", ex);
            ex.printStackTrace();
        }
    }

    /**
     *
     * @param userName
     * @return
     * @throws UsernameNotFoundException
     * @throws DataAccessException
     */
    @Override
    public CustomUserDTO loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
        logger.log(Level.INFO, "[loadUserByUsername]Nombre usuario=" + userName);
        CustomUserDTO user = new CustomUserDTO();
        try {
            user = usuarioBean.findByName(userName);
            user.setIpConnect(getIpRequest().toString().trim());
            if (user == null) {
                throw new UsernameNotFoundException("user not found in database");
            }
            user.setIpConnect(getIpRequest());
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException("user not found in database Exception:" + e.getMessage());
        }
        return user;
    }

    private static String getIpRequest() {
        String remoteAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();
        if (remoteAddress != null) {
            System.err.println("remote address=" + remoteAddress);
        } else {
            System.err.println("No se pudo cargar remote address");
        }
        return remoteAddress;
    }
}
