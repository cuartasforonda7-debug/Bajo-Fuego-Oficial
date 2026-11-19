package restaurante.app.presentacion.controllers;

import com.sun.net.httpserver.HttpExchange;
import restaurante.app.aplicacion.puertos.entrada.DetallePedidoServicePort;
import restaurante.app.aplicacion.servicios.NegocioException;
import restaurante.app.dominio.entidades.DetallePedido;
import java.io.IOException;

public class DetallePedidoController {
    private final DetallePedidoServicePort service;
    public DetallePedidoController(DetallePedidoServicePort service) { this.service=service; }
    public void handle(HttpExchange e)throws IOException{try{String p=e.getRequestURI().getPath();if(HttpSupport.method(e,"POST")&&p.equals("/detalle-pedidos")){DetallePedido d=HttpSupport.JSON.readValue(HttpSupport.body(e),DetallePedido.class);HttpSupport.json(e,201,service.crear(d));}else if(HttpSupport.method(e,"GET")&&p.matches("/detalle-pedidos/pedido/\\d+")){Long id=HttpSupport.idFromPath(e,"/detalle-pedidos/pedido/");HttpSupport.json(e,200,service.consultarPorPedido(id));}else HttpSupport.error(e,404,"Ruta no encontrada");}catch(NegocioException ex){HttpSupport.error(e,400,ex.getMessage());}catch(Exception ex){HttpSupport.error(e,400,"Datos inválidos: "+ex.getMessage());}}
}
