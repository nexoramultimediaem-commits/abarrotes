/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.abarrotesobjetosdominio_00000278938;

/**
 *
 * @author Acer
 */
public class ProductoGranel extends Producto{
    private float cantidad;
    
    public ProductoGranel(float cantidad){
        this.cantidad = cantidad;
    }
    
    public ProductoGranel (String clave, String nombre, char tipo, String unidad, float cantidad){
        super(clave, nombre, tipo, unidad);
        this.cantidad = cantidad;}
    
    public float getCantidad(){
        return cantidad;
    }
    
    public void setCantidad(float cantidad){
        this.cantidad = cantidad;
    }
    
    @Override
    public String toString(){
        return super.toString()+ ",cantidad=" + cantidad;
    }
}
