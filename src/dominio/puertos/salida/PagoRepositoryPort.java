package dominio.puertos.salida;

import dominio.entidades.Pago;
import java.util.List;

public interface PagoRepositoryPort {
    void guardar(Pago pago);
    List<Pago> listar();
    Pago buscarPorId(int id);
}