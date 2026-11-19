package restaurante.app.infraestructura.repositorios;

public class PersistenceException extends RuntimeException {
    public PersistenceException(String message, Throwable cause) { super(message, cause); }
}
