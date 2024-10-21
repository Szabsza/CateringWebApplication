package edu.bbte.idde.csim2126.backend.repository.memory;

import edu.bbte.idde.csim2126.backend.repository.DaoFactory;
import edu.bbte.idde.csim2126.backend.repository.MenuDao;
import edu.bbte.idde.csim2126.backend.repository.OrderDao;

public class MemoryDaoFactory extends DaoFactory {
    private static final MenuDao MENU_DAO = new MemoryMenuDao();
    private static final OrderDao ORDER_DAO = new MemoryOrderDao();

    @Override
    public MenuDao getMenuDao() {
        return MENU_DAO;
    }

    @Override
    public OrderDao getOrderDao() {
        return ORDER_DAO;
    }

}
