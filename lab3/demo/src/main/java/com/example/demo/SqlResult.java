package com.example.demo;

import java.sql.*;
import java.util.*;

public class SqlResult {
    public boolean isResultSet;
    public List<String> columns = new ArrayList<>();
    public List<List<Object>> rows = new ArrayList<>();
    public Integer updateCount;

    public static SqlResult updateCount(int count) {
        SqlResult r = new SqlResult();
        r.isResultSet = false;
        r.updateCount = count;
        return r;
    }

    public static SqlResult fromResultSet(ResultSet rs) throws SQLException {
        SqlResult r = new SqlResult();
        r.isResultSet = true;

        ResultSetMetaData md = rs.getMetaData();
        int cols = md.getColumnCount();
        for (int i = 1; i <= cols; i++) r.columns.add(md.getColumnName(i));

        while (rs.next()) {
            List<Object> row = new ArrayList<>();
            for (int i = 1; i <= cols; i++) row.add(rs.getObject(i));
            r.rows.add(row);
        }
        return r;
    }
}