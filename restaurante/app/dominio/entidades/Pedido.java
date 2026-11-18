package restaurante.app.dominio.entidades;

import java.time.LocalDateTime;
import java.util.List;

public class Pedido {
    private Long id;
    private String cliente;
    private String estado;
    private LocalDateTime fechaCreacion;
    private List<DetallePedido> detalles;

    public Pedido() {}
    public Pedido(Long id, String cliente, String estado) { this.id=id; this.cliente=cliente; this.estado=estado; }
    public Pedido(String cliente, String estado) { this.cliente=cliente; this.estado=estado; }
    public Long getId() { return id; } public void setId(Long id) { this.id=id; }
    public String getCliente() { return cliente; } public void setCliente(String cliente) { this.cliente=cliente; }
    public String getEstado() { return estado; } public void setEstado(String estado) { this.estado=estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; } public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion=fechaCreacion; }
    public List<DetallePedido> getDetalles() { return detalles; } public void setDetalles(List<DetallePedido> detalles) { this.detalles=detalles; }
}
