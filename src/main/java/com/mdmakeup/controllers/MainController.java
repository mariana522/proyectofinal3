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
         Producto p6 = new Producto("6", "Blush palette", 32000, "image 2.jpg", "Blush");
        Producto p7 = new Producto("7", "Foundation", 42000, "image 3.jpg", "Bases");
        Producto p8 = new Producto("8", "Brushes", 50000, "image 4.jpg", "Brochas");
        Producto p9 = new Producto("9", "Lip gloss", 22000, "image 5.jpg", "Labiales");
        Producto p10 = new Producto("10", "Beauty blender", 6000, "image 6.jpg", "Esponjas");
        Producto p11 = new Producto("11", "Loose powder", 35000, "image 7.jpg", "Polvos");
        Producto p12 = new Producto("12", "Eyeshadow palette", 80000, "image 8.jpg", "Sombras");
        Producto p13 = new Producto("13", "Concealer", 23000, "image 9.jpg", "Correctores");
        Producto p14 = new Producto("14", "Highligter", 24000, "image 10.jpg", "Iluminadores");
        Producto p15 = new Producto("15", "blush", 19000, "image 11.jpg", "Rubores");
        Producto p16 = new Producto("16", "Lip gloss", 21000, "image 14.jpg", "Labiales");
        Producto p17 = new Producto("17", "Borla", 8000, "image 12.jpg", "Esponjas");

        productosDisponibles.agregarProducto(p6);
        productosDisponibles.agregarProducto(p7);
        productosDisponibles.agregarProducto(p8);
        productosDisponibles.agregarProducto(p9);
        productosDisponibles.agregarProducto(p10);
        productosDisponibles.agregarProducto(p11);
        productosDisponibles.agregarProducto(p12);
        productosDisponibles.agregarProducto(p13);
        productosDisponibles.agregarProducto(p14);
        productosDisponibles.agregarProducto(p15);
        productosDisponibles.agregarProducto(p16);
        productosDisponibles.agregarProducto(p17);
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
