/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import javax.ejb.Local;
import sv.com.diserv.liquidaciones.dto.OperacionesBodegaVendedorDTO;
import sv.com.diserv.liquidaciones.entity.BodegaVendedor;
import sv.com.diserv.liquidaciones.entity.Bodegas;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

/**
 *
 * @author sonia.garcia
 */
@Local
public interface BodegaVendedorBeanLocal {

    
    public List<Bodegas> loadAllBodegasAsignables() throws DiservBusinessException;
    public Integer countAllBodegasAsignables() throws DiservBusinessException;
    public OperacionesBodegaVendedorDTO asignarBodega(Integer idBodega, Integer idVendedor) throws DiservBusinessException;
    public OperacionesBodegaVendedorDTO desasignarBodega(BodegaVendedor bodegaVendedor) throws DiservBusinessException ;
    public Bodegas findBodegaByID(Integer idBodega) throws DiservBusinessException;
    public Bodegas findBodegaAsignada(Integer idVendedor) throws DiservBusinessException;
    public BodegaVendedor findBodegaVendedorByIdVendedorBodega(Integer idVendedor, Integer idBodega) throws DiservBusinessException;

}
