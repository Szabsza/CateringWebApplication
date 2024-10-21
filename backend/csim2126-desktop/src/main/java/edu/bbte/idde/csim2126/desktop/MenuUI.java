package edu.bbte.idde.csim2126.desktop;

import edu.bbte.idde.csim2126.backend.model.Menu;
import edu.bbte.idde.csim2126.backend.service.MenuService;
import edu.bbte.idde.csim2126.backend.service.ServiceException;
import edu.bbte.idde.csim2126.backend.service.ServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MenuUI {

    private static final Logger LOGGER = LoggerFactory.getLogger(MenuUI.class);

    public static void main(String[] args) {
        LOGGER.info("Desktop application started!");

        Menu menu1 = new Menu();
        menu1.setName("Toast and Eggs");
        menu1.setCategory("Breakfast");
        menu1.setDescription("Menu 1 description");
        menu1.setIngredients("Bread: 100g, Eggs: 2");
        menu1.setPrice(5.0);

        Menu menu2 = new Menu();
        menu2.setName("Crispy");
        menu2.setCategory("Lunch");
        menu2.setDescription("Menu 2 description");
        menu2.setIngredients("Chicken: 200g, Eggs: 1, Flour: 50g, Cornflakes: 50g, Salt: 5g");
        menu2.setPrice(20.0);

        try {
            MenuService menuService = ServiceFactory.getInstance().getMenuService();

            menuService.createMenu(menu1);
            menuService.createMenu(menu2);

            List<Menu> menus = menuService.listMenus();
            for (Menu menu : menus) {
                LOGGER.info(menu.toString());
            }

            menuService.deleteMenu(menu1);

            menu2.setCategory("Dinner");
            menu2.setDescription("Updated description");
            menu2.setIngredients("Chicken: 300g, Eggs: 2, Flour: 100g, Cornflakes: 100g, Salt: 10g\"");
            menuService.updateMenu(menu2);

            menus = menuService.listMenus();
            for (Menu menu : menus) {
                LOGGER.info(menu.toString());
            }

        } catch (ServiceException e) {
            LOGGER.error("Operation failed!", e);
        }

    }
}
