package app.backend.configuracion;
import app.aplicacion.servicios.ProductoService;
import app.backend.infraestructura.adaptadores.ProductoRepositoryAdapter;
import app.backend.presentacion.controlador.ProductoController;

public class Config {

    public ProductoService obtenerProductoService() {
        return new ProductoService(new ProductoRepositoryAdapter());
    }

    public ProductoController obtenerProductoController() {
        return new ProductoController(obtenerProductoService());
    }
}