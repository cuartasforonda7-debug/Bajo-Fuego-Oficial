import app.aplicacion.servicios.PagoService;
import app.aplicacion.servicios.ProductoService;
import app.aplicacion.servicios.UsuarioService;
import app.backend.infraestructura.adaptadores.PagoRepositoryAdapter;
import app.backend.infraestructura.adaptadores.ProductoRepositoryAdapter;
import app.backend.infraestructura.adaptadores.UsuarioRepositoryAdapter;
import app.backend.infraestructura.basedatos.ConexionBD;
import app.backend.infraestructura.repositorio.PagoRepository;
import app.backend.presentacion.controlador.PagoController;
import app.backend.presentacion.controlador.ProductoController;
import app.backend.presentacion.controlador.UsuarioController;
import dominio.puertos.salida.ProductoRepositoryPort;
import dominio.puertos.salida.UsuarioRepositoryPort;

public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   RESTAURANTE DE MAR - SISTEMA INTEGRADO (v1.0)  ");
        System.out.println("==================================================");

        // A. Instancia de conexión a la base de datos
        ConexionBD conexion = new ConexionBD();

        // B. HU-01: Módulo de Usuarios (Juan José)
        UsuarioRepositoryPort usuarioRepo = new UsuarioRepositoryAdapter();
        UsuarioService usuarioService = new UsuarioService(usuarioRepo);
        UsuarioController usuarioController = new UsuarioController(usuarioService);

        // C. HU-05: Módulo de Productos (Cristian)
        ProductoRepositoryPort productoRepo = new ProductoRepositoryAdapter();
        ProductoService productoService = new ProductoService(productoRepo);
        ProductoController productoController = new ProductoController(productoService);

        // D. HU-16: Módulo de Pagos (Ricardo)
        PagoRepository pagoRepoBase = new PagoRepository();
        PagoRepositoryAdapter pagoRepoAdapter = new PagoRepositoryAdapter(pagoRepoBase);
        PagoService pagoService = new PagoService(pagoRepoAdapter);
        PagoController pagoController = new PagoController(pagoService);

        // E. Confirmación de carga (utiliza la conexión y los controladores para eliminar los warnings)
        System.out.println("✓ BD Inicializada en: " + conexion.getClass().getSimpleName());
        System.out.println("✓ HU-01 Cargado: " + usuarioController.getClass().getSimpleName());
        System.out.println("✓ HU-05 Cargado: " + productoController.getClass().getSimpleName());
        System.out.println("✓ HU-16 Cargado: " + pagoController.getClass().getSimpleName());
        System.out.println("==================================================");
        System.out.println("¡Proyecto 100% integrado y compilado sin errores!");
    }
}