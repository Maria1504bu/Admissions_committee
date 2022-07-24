package dao;

import java.util.List;

public interface CrudDao<T> {
    T getById (int id);
    void save(T model);
    void update (T model);
    void delete (int id);
    List<T> findAll();
}
