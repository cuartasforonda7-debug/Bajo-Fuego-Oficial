package restaurante.app.aplicacion.servicios;

import restaurante.app.dominio.entidades.DetallePedido;
import restaurante.app.infraestructura.repositorios.DetallePedidoRepository;
import java.sql.SQLException;
import java.util.*;

public class DetallePedidoService {
    private final DetallePedidoRepository repository = new DetallePedidoRepository();
    public void validar(DetallePedido d) {
        if(d==null)throw new NegocioException("Detalle requerido");
        boolean menu=d.getIdMenu()!=null&&d.getIdProducto()==null;
        boolean producto=d.getIdMenu()==null&&d.getIdProducto()!=null;
        if(!menu&&!producto)throw new NegocioException("El detalle debe ser exclusivamente MENU o PRODUCTO");
        if(d.getCantidad()==null||d.getCantidad()<1)throw new NegocioException("La cantidad debe ser mayor a cero");
        if(d.getPrecioUnitario()==null||d.getPrecioUnitario().signum()<0)throw new NegocioException("El precio unitario histórico es requerido");
    }
    public DetallePedido crear(DetallePedido d){validar(d);try{return repository.crear(d);}catch(SQLException e){throw new NegocioException(e.getMessage());}}
    public List<DetallePedido> consultarPorPedido(Long idPedido){try{return repository.consultarPorIdPedido(idPedido);}catch(SQLException e){throw new NegocioException(e.getMessage());}}
}
