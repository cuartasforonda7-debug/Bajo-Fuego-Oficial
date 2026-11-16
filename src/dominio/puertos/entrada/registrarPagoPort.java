package dominio.puertos.entrada;

import org.restaurante.entidades.pago;
import java.util.List;

public interface RegistrarPagoUseCase {
    pago registrarPago(int pedidoId, double totalPedido, double dineroRecibido);
    List<pago> listarPagos();
}
