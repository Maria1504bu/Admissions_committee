package dao.interfaces;

import dao.AlreadyExistException;
import dao.WrongExecutedQueryException;
import models.Grade;

import java.util.List;

public interface GradeDao extends CrudDao<Grade>{
    void createApplGradesSet(int applId, int gradeId) throws WrongExecutedQueryException, AlreadyExistException;
    List<Grade> getApplGrades(int applId);
}
