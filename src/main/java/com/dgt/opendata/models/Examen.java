package com.dgt.opendata.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Examenes")
public class Examen {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Column(name = "codigo_autoescuela", nullable = false)
    String codigoAutoescuela;
    @Column(name = "codigo_seccion", nullable = false)
    String codigoSeccion;
    @Column(name = "centro_examen", nullable = false)
    String centroExamen;
    @Column(name = "mes_examen", nullable = false)
    String mesExamen;
    @Column(name = "anio_examen", nullable = false)
    String anioExamen;
    @Column(name = "permiso", nullable = false)
    TipoPermiso permiso;
    @Column(name = "tipo_examen", nullable = false)
    String tipoExamen;
    @Column(name = "numero_aptos", nullable = false)
    int numeroAptos;
    @Column(name = "aptos_primera", nullable = false)
    int aptosPrimera;
    @Column(name = "aptos_segunda", nullable = false)
    int aptosSegunda;
    @Column(name = "aptos_tercera", nullable = false)
    int aptosTercera;
    @Column(name = "aptos_cuarta", nullable = false)
    int aptosCuarta;
    @Column(name = "aptos_quinta", nullable = false)    
    int aptosQuinta;
    @Column(name = "numero_no_aptos", nullable = false)
    int numeroNoAptos;

    public Examen(String codigoAutoescuela, String codigoSeccion, String centroExamen, String mesExamen, String anioExamen, TipoPermiso permiso,
            String tipoExamen, int numeroAptos, int aptosPrimera, int aptosSegunda, int aptosTercera, int aptosCuarta,
            int aptosQuinta, int numeroNoAptos) {
        this.codigoAutoescuela = codigoAutoescuela;
        this.codigoSeccion = codigoSeccion;
        this.centroExamen = centroExamen;
        this.mesExamen = mesExamen;
        this.anioExamen = anioExamen;
        this.permiso = permiso;
        this.tipoExamen = tipoExamen;
        this.numeroAptos = numeroAptos;
        this.aptosPrimera = aptosPrimera;
        this.aptosSegunda = aptosSegunda;
        this.aptosTercera = aptosTercera;
        this.aptosCuarta = aptosCuarta;
        this.aptosQuinta = aptosQuinta;
        this.numeroNoAptos = numeroNoAptos;
    }
}

