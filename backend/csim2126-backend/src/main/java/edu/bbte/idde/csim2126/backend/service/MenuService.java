package edu.bbte.idde.csim2126.backend.service;

import edu.bbte.idde.csim2126.backend.model.Menu;

import java.util.List;

public interface MenuService {

    void createMenu(Menu menu);

    List<Menu> listMenus();

    Menu getMenu(Long id);

    void deleteMenu(Menu menu);

    void deleteMenuById(Long id);

    Menu updateMenu(Menu menu);

    List<Menu> getMenusByCategory(String category);

}
