package edu.bbte.idde.csim2126.backend.repository.jdbc;

import edu.bbte.idde.csim2126.backend.repository.DaoFactory;
import edu.bbte.idde.csim2126.backend.repository.MenuDao;
import edu.bbte.idde.csim2126.backend.repository.OrderDao;

public class JdbcDaoFactory extends DaoFactory {
    private static final MenuDao MENU_DAO = new JdbcMenuDao();
    private static final OrderDao ORDER_DAO = new JdbcOrderDao();

    @Override
    public synchronized MenuDao getMenuDao() {
        return MENU_DAO;
    }

    @Override
    public synchronized OrderDao getOrderDao() {
        return ORDER_DAO;
    }
}
