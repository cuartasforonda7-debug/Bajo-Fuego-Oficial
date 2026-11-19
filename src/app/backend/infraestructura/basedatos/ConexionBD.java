package app.backend.infraestructura.basedatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

        public static Connection obtenerConexion() throws SQLException {
            String url = System.getenv().getOrDefault(
                    "DB_URL",
                    "jdbc:sqlserver://localhost:1433;databaseName=Bajofuegodb;encrypt=true;trustServerCertificate=true");
            String usuario = variableObligatoria("DB_USER");
            String password = variableObligatoria("DB_PASSWORD");

            return DriverManager.getConnection(url, usuario, password);
        }

        private static String variableObligatoria(String nombre) {
            String valor = System.getenv(nombre);
            if (valor == null || valor.isBlank()) {
                throw new IllegalStateException("Debe configurar la variable de entorno " + nombre);
            }
            return valor;
        }
}