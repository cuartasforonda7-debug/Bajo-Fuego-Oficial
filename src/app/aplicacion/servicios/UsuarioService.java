package app.aplicacion.servicios;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import dominio.entidades.Usuario;
import dominio.puertos.entrada.RegistrarUsuarioPort;
import dominio.puertos.salida.UsuarioRepositoryPort;

public class UsuarioService implements RegistrarUsuarioPort {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private final UsuarioRepositoryPort repositoryPort;

    public UsuarioService(UsuarioRepositoryPort repositoryPort) {
        this.repositoryPort = Objects.requireNonNull(repositoryPort, "El repositorio es obligatorio");
    }

    @Override
    public void registrarUsuario(Usuario usuario) {
        validarUsuario(usuario);
        verificarDuplicado(usuario.getEmail());

        if (usuario.getFechaRegistro() == null) {
            usuario.setFechaRegistro(LocalDateTime.now());
        }
        usuario.setEstado(true);

        repositoryPort.registrarUsuario(usuario);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }
        return repositoryPort.buscarPorEmail(email.trim().toLowerCase());
    }

    public Optional<Usuario> buscarPorId(int idUsuario) {
        return repositoryPort.buscarPorId(idUsuario);
    }

    private void validarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario es obligatorio");
        }

        if (usuario.getNombre() == null || usuario.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del usuario es obligatorio");
        }
        usuario.setNombre(usuario.getNombre().trim());

        if (usuario.getEmail() == null || usuario.getEmail().isBlank()) {
            throw new IllegalArgumentException("El correo electronico es obligatorio");
        }
        usuario.setEmail(usuario.getEmail().trim().toLowerCase());

        if (!EMAIL_PATTERN.matcher(usuario.getEmail()).matches()) {
            throw new IllegalArgumentException("El formato del correo electronico no es valido");
        }

        if (usuario.getContrasena() == null || usuario.getContrasena().isBlank()) {
            throw new IllegalArgumentException("La contrasena es obligatoria");
        }
        if (usuario.getContrasena().length() < 6) {
            throw new IllegalArgumentException("La contrasena debe tener al menos 6 caracteres");
        }

        if (usuario.getTelefono() != null) {
            usuario.setTelefono(usuario.getTelefono().trim());
        }
    }

    private void verificarDuplicado(String email) {
        if (repositoryPort.existePorEmail(email)) {
            throw new IllegalArgumentException("Ya existe un usuario registrado con el correo: " + email);
        }
    }
}
