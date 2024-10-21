package edu.bbte.idde.csim2126.backend.repository.memory;

import edu.bbte.idde.csim2126.backend.repository.MenuDao;
import edu.bbte.idde.csim2126.backend.model.Menu;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class MemoryMenuDao implements MenuDao {
    private final Map<Long, Menu> menus = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public MemoryMenuDao() {
        Menu menu1 = new Menu();
        menu1.setId(idGenerator.incrementAndGet());
        menu1.setName("Menu A");
        menu1.setCategory("Category A");
        menu1.setDescription("Category A");
        menu1.setIngredients("Ingredients A");
        menu1.setPrice(10.0);

        Menu menu2 = new Menu();
        menu2.setId(idGenerator.incrementAndGet());
        menu2.setName("Menu B");
        menu2.setCategory("Category B");
        menu2.setDescription("Category B");
        menu2.setIngredients("Ingredients B");
        menu2.setPrice(10.0);

        menus.put(menu1.getId(), menu1);
        menus.put(menu2.getId(), menu2);

        log.info("Database migration successful!");
    }

    @Override
    public Menu create(Menu menu) {
        menu.setId(idGenerator.incrementAndGet());
        log.info("Creating menu with ID: {}...", menu.getId());
        menus.put(menu.getId(), menu);
        return menu;
    }

    @Override
    public Menu update(Menu menu) {
        log.info("Updating menu with ID: {}...", menu.getId());
        if (menus.containsKey(menu.getId())) {
            menus.put(menu.getId(), menu);
        }
        return menus.get(menu.getId());
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting menu with ID: {}...", id);
        menus.remove(id);
    }

    @Override
    public Menu findById(Long id) {
        log.info("Finding menu with ID: {}...", id);
        return menus.get(id);
    }

    @Override
    public List<Menu> findAll() {
        log.info("Finding all menus...");
        return new ArrayList<>(menus.values());
    }

    @Override
    public List<Menu> findByCategory(String category) {
        log.info("Finding menus by category: {}...", category);
        List<Menu> result = new ArrayList<>();
        for (Menu menu : menus.values()) {
            if (menu.getCategory().equals(category)) {
                result.add(menu);
            }
        }
        return result;
    }
}
