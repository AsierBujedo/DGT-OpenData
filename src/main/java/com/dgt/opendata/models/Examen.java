package com.dgt.opendata.models;

public class Examen {
    public String nombre_autoescuela;
    public String provincia;
    public String seccion;
    public String tipo_examen;
    public String nombre_permiso;
    public Long numero_aptos;
    public Long numero_no_aptos;
    public Long numero_aptos_1;
    public Long numero_aptos_2;
    public Long numero_aptos_3o4;
    public Long numero_aptos_mas_5;
    public String mes;
    public String anyo;

    public Examen(String nombre_autoescuela, String provincia, String seccion, String tipo_examen, 
                  String nombre_permiso, Long numero_aptos, Long numero_no_aptos, Long numero_aptos_1, 
                  Long numero_aptos_2, Long numero_aptos_3o4, Long numero_aptos_mas_5, String mes, String anyo) {
        this.nombre_autoescuela = nombre_autoescuela;
        this.provincia = provincia;
        this.seccion = seccion;
        this.tipo_examen = tipo_examen;
        this.nombre_permiso = nombre_permiso;
        this.numero_aptos = numero_aptos;
        this.numero_no_aptos = numero_no_aptos;
        this.numero_aptos_1 = numero_aptos_1;
        this.numero_aptos_2 = numero_aptos_2;
        this.numero_aptos_3o4 = numero_aptos_3o4;
        this.numero_aptos_mas_5 = numero_aptos_mas_5;
        this.mes = mes;
        this.anyo = anyo;
    }
}
