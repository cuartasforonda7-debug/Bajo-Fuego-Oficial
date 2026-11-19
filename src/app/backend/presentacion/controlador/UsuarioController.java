package app.backend.presentacion.controlador;

import java.util.Objects;
import dominio.entidades.Usuario;
import dominio.puertos.entrada.RegistrarUsuarioPort;

public class UsuarioController {

    private final RegistrarUsuarioPort registrarUsuarioPort;

    public UsuarioController(RegistrarUsuarioPort registrarUsuarioPort) {
        this.registrarUsuarioPort = Objects.requireNonNull(
                registrarUsuarioPort, "El puerto de registro es obligatorio");
    }

    public void registrarUsuario(Usuario usuario) {
        registrarUsuarioPort.registrarUsuario(usuario);
    }
}
