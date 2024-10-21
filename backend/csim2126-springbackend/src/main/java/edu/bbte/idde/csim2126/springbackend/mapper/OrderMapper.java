package edu.bbte.idde.csim2126.springbackend.mapper;

import edu.bbte.idde.csim2126.springbackend.dto.MenuOrderInDto;
import edu.bbte.idde.csim2126.springbackend.dto.OrderInDto;
import edu.bbte.idde.csim2126.springbackend.dto.OrderOutDto;
import edu.bbte.idde.csim2126.springbackend.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = MenuMapper.class)
public interface OrderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "menuId", target = "menu.id")
    Order orderFromOrderIn(OrderInDto order);

    @Mapping(target = "id", ignore = true)
    Order orderFromMenuOrderIn(MenuOrderInDto menuOrderInDto);

    @Mapping(source = "menu.id", target = "menuId")
    OrderOutDto orderOutFromOrder(Order order);

    List<OrderOutDto> orderOutsFromOrders(List<Order> orders);

}
