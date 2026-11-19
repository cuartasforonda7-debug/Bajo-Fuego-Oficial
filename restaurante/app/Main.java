package restaurante.app;

import com.sun.net.httpserver.HttpServer;
import restaurante.app.aplicacion.servicios.*;
import restaurante.app.infraestructura.repositorios.*;
import restaurante.app.presentacion.controllers.*;
import restaurante.app.presentacion.routes.RestaurantRoutes;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws Exception {
        PedidoRepository pedidoRepository=new PedidoRepository();
        DetallePedidoRepository detallePedidoRepository=new DetallePedidoRepository();
        SeleccionMenuRepository seleccionMenuRepository=new SeleccionMenuRepository();
        VentaRepository ventaRepository=new VentaRepository();
        DetalleVentaRepository detalleVentaRepository=new DetalleVentaRepository();

        DetallePedidoService detallePedidoService=new DetallePedidoService(detallePedidoRepository);
        SeleccionMenuService seleccionMenuService=new SeleccionMenuService(seleccionMenuRepository);
        DetalleVentaService detalleVentaService=new DetalleVentaService(detalleVentaRepository);
        PedidoService pedidoService=new PedidoService(pedidoRepository,detallePedidoRepository,seleccionMenuRepository,detallePedidoService,seleccionMenuService);
        VentaService ventaService=new VentaService(ventaRepository,detalleVentaRepository,seleccionMenuRepository,pedidoService,detallePedidoService,seleccionMenuService,detalleVentaService);

        PedidoController pedidoController=new PedidoController(pedidoService);
        DetallePedidoController detallePedidoController=new DetallePedidoController(detallePedidoService);
        SeleccionMenuController seleccionMenuController=new SeleccionMenuController(seleccionMenuService);
        VentaController ventaController=new VentaController(ventaService);
        DetalleVentaController detalleVentaController=new DetalleVentaController(detalleVentaService);

        int port=Integer.parseInt(System.getenv().getOrDefault("PORT","8080"));
        HttpServer server=HttpServer.create(new InetSocketAddress(port),0);
        RestaurantRoutes.register(server,pedidoController,ventaController,detallePedidoController,detalleVentaController,seleccionMenuController);
        server.setExecutor(null); server.start();
        System.out.println("Servidor iniciado en http://localhost:"+port);
    }
}
