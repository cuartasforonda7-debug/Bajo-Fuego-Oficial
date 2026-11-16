package app.aplicacion.servicios;

import org.restaurante.entidades.pago;
import org.restaurante.port.PagoRepositoryPort;
import org.restaurante.port.RegistrarPagoUseCase;
import java.util.List;

public class RegistrarPagoService implements RegistrarPagoUseCase {
    private final PagoRepositoryPort repository;
    public RegistrarPagoService(PagoRepositoryPort repository){ this.repository = repository; }

    @Override
    public pago registrarPago(int pedidoId, double totalPedido, double dineroRecibido) {
        if(dineroRecibido < totalPedido){
            throw new RuntimeException("Dinero insuficiente. Faltan: " + (totalPedido - dineroRecibido));
        }
        double vueltas = dineroRecibido - totalPedido;
        pago p = new pago(pedidoId, totalPedido, dineroRecibido, vueltas);
        return repository.guardar(p);
    }
    @Override
    public List<pago> listarPagos(){ return repository.listarTodos(); }
}
