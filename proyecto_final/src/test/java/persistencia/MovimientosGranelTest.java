package persistencia;

import com.mycompany.abarrotesobjetosdominio_00000278938.MovimientoGranel;
import com.mycompany.abarrotesobjetosdominio_00000278938.ProductoGranel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase MovimientosGranel.
 */
public class MovimientosGranelTest {

    private MovimientosGranel repo;
    private ProductoGranel producto;
    private Date hoy;
    private Date ayer;

    @BeforeEach
    void setUp() {
        repo = new MovimientosGranel();
        producto = new ProductoGranel("AT001", "Arroz", 'G', "KG", 50.0f);

        // Fecha de hoy (sin horas futuras)
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 8);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        hoy = cal.getTime();

        // Fecha de ayer
        cal.add(Calendar.DAY_OF_MONTH, -1);
        ayer = cal.getTime();
    }

    // =========================================================================
    // HELPERS
    // =========================================================================

    private MovimientoGranel crearMovimiento(String cve, ProductoGranel p, Date fecha) {
        MovimientoGranel m = new MovimientoGranel(cve, p);
        m.setFecha(fecha);
        return m;
    }

    private Date fechaMesAnterior() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    private Date fechaManana() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    // =========================================================================
    // COMPRAR GRANEL
    // =========================================================================

    @Test
    void comprarGranel_flujoNormal_debeRetornarTrue() {
        MovimientoGranel m = crearMovimiento("MV001", producto, hoy);
        assertTrue(repo.comprarGranel(m));
    }

    @Test
    void comprarGranel_null_debeRetornarFalse() {
        assertFalse(repo.comprarGranel(null));
    }

    @Test
    void comprarGranel_sinProducto_debeRetornarFalse() {
        MovimientoGranel m = new MovimientoGranel("MV001");
        m.setFecha(hoy);
        assertFalse(repo.comprarGranel(m));
    }

    @Test
    void comprarGranel_fechaFutura_debeRetornarFalse() {
        // setFecha en Movimiento lanza excepción si es futura, así que capturamos eso
        assertThrows(IllegalArgumentException.class, () -> {
            MovimientoGranel m = crearMovimiento("MV001", producto, fechaManana());
            repo.comprarGranel(m);
        });
    }

    @Test
    void comprarGranel_fechaMesAnterior_debeRetornarFalse() {
        MovimientoGranel m = crearMovimiento("MV001", producto, fechaMesAnterior());
        assertFalse(repo.comprarGranel(m));
    }

    @Test
    void comprarGranel_mismoProductoMismoDia_debeRetornarFalse() {
        MovimientoGranel m1 = crearMovimiento("MV001", producto, hoy);
        MovimientoGranel m2 = crearMovimiento("MV002", producto, hoy);
        repo.comprarGranel(m1);
        assertFalse(repo.comprarGranel(m2));
    }

    @Test
    void comprarGranel_mismoProductoDiaDiferente_debeRetornarTrue() {
        MovimientoGranel m1 = crearMovimiento("MV001", producto, ayer);
        MovimientoGranel m2 = crearMovimiento("MV002", producto, hoy);
        assertTrue(repo.comprarGranel(m1));
        assertTrue(repo.comprarGranel(m2));
    }

    @Test
    void comprarGranel_estatusSiempreFalse() {
        MovimientoGranel m = crearMovimiento("MV001", producto, hoy);
        m.setProcesado(true); // Intentamos poner true
        repo.comprarGranel(m);
        assertFalse(m.getProcesado()); // Debe quedar en false
    }

    // =========================================================================
    // CONSULTAR COMPRAS
    // =========================================================================

    @Test
    void consultarCompras_conDatos_debeRetornarLista() {
        repo.comprarGranel(crearMovimiento("MV001", producto, hoy));
        assertEquals(1, repo.consultarCompras().size());
    }

    @Test
    void consultarCompras_vacio_debeRetornarListaVacia() {
        assertTrue(repo.consultarCompras().isEmpty());
    }

    @Test
    void consultarComprasPorPeriodo_dentroDelPeriodo_debeRetornarMovimiento() {
        repo.comprarGranel(crearMovimiento("MV001", producto, hoy));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        Date inicio = cal.getTime();

        List<MovimientoGranel> resultado = repo.consultarComprasPorPeriodo(inicio, hoy);
        assertFalse(resultado.isEmpty());
    }

    @Test
    void consultarComprasPorPeriodo_fueraDelPeriodo_debeRetornarVacio() {
        repo.comprarGranel(crearMovimiento("MV001", producto, hoy));

        // Periodo del mes pasado
        Date inicio = fechaMesAnterior();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        cal.add(Calendar.DAY_OF_MONTH, 5);
        Date fin = cal.getTime();

        List<MovimientoGranel> resultado = repo.consultarComprasPorPeriodo(inicio, fin);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void consultarComprasPorPeriodo_parametrosNull_debeRetornarVacio() {
        assertTrue(repo.consultarComprasPorPeriodo(null, null).isEmpty());
    }

    @Test
    void consultarComprasPorPeriodo_inicioMayorQueFin_debeRetornarVacio() {
        assertTrue(repo.consultarComprasPorPeriodo(hoy, ayer).isEmpty());
    }

    // =========================================================================
    // VENDER GRANEL
    // =========================================================================

    @Test
    void venderGranel_flujoNormal_debeRetornarTrue() {
        MovimientoGranel m = crearMovimiento("MV010", producto, hoy);
        assertTrue(repo.venderGranel(m));
    }

    @Test
    void venderGranel_null_debeRetornarFalse() {
        assertFalse(repo.venderGranel(null));
    }

    @Test
    void venderGranel_sinProducto_debeRetornarFalse() {
        MovimientoGranel m = new MovimientoGranel("MV010");
        m.setFecha(hoy);
        assertFalse(repo.venderGranel(m));
    }

    @Test
    void venderGranel_fechaMesAnterior_debeRetornarFalse() {
        MovimientoGranel m = crearMovimiento("MV010", producto, fechaMesAnterior());
        assertFalse(repo.venderGranel(m));
    }

    @Test
    void venderGranel_estatusSiempreFalse() {
        MovimientoGranel m = crearMovimiento("MV010", producto, hoy);
        m.setProcesado(true);
        repo.venderGranel(m);
        assertFalse(m.getProcesado());
    }

    // =========================================================================
    // CONSULTAR VENTAS
    // =========================================================================

    @Test
    void consultarVentas_conDatos_debeRetornarLista() {
        repo.venderGranel(crearMovimiento("MV010", producto, hoy));
        assertEquals(1, repo.consultarVentas().size());
    }

    @Test
    void consultarVentas_vacio_debeRetornarListaVacia() {
        assertTrue(repo.consultarVentas().isEmpty());
    }

    @Test
    void consultarVentasPorPeriodo_dentroDelPeriodo_debeRetornarMovimiento() {
        repo.venderGranel(crearMovimiento("MV010", producto, hoy));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        Date inicio = cal.getTime();

        List<MovimientoGranel> resultado = repo.consultarVentasPorPeriodo(inicio, hoy);
        assertFalse(resultado.isEmpty());
    }

    @Test
    void consultarVentasPorPeriodo_parametrosNull_debeRetornarVacio() {
        assertTrue(repo.consultarVentasPorPeriodo(null, null).isEmpty());
    }
}
