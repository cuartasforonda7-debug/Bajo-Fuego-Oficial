package app.aplicacion.servicios;

import dominio.entidades.Pago;
import dominio.puertos.entrada.RegistrarPagoPort;
import dominio.puertos.salida.PagoRepositoryPort;
import java.util.List;

public class PagoService implements RegistrarPagoPort {

    private final PagoRepositoryPort pagoRepositorio;

    public PagoService(PagoRepositoryPort pagoRepositorio) {
        this.pagoRepositorio = pagoRepositorio;
    }

    @Override
    public void registrarPago(Pago pago) {
        if (pago.getMonto() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a 0");
        }
        pagoRepositorio.guardar(pago);
    }

    @Override
    public List<Pago> obtenerPagos() {
        return pagoRepositorio.listar();
    }

    @Override
    public Pago obtenerPagoPorId(int id) {
        return pagoRepositorio.buscarPorId(id);
    }
}
