package edu.bbte.idde.csim2126.backend.repository;

import edu.bbte.idde.csim2126.backend.repository.jdbc.JdbcDaoFactory;
import edu.bbte.idde.csim2126.backend.repository.memory.MemoryDaoFactory;
import edu.bbte.idde.csim2126.backend.util.ConfigurationProvider;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class DaoFactory {
    private static DaoFactory instance;

    public static synchronized DaoFactory getInstance() {
        if (instance != null) {
            return instance;
        }

        String databaseType = ConfigurationProvider.getMainConfig().getDatabaseType();
        if ("mem".equals(databaseType)) {
            instance = new MemoryDaoFactory();
            log.info("Using in memory implementation.");
        } else if ("jdbc".equals(databaseType)) {
            instance = new JdbcDaoFactory();
            log.info("Using jdbc implementation.");
        } else {
            log.error("Could not read config file...");
            throw new RepositoryException("Could not read config file...");
        }
        instance = new JdbcDaoFactory();
        return instance;
    }

    public abstract MenuDao getMenuDao();

    public abstract OrderDao getOrderDao();

}
