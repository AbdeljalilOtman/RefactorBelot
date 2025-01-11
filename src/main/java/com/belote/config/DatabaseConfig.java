package com.belote.config;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class DatabaseConfig {

    private static Connection connection;

    /**
     * Initializes the database connection using HSQLDB, given the storage folder path.
     */
    public static void init(String storagePath) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class.forName("org.hsqldb.jdbcDriver").newInstance();
        String url = "jdbc:hsqldb:file:" + storagePath + File.separator + "belote";
        connection = DriverManager.getConnection(url, "sa", "");
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            throw new SQLException("Database connection is not initialized or already closed.");
        }
        return connection;
    }

    /**
     * Imports an SQL file (e.g. create.sql) for creating or updating schema.
     */
    public static void importSQL(File sqlFile) throws SQLException, FileNotFoundException {
        if (!sqlFile.exists()) {
            System.out.println("SQL file not found: " + sqlFile.getAbsolutePath());
            return;
        }
        try (Scanner scanner = new Scanner(sqlFile)) {
            scanner.useDelimiter("(;(\r)?\n)|(--\n)");
            Statement statement = getConnection().createStatement();
            while (scanner.hasNext()) {
                String line = scanner.next().trim();
                if (line.startsWith("/*!") && line.endsWith("*/")) {
                    int i = line.indexOf(' ');
                    line = line.substring(i + 1, line.length() - " */".length());
                }
                if (line.length() > 0) {
                    statement.execute(line);
                }
            }
            statement.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Cannot connect to the database. Please ensure no other instance is running.\n" + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
}
