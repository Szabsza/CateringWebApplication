package edu.bbte.idde.csim2126.springbackend.service.implementation;

import edu.bbte.idde.csim2126.springbackend.controller.exception.NotFoundException;
import edu.bbte.idde.csim2126.springbackend.model.Menu;
import edu.bbte.idde.csim2126.springbackend.model.Order;
import edu.bbte.idde.csim2126.springbackend.repository.MenuRepository;
import edu.bbte.idde.csim2126.springbackend.repository.OrderRepository;
import edu.bbte.idde.csim2126.springbackend.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;

    @Override
    public Menu create(Menu entity) {
        log.info("Creating Menu");
        return menuRepository.save(entity);
    }

    @Override
    public Menu update(Menu entity, Long id) {
        log.info("Updating Menu with id: {}", entity.getId());
        Optional<Menu> menuOptional = menuRepository.findById(id);

        if (menuOptional.isEmpty()) {
            log.error("There is no such Menu with this id: {}", entity.getId());
            throw new NotFoundException();
        }

        entity.setId(id);

        return menuRepository.save(entity);
    }

    @Override
    public List<Menu> findAll() {
        log.info("Finding all Menus");
        return menuRepository.findAll();
    }

    @Override
    public Menu findById(Long id) {
        log.info("Finding Menu by id: {}", id);
        Optional<Menu> menu = menuRepository.findById(id);

        if (menu.isEmpty()) {
            log.error("There is no such Menu with this id: {}", id);
            throw new NotFoundException();
        }

        return menu.get();
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting Menu with id: {}", id);
        if (menuRepository.existsById(id)) {
            log.info("Finding all Orders by menuId: {}", id);
            List<Order> orders = orderRepository.findAllByMenuId(id);

            log.info("Deleting all Orders by menuId: {}", id);
            orderRepository.deleteAll(orders);

            menuRepository.deleteById(id);
            return;
        }
        log.error("There is no such Menu with this id: {}", id);
        throw new NotFoundException();
    }

    @Override
    public List<Menu> findByCategory(String category) {
        log.info("Finding all Menus by category: {}", category);
        return menuRepository.findByCategory(category);
    }

}
