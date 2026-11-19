package app.backend.pruebas;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import app.aplicacion.servicios.UsuarioService;
import app.backend.presentacion.controlador.UsuarioController;
import dominio.entidades.Usuario;
import dominio.puertos.salida.UsuarioRepositoryPort;

public class UsuarioServicePrueba {

    public static void main(String[] args) {
        RepositorioEnMemoria repositorio = new RepositorioEnMemoria();
        UsuarioService service = new UsuarioService(repositorio);
        UsuarioController controller = new UsuarioController(service);

        // Caso 1: Registro exitoso a través del controlador
        Usuario usuario = usuarioValido();
        controller.registrarUsuario(usuario);
        verificar(repositorio.usuarioRegistrado == usuario, "El controlador debe delegar el registro");
        verificar("Juan Perez".equals(usuario.getNombre()), "El nombre debe normalizarse sin espacios extra");
        verificar("juan.perez@example.com".equals(usuario.getEmail()), "El email debe guardarse en minusculas y recortado");
        verificar(usuario.isEstado(), "El usuario nuevo debe crearse con estado activo");
        verificar(usuario.getFechaRegistro() != null, "La fecha de registro debe ser asignada");

        // Caso 2: Validaciones de campos requeridos y formatos
        esperarError(() -> service.registrarUsuario(null));
        esperarError(() -> service.registrarUsuario(usuarioConNombre("   ")));
        esperarError(() -> service.registrarUsuario(usuarioConEmail("   ")));
        esperarError(() -> service.registrarUsuario(usuarioConEmail("correo-invalido")));
        esperarError(() -> service.registrarUsuario(usuarioConEmail("@dominio.com")));
        esperarError(() -> service.registrarUsuario(usuarioConEmail("correo@sin-tld")));
        esperarError(() -> service.registrarUsuario(usuarioConContrasena("   ")));
        esperarError(() -> service.registrarUsuario(usuarioConContrasena("12345"))); // Menor a 6 caracteres

        // Caso 3: Prevención de usuarios duplicados
        Usuario duplicado = usuarioValido();
        duplicado.setEmail("JUAN.PEREZ@example.com"); // Mismo correo en mayúsculas
        esperarError(() -> service.registrarUsuario(duplicado));

        verificar(repositorio.llamadas == 1, "Los usuarios invalidos o duplicados no deben persistirse");

        System.out.println("Pruebas HU-01 completadas correctamente");
    }

    private static Usuario usuarioValido() {
        Usuario usuario = new Usuario();
        usuario.setNombre("  Juan Perez  ");
        usuario.setEmail("  Juan.Perez@Example.com  ");
        usuario.setTelefono(" 3001234567 ");
        usuario.setContrasena("claveSegura123");
        return usuario;
    }

    private static Usuario usuarioConNombre(String nombre) {
        Usuario usuario = usuarioValido();
        usuario.setNombre(nombre);
        return usuario;
    }

    private static Usuario usuarioConEmail(String email) {
        Usuario usuario = usuarioValido();
        usuario.setEmail(email);
        return usuario;
    }

    private static Usuario usuarioConContrasena(String contrasena) {
        Usuario usuario = usuarioValido();
        usuario.setContrasena(contrasena);
        return usuario;
    }

    private static void esperarError(Runnable accion) {
        try {
            accion.run();
            throw new AssertionError("Se esperaba una validacion fallida");
        } catch (IllegalArgumentException esperado) {
            // Error de validacion esperado
        }
    }

    private static void verificar(boolean condicion, String mensaje) {
        if (!condicion) {
            throw new AssertionError(mensaje);
        }
    }

    private static class RepositorioEnMemoria implements UsuarioRepositoryPort {
        private final List<Usuario> usuarios = new ArrayList<>();
        private Usuario usuarioRegistrado;
        private int llamadas;

        @Override
        public void registrarUsuario(Usuario usuario) {
            this.usuarioRegistrado = usuario;
            this.usuarios.add(usuario);
            this.llamadas++;
        }

        @Override
        public Optional<Usuario> buscarPorEmail(String email) {
            return usuarios.stream()
                    .filter(u -> u.getEmail().equalsIgnoreCase(email))
                    .findFirst();
        }

        @Override
        public boolean existePorEmail(String email) {
            return usuarios.stream()
                    .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
        }

        @Override
        public Optional<Usuario> buscarPorId(int idUsuario) {
            return usuarios.stream()
                    .filter(u -> u.getIdUsuario() == idUsuario)
                    .findFirst();
        }
    }
}
