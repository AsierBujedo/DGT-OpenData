package com.dgt.opendata.service.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.jdbc.core.SqlParameter;

public class DBUtils {

    private Connection connection;

    /**
     * Executes a stored procedure with the given name and parameters.
     *
     * @param storedProcedure the name of the stored procedure to execute
     * @param params a map of parameter names and their corresponding values
     * @return true if the stored procedure executed successfully
     * @throws SQLException if a database access error occurs
     */
    public boolean executeStoredProcedure(String storedProcedure, SqlParameter[] params) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=DGTOpendata;encrypt=true;trustServerCertificate=true", "devuser", "devpassword");
        SqlParameter[] localParams = params == null ? new SqlParameter[0] : params;
        int paramCount = localParams.length;
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < paramCount; i++) {
            if (i > 0) {
                placeholders.append(", ");
            }
            placeholders.append("?");
        }
        String callSql = "{call " + storedProcedure + "(" + placeholders.toString() + ")}";
        try (PreparedStatement statement = connection.prepareStatement(callSql)) {
            for (int i = 0; i < paramCount; i++) {
                SqlParameter param = localParams[i];
                Object value = null;
                if (param instanceof org.springframework.jdbc.core.SqlParameterValue) {
                    value = ((org.springframework.jdbc.core.SqlParameterValue) param).getValue();
                }
                if (value != null) {
                    statement.setObject(i + 1, value);
                } else {
                    // If no value provided, bind NULL using the declared SQL type
                    statement.setNull(i + 1, param.getSqlType());
                }
            }
            statement.execute();
        }
        // Do not close the connection here if using connection pooling
        return true;
    }
}
