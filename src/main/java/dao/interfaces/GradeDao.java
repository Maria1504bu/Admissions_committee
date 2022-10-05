package dao.interfaces;

import dao.AlreadyExistException;
import dao.DaoException;
import dao.WrongExecutedQueryException;
import models.Grade;

import java.sql.Connection;
import java.util.List;

public interface GradeDao{
    List<Grade> getCandidatesGrades(int applId);

    void save(Connection connection, Grade grade) throws WrongExecutedQueryException, AlreadyExistException, DaoException;

    Grade getById(int id) throws DaoException;

    List<Grade> findAll() throws DaoException;

    List<Grade> getApplGrades(int applId);

    void createApplGradesSet(Connection connection) throws WrongExecutedQueryException, AlreadyExistException;

    void update(Grade grade) throws WrongExecutedQueryException, AlreadyExistException, DaoException;

    void delete(int id) throws WrongExecutedQueryException, DaoException;
}
