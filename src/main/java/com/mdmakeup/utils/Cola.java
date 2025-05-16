package com.mdmakeup.utils;

import com.mdmakeup.models.Orden;

public class Cola {
    private Nodo frente;
    private Nodo fin;
    private int tamaño;

    private class Nodo {
        Orden orden;
        Nodo siguiente;

        public Nodo(Orden orden) {
            this.orden = orden;
            this.siguiente = null;
        }
    }

    public Cola() {
        this.frente = null;
        this.fin = null;
        this.tamaño = 0;
    }

    public void encolar(Orden orden) {
        Nodo nuevoNodo = new Nodo(orden);
        
        if (fin == null) {
            frente = nuevoNodo;
            fin = nuevoNodo;
        } else {
            fin.siguiente = nuevoNodo;
            fin = nuevoNodo;
        }
        tamaño++;
    }

    public Orden desencolar() {
        if (frente == null) return null;
        
        Nodo temp = frente;
        frente = frente.siguiente;
        
        if (frente == null) {
            fin = null;
        }
        
        tamaño--;
        return temp.orden;
    }

    public Orden verFrente() {
        return frente != null ? frente.orden : null;
    }

    public int getTamaño() {
        return tamaño;
    }

    public boolean estaVacia() {
        return frente == null;
    }
}