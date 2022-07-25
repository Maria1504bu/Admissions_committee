package dao;

import java.util.List;

public interface CrudDao<T> {
    T getById (int id) throws DaoException;
    void save(T model) throws DaoException;
    void update (T model) throws DaoException;
    void delete (int id) throws DaoException;
    List<T> findAll() throws DaoException;
}
