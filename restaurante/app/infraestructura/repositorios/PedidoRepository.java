package restaurante.app.infraestructura.repositorios;

import restaurante.app.dominio.entidades.Pedido;
import restaurante.app.infraestructura.basedatos.ConexionBaseDatos;
import java.sql.*;
import java.util.*;

public class PedidoRepository implements restaurante.app.dominio.puertos.salida.PedidoRepositoryPort {
    public Pedido crear(Pedido p) throws SQLException {
        try (Connection c=ConexionBaseDatos.getConnection(); PreparedStatement ps=c.prepareStatement("INSERT INTO pedido(cliente,estado) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1,p.getCliente()); ps.setString(2,p.getEstado()==null?"PENDIENTE":p.getEstado()); ps.executeUpdate();
            try(ResultSet k=ps.getGeneratedKeys()){ if(k.next()) p.setId(k.getLong(1)); }
        }
        return p;
    }
    public Pedido findById(Long id) throws SQLException { try(Connection c=ConexionBaseDatos.getConnection(); PreparedStatement ps=c.prepareStatement("SELECT * FROM pedido WHERE id=?")){ ps.setLong(1,id); try(ResultSet r=ps.executeQuery()){ return r.next()?map(r):null; } } }
    public Optional<Pedido> consultarPorId(Long id) throws SQLException { return Optional.ofNullable(findById(id)); }
    public List<Pedido> listarTodos() throws SQLException { List<Pedido> out=new ArrayList<>(); try(Connection c=ConexionBaseDatos.getConnection(); Statement s=c.createStatement(); ResultSet r=s.executeQuery("SELECT * FROM pedido ORDER BY id")){while(r.next())out.add(map(r));} return out; }
    public void actualizarEstado(Long id,String estado) throws SQLException { try(Connection c=ConexionBaseDatos.getConnection(); PreparedStatement ps=c.prepareStatement("UPDATE pedido SET estado=? WHERE id=?")){ps.setString(1,estado);ps.setLong(2,id);ps.executeUpdate();} }
    public void cancelar(Long id) throws SQLException { actualizarEstado(id,"CANCELADO"); }
    private Pedido map(ResultSet r) throws SQLException { Pedido p=new Pedido();p.setId(r.getLong("id"));p.setCliente(r.getString("cliente"));p.setEstado(r.getString("estado"));Timestamp t=r.getTimestamp("fecha_creacion");if(t!=null)p.setFechaCreacion(t.toLocalDateTime());return p; }
}
