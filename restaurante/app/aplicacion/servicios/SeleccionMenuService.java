package restaurante.app.aplicacion.servicios;

import restaurante.app.dominio.entidades.SeleccionMenu;
import restaurante.app.aplicacion.puertos.entrada.SeleccionMenuServicePort;
import restaurante.app.dominio.puertos.salida.SeleccionMenuRepositoryPort;
import java.sql.SQLException;
import java.util.*;

public class SeleccionMenuService implements SeleccionMenuServicePort {
    public SeleccionMenuService(SeleccionMenuRepositoryPort repository) { this.repository = repository; }
    private final SeleccionMenuRepositoryPort repository;
    public void validar(SeleccionMenu s){boolean pedido=s!=null&&s.getIdDetallePedido()!=null&&s.getIdDetalleVenta()==null;boolean venta=s!=null&&s.getIdDetallePedido()==null&&s.getIdDetalleVenta()!=null;if(!pedido&&!venta)throw new NegocioException("La selección debe pertenecer exclusivamente a un DetallePedido o a un DetalleVenta");}
    public SeleccionMenu crear(SeleccionMenu s){validar(s);try{return repository.crear(s);}catch(SQLException e){throw new NegocioException(e.getMessage());}}
    public List<SeleccionMenu> consultarPorDetallePedido(Long id){try{return repository.consultarPorIdDetallePedido(id);}catch(SQLException e){throw new NegocioException(e.getMessage());}}
    public List<SeleccionMenu> consultarPorDetalleVenta(Long id){try{return repository.consultarPorIdDetalleVenta(id);}catch(SQLException e){throw new NegocioException(e.getMessage());}}
}
