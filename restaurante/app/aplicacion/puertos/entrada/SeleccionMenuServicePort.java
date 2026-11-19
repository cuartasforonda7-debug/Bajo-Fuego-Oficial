package restaurante.app.aplicacion.puertos.entrada;
import restaurante.app.dominio.entidades.SeleccionMenu; import java.util.*;
public interface SeleccionMenuServicePort { void validar(SeleccionMenu seleccion); SeleccionMenu crear(SeleccionMenu seleccion); List<SeleccionMenu> consultarPorDetallePedido(Long id); List<SeleccionMenu> consultarPorDetalleVenta(Long id); }
