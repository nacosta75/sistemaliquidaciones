/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.diserv.liquidaciones.ejb;

import java.util.List;
import sv.com.diserv.liquidaciones.entity.EncListaPrecio;
import sv.com.diserv.liquidaciones.exception.DiservBusinessException;

/**
 *
 * @author abraham.acosta
 */
public interface EncListaPrecioBeanLocal {
    
     public List<EncListaPrecio> loadAllCanales() throws DiservBusinessException;
    public EncListaPrecio loadCanalById(int id) throws DiservBusinessException ;
    
}
