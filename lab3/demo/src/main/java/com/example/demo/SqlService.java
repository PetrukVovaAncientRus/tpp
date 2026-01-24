package com.example.demo;

import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;

@Service
public class SqlService {
    private final DataSource dataSource;

    public SqlService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public SqlResult executeUnsafe(String sql) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             Statement st = conn.createStatement()) {

            boolean hasRs = st.execute(sql);

            if (hasRs) {
                try (ResultSet rs = st.getResultSet()) {
                    return SqlResult.fromResultSet(rs);
                }
            } else {
                return SqlResult.updateCount(st.getUpdateCount());
            }
        }
    }

    
}
