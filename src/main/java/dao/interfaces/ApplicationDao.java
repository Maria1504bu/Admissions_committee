package dao.interfaces;

import dao.AlreadyExistException;
import dao.DaoException;
import dao.WrongExecutedQueryException;
import models.Application;

import java.sql.Connection;
import java.util.List;

public interface ApplicationDao {
    Application getById(int id) throws DaoException;

    public List<Application> getCandidateAppls(int candidateId) ;

    List<Application> getFacultyAppls(int facultyId);

    List<Application> findAll() throws DaoException;

    Connection save(Application application) throws
            WrongExecutedQueryException, AlreadyExistException, DaoException;

    void update(Application application) throws
            WrongExecutedQueryException, AlreadyExistException, DaoException;

    void delete(int id) throws WrongExecutedQueryException, DaoException;
}
