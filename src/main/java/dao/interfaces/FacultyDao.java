package dao.interfaces;

import dao.AlreadyExistException;
import dao.DaoException;
import dao.WrongExecutedQueryException;
import models.Faculty;

import java.util.List;

public interface FacultyDao {
    
    Faculty getById(int id) throws DaoException;
    
    void save(Faculty model) throws DaoException, AlreadyExistException, WrongExecutedQueryException;
    
    void update(Faculty model) throws DaoException, WrongExecutedQueryException, AlreadyExistException;
    
    void delete(int id) throws DaoException, WrongExecutedQueryException;

    List<Faculty> getAllOrderBy(String lang, String orderBy) throws DaoException;

    public void addSubjectsToFaculty(Faculty faculty) throws WrongExecutedQueryException;
    
    public void updateLangSet(Faculty faculty);
}
