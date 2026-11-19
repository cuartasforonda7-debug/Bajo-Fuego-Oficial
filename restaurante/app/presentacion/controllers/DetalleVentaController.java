package restaurante.app.presentacion.controllers;

import com.sun.net.httpserver.HttpExchange;
import restaurante.app.aplicacion.puertos.entrada.DetalleVentaServicePort;
import restaurante.app.dominio.entidades.DetalleVenta;
import restaurante.app.aplicacion.servicios.NegocioException;
import java.io.IOException;

public class DetalleVentaController {
    private final DetalleVentaServicePort service;
    public DetalleVentaController(DetalleVentaServicePort service) { this.service=service; }
    public void handle(HttpExchange e)throws IOException{try{String p=e.getRequestURI().getPath();if(HttpSupport.method(e,"POST")&&p.equals("/detalle-ventas")){DetalleVenta d=HttpSupport.JSON.readValue(HttpSupport.body(e),DetalleVenta.class);HttpSupport.json(e,201,service.crear(d));}else if(HttpSupport.method(e,"GET")&&p.matches("/detalle-ventas/venta/\\d+")){Long id=HttpSupport.idFromPath(e,"/detalle-ventas/venta/");HttpSupport.json(e,200,service.consultarPorVenta(id));}else HttpSupport.error(e,404,"Ruta no encontrada");}catch(NegocioException ex){HttpSupport.error(e,400,ex.getMessage());}catch(Exception ex){HttpSupport.error(e,400,"Datos inválidos: "+ex.getMessage());}}
}
