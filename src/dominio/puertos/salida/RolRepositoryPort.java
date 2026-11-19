package dominio.puertos.salida;

import java.util.List;
import java.util.Optional;
import dominio.entidades.Rol;

public interface RolRepositoryPort {

    void guardar(Rol rol);

    List<Rol> consultarTodos();

    Optional<Rol> buscarPorId(int idRol);

    Optional<Rol> buscarPorNombre(String nombre);
}
