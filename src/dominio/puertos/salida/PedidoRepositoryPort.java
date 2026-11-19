package restaurante.app.dominio.puertos.salida;
import restaurante.app.dominio.entidades.Pedido;
import java.util.List;
import java.sql.SQLException;
public interface PedidoRepositoryPort { Pedido crear(Pedido pedido) throws SQLException; Pedido findById(Long id) throws SQLException; List<Pedido> listarTodos() throws SQLException; void actualizarEstado(Long id,String estado) throws SQLException; void cancelar(Long id) throws SQLException; }
