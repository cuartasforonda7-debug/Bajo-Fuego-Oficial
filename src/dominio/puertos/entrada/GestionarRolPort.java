package dominio.puertos.entrada;

import java.util.List;
import java.util.Optional;
import dominio.entidades.Rol;

public interface GestionarRolPort {

    void crearRol(Rol rol);

    List<Rol> consultarRoles();

    Optional<Rol> buscarPorId(int idRol);
}
