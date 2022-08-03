package dao;

import models.Exam;

import java.util.Map;

public interface ExamDao extends CrudDao<Exam>{
    public Exam getByName(String name) throws DaoException;
    public Map<String, Integer> findAllByCandidatesId(int id) throws DaoException;
}
