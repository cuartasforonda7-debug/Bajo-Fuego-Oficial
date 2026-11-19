package app.aplicacion.servicios;

import java.util.Objects;

import dominio.entidades.Producto;
import dominio.puertos.entrada.RegistrarProductoPort;
import dominio.puertos.salida.ProductoRepositoryPort;

public class ProductoService implements RegistrarProductoPort {

    private final ProductoRepositoryPort repositoryPort;

    public ProductoService(ProductoRepositoryPort repositoryPort) {
        this.repositoryPort = Objects.requireNonNull(repositoryPort, "El repositorio es obligatorio");
    }

    @Override
    public void registrarProducto(Producto producto) {
        validarProducto(producto);
        repositoryPort.registrarProducto(producto);
    }

    private void validarProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto es obligatorio");
        }

        if (producto.getNombre() == null || producto.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        producto.setNombre(producto.getNombre().trim());

        if (producto.getUnidadMedida() == null || producto.getUnidadMedida().isBlank()) {
            throw new IllegalArgumentException("La unidad de medida es obligatoria");
        }
        producto.setUnidadMedida(producto.getUnidadMedida().trim());

        validarValorNoNegativo(producto.getCosto(), "El costo");
        validarValorNoNegativo(producto.getPrecioVenta(), "El precio de venta");
        validarValorNoNegativo(producto.getStockActual(), "El stock actual");
        validarValorNoNegativo(producto.getStockMinimo(), "El stock minimo");

        if (producto.getEstado() == null) {
            throw new IllegalArgumentException("El estado del producto es obligatorio");
        }
    }

    private void validarValorNoNegativo(double valor, String campo) {
        if (!Double.isFinite(valor) || valor < 0) {
            throw new IllegalArgumentException(campo + " debe ser un valor finito no negativo");
        }
    }
}