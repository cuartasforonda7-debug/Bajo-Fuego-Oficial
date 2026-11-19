package restaurante.app.presentacion.routes;

import com.sun.net.httpserver.HttpServer;
import restaurante.app.presentacion.controllers.*;

public final class RestaurantRoutes {
    public static void register(HttpServer server,
            PedidoController pedido, VentaController venta,
            DetallePedidoController detallePedido, DetalleVentaController detalleVenta,
            SeleccionMenuController seleccion) {
        server.createContext("/pedidos",pedido::handle); server.createContext("/ventas",venta::handle);
        server.createContext("/detalle-pedidos",detallePedido::handle); server.createContext("/detalle-ventas",detalleVenta::handle); server.createContext("/selecciones-menu",seleccion::handle);
    }
}
