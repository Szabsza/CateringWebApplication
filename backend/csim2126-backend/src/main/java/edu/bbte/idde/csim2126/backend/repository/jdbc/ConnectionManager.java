package edu.bbte.idde.csim2126.backend.repository.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.csim2126.backend.util.Config;
import edu.bbte.idde.csim2126.backend.util.ConfigurationProvider;

public final class ConnectionManager {
    private static final Config config = ConfigurationProvider.getMainConfig();
    private static HikariDataSource dataSource;

    public static synchronized HikariDataSource getInstance() {
        if (dataSource == null) {
            dataSource = new HikariDataSource();
            dataSource.setDriverClassName(config.getDriverClass());
            dataSource.setJdbcUrl(config.getJdbcUrl());
            dataSource.setUsername(config.getUsername());
            dataSource.setPassword(config.getPassword());
        }
        return dataSource;
    }

    private ConnectionManager() {}
}
