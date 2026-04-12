package com.dgt.opendata.service.queries;

import org.springframework.stereotype.Repository;

import com.dgt.opendata.service.utils.DBUtils;

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
        return DBUtils.executeStoredProcedure(
            dataSource,
            "[DGTOpendata].[dbo].[Autoescuelas_GetAutoescuela]",
            DBUtils.param(codigo_autoescuela, Types.VARCHAR),
            DBUtils.param(nombre_autoescuela, Types.VARCHAR),
            DBUtils.param(provincia, Types.VARCHAR),
            DBUtils.param(seccion, Types.VARCHAR)
        );
    }

    @Override
    public ResultSet executeQueryGetLastUpdate() throws IOException, SQLException {
        return DBUtils.executeStoredProcedure(
            dataSource,
            "[DGTOpendata].[dbo].[Ficheros_GetUltimaFecha]"
        );
    }

    @Override
    public ResultSet executeQueryGetPermisosAutoescuela(String id_autoescuela) throws IOException, SQLException {
        return DBUtils.executeStoredProcedure(
            dataSource,
            "[DGTOpendata].[dbo].[Permisos_GetPermisosByAutoescuela]",
            DBUtils.param(id_autoescuela, Types.VARCHAR)
        );
    }

    @Override
    public ResultSet executeQueryGetJefaturasPorAutoescuela(String id_autoescuela) throws IOException, SQLException {
        return DBUtils.executeStoredProcedure(
            dataSource,
            "[DGTOpendata].[dbo].[Jefaturas_GetProvinciasYCentros]",
            DBUtils.param(id_autoescuela, Types.VARCHAR)
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
        return DBUtils.executeStoredProcedure(
            dataSource,
            "[DGTOpendata].[dbo].[Examenes_GetExamenes]",
            DBUtils.param(codigo_autoescuela, Types.VARCHAR),
            DBUtils.param(codigo_seccion, Types.VARCHAR),
            DBUtils.param(provincia, Types.VARCHAR),
            DBUtils.param(centro_examen, Types.VARCHAR),
            DBUtils.param(permiso, Types.VARCHAR),
            DBUtils.param(tipo_examen, Types.VARCHAR),
            DBUtils.param(mes, Types.VARCHAR),
            DBUtils.param(anyo, Types.VARCHAR),
            DBUtils.param(isAddition, Types.BOOLEAN)
        );
    }
}