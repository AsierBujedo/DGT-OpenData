package com.dgt.opendata.service;

import java.util.List;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

import com.dgt.opendata.models.Autoescuela;
import com.dgt.opendata.models.Centro;
import com.dgt.opendata.models.Examen;
import com.dgt.opendata.service.queries.IQueries;

@Service
public class ApplicationService {
    
    private final IQueries queries;

    public ApplicationService(IQueries queries) {
        this.queries = queries;
    }

    public List<Autoescuela> getAutoescuelas(String codigo_autoescuela, String nombre_autoescuela, String provincia, String seccion) throws Exception {
        List<Autoescuela> autoescuelas = new ArrayList<>();

        try (var rs = queries.executeQueryGetAutoescuelas(codigo_autoescuela, nombre_autoescuela, provincia, seccion)) {
            while (rs.next()) {
                autoescuelas.add(new Autoescuela(
                    rs.getString("id"),
                    rs.getString("codigo_autoescuela"),
                    rs.getString("nombre_autoescuela"),
                    rs.getString("desc_provincia"),
                    rs.getString("codigo_seccion")
                ));
            }
        }

        return autoescuelas;
    }

    public Date getLastUpdate() throws Exception {
        try (var rs = queries.executeQueryGetLastUpdate()) {
            if (rs.next()) {
                return rs.getDate("last_update");
            } else {
                throw new Exception("No se pudo obtener la fecha de última actualización");
            }
        }
    }

    public List<String> getPermisosAutoescuela(String id_autoescuela) throws Exception {
        List<String> permisos = new ArrayList<>();

        try (var rs = queries.executeQueryGetPermisosAutoescuela(id_autoescuela)) {
            while (rs.next()) {
                permisos.add(rs.getString("nombre_permiso").trim());
            }
        }

        return permisos;
    }

    public List<Centro> getCentrosByAutoescuela(String id_autoescuela) throws Exception {
        List<Centro> centros = new ArrayList<>();

        try (var rs = queries.executeQueryGetJefaturasPorAutoescuela(id_autoescuela)) {
            while (rs.next()) {
                centros.add(new Centro(
                    rs.getString("provincia"),
                    rs.getString("centro")
                ));
            }
        }

        return centros;
    }

    public List<Examen> getExamenes(String codigo_autoescuela, String codigo_seccion, String provincia, String centro_examen, String permiso, String tipo_examen, String mes, String anyo, boolean isAddition) throws Exception {
        List<Examen> examenes = new ArrayList<>();

        try (var rs = queries.executeQueryGetExamen(codigo_autoescuela, codigo_seccion, provincia, centro_examen, permiso, tipo_examen, mes, anyo, isAddition)) {
            while (rs.next()) {
                examenes.add(new Examen(
                    rs.getString("nombre_autoescuela"),
                    rs.getString("provincia"),
                    rs.getString("seccion"),
                    rs.getLong("numero_aptos"),
                    rs.getLong("numero_no_aptos"),
                    rs.getLong("numero_aptos_1"),
                    rs.getLong("numero_aptos_2"),
                    rs.getLong("numero_aptos_3o4"),
                    rs.getLong("numero_aptos_mas_5")
                ));
            }
        }

        return examenes;
    }
}
