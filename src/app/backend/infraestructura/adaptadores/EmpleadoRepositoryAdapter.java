package app.backend.infraestructura.adaptadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import app.backend.infraestructura.basedatos.ConexionBD;
import app.backend.infraestructura.excepciones.InfraestructuraException;
import dominio.entidades.Empleado;
import dominio.entidades.Rol;
import dominio.puertos.salida.EmpleadoRepositoryPort;

public class EmpleadoRepositoryAdapter implements EmpleadoRepositoryPort {

    @Override
    public void registrarEmpleado(Empleado empleado) {
        String sqlUsuario = """
            INSERT INTO Usuarios (nombre, email, telefono, contrasena, estado, fechaRegistro, idRol)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
        String sqlEmpleado = """
            INSERT INTO Empleados (idUsuario, fechaIngreso)
            VALUES (?, ?)
            """;

        Connection conexion = null;
        try {
            conexion = ConexionBD.obtenerConexion();
            conexion.setAutoCommit(false);

            int idGenerado = 0;
            try (PreparedStatement stmtUsuario = conexion.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS)) {
                stmtUsuario.setString(1, empleado.getNombre());
                stmtUsuario.setString(2, empleado.getEmail());
                stmtUsuario.setString(3, empleado.getTelefono());
                stmtUsuario.setString(4, empleado.getContrasena());
                stmtUsuario.setBoolean(5, empleado.isEstado());

                LocalDateTime fechaReg = empleado.getFechaRegistro() != null ? empleado.getFechaRegistro() : LocalDateTime.now();
                stmtUsuario.setTimestamp(6, Timestamp.valueOf(fechaReg));

                if (empleado.getRol() != null && empleado.getRol().getIdRol() > 0) {
                    stmtUsuario.setInt(7, empleado.getRol().getIdRol());
                } else {
                    stmtUsuario.setNull(7, Types.INTEGER);
                }

                stmtUsuario.executeUpdate();

                try (ResultSet keys = stmtUsuario.getGeneratedKeys()) {
                    if (keys.next()) {
                        idGenerado = keys.getInt(1);
                        empleado.setIdUsuario(idGenerado);
                    }
                }
            }

            try (PreparedStatement stmtEmpleado = conexion.prepareStatement(sqlEmpleado)) {
                stmtEmpleado.setInt(1, idGenerado > 0 ? idGenerado : empleado.getIdUsuario());
                LocalDateTime fechaIng = empleado.getFechaIngreso() != null ? empleado.getFechaIngreso() : LocalDateTime.now();
                stmtEmpleado.setTimestamp(2, Timestamp.valueOf(fechaIng));
                stmtEmpleado.executeUpdate();
            }

            conexion.commit();
        } catch (SQLException e) {
            if (conexion != null) {
                try {
                    conexion.rollback();
                } catch (SQLException rollbackEx) {
                    // Ignore rollback exception
                }
            }
            throw new InfraestructuraException("Error al registrar el empleado", e);
        } finally {
            if (conexion != null) {
                try {
                    conexion.setAutoCommit(true);
                    conexion.close();
                } catch (SQLException closeEx) {
                    // Ignore close exception
                }
            }
        }
    }

    @Override
    public List<Empleado> consultarEmpleados() {
        String sql = """
            SELECT u.idUsuario, u.nombre, u.email, u.telefono, u.contrasena, u.estado, u.fechaRegistro,
                   u.idRol, r.nombre AS nombreRol, r.descripcion AS descripcionRol,
                   e.fechaIngreso
            FROM Empleados e
            INNER JOIN Usuarios u ON e.idUsuario = u.idUsuario
            LEFT JOIN Roles r ON u.idRol = r.idRol
            """;

        List<Empleado> empleados = new ArrayList<>();
        try (
            Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()
        ) {
            while (rs.next()) {
                empleados.add(mapearEmpleado(rs));
            }
        } catch (SQLException e) {
            throw new InfraestructuraException("Error al consultar empleados", e);
        }
        return empleados;
    }

    @Override
    public Optional<Empleado> buscarPorId(int idEmpleado) {
        String sql = """
            SELECT u.idUsuario, u.nombre, u.email, u.telefono, u.contrasena, u.estado, u.fechaRegistro,
                   u.idRol, r.nombre AS nombreRol, r.descripcion AS descripcionRol,
                   e.fechaIngreso
            FROM Empleados e
            INNER JOIN Usuarios u ON e.idUsuario = u.idUsuario
            LEFT JOIN Roles r ON u.idRol = r.idRol
            WHERE e.idUsuario = ?
            """;

        try (
            Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement statement = conexion.prepareStatement(sql)
        ) {
            statement.setInt(1, idEmpleado);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearEmpleado(rs));
                }
            }
        } catch (SQLException e) {
            throw new InfraestructuraException("Error al buscar empleado por id", e);
        }
        return Optional.empty();
    }

    @Override
    public void actualizarRol(int idEmpleado, Rol rol) {
        String sql = "UPDATE Usuarios SET idRol = ? WHERE idUsuario = ?";

        try (
            Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement statement = conexion.prepareStatement(sql)
        ) {
            statement.setInt(1, rol.getIdRol());
            statement.setInt(2, idEmpleado);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new InfraestructuraException("Error al actualizar el rol del empleado", e);
        }
    }

    @Override
    public boolean existePorEmail(String email) {
        String sql = "SELECT COUNT(1) FROM Usuarios WHERE email = ?";

        try (
            Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement statement = conexion.prepareStatement(sql)
        ) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new InfraestructuraException("Error al verificar existencia de correo", e);
        }
        return false;
    }

    private Empleado mapearEmpleado(ResultSet rs) throws SQLException {
        Empleado empleado = new Empleado();
        empleado.setIdUsuario(rs.getInt("idUsuario"));
        empleado.setNombre(rs.getString("nombre"));
        empleado.setEmail(rs.getString("email"));
        empleado.setTelefono(rs.getString("telefono"));
        empleado.setContrasena(rs.getString("contrasena"));
        empleado.setEstado(rs.getBoolean("estado"));

        Timestamp tsRegistro = rs.getTimestamp("fechaRegistro");
        if (tsRegistro != null) {
            empleado.setFechaRegistro(tsRegistro.toLocalDateTime());
        }

        Timestamp tsIngreso = rs.getTimestamp("fechaIngreso");
        if (tsIngreso != null) {
            empleado.setFechaIngreso(tsIngreso.toLocalDateTime());
        }

        int idRol = rs.getInt("idRol");
        if (!rs.wasNull() && idRol > 0) {
            Rol rol = new Rol();
            rol.setIdRol(idRol);
            rol.setNombre(rs.getString("nombreRol"));
            rol.setDescripcion(rs.getString("descripcionRol"));
            empleado.setRol(rol);
        }

        return empleado;
    }
}
