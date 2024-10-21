package edu.bbte.idde.csim2126.springbackend.service;

import edu.bbte.idde.csim2126.springbackend.model.Order;

import java.util.List;

public interface OrderService extends BaseService<Order> {
    List<Order> findByCustomerName(String customerName);

    List<Order> findAllOrdersForMenu(Long menuId);

    Order createForMenu(Order entity, Long menuId);

    void deleteByMenu(Long orderId, Long menuId);

}
