package edu.bbte.idde.csim2126.springbackend.service;

import edu.bbte.idde.csim2126.springbackend.model.Menu;
import java.util.List;

public interface MenuService extends BaseService<Menu> {
    List<Menu> findByCategory(String category);

}
