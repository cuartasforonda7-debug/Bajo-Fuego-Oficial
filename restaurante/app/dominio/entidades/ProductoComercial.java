package restaurante.app.dominio.entidades;

import java.math.BigDecimal;

public class ProductoComercial {
    private Long idMenu;
    private Long idProducto;
    private String nombre;
    private BigDecimal precio;

    public ProductoComercial() {}
    public ProductoComercial(Long idMenu, Long idProducto, String nombre, BigDecimal precio) { this.idMenu=idMenu; this.idProducto=idProducto; this.nombre=nombre; this.precio=precio; }
    public Long getIdMenu() { return idMenu; } public void setIdMenu(Long idMenu) { this.idMenu=idMenu; }
    public Long getIdProducto() { return idProducto; } public void setIdProducto(Long idProducto) { this.idProducto=idProducto; }
    public String getNombre() { return nombre; } public void setNombre(String nombre) { this.nombre=nombre; }
    public BigDecimal getPrecio() { return precio; } public void setPrecio(BigDecimal precio) { this.precio=precio; }
}
