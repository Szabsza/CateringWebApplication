package edu.bbte.idde.csim2126.backend.repository;

import edu.bbte.idde.csim2126.backend.model.Order;

import java.util.List;

public interface OrderDao extends Dao<Order, Long> {
    List<Order> findByCustomersName(String customersName);
}
