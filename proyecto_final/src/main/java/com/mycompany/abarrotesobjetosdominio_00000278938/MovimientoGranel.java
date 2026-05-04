/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.abarrotesobjetosdominio_00000278938;

/**
 *
 * @author Acer
 */
public class MovimientoGranel extends Movimiento {
    private ProductoGranel productoGranel;
    
    //Constructores
    public MovimientoGranel(){
        super();
    }
    
    //Constructor que inicialice solo los atributos
    public MovimientoGranel (String cveMovimiento, ProductoGranel productoGranel){
        super(cveMovimiento);
        this.productoGranel = productoGranel;
    }
    
     //Constructor que reciba solo la clave
    public MovimientoGranel (String cveMovimiento){
        super (cveMovimiento);
    }
     
    //Getter y Setters
    public ProductoGranel getProductoGranel(){
        return productoGranel;
    }
    //Mostrar un error
    public void setProductoGranel (ProductoGranel productoGranel){
        if(productoGranel == null){
            throw new IllegalArgumentException("Producto no puede ser null");
        }
        this.productoGranel = productoGranel;
    }
    
    //toString
    @Override
    public String toString(){
        return super.toString()+ "," + productoGranel;      
    }
}
