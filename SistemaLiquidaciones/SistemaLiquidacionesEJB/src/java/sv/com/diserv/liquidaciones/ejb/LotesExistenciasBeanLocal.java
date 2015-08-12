/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.Local;
import sv.com.diserv.liquidaciones.dto.BusquedaLoteExistenciaDTO;
import sv.com.diserv.liquidaciones.dto.ConsolidadoAsignacionesDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesLotesExistenciasDTO;
import sv.com.diserv.liquidaciones.entity.LotesExistencia;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

/**
 *
 * @author sonia.garcia
 */
@Local
public interface LotesExistenciasBeanLocal {

   
    public int actualizarLotes(List<LotesExistencia> lotes) throws DiservBusinessException;
    public List<LotesExistencia> buscarLoteByCriteria(BusquedaLoteExistenciaDTO re) throws DiservBusinessException;
    public List<ConsolidadoAsignacionesDTO> buscarLoteByCriterias(BusquedaLoteExistenciaDTO re) throws DiservBusinessException;
    public OperacionesLotesExistenciasDTO guardarLote(LotesExistencia lote) throws DiservBusinessException;
    public OperacionesLotesExistenciasDTO actualizarLote(LotesExistencia lote) throws DiservBusinessException;
    
}
