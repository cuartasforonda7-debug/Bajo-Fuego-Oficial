package restaurante.app.infraestructura.repositorios;

import restaurante.app.dominio.entidades.DetallePedido;
import restaurante.app.infraestructura.basedatos.ConexionBaseDatos;
import java.sql.*;
import java.util.*;

public class DetallePedidoRepository implements restaurante.app.dominio.puertos.salida.DetallePedidoRepositoryPort {
    public DetallePedido crear(DetallePedido d) throws SQLException { try(Connection c=ConexionBaseDatos.getConnection(); PreparedStatement ps=c.prepareStatement("INSERT INTO detalle_pedido(id_pedido,id_producto,id_menu,cantidad,precio_unitario) VALUES(?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS)){ps.setLong(1,d.getIdPedido());if(d.getIdProducto()==null)ps.setNull(2,Types.BIGINT);else ps.setLong(2,d.getIdProducto());if(d.getIdMenu()==null)ps.setNull(3,Types.BIGINT);else ps.setLong(3,d.getIdMenu());ps.setInt(4,d.getCantidad());ps.setBigDecimal(5,d.getPrecioUnitario());ps.executeUpdate();try(ResultSet k=ps.getGeneratedKeys()){if(k.next())d.setId(k.getLong(1));}}return d; }
    public List<DetallePedido> consultarPorIdPedido(Long idPedido) throws SQLException { List<DetallePedido> out=new ArrayList<>();try(Connection c=ConexionBaseDatos.getConnection();PreparedStatement ps=c.prepareStatement("SELECT * FROM detalle_pedido WHERE id_pedido=?")){ps.setLong(1,idPedido);try(ResultSet r=ps.executeQuery()){while(r.next()){DetallePedido d=new DetallePedido();d.setId(r.getLong("id"));d.setIdPedido(r.getLong("id_pedido"));d.setIdProducto((Long)r.getObject("id_producto"));d.setIdMenu((Long)r.getObject("id_menu"));d.setCantidad(r.getInt("cantidad"));d.setPrecioUnitario(r.getBigDecimal("precio_unitario"));out.add(d);}}}return out; }
}
