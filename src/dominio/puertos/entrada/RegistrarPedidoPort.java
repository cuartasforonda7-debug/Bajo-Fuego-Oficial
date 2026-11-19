package restaurante.app.aplicacion.puertos.entrada;
import restaurante.app.dominio.entidades.*; import java.util.*;
public interface PedidoServicePort { Pedido crear(Pedido pedido,List<DetallePedido> detalles,Map<Long,List<SeleccionMenu>> selecciones); Pedido crear(Pedido pedido); List<Pedido> listar(); Pedido consultar(Long id); Pedido actualizarEstado(Long id,String nuevo); Pedido cancelar(Long id); }
