
package com.mdmakeup.utils;

import com.mdmakeup.models.Producto;
import java.util.ArrayList;
import java.util.List;

public class ListaDobleEnlazada {
    private Nodo cabeza;
    private Nodo cola;
    private int tamaño;

    private class Nodo {
        Producto producto;
        Nodo anterior;
        Nodo siguiente;

        public Nodo(Producto producto) {
            this.producto = producto;
            this.anterior = null;
            this.siguiente = null;
        }
    }

    public ListaDobleEnlazada() {
        this.cabeza = null;
        this.cola = null;
        this.tamaño = 0;
    }

    public void agregarProducto(Producto producto) {
        Nodo nuevoNodo = new Nodo(producto);
        
        if (cabeza == null) {
            cabeza = nuevoNodo;
            cola = nuevoNodo;
        } else {
            cola.siguiente = nuevoNodo;
            nuevoNodo.anterior = cola;
            cola = nuevoNodo;
        }
        tamaño++;
    }

    public void eliminarProducto(String productoId) {
        Nodo actual = cabeza;
        
        while (actual != null) {
            if (actual.producto.getId().equals(productoId)) {
                if (actual.anterior != null) {
                    actual.anterior.siguiente = actual.siguiente;
                } else {
                    cabeza = actual.siguiente;
                }
                
                if (actual.siguiente != null) {
                    actual.siguiente.anterior = actual.anterior;
                } else {
                    cola = actual.anterior;
                }
                
                tamaño--;
                return;
            }
            actual = actual.siguiente;
        }
    }

    public boolean contieneProducto(String productoId) {
        Nodo actual = cabeza;
        
        while (actual != null) {
            if (actual.producto.getId().equals(productoId)) {
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }
    
    public List<Producto> toList() {
    List<Producto> lista = new ArrayList<>();
    Nodo actual = cabeza;
    
    while (actual != null) {
        lista.add(actual.producto);
        actual = actual.siguiente;
    }
    
    return lista;
}

    public int getTamaño() {
        return tamaño;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }
}