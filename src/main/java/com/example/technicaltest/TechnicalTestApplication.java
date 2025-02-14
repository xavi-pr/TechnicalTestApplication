package com.example.technicaltest;

import com.example.technicaltest.configuration.DatabaseConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class TechnicalTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechnicalTestApplication.class, args);
        DatabaseConfiguration.configuration();

        // Drop the table before closing the app
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                try {
                    DatabaseConfiguration.dropTable();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "Shutdown-thread"));
    }
}
