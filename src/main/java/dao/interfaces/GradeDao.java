package dao.interfaces;

import dao.AlreadyExistException;
import dao.WrongExecutedQueryException;
import models.Grade;

public interface GradeDao extends CrudDao<Grade>{
    public void createApplGradesSet(int applId, int gradeId) throws WrongExecutedQueryException, AlreadyExistException;
}
