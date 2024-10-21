package edu.bbte.idde.csim2126.backend.repository;

import edu.bbte.idde.csim2126.backend.model.BaseModel;

import java.util.List;

public interface Dao<T extends BaseModel, I> {

    T create(T entity);

    T update(T entity);

    void deleteById(I id);

    T findById(I id);

    List<T> findAll();

}
