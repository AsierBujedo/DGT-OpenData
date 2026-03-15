package com.dgt.opendata.service.queries;

import java.util.Properties;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Queries implements IQueries {
    
    private Connection connection;

    public Queries() { }

    public ResultSet executeQueryGetAutoescuelas(String codigo_autoescuela, String nombre_autoescuela, String provincia, String seccion) throws IOException, SQLException {
        String sql = "{call [DGTOpendata].[dbo].[Autoescuelas_GetAutoescuela](?, ?, ?, ?)}";

        Properties props = new Properties();
        try (var input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new IOException("Unable to find application.properties in classpath");
            }
            props.load(input);
        }

        connection = DriverManager.getConnection(
            props.getProperty("spring.datasource.url"),
            props.getProperty("spring.datasource.username"),
            props.getProperty("spring.datasource.password")
        );

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

        Properties props = new Properties();
        try (var input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new IOException("Unable to find application.properties in classpath");
            }
            props.load(input);
        }

        connection = DriverManager.getConnection(
            props.getProperty("spring.datasource.url"),
            props.getProperty("spring.datasource.username"),
            props.getProperty("spring.datasource.password")
        );

        var stmt = connection.prepareCall(sql);
        return stmt.executeQuery();
    }

    public ResultSet executeQueryGetPermisosAutoescuela(String id_autoescuela) throws IOException, SQLException {
        String sql = "{call [DGTOpendata].[dbo].[Permisos_GetPermisosByAutoescuela](?)}";

        Properties props = new Properties();
        try (var input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new IOException("Unable to find application.properties in classpath");
            }
            props.load(input);
        }

        connection = DriverManager.getConnection(
            props.getProperty("spring.datasource.url"),
            props.getProperty("spring.datasource.username"),
            props.getProperty("spring.datasource.password")
        );

        var stmt = connection.prepareCall(sql);
        stmt.setString(1, id_autoescuela);
        return stmt.executeQuery();
    }

    public ResultSet executeQueryGetJefaturasPorAutoescuela(String id_autoescuela) throws IOException, SQLException {
        String sql = "{call [DGTOpendata].[dbo].[Jefaturas_GetProvinciasYCentros](?)}";

        Properties props = new Properties();
        try (var input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new IOException("Unable to find application.properties in classpath");
            }
            props.load(input);
        }

        connection = DriverManager.getConnection(
            props.getProperty("spring.datasource.url"),
            props.getProperty("spring.datasource.username"),
            props.getProperty("spring.datasource.password")
        );

        var stmt = connection.prepareCall(sql);
        stmt.setString(1, id_autoescuela);
        return stmt.executeQuery();
    }
}
