package com.example.technicaltest.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {

    public static final String JDBC = "jdbc:sqlite";

    public static final String SCRIPT_INSERT_PRODUCTS_SQL = "products.sql";

    public static final String DB_NAME = "TestDB";

    // Path of the working directory to locate the DB File
    public static final Path CURRENT_RELATIVE_PATH = Paths.get("");
    // Create the JDBC path
    public static final String DB_PATH = Constants.JDBC + ":" + CURRENT_RELATIVE_PATH.toAbsolutePath() + "\\" + DB_NAME ;

    // Products and table columns
    public static final String PRODUCTS_TABLE_NAME = "products";
    public static final String SKU = "sku";
    public static final String PRICE = "price";
    public static final String DESCRIPTION = "description";
    public static final String CATEGORY = "category";

}
