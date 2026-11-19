package dominio.entidades;

import dominio.enums.EstadoProducto;

public class Producto {

    // ATRIBUTOS!

    private int idProducto;
    private String nombre;
    private String descripcion;
    private String unidadMedida;
    private double costo;
    private double precioVenta;
    private double stockActual;
    private double stockMinimo;
    private EstadoProducto estado = EstadoProducto.ACTIVO;

    // CONSTRUCTOR VACIO!
    public Producto() {
    }

    // CONSTRUCTOR COMPLETO!
    public Producto(int idProducto, String nombre, String descripcion,
                    String unidadMedida, double costo, double precioVenta,
                    double stockActual, double stockMinimo, EstadoProducto estado) {

        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.unidadMedida = unidadMedida;
        this.costo = costo;
        this.precioVenta = precioVenta;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.estado = estado;
    }

    // GETTERS Y SETTERS!

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public double getStockActual() {
        return stockActual;
    }

    public void setStockActual(double stockActual) {
        this.stockActual = stockActual;
    }

    public double getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(double stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public EstadoProducto getEstado() {
        return estado;
    }

    public void setEstado(EstadoProducto estado) {
        this.estado = estado;
    }
}
