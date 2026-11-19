package app.backend.presentacion.controlador;

import app.aplicacion.servicios.PagoService;
import dominio.entidades.Pago;
import java.util.List;

public class PagoController {
    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    public void crearPago(Pago pago) {
        pagoService.registrarPago(pago);
    }

    public List<Pago> listarPagos() {
        return pagoService.obtenerPagos();
    }

    public Pago buscarPorId(int id) {
        return pagoService.obtenerPagoPorId(id);
    }
}
