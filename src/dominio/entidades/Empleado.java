package dominio.entidades;

import java.time.LocalDateTime;

public class Empleado extends Usuario {

    private LocalDateTime fechaIngreso;

    // === CONSTRUCTOR VACIO ===
    public Empleado() {
    }

    // === CONSTRUCTOR COMPLETO ===
    public Empleado(int idUsuario, String nombre, String email,
                    String telefono, String contrasena, boolean estado,
                    LocalDateTime fechaRegistro,
                    LocalDateTime fechaIngreso, Rol rol) {

        super(idUsuario, nombre, email, telefono, contrasena, estado, fechaRegistro, rol);
        this.fechaIngreso = fechaIngreso;
    }

    // === GETTERS Y SETTERS ===
    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
}
