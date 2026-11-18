package restaurante.app.aplicacion.servicios;

import restaurante.app.dominio.entidades.*;
import restaurante.app.infraestructura.basedatos.ConexionBaseDatos;
import restaurante.app.infraestructura.repositorios.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class VentaService {
    private final VentaRepository ventaRepository=new VentaRepository();
    private final DetalleVentaService detalleVentaService=new DetalleVentaService();
    private final SeleccionMenuService seleccionMenuService=new SeleccionMenuService();
    private final PedidoService pedidoService=new PedidoService();
    private final DetallePedidoService detallePedidoService=new DetallePedidoService();

    public Venta convertirPedidoAVenta(Long idPedido,String metodoPago,String estadoPago) {
        Pedido pedido=pedidoService.consultar(idPedido);
        if("CANCELADO".equalsIgnoreCase(pedido.getEstado())) throw new NegocioException("No se puede vender un pedido CANCELADO");
        try { if(ventaRepository.existeVentaPorPedido(idPedido)) throw new NegocioException("Ya existe una venta para este pedido"); } catch(SQLException e){throw new NegocioException(e.getMessage());}
        Connection c=null;
        try {
            c=ConexionBaseDatos.getConnection(); c.setAutoCommit(false);
            Venta venta=new Venta(idPedido,metodoPago,estadoPago==null?"CONFIRMADO":estadoPago,BigDecimal.ZERO);
            try(PreparedStatement ps=c.prepareStatement("INSERT INTO venta(id_pedido,metodo_pago,estado_pago,total) VALUES(?,?,?,?)",Statement.RETURN_GENERATED_KEYS)){if(idPedido==null)ps.setNull(1,Types.BIGINT);else ps.setLong(1,idPedido);ps.setString(2,metodoPago);ps.setString(3,venta.getEstadoPago());ps.setBigDecimal(4,BigDecimal.ZERO);ps.executeUpdate();try(ResultSet k=ps.getGeneratedKeys()){if(k.next())venta.setId(k.getLong(1));}}
            BigDecimal total=BigDecimal.ZERO;
            for(DetallePedido origen:detallePedidoService.consultarPorPedido(idPedido)){
                DetalleVenta dv=new DetalleVenta(venta.getId(),origen.getIdProducto(),origen.getIdMenu(),origen.getCantidad(),origen.getPrecioUnitario()); detalleVentaService.validar(dv);
                try(PreparedStatement ps=c.prepareStatement("INSERT INTO detalle_venta(id_venta,id_producto,id_menu,cantidad,precio_unitario,subtotal) VALUES(?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS)){ps.setLong(1,venta.getId());setOptional(ps,2,dv.getIdProducto());setOptional(ps,3,dv.getIdMenu());ps.setInt(4,dv.getCantidad());ps.setBigDecimal(5,dv.getPrecioUnitario());ps.setBigDecimal(6,dv.getSubtotal());ps.executeUpdate();try(ResultSet k=ps.getGeneratedKeys()){if(k.next())dv.setId(k.getLong(1));}}
                total=total.add(dv.getSubtotal());
                for(SeleccionMenu original:seleccionMenuService.consultarPorDetallePedido(origen.getId())){SeleccionMenu clon=new SeleccionMenu(null,dv.getId(),original.getIdMenu(),original.getIdProducto(),original.getDescripcion());clon.setCantidad(original.getCantidad());seleccionMenuService.validar(clon);insertarSeleccion(c,clon);}
            }
            if(total.signum()==0)throw new NegocioException("El pedido no contiene detalles para convertir");
            venta.setTotal(total);try(PreparedStatement ps=c.prepareStatement("UPDATE venta SET total=? WHERE id=?")){ps.setBigDecimal(1,total);ps.setLong(2,venta.getId());ps.executeUpdate();}
            c.commit();return venta;
        } catch(NegocioException e){rollback(c);throw e;} catch(SQLException|RuntimeException e){rollback(c);throw new NegocioException("Error al convertir pedido: "+e.getMessage());} finally{close(c);} 
    }

    public Venta crearVentaDirecta(Venta venta,List<DetalleVenta> detalles,Map<Long,List<SeleccionMenu>> selecciones){
        if(venta==null||detalles==null||detalles.isEmpty())throw new NegocioException("Una venta directa requiere venta y al menos un detalle");venta.setIdPedido(null);venta.setEstadoPago(venta.getEstadoPago()==null?"CONFIRMADO":venta.getEstadoPago());
        Connection c=null;try{c=ConexionBaseDatos.getConnection();c.setAutoCommit(false);BigDecimal total=BigDecimal.ZERO;venta.setTotal(BigDecimal.ZERO);try(PreparedStatement ps=c.prepareStatement("INSERT INTO venta(id_pedido,metodo_pago,estado_pago,total) VALUES(NULL,?,?,?)",Statement.RETURN_GENERATED_KEYS)){ps.setString(1,venta.getMetodoPago());ps.setString(2,venta.getEstadoPago());ps.setBigDecimal(3,BigDecimal.ZERO);ps.executeUpdate();try(ResultSet k=ps.getGeneratedKeys()){if(k.next())venta.setId(k.getLong(1));}}
            for(DetalleVenta d:detalles){d.setIdVenta(venta.getId());detalleVentaService.validar(d);try(PreparedStatement ps=c.prepareStatement("INSERT INTO detalle_venta(id_venta,id_producto,id_menu,cantidad,precio_unitario,subtotal) VALUES(?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS)){ps.setLong(1,venta.getId());setOptional(ps,2,d.getIdProducto());setOptional(ps,3,d.getIdMenu());ps.setInt(4,d.getCantidad());ps.setBigDecimal(5,d.getPrecioUnitario());ps.setBigDecimal(6,d.getSubtotal());ps.executeUpdate();try(ResultSet k=ps.getGeneratedKeys()){if(k.next())d.setId(k.getLong(1));}}total=total.add(d.getSubtotal());if(d.getSelecciones()!=null)for(SeleccionMenu s:d.getSelecciones()){s.setIdDetalleVenta(d.getId());s.setIdDetallePedido(null);seleccionMenuService.validar(s);insertarSeleccion(c,s);}if(selecciones!=null)for(SeleccionMenu s:selecciones.getOrDefault(d.getId(),Collections.emptyList())){s.setIdDetalleVenta(d.getId());s.setIdDetallePedido(null);seleccionMenuService.validar(s);insertarSeleccion(c,s);}}
            venta.setTotal(total);try(PreparedStatement ps=c.prepareStatement("UPDATE venta SET total=? WHERE id=?")){ps.setBigDecimal(1,total);ps.setLong(2,venta.getId());ps.executeUpdate();}c.commit();return venta;
        }catch(NegocioException e){rollback(c);throw e;}catch(SQLException|RuntimeException e){rollback(c);throw new NegocioException("Error al crear venta directa: "+e.getMessage());}finally{close(c);} 
    }
    public Venta listarConsultar(Long id){try{Venta v=ventaRepository.findById(id);if(v==null)throw new NegocioException("Venta no encontrada");List<DetalleVenta> detalles=new DetalleVentaRepository().consultarPorIdVenta(id);for(DetalleVenta d:detalles)d.setSelecciones(new SeleccionMenuRepository().consultarPorIdDetalleVenta(d.getId()));v.setDetalles(detalles);return v;}catch(SQLException e){throw new NegocioException(e.getMessage());}}
    public List<Venta> listar(){try{return ventaRepository.listarTodas();}catch(SQLException e){throw new NegocioException(e.getMessage());}}

    private void insertarSeleccion(Connection c,SeleccionMenu s)throws SQLException{try(PreparedStatement ps=c.prepareStatement("INSERT INTO seleccion_menu(id_detalle_pedido,id_detalle_venta,id_menu,id_producto,descripcion,cantidad) VALUES(?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS)){setOptional(ps,1,s.getIdDetallePedido());setOptional(ps,2,s.getIdDetalleVenta());setOptional(ps,3,s.getIdMenu());setOptional(ps,4,s.getIdProducto());ps.setString(5,s.getDescripcion());ps.setInt(6,s.getCantidad()==null?1:s.getCantidad());ps.executeUpdate();try(ResultSet k=ps.getGeneratedKeys()){if(k.next())s.setId(k.getLong(1));}}}
    private void setOptional(PreparedStatement ps,int i,Long v)throws SQLException{if(v==null)ps.setNull(i,Types.BIGINT);else ps.setLong(i,v);}
    private void rollback(Connection c){if(c!=null)try{c.rollback();}catch(SQLException ignored){}}
    private void close(Connection c){if(c!=null)try{c.close();}catch(SQLException ignored){}}
}
