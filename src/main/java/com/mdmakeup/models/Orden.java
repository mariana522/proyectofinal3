
package com.mdmakeup.models;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Orden {
    private String id;
    private LocalDateTime fecha;
    private Map<Producto, Integer> productos; 
    private double subtotal;
    private double descuento;
    private double costoEnvio;
    private double total;
    private String direccionEnvio;
    private String estado;
    
    
    public Orden(String id, String direccionEnvio) {
        this.id = id;
        this.fecha = LocalDateTime.now();
        this.productos = new HashMap<>();
        this.subtotal = 0;
        this.descuento = 0;
        this.costoEnvio = 0;
        this.total = 0;
        this.direccionEnvio = direccionEnvio;
        this.estado = "Pendiente";
    }
    
 
    public String getId() { return id; }
    public LocalDateTime getFecha() { return fecha; }
    public Map<Producto, Integer> getProductos() { return productos; }
    public double getSubtotal() { return subtotal; }
    public double getDescuento() { return descuento; }
    public double getCostoEnvio() { return costoEnvio; }
    public double getTotal() { return total; }
    public String getDireccionEnvio() { return direccionEnvio; }
    public String getEstado() { return estado; }
    
   
    public void setDescuento(double descuento) { 
        this.descuento = descuento;
        calcularTotal();
    }
    
    public void setCostoEnvio(double costoEnvio) { 
        this.costoEnvio = costoEnvio;
        calcularTotal();
    }
    
    public void setEstado(String estado) { this.estado = estado; }
    
   
    public void agregarProducto(Producto producto, int cantidad) {
        productos.put(producto, productos.getOrDefault(producto, 0) + cantidad);
        subtotal += producto.getPrecio() * cantidad;
        calcularTotal();
    }
    
    public void eliminarProducto(Producto producto) {
        if(productos.containsKey(producto)) {
            subtotal -= producto.getPrecio() * productos.get(producto);
            productos.remove(producto);
            calcularTotal();
        }
    }
    
    public void actualizarCantidad(Producto producto, int nuevaCantidad) {
        if(productos.containsKey(producto) && nuevaCantidad > 0) {
            subtotal -= producto.getPrecio() * productos.get(producto);
            productos.put(producto, nuevaCantidad);
            subtotal += producto.getPrecio() * nuevaCantidad;
            calcularTotal();
        }
    }
    
    private void calcularTotal() {
        total = subtotal - descuento + costoEnvio;
    }
    
   
    public String getResumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("Orden #").append(id).append("\n");
        sb.append("Fecha: ").append(fecha).append("\n");
        sb.append("Productos:\n");
        
        for(Map.Entry<Producto, Integer> entry : productos.entrySet()) {
            sb.append("- ").append(entry.getKey().getNombre())
              .append(" x").append(entry.getValue())
              .append(": $").append(entry.getKey().getPrecio() * entry.getValue())
              .append("\n");
        }
        
        sb.append("Subtotal: $").append(subtotal).append("\n");
        sb.append("Descuento: $").append(descuento).append("\n");
        sb.append("Envío: $").append(costoEnvio).append("\n");
        sb.append("TOTAL: $").append(total).append("\n");
        sb.append("Estado: ").append(estado);
        
        return sb.toString();
    }
}