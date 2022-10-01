package dao.interfaces;

import dao.DaoException;
import models.Subject;

import java.util.Map;

public interface SubjectDao extends CrudDao<Subject> {
    public Map<Subject, Integer> findAllByFacultyId(int facultyId) throws DaoException;
}
