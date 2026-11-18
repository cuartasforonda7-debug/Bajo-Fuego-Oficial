package restaurante.app.aplicacion.servicios;

import restaurante.app.dominio.entidades.SeleccionMenu;
import restaurante.app.infraestructura.repositorios.SeleccionMenuRepository;
import java.sql.SQLException;
import java.util.*;

public class SeleccionMenuService {
    private final SeleccionMenuRepository repository=new SeleccionMenuRepository();
    public void validar(SeleccionMenu s){boolean pedido=s!=null&&s.getIdDetallePedido()!=null&&s.getIdDetalleVenta()==null;boolean venta=s!=null&&s.getIdDetallePedido()==null&&s.getIdDetalleVenta()!=null;if(!pedido&&!venta)throw new NegocioException("La selección debe pertenecer exclusivamente a un DetallePedido o a un DetalleVenta");}
    public SeleccionMenu crear(SeleccionMenu s){validar(s);try{return repository.crear(s);}catch(SQLException e){throw new NegocioException(e.getMessage());}}
    public List<SeleccionMenu> consultarPorDetallePedido(Long id){try{return repository.consultarPorIdDetallePedido(id);}catch(SQLException e){throw new NegocioException(e.getMessage());}}
    public List<SeleccionMenu> consultarPorDetalleVenta(Long id){try{return repository.consultarPorIdDetalleVenta(id);}catch(SQLException e){throw new NegocioException(e.getMessage());}}
}
