package app.backend.presentacion.controlador;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import dominio.entidades.Rol;
import dominio.puertos.entrada.GestionarRolPort;

public class RolController {

    private final GestionarRolPort gestionarRolPort;

    public RolController(GestionarRolPort gestionarRolPort) {
        this.gestionarRolPort = Objects.requireNonNull(gestionarRolPort, "El puerto de gestion de roles es obligatorio");
    }

    public void crearRol(Rol rol) {
        gestionarRolPort.crearRol(rol);
    }

    public List<Rol> consultarRoles() {
        return gestionarRolPort.consultarRoles();
    }

    public Optional<Rol> buscarPorId(int idRol) {
        return gestionarRolPort.buscarPorId(idRol);
    }
}
