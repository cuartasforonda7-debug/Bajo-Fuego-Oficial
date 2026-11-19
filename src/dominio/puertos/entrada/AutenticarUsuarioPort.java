package dominio.puertos.entrada;

import dominio.entidades.Usuario;

public interface AutenticarUsuarioPort {

    Usuario iniciarSesion(String email, String contrasena);
}
