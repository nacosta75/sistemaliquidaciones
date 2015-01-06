/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.Local;
import sv.com.diserv.liquidaciones.dto.BusquedaBodegaDTO;
import sv.com.diserv.liquidaciones.dto.OperacionesBodegaVendedorDTO;
import sv.com.diserv.liquidaciones.entity.Bodegas;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

/**
 *
 * @author sonia.garcia
 */
@Local
public interface BodegaVendedorBeanLocal {

    /**
     * *
     *
     * @param inicio
     * @param fin
     * @return
     * @throws DiservBusinessException
     */
    public List<Bodegas> loadAllBodegasAsignables() throws DiservBusinessException;

    /**
     * *
     *
     * @return @throws DiservBusinessException
     */
    public Integer countAllBodegasAsignables() throws DiservBusinessException;

    /**
     * *
     *
     * @param bodega
     * @return
     * @throws DiservBusinessException
     */
    public OperacionesBodegaVendedorDTO asignarBodega(Bodegas bodega) throws DiservBusinessException;

}
