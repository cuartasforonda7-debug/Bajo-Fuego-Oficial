package app.backend.pruebas;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import app.aplicacion.servicios.AutenticacionService;
import app.aplicacion.servicios.UsuarioService;
import app.backend.presentacion.controlador.AutenticacionController;
import app.backend.presentacion.controlador.UsuarioController;
import dominio.entidades.Usuario;
import dominio.puertos.salida.UsuarioRepositoryPort;

public class UsuarioServicePrueba {

    public static void main(String[] args) {
        RepositorioEnMemoria repositorio = new RepositorioEnMemoria();
        UsuarioService usuarioService = new UsuarioService(repositorio);
        UsuarioController usuarioController = new UsuarioController(usuarioService);

        AutenticacionService authService = new AutenticacionService(repositorio);
        AutenticacionController authController = new AutenticacionController(authService);

        // ==========================================
        // PRUEBAS HU-01: REGISTRAR USUARIO
        // ==========================================

        // Caso 1: Registro exitoso a través del controlador
        Usuario usuario = usuarioValido();
        usuarioController.registrarUsuario(usuario);
        verificar(repositorio.usuarioRegistrado == usuario, "El controlador debe delegar el registro");
        verificar("Juan Perez".equals(usuario.getNombre()), "El nombre debe normalizarse sin espacios extra");
        verificar("juan.perez@example.com".equals(usuario.getEmail()), "El email debe guardarse en minusculas y recortado");
        verificar(usuario.isEstado(), "El usuario nuevo debe crearse con estado activo");
        verificar(usuario.getFechaRegistro() != null, "La fecha de registro debe ser asignada");

        // Caso 2: Validaciones de campos requeridos y formatos
        esperarError(() -> usuarioService.registrarUsuario(null));
        esperarError(() -> usuarioService.registrarUsuario(usuarioConNombre("   ")));
        esperarError(() -> usuarioService.registrarUsuario(usuarioConEmail("   ")));
        esperarError(() -> usuarioService.registrarUsuario(usuarioConEmail("correo-invalido")));
        esperarError(() -> usuarioService.registrarUsuario(usuarioConEmail("@dominio.com")));
        esperarError(() -> usuarioService.registrarUsuario(usuarioConEmail("correo@sin-tld")));
        esperarError(() -> usuarioService.registrarUsuario(usuarioConContrasena("   ")));
        esperarError(() -> usuarioService.registrarUsuario(usuarioConContrasena("12345"))); // Menor a 6 caracteres

        // Caso 3: Prevención de usuarios duplicados
        Usuario duplicado = usuarioValido();
        duplicado.setEmail("JUAN.PEREZ@example.com"); // Mismo correo en mayúsculas
        esperarError(() -> usuarioService.registrarUsuario(duplicado));

        verificar(repositorio.llamadas == 1, "Los usuarios invalidos o duplicados no deben persistirse");
        System.out.println("Pruebas HU-01 completadas correctamente");

        // ==========================================
        // PRUEBAS HU-02: INICIAR SESIÓN
        // ==========================================

        // Caso 4: Inicio de sesión exitoso con credenciales correctas
        Usuario usuarioAutenticado = authController.iniciarSesion("juan.perez@example.com", "claveSegura123");
        verificar(usuarioAutenticado != null, "El usuario debe autenticarse exitosamente");
        verificar(usuarioAutenticado.getEmail().equals("juan.perez@example.com"), "Debe devolver el usuario correspondiente");

        // Caso 5: Error con contraseña incorrecta
        esperarError(() -> authService.iniciarSesion("juan.perez@example.com", "claveIncorrecta"));

        // Caso 6: Error con usuario inexistente
        esperarError(() -> authService.iniciarSesion("noexiste@example.com", "claveSegura123"));

        // Caso 7: Error con datos vacíos o nulos
        esperarError(() -> authService.iniciarSesion(null, "claveSegura123"));
        esperarError(() -> authService.iniciarSesion("juan.perez@example.com", null));
        esperarError(() -> authService.iniciarSesion("  ", "claveSegura123"));

        // Caso 8: Rechazo de usuario inactivo
        Usuario inactivo = usuarioValido();
        inactivo.setEmail("inactivo@example.com");
        inactivo.setEstado(false);
        repositorio.registrarUsuario(inactivo);
        esperarEstadoInvalido(() -> authService.iniciarSesion("inactivo@example.com", "claveSegura123"));

        System.out.println("Pruebas HU-02 completadas correctamente");
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
            throw new AssertionError("Se esperaba una validacion fallida (IllegalArgumentException)");
        } catch (IllegalArgumentException esperado) {
            // Error de validacion esperado
        }
    }

    private static void esperarEstadoInvalido(Runnable accion) {
        try {
            accion.run();
            throw new AssertionError("Se esperaba una validacion de estado (IllegalStateException)");
        } catch (IllegalStateException esperado) {
            // Estado inactivo esperado
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
