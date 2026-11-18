package restaurante.app.infraestructura.repositorios;

import restaurante.app.dominio.entidades.DetalleVenta;
import restaurante.app.infraestructura.basedatos.ConexionBaseDatos;
import java.sql.*;
import java.util.*;

public class DetalleVentaRepository {
    public DetalleVenta crear(DetalleVenta d)throws SQLException { try(Connection c=ConexionBaseDatos.getConnection();PreparedStatement ps=c.prepareStatement("INSERT INTO detalle_venta(id_venta,id_producto,id_menu,cantidad,precio_unitario,subtotal) VALUES(?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS)){ps.setLong(1,d.getIdVenta());if(d.getIdProducto()==null)ps.setNull(2,Types.BIGINT);else ps.setLong(2,d.getIdProducto());if(d.getIdMenu()==null)ps.setNull(3,Types.BIGINT);else ps.setLong(3,d.getIdMenu());ps.setInt(4,d.getCantidad());ps.setBigDecimal(5,d.getPrecioUnitario());ps.setBigDecimal(6,d.getSubtotal());ps.executeUpdate();try(ResultSet k=ps.getGeneratedKeys()){if(k.next())d.setId(k.getLong(1));}}return d; }
    public List<DetalleVenta> consultarPorIdVenta(Long idVenta)throws SQLException{List<DetalleVenta>out=new ArrayList<>();try(Connection c=ConexionBaseDatos.getConnection();PreparedStatement ps=c.prepareStatement("SELECT * FROM detalle_venta WHERE id_venta=?")){ps.setLong(1,idVenta);try(ResultSet r=ps.executeQuery()){while(r.next()){DetalleVenta d=new DetalleVenta();d.setId(r.getLong("id"));d.setIdVenta(r.getLong("id_venta"));d.setIdProducto((Long)r.getObject("id_producto"));d.setIdMenu((Long)r.getObject("id_menu"));d.setCantidad(r.getInt("cantidad"));d.setPrecioUnitario(r.getBigDecimal("precio_unitario"));d.setSubtotal(r.getBigDecimal("subtotal"));out.add(d);}}}return out;}
}
