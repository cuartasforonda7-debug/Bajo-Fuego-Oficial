package app.backend.infraestructura.repositorio;

import dominio.entidades.Pago;
import java.util.ArrayList;
import java.util.List;

public class PagoRepositorio {

    private List<Pago> pagos = new ArrayList<>();

    public void guardar(Pago pago) {
        pagos.add(pago);
    }

    public List<Pago> listar() {
        return pagos;
    }
}
