package dao;

import models.Faculty;

public interface FacultyDao extends CrudDao<Faculty> {
    public void addSubjectsToFaculty(Faculty faculty) throws WrongExecutedQueryException;
    public void updateLangSet(Faculty faculty);
}
