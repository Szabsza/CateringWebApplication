package edu.bbte.idde.csim2126.springbackend.repository;

import edu.bbte.idde.csim2126.springbackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerName(String customerName);

    List<Order> findAllByMenuId(Long menuId);

}
