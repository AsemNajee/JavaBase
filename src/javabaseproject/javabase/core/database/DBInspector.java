package javabaseproject.javabase.core.database;

import javabaseproject.javabase.framework.commandline.Command;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * get all metadata of the database
 *
 * @author ChatGPT :(
 * Edited by AsemNajee (:
 */
public class DBInspector {

    private final String url;
    private final String user;
    private final String password;

    public DBInspector(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public List<String> getAllTables() {
        List<String> tables = new ArrayList<>();
        try (Connection conn = connect()) {
            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet rs = meta.getTables(null, null, "%", new String[]{"TABLE"})) {
                while (rs.next()) {
                    tables.add(rs.getString("TABLE_NAME"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tables;
    }

    public void describeTable(String tableName) {
        try (Connection conn = connect()) {
            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet rs = meta.getColumns(null, null, tableName, "%")) {
                StringBuilder json = new StringBuilder("{\n\t\"").append(tableName).append("\" : ");
                Command.println("g{=== table metaData: " + tableName + " ===}");
                while (rs.next()) {
                    json.append("\n\t{\n\t\t\"column\" : \"").append(rs.getString("COLUMN_NAME")).append("\" ,");
                    json.append("\n\t\t\"type\" : \"").append(rs.getString("TYPE_NAME")).append("\" ,");
                    json.append("\n\t\t\"size\" : ").append(rs.getInt("COLUMN_SIZE")).append(",");
                    json.append("\n\t\t\"nullable\" : ").append((rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable ? "true" : "false")).append(",");
                    json.append("\n\t\t\"default\" : ").append(rs.getString("COLUMN_DEF"));
                    json.append("\n\t},");
                }
                json.deleteCharAt(json.length()-1);
                json.append("\n}");
                Command.printJson(json.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listPrimaryKeys(String tableName) {
        try (Connection conn = connect()) {
            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet rs = meta.getPrimaryKeys(null, null, tableName)) {
                System.out.println("=== primaryKeys: " + tableName + " ===");
                while (rs.next()) {
                    System.out.println("column: " + rs.getString("COLUMN_NAME"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listForeignKeys(String tableName) {
        try (Connection conn = connect()) {
            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet rs = meta.getImportedKeys(null, null, tableName)) {
                System.out.println("=== ForeignKeys: " + tableName + " ===");
                while (rs.next()) {
                    System.out.printf("column: %s → table %s(column %s)%n",
                            rs.getString("FKCOLUMN_NAME"),
                            rs.getString("PKTABLE_NAME"),
                            rs.getString("PKCOLUMN_NAME"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void listIndexes(String tableName) {
        try (Connection conn = connect()) {
            DatabaseMetaData meta = conn.getMetaData();
            try (ResultSet rs = meta.getIndexInfo(null, null, tableName, false, false)) {
                System.out.println("=== indexes in table: " + tableName + " ===");
                while (rs.next()) {
                    System.out.printf("index: %s | column: %s |unique? %s%n",
                            rs.getString("INDEX_NAME"),
                            rs.getString("COLUMN_NAME"),
                            rs.getBoolean("NON_UNIQUE") ? "no" : "yes");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // SQLite
        // DBInspector inspector = new DBInspector("jdbc:sqlite:mydb.db", null, null);

        // MySQL
        // DBInspector inspector = new DBInspector("jdbc:mysql://localhost:3306/mydb", "root", "1234");

        DBInspector inspector = new DBInspector("jdbc:sqlite:javabase.db", "", "");

        List<String> tables = inspector.getAllTables();
        System.out.println("tables: " + tables);

        if (!tables.isEmpty()) {
            for (String table: tables) {
                Command.println("---------------------");
                inspector.describeTable(table);
                inspector.listPrimaryKeys(table);
                inspector.listForeignKeys(table);
                inspector.listIndexes(table);
            }
        }
    }
}