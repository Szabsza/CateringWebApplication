package edu.bbte.idde.csim2126.springbackend.service;

import edu.bbte.idde.csim2126.springbackend.model.BaseEntity;

import java.util.List;

public interface BaseService<T extends BaseEntity> {
    T create(T entity);

    T update(T entity, Long id);

    List<T> findAll();

    T findById(Long id);

    void deleteById(Long id);

}
