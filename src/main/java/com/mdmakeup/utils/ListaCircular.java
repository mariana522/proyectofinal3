package com.mdmakeup.utils;

import com.mdmakeup.models.Producto;
import java.util.*;

public class ListaCircular {
    private Nodo primero;
    private Nodo ultimo;
    private int tamaño;

    private class Nodo {
        Producto producto;
        int cantidad;
        Nodo siguiente;

        public Nodo(Producto producto, int cantidad) {
            this.producto = producto;
            this.cantidad = cantidad;
            this.siguiente = null;
        }
    }

    public ListaCircular() {
        this.primero = null;
        this.ultimo = null;
        this.tamaño = 0;
    }

    public Map<Producto, Integer> obtenerProductosConCantidad() {
        Map<Producto, Integer> productos = new HashMap<>();
        if (primero != null) {
            Nodo actual = primero;
            do {
                productos.put(actual.producto, actual.cantidad);
                actual = actual.siguiente;
            } while (actual != primero);
        }
        return productos;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        Nodo nuevoNodo = new Nodo(producto, cantidad);

        if (primero == null) {
            primero = nuevoNodo;
            ultimo = nuevoNodo;
            ultimo.siguiente = primero;
        } else {
            ultimo.siguiente = nuevoNodo;
            nuevoNodo.siguiente = primero;
            ultimo = nuevoNodo;
        }
        tamaño++;
    }

    public void actualizarCantidad(String productoId, int nuevaCantidad) {
    if (primero == null) return;
    
    Nodo actual = primero;
    do {
        if (actual.producto.getId().equals(productoId)) {
            actual.cantidad = nuevaCantidad;
            return;
        }
        actual = actual.siguiente;
    } while (actual != primero);
}

    public void eliminarProducto(String productoId) {
        if (primero == null) return;

        Nodo actual = primero;
        Nodo anterior = ultimo;
        boolean encontrado = false;

        do {
            if (actual.producto.getId().equals(productoId)) {
                if (actual == primero && actual == ultimo) {
                    primero = null;
                    ultimo = null;
                } else if (actual == primero) {
                    primero = primero.siguiente;
                    ultimo.siguiente = primero;
                } else if (actual == ultimo) {
                    anterior.siguiente = primero;
                    ultimo = anterior;
                } else {
                    anterior.siguiente = actual.siguiente;
                }
                tamaño--;
                encontrado = true;
                break;
            }
            anterior = actual;
            actual = actual.siguiente;
        } while (actual != primero);

        if (!encontrado) {
            System.out.println("Producto no encontrado en el carrito");
        }
    }

    public double calcularTotal() {
        if (primero == null) return 0.0;

        double total = 0.0;
        Nodo actual = primero;

        do {
            total += actual.producto.getPrecio() * actual.cantidad;
            actual = actual.siguiente;
        } while (actual != primero);

        return total;
    }

    public int getTamaño() {
        return tamaño;
    }

    public boolean estaVacia() {
        return primero == null;
    }

    public void mostrarCarrito() {
        if (primero == null) {
            System.out.println("El carrito está vacío");
            return;
        }

        Nodo actual = primero;
        System.out.println("--- Carrito de Compras ---");

        do {
            System.out.printf("%s x%d - $%.3f%n",
                actual.producto.getNombre(),
                actual.cantidad,
                actual.producto.getPrecio() * actual.cantidad);
            actual = actual.siguiente;
        } while (actual != primero);

        System.out.printf("TOTAL: $%.3f%n", calcularTotal());
    }

    public List<Producto> obtenerProductos() {
        List<Producto> productos = new ArrayList<>();
        if (primero != null) {
            Nodo actual = primero;
            do {
                productos.add(actual.producto);
                actual = actual.siguiente;
            } while (actual != primero);
        }
        return productos;
    }
    public boolean contieneProducto(String productoId) {
    if (primero == null) return false;
    
    Nodo actual = primero;
    do {
        if (actual.producto.getId().equals(productoId)) {
            return true;
        }
        actual = actual.siguiente;
    } while (actual != primero);
    
    return false;
}
}
