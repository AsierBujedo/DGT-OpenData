package com.dgt.opendata.service.queries;

import org.springframework.jdbc.core.SqlParameter;
import java.sql.Types;

public class Queries implements IQueries {
    
    public Queries() {}

    public void loadDataToDatabase(String extractedPath) {
        SqlParameter[] params = new SqlParameter[] {
            new SqlParameter("extractedPath", Types.VARCHAR)
        };
    }
}
