package persistencia;

import com.mycompany.abarrotesobjetosdominio_00000278938.Producto;
import java.util.HashMap;

/**
 * Clase de persistencia para el catálogo de productos.
 * Actúa como repositorio de objetos Producto.
 */
public class Productos {

    private HashMap<String, Producto> productos = new HashMap<>();

    // -------------------------------------------------------------------------
    // AGREGAR
    // -------------------------------------------------------------------------

    /**
     * Agrega un nuevo producto al catálogo.
     * @return true si se agregó correctamente, false en cualquier caso inválido.
     */
    public boolean agregarProducto(Producto producto) {

        if (producto == null) {
            return false;
        }

        // No pueden existir dos productos con la misma clave
        if (productos.containsKey(producto.getClave())) {
            return false;
        }

        // La clave debe tener formato: 2 letras + 3 dígitos (ej. AT001)
        if (producto.getClave() == null || !producto.getClave().matches("[A-Za-z]{2}\\d{3}")) {
            return false;
        }

        // No agregar si no tiene nombre o tipo
        if (producto.getNombre() == null || producto.getNombre().isEmpty()
                || producto.getTipo() == null || producto.getTipo().isEmpty()) {
            return false;
        }

        // La unidad solo puede ser KG, L o PZ
        if (!producto.getUnidad().equals("KG")
                && !producto.getUnidad().equals("L")
                && !producto.getUnidad().equals("PZ")) {
            return false;
        }

        // El tipo solo puede ser E (empaquetado) o G (granel)
        if (!producto.getTipo().equals("E") && !producto.getTipo().equals("G")) {
            return false;
        }

        productos.put(producto.getClave(), producto);
        return true;
    }

    // -------------------------------------------------------------------------
    // CONSULTAR
    // -------------------------------------------------------------------------

    /**
     * Consulta un producto individual por su clave.
     * @return El producto encontrado, o null si no existe.
     */
    public Producto consultarProducto(String clave) {
        if (clave == null || clave.isEmpty()) {
            return null;
        }
        return productos.get(clave);
    }

    /**
     * Consulta el catálogo completo con filtros opcionales de tipo y/o unidad.
     * Si ambos parámetros son null o vacíos, regresa todos los productos.
     */
    public HashMap<String, Producto> consultarProductos(String tipo, String unidad) {

        HashMap<String, Producto> resultado = new HashMap<>();

        for (Producto p : productos.values()) {

            boolean sinTipo    = (tipo   == null || tipo.isEmpty());
            boolean sinUnidad  = (unidad == null || unidad.isEmpty());

            if (sinTipo && sinUnidad) {
                // Sin filtros: todos
                resultado.put(p.getClave(), p);

            } else if (!sinTipo && sinUnidad) {
                // Solo filtro de tipo
                if (p.getTipo().equals(tipo)) {
                    resultado.put(p.getClave(), p);
                }

            } else if (sinTipo && !sinUnidad) {
                // Solo filtro de unidad
                if (p.getUnidad().equals(unidad)) {
                    resultado.put(p.getClave(), p);
                }

            } else {
                // Ambos filtros
                if (p.getTipo().equals(tipo) && p.getUnidad().equals(unidad)) {
                    resultado.put(p.getClave(), p);
                }
            }
        }

        return resultado;
    }

    // -------------------------------------------------------------------------
    // ACTUALIZAR
    // -------------------------------------------------------------------------

    /**
     * Actualiza un producto existente.
     * La clave se conserva; el producto debe existir previamente.
     */
    public boolean actualizarProducto(Producto producto) {

        if (producto == null) {
            return false;
        }

        // Debe existir previamente
        if (!productos.containsKey(producto.getClave())) {
            return false;
        }

        // Mismas validaciones que al agregar
        if (producto.getNombre() == null || producto.getNombre().isEmpty()
                || producto.getTipo() == null || producto.getTipo().isEmpty()) {
            return false;
        }

        if (!producto.getUnidad().equals("KG")
                && !producto.getUnidad().equals("L")
                && !producto.getUnidad().equals("PZ")) {
            return false;
        }

        if (!producto.getTipo().equals("E") && !producto.getTipo().equals("G")) {
            return false;
        }

        // Se conserva la misma clave
        productos.put(producto.getClave(), producto);
        return true;
    }

    // -------------------------------------------------------------------------
    // ELIMINAR
    // -------------------------------------------------------------------------

    /**
     * Elimina un producto usando su clave.
     */
    public boolean eliminarProducto(String clave) {

        if (clave == null || clave.isEmpty()) {
            return false;
        }

        if (!productos.containsKey(clave)) {
            return false;
        }

        productos.remove(clave);
        return true;
    }
}
