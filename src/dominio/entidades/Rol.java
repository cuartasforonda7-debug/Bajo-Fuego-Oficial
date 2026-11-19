package dominio.entidades;

import java.util.ArrayList;
import java.util.List;
import dominio.enums.TipoPermiso;

public class Rol {

    // === ATRIBUTOS ===
    private int idRol;
    private String nombre;
    private String descripcion;
    private List<TipoPermiso> permisos;

    // === CONSTRUCTOR VACIO ===
    public Rol() {
        this.permisos = new ArrayList<>();
    }

    // === CONSTRUCTOR BASICO ===
    public Rol(int idRol, String nombre, String descripcion) {
        this.idRol = idRol;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.permisos = new ArrayList<>();
    }

    // === CONSTRUCTOR COMPLETO ===
    public Rol(int idRol, String nombre, String descripcion, List<TipoPermiso> permisos) {
        this.idRol = idRol;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.permisos = (permisos != null) ? new ArrayList<>(permisos) : new ArrayList<>();
    }

    // === GETTERS Y SETTERS ===
    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
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

    public List<TipoPermiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<TipoPermiso> permisos) {
        this.permisos = (permisos != null) ? new ArrayList<>(permisos) : new ArrayList<>();
    }
}
