package edu.bbte.idde.csim2126.backend.repository.memory;

import edu.bbte.idde.csim2126.backend.model.Order;
import edu.bbte.idde.csim2126.backend.repository.OrderDao;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class MemoryOrderDao implements OrderDao {
    private final Map<Long, Order> orders = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Order create(Order entity) {
        entity.setId(idGenerator.incrementAndGet());
        log.info("Creating order with ID: {}...", entity.getId());
        orders.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Order update(Order entity) {
        log.info("Updating order with ID: {}...", entity.getId());
        if (orders.containsKey(entity.getId())) {
            orders.put(entity.getId(), entity);
        }
        return orders.get(entity.getId());
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting order with ID: {}...", id);
        orders.remove(id);
    }

    @Override
    public Order findById(Long id) {
        log.info("Finding order with ID: {}...", id);
        return orders.get(id);
    }

    @Override
    public List<Order> findAll() {
        log.info("Finding all orders...");
        return new ArrayList<>(orders.values());
    }

    @Override
    public List<Order> findByCustomersName(String customersName) {
        log.info("Finding orders by customers name: {}...", customersName);
        List<Order> result = new ArrayList<>();
        for (Order order : orders.values()) {
            if (order.getCustomerName().equals(customersName)) {
                result.add(order);
            }
        }
        return result;
    }
}
