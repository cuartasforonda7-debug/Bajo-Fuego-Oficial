package restaurante.app.presentacion.controllers;

import com.sun.net.httpserver.HttpExchange;
import restaurante.app.aplicacion.puertos.entrada.SeleccionMenuServicePort;
import restaurante.app.dominio.entidades.SeleccionMenu;
import restaurante.app.aplicacion.servicios.NegocioException;
import java.io.IOException;

public class SeleccionMenuController {
    private final SeleccionMenuServicePort service;
    public SeleccionMenuController(SeleccionMenuServicePort service) { this.service=service; }
    public void handle(HttpExchange e)throws IOException{try{String p=e.getRequestURI().getPath();if(HttpSupport.method(e,"POST")&&p.equals("/selecciones-menu")){SeleccionMenu s=HttpSupport.JSON.readValue(HttpSupport.body(e),SeleccionMenu.class);HttpSupport.json(e,201,service.crear(s));}else if(HttpSupport.method(e,"GET")&&p.matches("/selecciones-menu/pedido/\\d+")){Long id=HttpSupport.idFromPath(e,"/selecciones-menu/pedido/");HttpSupport.json(e,200,service.consultarPorDetallePedido(id));}else if(HttpSupport.method(e,"GET")&&p.matches("/selecciones-menu/venta/\\d+")){Long id=HttpSupport.idFromPath(e,"/selecciones-menu/venta/");HttpSupport.json(e,200,service.consultarPorDetalleVenta(id));}else HttpSupport.error(e,404,"Ruta no encontrada");}catch(NegocioException ex){HttpSupport.error(e,400,ex.getMessage());}catch(Exception ex){HttpSupport.error(e,400,"Datos inválidos: "+ex.getMessage());}}
}
