package com.mycompany.abarrotesobjetosdominio_00000278938;

import java.util.Objects;

/**
 * Clase de dominio que representa un producto del catálogo de abarrotes.
 */
public class Producto {

    private String clave;
    private String nombre;
    private String tipo;    // "E" = empaquetado, "G" = granel
    private String unidad;  // "KG", "L", "PZ"

    // -------------------------------------------------------------------------
    // CONSTRUCTORES
    // -------------------------------------------------------------------------

    public Producto() {
    }

    public Producto(String clave, String nombre, String tipo, String unidad) {
        this.clave  = clave;
        this.nombre = nombre;
        this.tipo   = tipo;
        this.unidad = unidad;
    }

    // Constructor que acepta char para tipo (compatibilidad con ProductoGranel)
    public Producto(String clave, String nombre, char tipo, String unidad) {
        this.clave  = clave;
        this.nombre = nombre;
        this.tipo   = String.valueOf(tipo);
        this.unidad = unidad;
    }

    // -------------------------------------------------------------------------
    // GETTERS Y SETTERS
    // -------------------------------------------------------------------------

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    // -------------------------------------------------------------------------
    // toString, equals, hashCode
    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return clave + "," + nombre + "," + tipo + "," + unidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto)) return false;
        Producto p = (Producto) o;
        return Objects.equals(clave, p.clave);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clave);
    }
}
