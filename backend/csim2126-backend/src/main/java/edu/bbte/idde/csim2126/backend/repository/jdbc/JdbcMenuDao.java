package edu.bbte.idde.csim2126.backend.repository.jdbc;

import edu.bbte.idde.csim2126.backend.model.Menu;
import edu.bbte.idde.csim2126.backend.repository.MenuDao;
import edu.bbte.idde.csim2126.backend.repository.RepositoryException;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JdbcMenuDao implements MenuDao {
    private Menu getMenuFromResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            Menu menu = new Menu();
            menu.setId(resultSet.getLong("id"));
            menu.setName(resultSet.getString("name"));
            menu.setCategory(resultSet.getString("category"));
            menu.setDescription(resultSet.getString("description"));
            menu.setIngredients(resultSet.getString("ingredients"));
            menu.setPrice(resultSet.getDouble("price"));
            menu.setOrderId(resultSet.getLong("orderId"));
            return menu;
        }
        return null;
    }

    @Override
    public Menu create(Menu entity) {
        log.info("Creating menu with ID: {}...", entity.getId());
        PreparedStatement stmt;
        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            stmt = connection.prepareStatement(
                    "INSERT INTO Menus (name, category, description, ingredients, price) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getCategory());
            stmt.setString(3, entity.getDescription());
            stmt.setString(4, entity.getIngredients());
            stmt.setDouble(5, entity.getPrice());
            stmt.execute();
        } catch (SQLException e) {
            log.error("Create failed!");
            throw new RepositoryException("Create failed!", e);
        }
        return entity;
    }

    @Override
    public Menu update(Menu entity) {
        log.info("Updating menu with ID: {}...", entity.getId());
        PreparedStatement stmt;
        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            stmt = connection.prepareStatement(
                    "UPDATE Menus SET name=?, category=?, description=?, ingredients=?, price=? WHERE id=?");
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getCategory());
            stmt.setString(3, entity.getDescription());
            stmt.setString(4, entity.getIngredients());
            stmt.setDouble(5, entity.getPrice());
            stmt.setLong(6, entity.getId());
            stmt.execute();
        } catch (SQLException e) {
            log.error("Update failed!");
            throw new RepositoryException("Update failed!", e);
        }
        return entity;
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting menu with ID: {}...", id);
        PreparedStatement stmt;
        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            stmt = connection.prepareStatement(
                    "DELETE FROM Menus WHERE id=?");
            stmt.setLong(1, id);
            stmt.execute();
        } catch (SQLException e) {
            log.error("Delete failed!");
            throw new RepositoryException("Delete failed!", e);
        }
    }

    @Override
    public Menu findById(Long id) {
        log.info("Finding menu with ID: {}...", id);
        PreparedStatement stmt;
        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            stmt = connection.prepareStatement(
                    "SELECT id, name, category, description, ingredients, price, orderId FROM Menus WHERE id=?");
            stmt.setLong(1, id);

            ResultSet resultSet = stmt.executeQuery();
            return getMenuFromResultSet(resultSet);
        } catch (SQLException e) {
            log.error("Select by ID failed!");
            throw new RepositoryException("Select by ID failed!", e);
        }
    }

    @Override
    public List<Menu> findAll() {
        log.info("Finding all menus...");
        List<Menu> list = new ArrayList<>();
        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(
                    "SELECT id, name, category, description, ingredients, price, orderId FROM Menus");

            Menu menu = getMenuFromResultSet(resultSet);
            while (menu  != null) {
                list.add(menu);
                menu = getMenuFromResultSet(resultSet);
            }
            return list;
        } catch (SQLException e) {
            log.error("Select failed!");
            throw new RepositoryException("Select failed!", e);
        }
    }

    @Override
    public List<Menu> findByCategory(String category) {
        log.info("Finding menus by category: {}...", category);
        List<Menu> list = new ArrayList<>();
        PreparedStatement stmt;
        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            stmt = connection.prepareStatement(
                    "SELECT id, name, category, description, ingredients, price, orderId FROM Menus WHERE category=?");
            stmt.setString(1, category);
            ResultSet resultSet = stmt.executeQuery();

            Menu menu = getMenuFromResultSet(resultSet);
            while (menu  != null) {
                list.add(menu);
                menu = getMenuFromResultSet(resultSet);
            }
            return list;
        } catch (SQLException e) {
            log.error("Select by category failed!");
            throw new RepositoryException("Select by category failed!", e);
        }
    }
}
