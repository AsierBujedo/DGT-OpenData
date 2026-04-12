package com.dgt.opendata.service.queries;

import org.springframework.stereotype.Repository;

import com.dgt.opendata.service.utils.BDUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

@Repository
public class Queries implements IQueries {

    private final DataSource dataSource;

    public Queries(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ResultSet executeQueryGetAutoescuelas(
            String codigo_autoescuela,
            String nombre_autoescuela,
            String provincia,
            String seccion
    ) throws IOException, SQLException {
        return BDUtils.executeStoredProcedure(
            dataSource,
            "[DGTOpendata].[dbo].[Autoescuelas_GetAutoescuela]",
            BDUtils.param(codigo_autoescuela, Types.VARCHAR),
            BDUtils.param(nombre_autoescuela, Types.VARCHAR),
            BDUtils.param(provincia, Types.VARCHAR),
            BDUtils.param(seccion, Types.VARCHAR)
        );
    }

    @Override
    public ResultSet executeQueryGetLastUpdate() throws IOException, SQLException {
        return BDUtils.executeStoredProcedure(
            dataSource,
            "[DGTOpendata].[dbo].[Ficheros_GetUltimaFecha]"
        );
    }

    @Override
    public ResultSet executeQueryGetPermisosAutoescuela(String id_autoescuela) throws IOException, SQLException {
        return BDUtils.executeStoredProcedure(
            dataSource,
            "[DGTOpendata].[dbo].[Permisos_GetPermisosByAutoescuela]",
            BDUtils.param(id_autoescuela, Types.VARCHAR)
        );
    }

    @Override
    public ResultSet executeQueryGetJefaturasPorAutoescuela(String id_autoescuela) throws IOException, SQLException {
        return BDUtils.executeStoredProcedure(
            dataSource,
            "[DGTOpendata].[dbo].[Jefaturas_GetProvinciasYCentros]",
            BDUtils.param(id_autoescuela, Types.VARCHAR)
        );
    }

    @Override
    public ResultSet executeQueryGetExamen(
            String codigo_autoescuela,
            String codigo_seccion,
            String provincia,
            String centro_examen,
            String permiso,
            String tipo_examen,
            String mes,
            String anyo,
            boolean isAddition
    ) throws IOException, SQLException {
        return BDUtils.executeStoredProcedure(
            dataSource,
            "[DGTOpendata].[dbo].[Examenes_GetExamenes]",
            BDUtils.param(codigo_autoescuela, Types.VARCHAR),
            BDUtils.param(codigo_seccion, Types.VARCHAR),
            BDUtils.param(provincia, Types.VARCHAR),
            BDUtils.param(centro_examen, Types.VARCHAR),
            BDUtils.param(permiso, Types.VARCHAR),
            BDUtils.param(tipo_examen, Types.VARCHAR),
            BDUtils.param(mes, Types.VARCHAR),
            BDUtils.param(anyo, Types.VARCHAR),
            BDUtils.param(isAddition, Types.BOOLEAN)
        );
    }
}