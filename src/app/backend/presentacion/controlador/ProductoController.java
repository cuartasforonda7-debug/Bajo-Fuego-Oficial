package app.backend.presentacion.controlador;

import app.aplicacion.servicios.ProductoService;
import dominio.entidades.Producto;

public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    public void crearProducto(Producto producto) {
        productoService.registrarProducto(producto);
    }

    public void registrarProducto(Producto producto) {
        crearProducto(producto);
    }
}
