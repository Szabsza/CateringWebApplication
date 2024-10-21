package edu.bbte.idde.csim2126.backend.repository;

import edu.bbte.idde.csim2126.backend.model.Menu;

import java.util.List;

public interface MenuDao extends Dao<Menu, Long> {
    List<Menu> findByCategory(String category);
}
