package edu.bbte.idde.csim2126.springbackend.controller;

import edu.bbte.idde.csim2126.springbackend.dto.OrderInDto;
import edu.bbte.idde.csim2126.springbackend.dto.OrderOutDto;
import edu.bbte.idde.csim2126.springbackend.mapper.OrderMapper;

import edu.bbte.idde.csim2126.springbackend.model.Order;
import edu.bbte.idde.csim2126.springbackend.service.OrderService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(
        origins = "*",
        allowedHeaders = "*",
        exposedHeaders = "*",
        maxAge = 3600,
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.PUT, RequestMethod.DELETE}
)
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping
    public List<OrderOutDto> findAllOrders(@Nullable @RequestAttribute(name = "customerName") String customerName) {
        if (customerName == null) {
            log.info("GET request");
            List<Order> orders = orderService.findAll();
            return orderMapper.orderOutsFromOrders(orders);
        }
        log.info("GET request with customerName request attribute: {}", customerName);
        List<Order> ordersByCustomerName = orderService.findByCustomerName(customerName);
        return orderMapper.orderOutsFromOrders(ordersByCustomerName);
    }

    @GetMapping(path = "/{id}")
    public OrderOutDto findOrderById(@PathVariable(name = "id") Long id) {
        log.info("GET request with id path variable: {}", id);
        Order order = orderService.findById(id);
        return orderMapper.orderOutFromOrder(order);
    }

    @PostMapping
    public OrderOutDto createOrder(@RequestBody @Valid OrderInDto orderInDto) {
        log.info("POST request with body: {}", orderInDto);
        Order orderToCreate = orderMapper.orderFromOrderIn(orderInDto);
        Order createdOrder = orderService.create(orderToCreate);
        return orderMapper.orderOutFromOrder(createdOrder);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteOrder(@PathVariable(name = "id") Long id) {
        log.info("DELETE request with id path variable: {}", id);
        orderService.deleteById(id);
    }

    @PutMapping(path = "/{id}")
    public OrderOutDto updateOrder(
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid OrderInDto orderInDto
    ) {
        log.info("PUT request with id path variable: {} and body: {}", id, orderInDto);
        Order orderToUpdate = orderMapper.orderFromOrderIn(orderInDto);
        Order updatedOrder = orderService.update(orderToUpdate, id);
        return orderMapper.orderOutFromOrder(updatedOrder);
    }

}
