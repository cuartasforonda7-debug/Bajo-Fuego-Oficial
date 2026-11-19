package app.backend.pruebas;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import app.aplicacion.servicios.EmpleadoService;
import app.aplicacion.servicios.RolService;
import app.backend.presentacion.controlador.EmpleadoController;
import app.backend.presentacion.controlador.RolController;
import dominio.entidades.Empleado;
import dominio.entidades.Rol;
import dominio.puertos.salida.EmpleadoRepositoryPort;
import dominio.puertos.salida.RolRepositoryPort;

public class EmpleadoServicePrueba {

    public static void main(String[] args) {
        RolRepositoryEnMemoria rolRepo = new RolRepositoryEnMemoria();
        EmpleadoRepositoryEnMemoria empleadoRepo = new EmpleadoRepositoryEnMemoria();

        RolService rolService = new RolService(rolRepo);
        RolController rolController = new RolController(rolService);

        EmpleadoService empleadoService = new EmpleadoService(empleadoRepo, rolRepo);
        EmpleadoController empleadoController = new EmpleadoController(empleadoService);

        // ==========================================
        // PRUEBAS HU-03: GESTIONAR EMPLEADOS Y ROLES
        // ==========================================

        // Caso 1: Creación de roles
        Rol rolAdmin = new Rol(1, "Administrador", "Acceso total al sistema");
        Rol rolCajero = new Rol(2, "Cajero", "Gestion de ventas y pagos");
        rolController.crearRol(rolAdmin);
        rolController.crearRol(rolCajero);

        List<Rol> roles = rolController.consultarRoles();
        verificar(roles.size() == 2, "Deben existir 2 roles creados");

        // Caso 2: Registro de empleado a través de controlador
        Empleado emp = new Empleado();
        emp.setIdUsuario(10);
        emp.setNombre("  Carlos Rodriguez  ");
        emp.setEmail(" Carlos.Rodriguez@Restaurante.com ");
        emp.setContrasena("claveEmpleado123");
        emp.setTelefono("3112223344");
        emp.setRol(rolCajero);

        empleadoController.registrarEmpleado(emp);

        verificar(empleadoRepo.empleados.size() == 1, "El empleado debe estar persistido");
        verificar("Carlos Rodriguez".equals(emp.getNombre()), "El nombre debe normalizarse");
        verificar("carlos.rodriguez@restaurante.com".equals(emp.getEmail()), "El correo debe estar en minusculas");
        verificar(emp.isEstado(), "El empleado debe crearse con estado activo");
        verificar(emp.getFechaIngreso() != null, "La fecha de ingreso debe asignarse");
        verificar(emp.getRol().getIdRol() == 2, "El empleado debe tener el rol de Cajero asignado");

        // Caso 3: Consultar empleados
        List<Empleado> listaEmpleados = empleadoController.consultarEmpleados();
        verificar(listaEmpleados.size() == 1, "Debe retornar la lista con el empleado registrado");

        // Caso 4: Asignar un nuevo rol al empleado
        empleadoController.asignarRol(10, 1); // Cambiar a Administrador
        Empleado empActualizado = empleadoController.buscarPorId(10).orElseThrow();
        verificar(empActualizado.getRol().getIdRol() == 1, "El rol del empleado debe actualizarse a Administrador");
        verificar("Administrador".equals(empActualizado.getRol().getNombre()), "El nombre del nuevo rol debe ser Administrador");

        // Caso 5: Validaciones y errores
        // Correo duplicado
        Empleado empDuplicado = new Empleado();
        empDuplicado.setIdUsuario(20);
        empDuplicado.setNombre("Otro Empleado");
        empDuplicado.setEmail("carlos.rodriguez@restaurante.com");
        empDuplicado.setContrasena("clave12345");
        esperarError(() -> empleadoService.registrarEmpleado(empDuplicado));

        // Asignar rol inexistente
        esperarError(() -> empleadoService.asignarRol(10, 999));

        // Asignar rol a empleado inexistente
        esperarError(() -> empleadoService.asignarRol(999, 1));

        // Crear rol duplicado
        esperarError(() -> rolService.crearRol(new Rol(3, "Administrador", "Duplicado")));

        System.out.println("Pruebas HU-03 completadas correctamente");
    }

    private static void esperarError(Runnable accion) {
        try {
            accion.run();
            throw new AssertionError("Se esperaba una excepcion de validacion fallida");
        } catch (IllegalArgumentException esperado) {
            // Excepción esperada
        }
    }

    private static void verificar(boolean condicion, String mensaje) {
        if (!condicion) {
            throw new AssertionError(mensaje);
        }
    }

    private static class RolRepositoryEnMemoria implements RolRepositoryPort {
        private final List<Rol> roles = new ArrayList<>();

        @Override
        public void guardar(Rol rol) {
            roles.add(rol);
        }

        @Override
        public List<Rol> consultarTodos() {
            return new ArrayList<>(roles);
        }

        @Override
        public Optional<Rol> buscarPorId(int idRol) {
            return roles.stream().filter(r -> r.getIdRol() == idRol).findFirst();
        }

        @Override
        public Optional<Rol> buscarPorNombre(String nombre) {
            return roles.stream().filter(r -> r.getNombre().equalsIgnoreCase(nombre)).findFirst();
        }
    }

    private static class EmpleadoRepositoryEnMemoria implements EmpleadoRepositoryPort {
        private final List<Empleado> empleados = new ArrayList<>();

        @Override
        public void registrarEmpleado(Empleado empleado) {
            empleados.add(empleado);
        }

        @Override
        public List<Empleado> consultarEmpleados() {
            return new ArrayList<>(empleados);
        }

        @Override
        public Optional<Empleado> buscarPorId(int idEmpleado) {
            return empleados.stream().filter(e -> e.getIdUsuario() == idEmpleado).findFirst();
        }

        @Override
        public void actualizarRol(int idEmpleado, Rol rol) {
            buscarPorId(idEmpleado).ifPresent(e -> e.setRol(rol));
        }

        @Override
        public boolean existePorEmail(String email) {
            return empleados.stream().anyMatch(e -> e.getEmail().equalsIgnoreCase(email));
        }
    }
}
