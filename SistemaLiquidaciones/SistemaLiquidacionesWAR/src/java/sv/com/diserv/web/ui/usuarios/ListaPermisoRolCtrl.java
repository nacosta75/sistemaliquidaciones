/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE DISERV,SA DE CV  Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 * Fiscalía General de la República 2013
 */
package sv.com.diserv.liquidaciones.web.ui.usuarios;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import sv.com.diserv.web.ui.usuarios.renderer.PermisoItemRendered;
import sv.com.diserv.web.ui.usuarios.renderer.RolItemRendered;
import sv.com.diserv.web.ui.util.BaseController;
import sv.com.diserv.web.ui.util.MensajeMultilinea;
import sv.com.diserv.web.ui.util.UserWorkspace;
import sv.com.diserv.liquidaciones.ejb.ManejadorUsuarioBeanLocal;
import sv.com.diserv.liquidaciones.entity.Authorities;
import sv.com.diserv.liquidaciones.entity.GroupAuthorities;
import sv.com.diserv.liquidaciones.entity.Groups;
import sv.com.diserv.liquidaciones.exception.DiservWebException;
import sv.com.diserv.liquidaciones.exception.ServiceLocatorException;
import sv.com.diserv.liquidaciones.util.Constants;
import sv.com.diserv.liquidaciones.util.ServiceLocator;

/**
 *
 * @author programador
 */
public class ListaPermisoRolCtrl extends BaseController {

    static final Logger logger = Logger.getLogger(ListaUsuariosCtrl.class.getCanonicalName());
    private ManejadorUsuarioBeanLocal usuarioBean;
    private ServiceLocator serviceLocator;
    protected Window listaGruposDerechosWindow;
    protected Listbox listBoxGrupos;
    protected Listbox listBoxDerecho;
    protected Button btnSaveGruposDerechos;
    protected Listheader listheaderDescripcion;
    protected Listheader listheaderLongDescripcion;
//    protected Listheader listheaderVersionGrupo;
    protected Listheader listheaderSelect;
    protected Listheader listheaderRightName;
    protected Listheader listheaderRightDescription;
//    protected Listheader listheaderRightLongDescription;
    //basic operations
    private List<Authorities> listaPermiso;
    private List<Groups> listaRoles;
    protected Paging pagingRolList;
    protected Paging pagingRightList;
    private Integer totalRegistrosRoles;
    private Integer totalRegistrosPermiso;
    private Integer numeroPaginGrupo = 0;
    private Integer numeroPaginDerecho = 0;
    private Groups groupSelected;

