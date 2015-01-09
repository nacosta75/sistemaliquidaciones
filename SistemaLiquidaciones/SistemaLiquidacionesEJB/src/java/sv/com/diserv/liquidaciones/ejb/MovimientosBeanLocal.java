/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.Local;
import sv.com.diserv.liquidaciones.dto.BusquedaPersonaDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesPersonaDTO;
import sv.com.diserv.liquidaciones.entity.Bodegas;
import sv.com.diserv.liquidaciones.entity.Movimientos;
import sv.com.diserv.liquidaciones.entity.Personas;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

/**
 *
 * @author sonia.garcia
 */
@Local
public interface MovimientosBeanLocal {

   
    public List<Movimientos> loadAllMovimientos(int inicio, int fin, int tipo) throws DiservBusinessException;
    public Integer countAllPersonas(int tipoPersona) throws DiservBusinessException;
    public OperacionesPersonaDTO guardarPersona(Personas persona) throws DiservBusinessException;
    public List<Personas> loadAllPersonasByLike(String nombreLike) throws DiservBusinessException;
    public OperacionesPersonaDTO actualizarPersona(Personas persona) throws DiservBusinessException;
    public OperacionesPersonaDTO eliminarPersona(Personas persona) throws DiservBusinessException;
    public List<Personas> buscarPersonaByCriteria(BusquedaPersonaDTO request) throws DiservBusinessException;
    public List<Bodegas> loadBodegaByNombreLike(String likeNombre) throws DiservBusinessException;
    public Bodegas loadBodegaByID(Integer idBodega) throws DiservBusinessException;
}
