package edu.bbte.idde.csim2126.springbackend.service.implementation;

import edu.bbte.idde.csim2126.springbackend.controller.exception.NotFoundException;
import edu.bbte.idde.csim2126.springbackend.model.Menu;
import edu.bbte.idde.csim2126.springbackend.model.Order;
import edu.bbte.idde.csim2126.springbackend.repository.MenuRepository;
import edu.bbte.idde.csim2126.springbackend.repository.OrderRepository;
import edu.bbte.idde.csim2126.springbackend.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;

    @Override
    public Order create(Order entity) {
        log.info("Creating Order");

        log.info("Finding Menu with id: {}", entity.getMenu().getId());
        Optional<Menu> menu = menuRepository.findById(entity.getMenu().getId());

        if (menu.isEmpty()) {
            log.error("There is no such Menu with this id {}", entity.getMenu().getId());
            throw new NotFoundException();
        }

        entity.setMenu(menu.get());
        orderRepository.save(entity);

        return entity;
    }

    @Override
    public Order update(Order entity, Long id) {
        log.info("Updating Order with id: {}", entity.getId());
        Optional<Order> orderOptional = orderRepository.findById(id);

        if (orderOptional.isEmpty()) {
            log.error("There is no such Order with this id: {}", entity.getId());
            throw new NotFoundException();
        }

        entity.setId(id);

        return orderRepository.save(entity);
    }

    @Override
    public List<Order> findAll() {
        log.info("Finding all Orders");
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        log.info("Finding Order by id: {}", id);
        Optional<Order> order = orderRepository.findById(id);

        if (order.isEmpty()) {
            log.error("There is no such Order with this id: {}", id);
            throw new NotFoundException();
        }

        return order.get();
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting Order with id: {}", id);
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);

            return;
        }
        log.error("There is no such Order with this id: {}", id);
        throw new NotFoundException();
    }

    @Override
    public List<Order> findByCustomerName(String customerName) {
        log.info("Finding all Orders by customerName: {}", customerName);
        return orderRepository.findByCustomerName(customerName);
    }

    @Override
    public List<Order> findAllOrdersForMenu(Long menuId) {
        log.info("Finding all Orders by menuId: {}", menuId);
        return orderRepository.findAllByMenuId(menuId);
    }

    @Override
    public Order createForMenu(Order entity, Long menuId) {
        log.info("Creating Order for Menu with menuId: {}", menuId);
        Optional<Menu> menuOptional = menuRepository.findById(menuId);

        if (menuOptional.isEmpty()) {
            log.error("There is no such Menu with this id {}", menuId);
            throw new NotFoundException();
        }

        entity.setMenu(menuOptional.get());
        return orderRepository.save(entity);
    }

    @Override
    public void deleteByMenu(Long orderId, Long menuId) {
        log.info("Deleting Order with id: {} by Menu with id: {}", orderId, menuId);
        Optional<Menu> menuOptional = menuRepository.findById(menuId);

        if (menuOptional.isEmpty()) {
            log.error("There is no such Menu with this id {}", menuId);
            throw new NotFoundException();
        }

        List<Order> ordersByMenu = orderRepository.findAllByMenuId(menuId);

        boolean orderExists = ordersByMenu.stream().anyMatch(order -> order.getId().equals(orderId));
        if (!orderExists) {
            log.error("There is no such Order with this id {} for this Menu with id: {}", orderId, menuId);
            throw new NotFoundException();
        }

        orderRepository.deleteById(orderId);
    }

}
