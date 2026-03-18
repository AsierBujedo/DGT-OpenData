package com.dgt.opendata.service.queries;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class Queries implements IQueries {
    
    private final DataSource dataSource;

    public Queries(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ResultSet executeQueryGetAutoescuelas(String codigo_autoescuela, String nombre_autoescuela, String provincia, String seccion) throws IOException, SQLException {
        String sql = "{call [DGTOpendata].[dbo].[Autoescuelas_GetAutoescuela](?, ?, ?, ?)}";

        Connection connection = dataSource.getConnection();

        var stmt = connection.prepareCall(sql);

        int paramIndex = 1;
        if (codigo_autoescuela != null) {
            stmt.setString(paramIndex++, codigo_autoescuela);
        } else {
            stmt.setNull(paramIndex++, java.sql.Types.VARCHAR);
        }
        if (nombre_autoescuela != null) {
            stmt.setString(paramIndex++, nombre_autoescuela);
        } else {
            stmt.setNull(paramIndex++, java.sql.Types.VARCHAR);
        }
        if (provincia != null) {
            stmt.setString(paramIndex++, provincia);
        } else {
            stmt.setNull(paramIndex++, java.sql.Types.VARCHAR);
        }
        if (seccion != null) {
            stmt.setString(paramIndex++, seccion);
        } else {
            stmt.setNull(paramIndex++, java.sql.Types.VARCHAR);
        }

        return stmt.executeQuery();
    }

    public ResultSet executeQueryGetLastUpdate() throws IOException, SQLException {
        String sql = "{call [DGTOpendata].[dbo].[Ficheros_GetUltimaFecha]()}";

        Connection connection = dataSource.getConnection();

        var stmt = connection.prepareCall(sql);
        return stmt.executeQuery();
    }

    public ResultSet executeQueryGetPermisosAutoescuela(String id_autoescuela) throws IOException, SQLException {
        String sql = "{call [DGTOpendata].[dbo].[Permisos_GetPermisosByAutoescuela](?)}";

        Connection connection = dataSource.getConnection();

        var stmt = connection.prepareCall(sql);
        stmt.setString(1, id_autoescuela);
        return stmt.executeQuery();
    }

    public ResultSet executeQueryGetJefaturasPorAutoescuela(String id_autoescuela) throws IOException, SQLException {
        String sql = "{call [DGTOpendata].[dbo].[Jefaturas_GetProvinciasYCentros](?)}";

        Connection connection = dataSource.getConnection();

        var stmt = connection.prepareCall(sql);
        stmt.setString(1, id_autoescuela);
        return stmt.executeQuery();
    }

    public ResultSet executeQueryGetExamen(String codigo_autoescuela, String codigo_seccion, String provincia, String centro_examen, String permiso, String tipo_examen, int mes, int anyo, boolean isAddition) throws IOException, SQLException {
        String sql = "{call [DGTOpendata].[dbo].[Examenes_GetExamenes](?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        Connection connection = dataSource.getConnection();

        var stmt = connection.prepareCall(sql);
        int paramIndex = 1;
        if (codigo_autoescuela != null) {
            stmt.setString(paramIndex++, codigo_autoescuela);
        } else {
            stmt.setNull(paramIndex++, java.sql.Types.VARCHAR);
        }
        if (codigo_seccion != null) {
            stmt.setString(paramIndex++, codigo_seccion);
        } else {
            stmt.setNull(paramIndex++, java.sql.Types.VARCHAR);
        }
        if (provincia != null) {
            stmt.setString(paramIndex++, provincia);
        } else {
            stmt.setNull(paramIndex++, java.sql.Types.VARCHAR);
        }
        if (centro_examen != null) {
            stmt.setString(paramIndex++, centro_examen);
        } else {
            stmt.setNull(paramIndex++, java.sql.Types.VARCHAR);
        }
        if (permiso != null) {
            stmt.setString(paramIndex++, permiso);
        } else {
            stmt.setNull(paramIndex++, java.sql.Types.VARCHAR);
        }
        if (tipo_examen != null) {
            stmt.setString(paramIndex++, tipo_examen);
        } else {
            stmt.setNull(paramIndex++, java.sql.Types.VARCHAR);
        }
        if (mes != 0) {
            stmt.setInt(paramIndex++, mes);
        } else {
            stmt.setNull(paramIndex++, java.sql.Types.INTEGER);
        }
        if (anyo != 0) {
            stmt.setInt(paramIndex++, anyo);
        } else {
            stmt.setNull(paramIndex++, java.sql.Types.INTEGER);
        }
        stmt.setBoolean(paramIndex++, isAddition);
        return stmt.executeQuery();
    }
}
