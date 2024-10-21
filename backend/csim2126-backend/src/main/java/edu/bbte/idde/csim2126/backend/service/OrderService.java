package edu.bbte.idde.csim2126.backend.service;

import edu.bbte.idde.csim2126.backend.model.Order;

import java.util.List;

public interface OrderService {
    void createOrder(Order order);

    List<Order> listOrders();

    Order getOrder(Long id);

    void deleteOrder(Order order);

    void deleteOrderById(Long id);

    Order updateOrder(Order order);

    List<Order> getOrdersByCustomersName(String customersName);

}
