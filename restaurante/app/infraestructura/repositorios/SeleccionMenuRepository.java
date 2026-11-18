package restaurante.app.infraestructura.repositorios;

import restaurante.app.dominio.entidades.SeleccionMenu;
import restaurante.app.infraestructura.basedatos.ConexionBaseDatos;
import java.sql.*;
import java.util.*;

public class SeleccionMenuRepository {
    public SeleccionMenu crear(SeleccionMenu s) throws SQLException { try(Connection c=ConexionBaseDatos.getConnection();PreparedStatement ps=c.prepareStatement("INSERT INTO seleccion_menu(id_detalle_pedido,id_detalle_venta,id_menu,id_producto,descripcion,cantidad) VALUES(?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS)){setId(ps,1,s.getIdDetallePedido());setId(ps,2,s.getIdDetalleVenta());setId(ps,3,s.getIdMenu());setId(ps,4,s.getIdProducto());ps.setString(5,s.getDescripcion());ps.setInt(6,s.getCantidad()==null?1:s.getCantidad());ps.executeUpdate();try(ResultSet k=ps.getGeneratedKeys()){if(k.next())s.setId(k.getLong(1));}}return s; }
    public List<SeleccionMenu> consultarPorIdDetallePedido(Long id) throws SQLException { return consultar("id_detalle_pedido",id); }
    public List<SeleccionMenu> consultarPorIdDetalleVenta(Long id) throws SQLException { return consultar("id_detalle_venta",id); }
    private List<SeleccionMenu> consultar(String col,long id)throws SQLException{List<SeleccionMenu>out=new ArrayList<>();try(Connection c=ConexionBaseDatos.getConnection();PreparedStatement ps=c.prepareStatement("SELECT * FROM seleccion_menu WHERE "+col+"=?")){ps.setLong(1,id);try(ResultSet r=ps.executeQuery()){while(r.next()){SeleccionMenu s=new SeleccionMenu();s.setId(r.getLong("id"));s.setIdDetallePedido((Long)r.getObject("id_detalle_pedido"));s.setIdDetalleVenta((Long)r.getObject("id_detalle_venta"));s.setIdMenu((Long)r.getObject("id_menu"));s.setIdProducto((Long)r.getObject("id_producto"));s.setDescripcion(r.getString("descripcion"));s.setCantidad(r.getInt("cantidad"));out.add(s);}}}return out;}
    private void setId(PreparedStatement ps,int i,Long v)throws SQLException{if(v==null)ps.setNull(i,Types.BIGINT);else ps.setLong(i,v);}
}
