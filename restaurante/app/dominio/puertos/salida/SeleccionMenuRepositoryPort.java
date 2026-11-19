package restaurante.app.dominio.puertos.salida;
import restaurante.app.dominio.entidades.SeleccionMenu;
import java.util.List;
import java.sql.SQLException;
public interface SeleccionMenuRepositoryPort { SeleccionMenu crear(SeleccionMenu seleccion) throws SQLException; List<SeleccionMenu> consultarPorIdDetallePedido(Long idDetallePedido) throws SQLException; List<SeleccionMenu> consultarPorIdDetalleVenta(Long idDetalleVenta) throws SQLException; }
