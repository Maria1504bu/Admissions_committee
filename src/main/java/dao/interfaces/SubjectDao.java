package dao.interfaces;

import dao.DaoException;
import models.Subject;

import java.util.List;

public interface SubjectDao extends CrudDao<Subject> {
    public List<Subject> findAllByFacultyId(int facultyId) throws DaoException;
}
