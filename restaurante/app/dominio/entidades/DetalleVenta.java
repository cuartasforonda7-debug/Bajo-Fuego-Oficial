package restaurante.app.dominio.entidades;

import java.math.BigDecimal;
import java.util.List;

public class DetalleVenta {
    private Long id;
    private Long idVenta;
    private Long idProducto;
    private Long idMenu;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private List<SeleccionMenu> selecciones;

    public DetalleVenta() {}
    public DetalleVenta(Long idVenta, Long idProducto, Long idMenu, Integer cantidad, BigDecimal precioUnitario) { this.idVenta=idVenta; this.idProducto=idProducto; this.idMenu=idMenu; this.cantidad=cantidad; this.precioUnitario=precioUnitario; }
    public Long getId() { return id; } public void setId(Long id) { this.id=id; }
    public Long getIdVenta() { return idVenta; } public void setIdVenta(Long idVenta) { this.idVenta=idVenta; }
    public Long getIdProducto() { return idProducto; } public void setIdProducto(Long idProducto) { this.idProducto=idProducto; }
    public Long getIdMenu() { return idMenu; } public void setIdMenu(Long idMenu) { this.idMenu=idMenu; }
    public Integer getCantidad() { return cantidad; } public void setCantidad(Integer cantidad) { this.cantidad=cantidad; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; } public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario=precioUnitario; }
    public BigDecimal getSubtotal() { return subtotal; } public void setSubtotal(BigDecimal subtotal) { this.subtotal=subtotal; }
    public List<SeleccionMenu> getSelecciones() { return selecciones; } public void setSelecciones(List<SeleccionMenu> selecciones) { this.selecciones=selecciones; }
}
