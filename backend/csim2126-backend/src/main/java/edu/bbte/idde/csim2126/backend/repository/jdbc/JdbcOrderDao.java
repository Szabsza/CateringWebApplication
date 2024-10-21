package edu.bbte.idde.csim2126.backend.repository.jdbc;

import edu.bbte.idde.csim2126.backend.model.Order;
import edu.bbte.idde.csim2126.backend.repository.OrderDao;
import edu.bbte.idde.csim2126.backend.repository.RepositoryException;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class JdbcOrderDao implements OrderDao {
    private Order getOrderFromResultSet(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            Order order = new Order();
            order.setId(resultSet.getLong("id"));
            order.setDate(resultSet.getString("date"));
            order.setCustomerName(resultSet.getString("customerName"));
            order.setDeliveryAddress(resultSet.getString("deliveryAddress"));
            order.setTotalPrice(resultSet.getDouble("totalPrice"));
            return order;
        }
        return null;
    }

    @Override
    public Order create(Order entity) {
        log.info("Creating order with ID: {}...", entity.getId());
        PreparedStatement stmt;
        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            stmt = connection.prepareStatement(
                    "INSERT INTO Orders (date, customerName, deliveryAddress, totalPrice) VALUES (?, ?, ?, ?)");
            stmt.setString(1, entity.getDate());
            stmt.setString(2, entity.getCustomerName());
            stmt.setString(3, entity.getDeliveryAddress());
            stmt.setDouble(4, entity.getTotalPrice());
            stmt.execute();
        } catch (SQLException e) {
            log.error("Create failed!");
            throw new RepositoryException("Create failed!", e);
        }
        return entity;
    }

    @Override
    public Order update(Order entity) {
        log.info("Updating order with ID: {}...", entity.getId());
        PreparedStatement stmt;
        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            stmt = connection.prepareStatement(
                    "UPDATE Orders SET date=?, customerName=?, deliveryAddress=?, totalPrice=? WHERE id=?");
            stmt.setString(1, entity.getDate());
            stmt.setString(2, entity.getCustomerName());
            stmt.setString(3, entity.getDeliveryAddress());
            stmt.setDouble(4, entity.getTotalPrice());
            stmt.setLong(5, entity.getId());
            stmt.execute();
        } catch (SQLException e) {
            log.error("Update failed!");
            throw new RepositoryException("Update failed!", e);
        }
        return entity;
    }

    @Override
    public void deleteById(Long id) {
        log.info("Deleting order with ID: {}...", id);
        PreparedStatement stmt;
        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            stmt = connection.prepareStatement(
                    "DELETE FROM Orders WHERE id=?");
            stmt.setLong(1, id);
            stmt.execute();
        } catch (SQLException e) {
            log.error("Delete failed!");
            throw new RepositoryException("Delete failed!", e);
        }
    }

    @Override
    public Order findById(Long id) {
        log.info("Finding order with ID: {}...", id);
        PreparedStatement stmt;
        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            stmt = connection.prepareStatement(
                    "SELECT id, date, customerName, deliveryAddress, totalPrice FROM Orders WHERE id=?");
            stmt.setLong(1, id);

            ResultSet resultSet = stmt.executeQuery();
            return getOrderFromResultSet(resultSet);
        } catch (SQLException e) {
            log.error("Select by ID failed!");
            throw new RepositoryException("Select by ID failed!", e);
        }
    }

    @Override
    public List<Order> findAll() {
        log.info("Finding all orders...");
        List<Order> list = new ArrayList<>();
        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(
                    "SELECT id, date, customerName, deliveryAddress, totalPrice FROM Orders");

            Order order = getOrderFromResultSet(resultSet);
            while (order != null) {
                list.add(order);
                order = getOrderFromResultSet(resultSet);
            }
            return list;
        } catch (SQLException e) {
            log.error("Select failed!");
            throw new RepositoryException("Select failed!", e);
        }
    }

    @Override
    public List<Order> findByCustomersName(String customersName) {
        log.info("Finding orders customers name: {}...", customersName);
        List<Order> list = new ArrayList<>();
        PreparedStatement stmt;
        try (Connection connection = ConnectionManager.getInstance().getConnection()) {
            stmt = connection.prepareStatement(
                    "SELECT id, date, customerName, deliveryAddress, totalPrice FROM Orders WHERE customerName=?");
            stmt.setString(1, customersName);
            ResultSet resultSet = stmt.executeQuery();

            Order order = getOrderFromResultSet(resultSet);
            while (order != null) {
                list.add(order);
                order = getOrderFromResultSet(resultSet);
            }
            return list;
        } catch (SQLException e) {
            log.error("Select by customers name failed!");
            throw new RepositoryException("Select by customers name failed!", e);
        }
    }

}
