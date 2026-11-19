package app.aplicacion.servicios;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import dominio.entidades.Rol;
import dominio.puertos.entrada.GestionarRolPort;
import dominio.puertos.salida.RolRepositoryPort;

public class RolService implements GestionarRolPort {

    private final RolRepositoryPort rolRepositoryPort;

    public RolService(RolRepositoryPort rolRepositoryPort) {
        this.rolRepositoryPort = Objects.requireNonNull(rolRepositoryPort, "El repositorio de roles es obligatorio");
    }

    @Override
    public void crearRol(Rol rol) {
        if (rol == null) {
            throw new IllegalArgumentException("El rol es obligatorio");
        }
        if (rol.getNombre() == null || rol.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del rol es obligatorio");
        }
        rol.setNombre(rol.getNombre().trim());

        if (rolRepositoryPort.buscarPorNombre(rol.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un rol con el nombre: " + rol.getNombre());
        }

        rolRepositoryPort.guardar(rol);
    }

    @Override
    public List<Rol> consultarRoles() {
        return rolRepositoryPort.consultarTodos();
    }

    @Override
    public Optional<Rol> buscarPorId(int idRol) {
        return rolRepositoryPort.buscarPorId(idRol);
    }
}
