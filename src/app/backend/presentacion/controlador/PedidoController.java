package restaurante.app.presentacion.controllers;

import com.sun.net.httpserver.HttpExchange;
import restaurante.app.aplicacion.puertos.entrada.PedidoServicePort;
import restaurante.app.dominio.entidades.Pedido;
import restaurante.app.aplicacion.servicios.NegocioException;
import java.io.IOException;

public class PedidoController {
    private final PedidoServicePort service;
    public PedidoController(PedidoServicePort service) { this.service=service; }
    public void handle(HttpExchange e)throws IOException{
        try{String path=e.getRequestURI().getPath();Long id=HttpSupport.idFromPath(e,"/pedidos");
            if(HttpSupport.method(e,"POST")&&path.equals("/pedidos")){Pedido p=HttpSupport.JSON.readValue(HttpSupport.body(e),Pedido.class);Pedido created=service.crear(p,p.getDetalles(),null);HttpSupport.json(e,201,created);}
            else if(HttpSupport.method(e,"GET")&&path.equals("/pedidos"))HttpSupport.json(e,200,service.listar());
            else if(HttpSupport.method(e,"GET")&&id!=null)HttpSupport.json(e,200,service.consultar(id));
            else if(HttpSupport.method(e,"PUT")&&path.matches("/pedidos/\\d+/estado")){java.util.Map<?,?> b=HttpSupport.JSON.readValue(HttpSupport.body(e),java.util.Map.class);HttpSupport.json(e,200,service.actualizarEstado(id,String.valueOf(b.get("estado"))));}
            else if(HttpSupport.method(e,"POST")&&path.matches("/pedidos/\\d+/cancelar"))HttpSupport.json(e,200,service.cancelar(id));
            else HttpSupport.error(e,404,"Ruta de pedidos no encontrada");
        }catch(NegocioException ex){HttpSupport.error(e,400,ex.getMessage());}catch(Exception ex){HttpSupport.error(e,400,"Datos inválidos: "+ex.getMessage());}
    }
}
