package com.dgt.opendata.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Autoescuelas")
public class Autoescuela {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
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
