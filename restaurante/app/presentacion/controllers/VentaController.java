package restaurante.app.presentacion.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.net.httpserver.HttpExchange;
import restaurante.app.aplicacion.puertos.entrada.VentaServicePort;
import restaurante.app.dominio.entidades.Venta;
import restaurante.app.aplicacion.servicios.NegocioException;
import java.io.IOException;

public class VentaController {
    private final VentaServicePort service;
    public VentaController(VentaServicePort service) { this.service=service; }
    public void handle(HttpExchange e)throws IOException{
        try{String path=e.getRequestURI().getPath();Long id=HttpSupport.idFromPath(e,"/ventas");
            if(HttpSupport.method(e,"POST")&&path.equals("/ventas")){JsonNode n=HttpSupport.JSON.readTree(HttpSupport.body(e));if(n.has("idPedido")&&!n.get("idPedido").isNull())HttpSupport.json(e,201,service.convertirPedidoAVenta(n.get("idPedido").asLong(),n.has("metodoPago")?n.get("metodoPago").asText():null,n.has("estadoPago")?n.get("estadoPago").asText():null));else{Venta v=HttpSupport.JSON.treeToValue(n,Venta.class);HttpSupport.json(e,201,service.crearVentaDirecta(v,v.getDetalles(),null));}}
            else if(HttpSupport.method(e,"GET")&&path.equals("/ventas"))HttpSupport.json(e,200,service.listar());
            else if(HttpSupport.method(e,"GET")&&id!=null)HttpSupport.json(e,200,service.listarConsultar(id));
            else HttpSupport.error(e,404,"Ruta de ventas no encontrada");
        }catch(NegocioException ex){HttpSupport.error(e,400,ex.getMessage());}catch(Exception ex){HttpSupport.error(e,400,"Datos inválidos: "+ex.getMessage());}
    }
}
