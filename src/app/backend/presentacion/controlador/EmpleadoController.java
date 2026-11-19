package app.backend.presentacion.controlador;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import dominio.entidades.Empleado;
import dominio.puertos.entrada.GestionarEmpleadoPort;

public class EmpleadoController {

    private final GestionarEmpleadoPort gestionarEmpleadoPort;

    public EmpleadoController(GestionarEmpleadoPort gestionarEmpleadoPort) {
        this.gestionarEmpleadoPort = Objects.requireNonNull(gestionarEmpleadoPort, "El puerto de gestion de empleados es obligatorio");
    }

    public void registrarEmpleado(Empleado empleado) {
        gestionarEmpleadoPort.registrarEmpleado(empleado);
    }

    public List<Empleado> consultarEmpleados() {
        return gestionarEmpleadoPort.consultarEmpleados();
    }

    public Optional<Empleado> buscarPorId(int idEmpleado) {
        return gestionarEmpleadoPort.buscarPorId(idEmpleado);
    }

    public void asignarRol(int idEmpleado, int idRol) {
        gestionarEmpleadoPort.asignarRol(idEmpleado, idRol);
    }
}
