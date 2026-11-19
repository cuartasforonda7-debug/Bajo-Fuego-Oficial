package app.backend.pruebas;

import app.aplicacion.servicios.ProductoService;
import app.backend.presentacion.controlador.ProductoController;
import dominio.entidades.Producto;
import dominio.enums.EstadoProducto;
import dominio.puertos.salida.ProductoRepositoryPort;

public class ProductoServicePrueba {

    public static void main(String[] args) {
        RepositorioEnMemoria repositorio = new RepositorioEnMemoria();
        ProductoService service = new ProductoService(repositorio);
        ProductoController controller = new ProductoController(service);

        Producto producto = productoValido();
        controller.registrarProducto(producto);
        verificar(repositorio.productoRegistrado == producto, "El controlador debe delegar el registro");
        verificar("Pescado".equals(producto.getNombre()), "El nombre debe normalizarse");
        verificar(producto.getEstado() == EstadoProducto.ACTIVO, "El producto nuevo debe estar activo");

        esperarError(() -> service.registrarProducto(null));
        esperarError(() -> service.registrarProducto(productoConNombre("  ")));
        esperarError(() -> service.registrarProducto(productoConUnidad(" ")));
        esperarError(() -> service.registrarProducto(productoConCosto(-1)));
        esperarError(() -> service.registrarProducto(productoConCosto(Double.NaN)));
        esperarError(() -> service.registrarProducto(productoConPrecio(-1)));
        esperarError(() -> service.registrarProducto(productoConPrecio(Double.POSITIVE_INFINITY)));
        esperarError(() -> service.registrarProducto(productoConStock(-1)));
        esperarError(() -> service.registrarProducto(productoConStock(Double.POSITIVE_INFINITY)));
        esperarError(() -> service.registrarProducto(productoConStockMinimo(-1)));
        esperarError(() -> service.registrarProducto(productoConEstado(null)));
        verificar(repositorio.llamadas == 1, "Los productos invalidos no deben persistirse");

        System.out.println("Pruebas HU-05 completadas correctamente");
    }

    private static Producto productoValido() {
        Producto producto = new Producto();
        producto.setNombre("  Pescado  ");
        producto.setDescripcion("Filete de trucha");
        producto.setUnidadMedida(" kg ");
        producto.setCosto(12000);
        producto.setPrecioVenta(18000);
        producto.setStockActual(18);
        producto.setStockMinimo(5);
        return producto;
    }

    private static Producto productoConPrecio(double precio) {
        Producto producto = productoValido();
        producto.setPrecioVenta(precio);
        return producto;
    }

    private static Producto productoConNombre(String nombre) {
        Producto producto = productoValido();
        producto.setNombre(nombre);
        return producto;
    }

    private static Producto productoConUnidad(String unidad) {
        Producto producto = productoValido();
        producto.setUnidadMedida(unidad);
        return producto;
    }

    private static Producto productoConCosto(double costo) {
        Producto producto = productoValido();
        producto.setCosto(costo);
        return producto;
    }

    private static Producto productoConStock(double stock) {
        Producto producto = productoValido();
        producto.setStockActual(stock);
        return producto;
    }

    private static Producto productoConStockMinimo(double stockMinimo) {
        Producto producto = productoValido();
        producto.setStockMinimo(stockMinimo);
        return producto;
    }

    private static Producto productoConEstado(EstadoProducto estado) {
        Producto producto = productoValido();
        producto.setEstado(estado);
        return producto;
    }

    private static void esperarError(Runnable accion) {
        try {
            accion.run();
            throw new AssertionError("Se esperaba una validacion fallida");
        } catch (IllegalArgumentException esperado) {
            // Expected validation error.
        }
    }

    private static void verificar(boolean condicion, String mensaje) {
        if (!condicion) {
            throw new AssertionError(mensaje);
        }
    }

    private static class RepositorioEnMemoria implements ProductoRepositoryPort {
        private Producto productoRegistrado;
        private int llamadas;

        @Override
        public void registrarProducto(Producto producto) {
            productoRegistrado = producto;
            llamadas++;
        }
    }
}
