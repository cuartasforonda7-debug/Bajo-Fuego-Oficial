package app.aplicacion.servicios;

import java.util.Objects;
import dominio.entidades.Usuario;
import dominio.puertos.entrada.AutenticarUsuarioPort;
import dominio.puertos.salida.UsuarioRepositoryPort;

public class AutenticacionService implements AutenticarUsuarioPort {

    private final UsuarioRepositoryPort repositoryPort;

    public AutenticacionService(UsuarioRepositoryPort repositoryPort) {
        this.repositoryPort = Objects.requireNonNull(repositoryPort, "El repositorio es obligatorio");
    }

    @Override
    public Usuario iniciarSesion(String email, String contrasena) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("El correo electronico es obligatorio");
        }
        if (contrasena == null || contrasena.isBlank()) {
            throw new IllegalArgumentException("La contrasena es obligatoria");
        }

        String emailNormalizado = email.trim().toLowerCase();

        Usuario usuario = repositoryPort.buscarPorEmail(emailNormalizado)
                .orElseThrow(() -> new IllegalArgumentException("Credenciales incorrectas"));

        if (!usuario.getContrasena().equals(contrasena)) {
            throw new IllegalArgumentException("Credenciales incorrectas");
        }

        if (!usuario.isEstado()) {
            throw new IllegalStateException("El usuario se encuentra inactivo");
        }

        return usuario;
    }
}
