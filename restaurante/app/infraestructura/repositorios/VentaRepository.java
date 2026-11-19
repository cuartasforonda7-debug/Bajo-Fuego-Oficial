package restaurante.app.infraestructura.repositorios;

import restaurante.app.dominio.entidades.Venta;
import restaurante.app.infraestructura.basedatos.ConexionBaseDatos;
import java.sql.*;
import java.util.*;

public class VentaRepository implements restaurante.app.dominio.puertos.salida.VentaRepositoryPort {
    public Venta crear(Venta v) throws SQLException { try(Connection c=ConexionBaseDatos.getConnection();PreparedStatement ps=c.prepareStatement("INSERT INTO venta(id_pedido,metodo_pago,estado_pago,total) VALUES(?,?,?,?)",Statement.RETURN_GENERATED_KEYS)){if(v.getIdPedido()==null)ps.setNull(1,Types.BIGINT);else ps.setLong(1,v.getIdPedido());ps.setString(2,v.getMetodoPago());ps.setString(3,v.getEstadoPago()==null?"PENDIENTE":v.getEstadoPago());ps.setBigDecimal(4,v.getTotal());ps.executeUpdate();try(ResultSet k=ps.getGeneratedKeys()){if(k.next())v.setId(k.getLong(1));}}return v; }
    public Venta findById(Long id)throws SQLException{try(Connection c=ConexionBaseDatos.getConnection();PreparedStatement ps=c.prepareStatement("SELECT * FROM venta WHERE id=?")){ps.setLong(1,id);try(ResultSet r=ps.executeQuery()){return r.next()?map(r):null;}}}
    public List<Venta> listarTodas()throws SQLException{List<Venta>out=new ArrayList<>();try(Connection c=ConexionBaseDatos.getConnection();Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT * FROM venta ORDER BY id")){while(r.next())out.add(map(r));}return out;}
    public boolean existeVentaPorPedido(Long idPedido)throws SQLException{return findByIdPedido(idPedido)!=null;}
    public Venta findByIdPedido(Long idPedido)throws SQLException{try(Connection c=ConexionBaseDatos.getConnection();PreparedStatement ps=c.prepareStatement("SELECT * FROM venta WHERE id_pedido=?")){ps.setLong(1,idPedido);try(ResultSet r=ps.executeQuery()){return r.next()?map(r):null;}}}
    private Venta map(ResultSet r)throws SQLException{Venta v=new Venta();v.setId(r.getLong("id"));v.setIdPedido((Long)r.getObject("id_pedido"));v.setMetodoPago(r.getString("metodo_pago"));v.setEstadoPago(r.getString("estado_pago"));v.setTotal(r.getBigDecimal("total"));Timestamp t=r.getTimestamp("fecha_venta");if(t!=null)v.setFechaVenta(t.toLocalDateTime());return v;}
}
