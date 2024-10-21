package edu.bbte.idde.csim2126.backend.service.implementation;

import edu.bbte.idde.csim2126.backend.model.Order;
import edu.bbte.idde.csim2126.backend.repository.DaoFactory;
import edu.bbte.idde.csim2126.backend.repository.OrderDao;
import edu.bbte.idde.csim2126.backend.repository.RepositoryException;
import edu.bbte.idde.csim2126.backend.service.OrderService;
import edu.bbte.idde.csim2126.backend.service.ServiceException;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@Slf4j
public class OrderServiceImplementation implements OrderService {
    OrderDao orderDao;

    public OrderServiceImplementation() {
        orderDao = DaoFactory.getInstance().getOrderDao();
    }

    @Override
    public void createOrder(Order order) {
        try {
            orderDao.create(order);
        } catch (RepositoryException e) {
            log.error("Order creation failed!", e);
            throw new ServiceException("Order creation failed!", e);
        }
    }

    @Override
    public List<Order> listOrders() {
        try {
            return orderDao.findAll();
        } catch (RepositoryException e) {
            log.error("Listing all orders failed!", e);
            throw new ServiceException("Listing all orders failed!", e);
        }
    }

    @Override
    public Order getOrder(Long id) {
        try {
            return orderDao.findById(id);
        } catch (RepositoryException e) {
            log.error("Finding order by id failed!", e);
            throw new ServiceException("Listing all orders failed!", e);
        }
    }

    @Override
    public void deleteOrder(Order order) {
        try {
            orderDao.deleteById(order.getId());
        } catch (RepositoryException e) {
            log.error("Deleting order failed!", e);
            throw new ServiceException("Deleting order failed!", e);
        }
    }

    @Override
    public void deleteOrderById(Long id) {
        try {
            orderDao.deleteById(id);
        } catch (RepositoryException e) {
            log.error("Deleting order failed!", e);
            throw new ServiceException("Deleting order failed!", e);
        }
    }

    @Override
    public Order updateOrder(Order order) {
        try {
            return orderDao.update(order);
        } catch (RepositoryException e) {
            log.error("Updating order failed!", e);
            throw new ServiceException("Updating order failed!", e);
        }
    }

    @Override
    public List<Order> getOrdersByCustomersName(String customersName) {
        try {
            return orderDao.findByCustomersName(customersName);
        } catch (RepositoryException e) {
            log.error("Finding orders by customer`s name failed!", e);
            throw new ServiceException("Finding orders by customer`s failed!", e);
        }
    }

}
