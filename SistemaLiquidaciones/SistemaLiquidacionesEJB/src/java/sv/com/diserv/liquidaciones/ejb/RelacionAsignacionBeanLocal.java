/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.Local;
import sv.com.diserv.liquidaciones.dto.OperacionesRelAsignacionDTO;
import sv.com.diserv.liquidaciones.entity.LotesExistencia;
import sv.com.diserv.liquidaciones.entity.Movimientos;
import sv.com.diserv.liquidaciones.entity.RelacionAsignaciones;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

/**
 *
 * @author sonia.garcia
 */
@Local
public interface RelacionAsignacionBeanLocal {

    public Integer countAllRelASig() throws DiservBusinessException;
    public List<RelacionAsignaciones> loadAllRelAsig(int inicio, int fin) throws DiservBusinessException;
    public OperacionesRelAsignacionDTO guardarRelacionAsignacion(RelacionAsignaciones relacionAsignacion) throws DiservBusinessException;
    public OperacionesRelAsignacionDTO eliminarRelacionAsignacion(RelacionAsignaciones relacionAsignacion) throws DiservBusinessException;
    public OperacionesRelAsignacionDTO guardarRelacionAsignacion(Movimientos movimiento, List<LotesExistencia> lotes) throws DiservBusinessException;
    public RelacionAsignaciones loadRelacionByVendedor(Integer vendedor, String icc) throws DiservBusinessException;
}
