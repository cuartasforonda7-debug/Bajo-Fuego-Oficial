package restaurante.app.aplicacion.puertos.entrada;
import restaurante.app.dominio.entidades.DetallePedido; import java.util.*;
public interface DetallePedidoServicePort { void validar(DetallePedido detalle); DetallePedido crear(DetallePedido detalle); List<DetallePedido> consultarPorPedido(Long idPedido); }
