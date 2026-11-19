package app.backend.infraestructura.adaptadores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import app.backend.infraestructura.basedatos.ConexionBD;
import app.backend.infraestructura.excepciones.InfraestructuraException;
import dominio.entidades.Rol;
import dominio.puertos.salida.RolRepositoryPort;

public class RolRepositoryAdapter implements RolRepositoryPort {

    @Override
    public void guardar(Rol rol) {
        String sql = "INSERT INTO Roles (nombre, descripcion) VALUES (?, ?)";

        try (
            Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement statement = conexion.prepareStatement(sql)
        ) {
            statement.setString(1, rol.getNombre());
            statement.setString(2, rol.getDescripcion());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new InfraestructuraException("Error al registrar el rol", e);
        }
    }

    @Override
    public List<Rol> consultarTodos() {
        String sql = "SELECT idRol, nombre, descripcion FROM Roles";
        List<Rol> roles = new ArrayList<>();

        try (
            Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement statement = conexion.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()
        ) {
            while (rs.next()) {
                roles.add(mapearRol(rs));
            }
        } catch (SQLException e) {
            throw new InfraestructuraException("Error al consultar roles", e);
        }
        return roles;
    }

    @Override
    public Optional<Rol> buscarPorId(int idRol) {
        String sql = "SELECT idRol, nombre, descripcion FROM Roles WHERE idRol = ?";

        try (
            Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement statement = conexion.prepareStatement(sql)
        ) {
            statement.setInt(1, idRol);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearRol(rs));
                }
            }
        } catch (SQLException e) {
            throw new InfraestructuraException("Error al buscar rol por id", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Rol> buscarPorNombre(String nombre) {
        String sql = "SELECT idRol, nombre, descripcion FROM Roles WHERE nombre = ?";

        try (
            Connection conexion = ConexionBD.obtenerConexion();
            PreparedStatement statement = conexion.prepareStatement(sql)
        ) {
            statement.setString(1, nombre);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapearRol(rs));
                }
            }
        } catch (SQLException e) {
            throw new InfraestructuraException("Error al buscar rol por nombre", e);
        }
        return Optional.empty();
    }

    private Rol mapearRol(ResultSet rs) throws SQLException {
        Rol rol = new Rol();
        rol.setIdRol(rs.getInt("idRol"));
        rol.setNombre(rs.getString("nombre"));
        rol.setDescripcion(rs.getString("descripcion"));
        return rol;
    }
}
