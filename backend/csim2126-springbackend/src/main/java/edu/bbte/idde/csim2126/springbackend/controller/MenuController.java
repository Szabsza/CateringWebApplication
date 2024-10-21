package edu.bbte.idde.csim2126.springbackend.controller;

import edu.bbte.idde.csim2126.springbackend.dto.MenuInDto;
import edu.bbte.idde.csim2126.springbackend.dto.MenuOutDto;
import edu.bbte.idde.csim2126.springbackend.mapper.MenuMapper;
import edu.bbte.idde.csim2126.springbackend.model.Menu;
import edu.bbte.idde.csim2126.springbackend.service.MenuService;
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
@RequestMapping("api/menus")
public class MenuController {
    private final MenuService menuService;
    private final MenuMapper menuMapper;

    @GetMapping
    public List<MenuOutDto> findAllMenus(@Nullable @RequestAttribute(name = "category") String category) {
        if (category == null) {
            log.info("GET request");
            List<Menu> menus = menuService.findAll();
            return menuMapper.menuOutsFromMenus(menus);
        }
        log.info("GET request with category request attribute: {}", category);
        List<Menu> menusByCategory = menuService.findByCategory(category);
        return menuMapper.menuOutsFromMenus(menusByCategory);
    }

    @GetMapping(path = "/{id}")
    public MenuOutDto findMenuById(@PathVariable(name = "id") Long id) {
        log.info("GET request with id path variable: {}", id);
        Menu menu = menuService.findById(id);
        return menuMapper.menuOutFromMenu(menu);
    }

    @PostMapping
    public MenuOutDto createMenu(@RequestBody @Valid MenuInDto menuInDto) {
        log.info("POST request with body: {}", menuInDto);
        Menu menuToCreate = menuMapper.menuFromMenuIn(menuInDto);
        Menu createdMenu = menuService.create(menuToCreate);
        return menuMapper.menuOutFromMenu(createdMenu);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteMenu(@PathVariable(name = "id") Long id) {
        log.info("DELETE request with id path variable: {}", id);
        menuService.deleteById(id);
    }

    @PutMapping(path = "/{id}")
    public MenuOutDto updateMenu(
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid MenuInDto menuInDto
    ) {
        log.info("PUT request with id path variable: {} and body: {}", id, menuInDto);
        Menu menuToUpdate = menuMapper.menuFromMenuIn(menuInDto);
        Menu updatedMenu = menuService.update(menuToUpdate, id);
        return menuMapper.menuOutFromMenu(updatedMenu);
    }

}
