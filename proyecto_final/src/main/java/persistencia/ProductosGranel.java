package persistencia;

import com.mycompany.abarrotesobjetosdominio_00000278938.ProductoGranel;
import java.util.HashMap;

/**
 * Clase de persistencia para el inventario de productos a granel.
 * Actúa como repositorio de objetos ProductoGranel.
 */
public class ProductosGranel {

    private HashMap<String, ProductoGranel> inventario = new HashMap<>();

    // -------------------------------------------------------------------------
    // AGREGAR
    // -------------------------------------------------------------------------

    /**
     * Agrega un producto granel al inventario.
     * No permite duplicados ni cantidad igual o menor a 0.
     */
    public boolean agregarProductoGranel(ProductoGranel producto) {

        if (producto == null) {
            return false;
        }

        // No deben existir productos granel repetidos en el inventario
        if (inventario.containsKey(producto.getClave())) {
            return false;
        }

        // La cantidad no puede ser 0 o menor
        if (producto.getCantidad() <= 0) {
            return false;
        }

        inventario.put(producto.getClave(), producto);
        return true;
    }

    // -------------------------------------------------------------------------
    // CONSULTAR
    // -------------------------------------------------------------------------

    /**
     * Consulta un producto granel por su clave.
     */
    public ProductoGranel consultarProductoGranel(String clave) {
        if (clave == null || clave.isEmpty()) {
            return null;
        }
        return inventario.get(clave);
    }

    /**
     * Regresa el inventario completo de productos granel.
     */
    public HashMap<String, ProductoGranel> consultarInventario() {
        return new HashMap<>(inventario);
    }

    // -------------------------------------------------------------------------
    // ACTUALIZAR
    // -------------------------------------------------------------------------

    /**
     * Actualiza un producto granel existente en el inventario.
     * La cantidad actualizada no puede ser 0 o menor.
     */
    public boolean actualizarProductoGranel(ProductoGranel producto) {

        if (producto == null) {
            return false;
        }

        // El producto debe existir previamente
        if (!inventario.containsKey(producto.getClave())) {
            return false;
        }

        // La cantidad no puede ser 0 o menor
        if (producto.getCantidad() <= 0) {
            return false;
        }

        inventario.put(producto.getClave(), producto);
        return true;
    }

    // -------------------------------------------------------------------------
    // ELIMINAR
    // -------------------------------------------------------------------------

    /**
     * Elimina un producto granel del inventario usando su clave.
     */
    public boolean eliminarProductoGranel(String clave) {

        if (clave == null || clave.isEmpty()) {
            return false;
        }

        if (!inventario.containsKey(clave)) {
            return false;
        }

        inventario.remove(clave);
        return true;
    }
}
