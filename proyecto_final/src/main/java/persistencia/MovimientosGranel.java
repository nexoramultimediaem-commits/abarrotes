package persistencia;

import com.mycompany.abarrotesobjetosdominio_00000278938.MovimientoGranel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Clase de persistencia para los movimientos (compras y ventas) de productos granel.
 * Mantiene registros separados para compras y ventas.
 */
public class MovimientosGranel {

    private List<MovimientoGranel> compras = new ArrayList<>();
    private List<MovimientoGranel> ventas  = new ArrayList<>();

    // -------------------------------------------------------------------------
    // HELPERS PRIVADOS
    // -------------------------------------------------------------------------

    /**
     * Verifica que la fecha esté dentro del mes actual y no sea futura.
     */
    private boolean fechaValida(Date fecha) {
        if (fecha == null) {
            return false;
        }

        Date hoy = new Date();

        // No puede ser después de hoy
        if (fecha.after(hoy)) {
            return false;
        }

        // Debe estar dentro del mes actual
        Calendar calFecha = Calendar.getInstance();
        calFecha.setTime(fecha);

        Calendar calHoy = Calendar.getInstance();
        calHoy.setTime(hoy);

        return calFecha.get(Calendar.YEAR)  == calHoy.get(Calendar.YEAR)
            && calFecha.get(Calendar.MONTH) == calHoy.get(Calendar.MONTH);
    }

    /**
     * Verifica si ya existe una compra del mismo producto en la misma fecha (día).
     */
    private boolean existeCompraEnDia(String cveProducto, Date fecha) {
        Calendar calNueva = Calendar.getInstance();
        calNueva.setTime(fecha);

        for (MovimientoGranel m : compras) {
            if (m.getProductoGranel() != null
                    && m.getProductoGranel().getClave().equals(cveProducto)) {

                Calendar calExistente = Calendar.getInstance();
                calExistente.setTime(m.getFecha());

                if (calExistente.get(Calendar.YEAR)         == calNueva.get(Calendar.YEAR)
                        && calExistente.get(Calendar.MONTH) == calNueva.get(Calendar.MONTH)
                        && calExistente.get(Calendar.DAY_OF_MONTH) == calNueva.get(Calendar.DAY_OF_MONTH)) {
                    return true;
                }
            }
        }
        return false;
    }

    // -------------------------------------------------------------------------
    // COMPRAS
    // -------------------------------------------------------------------------

    /**
     * Registra una compra de producto granel.
     * - Fecha dentro del mes actual y no después de hoy.
     * - Estatus siempre será false al registrar.
     * - No se puede hacer más de una compra del mismo producto en el mismo día.
     */
    public boolean comprarGranel(MovimientoGranel movimiento) {

        if (movimiento == null) {
            return false;
        }

        if (movimiento.getProductoGranel() == null) {
            return false;
        }

        if (!fechaValida(movimiento.getFecha())) {
            return false;
        }

        // No más de un movimiento de un mismo producto por día
        String cveProducto = movimiento.getProductoGranel().getClave();
        if (existeCompraEnDia(cveProducto, movimiento.getFecha())) {
            return false;
        }

        // Al realizar el movimiento su estatus siempre será false
        movimiento.setProcesado(false);

        compras.add(movimiento);
        return true;
    }

    /**
     * Consulta todos los movimientos de compra registrados.
     */
    public List<MovimientoGranel> consultarCompras() {
        return new ArrayList<>(compras);
    }

    /**
     * Consulta las compras dentro de un periodo dado (fechaInicio y fechaFin inclusive).
     */
    public List<MovimientoGranel> consultarComprasPorPeriodo(Date fechaInicio, Date fechaFin) {

        List<MovimientoGranel> resultado = new ArrayList<>();

        if (fechaInicio == null || fechaFin == null) {
            return resultado;
        }

        if (fechaInicio.after(fechaFin)) {
            return resultado;
        }

        for (MovimientoGranel m : compras) {
            Date f = m.getFecha();
            if (f != null && !f.before(fechaInicio) && !f.after(fechaFin)) {
                resultado.add(m);
            }
        }

        return resultado;
    }

    // -------------------------------------------------------------------------
    // VENTAS
    // -------------------------------------------------------------------------

    /**
     * Registra una venta de producto granel.
     * - Fecha dentro del mes actual y no después de hoy.
     * - Estatus siempre será false al registrar.
     */
    public boolean venderGranel(MovimientoGranel movimiento) {

        if (movimiento == null) {
            return false;
        }

        if (movimiento.getProductoGranel() == null) {
            return false;
        }

        if (!fechaValida(movimiento.getFecha())) {
            return false;
        }

        // Al realizar el movimiento su estatus siempre será false
        movimiento.setProcesado(false);

        ventas.add(movimiento);
        return true;
    }

    /**
     * Consulta todos los movimientos de venta registrados.
     */
    public List<MovimientoGranel> consultarVentas() {
        return new ArrayList<>(ventas);
    }

    /**
     * Consulta las ventas dentro de un periodo dado (fechaInicio y fechaFin inclusive).
     */
    public List<MovimientoGranel> consultarVentasPorPeriodo(Date fechaInicio, Date fechaFin) {

        List<MovimientoGranel> resultado = new ArrayList<>();

        if (fechaInicio == null || fechaFin == null) {
            return resultado;
        }

        if (fechaInicio.after(fechaFin)) {
            return resultado;
        }

        for (MovimientoGranel m : ventas) {
            Date f = m.getFecha();
            if (f != null && !f.before(fechaInicio) && !f.after(fechaFin)) {
                resultado.add(m);
            }
        }

        return resultado;
    }
}
