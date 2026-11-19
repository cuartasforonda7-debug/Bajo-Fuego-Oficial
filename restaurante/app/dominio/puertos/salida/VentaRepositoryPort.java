package restaurante.app.dominio.puertos.salida;
import restaurante.app.dominio.entidades.Venta;
import java.util.List;
import java.sql.SQLException;
public interface VentaRepositoryPort { Venta crear(Venta venta) throws SQLException; Venta findById(Long id) throws SQLException; List<Venta> listarTodas() throws SQLException; boolean existeVentaPorPedido(Long idPedido) throws SQLException; Venta findByIdPedido(Long idPedido) throws SQLException; }
