/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.Local;
import sv.com.diserv.liquidaciones.dto.BusquedaEmpresaDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesEmpresaDTO;
import sv.com.diserv.liquidaciones.entity.Empresas;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

/**
 *
 * @author abraham.acosta
 */

@Local
public interface EmpresasBeanLocal {
    
    
        /**
     * *
     *
     * @param inicio
     * @param fin
     * @return
     * @throws DiservBusinessException
     */
    public List<Empresas> loadAllEmpresa(int inicio, int fin) throws DiservBusinessException;

    /**
     * *
     *
     * @return @throws DiservBusinessException
     */
    public Integer countAllEmpresa() throws DiservBusinessException;

    /**
     * *
     *
     * @param empresa
     * @return
     * @throws DiservBusinessException
     */
    public OperacionesEmpresaDTO guardarEmpresa(Empresas empresa) throws DiservBusinessException;

    /**
     * *
     *
     * @param nombreLike
     * @return
     * @throws DiservBusinessException
     */
    public List<Empresas> loadAllEmpresasByLike(String nombreLike) throws DiservBusinessException;

    /**
     * *
     *
     * @param empresa
     * @return
     * @throws DiservBusinessException
     */
    public OperacionesEmpresaDTO actualizarEmpresa(Empresas empresa) throws DiservBusinessException;

    /**
     * *
     *
     * @param request
     * @return
     * @throws DiservBusinessException
     */
    public List<Empresas> buscarEmpresaByCriteria(BusquedaEmpresaDTO request) throws DiservBusinessException;

    /**
     * *
     *
     * @param likeNombre
     * @return
     * @throws DiservBusinessException
     */
    public List<Empresas> loadEmpresaByNombreLike(String likeNombre) throws DiservBusinessException;

    /**
     * **
     *
     * @param idEmpresa
     * @return
     * @throws DiservBusinessException
     */
    public Empresas loadEmpresaByID(Integer idEmpresa) throws DiservBusinessException;

}
