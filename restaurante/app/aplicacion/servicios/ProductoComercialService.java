package restaurante.app.aplicacion.servicios;

import restaurante.app.dominio.entidades.ProductoComercial;

public class ProductoComercialService {
    public void validar(ProductoComercial p) {
        boolean menu = p != null && p.getIdMenu() != null && p.getIdProducto() == null;
        boolean producto = p != null && p.getIdMenu() == null && p.getIdProducto() != null;
        if (!menu && !producto) throw new NegocioException("ProductoComercial debe ser exclusivamente MENU (idMenu) o PRODUCTO (idProducto)");
        if (p.getPrecio() == null || p.getPrecio().signum() < 0) throw new NegocioException("El precio debe ser válido");
    }
}
