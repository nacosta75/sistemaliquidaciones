package sv.com.diserv.web.ui.usuarios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.FieldComparator;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;
import sv.com.diserv.web.ui.usuarios.renderer.RolItemRendered;
import sv.com.diserv.web.ui.usuarios.renderer.UsuarioItemRendered;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.ManejadorMensajes;
import sv.com.diserv.web.ui.util.MensajeMultilinea;
import sv.com.diserv.web.ui.util.UserWorkspace;
import sv.com.diserv.liquidaciones.ejb.ManejadorUsuarioBeanLocal;
import sv.com.diserv.liquidaciones.entity.Groupmembers;
import sv.com.diserv.liquidaciones.entity.Groups;
import sv.com.diserv.liquidaciones.entity.Usuarios;
import sv.com.diserv.liquidaciones.exception.DiservWebException;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;
import sv.com.diserv.liquidaciones.util.UtilFormat;

public class ListaUsuariosCtrl extends BaseController {

    static final Logger logger = Logger.getLogger(ListaUsuariosCtrl.class.getCanonicalName());
    protected Window listaUsuarioWindow;
    protected Listbox listBoxUsuario;
    protected Listbox listBoxRol;
    protected Button btnABuscarUsuarios;
    protected Button btnSaveUsuarios;
    protected Button btnNuevoUsuario;
    protected Listheader listheaderCarnet;
    protected Listheader listheaderUsuario;
    protected Listheader listheaderNombreUsuario;
    protected Listheader listheaderSelect;
    protected Listheader listheaderEstado;
    protected Listheader listheaderShortDescription;
    private Usuarios usuarioSelected;
    protected List<Usuarios> listaUsuarios;
    protected List<Groups> listaRoles;
    protected Paging pagingUserList;
    protected Paging pagingRolList;
    private Integer totalRegistros;
    private Integer totalRegistrosRol;
    private int numeroPaginInicio = 0;
    private int numeroPaginInicioRol = 0;
    private ManejadorUsuarioBeanLocal usuariosBean;
    private ServiceLocator serviceLocator;

