package restaurante.app.dominio.entidades;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Venta {
    private Long id;
    private Long idPedido;
    private LocalDateTime fechaVenta;
    private String metodoPago;
    private String estadoPago;
    private BigDecimal total;
    private List<DetalleVenta> detalles;

    public Venta() {}
    public Venta(Long idPedido, String metodoPago, String estadoPago, BigDecimal total) { this.idPedido=idPedido; this.metodoPago=metodoPago; this.estadoPago=estadoPago; this.total=total; }
    public Long getId() { return id; } public void setId(Long id) { this.id=id; }
    public Long getIdPedido() { return idPedido; } public void setIdPedido(Long idPedido) { this.idPedido=idPedido; }
    public LocalDateTime getFechaVenta() { return fechaVenta; } public void setFechaVenta(LocalDateTime fechaVenta) { this.fechaVenta=fechaVenta; }
    public String getMetodoPago() { return metodoPago; } public void setMetodoPago(String metodoPago) { this.metodoPago=metodoPago; }
    public String getEstadoPago() { return estadoPago; } public void setEstadoPago(String estadoPago) { this.estadoPago=estadoPago; }
    public BigDecimal getTotal() { return total; } public void setTotal(BigDecimal total) { this.total=total; }
    public List<DetalleVenta> getDetalles() { return detalles; } public void setDetalles(List<DetalleVenta> detalles) { this.detalles=detalles; }
}
