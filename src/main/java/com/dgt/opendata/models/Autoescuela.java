package com.dgt.opendata.models;

public class Autoescuela {

    public String id_autoescuela;
    public String codigo_autoescuela;
    public String nombre_autoescuela;
    public String provincia;
    public String seccion;

    public Autoescuela(String id_autoescuela, String codigo_autoescuela, String nombre_autoescuela, String provincia, String seccion) {
        this.id_autoescuela = id_autoescuela;
        this.codigo_autoescuela = codigo_autoescuela;
        this.nombre_autoescuela = nombre_autoescuela;
        this.provincia = provincia;
        this.seccion = seccion;
    }
}
