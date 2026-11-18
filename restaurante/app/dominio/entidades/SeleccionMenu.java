package restaurante.app.dominio.entidades;

public class SeleccionMenu {
    private Long id;
    private Long idDetallePedido;
    private Long idDetalleVenta;
    private Long idMenu;
    private Long idProducto;
    private String descripcion;
    private Integer cantidad;

    public SeleccionMenu() {}
    public SeleccionMenu(Long idDetallePedido, Long idDetalleVenta, Long idMenu, Long idProducto, String descripcion) { this.idDetallePedido=idDetallePedido; this.idDetalleVenta=idDetalleVenta; this.idMenu=idMenu; this.idProducto=idProducto; this.descripcion=descripcion; this.cantidad=1; }
    public Long getId() { return id; } public void setId(Long id) { this.id=id; }
    public Long getIdDetallePedido() { return idDetallePedido; } public void setIdDetallePedido(Long idDetallePedido) { this.idDetallePedido=idDetallePedido; }
    public Long getIdDetalleVenta() { return idDetalleVenta; } public void setIdDetalleVenta(Long idDetalleVenta) { this.idDetalleVenta=idDetalleVenta; }
    public Long getIdMenu() { return idMenu; } public void setIdMenu(Long idMenu) { this.idMenu=idMenu; }
    public Long getIdProducto() { return idProducto; } public void setIdProducto(Long idProducto) { this.idProducto=idProducto; }
    public String getDescripcion() { return descripcion; } public void setDescripcion(String descripcion) { this.descripcion=descripcion; }
    public Integer getCantidad() { return cantidad; } public void setCantidad(Integer cantidad) { this.cantidad=cantidad; }
}
