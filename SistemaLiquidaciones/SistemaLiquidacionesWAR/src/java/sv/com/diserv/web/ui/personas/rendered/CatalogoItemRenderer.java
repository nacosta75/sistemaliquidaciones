package sv.com.diserv.web.ui.personas.rendered;

import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import sv.com.diserv.liquidaciones.dto.CatalogoDTO;
import sv.com.diserv.liquidaciones.entity.Articulos;
import sv.com.diserv.liquidaciones.entity.Bodegas;
import sv.com.diserv.liquidaciones.entity.EncListaPrecio;
import sv.com.diserv.liquidaciones.entity.LineaArticulo;
import sv.com.diserv.liquidaciones.entity.MarcaArticulo;
import sv.com.diserv.liquidaciones.entity.Personas;
import sv.com.diserv.liquidaciones.entity.Sucursales;
import sv.com.diserv.liquidaciones.entity.Tipoarticulo;
import sv.com.diserv.liquidaciones.entity.UnidadesMed;

public class CatalogoItemRenderer implements ComboitemRenderer {

    private Articulos art;
    private Personas personas;
    private CatalogoDTO catalogo;
    private Tipoarticulo tipoArt;
    private LineaArticulo linea;
    private MarcaArticulo marca;
    private UnidadesMed medida;
    private EncListaPrecio canales;
    private Bodegas bodegas;
    private Sucursales sucursales;
    
    @Override
    public void render(Comboitem item, Object data) {
        try {
            if (data instanceof CatalogoDTO) {
                catalogo = (CatalogoDTO) data;
                item.setLabel(catalogo.getDescripcionCatalogo());
                item.setValue(catalogo);
            } else if (data instanceof Articulos) {
                art = (Articulos) data;
                item.setLabel(art.getDescarticulo());
                item.setValue(art);
            }
            else if (data instanceof Tipoarticulo) {
                tipoArt = (Tipoarticulo) data;
                item.setLabel(tipoArt.getDescripcion());
                item.setValue(tipoArt);
            }
            else if (data instanceof Personas) {
                personas = (Personas) data;
                item.setLabel(personas.getNombre());
                item.setValue(personas);
            }
            else if (data instanceof LineaArticulo) {
                linea = (LineaArticulo) data;
                item.setLabel(linea.getDesclinea());
                item.setValue(linea);
            }
             else if (data instanceof MarcaArticulo) {
                marca = (MarcaArticulo) data;
                item.setLabel(marca.getDescmarca());
                item.setValue(marca);
            }
            else if (data instanceof UnidadesMed) {
                medida = (UnidadesMed) data;
                item.setLabel(medida.getDescumedida());
                item.setValue(medida);
            }
             else if (data instanceof EncListaPrecio) {
                canales = (EncListaPrecio) data;
                item.setLabel(canales.getDescripcionLista());
                item.setValue(canales);
            }
             else if (data instanceof Bodegas) {
                bodegas = (Bodegas) data;
                item.setLabel(bodegas.getNombre());
                item.setValue(bodegas);
            }
             else if (data instanceof Sucursales) {
                sucursales = (Sucursales) data;
                item.setLabel(sucursales.getDescripcion());
                item.setValue(sucursales);
            }
       
        } catch (Exception e) {
            e.printStackTrace();
        }
        item.setAttribute("data", data);

    }
}
