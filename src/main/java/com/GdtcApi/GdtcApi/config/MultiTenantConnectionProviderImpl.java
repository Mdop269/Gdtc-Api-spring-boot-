package com.GdtcApi.GdtcApi.config;


import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// provides a connection for the current tenant  based on the tenant identifier
@Component // or @Service
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider<String> {

    @Value("${spring.datasource.base-url}")
    private String baseUrl; // e.g. "jdbc:postgresql://localhost:5432/"

    @Value("${spring.datasource.tenant-username}")
    private String tenantUsername; // e.g. "postgres"

    @Value("${spring.datasource.tenant-password}")
    private String tenantPassword; // e.g. "postgres"

    // This is the default DB name for "no tenant" scenario
    private static final String DEFAULT_DB = "mdop";

    @Override
    public Connection getAnyConnection() throws SQLException {
        // We'll just connect to the default DB for "any" connection
        String url = baseUrl + DEFAULT_DB;
        return DriverManager.getConnection(url, tenantUsername, tenantPassword);
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        // If not set, use the default "mdop" database
        if (tenantIdentifier == null || tenantIdentifier.isEmpty()) {
            tenantIdentifier = DEFAULT_DB;
        }
        // Build the tenant DB's connection URL
        String url = baseUrl + tenantIdentifier;
        return DriverManager.getConnection(url, tenantUsername, tenantPassword);
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        throw new UnsupportedOperationException("unwrap is not supported");
    }
}
