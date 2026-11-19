package restaurante.app.aplicacion.servicios;

import restaurante.app.dominio.entidades.*;
import restaurante.app.aplicacion.puertos.entrada.PedidoServicePort;
import restaurante.app.dominio.puertos.salida.*;
import java.sql.SQLException;
import java.util.*;

public class PedidoService implements PedidoServicePort {
    public PedidoService(PedidoRepositoryPort pedidoRepository, DetallePedidoRepositoryPort detalleRepository, SeleccionMenuRepositoryPort seleccionRepository, DetallePedidoServicePort detalleService, SeleccionMenuServicePort seleccionService) {
        this.pedidoRepository=pedidoRepository; this.detalleRepository=detalleRepository; this.seleccionRepository=seleccionRepository;
        this.detalleService=detalleService; this.seleccionService=seleccionService;
    }
    private final PedidoRepositoryPort pedidoRepository;
    private final DetallePedidoRepositoryPort detalleRepository;
    private final SeleccionMenuRepositoryPort seleccionRepository;
    private final DetallePedidoServicePort detalleService;
    private final SeleccionMenuServicePort seleccionService;

    public Pedido crear(Pedido pedido, List<DetallePedido> detalles, Map<Long,List<SeleccionMenu>> selecciones) {
        if (pedido == null) throw new NegocioException("Pedido requerido");
        pedido.setEstado(pedido.getEstado()==null?"PENDIENTE":pedido.getEstado().toUpperCase());
        if (!pedido.getEstado().equals("PENDIENTE") || detalles==null || detalles.isEmpty()) throw new NegocioException("Un pedido nuevo debe estar PENDIENTE y tener al menos un detalle");
        try {
            Pedido creado=pedidoRepository.crear(pedido);
            for(DetallePedido d:detalles){ d.setIdPedido(creado.getId());detalleService.validar(d);DetallePedido guardado=detalleRepository.crear(d); if(d.getSelecciones()!=null){ for(SeleccionMenu s:d.getSelecciones()){ s.setIdDetallePedido(guardado.getId());s.setIdDetalleVenta(null);seleccionService.validar(s);seleccionRepository.crear(s); } } if(selecciones!=null){ for(SeleccionMenu s:selecciones.getOrDefault(d.getId(),Collections.emptyList())) { s.setIdDetallePedido(guardado.getId());s.setIdDetalleVenta(null);seleccionService.validar(s);seleccionRepository.crear(s); } } }
            return creado;
        } catch(SQLException e){throw new NegocioException("Error al crear pedido: "+e.getMessage());}
    }
    public Pedido crear(Pedido pedido){return crear(pedido,new ArrayList<>(),null);}
    public List<Pedido> listar(){try{return pedidoRepository.listarTodos();}catch(SQLException e){throw new NegocioException(e.getMessage());}}
    public Pedido consultar(Long id){try{Pedido p=pedidoRepository.findById(id);if(p==null)throw new NegocioException("Pedido no encontrado");List<DetallePedido> detalles=detalleRepository.consultarPorIdPedido(id);for(DetallePedido d:detalles)d.setSelecciones(seleccionRepository.consultarPorIdDetallePedido(d.getId()));p.setDetalles(detalles);return p;}catch(SQLException e){throw new NegocioException(e.getMessage());}}
    public Pedido actualizarEstado(Long id,String nuevo){nuevo=nuevo==null?"":nuevo.trim().toUpperCase();Pedido p=consultar(id);String actual=p.getEstado();if(actual.equals("CANCELADO"))throw new NegocioException("Un pedido CANCELADO es final y no puede cambiar de estado");Map<String,String> siguiente=Map.of("PENDIENTE","EN_PREPARACION","EN_PREPARACION","LISTO","LISTO","EN_CAMINO","EN_CAMINO","ENTREGADO");if(!nuevo.equals(siguiente.get(actual)))throw new NegocioException("Transición inválida de "+actual+" a "+nuevo);try{pedidoRepository.actualizarEstado(id,nuevo);p.setEstado(nuevo);return p;}catch(SQLException e){throw new NegocioException(e.getMessage());}}
    public Pedido cancelar(Long id){Pedido p=consultar(id);if(!p.getEstado().equals("PENDIENTE")&&!p.getEstado().equals("EN_PREPARACION")&&p.getEstado().equals("CANCELADO")==false)throw new NegocioException("Solo se puede cancelar un pedido PENDIENTE o EN_PREPARACION");try{pedidoRepository.cancelar(id);p.setEstado("CANCELADO");return p;}catch(SQLException e){throw new NegocioException(e.getMessage());}}
}
