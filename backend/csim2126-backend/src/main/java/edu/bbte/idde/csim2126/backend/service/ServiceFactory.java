package edu.bbte.idde.csim2126.backend.service;

import edu.bbte.idde.csim2126.backend.service.implementation.MenuServiceImplementation;
import edu.bbte.idde.csim2126.backend.service.implementation.OrderServiceImplementation;

public class ServiceFactory {

    private static ServiceFactory instance;

    public static synchronized ServiceFactory getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new ServiceFactory();
        return instance;
    }

    public MenuService getMenuService() {
        return new MenuServiceImplementation();
    }

    public OrderService getOrderService() {
        return new OrderServiceImplementation();
    }

}
