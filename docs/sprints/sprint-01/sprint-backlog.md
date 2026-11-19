# Sprint 1 — Sprint Backlog

## Proyecto

Restaurante de Mar

## Sprint

Sprint 1

## Objetivo del Sprint

Construir la base inicial del sistema de gestión del restaurante y desarrollar las primeras funcionalidades correspondientes a usuarios, productos, pedidos y pagos.

El Sprint busca establecer una primera base funcional sobre la cual continuar desarrollando los demás módulos del sistema.

---

# Historias seleccionadas

## HU-01 — Registrar usuario

**Responsable:** Juan José

Como usuario del sistema, quiero registrarme, para poder acceder a las funcionalidades correspondientes a mi cuenta.

### Tareas

- [x] Definir los datos necesarios del usuario.
- [x] Crear la entidad correspondiente.
- [x] Implementar el registro.
- [x] Validar los datos.
- [x] Probar el registro.

---

## HU-02 — Iniciar sesión

**Responsable:** Juan José

Como usuario registrado, quiero iniciar sesión, para acceder al sistema.

### Tareas

- [x] Definir el puerto de entrada para autenticación.
- [x] Validar las credenciales del usuario.
- [x] Permitir acceso si las credenciales son correctas.
- [x] Notificar en caso de credenciales incorrectas o usuario inactivo.
- [x] Probar el inicio de sesión.

---

## HU-03 — Gestionar empleados y roles

**Responsable:** Juan José

Como administrador, quiero gestionar empleados y sus roles, para organizar el acceso y las responsabilidades dentro del sistema.

### Tareas

- [x] Definir puertos de entrada y salida para roles y empleados.
- [x] Implementar registro de empleados y validación de correo único.
- [x] Implementar consulta de empleados.
- [x] Implementar asignación de roles validando su existencia.
- [x] Probar la gestión y asignación de roles.

---

## HU-04 — Controlar permisos según el rol

**Responsable:** Juan José

Como administrador, quiero controlar los permisos según el rol, para limitar el acceso a las funcionalidades correspondientes.

### Tareas

- [x] Definir catálogo de permisos granulares (`TipoPermiso`).
- [x] Asociar roles a usuarios y permisos a roles.
- [x] Implementar servicio de control de acceso y autorización.
- [x] Denegar acceso a funciones no autorizadas lanzando excepciones de seguridad.
- [x] Probar el control de accesos por roles.

---

## HU-05 — Registrar productos

**Responsable:** Cristian

Como empleado autorizado, quiero registrar productos, para mantener actualizado el catálogo del restaurante.

### Tareas

- [x] Definir los datos del producto.
- [x] Crear o ajustar la entidad Producto.
- [x] Implementar el registro del producto.
- [x] Validar los datos.
- [x] Probar el registro.

---

## HU-11 — Registrar pedido

**Responsable:** Darwin

Como empleado, quiero registrar un pedido, para llevar control de los productos solicitados por el cliente.

### Tareas

- [ ] Definir los datos del pedido.
- [ ] Crear o ajustar la entidad Pedido.
- [ ] Definir la relación con los productos.
- [ ] Implementar el registro del pedido.
- [ ] Probar el registro.

---

## HU-16 — Registrar pago

**Responsable:** Ricardo

Como empleado, quiero registrar el pago de una venta, para dejar constancia de la transacción realizada.

### Tareas

- [x] Definir los datos del pago.
- [x] Crear o ajustar la entidad Pago.
- [x] Definir la relación con la venta.
- [x] Implementar el registro del pago.
- [x] Probar el registro.

---

# Trabajo transversal

**Responsables:** Todo el equipo

### Tareas

- [x] Revisar la estructura del proyecto.
- [ ] Mantener actualizado el Product Backlog.
- [x] Mantener actualizado el Sprint Backlog.
- [x] Trabajar mediante ramas de Git.
- [x] Realizar commits claros y relacionados con cambios concretos.
- [ ] Integrar los cambios del equipo.
- [x] Probar el funcionamiento conjunto.
- [x] Registrar los avances del Sprint.

---

# Criterios generales de finalización

Una historia se considerará completada cuando:

- [x] La funcionalidad esté implementada.
- [x] Los datos sean validados correctamente.
- [x] La funcionalidad haya sido probada.
- [x] Los cambios estén registrados mediante Git.
- [ ] El código esté integrado correctamente.
- [x] La historia cumpla sus criterios de aceptación.

---

# Estado del Sprint

| Historia | Responsable | Estado |
|---|---|---|
| HU-01 | Juan José | Done |
| HU-02 | Juan José | Done |
| HU-03 | Juan José | Done |
| HU-04 | Juan José | Done |
| HU-05 | Cristian | Casi Done |
| HU-11 | Darwin | To Do |
| HU-16 | Ricardo | To Do |

---

# Resultado esperado

Al finalizar el Sprint se espera contar con una primera base funcional de los módulos principales del sistema y con una estructura preparada para continuar el desarrollo durante los siguientes Sprints.
