package edu.bbte.idde.csim2126.backend.service.implementation;

import edu.bbte.idde.csim2126.backend.model.Menu;
import edu.bbte.idde.csim2126.backend.repository.DaoFactory;
import edu.bbte.idde.csim2126.backend.repository.MenuDao;
import edu.bbte.idde.csim2126.backend.repository.RepositoryException;
import edu.bbte.idde.csim2126.backend.service.MenuService;
import edu.bbte.idde.csim2126.backend.service.ServiceException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MenuServiceImplementation implements MenuService {
    MenuDao menuDao;

    public MenuServiceImplementation() {
        menuDao = DaoFactory.getInstance().getMenuDao();
    }

    @Override
    public void createMenu(Menu menu) {
        try {
            menuDao.create(menu);
        } catch (RepositoryException e) {
            log.error("Menu creation failed!", e);
            throw new ServiceException("Menu creation failed!", e);
        }
    }

    @Override
    public List<Menu> listMenus() {
        try {
            return menuDao.findAll();
        } catch (RepositoryException e) {
            log.error("Listing all menus failed!", e);
            throw new ServiceException("Listing all menus failed!", e);
        }
    }

    @Override
    public Menu getMenu(Long id) {
        try {
            return menuDao.findById(id);
        } catch (RepositoryException e) {
            log.error("Finding menu by id failed!", e);
            throw new ServiceException("Listing all menus failed!", e);
        }
    }

    @Override
    public void deleteMenu(Menu menu) {
        try {
            menuDao.deleteById(menu.getId());
        } catch (RepositoryException e) {
            log.error("Deleting menu failed!", e);
            throw new ServiceException("Deleting menu failed!", e);
        }
    }

    @Override
    public void deleteMenuById(Long id) {
        try {
            menuDao.deleteById(id);
        } catch (RepositoryException e) {
            log.error("Deleting menu failed!", e);
            throw new ServiceException("Deleting menu failed!", e);
        }
    }

    @Override
    public Menu updateMenu(Menu menu) {
        try {
            return menuDao.update(menu);
        } catch (RepositoryException e) {
            log.error("Updating menu failed!", e);
            throw new ServiceException("Updating menu failed!", e);
        }
    }

    @Override
    public List<Menu> getMenusByCategory(String category) {
        try {
            return menuDao.findByCategory(category);
        } catch (RepositoryException e) {
            log.error("Finding menus by category failed!", e);
            throw new ServiceException("Finding menus by category failed!", e);
        }
    }

}
