package com.mdmakeup.models;

import java.io.Serializable;

public class Producto implements Serializable {
    private String id;
    private String nombre;
    private double precio;
    private String imagenUrl;
    private String categoria;
    private int cantidad; 
    private static final long serialVersionUID = 1L;

    
    public Producto(String id, String nombre, double precio, String imagenUrl, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.imagenUrl = imagenUrl;
        this.categoria = categoria;
        this.cantidad = 1; 
    }

   
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public String getImagenUrl() { return imagenUrl; }
    public String getCategoria() { return categoria; }
    public int getCantidad() { return cantidad; }

  
    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

 
    @Override
    public String toString() {
        return nombre + " - $" + precio;
    }
}
