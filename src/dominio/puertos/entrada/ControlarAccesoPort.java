package dominio.puertos.entrada;

import dominio.entidades.Usuario;
import dominio.enums.TipoPermiso;

public interface ControlarAccesoPort {

    boolean tienePermiso(Usuario usuario, TipoPermiso permiso);

    void validarAcceso(Usuario usuario, TipoPermiso permiso);

    String obtenerNombreRol(Usuario usuario);
}
