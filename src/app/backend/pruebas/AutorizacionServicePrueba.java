package app.backend.pruebas;

import java.util.Arrays;
import java.util.Collections;

import app.aplicacion.servicios.AutorizacionService;
import dominio.entidades.Rol;
import dominio.entidades.Usuario;
import dominio.enums.TipoPermiso;

public class AutorizacionServicePrueba {

    public static void main(String[] args) {
        AutorizacionService authService = new AutorizacionService();

        // Configuración de Roles con permisos diferenciados
        Rol rolAdmin = new Rol(1, "Administrador", "Acceso total", Arrays.asList(TipoPermiso.values()));
        Rol rolMesero = new Rol(2, "Mesero", "Atención a mesas", Arrays.asList(
                TipoPermiso.INICIAR_SESION,
                TipoPermiso.REGISTRAR_PEDIDO
        ));
        Rol rolCajero = new Rol(3, "Cajero", "Cobros y pagos", Arrays.asList(
                TipoPermiso.INICIAR_SESION,
                TipoPermiso.REGISTRAR_VENTA,
                TipoPermiso.REGISTRAR_PAGO
        ));

        // Usuarios para pruebas
        Usuario admin = new Usuario(1, "Admin General", "admin@restaurante.com", "3001112233", "pass123", true, null, rolAdmin);
        Usuario mesero = new Usuario(2, "Mesero Pedro", "pedro@restaurante.com", "3002223344", "pass123", true, null, rolMesero);
        Usuario cajero = new Usuario(3, "Cajera Maria", "maria@restaurante.com", "3003334455", "pass123", true, null, rolCajero);
        Usuario sinRol = new Usuario(4, "Usuario Sin Rol", "sinrol@restaurante.com", "3004445566", "pass123", true, null, null);
        Usuario inactivo = new Usuario(5, "Admin Inactivo", "inactivo@restaurante.com", "3005556677", "pass123", false, null, rolAdmin);

        // ==========================================
        // PRUEBAS HU-04: CONTROLAR PERMISOS SEGÚN EL ROL
        // ==========================================

        // Caso 1: Identificación correcta del rol
        verificar("Administrador".equals(authService.obtenerNombreRol(admin)), "Debe identificar el rol de Administrador");
        verificar("Mesero".equals(authService.obtenerNombreRol(mesero)), "Debe identificar el rol de Mesero");
        verificar("SIN_ROL".equals(authService.obtenerNombreRol(sinRol)), "Debe manejar usuarios sin rol");

        // Caso 2: Administrador tiene acceso a funcionalidades críticas
        verificar(authService.tienePermiso(admin, TipoPermiso.GESTIONAR_EMPLEADOS), "El admin debe poder gestionar empleados");
        verificar(authService.tienePermiso(admin, TipoPermiso.GESTIONAR_ROLES), "El admin debe poder gestionar roles");
        authService.validarAcceso(admin, TipoPermiso.GESTIONAR_EMPLEADOS); // No debe lanzar excepción

        // Caso 3: Control de acceso según el rol (Mesero puede registrar pedidos pero NO roles ni empleados)
        verificar(authService.tienePermiso(mesero, TipoPermiso.REGISTRAR_PEDIDO), "El mesero debe poder registrar pedidos");
        verificar(!authService.tienePermiso(mesero, TipoPermiso.GESTIONAR_EMPLEADOS), "El mesero NO debe gestionar empleados");
        verificar(!authService.tienePermiso(mesero, TipoPermiso.REGISTRAR_PAGO), "El mesero NO debe registrar pagos");

        // Caso 4: Excepción de seguridad al acceder a funcionalidad no autorizada
        esperarErrorSeguridad(() -> authService.validarAcceso(mesero, TipoPermiso.GESTIONAR_EMPLEADOS));
        esperarErrorSeguridad(() -> authService.validarAcceso(cajero, TipoPermiso.GESTIONAR_ROLES));

        // Caso 5: Excepción al intentar acceder sin rol asignado
        verificar(!authService.tienePermiso(sinRol, TipoPermiso.INICIAR_SESION), "Usuario sin rol no debe tener permisos");
        esperarErrorSeguridad(() -> authService.validarAcceso(sinRol, TipoPermiso.INICIAR_SESION));

        // Caso 6: Excepción para usuarios inactivos
        verificar(!authService.tienePermiso(inactivo, TipoPermiso.GESTIONAR_EMPLEADOS), "Usuario inactivo no debe tener permisos");
        esperarErrorSeguridad(() -> authService.validarAcceso(inactivo, TipoPermiso.GESTIONAR_EMPLEADOS));

        System.out.println("Pruebas HU-04 completadas correctamente");
    }

    private static void esperarErrorSeguridad(Runnable accion) {
        try {
            accion.run();
            throw new AssertionError("Se esperaba una excepcion de seguridad (SecurityException)");
        } catch (SecurityException esperado) {
            // Excepción de seguridad esperada
        }
    }

    private static void verificar(boolean condicion, String mensaje) {
        if (!condicion) {
            throw new AssertionError(mensaje);
        }
    }
}
