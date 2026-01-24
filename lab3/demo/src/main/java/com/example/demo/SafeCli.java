package com.example.demo;

import java.sql.*;
import java.util.*;
import java.util.regex.*;

public class SafeCli {

    private static final Pattern CMD =
            Pattern.compile("^(insert|delete|read)\\s+(participant|team|championship)\\s*(\\((.*)\\))?\\s*;?$",
                    Pattern.CASE_INSENSITIVE);

    private static final Pattern PAIR =
            Pattern.compile("(\\w+)\\s*=\\s*'([^']*)'");

    public static void main(String[] args) throws Exception {
        String url = "jdbc:postgresql://127.0.0.1:5432/lab3";
        String user = "postgres";
        String pass = "lilith45";

        System.out.println("Mini SAFE CLI (1.3.3). Commands:");
        System.out.println("insert participant(first_name='A', last_name='B', nickname='c');");
        System.out.println("delete participant(id='1');");
        System.out.println("read participant();");
        System.out.println("Type 'exit'.");

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Scanner sc = new Scanner(System.in)) {

            while (true) {
                System.out.print("CMD> ");
                String line = sc.nextLine().trim();
                if (line.equalsIgnoreCase("exit")) break;
                if (line.isEmpty()) continue;

                Matcher m = CMD.matcher(line);
                if (!m.matches()) {
                    System.out.println("Bad command");
                    continue;
                }

                String action = m.group(1).toLowerCase();
                String table = m.group(2).toLowerCase();
                Map<String, String> params = parsePairs(m.group(4));

                switch (action) {
                    case "insert" -> insert(conn, table, params);
                    case "delete" -> delete(conn, table, params);
                    case "read" -> readAll(conn, table);
                }
            }
        }
    }

    private static Map<String, String> parsePairs(String raw) {
        Map<String, String> map = new LinkedHashMap<>();
        if (raw == null) return map;
        Matcher pm = PAIR.matcher(raw);
        while (pm.find()) map.put(pm.group(1), pm.group(2));
        return map;
    }

    private static void insert(Connection conn, String table, Map<String, String> p) throws SQLException {
        if (p.isEmpty()) { System.out.println("No fields"); return; }
        p.remove("id");

        // whitelist columns per table
        Set<String> allowed = switch (table) {
            case "participant" -> Set.of("first_name", "last_name", "nickname");
            case "team" -> Set.of("name", "city");
            case "championship" -> Set.of("title", "game", "start_date", "end_date");
            default -> Set.of();
        };

        for (String col : p.keySet()) {
            if (!allowed.contains(col)) throw new IllegalArgumentException("Column not allowed: " + col);
        }

        String cols = String.join(", ", p.keySet());
        String q = String.join(", ", Collections.nCopies(p.size(), "?"));
        String sql = "INSERT INTO " + table + " (" + cols + ") VALUES (" + q + ")";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            int i = 1;
            for (Map.Entry<String, String> e : p.entrySet()) {
                if (e.getKey().equals("start_date") || e.getKey().equals("end_date")) {
                    ps.setDate(i++, java.sql.Date.valueOf(e.getValue())); // формат YYYY-MM-DD
                } else {
                    ps.setString(i++, e.getValue());
                }
            }
            System.out.println("Inserted: " + ps.executeUpdate());
        }
    }

    private static void delete(Connection conn, String table, Map<String, String> p) throws SQLException {
        if (!p.containsKey("id")) { System.out.println("Need id"); return; }

        String sql = "DELETE FROM " + table + " WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(p.get("id")));
            System.out.println("Deleted: " + ps.executeUpdate());
        }
    }

    private static void readAll(Connection conn, String table) throws SQLException {
        String sql = "SELECT * FROM " + table + " ORDER BY id";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            ResultSetMetaData md = rs.getMetaData();
            int cols = md.getColumnCount();

            boolean any = false;
            while (rs.next()) {
                any = true;
                for (int i = 1; i <= cols; i++) {
                    System.out.print(md.getColumnName(i) + "=" + rs.getObject(i));
                    if (i < cols) System.out.print(" | ");
                }
                System.out.println();
            }
            if (!any) System.out.println("(no rows)");
        }
    }
}
