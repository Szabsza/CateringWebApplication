package edu.bbte.idde.csim2126.springbackend.repository;

import edu.bbte.idde.csim2126.springbackend.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByCategory(String category);

}
