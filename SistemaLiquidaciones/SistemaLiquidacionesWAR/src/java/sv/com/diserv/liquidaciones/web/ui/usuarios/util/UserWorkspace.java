/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE DISERV,SA DE CV  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * @author alvarenga.miranda@gmail.com
 */
package sv.com.diserv.liquidaciones.web.ui.usuarios.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Executions;

/**
 * Workspace for the user. One workspace per userSession. <br>
 * <br>
 * Every logged in user have his own workspace. <br>
 * Here are stored several properties for the user. <br>
 * <br>
 * 1. Access the rights that the user have. <br>
 * 2. The office for that the user are logged in. <br>
 *
 */
public class UserWorkspace implements Serializable, DisposableBean {

    private static final long serialVersionUID = -3936210543827830197L;
    private final static Logger logger = Logger.getLogger(UserWorkspace.class);
    private String userLanguage;
    private String browserType;
    /**
     * difference in the height between TreeMenu and BarMenu.
     */
    private final int menuOffset = 32;
    private Set<String> grantedAuthoritySet = null;
    /**
     * Indicates that as mainMenu the TreeMenu is used, otherwise BarMenu. true
     * = init.
     */
    private boolean treeMenu = true;

    static private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * carga todas las varibles y permisos necesarios recuperados para al
     * usuario en session al momento de h pasar por login
     *
     * @return the users WorkSpace
     * @deprecated Sollte gegen Spring getauscht werden also Konfiguriert und
     * nicht über diese Methode!
     */
    @Deprecated
    public static UserWorkspace getInstance() {
        return (UserWorkspace) SpringUtil.getBean("userWorkspace", UserWorkspace.class);
    }

    /**
     * Default Constructor
     */
    public UserWorkspace() {
        if (logger.isDebugEnabled()) {
            logger.debug("create new UserWorkspace [" + this + "]");
        }
        // speed up the ModalDialogs while disabling the animation
        // Window.setDefaultActionOnShow("");
    }

    /**
     * Logout para desonectar e invalidar la session de usuario y los objetos
     * que se cargaron con este en el inicio de session.<br>
     * utilizando el metodo de spring securitysendRedirect()
     * <br>
     */
    public void doLogout() {
        destroy();
        Executions.sendRedirect("/j_spring_security_logout");

    }

    /**
     * copia la lista de grantedAuthorities a un Set de tipo strings <br>
     * para hacer mas rapida la busqueda ya que se carga solo una vez al inicio
     * de login
     *
     * @return String set of GrantedAuthorities (rightNames)
     */
    private Set<String> getGrantedAuthoritySet() {
        if (this.grantedAuthoritySet == null) {
            final Collection<GrantedAuthority> list = getAuthentication().getAuthorities();
            this.grantedAuthoritySet = new HashSet<String>(list.size());
            for (final GrantedAuthority grantedAuthority : list) {
                this.grantedAuthoritySet.add(grantedAuthority.getAuthority());
            }
        }
        return this.grantedAuthoritySet;
    }

    /**
     * velida si el permiso esta en la lista de<b>granted rights</b> que se
     * cargan cuando el usuario pasa por login, donde se guardan todos en el
     * context de spring
     * <br>
     *
     * @author edwin.alvarenga
     * @param rightName
     * @return true, si el permoso esta otorgado dentro de los permisos de el
     * usuario en session y false en caso contrario<br>
     * <br>
     */
    public boolean isAllowed(String rightName) {
        return getGrantedAuthoritySet().contains(rightName);
    }

    @Override
    public void destroy() {
        this.grantedAuthoritySet = null;
        SecurityContextHolder.clearContext();
        if (logger.isDebugEnabled()) {
            logger.debug("destroy Workspace [" + this + "]");
        }
    }

    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    // ++++++++++++++++ Setter/Getter ++++++++++++++++++ //
    // +++++++++++++++++++++++++++++++++++++++++++++++++ //
    public void setUserLanguage(String userLanguage) {
        this.userLanguage = userLanguage;
    }

    public String getUserLanguage() {
        return this.userLanguage;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public String getBrowserType() {
        return this.browserType;
    }

    public int getMenuOffset() {
        int result = 0;
        if (isTreeMenu()) {
            result = 0;
        } else {
            result = menuOffset;
        }
        return result;
    }

    public void setTreeMenu(boolean treeMenu) {
        this.treeMenu = treeMenu;
    }

    public boolean isTreeMenu() {
        return treeMenu;
    }
}
