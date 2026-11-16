package dominio.puertos.salida;

import org.restaurante.entidades.pago;
import java.util.List;

public interface PagoRepositoryPort {
    pago guardar(pago pago);
    List<pago> listarTodos();
}
