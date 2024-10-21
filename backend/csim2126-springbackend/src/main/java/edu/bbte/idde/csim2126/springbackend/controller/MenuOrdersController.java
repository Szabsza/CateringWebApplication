package edu.bbte.idde.csim2126.springbackend.controller;

import edu.bbte.idde.csim2126.springbackend.dto.MenuOrderInDto;
import edu.bbte.idde.csim2126.springbackend.dto.OrderOutDto;
import edu.bbte.idde.csim2126.springbackend.mapper.OrderMapper;
import edu.bbte.idde.csim2126.springbackend.model.Order;
import edu.bbte.idde.csim2126.springbackend.service.OrderService;
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
@RequestMapping("api/menu-orders")
public class MenuOrdersController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping(path = "/{menuId}")
    public List<OrderOutDto> findAllOrdersForMenu(@PathVariable(name = "menuId") Long menuId) {
        log.info("GET request with menuId path variable: {}", menuId);
        List<Order> ordersByMenuId = orderService.findAllOrdersForMenu(menuId);
        return orderMapper.orderOutsFromOrders(ordersByMenuId);
    }

    @PostMapping(path = "/{menuId}")
    public OrderOutDto createNewOrderForMenu(
            @PathVariable(name = "menuId") Long menuId,
            @RequestBody @Valid MenuOrderInDto orderInDto
    ) {
        log.info("POST request with menuId path variable: {}", menuId);
        Order orderToCreate = orderMapper.orderFromMenuOrderIn(orderInDto);
        Order createdOrder = orderService.createForMenu(orderToCreate, menuId);
        return orderMapper.orderOutFromOrder(createdOrder);
    }

    @DeleteMapping(path = "/{menuId}/{orderId}")
    public void deleteOrderByMenu(
            @PathVariable(name = "menuId") Long menuId,
            @PathVariable(name = "orderId") Long orderId
    ) {
        log.info("DELETE request with menuId path variable: {} and orderId path variable: {}", menuId, orderId);
        orderService.deleteByMenu(orderId, menuId);
    }

}
