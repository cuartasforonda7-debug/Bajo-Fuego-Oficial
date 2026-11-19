package app.aplicacion.servicios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import dominio.entidades.Empleado;
import dominio.entidades.Rol;
import dominio.puertos.entrada.GestionarEmpleadoPort;
import dominio.puertos.salida.EmpleadoRepositoryPort;
import dominio.puertos.salida.RolRepositoryPort;

public class EmpleadoService implements GestionarEmpleadoPort {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final EmpleadoRepositoryPort empleadoRepositoryPort;
    private final RolRepositoryPort rolRepositoryPort;

    public EmpleadoService(EmpleadoRepositoryPort empleadoRepositoryPort, RolRepositoryPort rolRepositoryPort) {
        this.empleadoRepositoryPort = Objects.requireNonNull(empleadoRepositoryPort, "El repositorio de empleados es obligatorio");
        this.rolRepositoryPort = Objects.requireNonNull(rolRepositoryPort, "El repositorio de roles es obligatorio");
    }

    @Override
    public void registrarEmpleado(Empleado empleado) {
        validarEmpleado(empleado);

        if (empleadoRepositoryPort.existePorEmail(empleado.getEmail())) {
            throw new IllegalArgumentException("Ya existe un empleado registrado con el correo: " + empleado.getEmail());
        }

        if (empleado.getRol() != null && empleado.getRol().getIdRol() > 0) {
            Rol rolValido = rolRepositoryPort.buscarPorId(empleado.getRol().getIdRol())
                    .orElseThrow(() -> new IllegalArgumentException("El rol asignado no existe"));
            empleado.setRol(rolValido);
        }

        if (empleado.getFechaRegistro() == null) {
            empleado.setFechaRegistro(LocalDateTime.now());
        }
        if (empleado.getFechaIngreso() == null) {
            empleado.setFechaIngreso(LocalDateTime.now());
        }
        empleado.setEstado(true);

        empleadoRepositoryPort.registrarEmpleado(empleado);
    }

    @Override
    public List<Empleado> consultarEmpleados() {
        return empleadoRepositoryPort.consultarEmpleados();
    }

    @Override
    public Optional<Empleado> buscarPorId(int idEmpleado) {
        return empleadoRepositoryPort.buscarPorId(idEmpleado);
    }

    @Override
    public void asignarRol(int idEmpleado, int idRol) {
        if (idEmpleado <= 0) {
            throw new IllegalArgumentException("El id del empleado debe ser mayor a cero");
        }
        if (idRol <= 0) {
            throw new IllegalArgumentException("El id del rol debe ser mayor a cero");
        }

        Empleado empleado = empleadoRepositoryPort.buscarPorId(idEmpleado)
                .orElseThrow(() -> new IllegalArgumentException("El empleado con id " + idEmpleado + " no existe"));

        Rol rol = rolRepositoryPort.buscarPorId(idRol)
                .orElseThrow(() -> new IllegalArgumentException("El rol con id " + idRol + " no existe"));

        empleadoRepositoryPort.actualizarRol(empleado.getIdUsuario(), rol);
    }

    private void validarEmpleado(Empleado empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException("El empleado es obligatorio");
        }

        if (empleado.getNombre() == null || empleado.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del empleado es obligatorio");
        }
        empleado.setNombre(empleado.getNombre().trim());

        if (empleado.getEmail() == null || empleado.getEmail().isBlank()) {
            throw new IllegalArgumentException("El correo electronico es obligatorio");
        }
        empleado.setEmail(empleado.getEmail().trim().toLowerCase());

        if (!EMAIL_PATTERN.matcher(empleado.getEmail()).matches()) {
            throw new IllegalArgumentException("El formato del correo electronico no es valido");
        }

        if (empleado.getContrasena() == null || empleado.getContrasena().isBlank()) {
            throw new IllegalArgumentException("La contrasena es obligatoria");
        }
        if (empleado.getContrasena().length() < 6) {
            throw new IllegalArgumentException("La contrasena debe tener al menos 6 caracteres");
        }

        if (empleado.getTelefono() != null) {
            empleado.setTelefono(empleado.getTelefono().trim());
        }
    }
}
