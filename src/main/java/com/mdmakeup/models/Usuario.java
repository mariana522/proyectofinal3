package com.mdmakeup.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String email;
    private String password;
    private String nombre;
    private List<String> direcciones;

    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email != null ? email.trim().toLowerCase() : "";
        this.password = password != null ? password : "";
        this.direcciones = new ArrayList<>();
    }


    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getNombre() { return nombre; }
    public List<String> getDirecciones() { return direcciones; }

    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public void agregarDireccion(String direccion) {
        direcciones.add(direccion);
    }

    public boolean autenticar(String password) {
        return this.password.equals(password);
    }
}