    public ListaUsuariosCtrl() {
        logger.log(Level.INFO, "[ListaUsuariosCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            usuariosBean = serviceLocator.getService(Constants.JNDI_USUARIOS_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$listaUsuarioWindow(Event event) throws Exception {
        logger.log(Level.INFO, "[onCreate$listaUsuarioWindow]-->{0}", event.toString());
        doOnCreateCommon(this.listaUsuarioWindow);
        MensajeMultilinea.doSetTemplate();
        pagingUserList.setPageSize(Constants.REGISTROS_A_MOSTRAR_LISTA);
        pagingUserList.setDetailed(true);
        pagingRolList.setPageSize(Constants.REGISTROS_A_MOSTRAR_LISTA_CON_CHECKBOX);
        pagingRolList.setDetailed(true);
        refresTotalRegistros();
        refreshModel(numeroPaginInicio);
        totalRegistrosRol = usuariosBean.countRoles();
        if (totalRegistrosRol != null) {
            setTotalRegistrosRol(totalRegistrosRol);
        } else {
            logger.info("[onCreate$listaUsuarioWindow]No se pudo obtener total registros");
        }
        refreshModelRoles(numeroPaginInicioRol);
        setOrderByListHeader();
        //checkPermisos();
    }

    public void refresTotalRegistros() {
        try {
            totalRegistros = usuariosBean.countUsers();
            if (totalRegistros != null) {
                setTotalRegistros(totalRegistros);
            } else {
                logger.info("[onCreate$listaUsuarioWindow]No se pudo obtener total registros");
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public void refreshModel(int activePage) {
        logger.log(Level.INFO, "[refreshModel]Recargar usuarios,Pagina activa:{0}", activePage);
        try {
            if (totalRegistros > 0) {
                listaUsuarios = usuariosBean.loadAllUser(activePage * Constants.REGISTROS_A_MOSTRAR_LISTA, Constants.REGISTROS_A_MOSTRAR_LISTA);
                if (listaUsuarios.size() > 0) {
                    logger.log(Level.INFO, "Registros cargados=={0}", listaUsuarios.size());
                    pagingUserList.setTotalSize(getTotalRegistros());
                    listBoxUsuario.setModel(new ListModelList(listaUsuarios));
                    listBoxUsuario.setItemRenderer(new UsuarioItemRendered());
                } else {
                    logger.info("No se cargaron registros");
                }
            } else {
                listBoxUsuario.setEmptyMessage("No se encontraron registros para mostrar con el criterio seleccionado");
            }
        } catch (Exception ex) {
            logger.log(Level.INFO, "[refreshModel]Ocurrio Una exception :{0}", ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    private void refreshModelRoles(int activePage) {
        logger.log(Level.INFO, "[refreshModelRoles]Recargar roles, Pagina activa:{0}", activePage);
        try {
            if (totalRegistrosRol > 0) {
                listaRoles = usuariosBean.loadAllRoles(activePage * Constants.REGISTROS_A_MOSTRAR_LISTA_CON_CHECKBOX, Constants.REGISTROS_A_MOSTRAR_LISTA_CON_CHECKBOX);
                if (listaRoles.size() > 0) {
                    logger.log(Level.INFO, "Registros cargados=={0}", listaRoles.size());
                    pagingRolList.setTotalSize(getTotalRegistrosRol());
                    if (usuarioSelected != null) {
                        mostrarSeleccionados();
                    } else {
                        listBoxRol.setModel(new ListModelList(listaRoles));
                        listBoxRol.setItemRenderer(new RolItemRendered());
                    }
                } else {
                    logger.info("No se cargaron registros");
                }
            } else {
                listBoxRol.setEmptyMessage("No se encontraron registros para mostrar con el criterio seleccionado");
            }
        } catch (Exception ex) {
            logger.log(Level.INFO, "[refreshModelRoles]Ocurrio una exception :{0}", ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onDoubleClickedUsuario(Event event) throws Exception {
        logger.log(Level.INFO, "[onDoubleClickedUsuario]Event:{0}", event.toString());
        Listitem item = this.listBoxUsuario.getSelectedItem();
        if (item != null) {
            usuarioSelected = (Usuarios) item.getAttribute("data");
            HashMap map = new HashMap();
            map.put("usuarioSelected", usuarioSelected);
            map.put("token", UtilFormat.getToken());
            map.put("listaUsuariosCtrl", this);
            Executions.createComponents("/WEB-INF/xhtml/usuario/detalleUsuario.zul", null, map);
        }
    }

    public void onClick$btnNuevoUsuario(Event event) {
        logger.log(Level.INFO, "[onClick$btnNuevoUsuario]Event:{0}", event.toString());
        HashMap map = new HashMap();
        map.put("token", UtilFormat.getToken());
        map.put("listaUsuariosCtrl", this);
        Executions.createComponents("/WEB-INF/xhtml/usuario/detalleUsuario.zul", null, map);
    }

    public void onPaging$pagingUserList(ForwardEvent event) {
        logger.log(Level.INFO, "[onPaging$pagingUserList]Event:", event.getName());
        final PagingEvent pe = (PagingEvent) event.getOrigin();
        numeroPaginInicio = pe.getActivePage();
        refreshModel(numeroPaginInicio);
    }

    public void onPaging$pagingRolList(ForwardEvent event) {
        logger.log(Level.INFO, "[onPaging$pagingRolList]Event:", event.getName());
        final PagingEvent pe = (PagingEvent) event.getOrigin();
        numeroPaginInicioRol = pe.getActivePage();
        refreshModelRoles(numeroPaginInicioRol);
    }

    private void setOrderByListHeader() {
        listheaderCarnet.setSortAscending(new FieldComparator("codigoEmpleado", true));
        listheaderCarnet.setSortDescending(new FieldComparator("codigoEmpleado", false));
        listheaderUsuario.setSortAscending(new FieldComparator("nombreUsuario", true));
        listheaderUsuario.setSortDescending(new FieldComparator("nombreUsuario", false));
        listheaderNombreUsuario.setSortAscending(new FieldComparator("nombreCompleto", true));
        listheaderNombreUsuario.setSortDescending(new FieldComparator("nombreCompleto", false));
        listheaderEstado.setSortAscending(new FieldComparator("status", true));
        listheaderEstado.setSortDescending(new FieldComparator("status|", false));
    }

    public void onClick$listBoxUsuario(Event event) {
        logger.log(Level.INFO, "[onClick$listBoxUsuario]Evento={0}", event.toString());
        mostrarSeleccionados();
    }

    public void mostrarSeleccionados() {
        logger.log(Level.INFO, "[mostrarSeleccionados]Se seleccionan los roles que corresponden al usuario seleccionado");
        List<Groupmembers> userRoleList = new ArrayList<Groupmembers>();
        Listitem item = this.listBoxUsuario.getSelectedItem();
        if (item != null) {
            usuarioSelected = (Usuarios) item.getAttribute("data");
            try {
                for (int i = 0; i < listaRoles.size(); i++) {
//                    listaRoles.get(i).setGroupSelected(Boolean.FALSE);
                }
                userRoleList = usuariosBean.findUserRoleByNumeroCarnet(usuarioSelected.getNombreusuario());
                if (userRoleList != null && userRoleList.size() > 0) {
                    for (int i = 0; i < userRoleList.size(); i++) {
                        if (listaRoles.contains(userRoleList.get(i).getGroupid())) { //new Roles(userRoleList.get(i).getRoleId().getRoleId())
//                            listaRoles.get(listaRoles.indexOf(userRoleList.get(i).getGroupid())).setGroupSelected(Boolean.TRUE);
                        }
                    }
                }
                listBoxRol.setModel(new ListModelList(listaRoles));
                listBoxRol.setItemRenderer(new RolItemRendered());
                ManejadorMensajes.mostraMensajeOperacion("Mostra Información user selected");
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Excepcion al buscar roles del usuario:{0}", usuarioSelected.getCodigoempleado());
                //e.printStackTrace();
            }
        }
    }

    public void onClick$btnABuscarUsuarios(Event event) {
        logger.log(Level.INFO, "[onClick$btnABuscarUsuarios]:{0}", event.toString());
        try {
            HashMap map = new HashMap();
            map.put("listaUsuariosCtrl", this);
            Executions.createComponents("/WEB-INF/xhtml/usuario/buscarUsuario.zul", null, map);
        } catch (Exception e) {
            MensajeMultilinea.show("Ocurrio una excepcion al crear componene", Constants.MENSAJE_TIPO_ERROR);
            e.printStackTrace();
        }
    }

    public void onClick$btnSaveUsuarios(Event event) {
        logger.log(Level.INFO, "[onClic$btnSaveUsuarios]Evento={0}", event.toString());
        try {
            if (getUsuarioSelected() != null) {
                doSave();
                MensajeMultilinea.show("Cambios almacenados correctamente!!", Constants.MENSAJE_TIPO_INFO);
            } else {
                logger.log(Level.INFO, "Intenta guardar y no se ha seleccionado usuario aun.");
                MensajeMultilinea.show("Seleccione un usuario!!", Constants.MENSAJE_TIPO_ALERTA);
            }

        } catch (Exception e) {
            e.printStackTrace();
            MensajeMultilinea.show(e.getMessage(), Constants.MENSAJE_TIPO_ERROR);
        }
    }

    public void doSave() throws DiservWebException {
        logger.log(Level.INFO, "[doSave]INIT");
        Groupmembers anUserRole = null;
        List<Listitem> li = this.listBoxRol.getItems();
        for (Listitem listitem : li) {
            Listcell lc = (Listcell) listitem.getFirstChild();
            Checkbox cb = (Checkbox) lc.getFirstChild();
            if (cb != null) {
                Groups aRole = (Groups) listitem.getAttribute("data");
                Usuarios anUser = getUsuarioSelected();
                try {
                    anUserRole = usuariosBean.getUserRoleByUserAndRole(anUser.getNombreusuario(), aRole.getId().intValue());
                } catch (Exception e) {
                    logger.log(Level.INFO, "Retorno null, no se encontraron registros");
                    throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Ocurrio un error al buscar  usuario-role," + e.getMessage());
                }
                if (cb.isChecked() == true) {
                    logger.log(Level.INFO, "usuario:{0}, Rol:{1}:{2}", new Object[]{anUser.getNombreusuario(), aRole.getId(), aRole.getGroupname()});
                    if (anUserRole == null) {
                        anUserRole = new Groupmembers();
                        anUserRole.setIdusuario(anUser);
                        anUserRole.setGroupid(aRole);
                        try {
                            usuariosBean.saveUserRole(anUserRole);
                            ManejadorMensajes.mostraMensajeOperacion("Guardar usuario-role");
                        } catch (Exception e) {
                            logger.log(Level.SEVERE, "Excepcion al guardar UserRole: {0}", e.toString());
                            throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Ocurrio un error al guardar  usuario-role," + e.getMessage());
                        }
                    }
                } else if (cb.isChecked() == false) {
                    logger.log(Level.INFO, "usuario:{0}, Rol no seleccionado:{1}:{2}", new Object[]{anUser.getNombreusuario(), aRole.getId(), aRole.getGroupname()});
                    if (anUserRole != null) {
                        try {
                            usuariosBean.deleteUserRole(anUserRole);
                            ManejadorMensajes.mostraMensajeOperacion("Eliminando UserRole");
                        } catch (Exception e) {
                            logger.log(Level.SEVERE, "Excepcion al eliminar UserRole: {0}", e.toString());
                            throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Ocurrio un error al borrar  usuario-role," + e.getMessage());

                        }
                    }
                }
            }
        }
    }

    private void checkPermisos() {
        final UserWorkspace workspace = getUserWorkspace();
        btnSaveUsuarios.setVisible(workspace.isAllowed("btnSaveUsuarios"));
        btnABuscarUsuarios.setVisible(workspace.isAllowed("btnABuscarUsuarios"));
        listBoxUsuario.setVisible(workspace.isAllowed("listBoxUsuario"));
        listheaderCarnet.setVisible(workspace.isAllowed("listheaderCarnet"));
        listheaderUsuario.setVisible(workspace.isAllowed("listheaderUsuario"));
        listheaderNombreUsuario.setVisible(workspace.isAllowed("listheaderNombreUsuario"));
        listBoxRol.setVisible(workspace.isAllowed("listBoxRol"));
        listheaderSelect.setVisible(workspace.isAllowed("listheaderSelect"));
        listheaderShortDescription.setVisible(workspace.isAllowed("listheaderShortDescription"));

    }

    public Usuarios getUsuarioSelected() {
        return usuarioSelected;
    }

    public void setUsuarioSelected(Usuarios usuarioSelected) {
        this.usuarioSelected = usuarioSelected;
    }

    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public int getNumeroPaginInicio() {
        return numeroPaginInicio;
    }

    public void setNumeroPaginInicio(int numeroPaginInicio) {
        this.numeroPaginInicio = numeroPaginInicio;
    }

    public List<Usuarios> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuarios> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public List<Groups> getListaRoles() {
        return listaRoles;
    }

    public void setListaRoles(List<Groups> listaRoles) {
        this.listaRoles = listaRoles;
    }

    public Integer getTotalRegistrosRol() {
        return totalRegistrosRol;
    }

    public void setTotalRegistrosRol(Integer totalRegistrosRol) {
        this.totalRegistrosRol = totalRegistrosRol;
    }

    public int getNumeroPaginInicioRol() {
        return numeroPaginInicioRol;
    }

    public void setNumeroPaginInicioRol(int numeroPaginInicioRol) {
        this.numeroPaginInicioRol = numeroPaginInicioRol;
    }

    public Listbox getListBoxUsuario() {
        return listBoxUsuario;
    }

    public void setListBoxUsuario(Listbox listBoxUsuario) {
        this.listBoxUsuario = listBoxUsuario;
    }
}
