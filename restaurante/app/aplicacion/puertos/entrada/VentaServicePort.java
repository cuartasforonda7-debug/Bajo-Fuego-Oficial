package restaurante.app.aplicacion.puertos.entrada;
import restaurante.app.dominio.entidades.*; import java.util.*;
public interface VentaServicePort { Venta convertirPedidoAVenta(Long idPedido,String metodoPago,String estadoPago); Venta crearVentaDirecta(Venta venta,List<DetalleVenta> detalles,Map<Long,List<SeleccionMenu>> selecciones); Venta listarConsultar(Long id); List<Venta> listar(); }