    public ListaPermisoRolCtrl() {
        logger.log(Level.INFO, "[ListaGruposDerechosCtrl]INIT");
        try {
            serviceLocator = ServiceLocator.getInstance();
            usuarioBean = serviceLocator.getService(Constants.JNDI_USUARIOS_BEAN);
        } catch (ServiceLocatorException ex) {
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onCreate$listaGruposDerechosWindow(Event event) throws Exception {
        logger.log(Level.INFO, "[onCreate$listaGruposDerechosWindow]-->{0}", event.toString());
        doOnCreateCommon(this.listaGruposDerechosWindow);
        MensajeMultilinea.doSetTemplate();
        pagingRolList.setPageSize(Constants.REGISTROS_A_MOSTRAR_LISTA);
        pagingRolList.setDetailed(true);
        pagingRightList.setPageSize(Constants.REGISTROS_A_MOSTRAR_LISTA_CON_CHECKBOX);
        pagingRightList.setDetailed(true);
        totalRegistrosRoles = usuarioBean.countRoles();
        if (totalRegistrosRoles != null) {
            setTotalRegistrosRoles(totalRegistrosRoles);
        } else {
            logger.info("[onCreate$listaGruposDerechosWindow]No se pudo obtener el total de registros");
        }
        refreshModel(numeroPaginGrupo);
        //cargamos los permisos
        totalRegistrosPermiso = usuarioBean.countRights();
        if (totalRegistrosPermiso != null) {
            setTotalRegistrosPermiso(totalRegistrosPermiso);
        } else {
            logger.info("[onCreate$listaGruposDerechosWindow]No se pudo obtener el total de registros");
        }
        refreshModelDerechos(numeroPaginDerecho);
        setOrderByListHeader();
        //   checkPermisos();
    }

    private void refreshModel(int activePage) {
        logger.log(Level.INFO, "[refreshModel]Recargar lista de gupos, pagina activa:{0}", activePage);
        try {
            if (getTotalRegistrosRoles() > 0) {
                listaRoles = usuarioBean.loadAllRoles(activePage * Constants.REGISTROS_A_MOSTRAR_LISTA, Constants.REGISTROS_A_MOSTRAR_LISTA);
                if (listaRoles.size() > 0) {
                    logger.log(Level.INFO, "Registros cargados=={0}", listaRoles.size());
                    pagingRolList.setTotalSize(getTotalRegistrosRoles());
                    listBoxGrupos.setModel(new ListModelList(listaRoles));
                    listBoxGrupos.setItemRenderer(new RolItemRendered());
                } else {
                    logger.info("No se cargaron registros");
                }
            } else {
                listBoxGrupos.setEmptyMessage("No se encontraron registros para mostrar con el criterio seleccionado");
            }
        } catch (Exception ex) {
            logger.log(Level.INFO, "[refreshModel]Ocurrio una exception :{0}", ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    private void refreshModelDerechos(int activePage) {
        logger.log(Level.INFO, "[refreshModelDerechos]Recargar derechos, pagina activa:{0}", activePage);
        try {
            if (getTotalRegistrosPermiso() > 0) {
                listaPermiso = usuarioBean.loadAllRights(activePage * Constants.REGISTROS_A_MOSTRAR_LISTA_CON_CHECKBOX, Constants.REGISTROS_A_MOSTRAR_LISTA_CON_CHECKBOX);
                if (listaPermiso.size() > 0) {
                    logger.log(Level.INFO, "Registros cargados=={0}", listaPermiso.size());
                    pagingRightList.setTotalSize(getTotalRegistrosPermiso());
                    if (groupSelected != null) {
                        mostrarSeleccionados();
                    } else {
                        listBoxDerecho.setModel(new ListModelList(listaPermiso));
                        listBoxDerecho.setItemRenderer(new PermisoItemRendered());
                    }
                } else {
                    logger.info("No se cargaron registros");
                }
            } else {
                listBoxDerecho.setEmptyMessage("No se encontraron registros para mostrar con el criterio seleccionado");
            }
        } catch (Exception ex) {
            logger.log(Level.INFO, "[refreshModelDerechos]Ocurrio una exception :{0}", ex.getLocalizedMessage());
            ex.printStackTrace();
        }
    }

    public void onPaging$pagingGroupList(ForwardEvent event) {
        logger.log(Level.INFO, "[onPaging$pagingGroupList]Event:", event.getName());
        final PagingEvent pe = (PagingEvent) event.getOrigin();
        numeroPaginGrupo = pe.getActivePage();
        refreshModel(numeroPaginGrupo);
    }

    public void onPaging$pagingRightList(ForwardEvent event) {
        logger.log(Level.INFO, "[onPaging$pagingRightList]Event:", event.getName());
        final PagingEvent pe = (PagingEvent) event.getOrigin();
        numeroPaginDerecho = pe.getActivePage();
        refreshModelDerechos(numeroPaginDerecho);
    }

    private void setOrderByListHeader() {
        listheaderDescripcion.setSortAscending(new FieldComparator("description", true));
        listheaderDescripcion.setSortDescending(new FieldComparator("description", false));
        listheaderLongDescripcion.setSortAscending(new FieldComparator("longdescription", true));
        listheaderLongDescripcion.setSortDescending(new FieldComparator("longdescription", false));
        listheaderRightName.setSortAscending(new FieldComparator("name", true));
        listheaderRightName.setSortDescending(new FieldComparator("name", false));
        listheaderRightDescription.setSortAscending(new FieldComparator("description", true));
        listheaderRightDescription.setSortDescending(new FieldComparator("description", false));
    }

    public void onClick$listBoxGrupos(Event event) {
        logger.log(Level.INFO, "[onClick$listBoxGrupos]Evento={0}", event.toString());
        mostrarSeleccionados();
    }

    public void mostrarSeleccionados() {
        logger.log(Level.INFO, "[mostrarSeleccionados]Se seleccionan los derechos que corresponden al grupo seleccionado");
        List<GroupAuthorities> permisoRolList;
        Listitem item = this.listBoxGrupos.getSelectedItem();
        if (item != null) {
            groupSelected = (Groups) item.getAttribute("data");
            try {
                for (int i = 0; i < listaPermiso.size(); i++) {
//                    listaPermiso.get(i).setAuthoritiSelected(Boolean.FALSE);
                }
                permisoRolList = usuarioBean.findPermisoRolByUdRole(groupSelected.getId().intValue());
                if (permisoRolList != null && permisoRolList.size() > 0) {
                    for (int i = 0; i < permisoRolList.size(); i++) {
                        if (listaPermiso.contains(permisoRolList.get(i).getIdauthority())) {
//                            listaPermiso.get(listaPermiso.indexOf(permisoRolList.get(i).getIdAuthority())).setAuthoritiSelected(Boolean.TRUE);
                        }
                    }
                }
                listBoxDerecho.setModel(new ListModelList(listaPermiso));
                listBoxDerecho.setItemRenderer(new PermisoItemRendered());
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Excepcion al buscar derechos del grupo:{0}", groupSelected.getId());
                e.printStackTrace();
            }
        }
    }

    public void onClick$btnSaveGruposDerechos(Event event) {
        logger.log(Level.INFO, "[onClic$btnSaveGruposDerechos]Evento={0}", event.toString());
        try {
            if (getGroupSelected() != null) {
                doSave();
                MensajeMultilinea.show("Cambios almacenados correctamente!!", Constants.MENSAJE_TIPO_INFO);
            } else {
                logger.log(Level.INFO, "Intenta guardar y no se ha seleccionado grupo aun.");
                MensajeMultilinea.show("Seleccione un grupo!!", Constants.MENSAJE_TIPO_ALERTA);
            }
        } catch (DiservWebException ex) {
            MensajeMultilinea.show(ex.getMensaje(), Constants.MENSAJE_TIPO_ERROR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para guardar los cambios realizados sobre los grupos y sus
     * permisos (derechos)
     *
     * @throws InterruptedException
     */
    public void doSave() throws InterruptedException, DiservWebException {
        logger.log(Level.INFO, "[doSave]INIT");
        GroupAuthorities aGroupRight = null;
        List<Listitem> li = this.listBoxDerecho.getItems();
        for (Listitem listitem : li) {
            Listcell lc = (Listcell) listitem.getFirstChild();
            Checkbox cb = (Checkbox) lc.getFirstChild();
            if (cb != null) {
                Authorities aRight = (Authorities) listitem.getAttribute("data");
                Groups aGroup = getGroupSelected();
                try {
                    aGroupRight = usuarioBean.getGroupRightByGroupAndRight(aGroup.getId().intValue(), aRight.getIdauthority());
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Ocurrio un error al buscar  permiso-grupo," + e.getMessage());
                }
                if (cb.isChecked()) {
                    logger.log(Level.INFO, "Grupo:{0}, Derecho:{1}:{2}", new Object[]{aGroup.getGroupname(), aRight.getNombre(), aRight.getDescripcion()});
                    if (aGroupRight == null) {
                        aGroupRight = new GroupAuthorities();
                        aGroupRight.setGroupid(aGroup);
                        aGroupRight.setIdauthority(aRight);

                        try {
                            usuarioBean.saveGroupRight(aGroupRight);
                        } catch (Exception e) {
                            logger.log(Level.SEVERE, "Excepcion al guardar GroupRight: {0}", e.toString());
                            throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Ocurrio un error al buscar  permiso-grupo," + e.getMessage());
                        }
                    }
                } else if (!cb.isChecked()) {
                    logger.log(Level.INFO, "Grupo:{0}, Derecho no seleccionado:{1}:{2}", new Object[]{aGroup.getGroupname(), aRight.getNombre(), aRight.getDescripcion()});
                    if (aGroupRight != null) {
                        try {
                            usuarioBean.deleteGroupRight(aGroupRight);
                        } catch (Exception e) {
                            logger.log(Level.SEVERE, "Excepcion al eliminar GroupRight: {0}", e.toString());
                            throw new DiservWebException(Constants.CODE_OPERATION_FALLIDA, "Ocurrio un error al buscar  permiso-grupo," + e.getMessage());

                        }
                    }
                }
            }
        }
    }

    private void checkPermisos() {
        final UserWorkspace workspace = getUserWorkspace();
        btnSaveGruposDerechos.setVisible(workspace.isAllowed("btnSaveGruposDerechos"));
        listBoxGrupos.setVisible(workspace.isAllowed("listBoxGrupos"));
        listheaderDescripcion.setVisible(workspace.isAllowed("listheaderDescripcion"));
        listheaderLongDescripcion.setVisible(workspace.isAllowed("listheaderLongDescripcion"));
//        listheaderVersionGrupo.setVisible(workspace.isAllowed("listheaderVersionGrupo"));
        listBoxDerecho.setVisible(workspace.isAllowed("listBoxDerecho"));
        listheaderSelect.setVisible(workspace.isAllowed("listheaderSelect"));
        listheaderRightName.setVisible(workspace.isAllowed("listheaderRightName"));
        listheaderRightDescription.setVisible(workspace.isAllowed("listheaderRightDescription"));
//        listheaderRightLongDescription.setVisible(workspace.isAllowed("listheaderRightLongDescription"));
    }

    public Integer getTotalRegistrosRoles() {
        return totalRegistrosRoles;
    }

    public void setTotalRegistrosRoles(Integer totalRegistrosRoles) {
        this.totalRegistrosRoles = totalRegistrosRoles;
    }

    public Integer getTotalRegistrosPermiso() {
        return totalRegistrosPermiso;
    }

    public void setTotalRegistrosPermiso(Integer totalRegistrosPermiso) {
        this.totalRegistrosPermiso = totalRegistrosPermiso;
    }

    public int getNumeroPaginGrupo() {
        return numeroPaginGrupo;
    }

    public void setNumeroPaginGrupo(int numeroPaginGrupo) {
        this.numeroPaginGrupo = numeroPaginGrupo;
    }

    public int getNumeroPaginDerecho() {
        return numeroPaginDerecho;
    }

    public void setNumeroPaginDerecho(int numeroPaginDerecho) {
        this.numeroPaginDerecho = numeroPaginDerecho;
    }

    public List<Authorities> getListaPermiso() {
        return listaPermiso;
    }

    public void setListaPermiso(List<Authorities> listaPermiso) {
        this.listaPermiso = listaPermiso;
    }

    public List<Groups> getListaRoles() {
        return listaRoles;
    }

    public void setListaRoles(List<Groups> listaRoles) {
        this.listaRoles = listaRoles;
    }

    public void setNumeroPaginGrupo(Integer numeroPaginGrupo) {
        this.numeroPaginGrupo = numeroPaginGrupo;
    }

    public void setNumeroPaginDerecho(Integer numeroPaginDerecho) {
        this.numeroPaginDerecho = numeroPaginDerecho;
    }

    public Groups getGroupSelected() {
        return groupSelected;
    }

    public void setGroupSelected(Groups groupSelected) {
        this.groupSelected = groupSelected;
    }
}
