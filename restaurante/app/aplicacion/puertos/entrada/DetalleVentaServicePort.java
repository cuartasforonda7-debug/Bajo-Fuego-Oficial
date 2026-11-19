package restaurante.app.aplicacion.puertos.entrada;
import restaurante.app.dominio.entidades.DetalleVenta; import java.util.*;
public interface DetalleVentaServicePort { void validar(DetalleVenta detalle); DetalleVenta crear(DetalleVenta detalle); List<DetalleVenta> consultarPorVenta(Long idVenta); }
