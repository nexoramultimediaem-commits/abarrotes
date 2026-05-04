package persistencia;

import com.mycompany.abarrotesobjetosdominio_00000278938.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase Productos.
 * Cubre flujo principal, flujos alternativos y flujos anómalos.
 */
public class ProductosTest {

    private Productos repo;
    private Producto productoValido;

    @BeforeEach
    void setUp() {
        repo = new Productos();
        // Producto válido reutilizable en cada prueba
        productoValido = new Producto("AT001", "Arroz", "G", "KG");
    }

    // =========================================================================
    // AGREGAR PRODUCTO
    // =========================================================================

    @Test
    void agregarProducto_flujoNormal_debeRetornarTrue() {
        assertTrue(repo.agregarProducto(productoValido));
    }

    @Test
    void agregarProducto_null_debeRetornarFalse() {
        assertFalse(repo.agregarProducto(null));
    }

    @Test
    void agregarProducto_claveRepetida_debeRetornarFalse() {
        repo.agregarProducto(productoValido);
        Producto duplicado = new Producto("AT001", "Frijol", "G", "KG");
        assertFalse(repo.agregarProducto(duplicado));
    }

    @Test
    void agregarProducto_claveInvalida_debeRetornarFalse() {
        Producto p = new Producto("1234X", "Sal", "E", "KG");
        assertFalse(repo.agregarProducto(p));
    }

    @Test
    void agregarProducto_sinNombre_debeRetornarFalse() {
        Producto p = new Producto("AT002", "", "G", "KG");
        assertFalse(repo.agregarProducto(p));
    }

    @Test
    void agregarProducto_unidadInvalida_debeRetornarFalse() {
        Producto p = new Producto("AT002", "Aceite", "G", "ML");
        assertFalse(repo.agregarProducto(p));
    }

    @Test
    void agregarProducto_tipoInvalido_debeRetornarFalse() {
        Producto p = new Producto("AT002", "Leche", "X", "L");
        assertFalse(repo.agregarProducto(p));
    }

    // =========================================================================
    // CONSULTAR PRODUCTO
    // =========================================================================

    @Test
    void consultarProducto_claveExistente_debeRetornarProducto() {
        repo.agregarProducto(productoValido);
        Producto encontrado = repo.consultarProducto("AT001");
        assertNotNull(encontrado);
        assertEquals("AT001", encontrado.getClave());
    }

    @Test
    void consultarProducto_claveInexistente_debeRetornarNull() {
        assertNull(repo.consultarProducto("ZZ999"));
    }

    @Test
    void consultarProducto_claveNull_debeRetornarNull() {
        assertNull(repo.consultarProducto(null));
    }

    // =========================================================================
    // ACTUALIZAR PRODUCTO
    // =========================================================================

    @Test
    void actualizarProducto_flujoNormal_debeRetornarTrue() {
        repo.agregarProducto(productoValido);
        Producto actualizado = new Producto("AT001", "Arroz Integral", "G", "KG");
        assertTrue(repo.actualizarProducto(actualizado));
    }

    @Test
    void actualizarProducto_productoNoExiste_debeRetornarFalse() {
        Producto p = new Producto("ZZ999", "Inexistente", "E", "PZ");
        assertFalse(repo.actualizarProducto(p));
    }

    @Test
    void actualizarProducto_null_debeRetornarFalse() {
        assertFalse(repo.actualizarProducto(null));
    }

    @Test
    void actualizarProducto_unidadInvalida_debeRetornarFalse() {
        repo.agregarProducto(productoValido);
        Producto p = new Producto("AT001", "Arroz", "G", "GR");
        assertFalse(repo.actualizarProducto(p));
    }

    // =========================================================================
    // ELIMINAR PRODUCTO
    // =========================================================================

    @Test
    void eliminarProducto_flujoNormal_debeRetornarTrue() {
        repo.agregarProducto(productoValido);
        assertTrue(repo.eliminarProducto("AT001"));
    }

    @Test
    void eliminarProducto_claveInexistente_debeRetornarFalse() {
        assertFalse(repo.eliminarProducto("ZZ999"));
    }

    @Test
    void eliminarProducto_claveNull_debeRetornarFalse() {
        assertFalse(repo.eliminarProducto(null));
    }

    // =========================================================================
    // CONSULTAR LISTADO CON FILTROS
    // =========================================================================

    @Test
    void consultarProductos_sinFiltros_debeRetornarTodos() {
        repo.agregarProducto(productoValido);
        repo.agregarProducto(new Producto("LT001", "Leche", "E", "L"));
        HashMap<String, Producto> resultado = repo.consultarProductos(null, null);
        assertEquals(2, resultado.size());
    }

    @Test
    void consultarProductos_filtroPorTipo_debeRetornarSoloEseTipo() {
        repo.agregarProducto(productoValido);                                  // tipo G
        repo.agregarProducto(new Producto("LT001", "Leche", "E", "L"));       // tipo E
        HashMap<String, Producto> resultado = repo.consultarProductos("G", null);
        assertEquals(1, resultado.size());
        assertTrue(resultado.containsKey("AT001"));
    }

    @Test
    void consultarProductos_filtroPorUnidad_debeRetornarSolaEsaUnidad() {
        repo.agregarProducto(productoValido);                                  // unidad KG
        repo.agregarProducto(new Producto("LT001", "Leche", "E", "L"));       // unidad L
        HashMap<String, Producto> resultado = repo.consultarProductos(null, "KG");
        assertEquals(1, resultado.size());
    }

    @Test
    void consultarProductos_filtroAmbos_debeRetornarCoincidencias() {
        repo.agregarProducto(productoValido);                                  // G, KG
        repo.agregarProducto(new Producto("LT001", "Leche", "E", "L"));       // E, L
        HashMap<String, Producto> resultado = repo.consultarProductos("G", "KG");
        assertEquals(1, resultado.size());
    }

    @Test
    void consultarProductos_sinCoincidencias_debeRetornarVacio() {
        repo.agregarProducto(productoValido);
        HashMap<String, Producto> resultado = repo.consultarProductos("E", "L");
        assertTrue(resultado.isEmpty());
    }
}
