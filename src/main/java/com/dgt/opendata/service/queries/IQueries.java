package com.dgt.opendata.service.queries;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IQueries {
    
    public ResultSet executeQueryGetAutoescuelas(String codigo_autoescuela, String nombre_autoescuela, String provincia, String seccion) throws IOException, SQLException;
    public ResultSet executeQueryGetLastUpdate() throws IOException, SQLException;
    public ResultSet executeQueryGetPermisosAutoescuela(String id_autoescuela) throws IOException, SQLException;
    public ResultSet executeQueryGetJefaturasPorAutoescuela(String id_autoescuela) throws IOException, SQLException;
    public ResultSet executeQueryGetExamen(String codigo_autoescuela, String codigo_seccion, String provincia, String centro_examen, String permiso, String tipo_examen, String mes, String anyo, boolean isAddition) throws IOException, SQLException;

}
