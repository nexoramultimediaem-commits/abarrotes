package persistencia;

import com.mycompany.abarrotesobjetosdominio_00000278938.ProductoGranel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase ProductosGranel.
 */
public class ProductosGranelTest {

    private ProductosGranel repo;
    private ProductoGranel productoValido;

    @BeforeEach
    void setUp() {
        repo = new ProductosGranel();
        productoValido = new ProductoGranel("AT001", "Arroz", 'G', "KG", 50.0f);
    }

    // =========================================================================
    // AGREGAR
    // =========================================================================

    @Test
    void agregarProductoGranel_flujoNormal_debeRetornarTrue() {
        assertTrue(repo.agregarProductoGranel(productoValido));
    }

    @Test
    void agregarProductoGranel_null_debeRetornarFalse() {
        assertFalse(repo.agregarProductoGranel(null));
    }

    @Test
    void agregarProductoGranel_duplicado_debeRetornarFalse() {
        repo.agregarProductoGranel(productoValido);
        ProductoGranel dup = new ProductoGranel("AT001", "Arroz", 'G', "KG", 10.0f);
        assertFalse(repo.agregarProductoGranel(dup));
    }

    @Test
    void agregarProductoGranel_cantidadCero_debeRetornarFalse() {
        ProductoGranel p = new ProductoGranel("AT002", "Frijol", 'G', "KG", 0.0f);
        assertFalse(repo.agregarProductoGranel(p));
    }

    @Test
    void agregarProductoGranel_cantidadNegativa_debeRetornarFalse() {
        ProductoGranel p = new ProductoGranel("AT002", "Frijol", 'G', "KG", -5.0f);
        assertFalse(repo.agregarProductoGranel(p));
    }

    // =========================================================================
    // CONSULTAR
    // =========================================================================

    @Test
    void consultarProductoGranel_claveExistente_debeRetornarProducto() {
        repo.agregarProductoGranel(productoValido);
        ProductoGranel encontrado = repo.consultarProductoGranel("AT001");
        assertNotNull(encontrado);
    }

    @Test
    void consultarProductoGranel_claveInexistente_debeRetornarNull() {
        assertNull(repo.consultarProductoGranel("ZZ999"));
    }

    @Test
    void consultarProductoGranel_claveNull_debeRetornarNull() {
        assertNull(repo.consultarProductoGranel(null));
    }

    @Test
    void consultarInventario_conDatos_debeRetornarTodos() {
        repo.agregarProductoGranel(productoValido);
        repo.agregarProductoGranel(new ProductoGranel("LT001", "Leche", 'G', "L", 20.0f));
        HashMap<String, ProductoGranel> inv = repo.consultarInventario();
        assertEquals(2, inv.size());
    }

    @Test
    void consultarInventario_vacio_debeRetornarMapaVacio() {
        assertTrue(repo.consultarInventario().isEmpty());
    }

    // =========================================================================
    // ACTUALIZAR
    // =========================================================================

    @Test
    void actualizarProductoGranel_flujoNormal_debeRetornarTrue() {
        repo.agregarProductoGranel(productoValido);
        ProductoGranel actualizado = new ProductoGranel("AT001", "Arroz", 'G', "KG", 80.0f);
        assertTrue(repo.actualizarProductoGranel(actualizado));
    }

    @Test
    void actualizarProductoGranel_noExiste_debeRetornarFalse() {
        ProductoGranel p = new ProductoGranel("ZZ999", "X", 'G', "KG", 10.0f);
        assertFalse(repo.actualizarProductoGranel(p));
    }

    @Test
    void actualizarProductoGranel_cantidadCero_debeRetornarFalse() {
        repo.agregarProductoGranel(productoValido);
        ProductoGranel p = new ProductoGranel("AT001", "Arroz", 'G', "KG", 0.0f);
        assertFalse(repo.actualizarProductoGranel(p));
    }

    @Test
    void actualizarProductoGranel_null_debeRetornarFalse() {
        assertFalse(repo.actualizarProductoGranel(null));
    }

    // =========================================================================
    // ELIMINAR
    // =========================================================================

    @Test
    void eliminarProductoGranel_flujoNormal_debeRetornarTrue() {
        repo.agregarProductoGranel(productoValido);
        assertTrue(repo.eliminarProductoGranel("AT001"));
    }

    @Test
    void eliminarProductoGranel_claveInexistente_debeRetornarFalse() {
        assertFalse(repo.eliminarProductoGranel("ZZ999"));
    }

    @Test
    void eliminarProductoGranel_claveNull_debeRetornarFalse() {
        assertFalse(repo.eliminarProductoGranel(null));
    }
}
