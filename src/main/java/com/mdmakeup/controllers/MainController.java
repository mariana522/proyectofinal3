package com.mdmakeup.controllers;

import com.mdmakeup.models.*;
import com.mdmakeup.utils.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainController {
    private ListaDobleEnlazada productosDisponibles;
    private ListaCircular carrito;
    private Cola ordenes;
    private Usuario usuarioActual;
    private List<Usuario> usuariosRegistrados; 

    public MainController() {
        productosDisponibles = new ListaDobleEnlazada();
        carrito = new ListaCircular();
        ordenes = new Cola();
        usuariosRegistrados = new ArrayList<>(); 

        
        Usuario admin = new Usuario("Administrador", "prueba@gmail.com", "admin123");
        admin.agregarDireccion("Calle 123, Ciudad");
        usuariosRegistrados.add(admin);

        
        cargarProductosEjemplo();
    }

    private void cargarProductosEjemplo() {
        Producto p1 = new Producto("1", "Base de Maquillaje", 50000, "base.jpg", "Bases");
        Producto p2 = new Producto("2", "Labial Rojo", 25000, "labial.jpg", "Labiales");
        Producto p3 = new Producto("3", "Paleta de Sombras", 80000, "sombras.jpg", "Sombras");
        Producto p4 = new Producto("4", "Máscara de Pestañas", 30000, "mascara.jpg", "Máscaras");
        Producto p5 = new Producto("5", "Rubor en Polvo", 35000, "rubor.jpg", "Rubores");

        productosDisponibles.agregarProducto(p1);
        productosDisponibles.agregarProducto(p2);
        productosDisponibles.agregarProducto(p3);
        productosDisponibles.agregarProducto(p4);
        productosDisponibles.agregarProducto(p5);
    }

    
    public boolean autenticarUsuario(String email, String password) {
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.getEmail().equalsIgnoreCase(email.trim()) &&
                usuario.autenticar(password)) {
                usuarioActual = usuario;
                return true;
            }
        }
        return false;
    }

    public boolean registrarUsuario(String nombre, String email, String password) {
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.getEmail().equalsIgnoreCase(email.trim())) {
                return false;
            }
        }

        Usuario nuevoUsuario = new Usuario(nombre, email, password);
        usuariosRegistrados.add(nuevoUsuario);
        usuarioActual = nuevoUsuario; 
        return true;
    }

    
    public void agregarAlCarrito(Producto producto, int cantidad) {
        Map<Producto, Integer> productos = carrito.obtenerProductosConCantidad();
        if (productos.containsKey(producto)) {
            int cantidadActual = productos.get(producto);
            carrito.actualizarCantidad(producto.getId(), cantidadActual + cantidad);
        } else {
            carrito.agregarProducto(producto, cantidad);
        }
    }

    public void eliminarDelCarrito(String productoId) {
        carrito.eliminarProducto(productoId);
    }

    public void actualizarCantidadCarrito(String productoId, int nuevaCantidad) {
        carrito.actualizarCantidad(productoId, nuevaCantidad);
    }

    public Map<Producto, Integer> getProductosConCantidad() {
        return carrito.obtenerProductosConCantidad();
    }

    public double getTotalCarrito() {
        return carrito.calcularTotal();
    }

    
    public List<Producto> getProductosDisponibles() {
        return productosDisponibles.toList(); 
    }

   
    public void crearOrden(String direccionEnvio) {
        if (usuarioActual == null || carrito.estaVacia()) return;

        String ordenId = "ORD-" + System.currentTimeMillis();
        Orden nuevaOrden = new Orden(ordenId, direccionEnvio);

        Map<Producto, Integer> productos = carrito.obtenerProductosConCantidad();
        productos.forEach(nuevaOrden::agregarProducto);

        nuevaOrden.setCostoEnvio(20000); 
        ordenes.encolar(nuevaOrden);

        carrito = new ListaCircular(); 
    }

    
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
}
