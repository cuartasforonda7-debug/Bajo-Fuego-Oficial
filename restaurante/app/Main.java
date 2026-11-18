package restaurante.app;

import com.sun.net.httpserver.HttpServer;
import restaurante.app.presentacion.routes.RestaurantRoutes;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws Exception {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        RestaurantRoutes.register(server);
        server.setExecutor(null);
        server.start();
        System.out.println("Servidor iniciado en http://localhost:" + port);
    }
}
