package app.backend.presentacion.controlador;

import app.aplicacion.servicios.ProductoService;
import dominio.entidades.Producto;
import java.util.List;

public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    public void crearProducto(Producto producto) {
        productoService.registrarProducto(producto);
    }

    public List<Producto> listarProductos() {
        return productoService.obtenerProductos();
    }
}
