package io.github.onetwostory.salon.dao;

import java.util.List;

public interface GenericDao<T> extends AutoCloseable {
    T findById(int id);
    List<T> findAll();
    void create(T obj);
    void update(T obj);
    void delete(T obj);
    void close();
}
