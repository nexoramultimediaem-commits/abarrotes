/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.abarrotesobjetosdominio_00000278938;

/**
 *
 * @author Acer
 */
import java.util.Date;
import java.util.Objects;

public class Movimiento {
    protected String cveMovimiento;
    protected Date fecha;
    protected boolean procesado;
    
    //Constructor
    public Movimiento(String cveMovimiento){
        this.cveMovimiento = cveMovimiento;
        this.fecha = null;
        this.procesado = false;
    }
    
    //Constructor por defecto 
    public Movimiento(){
    this.cveMovimiento = generarClave();
    this.fecha = null;
    this.procesado = false;
    }
    
    //Constructor que inicializa los atributos
    public Movimiento(String cveMovimiento, Date fecha, boolean procesado){
        this.cveMovimiento = cveMovimiento;
        setFecha(fecha);
        this.procesado = procesado;
    }
    
    //Clave automática 
    private String generarClave(){
        String generarClave =  "MV001";
        if (generarClave.matches("MV\\d{3}")){
            return generarClave;
        }else{
            return "ERROR";
        }
    }
    
    //Setters y Getters
    public String getCveMovimiento(){
        return cveMovimiento;
    }
    
    public void setCveMovimiento(String cveMovimiento){
        this.cveMovimiento = cveMovimiento;
    }
    
    
    public Date getFecha(){
        return fecha;
    }
    
    public boolean getProcesado(){
        return procesado;
    }
    
    public void setProcesado(boolean procesado){
        this.procesado = procesado;
    }
    
     //Fecha no mayor a fecha actual
    public void setFecha(Date fecha){
        Date hoy = new Date();
    
       if (fecha != null && fecha.after(hoy)){
           throw new IllegalArgumentException("Fecha no válida");
       }else{
           this.fecha = fecha;
       }
    }
       
    //toSring
    @Override
    public String toString() {
        return cveMovimiento +"," + fecha + "," + procesado;
    }

    //Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movimiento)) return false;
        Movimiento m = (Movimiento) o;
        return Objects.equals(cveMovimiento, m.cveMovimiento);
    }

    //Hashcode
    @Override
    public int hashCode() {
        return Objects.hash(cveMovimiento);
    }
    
    }
