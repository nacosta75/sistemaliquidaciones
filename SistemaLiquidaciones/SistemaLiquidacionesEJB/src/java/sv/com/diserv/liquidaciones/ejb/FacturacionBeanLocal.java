/*
 * ESTE COMPONENTE FUE REALIZADO BAJO LA METODOLOGÍA DE DESARROLLO
 * DE MINISTERIO DE HACIENDA EL SALVADDOR Y SE ENCUENTRA PROTEGIDO
 * POR LAS LEYES DE DERECHOS DE AUTOR.
 *  @author edwin.alvarenga@mh.gob.sv
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author edwin.alvarenga
 */
@Local
public interface FacturacionBeanLocal { 
    /**
     *
     * @return @throws AtsBusinessException
     */
    public List<GastosporCliente> loadGastosPorCliente() throws AtsBusinessException;

    /**
     * *
     *
     * @param gasto
     * @return
     * @throws AtsBusinessException
     */
    public OperacionesGastoClienteDTO actualizarGastoCliente(GastosporCliente gasto) throws AtsBusinessException;

    /**
     *
     * @param gasto
     * @return
     * @throws AtsBusinessException
     */
    public OperacionesGastoClienteDTO guardarGastoCliente(GastosporCliente gasto) throws AtsBusinessException;

    /**
     * *
     *
     * @param servicio
     * @return
     * @throws AtsBusinessException
     */
    public OperacionServicioClienteDTO guardarServicioCliente(ServiciosPrestados servicio) throws AtsBusinessException;

    /**
     *
     * @param servicio
     * @return
     * @throws AtsBusinessException
     */
    public OperacionServicioClienteDTO actualizarServicioCliente(ServiciosPrestados servicio) throws AtsBusinessException;

    /**
     * *
     *
     * @return @throws AtsBusinessException
     */
    public List<ServiciosPrestados> loadAllServiciosCliente() throws AtsBusinessException;

    /**
     *
     * @param re
     * @param paginaInicio
     * @param paginaFinal
     * @return
     * @throws AtsBusinessException
     */
    public List<Documentocliente> findDocumentoClienteByRequest(BusquedaFacturaDTO re, Integer paginaInicio, Integer paginaFinal) throws AtsBusinessException;

    /**
     *
     * @param re
     * @return
     * @throws AtsBusinessException
     */
    public Integer countFindDocumentosClienteByRequest(BusquedaFacturaDTO re) throws AtsBusinessException;

    /**
     *
     * @param idfactura
     * @param numeroSerie
     * @return
     * @throws AtsBusinessException
     */
    public List<DetalleDocumentoCliente> findDetalleDocumentoByRequest(Integer idfactura, String numeroSerie) throws AtsBusinessException;

    /**
     *
     * @return @throws AtsBusinessException
     */
    public Configuracion loadConfiguracionSistema() throws AtsBusinessException;

    /**
     *
     * @param encabezado
     * @param isNew
     * @return
     * @throws AtsBusinessBusinessRolledbackException
     */
    public OperacionesDocumentoClienteDTO guardarDocumetoCliente(Documentocliente encabezado, Boolean isNew) throws AtsBusinessBusinessRolledbackException;

    /**
     * 
     * @param detalle
     * @param encabezado
     * @param isnew
     * @return
     * @throws AtsBusinessBusinessRolledbackException 
     */
    public OperacionesDocumentoClienteDTO guardarDetalleDocumentoCliente(DetalleDocumentoCliente detalle, Documentocliente encabezado, Boolean isnew) throws AtsBusinessBusinessRolledbackException;

    /**
     * *
     *
     * @param encabezado
     * @return
     */
    public ConsultaFacturaDTO cargarInformacionFactura(Documentocliente encabezado);

    /**
     * *
     *
     * @param idEstado
     * @param cantidadDias
     * @param paginaInicio
     * @param paginaFinal
     * @return
     * @throws AtsBusinessException
     */
    public List<Documentocliente> findDocumentoClienteByEstadoAndDias(Integer idEstado, Integer cantidadDias, Integer paginaInicio, Integer paginaFinal) throws AtsBusinessException;

    /**
     * *
     *
     * @param idEstado
     * @param cantidadDias
     * @return
     * @throws AtsBusinessException
     */
    public Integer countFindDocumentoClienteByEstadoAndDias(Integer idEstado, Integer cantidadDias) throws AtsBusinessException;

    /**
     *
     * @return @throws AtsBusinessException
     */
    public List<EstadoFactura> loadAllEstadoFactura() throws AtsBusinessException;

    /**
     * 
     * @param numeroSerie
     * @param idFactura
     * @return
     * @throws AtsBusinessException 
     */
    public Documentocliente findDocumentoClienteByIdDFacturaAndSerie(String numeroSerie, Integer idFactura) throws AtsBusinessException;

    /***
     * 
     * @param detalle
     * @param encabezado
     * @return
     * @throws AtsBusinessBusinessRolledbackException 
     */
    public OperacionesDocumentoClienteDTO eliminarDetalleDocumentoCliente(DetalleDocumentoCliente detalle, Documentocliente encabezado) throws AtsBusinessBusinessRolledbackException;
}
