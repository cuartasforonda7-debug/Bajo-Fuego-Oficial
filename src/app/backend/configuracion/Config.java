package app.backend.configuracion;

import app.aplicacion.servicios.PagoService;
import app.aplicacion.servicios.ProductoService;
import app.backend.infraestructura.adaptadores.PagoRepositoryAdapter;
import app.backend.infraestructura.adaptadores.ProductoRepositoryAdapter;
import app.backend.presentacion.controlador.PagoController;
import app.backend.presentacion.controlador.ProductoController;

public class Config {


    public ProductoService obtenerProductoService() {
        return new ProductoService(new ProductoRepositoryAdapter());
    }

    public ProductoController obtenerProductoController() {
        return new ProductoController(obtenerProductoService());
    }

    // PAGO - HU-16 TUYO (nuevo)
    public PagoService obtenerPagoService() {
        return new PagoService(new PagoRepositoryAdapter());
    }

    public PagoController obtenerPagoController() {
        return new PagoController(obtenerPagoService());
    }
}