package edu.bbte.idde.csim2126.springbackend.mapper;

import edu.bbte.idde.csim2126.springbackend.dto.MenuInDto;
import edu.bbte.idde.csim2126.springbackend.dto.MenuOutDto;
import edu.bbte.idde.csim2126.springbackend.model.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuMapper {
    @Mapping(target = "id", ignore = true)
    Menu menuFromMenuIn(MenuInDto menu);

    MenuOutDto menuOutFromMenu(Menu menu);

    List<MenuOutDto> menuOutsFromMenus(List<Menu> menus);

}
