package restaurante.app.dominio.puertos.salida;
import restaurante.app.dominio.entidades.DetalleVenta;
import java.util.List;
import java.sql.SQLException;
public interface DetalleVentaRepositoryPort { DetalleVenta crear(DetalleVenta detalle) throws SQLException; List<DetalleVenta> consultarPorIdVenta(Long idVenta) throws SQLException; }
