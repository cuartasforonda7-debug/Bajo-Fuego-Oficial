package dominio.puertos.entrada;

import java.util.List;
import java.util.Optional;
import dominio.entidades.Empleado;

public interface GestionarEmpleadoPort {

    void registrarEmpleado(Empleado empleado);

    List<Empleado> consultarEmpleados();

    Optional<Empleado> buscarPorId(int idEmpleado);

    void asignarRol(int idEmpleado, int idRol);
}
