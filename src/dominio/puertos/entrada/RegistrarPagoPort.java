package dominio.puertos.entrada;

import dominio.entidades.Pago;
import java.util.List;

public interface RegistrarPagoPort {
    void registrarPago(Pago pago);
    List<Pago> obtenerPagos();
    Pago obtenerPagoPorId(int id);
}
