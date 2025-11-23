package com.dgt.opendata.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Autoescuela {
    @Column(name = "codigo", nullable = false)
    String codigo;
    @Column(name = "nombre", nullable = false)
    String nombre;
    @Column(name = "provincia", nullable = false)
    String provincia;

    public Autoescuela(String codigo, String nombre, String provincia) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.provincia = provincia;
    }
}
