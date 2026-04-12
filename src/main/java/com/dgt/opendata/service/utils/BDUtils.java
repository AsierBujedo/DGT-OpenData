package com.dgt.opendata.service.utils;

import javax.sql.DataSource;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class BDUtils {

    private BDUtils() {
        // Utility class
    }

    public static class SpParam {
        private final Object value;
        private final int sqlType;

        public SpParam(Object value, int sqlType) {
            this.value = value;
            this.sqlType = sqlType;
        }

        public Object getValue() {
            return value;
        }

        public int getSqlType() {
            return sqlType;
        }
    }

    public static SpParam param(Object value, int sqlType) {
        return new SpParam(value, sqlType);
    }

    public static ResultSet executeStoredProcedure(
            DataSource dataSource,
            String storedProcedure,
            SpParam... params
    ) throws SQLException {
        String sql = buildCallSql(storedProcedure, params.length);

        try (
            Connection connection = dataSource.getConnection();
            CallableStatement stmt = connection.prepareCall(sql)
        ) {
            for (int i = 0; i < params.length; i++) {
                setParameter(stmt, i + 1, params[i]);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                CachedRowSet cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();
                cachedRowSet.populate(rs);
                return cachedRowSet;
            }
        }
    }

    private static String buildCallSql(String storedProcedure, int paramCount) {
        StringBuilder sb = new StringBuilder();
        sb.append("{call ").append(storedProcedure).append("(");

        for (int i = 0; i < paramCount; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append("?");
        }

        sb.append(")}");
        return sb.toString();
    }

    private static void setParameter(CallableStatement stmt, int index, SpParam param) throws SQLException {
        if (param.getValue() == null) {
            stmt.setNull(index, param.getSqlType());
        } else {
            stmt.setObject(index, param.getValue(), param.getSqlType());
        }
    }
}