package app.backend.infraestructura.adaptadores;

import app.backend.infraestructura.basedatos.ConexionBD;
import app.backend.infraestructura.excepciones.InfraestructuraException;
import dominio.entidades.Usuario;
import dominio.entidades.Rol;
import dominio.puertos.salida.UsuarioRepositoryPort;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.Optional;

public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    @Override
    public void registrarUsuario(Usuario usuario) {
        String sql = """
            INSERT INTO Usuarios
            (nombre, email, telefono, contrasena, estado, fechaRegistro, idRol)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (
            Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement statement = conexion.prepareStatement(sql)
        ) {
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getEmail());
            statement.setString(3, usuario.getTelefono());
            statement.setString(4, usuario.getContrasena());
            statement.setBoolean(5, usuario.isEstado());
            
            LocalDateTime fecha = usuario.getFechaRegistro() != null ? usuario.getFechaRegistro() : LocalDateTime.now();
            statement.setTimestamp(6, Timestamp.valueOf(fecha));

            if (usuario.getRol() != null && usuario.getRol().getIdRol() > 0) {
                statement.setInt(7, usuario.getRol().getIdRol());
            } else {
                statement.setNull(7, Types.INTEGER);
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new InfraestructuraException("Error al registrar el usuario en base de datos", e);
        }
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        String sql = """
            SELECT idUsuario, nombre, email, telefono, contrasena, estado, fechaRegistro, idRol
            FROM Usuarios
            WHERE email = ?
            """;

        try (
            Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement statement = conexion.prepareStatement(sql)
        ) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearUsuario(rs));
                }
            }
        } catch (SQLException e) {
            throw new InfraestructuraException("Error al consultar usuario por email", e);
        }
        return Optional.empty();
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
            throw new InfraestructuraException("Error al verificar existencia de usuario por email", e);
        }
        return false;
    }

    @Override
    public Optional<Usuario> buscarPorId(int idUsuario) {
        String sql = """
            SELECT idUsuario, nombre, email, telefono, contrasena, estado, fechaRegistro, idRol
            FROM Usuarios
            WHERE idUsuario = ?
            """;

        try (
            Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement statement = conexion.prepareStatement(sql)
        ) {
            statement.setInt(1, idUsuario);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearUsuario(rs));
                }
            }
        } catch (SQLException e) {
            throw new InfraestructuraException("Error al consultar usuario por id", e);
        }
        return Optional.empty();
    }

    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("idUsuario"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setEmail(rs.getString("email"));
        usuario.setTelefono(rs.getString("telefono"));
        usuario.setContrasena(rs.getString("contrasena"));
        usuario.setEstado(rs.getBoolean("estado"));

        Timestamp timestamp = rs.getTimestamp("fechaRegistro");
        if (timestamp != null) {
            usuario.setFechaRegistro(timestamp.toLocalDateTime());
        }

        int idRol = rs.getInt("idRol");
        if (!rs.wasNull() && idRol > 0) {
            Rol rol = new Rol();
            rol.setIdRol(idRol);
            usuario.setRol(rol);
        }

        return usuario;
    }
}
