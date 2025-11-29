package com.dgt.opendata.service.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

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
    public boolean executeStoredProcedure(String storedProcedure, Map<String, String> params) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=DGTOpendata;encrypt=true;trustServerCertificate=true", "devuser", "devpassword");
        StringBuilder placeholders = new StringBuilder();
        int paramCount = params.size();
        for (int i = 0; i < paramCount; i++) {
            placeholders.append("?");
            if (i < paramCount - 1) {
                placeholders.append(", ");
            }
        
            if (i < paramCount - 1) {
                placeholders.append(", ");
            }
        }
        String callSql = "{call " + storedProcedure + "(" + placeholders.toString() + ")}";
        try (PreparedStatement statement = connection.prepareStatement(callSql)) {
            int index = 1;
            for (String pname : params.keySet()) {
                statement.setString(index++, params.get(pname));
            }
            statement.execute();
        }
        
        // Do not close the connection here if using connection pooling
        return true;
    }
}
