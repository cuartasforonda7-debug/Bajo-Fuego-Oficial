package app.backend.infraestructura.adaptadores;


import app.backend.infraestructura.repositorio.PagoRepository;
import dominio.entidades.Pago;
import dominio.puertos.salida.PagoRepositoryPort;
import java.util.List;

public class PagoRepositoryAdapter implements PagoRepositoryPort {

    private final PagoRepository repository;

    public PagoRepositoryAdapter(PagoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void guardar(Pago pago) {
        repository.guardar(pago);
    }

    @Override
    public List<Pago> listar() {
        return repository.listar();
    }

    @Override
    public Pago buscarPorId(int idPago) {
        return repository.buscarPorId(idPago);
    }
}
