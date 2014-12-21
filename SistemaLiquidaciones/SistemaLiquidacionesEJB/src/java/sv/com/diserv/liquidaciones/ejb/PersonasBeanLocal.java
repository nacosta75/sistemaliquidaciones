/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.Local;
import sv.com.diserv.liquidaciones.dto.BusquedaBodegaDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesPersonaDTO;
import sv.com.diserv.liquidaciones.entity.Bodegas;
import sv.com.diserv.liquidaciones.entity.Personas;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

/**
 *
 * @author sonia.garcia
 */
@Local
public interface PersonasBeanLocal {

    /**
     * *
     *
     * @param inicio
     * @param fin
     * @return
     * @throws DiservBusinessException
     */
    public List<Personas> loadAllPersona(int inicio, int fin) throws DiservBusinessException;

    /**
     * *
     *
     * @return @throws DiservBusinessException
     */
    public Integer countAllPersonas(int tipoPersona) throws DiservBusinessException;

    /**
     * *
     *
     * @param bodega
     * @return
     * @throws DiservBusinessException
     */
    public OperacionesPersonaDTO guardarPersona(Personas persona) throws DiservBusinessException;

    /**
     * *
     *
     * @param nombreLike
     * @return
     * @throws DiservBusinessException
     */
    public List<Personas> loadAllPersonasByLike(String nombreLike) throws DiservBusinessException;

    /**
     * *
     *
     * @param cliente
     * @return
     * @throws DiservBusinessException
     */
    public OperacionesPersonaDTO actualizarPersona(Personas persona) throws DiservBusinessException;

    /**
     * *
     *
     * @param request
     * @return
     * @throws DiservBusinessException
     */
    public List<Bodegas> buscarBodegaByCriteria(BusquedaBodegaDTO request) throws DiservBusinessException;

    /**
     * *
     *
     * @param likeNombre
     * @return
     * @throws DiservBusinessException
     */
    public List<Bodegas> loadBodegaByNombreLike(String likeNombre) throws DiservBusinessException;

    /**
     * **
     *
     * @param idBodega
     * @return
     * @throws DiservBusinessException
     */
    public Bodegas loadBodegaByID(Integer idBodega) throws DiservBusinessException;
}
