package dominio.puertos.salida;

import java.util.List;
import java.util.Optional;
import dominio.entidades.Empleado;
import dominio.entidades.Rol;

public interface EmpleadoRepositoryPort {

    void registrarEmpleado(Empleado empleado);

    List<Empleado> consultarEmpleados();

    Optional<Empleado> buscarPorId(int idEmpleado);

    void actualizarRol(int idEmpleado, Rol rol);

    boolean existePorEmail(String email);
}
