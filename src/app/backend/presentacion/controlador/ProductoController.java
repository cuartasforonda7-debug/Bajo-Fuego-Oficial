package app.backend.presentacion.controlador;

import java.util.Objects;

import dominio.entidades.Producto;
import dominio.puertos.entrada.RegistrarProductoPort;

public class ProductoController {
    private final RegistrarProductoPort registrarProductoPort;

    public ProductoController(RegistrarProductoPort registrarProductoPort) {
        this.registrarProductoPort = Objects.requireNonNull(
                registrarProductoPort, "El puerto de registro es obligatorio");
    }

    public void registrarProducto(Producto producto) {
        registrarProductoPort.registrarProducto(producto);
    }
}
