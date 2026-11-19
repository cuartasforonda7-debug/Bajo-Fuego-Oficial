package dominio.puertos.salida;

import java.util.Optional;
import dominio.entidades.Usuario;

public interface UsuarioRepositoryPort {

    void registrarUsuario(Usuario usuario);

    Optional<Usuario> buscarPorEmail(String email);

    boolean existePorEmail(String email);

    Optional<Usuario> buscarPorId(int idUsuario);
}
