package dao.interfaces;

import dao.AlreadyExistException;
import dao.DaoException;
import dao.WrongExecutedQueryException;

import java.util.List;

public interface CrudDao<T> {
    T getById(int id) throws DaoException;
    void save(T model) throws DaoException, AlreadyExistException, WrongExecutedQueryException;
    void update (T model) throws DaoException, WrongExecutedQueryException, AlreadyExistException;
    void delete (int id) throws DaoException, WrongExecutedQueryException;
    List<T> findAll() throws DaoException;
}
