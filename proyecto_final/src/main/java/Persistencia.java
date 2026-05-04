/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.HashMap;
import com.mycompany.abarrotesobjetosdominio_00000278938.MovimientoGranel;
import com.mycompany.abarrotesobjetosdominio_00000278938.ProductoGranel;
import com.mycompany.abarrotesobjetosdominio_00000278938.Movimiento;
import com.mycompany.abarrotesobjetosdominio_00000278938.Producto;
/**
 *
 * @author Acer
 */
public class Persistencia {

    //Sirve para guardas datos en pares
    private HashMap<String, Producto> productos = new HashMap<>();
    private HashMap<String, ProductoGranel> ProductosAGranel = new HashMap<>();

    public boolean agregarProducto(Producto producto) {

        /*
        Agregar nuevo producto
         */
        if (producto == null) {
            return false;
        }

        /*
        Los productos no pueden repetir la misma clave
         */
        if (productos.containsKey(producto.getClave())) {
            return false;
        }
/*
        La clave debe tener 2 letras y 3 números exactamente
         */
        if (producto.getClave() == null || !producto.getClave().matches("^[a-zA-Z]{2}\\d{3}$")) {
            return false;
        }
        /* Clave de 2 caracteres y 3 digitos
        No agregar producto si no tiene nombre ni tipo
        isEmpty verifica si está vació y || significa qque si falla uno se rechaza
         */
       if (producto.getNombre() == null || producto.getNombre().isEmpty()
                || producto.getTipo() == null || producto.getTipo().isEmpty()) {
            return false;
        }

        /*
No agregar producto si la unidad no es "KG, L, PZ"
         */
        if (!producto.getUnidad().equals("KG")
                && !producto.getUnidad().equals("L")
                && !producto.getUnidad().equals("PZ")) {
            return false;
        }

        
        /*
        No agregar producto si el tipo no es "E/G"
         */
        if (!producto.getTipo().equals("E") && !producto.getTipo().equals("G")) {
            return false;
        }

        /*
        Se guarda el producto
         */
        productos.put(producto.getClave(), producto);

        return true;
    }

    /*
    El sistema permite consultar un producto con su clave
     */
    public Producto consultarProducto(String clave) {
        return productos.get(clave);
    }

    /*
    El sistema permite actualizar un producto
    
     */
    public boolean actualizarProducto(Producto producto) {

        /*
        La clave debe tener 2 letras y 3 números exactamente
         */
        if (producto.getClave() == null || !producto.getClave().matches("^[a-zA-Z]{2}\\d{3}$")) {
            return false;
        }

        /*
        Actualizar producto
        
         */
        if (producto == null) {
            return false;
        }

        /*
        Los productos deben existir previamente
        
         */
        if (!productos.containsKey(producto.getClave())) {
            return false;
        }

        /*No agregar producto si no tiene nombre ni tipo
        //isEmpty verifica si está vació y || significa qque si falla uno se rechaza
         */
        if (producto.getNombre() == null || producto.getNombre().isEmpty()
                || producto.getTipo() == null || producto.getTipo().isEmpty()) {
            return false;
        }

        /*
        No agregar producto si la unidad no es "KG, L, PZ"
        
         */
        if (!producto.getUnidad().equals("KG")
                && !producto.getUnidad().equals("L")
                && !producto.getUnidad().equals("PZ")) {
            return false;
        }

        /* 
        No agregar producto si el tipo no es "E/G"
        
         */
        if (!producto.getTipo().equals("E") && !producto.getTipo().equals("G")) {
            return false;
        }

        /*
        Se conserva la clave
         */
        productos.put(producto.getClave(), producto);

        return true;
    }

    /*
    Un producto puede eeliminarse usando su clave
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

    /*
    Consultar listado de productos con filtros
     */
    public HashMap<String, Producto> consultaProductos(String tipo, String unidad) {

        HashMap<String, Producto> resultado = new HashMap<>();

        for (Producto p : productos.values()) {

            /* p = El producto que va revisando en cada momento
            //Sin filtros
             */
            if ((tipo == null || tipo.isEmpty())
                    && (unidad == null || unidad.isEmpty())) {

                resultado.put(p.getClave(), p);
            } //Por tipo
            else if (tipo != null && !tipo.isEmpty()
                    && (unidad == null || unidad.isEmpty())) {

                if (p.getTipo().equals(tipo)) {
                    resultado.put(p.getClave(), p);
                }
            } //Por unidad
            else if (unidad != null && !unidad.isEmpty()
                    && (tipo == null || tipo.isEmpty())) {

                if (p.getUnidad().equals(unidad)) {
                    resultado.put(p.getClave(), p);
                }
            } //Ambos filtros
            else if (p.getTipo().equals(tipo)
                    && p.getUnidad().equals(unidad)) {

                resultado.put(p.getClave(), p);
            }
        }

        return resultado;
    }
    //PUNTO 6
}
