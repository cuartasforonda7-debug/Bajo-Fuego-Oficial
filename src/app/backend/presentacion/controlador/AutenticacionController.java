package app.backend.presentacion.controlador;

import java.util.Objects;
import dominio.entidades.Usuario;
import dominio.puertos.entrada.AutenticarUsuarioPort;

public class AutenticacionController {

    private final AutenticarUsuarioPort autenticarUsuarioPort;

    public AutenticacionController(AutenticarUsuarioPort autenticarUsuarioPort) {
        this.autenticarUsuarioPort = Objects.requireNonNull(
                autenticarUsuarioPort, "El puerto de autenticacion es obligatorio");
    }

    public Usuario iniciarSesion(String email, String contrasena) {
        return autenticarUsuarioPort.iniciarSesion(email, contrasena);
    }
}
