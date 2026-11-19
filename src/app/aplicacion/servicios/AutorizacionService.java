package app.aplicacion.servicios;

import dominio.entidades.Usuario;
import dominio.enums.TipoPermiso;
import dominio.puertos.entrada.ControlarAccesoPort;

public class AutorizacionService implements ControlarAccesoPort {

    @Override
    public boolean tienePermiso(Usuario usuario, TipoPermiso permiso) {
        if (usuario == null || permiso == null || !usuario.isEstado()) {
            return false;
        }

        if (usuario.getRol() == null || usuario.getRol().getPermisos() == null) {
            return false;
        }

        return usuario.getRol().getPermisos().contains(permiso);
    }

    @Override
    public void validarAcceso(Usuario usuario, TipoPermiso permiso) {
        if (usuario == null) {
            throw new SecurityException("Acceso denegado: Usuario no autenticado");
        }

        if (!usuario.isEstado()) {
            throw new SecurityException("Acceso denegado: El usuario se encuentra inactivo");
        }

        if (usuario.getRol() == null) {
            throw new SecurityException("Acceso denegado: El usuario no tiene un rol asignado");
        }

        if (!tienePermiso(usuario, permiso)) {
            String nombreRol = usuario.getRol().getNombre() != null ? usuario.getRol().getNombre() : "DESCONOCIDO";
            throw new SecurityException("Acceso denegado: El rol " + nombreRol + " no tiene autorizacion para " + permiso);
        }
    }

    @Override
    public String obtenerNombreRol(Usuario usuario) {
        if (usuario == null || usuario.getRol() == null || usuario.getRol().getNombre() == null) {
            return "SIN_ROL";
        }
        return usuario.getRol().getNombre();
    }
}
