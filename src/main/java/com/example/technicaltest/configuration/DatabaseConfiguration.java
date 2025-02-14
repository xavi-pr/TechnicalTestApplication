package com.example.technicaltest.configuration;

import com.example.technicaltest.utils.Constants;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseConfiguration {

    /**
     * Configure the SQLite database that we are going to use for the app
     */
    public static void configuration() {

        // Connect to the database and perform the proper configuration
        // If it doesn't exist it creates the DB
        try (var conn = DriverManager.getConnection(Constants.DB_PATH)) {
            if (conn != null) {
                // Set autoCommit false so we can control when we want to commit the changes
                conn.setAutoCommit(false);
                var meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
                // Call to create the table
                createTable(conn);
                // Call to insert the date if the table was properly created
                insertData(conn, Constants.CURRENT_RELATIVE_PATH);

            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Create table in the SQLite database
     * @param conn Database connection
     */
    private static void createTable(Connection conn) throws SQLException {
        // Create table SQL
        String sqlCreate = "CREATE TABLE IF NOT EXISTS `products` (\n" +
                "    sku         TEXT not null\n" +
                "        constraint products_pk\n" +
                "            primary key,\n" +
                "    price       REAL,\n" +
                "    description TEXT,\n" +
                "    category    TEXT)";

        // Prepare the statement to execute in the DB
        var statement = conn.prepareStatement(sqlCreate);
        statement.execute();
        // Commit changes
        conn.commit();
        System.out.println("Table 'products' was created.");
    }

    /**
     * Insert the data from the script to the table
     * @param conn Database connection
     */
    private static void insertData(Connection conn, Path currentRelativePath) {

        /** Execute script to insert data to the database
         * Connection of the database
         * Path to the resource
         * False if continue on error
         * False on ignore failed drops
         * "--" Comment prefix of SQL
         * ";" Separator between SQL commands
         * "/*" Start delimiter of comment block
         * "*\/" End delimiter of comment block
         * */
        try {
            ScriptUtils.executeSqlScript(conn,
                    new EncodedResource(new PathResource(currentRelativePath + Constants.SCRIPT_INSERT_PRODUCTS_SQL)),
                    false, false, "--", ";",
                    "/*", "*/");
            conn.commit();
            System.out.println("Data was inserted successfully.");
        } catch (SQLException | ScriptException e) {
            System.err.println(e.getMessage());
            // If it's script problem it should mean that the data is already created
            if (e instanceof ScriptException) {
                System.out.println("Data was already created.");
            }
        }
    }

    /**
     * Drop table
     * @throws SQLException
     */
    public static void dropTable() throws SQLException {
        try (var conn = DriverManager.getConnection(Constants.DB_PATH)) {
            if (conn != null) {
                // Set autoCommit false so we can control when we want to commit the changes
                conn.setAutoCommit(false);
                String sqlDrop = "DROP TABLE IF EXISTS `products`";
                // Drop the table in the DB so we don't have problems everytime we want to run the code
                var statement = conn.prepareStatement(sqlDrop);
                statement.execute();
                conn.commit();
                System.out.println("Table was deleted sucessfully.");
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

}
