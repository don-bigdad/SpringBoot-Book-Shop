package com.example.springbootbookshop.config;

import org.testcontainers.containers.MySQLContainer;

public class CustomMySqlContainer extends MySQLContainer<CustomMySqlContainer> {
    private static final String DB_IMAGE = "mysql:8";
    private static  CustomMySqlContainer customSqlContainer;

    private CustomMySqlContainer() {
        super(DB_IMAGE);
    }

    public static synchronized CustomMySqlContainer getInstance() {
        if (customSqlContainer == null) {
            customSqlContainer = new CustomMySqlContainer();
        }
        return customSqlContainer;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("TEST_DB_URL", customSqlContainer.getJdbcUrl());
        System.setProperty("TEST_DB_USERNAME", customSqlContainer.getUsername());
        System.setProperty("TEST_DB_PASSWORD", customSqlContainer.getPassword());
    }

    @Override
    public void stop() {

    }
}
