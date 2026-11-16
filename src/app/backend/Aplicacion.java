package app.backend;

import dominio.entidades.Pago;

public class Aplicacion {
    public static void main(String[] args) {
        System.out.println("Probando HU-16 sin BD y sin pedidos...");

        try {
            Pago pago = new Pago();
            pago.setIdPago(1);
            pago.setMonto(50000);

            System.out.println(">>> PAGO CREADO EN MEMORIA CORRECTAMENTE");
            System.out.println("ID: " + pago.getIdPago());
            System.out.println("Monto: " + pago.getMonto());
            System.out.println("HU-16 OK - Sin necesidad de BD ni Pedidos");

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

