package app.backend;

import app.backend.configuracion.Config;
import app.backend.presentacion.controlador.PagoController;
import dominio.entidades.Pago;
import java.util.Scanner;

public class Aplicacion {
    public static void main(String[] args) {
        Config config = new Config();
        PagoController pagoController = config.obtenerPagoController();
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== PRUEBA HU-16 REGISTRAR PAGO =====");
        try {
            // Creamos un pago de prueba sin depender de pedidos
            Pago pago = new Pago();
            pago.setIdPago(1);
            pago.setIdPedido(1); // ID 1 ficticio para que no valide
            pago.setMonto(50000);
            pago.setMetodoPago("Efectivo");

            System.out.println("Registrando pago de prueba...");
            System.out.println("ID Pago: 1, ID Pedido: 1, Monto: 50000, Metodo: Efectivo");

            pagoController.registrarPago(pago);

            System.out.println(">>> ¡PAGO GUARDADO EN BD CORRECTAMENTE!");
            System.out.println("\n--- LISTA DE PAGOS EN BD ---");
            pagoController.listarPagos().forEach(p ->
                    System.out.println("ID: " + p.getIdPago() + " | Pedido: " + p.getIdPedido() + " | $" + p.getMonto() + " | " + p.getMetodoPago())
            );

        } catch (Exception e) {
            System.out.println("Error al registrar: " + e.getMessage());
            e.printStackTrace();
        }
        scanner.close();
    }
}
