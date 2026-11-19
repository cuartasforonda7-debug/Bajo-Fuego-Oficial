package restaurante.app.dominio.puertos.salida;
import restaurante.app.dominio.entidades.DetallePedido;
import java.util.List;
import java.sql.SQLException;
public interface DetallePedidoRepositoryPort { DetallePedido crear(DetallePedido detalle) throws SQLException; List<DetallePedido> consultarPorIdPedido(Long idPedido) throws SQLException; }
